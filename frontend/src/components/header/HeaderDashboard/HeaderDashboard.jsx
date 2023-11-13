import { faBars, faTimes } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useState } from "react";
import { Link } from "react-router-dom";
import HeadDash from "./headDashboard";
import "./HeaderStyle.css";

export default function HeaderDashboard() {
  const [navbar, setNavbar] = useState(false);

  return (
    <div className="full-header">
      <HeadDash />
      <header>
        <div className="container">
          <nav>
            <ul
              className={navbar ? "navbar" : "flexCT"}
              onClick={() => setNavbar(false)}
            >
              <li>
                <Link to="/administrador/noticia/nueva">Cargar Noticia</Link>
              </li>
              <li>
                <Link to="/administrador/noticia/listar">Listar Noticias</Link>
              </li>
              <li>
                <Link to="/administrador/seccion/nueva">Cargar Seccion</Link>
              </li>
              <li>
                <Link to="/administrador/seccion/listar">Listar Secciones</Link>
              </li>
              <li>
                <Link to="/administrador/autor/nuevo">Cargar Autor</Link>
              </li>
              <li>
                <Link to="/administrador/autor/listar">Listar Autores</Link>
              </li>
              <li>
                <Link to="/administrador/usuario/nuevo">Cargar Usuario</Link>
              </li>
              <li>
                <Link to="/administrador/usuario/listar">Listar Usuarios</Link>
              </li>
              <li>
                <Link to="/administrador/login">Login</Link>
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
