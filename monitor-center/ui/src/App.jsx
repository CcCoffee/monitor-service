import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import LoginPage from './pages/Login';
import Dashboard from './pages/Dashboard';
import AgentStatus from './pages/AgentStatus';
import ProcessMonitorRule from './pages/ProcessMonitorRule'

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route
          path="/"
          element={
            <PrivateRoute>
              <Dashboard />
            </PrivateRoute>
          }
        >
          <Route index path="/" element={<AgentStatus />}/>
          <Route index path="/agent-status" element={<AgentStatus />}/>
          <Route path="/process-rule" element={<ProcessMonitorRule />} />
          <Route path="/log-rule" element={<ProcessMonitorRule />} />
        </Route>
      </Routes>
    </Router>
  );
}

const PrivateRoute = ({ children }) => {
  const isAuthenticated = useAuth();
  return isAuthenticated ? children : <Navigate to="/login" />;
};

function useAuth() {
  console.log(localStorage.getItem('isLoggedIn') === 'true')
  return localStorage.getItem('isLoggedIn') === 'true';
}

export default App;