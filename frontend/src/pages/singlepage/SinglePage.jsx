import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import Side from "../../components/home/sideContent/side/Side.jsx";
import SinglePageSlider from "../../components/singlePage/SinglePageSlider.jsx";
import { principales } from "../../service/noticia/Principales.js";
import "./single-page.css";

export default function SinglePage() {
  const { titulo } = useParams();
  const [item, setItem] = useState(null);

  useEffect(() => {
    const item = principales.find((item) => item.titulo === titulo);
    window.scrollTo(0, 0);
    if (item) {
      setItem(item);
    }
  }, [titulo]);

  return (
    <>
      {item ? (
        <main className="singlePage">
          <SinglePageSlider />
          <div className="container">
            <section className="mainContent info">
              <h1 className="titulo">{item.titulo}</h1>
              <div className="autor">
                <span>por</span>
                <img src={item.autor.foto} alt="" />
                <Link to={`/autor/${item.autor.nombre}`}>
                  <p>{item.autor.nombre}</p>
                </Link>
                <label htmlFor="">{item.fecha}</label>
              </div>
              <div className="social">
                <div className="socBox">
                  <i className="fab fa-facebook-f"></i>
                </div>
                <div className="socBox">
                  <i className="fab fa-instagram"></i>
                </div>
                <div className="socBox">
                  <i className="fab fa-twitter"></i>
                </div>
                <div className="socBox">
                  <i className="fab fa-youtube"></i>
                </div>
              </div>
              <div className="descTop">
                {item.desc.map((elemento, id) =>
                  id < 2 ? (
                    <p key={id}>
                      {elemento}
                      {elemento}
                      {elemento}
                      {elemento}
                    </p>
                  ) : null
                )}
              </div>
              <img src={item.imagen} alt="" />
              <div className="descBot">
                <h1>{item.titulo}</h1>
                <p>{item.desc[0]}</p>
              </div>
              {item.quote ? (
                <div className="quote">
                  <i className="fa fa-quote-left"></i>
                  <p>
                    {item.quote}
                    {item.quote}
                    {item.quote}
                  </p>
                </div>
              ) : (
                <></>
              )}
              <div className="descTop">
                {item.desc.map((elemento, id) =>
                  id > 1 ? (
                    <p key={id}>
                      {elemento}
                      {elemento}
                      {elemento}
                      {elemento}
                    </p>
                  ) : null
                )}
              </div>
              <div className="sobre-autor">
                <div className="autor">
                  <img src={item.autor.foto} alt="" />
                  <div className="texto">
                    <span>Más de</span>
                    <Link to={`/autor/${item.autor.nombre}`}>
                      <p>{item.autor.nombre}</p>
                    </Link>
                  </div>
                </div>
              </div>
            </section>
            <section className="sideContent">
              <Side />
            </section>
          </div>
        </main>
      ) : (
        <>
          <SinglePageSlider />
          <h1 className="error">La noticia no está disponible</h1>
        </>
      )}
    </>
  );
}
