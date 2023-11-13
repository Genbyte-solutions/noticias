import axios from "axios";

import imagen1 from "../../assets/image/ImagenPersonal - copia.JPG";
import imagen2 from "../../assets/image/team-3 - copia.jpg";
import imagen3 from "../../assets/image/UrielElizaur.Fg-1343 - copia.jpg";

export const obtenerNoticiasRecientes = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/noticias/populares');
    return response.data;
  } catch (error) {
    console.error('Error al obtener las noticias populares: ', error);
    throw error;
  }
};


/* 3 Noticias Principales */
export const principales = [
  {
    imagen: imagen1,
    categoria: "San Rafael",
    titulo:
      "“San Rafael Emprende”, una ayuda de la comuna a microemprendedores",
    desc: [
      "Parrafo1 <strong>Descripcion</strong> de la noticia principa",
      "Parrafo2 Descripcion de la noticia principa",
      "Parrafo3 Descripcion de la noticia principa",
      "Parrafo4 Descripcion de la noticia principa",
      "Parrafo5 Descripcion de la noticia principa",
    ],
    quote: "Quote 1 for the noticia 1",
    autor: { nombre: "Autor1", id: "Autor1ID", foto: imagen1 },
    fecha: "2021-01-01",
  },
  {
    imagen: imagen2,
    categoria: "Espectáculos",
    titulo: "Show de humor esta noche en el Espacio Inca",
    desc: [
      "Parrafo1 <strong>Descripcion</strong> de la noticia principa",
      "Parrafo2 Descripcion de la noticia principa",
      "Parrafo3 Descripcion de la noticia principa",
      "Parrafo4 Descripcion de la noticia principa",
      "Parrafo5 Descripcion de la noticia principa",
    ],
    quote: "Quote 2 for the noticia 2",
    autor: { nombre: "Autor2", id: "Autor2ID", foto: imagen2 },
    fecha: "2022-02-02",
  },
  {
    imagen: imagen3,
    categoria: "San Rafael",
    titulo: "Un auto atropelló a una mujer en la avenida Espejo",
    desc: [
      "Parrafo1 <strong>Descripcion</strong> de la noticia principa",
      "Parrafo2 Descripcion de la noticia principa",
      "Parrafo3 Descripcion de la noticia principa",
      "Parrafo4 Descripcion de la noticia principa",
      "Parrafo5 Descripcion de la noticia principa",
    ],
    autor: { nombre: "Autor3", id: "Autor3ID", foto: imagen3 },
    fecha: "2023-03-03",
  },
];

