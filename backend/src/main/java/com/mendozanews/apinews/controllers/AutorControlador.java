package com.mendozanews.apinews.controllers;

import com.mendozanews.apinews.model.entidades.Autor;
import com.mendozanews.apinews.servicios.impl.AutorServicio;
import com.mendozanews.apinews.servicios.impl.ImagenServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/autor")
public class AutorControlador {

    private final AutorServicio autorServicio;
    private final ImagenServicio imagenServicio;

    public AutorControlador(AutorServicio autorServicio, ImagenServicio imagenServicio) {
        this.autorServicio = autorServicio;
        this.imagenServicio = imagenServicio;
    }

    @PostMapping("/nuevo")
    public ResponseEntity<?> crearAutor(@RequestParam("nombre") String nombre,
                                        @RequestParam("apellido") String apellido,
                                        @RequestParam("foto") MultipartFile foto){
        Autor buscarAutor = this.autorServicio.findByNombreCompleto(nombre, apellido);
        if(buscarAutor!=null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        buscarAutor = new Autor();
        buscarAutor.setNombre(nombre);
        buscarAutor.setApellido(apellido);
        buscarAutor.setFoto(this.imagenServicio.guardar(foto));
        this.autorServicio.crearAutor(buscarAutor);
        return new ResponseEntity<>(buscarAutor,HttpStatus.CREATED);
    }
    @GetMapping("/listar")
    public ResponseEntity<?> listarAutores() {
        List<Autor> autores = this.autorServicio.listarAutores();

        if (autores.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(autores, HttpStatus.OK);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> actualizarAutor(@PathVariable String id,
                                           @RequestParam("nombre") String nombre,
                                           @RequestParam("apellido") String apellido,
                                             @RequestParam("foto") MultipartFile foto){
        Autor autor = this.autorServicio.getOne(id);
        if(autor==null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        this.autorServicio.modificarAutor(autor,nombre,apellido,this.imagenServicio.actualizar(foto,autor.getFoto().getImagenId()));
        return new ResponseEntity<>(autor,HttpStatus.CREATED);
    }

    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<?> eliminarAutor(@PathVariable String id){
        this.autorServicio.eliminarAutorId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}