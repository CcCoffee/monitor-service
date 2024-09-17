'use client'

import { useState } from 'react'
import { Eye, EyeOff, Activity } from 'lucide-react'
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Card, CardContent, CardFooter, CardHeader, CardTitle } from "@/components/ui/card"

export default function LoginPage() {
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [showPassword, setShowPassword] = useState(false)
  const [errors, setErrors] = useState({ email: '', password: '' })

  const validateForm = () => {
    let isValid = true
    const newErrors = { email: '', password: '' }

    if (!email) {
      newErrors.email = 'Email is required'
      isValid = false
    } else if (!/\S+@\S+\.\S+/.test(email)) {
      newErrors.email = 'Email is invalid'
      isValid = false
    }

    if (!password) {
      newErrors.password = 'Password is required'
      isValid = false
    } else if (password.length < 6) {
      newErrors.password = 'Password must be at least 6 characters'
      isValid = false
    }

    setErrors(newErrors)
    return isValid
  }

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault()
    if (validateForm()) {
      // Implement login logic here
      console.log('Login submitted', { email, password })
    }
  }

  return (
      <div className="min-h-screen flex flex-col items-center justify-center relative overflow-hidden">
        {/* Cool background */}
        <div className="absolute inset-0 bg-gradient-to-br from-purple-400 via-pink-500 to-red-500">
          <div className="absolute inset-0 bg-grid-white/[0.2] bg-[length:20px_20px]" />
        </div>

        {/* Product info */}
        <div className="relative z-10 text-center mb-8 bg-white/80 p-6 rounded-lg backdrop-blur-sm">
          <div className="flex items-center justify-center mb-4">
            <Activity className="w-12 h-12 text-primary mr-2" />
            <h1 className="text-4xl font-bold text-primary">SmartWatch</h1>
          </div>
          <p className="text-lg text-gray-800 max-w-md">
            Your intelligent application process and log monitoring system.
            Stay on top of your systems with real-time insights and alerts.
          </p>
        </div>

        {/* Login card */}
        <Card className="w-full max-w-md relative z-10 bg-white/90 backdrop-blur-sm">
          <CardHeader>
            <CardTitle className="text-2xl font-bold text-center">Login to SmartWatch</CardTitle>
          </CardHeader>
          <CardContent>
            <form onSubmit={handleSubmit} className="space-y-4">
              <div className="space-y-2">
                <Label htmlFor="email">Email</Label>
                <Input
                    id="email"
                    type="email"
                    placeholder="Enter your email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                />
                {errors.email && <p className="text-sm text-red-500">{errors.email}</p>}
              </div>
              <div className="space-y-2">
                <Label htmlFor="password">Password</Label>
                <div className="relative">
                  <Input
                      id="password"
                      type={showPassword ? "text" : "password"}
                      placeholder="Enter your password"
                      value={password}
                      onChange={(e) => setPassword(e.target.value)}
                  />
                  <Button
                      type="button"
                      variant="ghost"
                      size="icon"
                      className="absolute right-0 top-0 h-full px-3 py-2 hover:bg-transparent"
                      onClick={() => setShowPassword(!showPassword)}
                  >
                    {showPassword ? (
                        <EyeOff className="h-4 w-4 text-gray-500" />
                    ) : (
                        <Eye className="h-4 w-4 text-gray-500" />
                    )}
                    <span className="sr-only">
                    {showPassword ? "Hide password" : "Show password"}
                  </span>
                  </Button>
                </div>
                {errors.password && <p className="text-sm text-red-500">{errors.password}</p>}
              </div>
              <Button type="submit" className="w-full">
                Login
              </Button>
            </form>
          </CardContent>
          <CardFooter className="flex flex-col space-y-2 text-center text-sm text-gray-600">
            <a href="#" className="hover:underline">
              Forgot password?
            </a>
            <span>
            Don't have an account?{' '}
              <a href="#" className="font-semibold text-primary hover:underline">
              Create new account
            </a>
          </span>
          </CardFooter>
        </Card>
      </div>
  )
}