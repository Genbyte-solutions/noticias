package com.mendozanews.apinews.controllers;

import com.mendozanews.apinews.model.entidades.Autor;
import com.mendozanews.apinews.servicios.AutorServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/autor")
public class AutorControlador {

    private final AutorServicio autorServicio;

    public AutorControlador(AutorServicio autorServicio) {
        this.autorServicio = autorServicio;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Autor>> listarAutores() {
        List<Autor> autores = autorServicio.listarAutores();
        return new ResponseEntity<>(autores, HttpStatus.OK);
    }
}