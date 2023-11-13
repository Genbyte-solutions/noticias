/* eslint-disable react/jsx-key */
import { Link } from "react-router-dom";
import Slider from "react-slick";
import "slick-carousel/slick/slick-theme.css";
import "slick-carousel/slick/slick.css";
import { popular } from "../../service/noticia/Principales.js";
import "./single-page-slider.css";

export default function SinglePageSlider() {
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
        {popular.map((val) => {
          return (
            <div className="items">
              <Link to={`/noticia/${val.titulo}`}>
                <div className="box">
                  <div className="images">
                    <div className="img">
                      <img src={val.imagen} alt="" />
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
