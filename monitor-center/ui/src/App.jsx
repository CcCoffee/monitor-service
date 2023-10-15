import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
} from 'react-router-dom'
import LoginPage from './pages/Login'
import Main from './pages/Main'
import AgentStatus from './pages/AgentStatus'
import RulePage from './pages/RulePage'
import ServerPage from './pages/ServerPage'
import NotificationChannelPage from './pages/NotificationChannelPage'
import AlertPage from './pages/AlertPage'
import AlertViewPage from './pages/AlertViewPage'

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
          }>
          <Route index path="/" element={<AgentStatus />} />
          <Route index path="/agent-status" element={<AgentStatus />} />
          <Route path="/rule" element={<RulePage />} />
          <Route path="/server" element={<ServerPage />} />
          <Route path="/channel" element={<NotificationChannelPage />} />
          <Route path="/alert" element={<AlertPage />} />
          <Route path="/alert/:id" element={<AlertViewPage />} />
        </Route>
      </Routes>
    </Router>
  )
}

const PrivateRoute = ({ children }) => {
  const isAuthenticated = useAuth()
  return isAuthenticated ? children : <Navigate to="/login" />
}

function useAuth() {
  return localStorage.getItem('isLoggedIn') === 'true'
}

export default App
