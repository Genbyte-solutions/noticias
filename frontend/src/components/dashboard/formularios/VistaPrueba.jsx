import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { imagenPorIdNoticia } from '../../../service/imagen/Imagen';
import { noticiaPorId } from '../../../service/noticia/Principales';

function VistaPrueba() {
    const { id } = useParams();
    const [noticia, setNoticia] = useState(null);
    const [imagen, setImagen] = useState('');

    useEffect(() => {
        // Obtener la imagen de la noticia
        imagenPorIdNoticia(id)
            .then(imageUrl => {
                setImagen(imageUrl);
            })
            .catch(error => {
                console.error('Error al obtener la portada de noticia: ', error);
                setImagen(null); // En caso de error, asegúrate de manejarlo adecuadamente.
            });
    }, [id]);

    useEffect(() => {
        // Obtener los detalles de la noticia
        noticiaPorId(id)
            .then(data => {
                setNoticia(data);
            })
            .catch(error => {
                console.error('Error al obtener la noticia: ', error);
                setNoticia(null); // En caso de error, asegúrate de manejarlo adecuadamente.
            });
    }, [id]);

    if (!noticia) {
        return <div>Cargando noticia...</div>;
    }

    return (
        <section className='flexCT'>
          <div className='form-container'>
            <div>
              <span className="titulo">Ver Noticia Prueba</span>
              {imagen ? (
                <img src={imagen} alt="Imagen de portada" className="imagen-portada" />
              ) : (
                <span>No Image Available</span> // Mostrar texto si no hay imagen
              )}
              <div className='titulo'>
                Título:
                <h3>{noticia.titulo}</h3>
              </div>
              <div className='titulo'>
                Subtítulo:
                <h3>{noticia.subtitulo}</h3>
              </div>
              <div className='titulo'>
                {noticia.parrafos && noticia.parrafos.map((parrafo, index) => (
                  <div key={index}>
                    {`Párrafo ${index + 1}:`}
                   <h3> { `${parrafo}`}</h3>
                  </div>
                ))}
              </div>
            </div>
          </div>
        </section>
      );
    }
    
    export default VistaPrueba;