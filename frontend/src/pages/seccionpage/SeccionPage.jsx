import { Link, useParams } from "react-router-dom";
import SeccionPaginas from "../../components/seccion/SeccionPaginas.jsx";
import SinglePageSlider from "../../components/singlePage/SinglePageSlider.jsx";
import { popular } from "../../service/noticia/Principales.js";
import "./seccion-page.css";

export default function SeccionPage() {
  /* const [lista, setLista] = useState(popular); */
  const { seccion } = useParams();

  window.scrollTo(0, 0);

  return (
    <>
      <SinglePageSlider />
      <main className="seccion-page">
        <div className="encabezado">
          <h1 className="titulo-encabezado">{seccion}</h1>
          <div>
            <h3>
              <Link to="/">Inicio / </Link>
              {seccion.toString()}
            </h3>
          </div>
        </div>
        <SeccionPaginas lista={popular} />
      </main>
    </>
  );
}
