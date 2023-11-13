import { popular } from '../../../../service/noticia/Principales.js';
import Side from '../../sideContent/side/Side.jsx';
import PNews from '../pnews/PNews.jsx';

import PubliSimple from '../publicidad/PubliSimple.jsx';
import SeccionRow from '../seccion-row/SeccionRow.jsx';
import Seccion from '../seccion/Seccion.jsx';
import './home.css';
import Recientes from '../Recientes/Recientes.jsx';


export default function Home() {
    return (
        <main>
            <div className="container">
                <section className="mainContent">
            
                    <Recientes />
         
                    <PNews />
                    <Seccion lista={popular} seccion='San Rafael' />
                    <SeccionRow lista={popular} seccion='Mendoza' />

                    <PubliSimple p={5} />
                    <Seccion lista={popular} seccion='Política' />

                    <PubliSimple p={4} />
                    <Seccion lista={popular} seccion='Nacionales' />
                    <SeccionRow lista={popular} seccion='Internacionales' />

                    <PubliSimple p={3} />
                    <Seccion lista={popular} seccion='Deportes' />
                    <SeccionRow lista={popular} seccion='Editorial' />

                    <PubliSimple p={2} />
                    <Seccion lista={popular} seccion='Espectáculos' />
                    <SeccionRow lista={popular} seccion='Tecnología' />

                    <PubliSimple p={1} />
                    <Seccion lista={popular} seccion='Gastronomía' />
                    <SeccionRow lista={popular} seccion='Astrología' />

                    <PubliSimple p={0} />
                </section>
                <section className="sideContent">
                    <Side />
                </section>
            </div>
        </main>
    );
}
