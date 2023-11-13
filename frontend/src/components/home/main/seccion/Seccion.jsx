/* eslint-disable react/jsx-key */
/* eslint-disable react/prop-types */
import { Link } from 'react-router-dom';
import Slider from "react-slick";
import "slick-carousel/slick/slick-theme.css";
import "slick-carousel/slick/slick.css";
import Heading from '../../../../components/heading/Heading.jsx';
import './seccion.css';

export default function Seccion(props) {
  const settings = {
    className: "center",
    centerMode: false,
    infinite: true,
    centerPadding: "",
    slidesToShow: 3,
    speed: 500,
    rows: 1,
    slidesPerRow: 1,
    dots: false,
    autoplay: true,
    autoplaySpeed: 10000,
    pauseOnHover: true,
    responsive: [
      {
        breakpoint: 800,
        settings: {
          slidesToShow: 1,
          rows: 3,
          slidesToScroll: 1
        }
      }
    ]
  };

  return (
    <section className='seccion'>
      <Heading title={props.seccion} />
      <Slider {...settings}>
        {props.lista.map((val) => {
          return (
            <div className="items">
              <div className="box shadow">
                <div className="images row">
                  <div className="img">
                    <img src={val.imagen} alt="" />
                  </div>

                  {/* ESTO ES CON FINES ILUSTRATIVOS */}
                  <div className="categoria categoria1">
                    <Link to={`/seccion/${props.seccion}`}>
                      <span>{props.seccion}</span>
                    </Link>
                  </div>
                  {/* HASTA ACÁ */}

                </div>
                <div className="text row">
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
    </section>
  );
}
