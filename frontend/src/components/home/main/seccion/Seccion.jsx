/* eslint-disable react/jsx-key */
/* eslint-disable react/prop-types */
import { Link } from 'react-router-dom';
import Slider from "react-slick";
import "slick-carousel/slick/slick-theme.css";
import "slick-carousel/slick/slick.css";
import Heading from '../../../../components/heading/Heading.jsx';
import './seccion.css';
import { useEffect, useState } from 'react';
import axios from 'axios';

export default function Seccion(props) {
  // console.log(props.idSeccion);
  const [portadas, setPortadas] = useState([])
  const [lista, setLista] = useState([])
  const [urlIconoSeccion, setIconoSeccion] = useState()

  //TRAYENDO EL ICONO DE CADA SECCION QUE VA EN EL HEADING = 
  useEffect(() => {
    try {
      const fetchData = async () => {
        const response = await axios.get(`http://localhost:8080/api/v1/icono/seccion/${props.idSeccion}`, { responseType: "arraybuffer" })
        const imageUrl = URL.createObjectURL(new Blob([response.data], { type: response.headers['content-type'] }));
        setIconoSeccion(imageUrl);
      };
      fetchData();
    } catch (error) {
      console.error("Error al obtener el icono de la sección:", error);
    }
  }, [props.seccion]);


  //TRAYENDO LAS NOTICIAS POPULARES DE CADA SECCION 

  useEffect(() => {
    try {
      const fetchData = async () => {

        const response = await axios.get(`http://localhost:8080/api/v1/noticias_populares/${props.seccion}?offset=0&limit=100`)
        const data = response.data

        setLista(data)
      }

      fetchData()
    } catch (error) {
      console.log(error, "ERRORRRRRRRRRRRRRRRRRRRRRRRRRR")
    }
  }, [])


  //TRAYENDO LA PORTADA DE LAS NOTICIAS POR EL ID
  useEffect(() => {
    const fetchData = async (id) => {
      try {
        const response = await axios.get(`http://localhost:8080/api/v1/portada/noticia/${id}`, { responseType: "arraybuffer" });
        const imageUrl = URL.createObjectURL(new Blob([response.data], { type: response.headers['content-type'] }));
        return imageUrl;
      } catch (error) {
        console.error("Error al obtener las portadas: ", error);
        throw error;
      }
    };

    const loadPortadas = async () => {
      try {
        const portadasPromises = lista.map(async (val) => {
          return fetchData(val.noticiaId);
        });

        const portadasUrls = await Promise.all(portadasPromises);

        setPortadas(portadasUrls);
      } catch (error) {
        console.error("Error al cargar las portadas: ", error);
      }
    };

    setPortadas([]);

    loadPortadas();
  }, [lista]);

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

      <Heading title={props.seccion} urlIconoSeccion={urlIconoSeccion} /> {/*Cambiar heading */}
      <Slider {...settings}>
        {lista.map((val, index) => {
          //  console.log("esto es val" , val);

          return (
            <div className="items">
              <div className="box shadow">
                <div className="images row">
                  <div className="img">
                    <img src={portadas[index]} alt="" />
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
                  <Link to={`/noticia/${val.titulo}/${val.noticiaId}`}>
                    <h1 className="titulo">{val.titulo}</h1>
                  </Link>

                  <div className="fecha">

                    <i className='fas fa-calendar-days'>
                      <label htmlFor=''>{new Date().toLocaleDateString()} </label></i>
                    <label> <p>  Por <strong>{val.autorResDto.nombre + " " + val.autorResDto.apellido}</strong></p></label>
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
