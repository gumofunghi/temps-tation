import Axios from 'axios'

class UploadFilesService {
  upload(file, onUploadProgress) {
    let formData = new FormData()
    formData.append('file', file)
    return Axios.post('http://localhost:8080/upload_file', formData, {
      header: {
        'Content-Type': 'multipart/form-data',
      },
      // onUploadProgress,
    })
  }
  getFiles() {
    return Axios.get('/files')
  }
}

export default new UploadFilesService()
