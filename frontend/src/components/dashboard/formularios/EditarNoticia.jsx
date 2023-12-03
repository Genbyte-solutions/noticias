import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { useForm } from "react-hook-form";
import axios from "axios";
import { noticiaPorId } from "../../../service/noticia/Principales";
import { imagenPorIdNoticia } from "../../../service/imagen/Imagen";
import { listaAutores } from "../../../service/autor/Listar";
import { listaSecciones } from "../../../service/seccion/Listar";
import Notification from "../../../components/notificacion/Notificacion";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faFileImage } from "@fortawesome/free-solid-svg-icons";

function EditarNoticia() {
  const { id } = useParams();
  const {
    register,
    handleSubmit,
    setValue,
    formState: { errors },
  } = useForm();
  const [showNotification, setShowNotification] = useState(false);
  const [notificationMessage, setNotificationMessage] = useState("");
  const [noticia, setNoticia] = useState({});
  const [portada, setPortada] = useState();
  const [parrafos, setParrafos] = useState([]);
  const [etiquetas, setEtiquetas] = useState([]);
  const [secciones, setSecciones] = useState([]);
  const [autores, setAutores] = useState([]);
  const [idSeccion, setIdSeccion] = useState("");
  const [idAutor, setIdAutor] = useState("");

  const handleReset = () => {
    // Restablece los estados a sus valores iniciales
    setNoticia({});
    setPortada(null);
    setParrafos([]);
    setEtiquetas([]);
    setIdSeccion("");
    setIdAutor("");

    // Restablece los valores de los campos del formulario
    setValue("titulo", "");
    setValue("subtitulo", "");
    setValue("portada", null);
  };
  useEffect(() => {
    noticiaPorId(id)
      .then((data) => {
        setNoticia(data);
        setValue("titulo", data.titulo);
        setValue("subtitulo", data.subtitulo);
        setParrafos(data.parrafos || []);
        setEtiquetas(data.etiquetas || []);
        setIdSeccion(data.seccionId);
        setIdAutor(data.autorId);
      })
      .catch((error) => console.error("Error al obtener la noticia: ", error));
  }, [id, setValue]);

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
    listaAutores()
      .then((data) => setAutores(data))
      .catch((error) =>
        console.error("Error al obtener la lista de autores: ", error)
      );

    listaSecciones()
      .then((data) => setSecciones(data))
      .catch((error) =>
        console.error("Error al obtener la lista de secciones: ", error)
      );
  }, []);

  const onSubmit = async (data) => {
    const formData = new FormData();
    formData.append("titulo", data.titulo);
    formData.append("subtitulo", data.subtitulo);
    parrafos.forEach((parrafo) => formData.append("parrafos", parrafo));
    etiquetas.forEach((etiqueta) => formData.append("etiquetas", etiqueta));
    formData.append("seccionId", idSeccion);
    formData.append("autorId", idAutor);

    if (data.portada && data.portada.length > 0) {
      formData.append("portada", data.portada[0]);
    }
    console.log(data);
    for (let [key, value] of formData.entries()) {
      console.log(key, value);
    }
    try {
      const response = await axios.put(
        `http://localhost:8080/api/v1/noticia/${id}`,
        formData
      );
      setShowNotification(true);
      setNotificationMessage("Noticia actualizada con éxito");
    } catch (error) {
      console.error("Error al actualizar la noticia: ", error);
      setShowNotification(true);
      setNotificationMessage("Error al actualizar la noticia");
    }
  };
  // Funciones para manejar los párrafos
  const handleAgregarParrafo = () => {
    setParrafos([...parrafos, ""]);
  };

  const handleNuevoParrafoChange = (e, index) => {
    const nuevosParrafos = [...parrafos];
    nuevosParrafos[index] = e.target.value;
    setParrafos(nuevosParrafos);
  };

  const handleEliminarParrafo = (index) => {
    const nuevosParrafos = parrafos.filter((_, i) => i !== index);
    setParrafos(nuevosParrafos);
  };

  // Funciones para manejar las etiquetas
  const handleAgregarEtiqueta = () => {
    setEtiquetas([...etiquetas, ""]);
  };

  const handleNuevaEtiquetaChange = (e, index) => {
    const nuevasEtiquetas = [...etiquetas];
    nuevasEtiquetas[index] = e.target.value;
    setEtiquetas(nuevasEtiquetas);
  };

  const handleEliminarEtiqueta = (index) => {
    const nuevasEtiquetas = etiquetas.filter((_, i) => i !== index);
    setEtiquetas(nuevasEtiquetas);
  };

  const handleSeccionChange = (e) => {
    setIdSeccion(e.target.value);
  };

  const handleAutorChange = (e) => {
    setIdAutor(e.target.value);
  };
  return (
    <section className="flexCT">
      <div className="form-container">
        <form onSubmit={handleSubmit(onSubmit)} className="form">
          <span className="titulo">Editar Noticia</span>

          {/* Título */}
          <div>
            <label htmlFor="titulo">Título</label>
            <input
              type="text"
              id="titulo"
              {...register("titulo", { required: "El título es requerido" })}
              className="input"
            />
            {errors.titulo && (
              <span className="error-msg">{errors.titulo.message}</span>
            )}
          </div>

          {/* Subtítulo */}
          <div>
            <label htmlFor="subtitulo">Subtítulo</label>
            <input
              type="text"
              id="subtitulo"
              {...register("subtitulo", {
                required: "El subtítulo es requerido",
              })}
              className="input"
            />
            {errors.subtitulo && (
              <span className="error-msg">{errors.subtitulo.message}</span>
            )}
          </div>

          {/* Portada */}
          <div>
            <label className="cargar-archivo input" htmlFor="portada">
              <div className="icono">
                <FontAwesomeIcon icon={faFileImage} />
              </div>
              <div className="texto">
                <span>Subir portada</span>
              </div>
            </label>
            <input
              type="file"
              id="portada"
              {...register("portada")}
              className="input"
            />
          </div>

          {/* Imágenes adicionales */}
          <div>
            <label className="cargar-archivo input" htmlFor="imagenes">
              <div className="icono">
                <FontAwesomeIcon icon={faFileImage} />
              </div>
              <div className="texto">
                <span>Imagenes extra</span>
              </div>
            </label>
            <input
              type="file"
              id="imagenes"
              {...register("imagenes")}
              className="input"
              multiple
            />
          </div>
          {/* Sección */}
          <div>
            <label htmlFor="seccion">Seleccionar Sección:</label>
            <select
              id="seccion"
              {...register("seccion", {
                required: "Por favor, selecciona una sección.",
              })}
              value={idSeccion}
              onChange={handleSeccionChange}
              className="input"
            >
              <option value="">Seleccionar Sección</option>
              {secciones.map((seccion) => (
                <option key={seccion.id} value={seccion.seccionId}>
                  {seccion.nombre}
                </option>
              ))}
            </select>
            {errors.seccion && (
              <span className="error-msg">{errors.seccion.message}</span>
            )}
          </div>

          {/* Autor */}
          <div>
            <label htmlFor="autor">Seleccionar Autor:</label>
            <select
              id="autor"
              {...register("autor", {
                required: "Por favor, selecciona un autor.",
              })}
              value={idAutor}
              onChange={handleAutorChange}
              className="input"
            >
              <option value="">Seleccionar Autor</option>
              {autores.map((autor) => (
                <option key={autor.id} value={autor.autorId}>
                  {autor.nombre} {autor.apellido}
                </option>
              ))}
            </select>
            {errors.autor && (
              <span className="error-msg">{errors.autor.message}</span>
            )}
          </div>

          {/* Parrafos */}
          <div>
            <label>Parrafos:</label>
            {parrafos.map((parrafo, index) => (
              <div key={index}>
                <textarea
                  value={parrafo}
                  onChange={(e) => handleNuevoParrafoChange(e, index)}
                  className="textarea"
                  placeholder="Ingrese un párrafo"
                  id={`parrafo-${index}`}
                />
                <button
                  type="button"
                  className="eliminar-parrafo-button"
                  onClick={() => handleEliminarParrafo(index)}
                >
                  Eliminar Párrafo
                </button>
              </div>
            ))}
            <button
              type="button"
              className="agregar-parrafo-button"
              onClick={handleAgregarParrafo}
            >
              Agregar Párrafo
            </button>
          </div>

          {/* Etiquetas */}
          <div>
            <label>Etiquetas:</label>
            {etiquetas.map((etiqueta, index) => (
              <div key={index}>
                <textarea
                  type="text"
                  value={etiqueta}
                  onChange={(e) => handleNuevaEtiquetaChange(e, index)}
                  className="textarea"
                  placeholder="Ingrese una etiqueta"
                  id={`etiqueta-${index}`}
                />
                <button
                  type="button"
                  className="eliminar-etiqueta-button"
                  onClick={() => handleEliminarEtiqueta(index)}
                >
                  Eliminar Etiqueta
                </button>
              </div>
            ))}
            <button
              type="button"
              className="agregar-etiqueta-button"
              onClick={handleAgregarEtiqueta}
            >
              Agregar Etiqueta
            </button>
          </div>
          {/* Botón de envío */}
          <div className="button-container">
            <button type="submit" className="send-button">
              Guardar Cambios
            </button>

            <div className="reset-button-container">
              <button
                id="reset-btn"
                className="reset-button"
                type="button"
                onClick={handleReset}
              >
                Resetear
              </button>
            </div>
          </div>
        </form>
      </div>
      {showNotification && (
        <Notification
          message={notificationMessage}
          onClose={() => setShowNotification(false)}
        />
      )}
    </section>
  );
}

export default EditarNoticia;
