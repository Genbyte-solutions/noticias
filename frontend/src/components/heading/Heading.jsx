import Aos from "aos";
import "aos/dist/aos.css";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import axios from "axios";

import "./heading.css";

const Heading = ({title,urlIconoSeccion}) => {
  // console.log("ESTA ES EL ICONO" ,urlIconoSeccion );


useEffect(() => {
  Aos.init();
}, []);

return (
  <div>

    <div className="heading" data-aos-duration="400" data-aos-delay="0">

      <Link to={`/seccion/${title}`}>
        <div className="heading-title" data-aos="fade-left">
          {title !== "Popular" && title !== "Radio Continental 92.9" && title !== "Categorias" &&title !== "Conectate" ? (
          <img src={urlIconoSeccion} alt={title}  />
          ):( "")}
          <h6 className="skew-forward">{title}</h6>
        </div>
      </Link>
    </div>

  </div>
);
};

export default Heading;
