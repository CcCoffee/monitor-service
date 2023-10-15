import React, { useState, useEffect } from 'react'
import {
  Table,
  Form,
  Button,
  Row,
  Col,
  Container,
  Modal,
  Alert,
  Card,
} from 'react-bootstrap'
import MyPagination from '../components/MyPagination'
import './ServerPage.css'
import ServerModal from '../components/ServerModal'
import { notifyFailure, notifySuccess } from '../utils/ToastUtil'

const ServerPage = () => {
  const [currentPage, setCurrentPage] = useState(1)
  const [pageSize, setPageSize] = useState(10)
  const [pages, setPages] = useState()
  const [servers, setServers] = useState([])
  const [filteredServers, setFilteredServers] = useState([])
  const [serverNameFilter, setServerNameFilter] = useState('')
  const [hostnameFilter, setHostnameFilter] = useState('')
  const [showModal, setShowModal] = useState(false)
  const [selectedServer, setSelectedServer] = useState({})
  const [showDeleteConfirmation, setShowDeleteConfirmation] = useState(false)
  const [deleteServerId, setDeleteServerId] = useState()
  const [resetFilters, setResetFilters] = useState(false)
  const [newServer, setNewServer] = useState({
    serverName: '',
    hostname: '',
    description: '',
  })

  // Fetch servers and servers from the backend
  useEffect(() => {
    fetchServers(currentPage, pageSize)
  }, [])

  useEffect(() => {
    fetchServers(currentPage, pageSize)
    if (resetFilters) {
      setHostnameFilter('')
      setServerNameFilter('')
      setResetFilters(false)
    }
  }, [currentPage, pageSize, resetFilters, serverNameFilter, hostnameFilter])

  const fetchServers = (currentPage, pageSize) => {
    let url = `http://localhost:8090/servers?pageNum=${currentPage}&pageSize=${pageSize}`

    if (serverNameFilter !== '') {
      url += `&serverNameFilter=${encodeURIComponent(serverNameFilter)}`
    }

    if (hostnameFilter !== '') {
      url += `&hostnameFilter=${encodeURIComponent(hostnameFilter)}`
    }

    fetch(url)
      .then((response) => response.json())
      .then((data) => {
        if (data.code === 200) {
          setServers(data.data.records)
          setFilteredServers(data.data.records)
          setPages(data.data.pages)
        } else {
          console.error(data.message)
          // TODO: 处理请求失败的情况，如显示错误消息等
        }
      })
      .catch((error) => {
        console.error('An error occurred while fetching servers:', error)
        // TODO: 处理异常情况，如显示错误消息等
      })
  }

  const handleServerInputChange = (event) => {
    const { name, value } = event.target
    setSelectedServer((prevServer) => ({
      ...prevServer,
      [name]: value,
    }))
  }

  const handleShowDeleteConfirmation = (serverId) => {
    setShowDeleteConfirmation(true)
    setDeleteServerId(serverId)
  }

  const handleConfirmDelete = () => {
    const serverId = deleteServerId
    const url = `http://localhost:8090/servers/${serverId}`

    fetch(url, {
      method: 'DELETE',
    })
      .then((response) => {
        if (response.ok) {
          // Server deleted successfully
          console.log('Server deleted successfully')
          notifySuccess()
          // Perform any additional tasks if needed
          // Refresh the servers list
          fetchServers(currentPage, pageSize)
        } else {
          response.json().then((data) => {
            console.error(data.message)
            // TODO: 处理请求失败的情况，如显示错误消息等
            notifyFailure('Delete server Failed', data.message)
          })
        }
      })
      .catch((error) => {
        console.error('An error occurred while deleting server:', error)
        // TODO: 处理异常情况，如显示错误消息等
        notifyFailure()
      })

    // 关闭确认框并重置服务器ID
    setShowDeleteConfirmation(false)
    setDeleteServerId(null)
  }

  const handlePageChange = (page) => {
    setCurrentPage(page)
    setResetFilters(false)
  }

  const handlePageSizeChange = (event) => {
    setPageSize(event.target.value)
    setResetFilters(false)
  }

  const handleServerNameFilterChange = (event) => {
    setServerNameFilter(event.target.value)
    setResetFilters(false)
  }

  const handleHostnameFilterChange = (event) => {
    setHostnameFilter(event.target.value)
    setResetFilters(false)
  }

  const handleEditServer = (serverId) => {
    // Find the server with the given serverId from the servers state
    const server = servers.find((server) => server.id === serverId)
    // Perform deep cloning of the server object
    const clonedServer = JSON.parse(JSON.stringify(server))
    setSelectedServer(clonedServer)
    setShowModal(true)
  }

  const handleDeleteServer = (serverId) => {
    handleShowDeleteConfirmation(serverId)
  }

  const handleAddServer = () => {
    setSelectedServer(newServer)
    setShowModal(true)
  }

  const handleSaveServer = async () => {
    console.log('handleSaveServer')
    try {
      const response = await fetch('http://localhost:8090/servers', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(selectedServer),
      })

      if (response.ok) {
        const data = await response.json()
        if (data.code === 200) {
          console.log('Server saved successfully')
          notifySuccess()
          setShowModal(false)
          fetchServers(currentPage, pageSize)
        } else {
          console.error(data.message)
          // TODO: 处理保存失败的情况，如显示错误消息等
          notifyFailure('Save server Failed', data.message)
        }
      } else {
        console.error('Failed to save server')
        // TODO: 处理保存失败的情况，如显示错误消息等
        notifyFailure()
      }
    } catch (error) {
      console.error('An error occurred while saving server:', error)
      // TODO: 处理异常情况，如显示错误消息等
      notifyFailure()
    }
  }

  return (
    <Container className="h-100 w-100 py-2">
      <Card className="h-100 w-100">
        <Card.Body>
          <Card.Title>Server Configuration</Card.Title>
          <hr />
          <Form>
            <Row className="mb-3">
              <Form.Group as={Col} controlId="serverNameFilter">
                <Form.Label>Server name filter</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="Enter server name"
                  value={serverNameFilter}
                  onChange={handleServerNameFilterChange}
                />
              </Form.Group>
              <Form.Group as={Col} controlId="hostnameFilter">
                <Form.Label>Hostname filter</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="Enter hostname"
                  value={hostnameFilter}
                  onChange={handleHostnameFilterChange}
                />
              </Form.Group>
              <Col className="d-flex justify-content-between align-items-end">
                <Button
                  variant="secondary"
                  onClick={() => setResetFilters(true)}>
                  Reset
                </Button>
                <Button variant="primary" onClick={handleAddServer}>
                  New
                </Button>
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
              {filteredServers.map((server) => (
                <tr key={server.id}>
                  <td>{server.id}</td>
                  <td>{server.serverName}</td>
                  <td>{server.hostname}</td>
                  <td>{server.description}</td>
                  <td>
                    <Button
                      size="sm"
                      variant="primary"
                      onClick={() => handleEditServer(server.id)}>
                      Edit
                    </Button>{' '}
                    <Button
                      size="sm"
                      variant="danger"
                      onClick={() => handleDeleteServer(server.id)}>
                      Delete
                    </Button>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
          {pages > 1 ? (
            <MyPagination
              currentPage={currentPage}
              totalPages={pages}
              onPageChange={handlePageChange}
            />
          ) : (
            <></>
          )}
          <ServerModal
            showModal={showModal}
            handleCloseModal={() => setShowModal(false)}
            handleSaveServer={handleSaveServer}
            server={selectedServer}
            handleServerInputChange={handleServerInputChange}
          />
          {/* 删除确认框 */}
          <Modal
            show={showDeleteConfirmation}
            onHide={() => setShowDeleteConfirmation(false)}>
            <Modal.Header closeButton>
              <Modal.Title>Confirm Delete</Modal.Title>
            </Modal.Header>
            <Modal.Body>
              <Alert variant="warning">
                <p>
                  Deleting this server will not delete any associated monitoring
                  rules.
                </p>
                <hr />
                <p className="mb-0">Are you sure you want to proceed?</p>
              </Alert>
            </Modal.Body>
            <Modal.Footer>
              <Button
                variant="secondary"
                onClick={() => setShowDeleteConfirmation(false)}>
                Cancel
              </Button>
              <Button variant="danger" onClick={handleConfirmDelete}>
                Delete
              </Button>
            </Modal.Footer>
          </Modal>
        </Card.Body>
      </Card>
    </Container>
  )
}

export default ServerPage
