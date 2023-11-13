import React from 'react';
import { Link } from "react-router-dom";
import banner from "../../assets/image/Mendoza-News-Banner.png";
import { popular } from '../../service/noticia/Principales.js';
import "./footer.css";


export default function Footer() {
  return (
    <>
      <footer>
        <div className="container">
          <div className="box logo">
            <img src={banner} alt="" />
            <p>Unimos seriedad y rigor periodístico para ofrecerte información confiable y relevante. ¡Bienvenido a la era de la verdad informativa!</p>

            <i className='fa fa-envelope'></i>
            <span>MendozaNews@gmail.com</span><br />
            <i className="fa fa-headphones"></i>
            <span>0260 15-433-0366</span>
          </div>

          <div className="box">
            <Link to={`/seccion/Deportes`}>
              <h3>Deportes</h3>
            </Link>
            <div className="item">
              <img src={popular[0].imagen} alt="" />
              <Link to={`/noticia/${popular[0].titulo}`}>
                <p>{popular[0].titulo.slice(0, 50)} ...</p>
              </Link>
            </div>
            <div className="item">
              <img src={popular[1].imagen} alt="" />
              <Link to={`/noticia/${popular[1].titulo}`}>
                <p>{popular[1].titulo}</p>
              </Link>
            </div>
          </div>

          <div className="box">
            <Link to={`/seccion/Espectáculo`}>
              <h3>Espectáculo</h3>
            </Link>
            <div className="item">
              <img src={popular[2].imagen} alt="" />
              <Link to={`/noticia/${popular[2].titulo}`}>
                <p>{popular[2].titulo}</p>
              </Link>
            </div>
            <div className="item">
              <img src={popular[3].imagen} alt="" />
              <Link to={`/noticia/${popular[3].titulo}`}>
                <p>{popular[3].titulo}</p>
              </Link>
            </div>
          </div>

          <div className="box">
            <h3>Etiquetas</h3>
            <ul>
              <Link to={`/Futbol`}><li>
                <span>Futbol</span><label htmlFor="">(6)</label>
              </li> </Link>
              <Link to={`/Naturaleza`}><li>
                <span>Naturaleza</span><label htmlFor="">(5)</label>
              </li></Link>
              <Link to={`/Moda`}><li>
                <span>Moda</span><label htmlFor="">(8)</label>
              </li></Link>
              <Link to={`/Salud`}><li>
                <span>Salud</span><label htmlFor="">(6)</label>
              </li></Link>
            </ul>
          </div>
        </div>
      </footer>


      <div className="legal">
        <div className="container flexSB">
          <p>
            © 2023 <Link to='/'>MendozaNews</Link>
          </p>
          <Link to='/privacidad/politica'>
          <p>
            Política de privacidad
          </p>
          </Link>
          <p>Desarrollado por <a href="https://digitalculture.es/Ar/index.html"><span>Digital Culture</span></a></p>
        </div>
      </div>

    </>
  );
}
