import axios from "axios";

// Obtener la lista de autores
export const listaAutores = async () => {
  try {
    const response = await axios.get("http://localhost:8080/api/autor/listar");
    return response.data;
  } catch (error) {
    console.error("Error al obtener la lista de autores: ", error);
    throw error; // Propaga el error para que el llamador pueda manejarlo
  }
};

// Obtener autor por ID
export const autorPorId = async (id) => {
  try {
    const response = await axios.get(`http://localhost:8080/api/autor/${id}`);
    return response.data;
  } catch (error) {
    console.error("Error al obtener autor por ID: ", error);
    throw error;
  }
};
