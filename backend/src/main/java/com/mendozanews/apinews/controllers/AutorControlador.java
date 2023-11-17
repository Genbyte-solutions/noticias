package com.mendozanews.apinews.controllers;

import com.mendozanews.apinews.model.dto.request.AutorDto;
import com.mendozanews.apinews.model.entidades.Autor;
import com.mendozanews.apinews.servicios.impl.AutorServicio;
import com.mendozanews.apinews.servicios.impl.ImagenServicio;
import com.mendozanews.apinews.servicios.interfaces.IAutor;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AutorControlador {

    private final IAutor autorServicio;

    public AutorControlador(AutorServicio autorServicio) {
        this.autorServicio = autorServicio;
    }

    @PostMapping("/autor")
    public ResponseEntity<?> crearAutor(@ModelAttribute @Valid AutorDto autorDto,
                                        @RequestParam("foto") MultipartFile foto) {

        try {
            autorServicio.crearAutor(autorDto, foto);

            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
                                             @ModelAttribute @Valid AutorDto autorDto,
                                             @RequestParam("foto") MultipartFile foto) {
        Autor autor = this.autorServicio.buscarAutorPorId(id);
        if (autor == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        this.autorServicio.modificarAutor(autor, autorDto, foto);
        return new ResponseEntity<>(autor, HttpStatus.CREATED);
    }

    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<?> eliminarAutor(@PathVariable String id) {
        this.autorServicio.eliminarAutorPorId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}