/* eslint-disable react/prop-types */
import Aos from "aos";
import "aos/dist/aos.css";
import { useEffect } from "react";
import { Link } from "react-router-dom";
import { iconos } from "../../service/iconos.js";
import "./heading.css";

const Heading = ({ title }) => {
  /* --USE EFFECT AOS-- */
  useEffect(() => {
    Aos.init();
  }, []);

  const iconoEncontrado = iconos.find((i) => i.tipo === title);

  if (!iconoEncontrado) {
    // Manejar la situación si no se encuentra el icono
    return (
      <div className="heading" data-aos-duration="400" data-aos-delay="0">
        <h6 className="hover" data-aos="fade-in">
          {title}
        </h6>
      </div>
    );
  }

  const { source } = iconoEncontrado;

  return (
    <div className="heading" data-aos-duration="400" data-aos-delay="0">
      <Link to={`/seccion/${title}`}>
        <div className="heading-title" data-aos="fade-left">
          <img src={source} alt={title} />
          <h6 className="skew-forward">{title}</h6>
        </div>
      </Link>
    </div>
  );
};

export default Heading;
