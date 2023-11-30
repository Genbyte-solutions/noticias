import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import Side from "../../components/home/sideContent/side/Side.jsx";
import SinglePageSlider from "../../components/singlePage/SinglePageSlider.jsx";
import { principales } from "../../service/noticia/Principales.js";
import "./single-page.css";
import axios from "axios";
import { imagenPorId } from "../../service/imagen/Imagen.js";

export default function SinglePage() {
  const { titulo, id } = useParams();
  const [item, setItem] = useState();
  const [imagenAutor, setImagenAutor] = useState("")
  const [imagenNews, setImagenNews] = useState([])

  //TRAYENDO LA NOTICIA POR EL ID
  useEffect(() => {
    const fetchNoticia = async () => {
      try {
        if (id) {
          const response = await axios.get(`http://localhost:8080/api/v1/noticia/${id}`);
          setItem(response.data);
        }
      } catch (error) {
        console.error(error);
      }
    };

    fetchNoticia();
  }, [id]);

  //TRAYENDO LA IMAGEN DEL AUTOR

  useEffect(() => {
    try {
      const fetchUrl = async () => {
        const response = await axios.get(`http://localhost:8080/api/v1/imagen/autor/${item.autorResDto.autorId}`, { responseType: "arraybuffer" })
        const imageUrl = URL.createObjectURL(new Blob([response.data], { type: response.headers['content-type'] }));

        setImagenAutor(imageUrl)
      }
      fetchUrl()
    } catch (e) {
      console.log(e);
    }
  })


  //TRAYENDO LAS IMAGENES DE LA NOTICIA (FALTA TERMINAR)

  useEffect(() => {
    const fetchImageNews = async () => {
      try {
        if (item && item.noticiaId) {
          const response = await axios.get(`http://localhost:8080/api/v1/imagenes/noticia/${item.noticiaId}`);
          let arrayDeImgs = response.data

          const imagePromises = arrayDeImgs.map(async (img) => {
            const imageResponse = await axios.get(`http://localhost:8080/api/v1/imagen/noticia/${img}`);
            return URL.createObjectURL(new Blob([imageResponse.data], { type: imageResponse.headers['content-type'] }));
          });

          // Esperar a que todas las promesas se resuelvan
          const imagenesResueltas = await Promise.all(imagePromises);

          // Ahora puedes establecer el estado con todas las imágenes resueltas
          setImagenNews(imagenesResueltas);
        }
      } catch (e) {
        console.log("error", e);
      }
    };

    fetchImageNews();
  }, [item]);

// console.log(item.autorResDto.apellido, "esto es item");
  return (
    <>
      {item ? (
        <main className="singlePage">
          <SinglePageSlider />
          <div className="container">
            <section className="mainContent info">
              <h1 className="titulo">{item.titulo}</h1>
              <div className="autor">
                <span>por</span>
                <img src={imagenAutor} alt="aqui va la foto del autor" />
                <Link to={`/autor/${item.autorResDto.nombre}/${item.autorResDto.apellido}`}>
                  <p>{item.autorResDto.nombre}</p>
                </Link>
                <label htmlFor="">{new Date().toLocaleDateString()}</label>
              </div>

              <div className="social">
                <div className="socBox">
                  <i className="fab fa-facebook-f"></i>
                </div>
                <div className="socBox">
                  <i className="fab fa-instagram"></i>
                </div>
                <div className="socBox">
                  <i className="fab fa-twitter"></i>
                </div>
                <div className="socBox">
                  <i className="fab fa-youtube"></i>
                </div>
              </div>
              <div className="descTop">
                {item.parrafos.map((elemento, id) =>
                  id < 2 ? (
                    <p key={id}>
                      {elemento}
                    </p>
                  ) : null
                )}
              </div>

              {/* AQUI ES DONDE SE TIENE QUE VER LA IMAGEN  */}
              {imagenNews.map((img, index) => 
              
              (
                
                <img src={img}  key={index}  alt="noticia" />
             ))}

              {/*   /////////////////////////////////////////////////   */}
              <div className="descBot">
                <h1>{item.titulo}</h1>
                <p>{item.parrafos[0]}</p>
              </div>
              {item.subtitulo ? (
                <div className="quote">
                  <i className="fa fa-quote-left"></i>
                  <p>
                    {item.subtitulo}
                  </p>
                </div>
              ) : (
                <></>
              )}
              <div className="descTop">
                {item.parrafos.map((elemento, id) =>
                  id > 1 ? (
                    <p key={id}>
                      {elemento}

                    </p>
                  ) : null
                )}
              </div>
              <div className="sobre-autor">
                <div className="autor">
                  <img src={imagenAutor} alt="" />
                  <div className="texto">
                    <span>Más de</span>
                    <Link to={`/autor/${item.autorResDto.nombre}/${item.autorResDto.apellido}`}>
                      <p>{item.autorResDto.nombre}</p>
                    </Link>
                  </div>
                </div>
              </div>
            </section>
            <section className="sideContent">
              <Side />
            </section>
          </div>
        </main>
      ) : (
        <>
          <SinglePageSlider />
          <h1 className="error">La noticia no está disponible</h1>
        </>
      )}
    </>
  );
}
