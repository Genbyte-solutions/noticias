/* eslint-disable react/jsx-key */
import { Link } from 'react-router-dom';
import Slider from "react-slick";
import Heading from "../../../../components/heading/Heading.jsx";
import { popular } from "../../../../service/noticia/Principales.js";

import './pnews.css';

export default function PNews() {

  const settings = {
    dots: false,
    infinite: true,
    speed: 500,
    slidesToShow: 2,
    slidesToScroll: 1,
    nextArrow: null,
    responsive: [
      {
        breakpoint: 800,
        settings: {
          slidesToShow: 1,
          slidesToScroll: 1
        }
      }
    ]
  };

  return (
    <section className='popularNews'>
      <Heading title='Mas vistas' />
      <div className="content">
        <Slider {...settings}>
          {popular.map((val) => {
            return (
              <div className="items">
                <div className="box">
                  <div className="images">
                    <div className="img">
                      <img src={val.imagen} alt="" />
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
                    </Link>
                    <div className="fecha">
                      <i className='fas fa-calendar-days'></i>
                      <label htmlFor=''>{val.fecha}</label>
                    </div>
                  </div>
                </div>
              </div>
            )
          })}
        </Slider>
      </div>
    </section>
  );
}
