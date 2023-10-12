import React from 'react';
import ReactDOM from 'react-dom';
import {createRoot} from 'react-dom/client'
import NotificationToast from '../components/NotificationToast';

const notify = (header, body, variant) => {
    const container = document.createElement('div');
    document.body.appendChild(container);
  
    const handleClose = () => {
      ReactDOM.unmountComponentAtNode(container);
      document.body.removeChild(container);
    };
  
    const root = createRoot(container);
    root.render(
      <NotificationToast header={header} body={body} variant={variant} onClose={handleClose} />
    );
  };
  
  const notifyFailure = (header, body) => {
    notify(header, body, 'danger');
  };
  
  const notifySuccess = (header, body) => {
    notify(header, body, 'success');
  };
  
  export { notify, notifyFailure, notifySuccess };