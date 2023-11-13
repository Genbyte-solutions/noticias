import { faBars, faTimes } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useState } from "react";
import { Link } from "react-router-dom";
import Head from "./Head";
import "./header.css";




export default function Header() {
  const [navbar, setNavbar] = useState(false);

  return (
    <div className="full-header">
      <Head />

      <header>
        <div className="container">
          <nav>
            <ul
              className={navbar ? "navbar" : "flexCT"}
              onClick={() => setNavbar(false)}
            >
              <li>
                <a
                  href="https://www.solumedia.com/radios/6020/index.html"
                  target="blank"
                >
                  Escuchanos en vivo
                </a>
              </li>
              {/* <li>
                <Link to="/administrador">Administrador</Link>
              </li> */}
              <li>
                <Link to="/seccion/sanrafael">San Rafael</Link>
              </li>
              <li>
                <Link to="/seccion/mendoza">Mendoza</Link>
              </li>
              <li>
                <Link to="/seccion/politica">Politica</Link>
              </li>
              <li>
                <Link to="/seccion/nacionales">Nacionales</Link>
              </li>
              <li>
                <Link to="/seccion/internacionales">Internacionales</Link>
              </li>
              <li>
                <Link to="/seccion/deportes">Deportes</Link>
              </li>
              <li>
                <Link to="/seccion/editorial">Editorial</Link>
              </li>
              <li>
                <Link to="/seccion/espectaculos">Espectáculos</Link>
              </li>
              <li>
                <Link to="/seccion/tecnologia">Tecnología</Link>
              </li>
              <li>
                <Link to="/seccion/gastronomia">Gastronomia</Link>
              </li>
              <li>
                <Link to="/seccion/astrologia">Alineando los planetas</Link>
              </li>
              <li>
              <Link to="/login2">Iniciar sesión</Link>
              </li>
             
              
            </ul>
            <button className="barIco" onClick={() => setNavbar(!navbar)}>
              {navbar ? (
                <FontAwesomeIcon icon={faTimes} />
              ) : (
                <FontAwesomeIcon icon={faBars} />
              )}
            </button>
          </nav>
        </div>
      </header>
    </div>
  );
}
