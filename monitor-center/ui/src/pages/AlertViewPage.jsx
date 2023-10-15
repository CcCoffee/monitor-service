import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import NotificationModal from '../components/NotificationModal'
import MyPagination from '../components/MyPagination'
import {
  Table,
  Form,
  Button,
  Row,
  Col,
  Container,
  Card,
  Accordion,
} from 'react-bootstrap'
import './AlertViewPage.css'

const AlertViewPage = () => {
  const { id } = useParams()
  const [alert, setAlert] = useState(null)

  const [currentPage, setCurrentPage] = useState(1)
  const [pageSize, setPageSize] = useState(10)
  const [pages, setPages] = useState()
  const [notifications, setNotifications] = useState([])
  const [channelTypes, setChannelTypes] = useState([])
  const [filteredNotifications, setFilteredNotifications] = useState([])
  const [channelTypeFilter, setChannelTypeFilter] = useState('')
  const [showModal, setShowModal] = useState(false)
  const [selectedNotification, setSelectedNotification] = useState({})
  const [resetFilters, setResetFilters] = useState(false)

  useEffect(() => {
    fetch(`http://localhost:8090/alerts/${id}`)
      .then((response) => response.json())
      .then((data) => setAlert(data.data))
      .then(() => {
        fetchNotifications(currentPage, pageSize)
        fetchChannelTypes()
      })
      .catch((error) => console.log(error))
  }, [id])

  const fetchChannelTypes = () => {
    // Fetch channel types from the backend and set the 'channelTypes' state
    // Example:
    fetch(`http://localhost:8090/alerts/${id}/notifications-channel-types`)
      .then((response) => response.json())
      .then((data) => {
        if (data.code === 200) {
          setChannelTypes(data.data)
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
  const fetchNotifications = (currentPage, pageSize) => {
    let url = `http://localhost:8090/alerts/${id}/notifications?pageNum=${currentPage}&pageSize=${pageSize}`

    if (channelTypeFilter !== '') {
      url += `&channelTypeFilter=${encodeURIComponent(channelTypeFilter)}`
    }

    fetch(url)
      .then((response) => response.json())
      .then((data) => {
        if (data.code === 200) {
          setNotifications(data.data.records)
          setFilteredNotifications(data.data.records)
          setPages(data.data.pages)
        } else {
          console.error(data.message)
          // TODO: 处理请求失败的情况，如显示错误消息等
        }
      })
      .catch((error) => {
        console.error('An error occurred while fetching notifications:', error)
        // TODO: 处理异常情况，如显示错误消息等
      })
  }

  useEffect(() => {
    if (resetFilters) {
      setChannelTypeFilter('')
      setResetFilters(false)
    }
    fetchNotifications(currentPage, pageSize)
  }, [currentPage, pageSize, resetFilters, channelTypeFilter])

  const handlePageChange = (page) => {
    setCurrentPage(page)
    setResetFilters(false)
  }

  const handlePageSizeChange = (event) => {
    setPageSize(event.target.value)
    setResetFilters(false)
  }

  const handleChannelTypeFilterChange = (event) => {
    setChannelTypeFilter(event.target.value)
    setResetFilters(false)
  }

  const handleViewNotification = (notificationId) => {
    // Find the notification with the given notificationId from the notifications state
    const notification = notifications.find(
      (notification) => notification.id === notificationId
    )
    // Perform deep cloning of the notification object
    const clonedNotification = JSON.parse(JSON.stringify(notification))
    setSelectedNotification(clonedNotification)
    setShowModal(true)
  }
  if (!alert) {
    return <div>Loading...</div>
  }

  return (
    <Container className="h-100 w-100 py-2" style={{ overflow: 'auto' }}>
      <Card>
        <Card.Body>
          <Card.Title>{alert.name}</Card.Title>
          <hr />
          <Accordion defaultActiveKey={['0']} alwaysOpen>
            <Accordion.Item eventKey="0">
              <Accordion.Header>
                <strong>Basic information</strong>
              </Accordion.Header>
              <Accordion.Body className="p-3">
                <Row>
                  <Col>
                    <Card.Text>
                      <strong>ID:</strong> {alert.id}
                    </Card.Text>
                  </Col>
                  <Col>
                    <Card.Text>
                      <strong>Severity:</strong> {alert.severity}
                    </Card.Text>
                  </Col>
                </Row>
                <Row>
                  <Col>
                    <Card.Text>
                      <strong>Type:</strong> {alert.type}
                    </Card.Text>
                  </Col>
                  <Col>
                    <Card.Text>
                      <strong>Hostname:</strong> {alert.hostname}
                    </Card.Text>
                  </Col>
                </Row>
                <Row>
                  <Col>
                    <Card.Text>
                      <strong>Application:</strong> {alert.application}
                    </Card.Text>
                  </Col>
                  <Col>
                    <Card.Text>
                      <strong>Status:</strong> {alert.status}
                    </Card.Text>
                  </Col>
                </Row>
                <Row>
                  <Col>
                    <Card.Text>
                      <strong>Repeat Count:</strong> {alert.repeatCount}
                    </Card.Text>
                  </Col>
                  <Col>
                    <Card.Text>
                      <strong>Acknowledged By:</strong> {alert.acknowledgedBy}
                    </Card.Text>
                  </Col>
                </Row>
                <Row>
                  <Col>
                    <Card.Text>
                      <strong>Rule ID:</strong> {alert.ruleId}
                    </Card.Text>
                  </Col>
                  <Col>
                    <Card.Text>
                      <strong>Action:</strong> {alert.action}
                    </Card.Text>
                  </Col>
                </Row>
                <Row>
                  <Col>
                    <Card.Text>
                      <strong>Start Time:</strong> {alert.startTime}
                    </Card.Text>
                  </Col>
                  <Col>
                    <Card.Text>
                      <strong>End Time:</strong> {alert.endTime}
                    </Card.Text>
                  </Col>
                </Row>
              </Accordion.Body>
            </Accordion.Item>
            <Accordion.Item eventKey="1">
              <Accordion.Header>
                <strong>Error log</strong>
              </Accordion.Header>
              <Accordion.Body
                className="p-3"
                style={{ whiteSpace: 'pre-wrap' }}>
                {alert.content}
              </Accordion.Body>
            </Accordion.Item>
          </Accordion>

          <Row></Row>
        </Card.Body>
      </Card>
      <Card className="mt-2">
        <Card.Body>
          <Card.Title>Notification history</Card.Title>
          <hr />
          <Form>
            <Row className="mb-3">
              <Form.Group as={Col} controlId="channelTypeFilter">
                <Form.Label>Channel type filter</Form.Label>
                <Form.Select
                  value={channelTypeFilter}
                  onChange={handleChannelTypeFilterChange}>
                  <option value="">-- All --</option>
                  {channelTypes.map((channelType) => (
                    <option value={channelType} key={channelType}>
                      {channelType}
                    </option>
                  ))}
                </Form.Select>
              </Form.Group>
              <Col className="d-flex justify-content-between align-items-end">
                <Button
                  variant="secondary"
                  onClick={() => setResetFilters(true)}>
                  Reset
                </Button>
              </Col>
            </Row>
          </Form>

          <Table striped bordered hover>
            <thead>
              <tr>
                <th>ID</th>
                <th>Channel type</th>
                <th>Notification time</th>
                <th>response code</th>
                <th>Response message</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              {filteredNotifications.map((notification) => (
                <tr key={notification.id}>
                  <td>{notification.id}</td>
                  <td>{notification.channel.type}</td>
                  <td>{notification.notificationTime}</td>
                  <td>{notification.responseCode}</td>
                  <td>{notification.responseMessage}</td>
                  <td>
                    <Button
                      size="sm"
                      variant="primary"
                      onClick={() => handleViewNotification(notification.id)}>
                      View
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
          {showModal ? (
            <NotificationModal
              showModal={showModal}
              handleCloseModal={() => setShowModal(false)}
              notification={selectedNotification}
            />
          ) : (
            <></>
          )}
        </Card.Body>
      </Card>
    </Container>
  )
}

export default AlertViewPage
