import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import Notification from "../../../../components/notificacion/Notificacion.jsx";
import { imagenPorIdUsuario } from "../../../../service/imagen/Imagen.js";
import { listaUsuarios } from "../../../../service/usuario/Listar.js";
import "./lista-usuarios.css";
import axios from "axios";
function ListaUsuarios() {
  const [usuarios, setUsuarios] = useState([]);
  const [imagenes, setImagenes] = useState();
  const [showNotification, setShowNotification] = useState(false);
  const [notificationMessage, setNotificationMessage] = useState("");

  const handleNotificationClose = () => {
    setShowNotification(false);
    setNotificationMessage("");
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        const usuariosData = await listaUsuarios();
        setUsuarios(usuariosData);
      } catch (error) {
        console.error("Error fetching usuarios:", error);
      }
    };
    fetchData();
  }, [showNotification]);

  useEffect(() => {
    // Cargar las imágenes por cada usuario
    const cargarImagenes = async () => {
      const imagenesPorUsuario = {};
      for (const usuario of usuarios) {
        try {
          const imagenData = await imagenPorIdUsuario(usuario.usuarioId);
          // console.log(imagenData);
          imagenesPorUsuario[usuario.usuarioId] = imagenData;
        } catch (error) {
          console.error(
            `Error al obtener la imagen para el usuario ${usuario.usuarioId}:`,
            error
          );
        }
      }
      setImagenes(imagenesPorUsuario);
    };

    cargarImagenes();
  }, [usuarios]);

  const handleEliminarUsuario = async (id) => {
    try {
      const response = await axios.delete(
        `http://localhost:8080/api/v1/usuario/${id}`
      );
      if (response.status === 200) {
        // const responseData = await response.text();
        setNotificationMessage("Usuario eliminado con éxito");
        setShowNotification(true);
        // Actualizar la lista de usuarios después de eliminar
        setUsuarios((prevUsuarios) =>
          prevUsuarios.filter((usuario) => usuario.usuarioId !== id)
        );
      } else {
        setNotificationMessage(
          "Error al eliminar el usuario. Response not ok: " + response.status
        );
        setShowNotification(true);
      }
    } catch (error) {
      setNotificationMessage("Error al eliminar el usuario: " + error.message);
      setShowNotification(true);
    }
  };

  return (
    <div className="lista-usuarios-container">
      <table className="lista-usuarios-table">
        <thead>
          <tr>
            <th className="table-header">Nombre</th>
            <th className="table-header">Apellido</th>
            <th className="table-header">Usuario</th>
            <th className="table-header">Rol</th>
            <th className="table-header">Email</th>
            <th className="table-header">Telefono</th>
            <th className="table-header">Imagen</th>
            <th className="table-header">Acciones</th>
          </tr>
        </thead>
        <tbody>
          {usuarios.map((usuario) => (
            <tr key={usuario.usuarioId} className="border-usuarios">
              <td className="table-body-usuarios">{usuario.nombre}</td>
              <td className="table-body-usuarios">{usuario.apellido}</td>
              <td className="table-body-usuarios">{usuario.nombreUsuario}</td>
              <td className="table-body-usuarios">{usuario.rol}</td>
              <td className="table-body-usuarios">{usuario.email}</td>
              <td className="table-body-usuarios">{usuario.telefono}</td>
              <td className="table-body-usuarios">
                {/* {console.log(imagenes[usuario.usuarioId])} */}
                {imagenes[usuario.usuarioId] ? (
                  <img
                    src={[imagenes[usuario.usuarioId]]}
                    alt="Icono"
                    className="icono-image"
                  />
                ) : (
                  ""
                )}
              </td>
              <td className="button-usuarios-td">
                <button
                  className="usuarios-button"
                  onClick={() => handleEliminarUsuario(usuario.usuarioId)}
                  // usuario.usuarioId
                >
                  Eliminar
                </button>
                <Link to={`/administrador/usuario/editar/${usuario.usuarioId}`}>
                  <button className="usuarios-button">Editar</button>
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

export default ListaUsuarios;
