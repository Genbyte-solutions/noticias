/* eslint-disable react/prop-types */

import "./notificacion.css";

const Notification = ({ message, onClose }) => {
  return (
    <div className="notification-container">
      <div className="notification">
        <div className="message">{message}</div>
        <button onClick={onClose} className="close-button">
          Cerrar
        </button>
      </div>
    </div>
  );
};

export default Notification;
