import axios from "axios";

// Obtener todas las noticias
export const listaNoticias = async () => {
  try {
    const response = await axios.get("http://localhost:8080/api/noticia/nueva");
    return response.data;
  } catch (error) {
    console.error("Error al obtener la lista de noticias: ", error);
    throw error;
  }
};



// Obtener noticia por ID
export const noticiaPorId = async (id) => {
  try {
    const response = await axios.get(`http://localhost:8080/api/noticia/${id}`);
    return response.data;
  } catch (error) {
    console.error("Error al obtener noticia por ID: ", error);
    throw error;
  }
};

// Cargar noticia
export const cargarNoticia = async (noticia) => {
  try {
    const response = await axios.post(
      "http://localhost:8080/api/noticia/nueva",
      noticia
    );
    return response.data;
  } catch (error) {
    console.error("Error al cargar la noticia: ", error);
    throw error;
  }
};

// Actualizar noticia por ID
export const actualizarNoticia = async (id, noticia) => {
  try {
    const response = await axios.put(
      `http://localhost:8080/api/noticia/${id}`,
      noticia
    );
    return response.data;
  } catch (error) {
    console.error("Error al actualizar la noticia: ", error);
    throw error;
  }
};

// Eliminar noticia por ID
export const eliminarNoticia = async (id) => {
  try {
    const response = await axios.delete(`http://localhost:8080/api/noticia/${id}`);
    return response.data;
  } catch (error) {
    console.error("Error al eliminar la noticia: ", error);
    throw error;
  }
};
