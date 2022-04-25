import React from 'react'
import { useNavigate } from 'react-router-dom'
import { CButton, CCard, CCardBody, CCardHeader, CCol, CContainer, CRow } from '@coreui/react'

const EmailConfirmation = () => {
  const navigate = useNavigate()
  let handleClick = async (e) => {
    e.preventDefault()
    navigate('/login')
    window.location.reload()
  }

  return (
    <div className="bg-light min-vh-100 d-flex flex-row align-items-center">
      <CContainer>
        <CRow className="justify-content-center">
          <CCol md={8}>
            <CCard className="p-4">
              <CCardHeader>
                <h3>Registration Successful</h3>
              </CCardHeader>
              <CCardBody className="justify-content-center">
                <p>Please verify your email at your mailbox.</p>
                <CButton onClick={handleClick}>
                  <span className="px-3">Confirm</span>
                </CButton>
              </CCardBody>
            </CCard>
          </CCol>
        </CRow>
      </CContainer>
    </div>
  )
}

export default EmailConfirmation
