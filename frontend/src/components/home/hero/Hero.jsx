import { useEffect, useState } from "react";
import axios from 'axios';
import Card from "./Card";
import "./hero.css";

export default function Hero() {
  const [principales, setPrincipales] = useState([]);
  const [imagenId, setImagenId] = useState([]);

  const fetchData = async () => {
    try {
      const apiUrl = 'http://localhost:8080/api/v1/noticias_populares';
      const params = {
        offset: 0,
        limit: 3,
      };
      const response = await axios.get(apiUrl, { params });
      setPrincipales(response.data);
    } catch (error) {
      console.error('Error al realizar la petición principal:', error);
    }
  };

  const fetchImage = async (id) => {
    try {
      const response = await axios.get(`http://localhost:8080/api/v1/portada/noticia/${id}`, { responseType: "arraybuffer" });
      const imageUrl = URL.createObjectURL(new Blob([response.data], { type: response.headers['content-type'] }));
      return imageUrl;
    } catch (error) {
      console.error("Error al obtener las portadas: ", error);
      throw error;
    }
  };

  const loadImage = async () => {
    try {
      const portadasPromises = principales.map(async (val) => {
        return fetchImage(val.noticiaId);
      });

      const portadasUrls = await Promise.all(portadasPromises);
      setImagenId(portadasUrls);
    } catch (error) {
      console.error("Error al cargar las portadas: ", error);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  useEffect(() => {
    loadImage();
  }, [principales]);

  return (
    <section className="hero">
      <div className="container">
        {principales.length > 0 &&
          principales.map((item, index) => (
            <Card key={item.titulo} imagen={imagenId[index]} item={item} />
          ))}
      </div>
    </section>
  );
}
