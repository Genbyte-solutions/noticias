/* eslint-disable react/prop-types */
/* eslint-disable react/jsx-key */
import { Link } from 'react-router-dom';
import Slider from 'react-slick';
import Heading from '../../../../components/heading/Heading.jsx';
import { useState, useEffect } from 'react';
import axios from 'axios';
import "./seccion-row.css";

export default function SeccionRow(props) {
    // console.log(props.idSeccion);
    const [urlIconoSeccion, setIconoSeccion] = useState()
    const [lista, setLista] = useState([])
    const [portadas, setPortadas] = useState([]);


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

    const settings = {
        className: "center",
        centerMode: false,
        infinite: true,
        centerPadding: "",
        slidesToShow: 1,
        speed: 500,
        rows: 3,
        slidesPerRow: 1,
        dots: false,
        /*         autoplay: true,
                autoplaySpeed: 3000,
                pauseOnHover: true, */
    };



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



    return (
        <section className='seccion-row'>
            <Heading title={props.seccion} urlIconoSeccion={urlIconoSeccion} />
            <div className="content">
                <Slider {...settings}>
                    {lista.map((val, index) => {
                        {console.log("esto es val" , );}
                        return (
                            <div className="items">
                                <div className="box shadow flexSB">
                                    <div className="images">
                                        <div className="img">
                                            <img src={portadas[index]} alt="" />
                                        </div>
                                        <div className="categoria categoria1">
                                            <Link to={`/seccion/${props.seccion}`}>
                                                <span>{props.seccion}</span>
                                            </Link>
                                        </div>
                                    </div>
                                    <div className="text col-SA">
                                        <Link to={`/noticia/${val.titulo}/${val.noticiaId}`}>
                                            <h1 className="titulo">{val.titulo}</h1>
                                        </Link>
                                        
                                        <p className='desc'>{val.parrafos ? val.parrafos[0].slice(0, 250) : ""}...</p>

                                        <div className="share">

                                            <i className='fas fa-calendar-days'></i>
                                            <label htmlFor=''>{new Date().toLocaleDateString()}</label>

                                            {/* <i className="fas fa-share"></i>
                                            <label> <p>  Por <strong>{val.autorResDto.nombre + " " + val.autorResDto.apellido}</strong></p></label> */}
                                        </div>
                                    </div>
                                </div>
                            </div>
                        )
                    })}
                </Slider>
            </div>
        </section>
    );
}
