import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import Notification from "../../../../components/notificacion/Notificacion.jsx";
import { imagenPorIdSeccion } from "../../../../service/imagen/Imagen.js";
import { listaSecciones } from "../../../../service/seccion/Listar.js";
import "./lista-secciones.css";
import axios from "axios";

function ListaSecciones() {
  const [secciones, setSecciones] = useState([]);
  const [iconos, setIconos] = useState();
  const [showNotification, setShowNotification] = useState(false);
  const [notificationMessage, setNotificationMessage] = useState("");

  const handleNotificationClose = () => {
    setShowNotification(false);
    setNotificationMessage("");
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        const seccionesData = await listaSecciones();
        setSecciones(seccionesData);
      } catch (error) {
        console.error("Error fetching secciones:", error);
      }
    };

    fetchData();
  }, [showNotification]);

  useEffect(() => {
    const cargarIconos = async () => {
      const iconosPorSeccion = {};
      for (const seccion of secciones) {
        try {
          const iconoData = await imagenPorIdSeccion(seccion.seccionId);
          iconosPorSeccion[seccion.seccionId] = iconoData;
        } catch (error) {
          console.error(
            `Error al obtener el icono de la seccion ${seccion.nombre}:`,
            error
          );
        }
      }
      setIconos(iconosPorSeccion);
    };

    cargarIconos();
  }, [secciones]);

  const handleEliminarSeccion = async (id) => {
    try {
      const response = await axios.delete(`http://localhost:8080/api/v1/seccion/${id}`);
      if (response.status === 201) {
        setNotificationMessage("Sección eliminada con éxito");
        setSecciones(secciones.filter((seccion) => seccion.seccionId !== id));
      } else {
        setNotificationMessage("Error al eliminar la sección: " + response.statusText);
      }
    } catch (error) {
      setNotificationMessage("Error al eliminar la sección: " + error.message);
    }
    setShowNotification(true);
  };


  return (
    <div className="lista-secciones-container">
      <table className="lista-secciones-table">
        <thead>
          <tr>
            
            
            <th className="table-header">Nombre</th>
            <th className="table-header">Icono</th>
            <th className="table-header">Acciones</th>
          </tr>
        </thead>
        <tbody>
          {secciones.map((seccion) => (
            <tr key={seccion.seccionId} className="border-seccion">
             
             
              <td className="table-body-seccion">{seccion.nombre}</td>
              <td className="table-body-seccion">
                {iconos[seccion.seccionId] && (
                  <img
                    src={[iconos[seccion.seccionId]]}
                    alt="Foto"
                    className="icono-image"
                  />
                )}
              </td>
              <td className="button-seccion-td">
                <button
                  className="seccion-button"
                  onClick={() => handleEliminarSeccion(seccion.seccionId)}
                >
                  Eliminar
                </button>
                <Link to={`/administrador/seccion/editar/${seccion.seccionId}`}>
                  <button className="seccion-button">Editar</button>
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

export default ListaSecciones;
