import React, { useState, useEffect } from 'react';
import { Toast, ToastContainer } from 'react-bootstrap';

const NotificationToast = ({ header, body, variant }) => {
  const [show, setShow] = useState(true);
  const [notificationTime, setNotificationTime] = useState(new Date());
  const [timeAgo, setTimeAgo] = useState("0 mins ago");

  let defaultHeader = '';
  let defaultBody = '';

  // 设置默认提示语
  if (variant === 'success') {
    defaultHeader = 'Success';
    defaultBody = 'Operation completed successfully.';
  } else if (variant === 'danger') {
    defaultHeader = 'Danger';
    defaultBody = 'An error occurred. Please try again.';
  } else {
    defaultHeader = 'Notification';
    defaultBody = 'This is a notification message.';
  }

  // 使用默认提示语或传入的参数
  const toastHeader = header || defaultHeader;
  const toastBody = body || defaultBody;

  useEffect(() => {
    let timer;
    if (show && variant === 'danger') {
      setNotificationTime(new Date());
      timer = setInterval(() => {
        setTimeAgo(getTimeAgo())
      }, 60000); // 更新时间间隔为1分钟
    }

    return () => {
      clearInterval(timer);
    };
  }, [show]);

  const getTimeAgo = () => {
    const now = new Date();
    const diff = Math.round((now - notificationTime) / 60000); // 计算分钟差异
    return `${diff} mins ago`;
  };

  return (
    <ToastContainer className="p-3" position="top-end" style={{ zIndex: 1 }}>
      <Toast
        bg={variant}
        onClose={() => setShow(false)}
        show={show}
        delay={variant === 'danger' ? null : 5000}
        autohide={variant !== 'danger'}
      >
        <Toast.Header>
          <strong className="me-auto">{toastHeader}</strong>
          {variant == 'danger'?<small>{timeAgo}</small>:<></>}
        </Toast.Header>
        <Toast.Body>{toastBody}</Toast.Body>
      </Toast>
    </ToastContainer>
  );
};

export default NotificationToast;