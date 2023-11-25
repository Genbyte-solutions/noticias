/* eslint-disable react/jsx-key */
/* eslint-disable react/prop-types */
import { Link } from "react-router-dom";
import Slider from "react-slick";
import "slick-carousel/slick/slick-theme.css";
import "slick-carousel/slick/slick.css";
import "./seccion-paginas.css";
import { imagenPorId } from "../../service/imagen/Imagen";
import { useState } from "react";

export default function SeccionPaginas({ lista }) {
  const [portada, setPortada] = useState(null)

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

  const setImage = async (id) =>{
     imagenPorId(id).then(data => console.log(data, "data"))
     
  }

  return (
    <section className="seccion-paginas">
      <Slider {...settings}>
        {lista.map((val) => {

        setImage(val.noticiaId)
        console.log(portada)

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
    </section>
  );
}
