// NotificationModal.js
import React from 'react'
import { Modal, Card, Row, Col } from 'react-bootstrap'

const NotificationModal = ({ showModal, handleCloseModal, notification }) => {
  return (
    <Modal show={showModal} onHide={handleCloseModal} size="lg">
      <Modal.Header closeButton>
        <Modal.Title>Notification detail - {notification.id}</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Card>
          <Card.Header>
            <strong>Basic information</strong>
          </Card.Header>
          <Card.Body className="p-3">
            <Row>
              <Col>
                <Card.Text>
                  <strong>Alert ID:</strong> {notification.alertId}
                </Card.Text>
              </Col>
              <Col>
                <Card.Text>
                  <strong>Channel ID:</strong> {notification.channelId}
                </Card.Text>
              </Col>
            </Row>
            <Row>
              <Col>
                <Card.Text>
                  <strong>Response Code:</strong> {notification.responseCode}
                </Card.Text>
              </Col>
              <Col>
                <Card.Text>
                  <strong>Response Message:</strong>{' '}
                  {notification.responseMessage}
                </Card.Text>
              </Col>
            </Row>
            <Row>
              <Col>
                <Card.Text>
                  <strong>Notification Time:</strong>{' '}
                  {notification.notificationTime}
                </Card.Text>
              </Col>
            </Row>
          </Card.Body>
        </Card>

        <Card className="mt-3">
          <Card.Header>
            <strong>Notification content</strong>
          </Card.Header>
          <Card.Body>
            <Row>
              <Col>
                <Card.Text>
                  <strong>Subject:</strong> {notification.content.subject}
                </Card.Text>
              </Col>
            </Row>
            <Row>
              <Col>
                <strong>To:</strong>{' '}
                {notification.content.toEmailAddressList.join(',')}
              </Col>
            </Row>
            {notification.content.ccEmailAddressList ? (
              <Row>
                <Col>
                  <strong>Cc:</strong>{' '}
                  {notification.content.ccEmailAddressList?.join(',')}
                </Col>
              </Row>
            ) : (
              <></>
            )}

            <hr />
            <Row>
              <Col>
                <pre>{notification.content.content}</pre>
              </Col>
            </Row>
          </Card.Body>
        </Card>
      </Modal.Body>
    </Modal>
  )
}

export default NotificationModal
