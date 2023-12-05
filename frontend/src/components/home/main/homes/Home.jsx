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
                    
                    {secciones.map((seccion,index)=>{
                //    console.log("esta es el log que estoy haciendo en la haome seccion par la seccion if" , seccion.seccionId);
                        return(
                            <>
                                {style === true 
                                ? (
                                    <>
                                        <Seccion seccion={seccion.nombre} idSeccion = {seccion.seccionId} />
                                        <PubliSimple p={index} />
                                    </>
                                ) 
                                : (
                                        <SeccionRow   seccion={seccion.nombre}  idSeccion = {seccion.seccionId}  />
                                        
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
