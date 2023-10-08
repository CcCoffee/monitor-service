import React, { useState, useEffect } from 'react';
import { Table, Form, Button, Badge, Row, Col } from 'react-bootstrap';
import MyPagination from '../components/MyPagination';
import './RulePage.css'
import RuleModal from '../components/RuleModal';

const RulePage = () => {
  const [currentPage, setCurrentPage] = useState(1);
  const [pageSize, setPageSize] = useState(10);
  const [pages, setPages] = useState();
  const [rules, setRules] = useState([]);
  const [servers, setServers] = useState([]);
  const [filteredRules, setFilteredRules] = useState([]);
  const [nameFilter, setNameFilter] = useState('');
  const [typeFilter, setTypeFilter] = useState('');
  const [serverFilter, setServerFilter] = useState('');
  const [showModal, setShowModal] = useState(false);
  const [selectedRule, setSelectedRule] = useState({});
  const [newRule, setNewRule] = useState({
    name: "",
    type: "",
    description: "",
    enabled: true,
    threshold: null,
    interval: null,
    notificationRecipients: [],
    processNameRegex: "",
    logFilePath: "",
    logPatterns: [],
  });

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

  // Fetch rules and servers from the backend
  useEffect(() => {
    fetchRules(currentPage, pageSize);
    fetchServers();
  }, []);

  useEffect(() => {
    fetchRules(currentPage, pageSize);
  }, [currentPage, pageSize]);

  const fetchRules = (currentPage, pageSize) => {
    const url = `http://localhost:8090/rules?pageNum=${currentPage}&pageSize=${pageSize}`;
  
    fetch(url)
      .then(response => response.json())
      .then(data => {
        setRules(data.records);
        setFilteredRules(data.records);
        setPages(data.pages);
      })
      .catch(error => console.error(error));
  };

  const fetchServers = () => {
    // Fetch servers from the backend and set the 'servers' state
    // Example:
    fetch('http://localhost:8090/servers')
      .then(response => response.json())
      .then(data => {
        setServers(data);
      })
      .catch(error => console.error(error));
  };

  const handlePageChange = (page) => {
    setCurrentPage(page);
  };
  
  const handlePageSizeChange = (event) => {
    setPageSize(event.target.value);
  };

  const handleNameFilterChange = (event) => {
    setNameFilter(event.target.value);
  };

  const handleTypeFilterChange = (event) => {
    setTypeFilter(event.target.value);
  };

  const handleServerFilterChange = (event) => {
    setServerFilter(event.target.value);
  };

  const handleEditRule = (ruleId) => {
    // Find the rule with the given ruleId from the rules state
    const rule = rules.find((rule) => rule.id === ruleId);
    // Perform deep cloning of the rule object
    const clonedRule = JSON.parse(JSON.stringify(rule));
    setSelectedRule(clonedRule);
    setShowModal(true);
  };

  const handleDeleteRule = (ruleId) => {
    const url = `http://localhost:8090/rules/${ruleId}`;
  
    fetch(url, {
      method: 'DELETE',
    })
      .then(response => {
        if (response.ok) {
          // Rule deleted successfully
          console.log('Rule deleted successfully');
          // Perform any additional tasks if needed
          // Refresh the rules list
          fetchRules(currentPage, pageSize);
        } else {
          // Rule deletion failed
          console.error('Failed to delete rule');
          // Perform any error handling if needed
        }
      })
      .catch(error => {
        console.error('An error occurred while deleting rule:', error);
        // Perform any error handling if needed
      });
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
        // 保存成功
        console.log("Rule saved successfully");
        setShowModal(false);
        fetchRules(currentPage, pageSize);
      } else {
        // 保存失败
        console.error("Failed to save rule");
        // TODO: 可以执行其他操作，如显示错误消息等
      }
    } catch (error) {
      // 处理异常情况
      console.error("An error occurred while saving rule:", error);
      // TODO: 可以执行其他操作，如显示错误消息等
    }
  };

  const handleCloseModal = () => {
    setShowModal(false);
  };

  const handleOpenModal = () => {
    setShowModal(true);
  };

  // Apply filters to the rules list
  useEffect(() => {
    let filtered = rules;

    if (nameFilter !== '') {
      filtered = filtered.filter(rule => rule.name.includes(nameFilter));
    }

    if (typeFilter !== '') {
      filtered = filtered.filter(rule => rule.type === typeFilter);
    }

    if (serverFilter !== '') {
      filtered = filtered.filter(rule => {
        // Check if the rule is associated with the selected server
        const associatedServers = rule.servers.map(server => server.id);
        return associatedServers.includes(parseInt(serverFilter));
      });
    }

    setFilteredRules(filtered);
  }, [nameFilter, typeFilter, serverFilter, rules]);

  return (
    <div className='h-100 w-100 px-3'>
      <h1>Rule查询页面</h1>
      <Form>
        <Row className='mb-3'>
          <Form.Group as={Col} controlId="nameFilter">
            <Form.Label >Name过滤器</Form.Label>
              <Form.Control
                type="text"
                placeholder="输入名称"
                value={nameFilter}
                onChange={handleNameFilterChange}
              />
          </Form.Group>
          <Form.Group as={Col} controlId="typeFilter">
            <Form.Label>Type过滤器</Form.Label>
            <Form.Select
              value={typeFilter}
              onChange={handleTypeFilterChange}
            >
              <option value="">全部</option>
              <option value="PROCESS">Process</option>
              <option value="LOG">Log</option>
              {/* Add other types here */}
            </Form.Select>
          </Form.Group>
          <Form.Group as={Col} controlId="serverFilter">
            <Form.Label>Server过滤器</Form.Label>
            <Form.Select
              value={serverFilter}
              onChange={handleServerFilterChange}
            >
              <option value="">全部</option>
              {servers.map(server => (
                <option value={server.id} key={server.id}>{server.serverName}</option>
              ))}
            </Form.Select>
          </Form.Group>
          <Col className="d-flex justify-content-end align-items-end">
            <Button variant="primary" onClick={handleAddRule}>新增</Button>
          </Col>
        </Row>
      </Form>

      <Table striped bordered hover>
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Type</th>
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
              <td>{rule.description}</td>
              <td>
                {rule.enabled?<Badge bg="success">Enabled</Badge>:<Badge bg="secondary">Disabled</Badge>}
              </td>
              {/* Add other table cells */}
              <td>
                <Button variant="primary" onClick={() => handleEditRule(rule.id)}>修改</Button>{' '}
                <Button variant="danger" onClick={() => handleDeleteRule(rule.id)}>删除</Button>
              </td>
            </tr>
          ))}
        </tbody>
      </Table>
      <MyPagination currentPage={currentPage} totalPages={pages} onPageChange={handlePageChange}/>
      <RuleModal
        showModal={showModal}
        handleCloseModal={() => setShowModal(false)}
        handleSaveRule={handleSaveRule}
        rule={selectedRule}
        handleRuleInputChange={handleRuleInputChange}
      />
    </div>
  );
};

export default RulePage;