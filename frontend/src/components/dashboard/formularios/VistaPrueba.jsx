import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { imagenPorIdNoticia } from '../../../service/imagen/Imagen';
import { noticiaPorId } from '../../../service/noticia/Principales';

function VistaPrueba() {
    const { id } = useParams();
    const [noticia, setNoticia] = useState({});
    const [imagen, setImagen] = useState();

    useEffect(() => {
        imagenPorIdNoticia(id)
            .then(data => {
                setImagen(data);
            })
            .catch(error => console.error('Error al obtener la portada de noticia: ', error));
    }, [id]);

    useEffect(() => {
        noticiaPorId(id)
            .then(data => {
                setNoticia(data);
            })
            .catch(error => console.error('Error al obtener el usuario: ', error));
    }, [id]);

    return (
        <section className='flexCT'>
            <div className='form-container'>
                <div>
                    <span className="titulo">Ver Noticia Prueba</span>
                    {imagen && <img src={imagen} alt="imagen" />}
                    <div>
                        Titulo
                        <h3 value={noticia.titulo} />
                    </div>
                    <div>
                        Subtitulo
                        <h3 value={noticia.subtitulo} />
                    </div>
                    <div>
                        {noticia.parrafos && noticia.parrafos.map((parrafo, index) => (
                            <div key={index}>
                                {`Parrafo ${index}`}
                                <p>{parrafo}</p>
                            </div>
                        ))}
                    </div>
                </div>
            </div>
        </section>
    )
}

export default VistaPrueba;