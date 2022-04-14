import React from 'react'
import { CFooter } from '@coreui/react'

const AppFooter = () => {
  return (
    <CFooter>
      <div>
        <span className="ms-1">Jiaqi &copy; 2022</span>
      </div>
      <div className="ms-auto">
        <span className="me-1">Developed using Quarkus, React &amp; CoreUI</span>
      </div>
    </CFooter>
  )
}

export default React.memo(AppFooter)
