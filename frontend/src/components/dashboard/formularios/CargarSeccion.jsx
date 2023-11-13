import { useEffect, useRef, useState } from "react";
import { useForm } from "react-hook-form";
import Notification from "../../../components/notificacion/Notificacion";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faFileImage } from "@fortawesome/free-solid-svg-icons";
function CargarSeccion() {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();
  const [iconoName, setIconoName] = useState("Subir icono");
  const dataRef = useRef(null);
  const [showNotification, setShowNotification] = useState(false);
  const [notificationMessage, setNotificationMessage] = useState("");

  const handleNotificationClose = () => {
    setShowNotification(false);
    setNotificationMessage("");
  };

  useEffect(() => {
    if (errors.icono) {
      setIconoName(errors.icono.message);
    } else {
      const icono = dataRef.current ? dataRef.current.icono : null;
      setIconoName(icono ? icono[0].name : "Subir icono");
    }
  }, [errors.icono, dataRef]);

  const onSubmit = async (data) => {
    dataRef.current = { ...data };
    const formData = new FormData();
    formData.append("codigo", data.codigo);
    formData.append("nombre", data.nombre);
    if (data.icono) {
      formData.append("icono", data.icono[0]);
    }

    try {
      const response = await fetch("http://localhost:8080/api/seccion/nueva", {
        method: "POST",
        body: formData,
      });

      if (response.ok) {
        const responseData = await response.text();
        setNotificationMessage(responseData);
        setShowNotification(true);
      } else {
        setNotificationMessage(
          "Error al enviar el formulario. Response not ok: " +
            response.statusText
        );
        setShowNotification(true);
      }
    } catch (error) {
      setNotificationMessage("Error al enviar el formulario: " + error.message);
      setShowNotification(true);
    }
  };

  return (
    <section className="flexCT">
      <div className="form-container">
        <form onSubmit={handleSubmit(onSubmit)} className="form">
          <span className="titulo">Cargar Sección</span>
          <div>
            <label htmlFor="codigo">Codigo:</label>
            <input
              type="text"
              id="codigo"
              {...register("codigo", { required: "Completa este campo" })}
              className="input"
            />
            {errors.codigo && (
              <span className="error-msg">{errors.codigo.message}</span>
            )}
          </div>
          <div>
            <label htmlFor="nombre">Nombre:</label>
            <input
              type="text"
              id="nombre"
              {...register("nombre", { required: "Completa este campo" })}
              className="input"
            />
            {errors.nombre && (
              <span className="error-msg">{errors.nombre.message}</span>
            )}
          </div>
          <div>
            <label className="cargar-archivo input" htmlFor="icono">
              <div className="icono">
                <FontAwesomeIcon icon={faFileImage} />
              </div>
              <div className="texto">
                <span className={errors.icono ? "error-msg" : ""}>
                  {errors.icono ? errors.icono.message : iconoName}
                </span>
              </div>
            </label>
            <input
              type="file"
              id="icono"
              {...register("icono", { required: "Icono requerido" })}
              className="input"
            />
          </div>
          <div className="button-container">
            <button type="submit" className="send-button">
              Agregar Sección
            </button>
            <div className="reset-button-container">
              <button id="reset-btn" className="reset-button" type="reset">
                Resetear
              </button>
            </div>
          </div>
        </form>
      </div>
      {showNotification && (
        <Notification
          message={notificationMessage}
          onClose={handleNotificationClose}
        />
      )}
    </section>
  );
}

export default CargarSeccion;
