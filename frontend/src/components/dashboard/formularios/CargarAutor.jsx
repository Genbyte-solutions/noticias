import { useEffect, useRef, useState } from "react";
import { useForm } from "react-hook-form";
import Notification from "../../../components/notificacion/Notificacion";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faFileImage } from "@fortawesome/free-solid-svg-icons";
const CargarAutor = () => {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();
  const [fotoName, setFotoName] = useState("Subir foto");
  const dataRef = useRef(null);
  const [showNotification, setShowNotification] = useState(false);
  const [notificationMessage, setNotificationMessage] = useState("");

  const handleNotificationClose = () => {
    setShowNotification(false);
    setNotificationMessage("");
  };

  useEffect(() => {
    if (errors.foto) {
      setFotoName(errors.foto.message);
    } else {
      const foto = dataRef.current ? dataRef.current.foto : null;
      setFotoName(foto ? foto[0].name : "Subir foto");
    }
  }, [errors.foto, dataRef]);

  const onSubmit = async (data) => {
    dataRef.current = { ...data };
    const formData = new FormData();
    formData.append("apellido", data.apellido);
    formData.append("nombre", data.nombre);
    formData.append("foto", data.foto[0]);

    try {
      const response = await fetch("http://localhost:8080/api/autor/nuevo", {
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
          <span className="titulo">Cargar Autor</span>
          <div>
            <label htmlFor="nombre">Nombre</label>
            <input
              type="text"
              id="nombre"
              {...register("nombre", {
                required: "Por favor, completa este campo.",
              })}
              className="input"
            />
            {errors.nombre && (
              <span className="error-msg">{errors.nombre.message}</span>
            )}
          </div>
          <div>
            <label htmlFor="apellido">Apellido</label>
            <input
              type="text"
              id="apellido"
              {...register("apellido", {
                required: "Por favor, completa este campo.",
              })}
              className="input"
            />
            {errors.apellido && (
              <span className="error-msg">{errors.apellido.message}</span>
            )}
          </div>
          <div>
            <label className="cargar-archivo input" htmlFor="foto">
              <div className="icono">
                <FontAwesomeIcon icon={faFileImage} />
              </div>
              <div className="texto">
                <span className={errors.foto ? "error-msg" : ""}>
                  {errors.foto ? errors.foto.message : fotoName}
                </span>
              </div>
            </label>
            <input
              type="file"
              id="foto"
              {...register("foto", {
                required: "Por favor, sube una imagen.",
              })}
            />
          </div>
          <div className="button-container">
            <button type="submit" className="send-button">
              Agregar Autor
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
};

export default CargarAutor;
