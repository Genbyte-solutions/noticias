import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { listaAutores } from "../../../../service/autor/Listar.js";
import { imagenPorIdAutor } from "../../../../service/imagen/Imagen.js";
import Notification from "../../../notificacion/Notificacion.jsx";
import "./lista-autores.css";

function ListaAutores() {
  const [autores, setAutores] = useState([]);
  const [fotos, setFotos] = useState();
  const [showNotification, setShowNotification] = useState(false);
  const [notificationMessage, setNotificationMessage] = useState("");

  const handleNotificationClose = () => {
    setShowNotification(false);
    setNotificationMessage("");
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        const autoresData = await listaAutores();
        setAutores(autoresData);
      } catch (error) {
        console.error("Error fetching autores:", error);
      }
    };

    fetchData();
  }, [showNotification]);

  useEffect(() => {
    const cargarFotos = async () => {
      const fotosPorAutor = {};
      for (const autor of autores) {
        try {
          const fotoData = await imagenPorIdAutor(autor.id);
          fotosPorAutor[autor.id] = fotoData;
        } catch (error) {
          console.error(
            `Error al obtener la foto para el autor ${autor.id}:`,
            error
          );
        }
      }
      setFotos(fotosPorAutor);
    };

    cargarFotos();
  }, [autores]);

  const handleEliminarAutor = (id) => {
    fetch(`http://localhost:8080/api/autor/eliminar/${id}`, {
      method: "POST",
    })
      .then(async (response) => {
        if (response.ok) {
          const responseData = await response.text();
          setNotificationMessage(responseData);
          setShowNotification(true);
          setAutores((prevAutores) =>
            prevAutores.filter((autor) => autor.id !== id)
          );
        } else {
          setNotificationMessage(
            "Error, una o mas noticias utilizan este autor" +
              response.statusText
          );
          setShowNotification(true);
        }
      })
      .catch((error) => {
        setNotificationMessage("Error al eliminar el autor: " + error.message);
        setShowNotification(true);
      });
  };

  return (
    <div className="lista-autores-container">
      <table className="lista-autores-table">
        <thead>
          <tr>
            <th className="table-header">ID</th>
            <th className="table-header">Nombre</th>
            <th className="table-header">Apellido</th>
            <th className="table-header">Foto</th>
            <th className="table-header">Accion</th>
          </tr>
        </thead>
        <tbody>
          {autores.map((autor) => (
            <tr key={autor.id} className="border-autor">
              <td className="table-body-seccion">{autor.id}</td>
              <td className="table-body-seccion">{autor.nombre}</td>
              <td className="table-body-seccion">{autor.apellido}</td>
              <td className="table-body-seccion">
                {autor.foto && fotos[autor.id] && (
                  <img
                    src={[fotos[autor.id]]}
                    alt="Foto"
                    className="icono-image"
                  />
                )}
              </td>
              <td className="button-autor-td">
                <button
                  className="autor-button"
                  onClick={() => handleEliminarAutor(autor.id)}
                >
                  Eliminar
                </button>
                <Link to={`/administrador/autor/editar/${autor.id}`}>
                  <button className="autor-button">Editar</button>
                </Link>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      {showNotification && (
        <Notification
          message={notificationMessage}
          onClose={handleNotificationClose}
        />
      )}
    </div>
  );
}

export default ListaAutores;
