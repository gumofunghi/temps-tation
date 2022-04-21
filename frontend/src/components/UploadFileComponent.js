import React, { Component } from 'react'
import uploadFileService from 'src/services/upload-file.service'
import { useState } from 'react'

import {
  CButton,
  CButtonGroup,
  CCard,
  CCardBody,
  CFormLabel,
  CFormInput,
  CCol,
  CRow,
  CProgress,
  CForm,
} from '@coreui/react'

const UploadFilesComponent = (props) => {
  const [validated, setValidated] = useState(false)
  const [progress, setProgress] = useState(0)
  const [selectedFile, setSelectedFile] = useState()
  const [currentFile, setCurrentFile] = useState()
  const [isSelected, setIsSelected] = useState(false)

  const handleChange = (event) => {
    setSelectedFile(event.target.files[0])
    setIsSelected(true)
  }

  const handleSubmit = (event) => {
    uploadFileService.upload(selectedFile, progress)
  }

  return (
    <CCard className="mb-4">
      <CCardBody>
        <CRow>
          <CCol sm={5}>
            <h4 className="card-title mb-0"> Upload data </h4>
          </CCol>
          <CCol sm={7} className="d-none d-md-block">
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
          <CForm
            className="row g-3 needs-validation"
            noValidate
            validated={validated}
            onSubmit={handleSubmit}
          >
            <CCol sm={7}>
              <CFormLabel htmlFor="formFile">Upload your data file here (.csv)</CFormLabel>
              <CFormInput
                className="mt-3"
                onChange={handleChange}
                type="file"
                id="formFile"
                required
              />
              {isSelected ? (
                <div>
                  <p>Filetype: {selectedFile.type}</p>
                  <p>Size in bytes: {selectedFile.size}</p>
                  <p>
                    lastModifiedDate:{}
                    {selectedFile.lastModifiedDate.toLocaleDateString()}
                  </p>
                </div>
              ) : (
                <p>Select a file to show details</p>
              )}
            </CCol>
            <CCol sm={3}>
              <CButton
                type="submit"
                className="mt-5"
                disabled={!selectedFile}
                onClick={handleSubmit}
              >
                Upload
              </CButton>
            </CCol>
          </CForm>
        </CRow>
        {/* <CRow>{currentFile && <CProgress value={progress}>{progress}%</CProgress>}</CRow> */}
      </CCardBody>
    </CCard>
  )
}

export default React.memo(UploadFilesComponent)
