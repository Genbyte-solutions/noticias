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

  console.log(autorNoticias, "estas son todas las noticias");
  return (
    <>
      <SinglePageSlider />
      <main className="seccion-page">
        <div className="encabezado">
          <h1 className="titulo-encabezado">{autorName + " " + apellido}</h1>
        </div>
          <h2>Noticias redactadas : {autorNoticias.length} </h2>
        <div className="todasLasNoticias">
          <ul>
            {autorNoticias.map((noticia) => (
              <li key={noticia.id}>
                <h2>{noticia.titulo.slice(0, 50)}</h2>
                <p>{noticia.subtitulo.slice(0, 250)}...</p>
                <p>Publicado en : </p>
                <h3>{noticia.seccionResDto.nombre}</h3>

              </li>
            ))}
          </ul>
        <Side className= "side" />
        </div>
      </main>
    </>
  );
}

export default AutorPage;
