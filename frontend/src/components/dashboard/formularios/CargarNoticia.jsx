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

  const [titulo, setTitulo] = useState("");
  const [subtitulo, setSubtitulo] = useState("");
  const [parrafos, setParrafos] = useState("");
  const [etiquetas, setEtiquetas] = useState("");
  const [idSeccion, setIdSeccion] = useState("");
  const [idAutor, setIdAutor] = useState("");
  const [portada, setPortada] = useState(null);
  const [imagenes, setImagenes] = useState([]);
  const [autores, setAutores] = useState([]);
  const [secciones, setSecciones] = useState([]);
  const [showNotification, setShowNotification] = useState(false);
  const [notificationMessage, setNotificationMessage] = useState("");

  const handleNuevoParrafoChange = (e) => {
    setParrafos(e.target.value);
  };

  const handleAgregarParrafo = () => {
    setParrafos(parrafos + "\n");
  };

  const handleNuevaEtiquetaChange = (e) => {
    setEtiquetas(e.target.value);
  };

  const handleAgregarEtiqueta = () => {
    setEtiquetas(etiquetas + " ");
  };

  const handleEliminarParrafo = (index) => {
    const updatedParrafos = parrafos.split("\n");
    updatedParrafos.splice(index, 1);
    setParrafos(updatedParrafos.join("\n"));
  };

  const handleEliminarEtiqueta = (index) => {
    const updatedEtiquetas = etiquetas.split(" ");
    updatedEtiquetas.splice(index, 1);
    setEtiquetas(updatedEtiquetas.join(" "));
  };

  const handlePortadaChange = (e) => {
    try {
      if (e.target.files && e.target.files.length > 0) {
        console.log("Portada seleccionada:", e.target.files[0]); // Agregar este console log
        setPortada(e.target.files[0]);
      } else {
        throw new Error("No se ha seleccionado ningún archivo de imagen.");
      }
    } catch (error) {
      console.error("Error al actualizar la portada:", error);
      setNotificationMessage(`Error al actualizar la portada: ${error.message}`);
      setShowNotification(true);
    }
  };
  
  const handleImagenesChange = (e) => {
    try {
      if (e.target.files && e.target.files.length > 0) {
        console.log("Imagen seleccionada:", e.target.files[0]); // Agregar este console log
        const newImages = [...imagenes];
        newImages.push(e.target.files[0]);
        setImagenes(newImages);
      } else {
        throw new Error("No se ha seleccionado ningún archivo de imagen.");
      }
    } catch (error) {
      console.error("Error al actualizar las imágenes:", error); // Registrar el error en la consola
      setNotificationMessage(`Error al actualizar las imágenes: ${error.message}`);
      setShowNotification(true);
    }
  };

  const handleSeccionChange = (e) => {
    setIdSeccion(e.target.value);
  };

  const handleAutorChange = (e) => {
  const selectedAutorId = e.target.value;
  setIdAutor(selectedAutorId);
};

  const handleNotificationClose = () => {
    setShowNotification(false);
    setNotificationMessage("");
  };

  const onSubmit = async (data) => {
    if (!data.titulo || !data.subtitulo) {
      setNotificationMessage("Por favor, completa el título y el subtítulo.");
      setShowNotification(true);
      return;
    }
  
    try {
      const formData = new FormData();
      formData.append("titulo", data.titulo);
      formData.append("subtitulo", data.subtitulo);
      formData.append("idSeccion", idSeccion);
      formData.append("idAutor", idAutor);
      formData.append("portada", portada);
      imagenes.forEach((imagen) => {
        formData.append("imagenes", imagen);
       
      });
      formData.append("parrafos", parrafos);
      formData.append("etiquetas", etiquetas);
      console.log("FormData enviado:", formData); // Agregar este console log
      const response = await axios.post(
        "http://localhost:8080/api/noticias",
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      );
  
      if (response.status !== 200) {
        const errorText = response.data;
        setNotificationMessage(`Error: ${errorText}`);
        setShowNotification(true);
      } else {
        const data = response.data;
        setNotificationMessage(data);
        setShowNotification(true);
      }
    } catch (error) {
      setNotificationMessage(`Error al enviar el formulario: ${error.message}`);
      setShowNotification(true);
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
              id="imagen"
              onChange={handleImagenesChange}
              name="imagen"
              accept="image/*"
              multiple
            />
            {errors.imagenes && (
              <span className="error-msg">{errors.imagenes.message}</span>
            )}
          </div>

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
                <option key={seccion.id} value={seccion.id}>
                  {seccion.nombre}
                </option>
              ))}
            </select>
            {errors.seccion && (
              <span className="error-msg">{errors.seccion.message}</span>
            )}
          </div>

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
                <option key={autor.id} value={autor.id}>
                  {autor.nombre}
                </option>
              ))}
            </select>
            {errors.autor && (
              <span className="error-msg">{errors.autor.message}</span>
            )}
          </div>

          <div>
            <label>Parrafos:</label>
            {parrafos.split("\n").map((parrafo, index) => (
              <div key={index}>
                <textarea
                  value={parrafo}
                  onChange={handleNuevoParrafoChange}
                  placeholder="Ingrese un párrafo"
                  className="textarea"
                  id="parrafo"
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

          <div>
            <label>Etiquetas:</label>
            {etiquetas.split(" ").map((etiqueta, index) => (
              <div key={index}>
                <input
                  type="text"
                  value={etiqueta}
                  onChange={handleNuevaEtiquetaChange}
                  placeholder="Ingrese una etiqueta"
                  className="textarea"
                  id="etiqueta"
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

          <div className="button-container">
            <button type="submit" className="send-button">
              Guardar Noticia
            </button>
            <div className="reset-button-container">
              <button id="reset-btn" className="reset-button" type="reset">
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
