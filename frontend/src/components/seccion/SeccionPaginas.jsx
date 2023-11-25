/* eslint-disable react/jsx-key */
/* eslint-disable react/prop-types */
import { Link } from "react-router-dom";
import axios from "axios";
import Slider from "react-slick";
import "slick-carousel/slick/slick-theme.css";
import "slick-carousel/slick/slick.css";
import "./seccion-paginas.css";
import { imagenPorId } from "../../service/imagen/Imagen";
import { useEffect, useState } from "react";

export default function SeccionPaginas({ lista }) {
  const [portadas, setPortadas] = useState([]);

  const settings = {
    className: "center",
    centerMode: false,
    infinite: true,
    centerPadding: "",
    slidesToShow: 1,
    speed: 500,
    rows: 3,
    arrows: false,
    draggable: false,
    slidesPerRow: 3,
    slidesToScroll: 1,
    dots: true,
    customPaging: (i) => <div>{i + 1}</div>,
    responsive: [
      {
        breakpoint: 800,
        settings: {
          slidesPerRow: 1,
          rows: 5,
        },
      },
    ],
  };


  useEffect(() => {
    const fetchData = async (id) => {
      try {
        const response = await axios.get(`http://localhost:8080/api/v1/portada/noticia/${id}`, { responseType: "arraybuffer" });
        const imageUrl = URL.createObjectURL(new Blob([response.data], { type: response.headers['content-type'] }));
        return imageUrl;
      } catch (error) {
        console.error("Error al obtener las portadas: ", error);
        throw error;
      }
    };

    const loadPortadas = async () => {
      try {
        const portadasPromises = lista.map(async (val) => {
          return fetchData(val.noticiaId);
        });

        const portadasUrls = await Promise.all(portadasPromises);
        setPortadas(portadasUrls);
      } catch (error) {
        console.error("Error al cargar las portadas: ", error);
      }
    };

    loadPortadas();
  }, [lista]);

  return (
    <section className="seccion-paginas">
      <Slider {...settings}>
        {portadas.map((url, index) => (
          <div className="items" key={index}>
            <div className="box shadow">
              <div className="images">
                <div className="img">
                  <img src={url} alt="" />
                </div>
                <div className="categoria categoria1">
                  <Link to={`/seccion/${lista[index].categoria}`}>
                    <span>{lista[index].categoria}</span>
                  </Link>
                </div>
              </div>
              <div className="text">
                <Link to={`/noticia/${lista[index].titulo}`}>
                  <h1 className="titulo">{lista[index].titulo}</h1>
                  <p className="desc">{lista[index].parrafos}</p>
                  <p className="desc">{lista[index].autorResDto.nombre} {lista[index].autorResDto.apellido}</p>
                </Link>
                <div className="fecha">
                  <i className="fas fa-calendar-days"></i>
                  <label htmlFor="">{lista[index].fecha}</label>
                </div>
              </div>
            </div>
          </div>
        ))}
      </Slider>
    </section>
  );
}


{/* <section className="seccion-paginas">
      <Slider {...settings}>
        {lista.map((val) => {
    setPortada(fetchData(val.noticiaId))
          return (
            <div className="items">
              <div className="box shadow">
                <div className="images">
                  <div className="img">
                    <img src={portada} alt="" />
                  </div>
                  <div className="categoria categoria1">
                    <Link to={`/seccion/${val.categoria}`}>
                      <span>{val.categoria}</span>
                    </Link>
                  </div>
                </div>
                <div className="text">
                  <Link to={`/noticia/${val.titulo}`}>
                    <h1 className="titulo">{val.titulo}</h1>
                    <p className="desc">{val.parrafos}</p>
                    <p className="desc">{val.autorResDto.nombre} {val.autorResDto.apellido}</p>
                  </Link>
                  <div className="fecha">
                    <i className="fas fa-calendar-days"></i>
                    <label htmlFor="">{val.fecha}</label>
                  </div>
                </div>
              </div>
            </div>
          );
        })}
      </Slider>
    </section> */}