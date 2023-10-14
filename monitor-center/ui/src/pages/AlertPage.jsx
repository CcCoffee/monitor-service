import React, { useState, useEffect } from 'react';
import { Table, Form, Button, Badge, Row, Col, Container, Modal, Alert, Card } from 'react-bootstrap';
import MyPagination from '../components/MyPagination';
import './AlertPage.css'
// import AlertModal from '../components/AlertModal';
import {notifyFailure, notifySuccess} from '../utils/ToastUtil'
import { useNavigate } from 'react-router-dom';

const AlertPage = () => {
  const [currentPage, setCurrentPage] = useState(1);
  const [pageSize, setPageSize] = useState(10);
  const [pages, setPages] = useState();
  const [alerts, setAlerts] = useState([]);
  const [applications, setApplications] = useState([]);
  const [filteredAlerts, setFilteredAlerts] = useState([]);
  const [nameFilter, setNameFilter] = useState('');
  const [typeFilter, setTypeFilter] = useState('');
  const [applicationFilter, setApplicationFilter] = useState('');
  const [statusFilter, setStatusFilter] = useState('');
  const [severityFilter, setSeverityFilter] = useState('');
  const [showModal, setShowModal] = useState(false);
  const [selectedAlert, setSelectedAlert] = useState({});
  const [showDeleteConfirmation, setShowDeleteConfirmation] = useState(false);
  const [deleteAlertId, setDeleteAlertId] = useState();
  const [resetFilters, setResetFilters] = useState(false);
  const [newAlert, setNewAlert] = useState({
    alertName: "",
    hostname: "",
    description: ""
  });

  const navigate = useNavigate();

  // Fetch alerts and alerts from the backend
  useEffect(() => {
    fetchAlerts(currentPage, pageSize);
    fetchApplications();
  }, []);

  useEffect(() => {
    fetchAlerts(currentPage, pageSize);
    if (resetFilters) {
      setNameFilter('');
      setSeverityFilter('');
      setApplicationFilter('');
      setStatusFilter('');
      setResetFilters(false);
    }
  }, [currentPage, pageSize, resetFilters, nameFilter,severityFilter, typeFilter, applicationFilter, statusFilter]);

  const fetchAlerts = (currentPage, pageSize) => {
    let url = `http://localhost:8090/alerts?pageNum=${currentPage}&pageSize=${pageSize}`;
  
    if (nameFilter !== '') {
      url += `&nameFilter=${encodeURIComponent(nameFilter)}`;
    }
    
    if (typeFilter !== '') {
      url += `&typeFilter=${encodeURIComponent(typeFilter)}`;
    }

    if (applicationFilter !== '') {
      url += `&applicationFilter=${encodeURIComponent(applicationFilter)}`;
    }

    if (severityFilter !== '') {
      url += `&severityFilter=${encodeURIComponent(severityFilter)}`;
    }

    if (statusFilter !== '') {
      url += `&statusFilter=${encodeURIComponent(statusFilter)}`;
    }
  
    fetch(url)
      .then(response => response.json())
      .then(data => {
        if (data.code === 200) {
          setAlerts(data.data.records);
          setFilteredAlerts(data.data.records);
          setPages(data.data.pages);
        } else {
          console.error(data.message);
          // TODO: 处理请求失败的情况，如显示错误消息等
        }
      })
      .catch(error => {
        console.error('An error occurred while fetching alerts:', error);
        // TODO: 处理异常情况，如显示错误消息等
      });
  };

  const fetchApplications = () => {
    // Fetch servers from the backend and set the 'servers' state
    // Example:
    fetch('http://localhost:8090/alerts/applications')
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

  const handleConfirmDelete = () => {
    const alertId = deleteAlertId;
    const url = `http://localhost:8090/alerts/${alertId}`;
  
    fetch(url, {
      method: 'DELETE',
    })
      .then(response => {
        if (response.ok) {
          // Alert deleted successfully
          console.log('Alert deleted successfully');
          notifySuccess();
          // Perform any additional tasks if needed
          // Refresh the alerts list
          fetchAlerts(currentPage, pageSize);
        } else {
          response.json().then(data => {
            console.error(data.message);
            // TODO: 处理请求失败的情况，如显示错误消息等
            notifyFailure("Delete alert Failed", data.message);
          });
        }
      })
      .catch(error => {
        console.error('An error occurred while deleting alert:', error);
        // TODO: 处理异常情况，如显示错误消息等
        notifyFailure();
      });
  
    // 关闭确认框并重置服务器ID
    setShowDeleteConfirmation(false);
    setDeleteAlertId(null);
  };

  const getSeverityVariant = (severity) => {
    switch (severity) {
      case 'Low':
        return 'info';
      case 'Medium':
        return 'warning';
      case 'High':
        return 'danger';
      case 'Critical':
        return 'danger';
      default:
        return 'primary';
    }
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

  const handleAlertSeverityFilterChange = (event) => {
    setSeverityFilter(event.target.value);
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

  const handleAlertStatusFilterChange = (event) => {
    setStatusFilter(event.target.value);
    setResetFilters(false);
  };

  const navigateToAlertViewPage = (alert) => {
    navigate(`/alert/${alert.id}`);
  }

  return (
      <Container className='h-100 w-100 py-2'>
        <Card className='h-100 w-100'>
          <Card.Body>
            <Card.Title>Alert Configuration</Card.Title>
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
                <Form.Group as={Col} controlId="severityFilter">
                  <Form.Label>Severity filter</Form.Label>
                  <Form.Select
                    value={severityFilter}
                    onChange={handleAlertSeverityFilterChange}
                  >
                    <option value="">-- All --</option>
                    <option value="Critical">Critical</option>
                    <option value="High">High</option>
                    <option value="Medium">Medium</option>
                    <option value="Low">Low</option>
                  </Form.Select>
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
                <Form.Group as={Col} controlId="statusFilter">
                  <Form.Label>Status filter</Form.Label>
                  <Form.Select
                    value={statusFilter}
                    onChange={handleAlertStatusFilterChange}
                  >
                    <option value="">-- All --</option>
                    <option value="Open">Open</option>
                    <option value="Acknowledged">Acknowledged</option>
                    <option value="Closed">Closed</option>
                    {/* Add other types here */}
                  </Form.Select>
                </Form.Group>
                <Col className="d-flex justify-content-between align-items-end">
                  <Button variant="secondary" onClick={() => setResetFilters(true)}>Reset</Button>
                </Col>
              </Row>

            </Form>

            <Table striped bordered hover>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Alert name</th>
                  <th>Severity</th>
                  <th>Type</th>
                  <th>Hostname</th>
                  <th>Application</th>
                  <th>Status</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
                {filteredAlerts.map(alert => (
                  <tr key={alert.id}>
                    <td>{alert.id}</td>
                    <td>{alert.name}</td>
                    <td>
                      <Badge bg={getSeverityVariant(alert.severity)}>{alert.severity}</Badge>
                    </td>
                    <td>{alert.type}</td>
                    <td>{alert.hostname}</td>
                    <td>{alert.application}</td>
                    <td>{alert.status}</td>
                    <td>
                      <Button variant="primary" onClick={() => navigateToAlertViewPage(alert)}>View</Button>{' '}
                      {alert.status === 'Open'?<Button variant="success" onClick={() =>{}}>Ackownleage</Button>:<></>}
                      {alert.status === 'Acknowledged'?<Button variant="danger" onClick={() => {}}>Close</Button>:<></>}
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
            {
              pages > 1?<MyPagination currentPage={currentPage} totalPages={pages} onPageChange={handlePageChange}/>:<></>
            }
            {/* <AlertModal
              showModal={showModal}
              handleCloseModal={() => setShowModal(false)}
              handleSaveAlert={handleSaveAlert}
              alert={selectedAlert}
              handleAlertInputChange={handleAlertInputChange}
            /> */}
          {/* 删除确认框 */}
          <Modal show={showDeleteConfirmation} onHide={() => setShowDeleteConfirmation(false)}>
            <Modal.Header closeButton>
              <Modal.Title>Confirm Delete</Modal.Title>
            </Modal.Header>
            <Modal.Body>
              <Alert variant="warning">
                <p>
                  Deleting this alert will not delete any associated monitoring rules.
                </p>
                <hr/>
                <p className="mb-0">
                  Are you sure you want to proceed?
                </p>
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

export default AlertPage;