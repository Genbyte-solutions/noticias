import * as types from "./authTypes";
import { request, setAuthHeader } from "../helpers/axios_helper.jsx";

export const login = (username, password) => async (dispatch) => {
  try {
    const response = await request("POST", "api/v1/entrar", {
      email: username,
      password: password,
    });
    const token = response.data.jwt;

    // Guardar el token en el localStorage
    localStorage.setItem("token", token);

    setAuthHeader(token);
    dispatch({ type: types.LOGIN_SUCCESS, payload: token });
  } catch (error) {
    setAuthHeader(null);
    dispatch({ type: types.LOGIN_FAIL });
  }
};

export const register =
  (firstName, lastName, username, password) => async (dispatch) => {
    try {
      const response = await request("POST", "/register", {
        firstName,
        lastName,
        login: username,
        password,
      });
      const token = response.data.token;
      setAuthHeader(token);
      dispatch({ type: types.LOGIN_SUCCESS, payload: token });
    } catch (error) {
      setAuthHeader(null);
      dispatch({ type: types.LOGIN_FAIL, error });
    }
  };

export const logout = () => (dispatch) => {
  // Eliminar el token del almacenamiento local (localStorage)
  localStorage.removeItem("token");

  // Eliminar el token del encabezado de autenticación
  setAuthHeader(null);

  // Disparar la acción de cierre de sesión
  dispatch({ type: types.LOGOUT });
};
