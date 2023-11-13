import axios from "axios";

// Obtener todos los usuarios
export const listaUsuarios = async () => {
  try {
    const response = await axios.get(
      "http://localhost:8080/api/usuario/listar"
    );
    return response.data;
  } catch (error) {
    console.error("Error al obtener la lista de usuarios: ", error);
    throw error;
  }
};

// Obtener usuario por ID
export const usuarioPorId = async (id) => {
  try {
    const response = await axios.get(`http://localhost:8080/api/usuario/${id}`);
    return response.data;
  } catch (error) {
    console.error("Error al obtener usuario por ID: ", error);
    throw error;
  }
};

// Obtener usuario por email
export const usuarioPorEmail = async (email) => {
  try {
    const response = await axios.get(
      `http://localhost:8080/api/usuario/email/${email}`
    );
    return response.data;
  } catch (error) {
    console.error("Error al obtener usuario por email: ", error);
    throw error;
  }
};

// Obtener usuario por nombre de usuario
export const usuarioPorUsername = async (nombreUsuario) => {
  try {
    const response = await axios.get(
      `http://localhost:8080/api/usuario/username/${nombreUsuario}`
    );
    return response.data;
  } catch (error) {
    console.error("Error al obtener usuario por nombre de usuario: ", error);
    throw error;
  }
};
