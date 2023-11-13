import Cotizacion from '../../components/home/cotizacion/Cotizacion';
import Hero from '../../components/home/hero/Hero';
import Home from '../../components/home/main/homes/Home';
import Publicidad from '../../components/home/main/publicidad/Publicidad';

export default function Homepages() {
  window.scrollTo(0, 0)
  return (
    <>
      <Cotizacion />
      <Hero />
      <Publicidad />
      <Home />
    </>
  );
}
