package com.mendozanews.apinews.controllers;

import com.mendozanews.apinews.model.entidades.Autor;
import com.mendozanews.apinews.model.entidades.Imagen;
import com.mendozanews.apinews.servicios.impl.AutorServicio;
import com.mendozanews.apinews.servicios.impl.ImagenServicio;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/imagen")
public class ControllerImagen {

    private final AutorServicio autorServicio;
    private final ImagenServicio imagenServicio;

    public ControllerImagen (AutorServicio autorServicio, ImagenServicio imagenServicio){
        this.imagenServicio = imagenServicio;
        this.autorServicio = autorServicio;
    }

    @GetMapping("/autor/{id}")
    public ResponseEntity<?> obtenerImagenPorAutor(@PathVariable String id){
        Autor autor = this.autorServicio.getOne(id);
        if(autor==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Imagen imagen = this.imagenServicio.getOne(autor.getFoto().getImagenId());
        HttpHeaders headers = this.imagenServicio.buildImageResponseHeaders(imagen);

        return new ResponseEntity<>(imagen.getContenido(),HttpStatus.OK);
    }
}
