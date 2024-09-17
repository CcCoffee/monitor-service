import React, { useEffect } from 'react'
import { Form, Button } from 'react-bootstrap'
import { useNavigate } from 'react-router-dom'

function Login() {
  useEffect(() => {
    handleLogin()
  }, [])

  const navigate = useNavigate()
  const handleLogin = () => {
    localStorage.setItem('isLoggedIn', 'true')
    navigate('/')
  }

  return (
    <div>
      <div className="d-flex justify-content-center align-items-center h-screen bg-gray-200 dark:bg-gray-900">
        <div className="w-80 p-6 bg-white rounded-md shadow-md dark:bg-gray-800">
          <h1 className="text-2xl font-bold text-center mb-4 dark:text-white">
            Login
          </h1>
          <Form className="space-y-4">
            <Form.Group className="space-y-2">
              <Form.Label htmlFor="adUsername" style={{ fontWeight: 'bold' }}>
                AD Username
              </Form.Label>
              <Form.Control
                id="adUsername"
                placeholder="Enter your AD username"
                required
                type="text"
              />
            </Form.Group>
            <Form.Group className="space-y-2">
              <Form.Label htmlFor="adPassword" style={{ fontWeight: 'bold' }}>
                AD Password
              </Form.Label>
              <Form.Control id="adPassword" required type="password" />
            </Form.Group>
            <Button
              className="w-full mt-4 bg-blue-500 text-white py-2 rounded-md hover:bg-blue-600 transition duration-200"
              type="submit"
              style={{ fontWeight: 'bold' }}>
              Login
            </Button>
          </Form>
        </div>
      </div>
    </div>
  )
}

export default Login
