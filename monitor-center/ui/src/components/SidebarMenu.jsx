import React from 'react';
import { Accordion, Card } from 'react-bootstrap';
import { Link } from 'react-router-dom';


function SidebarMenu() {
  return (
    <Accordion defaultActiveKey="0" data-bs-theme="blue" flush>
      {/* 提供有关监控告警系统的概览信息，例如警报数量、警报级别等。 */}
      <Accordion.Item eventKey="0">
        <Accordion.Header><span className='fw-bold'>Dashboard</span></Accordion.Header>
        <Accordion.Body>
          <Card.Body className="dashboard-menu">
            {/* 系统状态是指对整个系统的各个组件、服务和资源进行状态检查和评估。系统状态提供了关于系统整体健康状况的信息，如系统的可用性、运行状态、资源利用率等。它可以显示系统当前的状态（如正常、警告、故障等），并提供相关的详细信息，以帮助用户评估系统的健康程度和性能情况。 */}
            <Link to="/agent-status">System Status</Link>
            {/* 警报概览提供了关于系统中已触发的警报的总体概览信息。它通常显示警报的数量、级别、类型和相关的摘要信息。警报概览可以帮助用户快速了解系统中的警报情况，包括已触发的警报、待处理的警报和已解决的警报。用户可以通过警报概览界面查看和处理警报，以确保对系统中的问题做出及时响应 */}
            <Link to="/agent-status">Alerts Overview</Link>
          </Card.Body>
        </Accordion.Body>
      </Accordion.Item>
      {/* 显示当前活动的警报列表，包括警报级别、时间戳和其他详细信息。 */}
      <Accordion.Item eventKey="1">
        <Accordion.Header><span className='fw-bold'>Alerts</span></Accordion.Header>
        <Accordion.Body>
          <Card.Body className="dashboard-menu">
            <Link to="/log-rule">Active Alerts</Link>
            <Link to="/log-rule">Historical Alerts</Link>
          </Card.Body>
        </Accordion.Body>
      </Accordion.Item>
      {/* 提供有关警报通知的详细信息，例如通知类型、通知方式等。 */}
      <Accordion.Item eventKey="2">
        <Accordion.Header><span className='fw-bold'>Configuration</span></Accordion.Header>
        <Accordion.Body>
          <Card.Body className="dashboard-menu">
            <Link to="/log-rule">Rule Configuration</Link>
            <Link to="/log-rule">Notification Settings</Link>
            <Link to="/log-rule">Threshold Configuration</Link>
          </Card.Body>
        </Accordion.Body>
      </Accordion.Item>
    </Accordion>
  );
}

export default SidebarMenu;