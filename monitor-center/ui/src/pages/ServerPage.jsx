import React, { useState, useEffect } from 'react';
import { Table, Form, Button, Badge, Row, Col, Container } from 'react-bootstrap';
import MyPagination from '../components/MyPagination';
import './ServerPage.css'
import ServerModal from '../components/ServerModal';

const ServerPage = () => {
  const [currentPage, setCurrentPage] = useState(1);
  const [pageSize, setPageSize] = useState(10);
  const [pages, setPages] = useState();
  const [servers, setServers] = useState([]);
  const [filteredServers, setFilteredServers] = useState([]);
  const [serverNameFilter, setServerNameFilter] = useState('');
  const [hostnameFilter, setHostnameFilter] = useState('');
  const [showModal, setShowModal] = useState(false);
  const [selectedServer, setSelectedServer] = useState({});
  const [newServer, setNewServer] = useState({
    serverName: "",
    hostname: "",
    description: ""
  });

  const handleServerInputChange = (event) => {
    const { name, value } = event.target;
    setSelectedServer((prevServer) => ({
      ...prevServer,
      [name]: value,
    }));
  };

  // Fetch servers and servers from the backend
  useEffect(() => {
    fetchServers(currentPage, pageSize);
  }, []);

  useEffect(() => {
    fetchServers(currentPage, pageSize);
  }, [currentPage, pageSize, serverNameFilter, hostnameFilter]);

  const fetchServers = (currentPage, pageSize) => {
    let url = `http://localhost:8090/servers?pageNum=${currentPage}&pageSize=${pageSize}`;
  
    if (serverNameFilter !== '') {
      url += `&serverNameFilter=${encodeURIComponent(serverNameFilter)}`;
    }
  
    if (hostnameFilter !== '') {
      url += `&hostnameFilter=${encodeURIComponent(hostnameFilter)}`;
    }
  
    fetch(url)
      .then(response => response.json())
      .then(data => {
        if (data.code === 200) {
          setServers(data.data.records);
          setFilteredServers(data.data.records);
          setPages(data.data.pages);
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

  const handlePageChange = (page) => {
    setCurrentPage(page);
  };
  
  const handlePageSizeChange = (event) => {
    setPageSize(event.target.value);
  };

  const handleServerNameFilterChange = (event) => {
    setServerNameFilter(event.target.value);
  };

  const handleHostnameFilterChange = (event) => {
    setHostnameFilter(event.target.value);
  };

  const handleEditServer = (serverId) => {
    // Find the server with the given serverId from the servers state
    const server = servers.find((server) => server.id === serverId);
    // Perform deep cloning of the server object
    const clonedServer = JSON.parse(JSON.stringify(server));
    setSelectedServer(clonedServer);
    setShowModal(true);
  };

const handleDeleteServer = (serverId) => {
  const url = `http://localhost:8090/servers/${serverId}`;

  fetch(url, {
    method: 'DELETE',
  })
    .then(response => {
      if (response.ok) {
        // Server deleted successfully
        console.log('Server deleted successfully');
        // Perform any additional tasks if needed
        // Refresh the servers list
        fetchServers(currentPage, pageSize);
      } else {
        response.json().then(data => {
          console.error(data.message);
          // TODO: 处理请求失败的情况，如显示错误消息等
        });
      }
    })
    .catch(error => {
      console.error('An error occurred while deleting server:', error);
      // TODO: 处理异常情况，如显示错误消息等
    });
};

  const handleAddServer = () => {
    setSelectedServer(newServer);
    setShowModal(true);
  };

  const handleSaveServer = async () => {
    console.log("handleSaveServer");
    try {
      const response = await fetch("http://localhost:8090/servers", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(selectedServer),
      });
  
      if (response.ok) {
        const data = await response.json();
        if (data.code === 200) {
          console.log("Server saved successfully");
          setShowModal(false);
          fetchServers(currentPage, pageSize);
        } else {
          console.error(data.message);
          // TODO: 处理保存失败的情况，如显示错误消息等
        }
      } else {
        console.error("Failed to save server");
        // TODO: 处理保存失败的情况，如显示错误消息等
      }
    } catch (error) {
      console.error("An error occurred while saving server:", error);
      // TODO: 处理异常情况，如显示错误消息等
    }
  };

  const handleCloseModal = () => {
    setShowModal(false);
  };

  const handleOpenModal = () => {
    setShowModal(true);
  };

  return (
    <>
      <Container className='h-100 w-100'>
      <h1>Server Query</h1>
      <Form>
        <Row className='mb-3'>
          <Form.Group as={Col} controlId="serverNameFilter">
            <Form.Label >Server name filter</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter server name"
                value={serverNameFilter}
                onChange={handleServerNameFilterChange}
              />
          </Form.Group>
          <Form.Group as={Col} controlId="hostnameFilter">
            <Form.Label >Hostname filter</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter hostname"
                value={hostnameFilter}
                onChange={handleHostnameFilterChange}
              />
          </Form.Group>
          <Col className="d-flex justify-content-end align-items-end">
            <Button variant="primary" onClick={handleAddServer}>New</Button>
          </Col>
        </Row>
      </Form>

      <Table striped bordered hover>
        <thead>
          <tr>
            <th>ID</th>
            <th>Server name</th>
            <th>Hostname</th>
            <th>Description</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {filteredServers.map(server => (
            <tr key={server.id}>
              <td>{server.id}</td>
              <td>{server.serverName}</td>
              <td>{server.hostname}</td>
              <td>{server.description}</td>
              <td>
                <Button variant="primary" onClick={() => handleEditServer(server.id)}>Edit</Button>{' '}
                <Button variant="danger" onClick={() => handleDeleteServer(server.id)}>Delete</Button>
              </td>
            </tr>
          ))}
        </tbody>
      </Table>
      {
        pages > 1?<MyPagination currentPage={currentPage} totalPages={pages} onPageChange={handlePageChange}/>:<></>
      }
      <ServerModal
        showModal={showModal}
        handleCloseModal={() => setShowModal(false)}
        handleSaveServer={handleSaveServer}
        server={selectedServer}
        handleServerInputChange={handleServerInputChange}
      />

    </Container>
    </>

  );
};

export default ServerPage;