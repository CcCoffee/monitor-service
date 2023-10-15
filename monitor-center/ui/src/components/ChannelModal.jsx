// ChannelModal.js
import React, { useEffect, useState } from 'react'
import { Modal, Form, Button } from 'react-bootstrap'

const ChannelModal = ({
  showModal,
  handleCloseModal,
  handleSaveChannel,
  channel,
  handleChannelInputChange,
}) => {
  const [showEmailFields, setShowEmailFields] = useState(false)

  const handleTypeChange = (event) => {
    const { name, value } = event.target
    if (name === 'type' && value === 'EMAIL') {
      setShowEmailFields(true)
    } else {
      setShowEmailFields(false)
    }
    handleChannelInputChange(event)
  }

  useEffect(() => {
    setShowEmailFields(channel.type === 'EMAIL')
  }, [showModal])

  return (
    <Modal show={showModal} onHide={handleCloseModal} size="lg">
      <Modal.Header closeButton>
        <Modal.Title>
          {channel.id ? 'Edit Channel' : 'Insert Channel'}
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form>
          <Form.Group controlId="newChannelName" className="mb-1">
            <Form.Label>Channel name</Form.Label>
            <Form.Control
              type="text"
              placeholder="Input channel name"
              name="name"
              defaultValue={channel.name}
              onChange={handleChannelInputChange}
              required
            />
          </Form.Group>
          <Form.Group controlId="newChannelType" className="mb-1">
            <Form.Label>Type</Form.Label>
            <Form.Select
              name="type"
              defaultValue={channel.type}
              onChange={handleTypeChange}
              required>
              <option value="">Please select</option>
              <option value="EMAIL">Email</option>
            </Form.Select>
          </Form.Group>
          <Form.Group controlId="newChannelDescription" className="mb-1">
            <Form.Label>Description</Form.Label>
            <Form.Control
              type="text"
              placeholder="Enter description"
              name="description"
              defaultValue={channel.description}
              onChange={handleChannelInputChange}
              required
            />
          </Form.Group>
          <Form.Group controlId="newChannelEnabled" className="mb-1">
            <Form.Label>Enabled</Form.Label>
            <Form.Select
              label="Enabled"
              name="enabled"
              onChange={handleChannelInputChange}
              required
              defaultValue={channel.enabled}>
              <option value={true}>True</option>
              <option value={false}>False</option>
            </Form.Select>
          </Form.Group>
          {showEmailFields && (
            <>
              <Form.Group controlId="newChannelEmailAddresses" className="mb-1">
                <Form.Label>Email Addresses</Form.Label>
                <Form.Control
                  type="email"
                  multiple
                  placeholder="Enter email addresses separated by commas"
                  name="emailAddress"
                  defaultValue={channel.configuration.emailAddress}
                  onChange={handleChannelInputChange}
                  required
                />
              </Form.Group>
              <Form.Group controlId="newChannelEmailTemplate" className="mb-1">
                <Form.Label>Email Template</Form.Label>
                <Form.Control
                  as="textarea"
                  rows={12}
                  placeholder="Useful variables: 1) ${error}:error message; 2) ${username}: username."
                  name="emailTemplate"
                  defaultValue={channel.configuration.emailTemplate}
                  onChange={handleChannelInputChange}
                  required
                />
              </Form.Group>
            </>
          )}
        </Form>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={handleCloseModal}>
          Cancel
        </Button>
        <Button
          type="submit"
          variant="primary"
          onClick={() => handleSaveChannel(channel)}>
          Save
        </Button>
      </Modal.Footer>
    </Modal>
  )
}

export default ChannelModal
