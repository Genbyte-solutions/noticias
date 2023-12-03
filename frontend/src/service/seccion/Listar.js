import axios from "axios";

//  TODAS LAS SECCIONES
export const listaSecciones = async () => {
  try {
    const response = await axios.get(
      "http://localhost:8080/api/v1/secciones"
    );
    return response.data;
  } catch (error) {
    console.error("Error al obtener la lista de secciones de la api: ", error);
    throw error; // Propaga el error para que el llamador pueda manejarlo
  }
};

//  SECCION POR ID
export const seccionPorId = async (id) => {
  try {
    const response = await axios.get(`http://localhost:8080/api/seccion/${id}`);
    return response.data;
  } catch (error) {
    console.error("Error al obtener sección por id: ", error);
    throw error;
  }
};

//  SECCION POR CODIGO
export const seccionPorCodigo = async (id) => {
  try {
    const response = await axios.get(
      `http://localhost:8080/api/v1/seccion/${id}`
    );
    return response.data;
  } catch (error) {
    console.error("Error al obtener sección por código: ", error);
    throw error;
  }
};
