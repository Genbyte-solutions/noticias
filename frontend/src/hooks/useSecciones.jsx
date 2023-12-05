import { useState, useEffect } from "react"
import axios from "axios"

export function useSecciones(){
  const [secciones, setSecciones] = useState([])


  useEffect(() => {
    try {
      const fetchData = async () => {
        const response = await axios.get("http://localhost:8080/api/v1/secciones")
        setSecciones(response.data)
      }

      fetchData()
    } catch (e) {
      console.log("este es el error", e);
    }
    // console.log("esto es secciones", secciones);
  }, [])

  return {secciones, setSecciones}
}