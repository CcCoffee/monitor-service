import { Nav, Navbar, NavDropdown } from 'react-bootstrap';
import { ClockHistory, Speedometer2, Activity, Mailbox, Gear, SearchHeartFill } from 'react-bootstrap-icons';
import { useNavigate, useLocation } from 'react-router-dom';

const Sidebar = () => {
  const navigate = useNavigate();
  const location = useLocation();

  const handleLogout = () => {
    localStorage.setItem('isLoggedIn', 'false');
    navigate('/login', { replace: true });
  }

  const isNavLinkActive = (path) => {
    return location.pathname === path ? 'active' : '';
  }

  return (
    <div className="d-flex flex-column flex-shrink-0 p-3 text-bg-dark h-100" style={{ width: '280px' }}>
      <Navbar.Brand href="/" className="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-white text-decoration-none">
        <SearchHeartFill className="bi pe-none me-2" size={30} />
        <span className="fs-4">Monitor Center</span>
      </Navbar.Brand>
      <hr />
      <Nav className="nav-pills flex-column mb-auto">
        {/* 提供有关监控告警系统的概览信息，例如警报数量、警报级别等。 */}
        <Nav.Item>
          <Nav.Link href="/agent-status" className={`nav-link text-white d-flex align-items-center ${isNavLinkActive('/agent-status')}`} tabIndex={0}>
            <Speedometer2 className="bi pe-none me-2" size={16} />
            Dashboard
          </Nav.Link>
        </Nav.Item>
        {/* 显示当前活动的警报列表，包括警报级别、时间戳和其他详细信息。 */}
        <Nav.Item>
          <Nav.Link href="/rule1" className={`nav-link text-white d-flex align-items-center ${isNavLinkActive('/rule1')}`} tabIndex={1}>
            <Activity className="bi pe-none me-2" size={16} />
            Active Alerts
          </Nav.Link>
        </Nav.Item>
        {/* 显示历史的警报列表，包括警报级别、时间戳和其他详细信息。 */}
        <Nav.Item>
          <Nav.Link href="/rule2" className={`nav-link text-white d-flex align-items-center ${isNavLinkActive('/rule2')}`} tabIndex={2}>
            <ClockHistory className="bi pe-none me-2" size={16} />
            Historical Alerts
          </Nav.Link>
        </Nav.Item>
        <Nav.Item>
          <Nav.Link href="/rule" className={`nav-link text-white d-flex align-items-center ${isNavLinkActive('/rule')}`} tabIndex={3}>
            <Gear className="bi pe-none me-2" size={16} />
            Rule Configuration
          </Nav.Link>
        </Nav.Item>
        {/* 提供有关警报通知的详细信息，例如通知类型、通知方式等。 */}
        <Nav.Item>
          <Nav.Link href="/rule4" className={`nav-link text-white d-flex align-items-center ${isNavLinkActive('/rule4')}`} tabIndex={4}>
            <Mailbox className="bi pe-none me-2" size={16} />
            Notification Settings
          </Nav.Link>
        </Nav.Item>
      </Nav>
      <hr />
      <NavDropdown title={<><img src="https://github.com/mdo.png" alt="" width="32" height="32" className="rounded-circle me-2" /><strong>Kevin Zheng</strong></>}>
        <NavDropdown.Item href="#">Settings</NavDropdown.Item>
        <NavDropdown.Item href="#">Profile</NavDropdown.Item>
        <NavDropdown.Divider />
        <NavDropdown.Item href="#" onClick={handleLogout}>Sign out</NavDropdown.Item>
      </NavDropdown>
    </div>
  );
};

export default Sidebar;