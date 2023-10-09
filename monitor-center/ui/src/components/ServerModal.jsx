// ServerModal.js
import React from 'react';
import { Modal, Form, Button } from 'react-bootstrap';

const ServerModal = ({
  showModal,
  handleCloseModal,
  handleSaveServer,
  server,
  handleServerInputChange,
}) => {
      
  return (
    <Modal show={showModal} onHide={handleCloseModal}>
      <Modal.Header closeButton>
        <Modal.Title>{server.id ? 'Edit Server' : 'Insert Server'}</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form>
            <Form.Group controlId="newServerName" className='mb-1'>
              <Form.Label>Server name</Form.Label>
              <Form.Control
                type="text"
                placeholder="Input server name"
                name="serverName"
                defaultValue={server.serverName}
                onChange={handleServerInputChange}
                required
              />
            </Form.Group>
            <Form.Group controlId="newHostname" className='mb-1'>
              <Form.Label>Hostname</Form.Label>
              <Form.Control
                type="text"
                placeholder="Input hostname"
                name="hostname"
                defaultValue={server.hostname}
                onChange={handleServerInputChange}
                required
              />
            </Form.Group>
            <Form.Group controlId="newServerDescription" className='mb-1'>
              <Form.Label>Description</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter description"
                name="description"
                defaultValue={server.description}
                onChange={handleServerInputChange}
                required
              />
            </Form.Group>
          </Form>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={handleCloseModal}>
          Cancel
        </Button>
        <Button type="submit" variant="primary" onClick={()=>handleSaveServer(server)}>
          Save
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default ServerModal;
