import { popular } from '../../../../service/noticia/Principales.js';
import Side from '../../sideContent/side/Side.jsx';
// import PNews from '../pnews/PNews.jsx';

import PubliSimple from '../publicidad/PubliSimple.jsx';
import SeccionRow from '../seccion-row/SeccionRow.jsx';
import Seccion from '../seccion/Seccion.jsx';
import './home.css';
import Recientes from '../Recientes/Recientes.jsx';
import { useSecciones } from '../../../../hooks/useSecciones.jsx';


export default function Home() {
    const {secciones, setSecciones} = useSecciones() 
    let style = true

    return (
        <main>
            <div className="container">
                <section className="mainContent">
            
                    <Recientes />
                    
                    {secciones.map((seccion)=>{
                        
                        return(
                            <>
                                {style === true 
                                ? (
                                    <>
                                        <Seccion seccion={seccion.nombre} />
                                        <PubliSimple p={5} />
                                    </>
                                ) 
                                : (
                                        <Seccion seccion={seccion.nombre}  />
                                        
                                )
                                }
                                {style = !style}
                            </>

                        )
                    })}
         
                </section>
                <section className="sideContent">
                    <Side />
                </section>
            </div>
        </main>
    );
}
