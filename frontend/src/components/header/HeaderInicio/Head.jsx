import Aos from "aos";
import "aos/dist/aos.css";
import { useEffect } from "react";
import { Link } from "react-router-dom";
import srcBanner from "../../../assets/image/Mendoza-News-Banner.png";

function Head() {
  /* --USE EFFECT AOS-- */
  useEffect(() => {
    Aos.init();
  }, []);

  return (
    <section className="head">
      <div className="container flexCT">
        <div className="logo flexCT scale-up-center">
          <Link to="/">
            <img
              src={srcBanner}
              alt="logo"
              data-aos="zoom-in"
              data-aos-easing="ease-out-cubic"
              data-aos-duration="1000"
              data-aos-delay="400"
            />
          </Link>
        </div>
      </div>
    </section>
  );
}

export default Head;
