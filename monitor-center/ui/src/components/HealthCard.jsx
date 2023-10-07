import React from 'react';

function HealthCard({ hostname, status }) {
  return (
    <div
      style={{
        backgroundColor: status === 'UP' ? 'green' : 'red',
        height: '100px',
        width: '100%',
        borderRadius: '10px',
        boxShadow: '0px 0px 10px rgba(0, 0, 0, 0.5)',
        padding: '10px',
      }}
    >
      <h3>{hostname}</h3>
      <p>{status}</p>
    </div>
  );
}

export default HealthCard;