export const popular = [
  {
    id: 1,
    imagen: imagen1,
    categoria: "San Rafael",
    titulo: "“Popular 1”, una ayuda de la comuna a microemprendedores",
    autor: "Autor1",
    fecha: "2021-01-01",
    desc: "Descripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas f",
  },
  {
    id: 2,
    imagen: imagen2,
    categoria: "Mendoza",
    titulo: "Popular 2 esta noche en el Espacio Inca",
    autor: "Autor2",
    fecha: "2022-02-02",
    desc: "Descripcion de la noticia princiDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fpal 2 lorem asdas asdf asd asfasf asf afas f",
  },
  {
    id: 3,
    imagen: imagen3,
    categoria: "Politica",
    titulo: "Popular 3 a una mujer en la avenida Espejo",
    autor: "Autor3",
    fecha: "2023-03-03",
    desc: "Descripcion de lDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fa noticia principal 3 lorem asdas asdf asd asfasf asf afas f",
  },
  {
    id: 4,
    imagen: imagen1,
    categoria: "Nacionales",
    titulo: "Popular 4 mañana en el Parque de los Jovenes",
    autor: "Autor4",
    fecha: "2024-04-04",
    desc: "Descripcion de la noticia Descripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fprincipal 4 lorem asdas asdf asd asfasf asf afas f",
  },
  {
    id: 5,
    imagen: imagen2,
    categoria: "Internacionales",
    titulo: "Popular 5 mañana en el Parque de los Jovenes",
    autor: "Autor5",
    fecha: "2025-05-05",
    desc: "Descripcion de la noticia principal 5 lorem asdDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fas asdf asd asfasf asf afas f",
  },
  {
    id: 6,
    imagen: imagen3,
    categoria: "Deportes",
    titulo: "Popular 6 mañana en el Parque de los Jovenes",
    autor: "Autor6",
    fecha: "2026-06-06",
    desc: "Descripcion de la noticia principal 6 loDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas frem asdas asdf asd asfasf asf afas f",
  },
  {
    id: 7,
    imagen: imagen1,
    categoria: "Editorial",
    titulo: "Popular 7 mañana en el Parque de los Jovenes",
    autor: "Autor7",
    fecha: "2027-07-07",
    desc: "Descripcion de la noticia principal 7 loreDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fm asdas asdf asd asfasf asf afas f",
  },
  {
    id: 8,
    imagen: imagen2,
    categoria: "Espectáculos",
    titulo: "Popular 8 mañana en el Parque de los Jovenes",
    autor: "Autor8",
    fecha: "2028-08-08",
    desc: "Descripcion de la noticia principal 8 lDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas forem asdas asdf asd asfasf asf afas f",
  },
  {
    id: 9,
    imagen: imagen3,
    categoria: "Tecnología",
    titulo: "Popular 9 mañana en el Parque de los Jovenes",
    autor: "Autor9",
    fecha: "2029-09-09",
    desc: "Descripcion 9 de la noticia principal 9 loDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas frem asdas asdf asd asfasf asf afas f",
  },
  {
    id: 10,
    imagen: imagen1,
    categoria: "Gastronomía",
    titulo: "Popular 10 mañana en el Parque de los Jovenes",
    autor: "Autor10",
    fecha: "2010-10-10",
    desc: "Descripcion 10 de la noticia principal 10 lorDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fem asdas asdf asd asfasf asf afas f",
  },
  {
    id: 11,
    imagen: imagen2,
    categoria: "Astrología",
    titulo: "Popular 11 mañana en el Parque de los Jovenes",
    autor: "Autor11",
    fecha: "2011-11-11",
    desc: "Descripcion 11 de la noticia principal 11 lorDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fem asdas asdf asd asfasf asf afas f",
  },
  {
    id: 12,
    imagen: imagen1,
    categoria: "Radio Garka",
    titulo: "Popular 12 mañana en el Parque de los Jovenes",
    autor: "Autor12",
    fecha: "2012-12-12",
    desc: "Descripcion 12 de la noticia principal 12 loremDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas fDescripcion de la noticia principal 1 lorem asdas asdf asd asfasf asf afas f asdas asdf asd asfasf asf afas f",
  },
];

// Obtener todas las noticias
export const listaNoticias = async () => {
  try {
    const response = await axios.get(
      "http://localhost:8080/api/noticia/listar"
    );
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

// Obtener noticias resumidas
export const noticiasResumen = async () => {
  try {
    const response = await axios.get(
      "http://localhost:8080/api/noticia/seccion"
    );
    return response.data;
  } catch (error) {
    console.error("Error al obtener noticias resumidas: ", error);
    throw error;
  }
};

// Obtener noticias por ID de autor
export const noticiasPorAutor = async (id) => {
  try {
    const response = await axios.get(
      `http://localhost:8080/api/noticia/autor/${id}`
    );
    return response.data;
  } catch (error) {
    console.error("Error al obtener noticias por ID de autor: ", error);
    throw error;
  }
};

// Obtener noticias por ID de sección
export const noticiasPorSeccion = async (id) => {
  try {
    const response = await axios.get(
      `http://localhost:8080/api/noticia/seccion/${id}`
    );
    return response.data;
  } catch (error) {
    console.error("Error al obtener noticias por ID de sección: ", error);
    throw error;
  }
};

// Obtener 6 noticias por ID de sección
export const noticias6PorSeccion = async (id) => {
  try {
    const response = await axios.get(
      `http://localhost:8080/api/noticia/seccion/${id}/6`
    );
    return response.data;
  } catch (error) {
    console.error("Error al obtener 6 noticias por ID de sección: ", error);
    throw error;
  }
};
