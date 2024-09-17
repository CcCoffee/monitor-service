import React, { useState } from 'react'
import { Form, Button } from 'react-bootstrap'
import { useNavigate } from 'react-router-dom'
import axios from 'axios' // 确保已安装 axios

function Login() {
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const navigate = useNavigate()

  const handleLogin = async (e) => {
    e.preventDefault()
    try {
      const response = await axios.post('http://localhost:8090/login', {
        username,
        password,
      })
      if (response.data.message === 'Login successful') {
        localStorage.setItem('isLoggedIn', 'true')
        localStorage.setItem('username', response.data.username)
        navigate('/')
      }
    } catch (error) {
      console.error(
        'Login failed:',
        error.response?.data?.message || error.message
      )
      // 这里可以添加错误提示给用户
    }
  }

  return (
    <div>
      <div className="d-flex justify-content-center align-items-center h-screen bg-gray-200 dark:bg-gray-900">
        <div className="w-80 p-6 bg-white rounded-md shadow-md dark:bg-gray-800">
          <h1 className="text-2xl font-bold text-center mb-4 dark:text-white">
            Login
          </h1>
          <Form className="space-y-4" onSubmit={handleLogin}>
            <Form.Group className="space-y-2">
              <Form.Label htmlFor="adUsername" style={{ fontWeight: 'bold' }}>
                AD Username
              </Form.Label>
              <Form.Control
                id="adUsername"
                placeholder="Enter your AD username"
                required
                type="text"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
              />
            </Form.Group>
            <Form.Group className="space-y-2">
              <Form.Label htmlFor="adPassword" style={{ fontWeight: 'bold' }}>
                AD Password
              </Form.Label>
              <Form.Control
                id="adPassword"
                required
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
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
