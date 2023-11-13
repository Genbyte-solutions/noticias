/* eslint-disable react/prop-types */
import Aos from "aos";
import "aos/dist/aos.css";
import { useEffect } from "react";
import { Link } from "react-router-dom";

export default function Card(props) {
  /* --USE EFFECT AOS-- */
  useEffect(() => {
    Aos.init();
  }, []);

  return (
    <div
      className="box"
      /* data-aos="zoom-in"
            data-aos-easing="ease-out-cubic"
            data-aos-duration="1000"
            data-aos-delay="200" */
    >
      <div className="img">
        <img src={props.item.imagen} alt="" />
      </div>
      <div className="text">
        <Link to={`/seccion/${props.item.categoria.toString()}`}>
          <span className="categoria">{props.item.categoria.toString()}</span>
        </Link>
        <Link to={`/noticia/${props.item.titulo}`}>
          <h1 className="titleBG">{props.item.titulo}</h1>
        </Link>
      </div>
    </div>
  );
}
