import { useEffect, useRef, useState } from "react";
import { useForm } from "react-hook-form";
import Notification from "../../../components/notificacion/Notificacion";
import axios from "axios";

function CargarUsuario() {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();
  const [imagenName, setImagenName] = useState("Subir imagen");
  const dataRef = useRef(null);
  const [showNotification, setShowNotification] = useState(false);
  const [notificationMessage, setNotificationMessage] = useState("");


  const handleNotificationClose = () => {
    setShowNotification(false);
    setNotificationMessage("");
  };

  useEffect(() => {
    if (errors.foto) {
      setImagenName(errors.foto.message);
    } else {
      const imagen = dataRef.current ? dataRef.current.foto : null;
      setImagenName(imagen ? imagen[0].name : "Subir imagen");
    }
  }, [errors.foto, dataRef]);

  const onSubmit = async (data) => {
    dataRef.current = { ...data };
    const formData = new FormData();
    formData.append("nombre", data.nombre);
    formData.append("apellido", data.apellido);
    formData.append("nombreUsuario", data.nombreUsuario);
    formData.append("rol", data.rol);
    formData.append("foto", data.foto[0]);
    formData.append("email", data.email);
    formData.append("telefono", data.telefono);
    formData.append("password", data.password);
    formData.append("confirmPassword", data.confirmPassword);
    // console.log(formData);
    
    try {
        const response = await axios.post(`http://localhost:8080/api/v1/usuario`, formData, {
            headers: {
              'Content-Type': 'multipart/form-data'
            }
        })

      if (response.status === 201) {
        // const responseData = await response.text();
        setNotificationMessage('Usuario registrado con éxito');
        setShowNotification(true);
      } 
    } catch (error) {
      const status = error.response.status;
      if(status === 500){
        setNotificationMessage('El usuario ya existe');
        setShowNotification(true);
      }else{
        setNotificationMessage('Solicitud rechazada por sintaxis invalidad');
        setShowNotification(true);
      }

    }
  };

  return (
    <section className="flexCT">
      <div className="form-container">
        <form onSubmit={handleSubmit(onSubmit)} className="form">
          <span className="titulo">Cargar Usuario</span>
          <div>
            <label htmlFor="nombre">Nombre</label>
            <input
              type="text"
              id="nombre"
              {...register("nombre", {
                required: "Por favor, completa este campo.",
                minLength: {value: 3, message: 'Mínimo 3 carácteres'},
                maxLength: {value: 20, message: 'Máximo 20 carácteres'}
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
                minLength: {value: 3, message: 'Mínimo 3 carácteres'},
                maxLength: {value: 50, message: 'Máximo 50 carácteres'}
              })}
              className="input"
            />
            {errors.apellido && (
              <span className="error-msg">{errors.apellido.message}</span>
            )}
          </div>
          <div>
            <label htmlFor="nombreUsuario">Nombre de usuario</label>
            <input
              type="text"
              id="nombreUsuario"
              {...register("nombreUsuario", {
                required: "Por favor, completa este campo.",
                minLength: {value: 3, message: 'Mínimo 3 carácteres'},
                maxLength: {value: 20, message: 'Máximo 20 carácteres'}
              })}
              className="input"
            />
            {errors.nombreUsuario && (
              <span className="error-msg">{errors.nombreUsuario.message}</span>
            )}
          </div>
          <div>
            <label htmlFor="rol">Rol</label>
            <input type="text" 
              placeholder="ADMIN or USER"
              id="rol"
              className="input"
              {...register('rol', {
                required: 'Debe indicar el rol',
                minLength: {value: 4, message: 'Debe ser ADMIN or USER'},
                maxLength: {value: 5, message: 'Debe ser ADMIN or USER'}
              })}/>
              {errors.rol && (<span className="error-msg">{errors.rol.message}</span>)}
          </div>
          <div>
            <label className="cargar-archivo input" htmlFor="foto">
              <div className="icono">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  fill=""
                  viewBox="0 0 24 24"
                >
                  <g strokeWidth="0" id="SVGRepo_bgCarrier"></g>
                  <g
                    strokeLinejoin="round"
                    strokeLinecap="round"
                    id="SVGRepo_tracerCarrier"
                  ></g>
                  <g id="SVGRepo_iconCarrier">
                    <path
                      fill=""
                      d="M10 1C9.73478 1 9.48043 1.10536 9.29289 1.29289L3.29289 7.29289C3.10536 7.48043 3 7.73478 3 8V20C3 21.6569 4.34315 23 6 23H7C7.55228 23 8 22.5523 8 22C8 21.4477 7.55228 21 7 21H6C5.44772 21 5 20.5523 5 20V9H10C10.5523 9 11 8.55228 11 8V3H18C18.5523 3 19 3.44772 19 4V9C19 9.55228 19.4477 10 20 10C20.5523 10 21 9.55228 21 9V4C21 2.34315 19.6569 1 18 1H10ZM9 7H6.41421L9 4.41421V7ZM14 15.5C14 14.1193 15.1193 13 16.5 13C17.8807 13 19 14.1193 19 15.5V16V17H20C21.1046 17 22 17.8954 22 19C22 20.1046 21.1046 21 20 21H13C11.8954 21 11 20.1046 11 19C11 17.8954 11.8954 17 13 17H14V16V15.5ZM16.5 11C14.142 11 12.2076 12.8136 12.0156 15.122C10.2825 15.5606 9 17.1305 9 19C9 21.2091 10.7909 23 13 23H20C22.2091 23 24 21.2091 24 19C24 17.1305 22.7175 15.5606 20.9844 15.122C20.7924 12.8136 18.858 11 16.5 11Z"
                      clipRule="evenodd"
                      fillRule="evenodd"
                    ></path>
                  </g>
                </svg>
              </div>
              <div className="texto">
                <span className={errors.foto ? "error-msg" : ""}>
                  {errors.foto ? errors.foto.message : imagenName}
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
          <div>
            <label htmlFor="email">Email</label>
            <input
              type="email"
              id="email"
              {...register("email", {
                required: "Por favor, completa este campo.",
                pattern: {value: /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/, message: 'Formato inválido, debe tener el siguiente formato: ejemplo@email.com'}
              })}
              className="input"
            />
            {errors.email && (
              <span className="error-msg">{errors.email.message}</span>
            )}
          </div>
          <div>
            <label htmlFor="telefono">Telefono</label>
            <input
              type="tel"
              id="telefono"
              {...register("telefono", {
                // required: "Por favor, completa este campo.",
              })}
              className="input"
            />
            {errors.telefono && (
              <span className="error-msg">{errors.telefono.message}</span>
            )}
          </div>
          <div>
            <label htmlFor="password">Contraseña</label>
            <input
              type="password"
              id="password"
              {...register("password", {
                required: "Por favor, completa este campo.",
                minLength: {value: 6, message: 'Mínimo 6 carácteres'},
                maxLength: {value: 20, message: 'Máximo 20 carácteres'}
              })}
              className="input"
            />
            {errors.password && (
              <span className="error-msg">{errors.password.message}</span>
            )}
          </div>
          <div>
            <label htmlFor="confirmPassword">Repita contraseña</label>
            <input
              type="password"
              id="confirmPassword"
              {...register("confirmPassword", {
                required: "Por favor, completa este campo.",
                minLength: {value: 3, message: 'Mínimo 3 carácteres'},
                maxLength: {value: 20, message: 'Máximo 20 carácteres'}
              })}
              className="input"
            />
            {errors.confirmPassword && (
              <span className="error-msg">{errors.confirmPassword.message}</span>
            )}
          </div>
          <div className="button-container">
            <button type="submit" className="send-button">
              Agregar Usuario
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

export default CargarUsuario;
