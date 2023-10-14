import React, { useState, useEffect } from 'react';
import { Table, Form, Button, Badge, Row, Col, Container, Modal, Alert, Card } from 'react-bootstrap';
import MyPagination from '../components/MyPagination';
import './RulePage.css'
import RuleModal from '../components/RuleModal';
import {notifyFailure, notifySuccess} from '../utils/ToastUtil'

const RulePage = () => {
  const [currentPage, setCurrentPage] = useState(1);
  const [pageSize, setPageSize] = useState(10);
  const [pages, setPages] = useState();
  const [rules, setRules] = useState([]);
  const [servers, setServers] = useState([]);
  const [applications, setApplications] = useState([]);
  const [filteredRules, setFilteredRules] = useState([]);
  const [nameFilter, setNameFilter] = useState('');
  const [typeFilter, setTypeFilter] = useState('');
  const [applicationFilter, setApplicationFilter] = useState('');
  const [serverFilter, setServerFilter] = useState('');
  const [showModal, setShowModal] = useState(false);
  const [selectedRule, setSelectedRule] = useState({});
  const [showDeleteConfirmation, setShowDeleteConfirmation] = useState(false);
  const [deleteRuleId, setDeleteRuleId] = useState();
  const [resetFilters, setResetFilters] = useState(false);
  const [newRule, setNewRule] = useState({
    name: "",
    type: "",
    description: "",
    enabled: true,
    threshold: null,
    interval: null,
    notificationRecipients: [],
    application: "",
    processNameRegex: "",
    logFilePath: "",
    logPatterns: null,
    linkedServers: []
  });

  // Fetch rules and servers from the backend
  useEffect(() => {
    fetchRules(currentPage, pageSize);
    fetchApplications();
    fetchServers();
  }, []);

  useEffect(() => {
    fetchRules(currentPage, pageSize);
    if (resetFilters) {
      setNameFilter('');
      setTypeFilter('');
      setApplicationFilter('');
      setServerFilter('');
      setResetFilters(false);
    }
  }, [currentPage, pageSize, resetFilters, nameFilter, typeFilter, applicationFilter, serverFilter]);


  const fetchRules = (currentPage, pageSize) => {
    let url = `http://localhost:8090/rules?pageNum=${currentPage}&pageSize=${pageSize}`;

    if (nameFilter !== '') {
      url += `&nameFilter=${encodeURIComponent(nameFilter)}`;
    }

    if (typeFilter !== '') {
      url += `&typeFilter=${encodeURIComponent(typeFilter)}`;
    }

    if (applicationFilter !== '') {
      url += `&applicationFilter=${encodeURIComponent(applicationFilter)}`;
    }

    if (serverFilter !== '') {
      url += `&serverFilter=${encodeURIComponent(serverFilter)}`;
    }

    fetch(url)
      .then(response => response.json())
      .then(data => {
        if (data.code === 200) {
          setRules(data.data.records);
          setFilteredRules(data.data.records);
          setPages(data.data.pages);
        } else {
          console.error(data.message);
          // TODO: 处理请求失败的情况，如显示错误消息等
        }
      })
      .catch(error => {
        console.error('An error occurred while fetching rules:', error);
        // TODO: 处理异常情况，如显示错误消息等
      });
  };

  const fetchApplications = () => {
    // Fetch servers from the backend and set the 'servers' state
    // Example:
    fetch('http://localhost:8090/rules/applications')
      .then(response => response.json())
      .then(data => {
        if (data.code === 200) {
          setApplications(data.data);
        } else {
          console.error(data.message);
          // TODO: 处理请求失败的情况，如显示错误消息等
        }
      })
      .catch(error => {
        console.error('An error occurred while fetching servers:', error);
        // TODO: 处理异常情况，如显示错误消息等
      });
  };

  const fetchServers = () => {
    // Fetch servers from the backend and set the 'servers' state
    // Example:
    fetch('http://localhost:8090/servers/all')
      .then(response => response.json())
      .then(data => {
        if (data.code === 200) {
          setServers(data.data);
        } else {
          console.error(data.message);
          // TODO: 处理请求失败的情况，如显示错误消息等
        }
      })
      .catch(error => {
        console.error('An error occurred while fetching servers:', error);
        // TODO: 处理异常情况，如显示错误消息等
      });
  };

  const handleRuleInputChange = (event) => {
    const { name, value } = event.target;
    let newValue;
    if (name === 'notificationRecipients' || name === 'logPatterns') {
      newValue = value.split("\n");
    } else {
      newValue = value;
    }
    setSelectedRule((prevRule) => ({
      ...prevRule,
      [name]: newValue,
    }));
  };

  const handleShowDeleteConfirmation = (ruleId) => {
    setShowDeleteConfirmation(true);
    setDeleteRuleId(ruleId);
  };

  const handleConfirmDelete = () => {
    const ruleId = deleteRuleId;
    const url = `http://localhost:8090/rules/${ruleId}`;

    fetch(url, {
      method: 'DELETE',
    })
      .then(response => {
        if (response.ok) {
          // Server deleted successfully
          console.log('Rule deleted successfully');
          notifySuccess();
          // Perform any additional tasks if needed
          // Refresh the rules list
          fetchRules(currentPage, pageSize);
        } else {
          response.json().then(data => {
            console.error(data.message);
            // TODO: 处理请求失败的情况，如显示错误消息等
            notifyFailure("Delete rule Failed", data.message);
          });
        }
      })
      .catch(error => {
        console.error('An error occurred while deleting rule:', error);
        // TODO: 处理异常情况，如显示错误消息等
        notifyFailure();
      });

    // 关闭确认框并重置服务器ID
    setShowDeleteConfirmation(false);
    setDeleteRuleId(null);
  };

  const handleDeleteRule = (ruleId) => {
    handleShowDeleteConfirmation(ruleId);
  };

  const handlePageChange = (page) => {
    setCurrentPage(page);
    setResetFilters(false);
  };

  const handlePageSizeChange = (event) => {
    setPageSize(event.target.value);
    setResetFilters(false);
  };

  const handleNameFilterChange = (event) => {
    setNameFilter(event.target.value);
    setResetFilters(false);
  };

  const handleTypeFilterChange = (event) => {
    setTypeFilter(event.target.value);
    setResetFilters(false);
  }

  const handleApplicationFilterChange = (event) => {
    setApplicationFilter(event.target.value);
    setResetFilters(false);
  };

  const handleServerFilterChange = (event) => {
    setServerFilter(event.target.value);
    setResetFilters(false);
  };

  const handleEditRule = (ruleId) => {
    // Find the rule with the given ruleId from the rules state
    const rule = rules.find((rule) => rule.id === ruleId);
    // Perform deep cloning of the rule object
    const clonedRule = JSON.parse(JSON.stringify(rule));
    setSelectedRule(clonedRule);
    setShowModal(true);
  };

  const handleAddRule = () => {
    setSelectedRule(newRule);
    setShowModal(true);
  };

  const handleSaveRule = async () => {
    console.log("handleSaveRule")
    try {
      // 发起保存规则的请求到后端
      const response = await fetch("http://localhost:8090/rules", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(selectedRule),
      });

      if (response.ok) {
        const data = await response.json();
        if (data.code === 200) {
          console.log("Rule saved successfully");
          notifySuccess()
          setShowModal(false);
          fetchRules(currentPage, pageSize);
        } else {
          console.error(data.message);
          // TODO: 处理保存失败的情况，如显示错误消息等
          notifyFailure("Save rule Failed", data.message);
        }
      } else {
        // 保存失败
        console.error("Failed to save rule");
        // TODO: 可以执行其他操作，如显示错误消息等
        notifyFailure();
      }
    } catch (error) {
      console.error("An error occurred while saving rule:", error);
      // TODO: 处理异常情况，如显示错误消息等
      notifyFailure();
    }
  };

  return (
    <Container className='h-100 w-100 py-2'>
      <Card className='h-100 w-100'>
        <Card.Body>
          <Card.Title>Monitoring Rule Configuration</Card.Title>
          <hr/>
          <Form>
          <Row className='mb-3'>
            <Form.Group as={Col} controlId="nameFilter">
              <Form.Label >Name filter</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="Enter name"
                  value={nameFilter}
                  onChange={handleNameFilterChange}
                />
            </Form.Group>
            <Form.Group as={Col} controlId="typeFilter">
              <Form.Label>Type filter</Form.Label>
              <Form.Select
                value={typeFilter}
                onChange={handleTypeFilterChange}
              >
                <option value="">-- All --</option>
                <option value="PROCESS">Process</option>
                <option value="LOG">Log</option>
                {/* Add other types here */}
              </Form.Select>
            </Form.Group>
            <Form.Group as={Col} controlId="applicationFilter">
              <Form.Label>Applicatoin filter</Form.Label>
              <Form.Select
                value={applicationFilter}
                onChange={handleApplicationFilterChange}
              >
                <option value="">-- All --</option>
                {applications.map(application => (
                  <option value={application} key={application}>{application}</option>
                ))}
              </Form.Select>
            </Form.Group>
            <Form.Group as={Col} controlId="serverFilter">
              <Form.Label>Server filter</Form.Label>
              <Form.Select
                value={serverFilter}
                onChange={handleServerFilterChange}
              >
                <option value="">-- All --</option>
                {servers.map(server => (
                  <option value={server.id} key={server.id}>{server.serverName}</option>
                ))}
              </Form.Select>
            </Form.Group>
            <Col className="d-flex justify-content-between align-items-end">
              <Button variant="secondary" onClick={() => setResetFilters(true)}>Reset</Button>
              <Button variant="primary" onClick={handleAddRule}>New</Button>
            </Col>
          </Row>
          </Form>
          <Table striped bordered hover>
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Type</th>
                <th>Application</th>
                <th>Description</th>
                <th>Status</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              {filteredRules.map(rule => (
                <tr key={rule.id}>
                  <td>{rule.id}</td>
                  <td>{rule.name}</td>
                  <td>{rule.type}</td>
                  <td>{rule.application}</td>
                  <td>{rule.description}</td>
                  <td>
                    {rule.enabled?<Badge bg="success">Enabled</Badge>:<Badge bg="secondary">Disabled</Badge>}
                  </td>
                  {/* Add other table cells */}
                  <td>
                    <Button variant="primary" onClick={() => handleEditRule(rule.id)}>Edit</Button>{' '}
                    <Button variant="danger" onClick={() => handleDeleteRule(rule.id)}>Delete</Button>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
          {
            pages > 1?<MyPagination currentPage={currentPage} totalPages={pages} onPageChange={handlePageChange}/>:<></>
          }
          <RuleModal
            showModal={showModal}
            handleCloseModal={() => setShowModal(false)}
            handleSaveRule={handleSaveRule}
            rule={selectedRule}
            handleRuleInputChange={handleRuleInputChange}
          />
          {/* 删除确认框 */}
          <Modal show={showDeleteConfirmation} onHide={() => setShowDeleteConfirmation(false)}>
            <Modal.Header closeButton>
              <Modal.Title>Confirm Delete</Modal.Title>
            </Modal.Header>
            <Modal.Body>
              <Alert variant="warning">
                Are you sure you want to delete this rule?
              </Alert>
            </Modal.Body>
            <Modal.Footer>
              <Button variant="secondary" onClick={() => setShowDeleteConfirmation(false)}>Cancel</Button>
              <Button variant="danger" onClick={handleConfirmDelete}>Delete</Button>
            </Modal.Footer>
          </Modal>
        </Card.Body>
      </Card>
    </Container>
  );
};

export default RulePage;