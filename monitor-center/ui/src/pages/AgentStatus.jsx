import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Spinner, Card } from 'react-bootstrap';
import HealthCard from '../components/HealthCard';

function AgentStatus() {
  const [agents, setAgents] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const eventSource = new EventSource('http://localhost:8090/agent-health-check');

    eventSource.onmessage = (event) => {
      const data = JSON.parse(event.data);
      setAgents(data);
      setLoading(false);
    };

    return () => {
      eventSource.close();
    };
  }, []);

  return (
    <Container className='h-100 w-100 py-2'>
      <Card  className='h-100 w-100'>
        <Card.Body>
          <Card.Title>Agent health status</Card.Title>
          <hr/>
          <Container fluid>
        {loading ? (
          <div className='d-flex justify-content-center align-items-center h-100'>
            <Spinner animation='border' role='status'>
              <span className='visually-hidden'>Loading...</span>
            </Spinner>
          </div>
        ) : (
          <Row className='g-2'>
            {agents.map((agent) => (
              <Col key={agent.hostname} xs={12} sm={6} md={4} lg={3}>
                <HealthCard hostname={agent.hostname} status={agent.status} />
              </Col>
            ))}
          </Row>
        )}
      </Container>
        </Card.Body>
      </Card>
    </Container>
  );
}

export default AgentStatus;
