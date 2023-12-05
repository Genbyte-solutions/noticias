/* eslint-disable react/jsx-key */
import Aos from "aos";
import "aos/dist/aos.css";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import Heading from "../../../../components/heading/Heading.jsx";
import { publiVerticales } from "../../../../service/publicidad/Publicidad.js";
import SocialMedia from "../social/SocialMedia.jsx";
import "./side.css";
import axios from "axios";

export default function Side() {
  const [secciones, setSecciones] = useState([])

  useEffect(() => {
    try {

      const fetchData = async () => {
        const response = await axios.get("http://localhost:8080/api/v1/secciones")
        setSecciones(response.data);
      }
      fetchData()
    } catch (e) {
      console.log(e);
    }
  })
// console.log(secciones);
  const categoria = secciones

  useEffect(() => {
    Aos.init();
  }, []);

  return (
    <div className="side">
      <Heading title="Conectate" />
      <SocialMedia />

      <section className="banner flexCT">
        <img
          src={publiVerticales[0]}
          alt=""
          data-aos="zoom-out-left"
          data-aos-easing="ease-out-cubic"
          data-aos-duration="1000"
        />
      </section>

      <section className="radio">
        <Heading title="Radio Continental 92.9" />
        <div className="flexCT">
          <iframe
            src="https://www.solumedia.com/radios/6020/index.html"
            height={500}
            frameBorder="0"
            title="radio"
          ></iframe>
        </div>
      </section>

      <section className="banner flexCT">
        <img
          src={publiVerticales[1]}
          key={publiVerticales[1].indexOf}
          alt=""
          data-aos="zoom-out-left"
          data-aos-easing="ease-out-cubic"
          data-aos-duration="1000"
        />
      </section>

      <section className="categorias">
        <Heading title="Categorias" />
        {categoria.map((cat) => {
          return (
            <div key={cat.nombre} className="categoria categoria1">
              <Link to={`/seccion/${cat.nombre}`}>
                <span>{cat.nombre}</span>
              </Link>
            </div>
          );
        })}
      </section>
    </div>
  );
}
