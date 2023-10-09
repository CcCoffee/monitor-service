import React from 'react';
import { Container, Row, Col } from 'react-bootstrap';

const Footer = () => {
  return (
    <footer className="py-3 bg-light">
      <Container>
        <Row className="justify-content-between align-items-center">
          <Col md={4} className="mb-0 text-secondary">
            &copy; 2023 Company, Inc
          </Col>
          <Col md={4}>
            <ul className="nav justify-content-end">
              <li className="nav-item">
                <a href="#" className="nav-link px-2 text-secondary">Home</a>
              </li>
              <li className="nav-item">
                <a href="#" className="nav-link px-2 text-secondary">Features</a>
              </li>
              <li className="nav-item">
                <a href="#" className="nav-link px-2 text-secondary">Pricing</a>
              </li>
              <li className="nav-item">
                <a href="#" className="nav-link px-2 text-secondary">FAQs</a>
              </li>
              <li className="nav-item">
                <a href="#" className="nav-link px-2 text-secondary">About</a>
              </li>
            </ul>
          </Col>
        </Row>
      </Container>
    </footer>
  );
};

export default Footer;