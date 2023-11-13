import axios from "axios";

// Obtener una imagen por ID
export const obtenerImagen = (imagen) => {
  try {
    // Crear una URL para la imagen
    const imageUrl = URL.createObjectURL(
      new Blob([imagen.contenido], { type: imagen.mime })
    );
    return imageUrl;
  } catch (error) {
    console.error("Error:", error);
    throw error;
  }
};

// Obtener una imagen por ID
export const imagenPorId = async (id) => {
  try {
    const response = await axios.get(`http://localhost:8080/api/imagen/${id}`, {
      responseType: "arraybuffer",
    });
    const imageUrl = URL.createObjectURL(new Blob([response.data]));
    return imageUrl;
  } catch (error) {
    console.error("Error al obtener la imagen por ID: ", error);
    throw error;
  }
};

// Obtener una imagen por ID de noticia
export const imagenPorIdNoticia = async (id) => {
  try {
    const response = await axios.get(
      `http://localhost:8080/api/imagen/noticia/${id}`,
      { responseType: "arraybuffer" }
    );
    const imageUrl = URL.createObjectURL(new Blob([response.data]));
    return imageUrl;
  } catch (error) {
    console.error("Error al obtener la imagen por ID de noticia: ", error);
    throw error;
  }
};

// Obtener una imagen por ID de usuario
export const imagenPorIdUsuario = async (id) => {
  try {
    const response = await axios.get(
      `http://localhost:8080/api/imagen/usuario/${id}`,
      { responseType: "arraybuffer" }
    );
    const imageUrl = URL.createObjectURL(new Blob([response.data]));
    return imageUrl;
  } catch (error) {
    console.error("Error al obtener la imagen por ID de usuario: ", error);
    throw error;
  }
};

// Obtener una imagen por ID de autor
export const imagenPorIdAutor = async (id) => {
  try {
    const response = await axios.get(
      `http://localhost:8080/api/imagen/autor/${id}`,
      { responseType: "arraybuffer" }
    );
    const imageUrl = URL.createObjectURL(new Blob([response.data]));
    return imageUrl;
  } catch (error) {
    console.error("Error al obtener la imagen por ID de autor: ", error);
    throw error;
  }
};

// Obtener una imagen por ID de seccion
export const imagenPorIdSeccion = async (id) => {
  try {
    const response = await axios.get(
      `http://localhost:8080/api/imagen/seccion/${id}`,
      { responseType: "arraybuffer" }
    );
    const imageUrl = URL.createObjectURL(new Blob([response.data]));
    return imageUrl;
  } catch (error) {
    console.error("Error al obtener la imagen por ID de seccion: ", error);
    throw error;
  }
};
