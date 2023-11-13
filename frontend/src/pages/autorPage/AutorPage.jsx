import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import SinglePageSlider from "../../components/singlePage/SinglePageSlider.jsx";
import SeccionPaginas from "../../pages/seccionpage/SeccionPage.jsx";
import { popular } from "../../service/noticia/Principales.js";

function AutorPage() {
  const [listas /* , setLista */] = useState(popular);
  const { autorName } = useParams();
  const [autorNoticias, setAutorNoticias] = useState([]);

  useEffect(() => {
    const autorNoticias = listas.filter(
      (noticia) => noticia.autor === autorName
    );
    window.scrollTo(0, 0);
    if (autorNoticias) {
      setAutorNoticias(autorNoticias);
    }
  }, [listas, autorName]);

  return (
    <>
      <SinglePageSlider />
      <main className="seccion-page">
        <div className="encabezado">
          <h1 className="titulo-encabezado">{autorName}</h1>
        </div>
        <SeccionPaginas lista={autorNoticias} />
      </main>
    </>
  );
}

export default AutorPage;
