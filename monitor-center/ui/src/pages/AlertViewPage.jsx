import { useParams,useNavigate } from 'react-router-dom';
import { Table, Form, Button, Badge, Row, Col, Container, Modal, Alert, Card, NavLink } from 'react-bootstrap';

const AlertViewPage = () => {
  const params = useParams();
  const navigate = useNavigate();
  
  const { id } = params;
  const goBack = () => {
    navigate(-1); // Go back to the previous page
  };
  // Use the `id` parameter in your component logic

  return (
    // <div>
    //   <h1>Alert View Page</h1>
    //   <p>Alert ID: {id}</p>
    //   {/* Rest of your component code */}
    //   <div>
    //     <button onClick={goBack}>Go Back</button>
    //     {/* Other component content */}
    //     </div>
    // </div>

    <Container className='h-100 w-100 py-2'>
    <Card className='h-100 w-100'>
      <Card.Body>
        <Card.Title>Alert detail</Card.Title>
        <hr/>
      </Card.Body>
      <Card.Footer>
        <Button variant="primary" onClick={goBack}>Back</Button>
      </Card.Footer>
    </Card>
    </Container>
  );
};

export default AlertViewPage;