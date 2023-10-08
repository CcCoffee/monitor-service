import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import LoginPage from './pages/Login';
import Main from './pages/Main';
import AgentStatus from './pages/AgentStatus';
import RulePage from './pages/RulePage'

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route
          path="/"
          element={
            <PrivateRoute>
              <Main />
            </PrivateRoute>
          }
        >
          <Route index path="/" element={<AgentStatus />}/>
          <Route index path="/agent-status" element={<AgentStatus />}/>
          <Route path="/rule" element={<RulePage />} />
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