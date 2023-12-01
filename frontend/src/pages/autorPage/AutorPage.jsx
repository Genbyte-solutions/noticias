import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import SinglePageSlider from "../../components/singlePage/SinglePageSlider.jsx";
import SeccionPaginas from "../../pages/seccionpage/SeccionPage.jsx";
import { popular } from "../../service/noticia/Principales.js";
import axios from "axios";
import Side from "../../components/home/sideContent/side/Side.jsx";
import "./autor-page.css"
function AutorPage() {
  const { autorName, apellido } = useParams();
  const [autorNoticias, setAutorNoticias] = useState([]);
  const [portadaNoticia, setPortada] = useState([])
  useEffect(() => {
    const fetchNews = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8080/api/v1/noticias/autor?nombre=${autorName}&apellido=${apellido}&offset=0&limit=1000`
        );
        // Aquí puedes usar la respuesta de la API (response.data) para actualizar el estado o realizar otras operaciones.
        setAutorNoticias(response.data);
      } catch (e) {
        console.log("error", e);
      }
    };

    fetchNews();
  }, [autorName, apellido]);

  useEffect(() => {
    const fetchData = async (id) => {
      try {
        const response = await axios.get(` http://localhost:8080/api/v1/portada/noticia/${id}`, { responseType: "arraybuffer" });
        const imageUrl = URL.createObjectURL(new Blob([response.data], { type: response.headers['content-type'] }));
        return imageUrl;
      } catch (error) {
        console.error("Error al obtener las portadas: ", error);
        throw error;
      }
    };

    const loadPortadas = async () => {
      try {
        const portadasPromises = autorNoticias.map(async (val) => {
          return fetchData(val.noticiaId);
        });

        const portadasUrls = await Promise.all(portadasPromises);

        setPortada(portadasUrls);
      } catch (error) {
        console.error("Error al cargar las portadas: ", error);
      }
    };

    setPortada([]);

    loadPortadas();
  }, [autorNoticias]);


  // console.log(autorNoticias, "estas son todas las noticias");
  return (
    <>
      <SinglePageSlider />
      <main className="seccion-page">
        <div className="encabezado">
          <h1 className="titulo-encabezado">AUTOR : {autorName + " " + apellido}</h1>
        </div>
        <h2>Noticias redactadas : {autorNoticias.length} </h2>
        <div className="todasLasNoticias">
          <ul>
            {autorNoticias.map((noticia, index) => (

              <li key={noticia.id}>
                <div>

                  <img src={portadaNoticia[index]} alt="" />

                </div>
                <div className="noticias">

                  <h2>{noticia.titulo}</h2>
                  <p>{noticia.subtitulo.slice(0, 100)}...</p>
                  <p>Publicado en : </p>
                  <h3>{noticia.seccionResDto.nombre}</h3>
                </div>

              </li>
            ))}
          </ul>
          {/* <Side className="side" /> */}
        </div>
      </main>
    </>
  );
}

export default AutorPage;
