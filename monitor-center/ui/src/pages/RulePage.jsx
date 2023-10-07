import React, { useState, useEffect } from 'react';
import { Table, Pagination, Form, Button, Modal, Row, Col } from 'react-bootstrap';
import MyPagination from '../components/MyPagination';

const RulePage = () => {
  const [currentPage, setCurrentPage] = useState(1);
  const [pageSize, setPageSize] = useState(1);
  const [pages, setPages] = useState();
  const [rules, setRules] = useState([]);
  const [servers, setServers] = useState([]);
  const [filteredRules, setFilteredRules] = useState([]);
  const [nameFilter, setNameFilter] = useState('');
  const [typeFilter, setTypeFilter] = useState('');
  const [serverFilter, setServerFilter] = useState('');
  const [showModal, setShowModal] = useState(false);
  const [newRule, setNewRule] = useState({
    name: '',
    type: '',
    // Add other fields here
  });

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
    // Handle edit rule logic
  };

  const handleDeleteRule = (ruleId) => {
    // Handle delete rule logic
  };

  const handleNewRuleInputChange = (event) => {
    setNewRule({
      ...newRule,
      [event.target.name]: event.target.value
    });
  };

  const handleAddRule = () => {
    // Handle add rule logic
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
    <div>
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
            <Button variant="primary" onClick={handleOpenModal}>新增</Button>
          </Col>
        </Row>
      </Form>

      <Table striped bordered hover>
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Type</th>
            <th>Enabled</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          {filteredRules.map(rule => (
            <tr key={rule.id}>
              <td>{rule.id}</td>
              <td>{rule.name}</td>
              <td>{rule.type}</td>
              <td>
              <Form.Check
                type="checkbox"
                checked={rule.enabled}
                disabled
              />
              </td>
              {/* Add other table cells */}
              <td>
                <Button variant="primary" onClick={() => handleEditRule(rule.id)}>修改</Button>{' '}
                <Button variant="danger" onClick={() => handleDeleteRule(rule.id)}>删除</Button>
              </td>
            </tr>
          ))}
          <MyPagination currentPage={currentPage} totalPages={pages} onPageChange={handlePageChange}/>
        </tbody>
      </Table>



      <Modal show={showModal} onHide={handleCloseModal}>
        <Modal.Header closeButton>
          <Modal.Title>新增Rule</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            <Form.Group controlId="newRuleName">
              <Form.Label>Name</Form.Label>
              <Form.Control
                type="text"
                placeholder="输入名称"
                name="name"
                value={newRule.name}
                onChange={handleNewRuleInputChange}
              />
            </Form.Group>
            <Form.Group controlId="newRuleType">
              <Form.Label>Type</Form.Label>
              <Form.Control
                as="select"
                name="type"
                value={newRule.type}
                onChange={handleNewRuleInputChange}
              >
                <option value="">选择类型</option>
                <option value="PROCESS">Process</option>
                <option value="LOG">Log</option>
                {/* Add other types here */}
              </Form.Control>
            </Form.Group>
            {/* Add other form fields */}
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleCloseModal}>取消</Button>
          <Button variant="primary" onClick={handleAddRule}>保存</Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
};

export default RulePage;