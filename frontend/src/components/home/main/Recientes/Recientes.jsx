import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import Heading from '../../../../components/heading/Heading.jsx';
import "./Recientes.css"

export default function Recientes() {
  const [principales, setPrincipales] = useState([]);
  const [imagenId, setImagenId] = useState([]);

  const fetchData = async () => {
    try {
      const apiUrl = 'http://localhost:8080/api/v1/noticias_populares';
      const params = {
        offset: 0,
        limit: 3,
      };
      const response = await axios.get(apiUrl, { params });
      setPrincipales(response.data);
    } catch (error) {
      console.error('Error al realizar la petición principal:', error);
    }
  };

  const fetchImage = async (id) => {
    try {
      const response = await axios.get(`http://localhost:8080/api/v1/portada/noticia/${id}`, { responseType: "arraybuffer" });
      const imageUrl = URL.createObjectURL(new Blob([response.data], { type: response.headers['content-type'] }));
      return imageUrl;
    } catch (error) {
      console.error("Error al obtener las portadas: ", error);
      throw error;
    }
  };

  const loadImage = async () => {
    try {
      const portadasPromises = principales.map(async (val) => {
        return fetchImage(val.noticiaId);
      });

      const portadasUrls = await Promise.all(portadasPromises);
      setImagenId(portadasUrls);
    } catch (error) {
      console.error("Error al cargar las portadas: ", error);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  useEffect(() => {
    loadImage();
  }, [principales]);


  return (

    <section className="Recientes">
      <Heading title="Popular" />
      <div className="noticias-container">
        {principales.map((val, index) => (
          <div key={index} className="noticia">
            <div className="box shadow">
              <div className="images row">
                <div className="img">
                  <img className='img__popular' src={imagenId[index]} alt="" />
                </div>
                <div className="categoria categoria1">
                  <Link to={`/seccion/${val.seccionResDto.nombre}`}>
                    <span>{val.seccionResDto.nombre}</span>
                  </Link>
                </div>
              </div>
              <div className="text row">
                <Link to={`/noticia/${val.titulo}/${val.noticiaId}`}>
                  <h1>{val.titulo}</h1>
                </Link>
                <div className="fecha">
                  <i className="fas fa-calendar-days"></i>
                  <label>{new Date().toLocaleDateString()}</label>
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>
    </section>
  );
}

