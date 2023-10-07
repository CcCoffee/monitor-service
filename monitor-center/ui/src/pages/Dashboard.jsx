import React from 'react';
import { Outlet, Link, useNavigate } from 'react-router-dom';
import { Row, Col, Container, Navbar, Nav, NavDropdown } from 'react-bootstrap';
import './Dashboard.css';
import SidebarMenu from '../components/SidebarMenu';

function Dashboard() {

  const navigate = useNavigate();

  const handleLogout = ()=>{
    localStorage.setItem('isLoggedIn', 'false');
    navigate('/login', {replace: true});
  }

  return (
    <>
      <Navbar variant="dark" bg="dark" expand="lg">
        <Container fluid>
          <Navbar.Brand href="/">Monitor Center</Navbar.Brand>
          <Navbar.Toggle aria-controls="navbar-dark-example" />
          <Navbar.Collapse id="navbar-dark-example" className="justify-content-end">
            <Nav>
              <NavDropdown
                id="nav-dropdown-dark-example"
                title="Signed in as: Mark Otto"
                menuVariant="dark"
              >
                <NavDropdown.Item href="/profile">User profile</NavDropdown.Item>
                <NavDropdown.Divider />
                <NavDropdown.Item onClick={handleLogout}>
                  Sign out
                </NavDropdown.Item>
              </NavDropdown>
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>
      <Container fluid className='dashboard-container p-0'>
        <Row className='dashboard-row gx-0'>
          <Col xs={0} sm={0} md={3} lg={3} xl={3} xxl={3}>
            <SidebarMenu/>
          </Col>
          <Col xs={12} sm={12} md={9} lg={9} xl={9} xxl={9} className='pt-2 bg-light'>
            <Outlet/>
          </Col>
        </Row >
    </Container>
    </>
  );
}

export default Dashboard;