import { useState } from "react";
import { useForm } from "react-hook-form";
import { useDispatch } from "react-redux";
import { login } from "../../reducer/authActions.js";
import { setAuthHeader } from "../../helpers/axios_helper.jsx";
import axios from "axios";
import "./login2.css";
import { useNavigate } from 'react-router-dom';

const Login2 = () => {
  const navigate = useNavigate();

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();
  const dispatch = useDispatch();
  const [error, setError] = useState("");
  const [successMessage, setSuccessMessage] = useState("");

  const onSubmit = async (data) => {
    try {
      const response = await axios.post(
        "http://localhost:8080/api/v1/entrar",
        {
          email: data.username,
          password: data.password,
        },
        {
          headers: {
            'Content-Type': 'application/json',
          },
        }
      );

      if (response.status === 200) {
        const token = response.data.jwt;
        setAuthHeader(token);
        dispatch(login(data.username, data.password));
        setSuccessMessage("Inicio de sesión exitoso.");
        setError("");
        navigate('/administrador/', { state: { userData: response.data } });
      } else {
        setError("Error al iniciar sesión. Verifica tus credenciales.");
        setSuccessMessage("");
      }
    } catch (error) {
      console.error(error);
      if (error.response && error.response.status === 404) {
        setError("El correo no está registrado.");
      } else {
        setError("Error al iniciar sesión. Verifica tus credenciales.");
      }
      setSuccessMessage("");
    }
  };


  return (
    <div className="form-container">
      <form onSubmit={handleSubmit(onSubmit)} className="form">
        {error && <span className="error-msg">{error}</span>}
        {successMessage && <span className="success-msg">{successMessage}</span>}

        <div>
          <label htmlFor="username">Usuario</label>
          <input
            type="text"
            id="username"
            {...register("username", {
              required: "Por favor, completa este campo.",
            })}
            className="input"
          />
          {errors.username && (
            <span className="error-msg">{errors.username.message}</span>
          )}
        </div>

        <div>
          <label htmlFor="password">Contraseña</label>
          <input
            type="password"
            id="password"
            {...register("password", {
              required: "Por favor, completa este campo.",
            })}
            className="input"
          />
          {errors.password && (
            <span className="error-msg">{errors.password.message}</span>
          )}
        </div>

        <div className="button-container">
          <button type="submit" className="send-button">
            Iniciar Sesión
          </button>
        </div>
      </form>
    </div>
  );
};

export default Login2;
