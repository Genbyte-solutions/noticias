import { useState } from "react";
import { principales } from "../../../service/noticia/Principales.js";
import Card from "./Card";
import "./hero.css";

export default function Hero() {
  const [items] = useState(principales);
  return (
    <section className="hero">
      <div className="container">
        {items.map((item) => {
          return <Card key={item.titulo} item={item} />;
        })}
      </div>
    </section>
  );
}
