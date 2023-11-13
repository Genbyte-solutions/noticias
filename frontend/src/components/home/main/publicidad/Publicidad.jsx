/* eslint-disable react/jsx-key */
import { publicidades } from '../../../../service/publicidad/Publicidad.js';
import './publicidad.css';


export default function Publicidad() {
    return (
        <section id="clients" className="clients section-bg">
            <div className="container">
                <div className="flexSA publicidades">
                    {publicidades.slice(0, 7).map((publi) => {
                        return (
                            <div className="cajita">
                                <div className="client-logo">
                                    <img src={publi} className="img-fluid" alt="" />
                                </div>
                            </div>
                        )
                    })}
                </div>
            </div>
        </section>
    );
}
