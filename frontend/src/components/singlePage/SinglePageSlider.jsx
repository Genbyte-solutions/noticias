/* eslint-disable react/jsx-key */
import { Link } from "react-router-dom";
import Slider from "react-slick";
import "slick-carousel/slick/slick-theme.css";
import "slick-carousel/slick/slick.css";
import { popular } from "../../service/noticia/Principales.js";
import "./single-page-slider.css";
import { useState, useEffect } from "react";

import axios from "axios";
export default function SinglePageSlider() {
  const [principales, setPrincipales] = useState([]);
  const [imagenId, setImagenId] = useState([]);

  const fetchData = async () => {
    try {
      const apiUrl = 'http://localhost:8080/api/v1/noticias_populares';
      const params = {
        offset: 0,
        limit: 9,
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


  const settings = {
    dots: false,
    infinite: true,
    speed: 2500,
    slidesToShow: 7,
    slidesToScroll: 2,
    autoplay: true,
    autoplaySpeed: 2500,
    cssEase: "linear",
    pauseOnHover: true,
    responsive: [
      {
        breakpoint: 800,
        settings: {
          slidesToShow: 2,
          slidesToScroll: 1,
        },
      },
    ],
  };

  return (
    <section className="singlePopular">
      <Slider {...settings}>
        {principales.map((val, index) => {
          return (
            <div key={index} className="items">
              <Link to={`/noticia/${val.titulo}/${val.noticiaId}`}>
                <div className="box">
                  <div className="images">
                    <div className="img">
                      <img src={imagenId[index]} alt="" />
                    </div>
                  </div>
                  <div className="text">
                    <h1 className="titulo">{val.titulo}</h1>
                  </div>
                </div>
              </Link>
            </div>
          );
        })}
      </Slider>
    </section>
  );
}
