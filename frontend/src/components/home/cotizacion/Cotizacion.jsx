import Aos from "aos";
import "aos/dist/aos.css";
import { useEffect } from "react";
import "./cotizacion.css";

export default function Cotizacion() {
  /* --USE EFFECT AOS-- */
  useEffect(() => {
    Aos.init();
  }, []);

  return (
    <section className="cotizacion">
      <div
        data-aos="zoom-in"
        data-aos-easing="ease-out-cubic"
        data-aos-duration="1000"
        data-aos-delay="800"
      >
        <iframe
          src="https://dolarhoy.com/i/cotizaciones/dolar-blue"
          frameBorder="0"
          title="dolar blue"
        ></iframe>
      </div>

      <div
        data-aos="zoom-in"
        data-aos-easing="ease-out-cubic"
        data-aos-duration="1000"
        data-aos-delay="1000"
      >
        <iframe
          src="https://dolarhoy.com/i/cotizaciones/dolar-bancos-y-casas-de-cambio"
          frameBorder="0"
          title="dolar bancos"
        ></iframe>
      </div>

      <div
        data-aos="zoom-in"
        data-aos-easing="ease-out-cubic"
        data-aos-duration="1000"
        data-aos-delay="1200"
      >
        <iframe
          src="https://dolarhoy.com/i/cotizaciones/dolar-mep"
          frameBorder="0"
          title="dolar mep"
        ></iframe>
      </div>
    </section>
  );
}
