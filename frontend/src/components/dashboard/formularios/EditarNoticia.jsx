import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { imagenPorIdNoticia } from "../../../service/imagen/Imagen.js";
import { noticiaPorId } from "../../../service/noticia/Principales.js";

function EditarNoticia() {
  const { id } = useParams();
  const [noticia, setNoticia] = useState({});
  const [portada, setPortada] = useState();

  useEffect(() => {
    imagenPorIdNoticia(id)
      .then((data) => {
        setPortada(data);
      })
      .catch((error) =>
        console.error("Error al obtener la portada de noticia: ", error)
      );
  }, [id]);

  useEffect(() => {
    noticiaPorId(id)
      .then((data) => {
        setNoticia(data);
      })
      .catch((error) => console.error("Error al obtener el autor: ", error));
  }, [id]);
  return (
    <section>
      <div>
        <h1>{noticia.titulo}</h1>
      </div>
      {portada && <img src={portada} alt="Portada" />}
    </section>
  );
}

export default EditarNoticia;
