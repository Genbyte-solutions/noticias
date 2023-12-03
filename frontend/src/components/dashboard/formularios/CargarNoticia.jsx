/* eslint-disable no-unused-vars */
import { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import Notification from "../../../components/notificacion/Notificacion";
import { listaAutores } from "../../../service/autor/Listar.js";
import { listaSecciones } from "../../../service/seccion/Listar.js";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faFileImage } from "@fortawesome/free-solid-svg-icons";
import axios from "axios";

const CargarNoticia = () => {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();
  const handleReset = () => {
    setTitulo("");
    setSubtitulo("");
    setParrafos([]);
    setEtiquetas([]);
    setIdSeccion("");
    setIdAutor("");
    setPortada(null);
    setImagenes([]);
    // Restablecer otros estados si es necesario
  };
  
  const [titulo, setTitulo] = useState("");
  const [subtitulo, setSubtitulo] = useState("");
  const [parrafos, setParrafos] = useState([]);
  const [etiquetas, setEtiquetas] = useState([]);
  const [idSeccion, setIdSeccion] = useState("");
  const [idAutor, setIdAutor] = useState("");
  const [portada, setPortada] = useState(null);
  const [imagenes, setImagenes] = useState([]);
  const [autores, setAutores] = useState([]);
  const [secciones, setSecciones] = useState([]);
  const [showNotification, setShowNotification] = useState(false);
  const [notificationMessage, setNotificationMessage] = useState("");

  const handleNuevoParrafoChange = (e, index) => {
    const nuevosParrafos = [...parrafos];
    nuevosParrafos[index] = e.target.value;
    setParrafos(nuevosParrafos);
  };

  const handleAgregarParrafo = () => {
    setParrafos([...parrafos, ""]);
  };

  const handleEliminarParrafo = (index) => {
    const nuevosParrafos = parrafos.filter((_, i) => i !== index);
    setParrafos(nuevosParrafos);
  };

  const handleNuevaEtiquetaChange = (e, index) => {
    const nuevasEtiquetas = [...etiquetas];
    nuevasEtiquetas[index] = e.target.value;
    setEtiquetas(nuevasEtiquetas);
  };

  const handleAgregarEtiqueta = () => {
    setEtiquetas([...etiquetas, ""]);
  };

  const handleEliminarEtiqueta = (index) => {
    const nuevasEtiquetas = etiquetas.filter((_, i) => i !== index);
    setEtiquetas(nuevasEtiquetas);
  };

  const handlePortadaChange = (e) => {
    if (e.target.files && e.target.files[0]) {
      setPortada(e.target.files[0]);
    }
  };

  const handleImagenesChange = (e) => {
    if (e.target.files) {
      const nuevasImagenes = Array.from(e.target.files);
      setImagenes([...imagenes, ...nuevasImagenes]);
    }
  };

  const handleSeccionChange = (e) => {
    setIdSeccion(e.target.value);
  };

  const handleAutorChange = (e) => {
    setIdAutor(e.target.value);
  };

  const handleNotificationClose = () => {
    setShowNotification(false);
    setNotificationMessage("");
  };

  const onSubmit = async (data) => {
    if (!titulo || !subtitulo) {
      setNotificationMessage("Por favor, completa el título y el subtítulo.");
      setShowNotification(true);
      return;
    }

    const formData = new FormData();
    formData.append("titulo", titulo);
    formData.append("subtitulo", subtitulo);
    formData.append("seccionId", idSeccion);
    formData.append("autorId", idAutor);
    formData.append("portada", portada);

    parrafos.forEach((parrafo) => formData.append("parrafos", parrafo));
    etiquetas.forEach((etiqueta) => formData.append("etiquetas", etiqueta));
    imagenes.forEach((imagenes) =>
      formData.append(`imagenes`, imagenes)
    );

    //console.log(data)
    /*for (let [key, value] of formData.entries()) {
      console.log(key, value);
    }*/

    try {
      const response = await axios.post(
        "http://localhost:8080/api/v1/noticia",
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      );

      if (response.status !== 201) {
        setNotificationMessage(`Error: ${response.statusText}`);
      
      } else {
        setNotificationMessage("Noticia guardada con éxito");
      }
      setShowNotification(true);
    } catch (error) {
      setNotificationMessage(`Error al enviar el formulario: ${error.response.data.mensaje}`);
      setShowNotification(true);
      console.log(error);
    }
  };

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

  return (
    <section className="flexCT">
      <div className="form-container">
        <form onSubmit={handleSubmit(onSubmit)} className="form">
          <span className="titulo">Cargar Noticia</span>

          {/* Título */}
          <div>
            <label htmlFor="titulo">Título</label>
            <input
              type="text"
              id="titulo"
              {...register("titulo", {
                required: "Por favor, completa este campo.",
              })}
              className="input"
              value={titulo}
              onChange={(e) => setTitulo(e.target.value)}
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
                required: "Por favor, completa este campo.",
              })}
              className="input"
              value={subtitulo}
              onChange={(e) => setSubtitulo(e.target.value)}
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
              name="portada"
              accept="image/*"
              onChange={handlePortadaChange}
            />
            {errors.portada && (
              <span className="error-msg">{errors.portada.message}</span>
            )}
          </div>

          {/* Imágenes */}
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
              onChange={handleImagenesChange}
              name="imagenes"
              accept="image/*"
              multiple
            />
            {errors.imagenes && (
              <span className="error-msg">{errors.imagenes.message}</span>
            )}
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
                  placeholder="Ingrese un párrafo"
                  className="textarea"
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
                <input
                  type="text"
                  value={etiqueta}
                  onChange={(e) => handleNuevaEtiquetaChange(e, index)}
                  placeholder="Ingrese una etiqueta"
                  className="textarea"
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

          {/* Botones de envío y reset */}
          <div className="button-container">
            <button type="submit" className="send-button">
              Guardar Noticia
            </button>
            <div className="reset-button-container">
            <button id="reset-btn" className="reset-button" type="button" onClick={handleReset}>
  Resetear
</button>

            </div>
          </div>
        </form>
      </div>
      {showNotification && (
        <Notification
          message={notificationMessage}
          onClose={handleNotificationClose}
        />
      )}
    </section>
  );
};
export default CargarNoticia;
