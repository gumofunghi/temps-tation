import React from 'react'

import {
  CButton,
  CButtonGroup,
  CCard,
  CCardBody,
  CCardFooter,
  CCardHeader,
  CFormLabel,
  CFormInput,
  CCol,
  CRow,
} from '@coreui/react'
import CIcon from '@coreui/icons-react'
import { cilCloudDownload } from '@coreui/icons'

const UploadData = () => {
  return (
    <>
      <CCard className="mb-4">
        <CCardBody>
          <CRow>
            <CCol sm={5}>
              <h4 className="card-title mb-0"> Upload data </h4>
            </CCol>
            <CCol sm={7} className="d-none d-md-block">
              <CButton color="primary" className="float-end">
                <CIcon icon={cilCloudDownload} />
              </CButton>
              <CButtonGroup className="float-end me-3">
                {['Upload File', 'History'].map((value) => (
                  <CButton
                    color="outline-secondary"
                    key={value}
                    className="mx-0"
                    active={value === 'Upload File'}
                  >
                    {value}
                  </CButton>
                ))}
              </CButtonGroup>
            </CCol>
          </CRow>
          <CRow className="mt-4">
            {/* <CCol sm={1}></CCol> */}
            <CCol sm={5}>
              <CFormLabel htmlFor="formFile">Upload your data file here (.csv)</CFormLabel>
              <CFormInput type="file" id="formFile" />
            </CCol>
          </CRow>
        </CCardBody>
      </CCard>
    </>
  )
}

export default UploadData
