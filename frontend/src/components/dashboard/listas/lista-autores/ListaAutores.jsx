import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { listaAutores } from "../../../../service/autor/Listar.js";
import { imagenPorIdAutor } from "../../../../service/imagen/Imagen.js";
import Notification from "../../../notificacion/Notificacion.jsx";
import "./lista-autores.css";
import axios from "axios";

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
          const fotoData = await imagenPorIdAutor(autor.autorId);
          fotosPorAutor[autor.autorId] = fotoData;
        } catch (error) {
          console.error(
            `Error al obtener la foto para el autor ${autor.autorId}:`,
            error
          );
        }
      }
      setFotos(fotosPorAutor);
    };

    cargarFotos();
  }, [autores]);

  const handleEliminarAutor = (id) => {
    axios.delete(`http://localhost:8080/api/v1/autor/${id}`)
      .then(async (response) => {
        if (response.status === 204) {
          // const responseData = await response.text();
          setNotificationMessage('Usuario eliminado correctamente');
          setShowNotification(true);
          setAutores((prevAutores) =>
            prevAutores.filter((autor) => autor.autorId !== id)
          );
        } else {
          setNotificationMessage(
            "Error, una o mas noticias utilizan este autor" +
            response.status
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
            
            <th className="table-header">Nombre</th>
            <th className="table-header">Apellido</th>
            <th className="table-header">Foto</th>
            <th className="table-header">Accion</th>
          </tr>
        </thead>
        <tbody>
          {autores.map((autor) => (
            <tr key={autor.autorId} className="border-autor">
              
              <td className="table-body-seccion">{autor.nombre}</td>
              <td className="table-body-seccion">{autor.apellido}</td>
              <td className="table-body-seccion">
                {fotos[autor.autorId] && (
                  <img
                    src={fotos[autor.autorId]}
                    alt="Foto"
                    className="icono-image"
                  />
                )}
              </td>
              <td className="button-autor-td">
                <button
                  className="autor-button"
                  onClick={() => handleEliminarAutor(autor.autorId)}
                >
                  Eliminar
                </button>
                <Link to={`/administrador/autor/editar/${autor.autorId}`}>
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
