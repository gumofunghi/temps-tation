import React, { Component } from 'react'
import uploadFileService from 'src/services/upload-file.service'

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
  CProgress,
} from '@coreui/react'

export default class UploadFilesComponent extends Component {
  constructor(props) {
    super(props)
    this.state = {
      selectedFiles: undefined,
      currentFile: undefined,
      progress: 0,
      message: '',
      fileInfos: [],
    }
  }

  selectFile(event) {
    this.setState({
      selectedFiles: event.target.files,
    })
  }

  upload() {
    let currentFile = this.state.selectedFiles[0]
    this.setState({
      progress: 0,
      currentFile: currentFile,
    })
    uploadFileService
      .upload(currentFile, (event) => {
        this.setState({
          progress: Math.round((100 * event.loaded) / event.total),
        })
      })
      .then((response) => {
        this.setState({
          message: response.data.message,
        })
        return uploadFileService.getFiles()
      })
      .then((files) => {
        this.setState({
          fileInfos: files.data,
        })
      })
      .catch(() => {
        this.setState({
          progress: 0,
          message: 'Unable to upload file',
          currentFile: undefined,
        })
      })
    this.setState({
      selectedFiles: undefined,
    })
  }

  componentDidMount() {
    uploadFileService.getFiles().then((response) => {
      this.setState({
        fileInfos: response.data,
      })
    })
  }

  render() {
    const { selectedFiles, currentFile, progress, message, fileInfos } = this.state
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
            <CCol sm={5}>
              <CFormLabel htmlFor="formFile">Upload your data file here (.csv)</CFormLabel>
              <CFormInput type="file" id="formFile" />
            </CCol>
          </CRow>
          <CRow>{currentFile && <CProgress value={progress}>{progress}%</CProgress>}</CRow>
        </CCardBody>
      </CCard>
    )
  }
}
