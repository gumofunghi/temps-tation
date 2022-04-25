import React, { useState, useRef } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import {
  CButton,
  CCard,
  CCardBody,
  CCol,
  CContainer,
  CForm,
  CFormInput,
  CInputGroup,
  CInputGroupText,
  CRow,
} from '@coreui/react'
import CIcon from '@coreui/icons-react'
import { cilLockLocked, cilUser } from '@coreui/icons'
import AuthService from 'src/services/auth.service'

const Register = () => {
  let navigate = useNavigate()
  const form = useRef()
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const [passwordRepeat, setPasswordRepeat] = useState('')
  const [email, setEmail] = useState('')

  const onChangeUsername = (e) => {
    const username = e.target.value
    setUsername(username)
  }

  const onChangeEmail = (e) => {
    const email = e.target.value
    setEmail(email)
  }

  const onChangePassword = (e) => {
    const password = e.target.value
    setPassword(password)
  }

  const onChangePasswordRepeat = (e) => {
    const passwordRepeat = e.target.value
    setPasswordRepeat(passwordRepeat)
  }

  let handleSubmit = async (e) => {
    e.preventDefault()
    AuthService.register(username, email, password).then(
      (response) => {
        setUsername('')
        setPassword('')
        setPasswordRepeat('')
        setEmail('')
        navigate('/email_confirmation')
        window.location.reload()
        console.log(response)
      },
      (error) => {
        const resMessage =
          (error.response && error.response.data && error.response.data.message) ||
          error.message ||
          error.toString()
        console.log(resMessage)
      },
    )
  }

  return (
    <div className="bg-light min-vh-100 d-flex flex-row align-items-center">
      <CContainer>
        <CRow className="justify-content-center">
          <CCol md={9} lg={7} xl={6}>
            <CCard className="mx-4">
              <CCardBody className="p-4">
                <CForm onSubmit={handleSubmit}>
                  <h1 className="text-center">Create Account</h1>
                  <CInputGroup className="mb-3">
                    <CInputGroupText>
                      <CIcon icon={cilUser} />
                    </CInputGroupText>
                    <CFormInput
                      placeholder="Username"
                      autoComplete="username"
                      value={username}
                      onChange={onChangeUsername}
                    />
                  </CInputGroup>
                  <CInputGroup className="mb-3">
                    <CInputGroupText>@</CInputGroupText>
                    <CFormInput
                      placeholder="Email"
                      autoComplete="email"
                      value={email}
                      onChange={onChangeEmail}
                    />
                  </CInputGroup>
                  <CInputGroup className="mb-3">
                    <CInputGroupText>
                      <CIcon icon={cilLockLocked} />
                    </CInputGroupText>
                    <CFormInput
                      type="password"
                      placeholder="Password"
                      autoComplete="new-password"
                      value={password}
                      onChange={onChangePassword}
                    />
                  </CInputGroup>
                  <CInputGroup className="mb-4">
                    <CInputGroupText>
                      <CIcon icon={cilLockLocked} />
                    </CInputGroupText>
                    <CFormInput
                      type="password"
                      placeholder="Repeat password"
                      autoComplete="new-password"
                      value={passwordRepeat}
                      onChange={onChangePasswordRepeat}
                    />
                  </CInputGroup>
                  <div className="d-grid">
                    <CButton color="primary" type="submit">
                      Create Account
                    </CButton>
                    <span className="mt-2 text-center">
                      Already have an account?
                      <Link className="ml-1" to="/login">
                        Login Here
                      </Link>
                    </span>
                  </div>
                </CForm>
              </CCardBody>
            </CCard>
          </CCol>
        </CRow>
      </CContainer>
    </div>
  )
}

export default Register
