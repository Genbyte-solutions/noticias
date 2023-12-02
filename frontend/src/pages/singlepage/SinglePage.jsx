import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import Side from "../../components/home/sideContent/side/Side.jsx";
import SinglePageSlider from "../../components/singlePage/SinglePageSlider.jsx";
import { principales } from "../../service/noticia/Principales.js";
import "./single-page.css";
import { FaFacebookF } from "react-icons/fa";
import { FaInstagram } from "react-icons/fa";
import { FaTwitter } from "react-icons/fa";
import { FaYoutube } from "react-icons/fa";
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
  }, [item])


  //TRAYENDO LAS IMAGENES DE LA NOTICIA (FALTA TERMINAR)

  useEffect(() => {
    const fetchImageNews = async () => {
      try {
        if (id) {
          const response = await axios.get(`http://localhost:8080/api/v1/imagenes/noticia/${id}`);
          let arrayDeImgs = response.data;

          const imagePromises = arrayDeImgs.map(async (img) => {
            console.log(img, "este es el url de cada imagen");
            const imageResponse = await axios.get(`http://localhost:8080/api/v1/imagen/noticia/${img}`, { responseType: "arraybuffer" });
            return URL.createObjectURL(new Blob([imageResponse.data], { type: imageResponse.headers['content-type'] }));
          });

          const imagenesResueltas = await Promise.all(imagePromises);
          setImagenNews(imagenesResueltas);
        }
      } catch (e) {
        console.log("error", e);
      }
    };

    fetchImageNews();
  }, [id]);

  console.log(imagenNews, "estas son las imagenes ");
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
                <img src={imagenAutor} alt="foto del autor" />
                <Link to={`/autor/${item.autorResDto.nombre}/${item.autorResDto.apellido}`}>
                  <p>{item.autorResDto.nombre}</p>
                </Link>
                <label htmlFor="">{new Date().toLocaleDateString()}</label>
              </div>

              <div className="social">
                <div className="socBox">
                  <FaFacebookF />
                </div>
                <div className="socBox">
                  <FaInstagram />
                </div>
                <div className="socBox twitter">
                  <FaTwitter />
                </div>
                <div className="socBox youtube">
                  <FaYoutube />
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

              {item.etiquetas.map((p, i) => (
                <p key={i} className="etiqueta">
                  <strong>#{p}</strong>
                </p>
              ))}

              {/* Primera imagen después de las etiquetas */}
              {imagenNews.length > 0 && (
                <img src={imagenNews[0]} alt={imagenNews[0]} />
              )}

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
         
              {/* Segunda imagen después del subtitulo */}
              {imagenNews.length > 1 && (
                <img src={imagenNews[1]} alt={imagenNews[1]} />
              )}

              <div className="sobre-autor">
                <div className="autor">
                  <div className="img__autor">
                    <img src={imagenAutor} alt="" />
                  </div>
                  <div className="texto">
                    <span>Más de</span>
                    <Link to={`/autor/${item.autorResDto.nombre}/${item.autorResDto.apellido}`}>
                      <p>{item.autorResDto.nombre + " " + item.autorResDto.apellido} </p>
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