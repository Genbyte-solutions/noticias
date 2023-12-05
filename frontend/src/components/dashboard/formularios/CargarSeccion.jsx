import React, { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import Notification from "../../../components/notificacion/Notificacion";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faFileImage } from "@fortawesome/free-solid-svg-icons";
import axios from "axios";

function CargarSeccion() {
  const { register, handleSubmit, formState: { errors }, reset } = useForm();
  const [iconoName, setIconoName] = useState("Subir icono");
  const [showNotification, setShowNotification] = useState(false);
  const [notificationMessage, setNotificationMessage] = useState("");

  const handleNotificationClose = () => {
    setShowNotification(false);
    setNotificationMessage("");
  };

  const onSubmit = async (data) => {
    const formData = new FormData();
    formData.append("nombre", data.nombre);
    formData.append("codigo", data.codigo);
    if (data.icono && data.icono.length > 0) {
      formData.append("icono", data.icono[0]);
    }

    try {
      const response = await axios({
        method: "post",
        url: "http://localhost:8080/api/v1/seccion",
        data: formData,
        headers: { "Content-Type": "multipart/form-data" },
      });

      if (response.status === 201) {
        setNotificationMessage("Sección agregada con éxito");
      } else {
        setNotificationMessage("Error al enviar el formulario: " + response.statusText);
      }
    } catch (error) {
      setNotificationMessage("Error al enviar el formulario: " + error.message);
    }
    setShowNotification(true);
    resetForm(); // Resetear el formulario después de enviar los datos
  };

  const resetForm = () => {
    reset(); // Resetear los campos del formulario
    setIconoName("Subir icono"); // Resetear el nombre del icono
  };

  useEffect(() => {
    if (errors.icono) {
      setIconoName(errors.icono.message);
    }
  }, [errors.icono]);

  return (
    <section className="flexCT">
      <div className="form-container">
        <form onSubmit={handleSubmit(onSubmit)} className="form">
          <span className="titulo">Cargar Sección</span>
          <div>
            
            
          </div>
          <div>
            <label htmlFor="nombre">Nombre:</label>
            <input
              type="text"
              id="nombre"
              {...register("nombre", { required: "Completa este campo" })}
              className="input"
            />
            {errors.nombre && <span className="error-msg">{errors.nombre.message}</span>}
          </div>
          <div>
            <label className="cargar-archivo input" htmlFor="icono">
              <div className="icono">
                <FontAwesomeIcon icon={faFileImage} />
              </div>
              <div className="texto">
                <span>{iconoName}</span>
              </div>
            </label>
            <input
              type="file"
              id="icono"
              {...register("icono", { required: "Icono requerido" })}
              className="input"
              onChange={(e) => setIconoName(e.target.files[0].name)}
            />
            {errors.icono && <span className="error-msg">{errors.icono.message}</span>}
          </div>
          <div className="button-container">
            <button type="submit" className="send-button">Agregar Sección</button>
            <div className="reset-button-container">
              <button id="reset-btn" className="reset-button"onClick={resetForm} > Resetear</button>
          </div>
          </div>
        </form>
      </div>
      {showNotification && (
        <Notification message={notificationMessage} onClose={handleNotificationClose} />
      )}
    </section>
  );
}

export default CargarSeccion;
