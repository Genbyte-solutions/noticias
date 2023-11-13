import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import Notification from "../../../../components/notificacion/Notificacion.jsx";
import { imagenPorIdSeccion } from "../../../../service/imagen/Imagen.js";
import { listaSecciones } from "../../../../service/seccion/Listar.js";
import "./lista-secciones.css";

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
          const iconoData = await imagenPorIdSeccion(seccion.id);
          iconosPorSeccion[seccion.id] = iconoData;
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

  const handleEliminarSeccion = (id) => {
    fetch(`http://localhost:8080/api/seccion/eliminar/${id}`, {
      method: "POST",
    })
      .then(async (response) => {
        if (response.ok) {
          const responseData = await response.text();
          setNotificationMessage(responseData);
          setShowNotification(true);
          // Actualizar la lista de secciones después de eliminar
          setSecciones((prevSecciones) =>
            prevSecciones.filter((seccion) => seccion.id !== id)
          );
        } else {
          setNotificationMessage(
            "Error, una o mas noticias utilizan esta seccion" +
              response.statusText
          );
          setShowNotification(true);
        }
      })
      .catch((error) => {
        setNotificationMessage(
          "Error al eliminar la seccion: " + error.message
        );
        setShowNotification(true);
      });
  };

  return (
    <div className="lista-secciones-container">
      <table className="lista-secciones-table">
        <thead>
          <tr>
            <th className="table-header">ID</th>
            <th className="table-header">Codigo</th>
            <th className="table-header">Nombre</th>
            <th className="table-header">Icono</th>
            <th className="table-header">Acciones</th>
          </tr>
        </thead>
        <tbody>
          {secciones.map((seccion) => (
            <tr key={seccion.id} className="border-seccion">
              <td className="table-body-seccion">{seccion.id}</td>
              <td className="table-body-seccion">{seccion.codigo}</td>
              <td className="table-body-seccion">{seccion.nombre}</td>
              <td className="table-body-seccion">
                {seccion.icono && iconos[seccion.id] && (
                  <img
                    src={[iconos[seccion.id]]}
                    alt="Foto"
                    className="icono-image "
                  />
                )}
              </td>
              <td className="button-seccion-td">
                <button
                  className="seccion-button"
                  onClick={() => handleEliminarSeccion(seccion.id)}
                >
                  Eliminar
                </button>
                <Link to={`/administrador/seccion/editar/${seccion.codigo}`}>
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
