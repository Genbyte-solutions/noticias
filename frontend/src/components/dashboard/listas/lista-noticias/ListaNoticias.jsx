import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import axios from "axios";
import Notification from "../../../../components/notificacion/Notificacion.jsx";
import "./lista-noticias.css";

function ListaNoticias() {
  const [noticias, setNoticias] = useState([]);
  const [portadas, setPortadas] = useState({});
  const [showNotification, setShowNotification] = useState(false);
  const [notificationMessage, setNotificationMessage] = useState("");

  useEffect(() => {
    const fetchData = async () => {
      try {
        const offset = 0;
        const limit = 10;
        

        const response = await axios.get(`http://localhost:8080/api/v1/noticias?offset=${offset}&limit=${limit}`);
        const noticiasData = response.data;

        setNoticias(noticiasData);

        // Cargar las portadas en base64 para cada noticia
        noticiasData.forEach(async (noticia) => {
          try {
            const portadaResponse = await axios.get(`http://localhost:8080/api/v1/portada/noticia/${noticia.noticiaId}`, { responseType: 'blob' });
            const portadaBlob = portadaResponse.data;
            const reader = new FileReader();
            reader.readAsDataURL(portadaBlob);
            reader.onloadend = () => {
              const base64data = reader.result;
              setPortadas(prevPortadas => ({ ...prevPortadas, [noticia.noticiaId]: base64data }));
            };
          } catch (error) {
            console.error(`Error al cargar portada de noticia ${noticia.noticiaId}:`, error);
          }
        });
      } catch (error) {
        console.error("Error al cargar noticias:", error);
      }
    };
    fetchData();
  }, [showNotification]);

  const handleEliminarNoticia = async (id) => {
    try {
      const response = await axios.delete(`http://localhost:8080/api/v1/noticia/${id}`);
      setNotificationMessage(response.data.mensaje);
      setShowNotification(true);
      setNoticias(noticias.filter(noticia => noticia.id !== id));
    } catch (error) {
      setNotificationMessage(`Error al eliminar la noticia: ${error.message}`);
      setShowNotification(true);
    }
  };
 
  return (
    <div className="lista-noticias-container">
      <table className="lista-noticias-table">
        <thead>
          <tr>
            <th className="table-header">Título</th>
            <th className="table-header">Portada</th>
            <th className="table-header">Sección</th>
            <th className="table-header">Autor</th>
            <th className="table-header">Párrafos</th>
            <th className="table-header">Etiquetas</th>
            <th className="table-header">Acciones</th>
          </tr>
        </thead>
        
        <tbody>
          {noticias.map((noticia) => (
            <tr key={noticia.noticiaId} className="border-noticia">
              <td className="table-body">{noticia.titulo}</td>
              <td className="table-body">
              {portadas[noticia.noticiaId] ? (
                  <img src={portadas[noticia.noticiaId]} alt="Portada" className="icono-image" />
                ) : (
                  <span>No Image</span> // Mostrar texto si no hay imagen
                )}
              </td>
              <td className="table-body">{noticia.seccionResDto.nombre}</td>
              <td className="table-body">
                {noticia.autorResDto.nombre} {noticia.autorResDto.apellido}
              </td>
              <td className="table-body">{noticia.parrafos.join('. ')}</td>
              <td className="table-body">{noticia.etiquetas.join('. ')}</td>
              <td className="button-noticia-td">
                <button
                  className="button-noticia"
                  onClick={() => handleEliminarNoticia(noticia.noticiaId)}
                >
                  Eliminar
                </button>
                <Link to={`/administrador/noticia/editar/${noticia.noticiaId}`}>
                  <button className="button-noticia">Editar</button>
                </Link>
                <Link to={`/administrador/noticia/${noticia.noticiaId}`}>
                  <button className="button-noticia">Ver</button>
                </Link>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      {showNotification && (
        <Notification
          message={notificationMessage}
          onClose={() => setShowNotification(false)}
        />
      )}
    </div>
  );
}

export default ListaNoticias;
