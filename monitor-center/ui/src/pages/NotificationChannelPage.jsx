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
import './NotificationChannelPage.css'
import ChannelModal from '../components/ChannelModal'
import { notifyFailure, notifySuccess } from '../utils/ToastUtil'

const NotificationChannelPage = () => {
  const [currentPage, setCurrentPage] = useState(1)
  const [pageSize, setPageSize] = useState(10)
  const [pages, setPages] = useState()
  const [channels, setChannels] = useState([])
  const [filteredChannels, setFilteredChannels] = useState([])
  const [nameFilter, setNameFilter] = useState('')
  const [typeFilter, setTypeFilter] = useState('')
  const [showModal, setShowModal] = useState(false)
  const [selectedChannel, setSelectedChannel] = useState({})
  const [showDeleteConfirmation, setShowDeleteConfirmation] = useState(false)
  const [deleteChannelId, setDeleteChannelId] = useState()
  const [resetFilters, setResetFilters] = useState(false)
  const [newChannel, setNewChannel] = useState({
    name: '',
    type: '',
    description: '',
    enabled: true,
    configuration: {},
  })

  // Fetch channels and channels from the backend
  useEffect(() => {
    fetchChannels(currentPage, pageSize)
  }, [])

  useEffect(() => {
    fetchChannels(currentPage, pageSize)
    if (resetFilters) {
      setNameFilter('')
      setTypeFilter('')
      setResetFilters(false)
    }
  }, [currentPage, pageSize, resetFilters, nameFilter, typeFilter])

  const fetchChannels = (currentPage, pageSize) => {
    let url = `http://localhost:8090/channels?pageNum=${currentPage}&pageSize=${pageSize}`

    if (nameFilter !== '') {
      url += `&nameFilter=${encodeURIComponent(nameFilter)}`
    }

    if (typeFilter !== '') {
      url += `&typeFilter=${encodeURIComponent(typeFilter)}`
    }

    fetch(url)
      .then((response) => response.json())
      .then((data) => {
        if (data.code === 200) {
          setChannels(data.data.records)
          setFilteredChannels(data.data.records)
          setPages(data.data.pages)
        } else {
          console.error(data.message)
          // TODO: 处理请求失败的情况，如显示错误消息等
        }
      })
      .catch((error) => {
        console.error('An error occurred while fetching channels:', error)
        // TODO: 处理异常情况，如显示错误消息等
      })
  }

  const handleChannelInputChange = (event) => {
    const { name, value } = event.target
    setSelectedChannel((prevChannel) => ({
      ...prevChannel,
      [name]: value,
    }))
  }

  const handleShowDeleteConfirmation = (channelId) => {
    setShowDeleteConfirmation(true)
    setDeleteChannelId(channelId)
  }

  const handleConfirmDelete = () => {
    const channelId = deleteChannelId
    const url = `http://localhost:8090/channels/${channelId}`

    fetch(url, {
      method: 'DELETE',
    })
      .then((response) => {
        if (response.ok) {
          // Channel deleted successfully
          console.log('Channel deleted successfully')
          notifySuccess()
          // Perform any additional tasks if needed
          // Refresh the channels list
          fetchChannels(currentPage, pageSize)
        } else {
          response.json().then((data) => {
            console.error(data.message)
            // TODO: 处理请求失败的情况，如显示错误消息等
            notifyFailure('Delete Channel Failed', data.message)
          })
        }
      })
      .catch((error) => {
        console.error('An error occurred while deleting channel:', error)
        // TODO: 处理异常情况，如显示错误消息等
        notifyFailure()
      })

    // 关闭确认框并重置服务器ID
    setShowDeleteConfirmation(false)
    setDeleteChannelId(null)
  }

  const handlePageChange = (page) => {
    setCurrentPage(page)
    setResetFilters(false)
  }

  const handlePageSizeChange = (event) => {
    setPageSize(event.target.value)
    setResetFilters(false)
  }

  const handleNameFilterChange = (event) => {
    setNameFilter(event.target.value)
    setResetFilters(false)
  }

  const handleTypeFilterChange = (event) => {
    setTypeFilter(event.target.value)
    setResetFilters(false)
  }

  const handleEditChannel = (channelId) => {
    // Find the channel with the given channelId from the channels state
    const channel = channels.find((channel) => channel.id === channelId)
    // Perform deep cloning of the channel object
    const clonedChannel = JSON.parse(JSON.stringify(channel))
    setSelectedChannel(clonedChannel)
    setShowModal(true)
  }

  const handleDeleteChannel = (channelId) => {
    handleShowDeleteConfirmation(channelId)
  }

  const handleAddChannel = () => {
    setSelectedChannel(newChannel)
    setShowModal(true)
  }

  const handleSaveChannel = async () => {
    selectedChannel.configuration = {
      emailAddress:
        typeof selectedChannel.newEmailAddresses !== 'undefined'
          ? selectedChannel.newEmailAddresses.trim().split(',')
          : selectedChannel.configuration.emailAddress,
      emailTemplate:
        typeof selectedChannel.emailTemplate !== 'undefined'
          ? selectedChannel.emailTemplate
          : selectedChannel.configuration.emailTemplate,
    }
    try {
      const response = await fetch('http://localhost:8090/channels', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(selectedChannel),
      })

      if (response.ok) {
        const data = await response.json()
        if (data.code === 200) {
          console.log('Channel saved successfully')
          notifySuccess()
          setShowModal(false)
          fetchChannels(currentPage, pageSize)
        } else {
          console.error(data.message)
          // TODO: 处理保存失败的情况，如显示错误消息等
          notifySuccess('Save channel failed', data.message)
        }
      } else {
        console.error('Failed to save channel')
        // TODO: 处理保存失败的情况，如显示错误消息等
        notifyFailure()
      }
    } catch (error) {
      console.error('An error occurred while saving channel:', error)
      // TODO: 处理异常情况，如显示错误消息等
      notifyFailure()
    }
  }

  return (
    <Container className="h-100 w-100 py-2">
      <Card className="h-100 w-100">
        <Card.Body>
          <Card.Title>Channel Configuration</Card.Title>
          <hr />
          <Form>
            <Row className="mb-3">
              <Form.Group as={Col} controlId="nameFilter">
                <Form.Label>Name filter</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="Enter channel name"
                  value={nameFilter}
                  onChange={handleNameFilterChange}
                />
              </Form.Group>
              <Form.Group as={Col} controlId="typeFilter">
                <Form.Label>Type filter</Form.Label>
                <Form.Select
                  value={typeFilter}
                  onChange={handleTypeFilterChange}>
                  <option value="">-- All --</option>
                  <option value="EMAIL">Email</option>
                </Form.Select>
              </Form.Group>
              <Col className="d-flex justify-content-between align-items-end">
                <Button
                  variant="secondary"
                  onClick={() => setResetFilters(true)}>
                  Reset
                </Button>
                <Button variant="primary" onClick={handleAddChannel}>
                  New
                </Button>
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
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              {filteredChannels.map((channel) => (
                <tr key={channel.id}>
                  <td>{channel.id}</td>
                  <td>{channel.name}</td>
                  <td>{channel.type}</td>
                  <td>{channel.description}</td>
                  <td>
                    <Button
                      size="sm"
                      variant="primary"
                      onClick={() => handleEditChannel(channel.id)}>
                      Edit
                    </Button>{' '}
                    <Button
                      size="sm"
                      variant="danger"
                      onClick={() => handleDeleteChannel(channel.id)}>
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
          <ChannelModal
            showModal={showModal}
            handleCloseModal={() => setShowModal(false)}
            handleSaveChannel={handleSaveChannel}
            channel={selectedChannel}
            handleChannelInputChange={handleChannelInputChange}
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
                  Please make sure that the channel to be deleted is not being
                  used by any rule.
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

export default NotificationChannelPage
