import React, { useEffect, useState } from 'react';
import { Link } from "react-router-dom";
import banner from "../../assets/image/Mendoza-News-Banner.png";
import { popular } from '../../service/noticia/Principales.js';
import "./footer.css";
import axios from 'axios';
import { imagenPorIdNoticia } from '../../service/imagen/Imagen.js';
import { listaSecciones } from '../../service/seccion/Listar.js';
export default function Footer() {
  const [deportes, setDeportes] = useState([])
  const [imagenDeporte, setImagenesDeportes] = useState({})
  const [mendoza, setMendoza] = useState([])
  const [imagenMendoza, setImagenMendoza] = useState([])
  const [secciones, setSecciones] = useState([]);
  useEffect(() => {
    try {
      const fetchNews = async () => {
        const response = await axios.get("http://localhost:8080/api/v1/noticias_populares/deportes?offset=0&limit=3")
        const noticiasDeportes = response.data;
        setDeportes(noticiasDeportes);

        // Obtén las imágenes para cada noticia de deportes
        const imagenesPromises = noticiasDeportes.map(async (noticia) => {
          try {
            const imageUrl = await imagenPorIdNoticia(noticia.noticiaId);
            return imageUrl;
          } catch (error) {
            console.error("Error al obtener imagen por ID de noticia: ", error);
            return null;
          }
        });

        // Espera a que todas las imágenes se carguen antes de actualizar el estado
        const imagenes = await Promise.all(imagenesPromises);
        setImagenesDeportes(imagenes);
      }

      fetchNews();
    } catch (e) {
      console.log(e, "errorrr");
    }
  }, [])

  // TRAYENDO LA DE MENDOZA
  useEffect(() => {
    try {
      const fetchNews = async () => {
        const response = await axios.get("http://localhost:8080/api/v1/noticias_populares/mendoza?offset=0&limit=3")
        const noticiasMendoza = response.data;
        setMendoza(noticiasMendoza);

        // Obtén las imágenes para cada noticia de deportes
        const imagenesPromises = noticiasMendoza.map(async (noticia) => {
          try {
            const imageUrl = await imagenPorIdNoticia(noticia.noticiaId);
            return imageUrl;
          } catch (error) {
            console.error("Error al obtener imagen por ID de noticia: ", error);
            return null;
          }
        });

        // Espera a que todas las imágenes se carguen antes de actualizar el estado
        const imagenes = await Promise.all(imagenesPromises);
        setImagenMendoza(imagenes)
      }

      fetchNews();
    } catch (e) {
      console.log(e, "errorrr");
    }
  }, [])

  // Obtén la lista de secciones

  useEffect(() => {
    try {
      const obtenerSecciones = async () => {
        try {
          const seccionesData = await listaSecciones();
          setSecciones(seccionesData);
        } catch (error) {
          console.error("Error al obtener la lista de secciones: ", error);
        }
      };

      obtenerSecciones();
    } catch (e) {
      console.log(e, "errorrr");
    }
  }, []);

  return (
    <div className='container__footer'>
      <footer >
        <div className="container">
          <div className="box logo">
            <img src={banner} alt="" />
            <p>Unimos seriedad y rigor periodístico para ofrecerte información confiable y relevante. ¡Bienvenido a la era de la verdad informativa!</p>

            <i className='fa fa-envelope'></i>
            <span>MendozaNews@gmail.com</span><br />
            <i className="fa fa-headphones"></i>
            <span>0260 15-433-0366</span>
          </div>

          <div className="box">
            <Link to={`/seccion/Deportes`}>
              <h3>DEPORTES</h3>
            </Link>
            {deportes.map((d, index) => {
              return (
                <div className="item">
                  <img className='img__footer' src={imagenDeporte[index]} alt="adwawdawd" />
                  <Link to={`/noticia/${d.titulo}/${d.noticiaId}`}>
                    <p>{d.subtitulo.slice(0, 50)} ...</p>
                  </Link>
                </div>
              )
            })}

          </div>

          <div className="box">
            <Link to={`/seccion/MENDOZA`}>
              <h3>MENDOZA</h3>
            </Link>
            {mendoza.map((d, index) => {
              return (
                <div className="item">
                  <img className="img__footer" src={imagenMendoza[index]} alt="adwawdawd" />
                  <Link to={`/noticia/${d.titulo}/${d.noticiaId}`}>
                    <p>{d.subtitulo.slice(0, 50)} ...</p>
                  </Link>
                </div>
              )
            })}
          </div>


          <div className="box">
            <h3>Etiquetas</h3>
            <ul>
              {secciones.slice(0, 4).map((seccion, index) => (
                <Link key={seccion.id} to={`/seccion/${seccion.nombre}`}>
                  <li>
                    <span>{seccion.nombre}</span>
                  </li>
                </Link>
              ))}
              <li>
                ......
              </li>
            </ul>
            {/* <Link to={`/Naturaleza`}><li>
                <span>Naturaleza</span><label htmlFor="">(5)</label>
              </li></Link>
              <Link to={`/Moda`}><li>
                <span>Moda</span><label htmlFor="">(8)</label>
              </li></Link>
              <Link to={`/Salud`}><li>
                <span>Salud</span><label htmlFor="">(6)</label>
              </li></Link> */}
          </div>
        </div>
      </footer>


      <div className="legal">
        <div className="container flexSB">
          <p>
            © 2023 <Link to='/'>MendozaNews</Link>
          </p>
          <Link to='/privacidad/politica'>
            <p>
              Política de privacidad
            </p>
          </Link>
          <p>Desarrollado por <a href="https://digitalculture.es/Ar/index.html"><span>Digital Culture</span></a></p>
        </div>
      </div>

    </div>
  );
}
