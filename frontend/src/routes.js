import React from 'react'

const Dashboard = React.lazy(() => import('./views/dashboard/Dashboard'))
const UploadData = React.lazy(() => import('./views/pages/upload-file/upload-file'))
const EmailComfirmation = React.lazy(() =>
  import('./views/pages/email-confirmation/email-confirmation'),
)

const routes = [
  { path: '/', exact: true, name: 'Login' },
  { path: '/dashboard', name: 'Dashboard', element: Dashboard },
  { path: '/upload-data', name: 'UploadData', element: UploadData },
  { path: '/login', name: 'Login', exact: true },
  { path: '/email_confirmation', name: 'EmailConfirmation', exact: true },
]

export default routes
