import React, { useState } from 'react'
import { Form, Button, Alert } from 'react-bootstrap'
import { useNavigate } from 'react-router-dom'
import axios from 'axios' // 确保已安装 axios
// 导入图标库
import { FaUser, FaLock, FaSignInAlt } from 'react-icons/fa'
import './Login.css' // 新增：导入自定义CSS文件

function Login() {
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState('')
  const navigate = useNavigate()

  const handleLogin = async (e) => {
    e.preventDefault()
    setError('')
    try {
      const response = await axios.post('http://localhost:8090/login', {
        username,
        password,
      })
      if (response.status === 200) {
        localStorage.setItem('isLoggedIn', 'true')
        localStorage.setItem('username', response.data.username)
        navigate('/')
      }
    } catch (error) {
      console.error(
        'Login failed:',
        error.response?.data?.message || error.message
      )
      setError('登录失败，请检查您的用户名和密码。')
    }
  }

  return (
    <div className="login-container">
      <div className="login-form">
        <h1 className="login-title">欢迎回来</h1>
        {error && (
          <Alert variant="danger" className="mb-4">
            {error}
          </Alert>
        )}
        <Form className="login-inputs" onSubmit={handleLogin}>
          <Form.Group className="mb-3">
            <div className="input-icon-wrapper">
              <FaUser className="input-icon" />
              <Form.Control
                id="adUsername"
                placeholder="AD用户名"
                required
                type="text"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
              />
            </div>
          </Form.Group>
          <Form.Group className="mb-3">
            <div className="input-icon-wrapper">
              <FaLock className="input-icon" />
              <Form.Control
                id="adPassword"
                placeholder="AD密码"
                required
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
            </div>
          </Form.Group>
          <Button className="login-button" type="submit">
            <FaSignInAlt className="mr-2" />
            登录
          </Button>
        </Form>
        <p className="support-text">
          遇到问题？{' '}
          <a href="#" className="support-link">
            联系IT支持
          </a>
        </p>
      </div>
    </div>
  )
}

export default Login
