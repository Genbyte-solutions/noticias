import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import Heading from '../../../../components/heading/Heading.jsx';

export default function Recientes() {
  const [recientes, setRecientes] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/noticias/recientes');
        setRecientes(response.data); // Establecer los datos obtenidos de la API
      } catch (error) {
        console.error('Error al obtener las noticias recientes: ', error);
      }
    };
    fetchData();
  }, []);

  return (
    <section className="Recientes">
      <Heading title="Popular" />
      <div className="noticias-container">
        {recientes.slice(0, 4).map((val, index) => (
          <div key={index} className="noticia">
            <div className="box shadow">
              <div className="images row">
                <div className="img">
                  <img src={`http://localhost:8080/api/noticias/${val.id}/portada`} alt="" />
                </div>
                <div className="categoria categoria1">
                  <Link to={`/seccion/${val.seccion.nombre}`}>
                    <span>{val.seccion.nombre}</span>
                  </Link>
                </div>
              </div>
              <div className="text row">
                <Link to={`/noticia/${val.titulo}`}>
                  <h1>{val.titulo}</h1>
                </Link>
                <div className="fecha">
                  <i className="fas fa-calendar-days"></i>
                  <label>{new Date(val.fechaPublicacion).toLocaleDateString()}</label>
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>
    </section>
  );
}
