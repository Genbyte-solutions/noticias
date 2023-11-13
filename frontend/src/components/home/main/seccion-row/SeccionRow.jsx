/* eslint-disable react/prop-types */
/* eslint-disable react/jsx-key */
import { Link } from 'react-router-dom';
import Slider from 'react-slick';
import Heading from '../../../../components/heading/Heading.jsx';
import "./seccion-row.css";

export default function SeccionRow(props) {
    const settings = {
        className: "center",
        centerMode: false,
        infinite: true,
        centerPadding: "",
        slidesToShow: 1,
        speed: 500,
        rows: 2,
        slidesPerRow: 1,
        dots: false,
        /*         autoplay: true,
                autoplaySpeed: 3000,
                pauseOnHover: true, */
    };

    return (
        <section className='seccion-row'>
            <Heading title={props.seccion} />
            <div className="content">
                <Slider {...settings}>
                    {props.lista.map((val) => {
                        return (
                            <div className="items">
                                <div className="box shadow flexSB">
                                    <div className="images">
                                        <div className="img">
                                            <img src={val.imagen} alt="" />
                                        </div>
                                        <div className="categoria categoria1">
                                            <Link to={`/seccion/${val.categoria}`}>
                                                <span>{val.categoria}</span>
                                            </Link>
                                        </div>
                                    </div>
                                    <div className="text col-SA">
                                        <Link to={`/noticia/${val.titulo}`}>
                                            <h1 className="titulo">{val.titulo}</h1>
                                        </Link>
                                        <div className="fecha">
                                            <i className='fas fa-calendar-days'></i>
                                            <label htmlFor=''>{val.fecha}</label>
                                        </div>
                                        <p className='desc'>{val.desc.slice(0, 250)}...</p>
                                        <div className="share">
                                            <i className="fas fa-share"></i>
                                            <label htmlFor="">Compartir</label>
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
