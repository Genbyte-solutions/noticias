/* eslint-disable react/prop-types */
import Aos from 'aos';
import 'aos/dist/aos.css';
import { useEffect } from 'react';
import { publicidades } from '../../../../service/publicidad/Publicidad.js';
import './publicidad.css';

export default function PubliSimple(props) {

    useEffect(() => { Aos.init() }, []);

    return (
        <section id="publis" className="publi-simple">
            <div className="publi">
                <div className="img-container"
                    data-aos="flip-left"
                    data-aos-easing="ease-out-cubic"
                    data-aos-duration="1000">
                    <img src={publicidades[props.p]} alt="" />
                </div>
            </div>
        </section>
    );
}