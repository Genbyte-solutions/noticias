import { Link, useParams } from "react-router-dom";
import SeccionPaginas from "../../components/seccion/SeccionPaginas.jsx";
import SinglePageSlider from "../../components/singlePage/SinglePageSlider.jsx";
import { noticiasPorSeccion } from "../../service/noticia/Principales.js";
import "./seccion-page.css";
import { popular } from "../../service/noticia/Principales.js";
import { useEffect, useState } from "react";

export default function SeccionPage() {
  const [lista, setLista] = useState([]);
  const [portada, setPortada] = useState()
  const { seccion } = useParams();

  useEffect(() => {
    noticiasPorSeccion(seccion)
      .then(data => setLista(data))
  }, [seccion])

  window.scrollTo(0, 0);

  return (
    <>
      <SinglePageSlider />
      <main className="seccion-page">
        <div className="encabezado">
          <h1 className="titulo-encabezado">{seccion}</h1>
          <div>
            <h3>
              <Link to="/">iNICIO/</Link>
              {seccion}
            </h3>
          </div>
        </div>
      </main>
      <SeccionPaginas lista={lista} portada={portada} />
    </>
  );
}
