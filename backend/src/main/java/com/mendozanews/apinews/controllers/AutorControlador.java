package com.mendozanews.apinews.controllers;

import com.mendozanews.apinews.mapper.AutorMapper;
import com.mendozanews.apinews.model.dto.request.AutorDto;
import com.mendozanews.apinews.model.dto.response.AutorResDto;
import com.mendozanews.apinews.model.entidades.Autor;
import com.mendozanews.apinews.servicios.impl.AutorServicio;
import com.mendozanews.apinews.servicios.interfaces.IAutor;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AutorControlador {

    private final IAutor autorServicio;
    private final AutorMapper autorMapper;

    public AutorControlador(AutorServicio autorServicio, AutorMapper autorMapper) {
        this.autorServicio = autorServicio;
        this.autorMapper = autorMapper;
    }

    @PostMapping("/autor")
    public ResponseEntity<?> crearAutor(@ModelAttribute @Valid AutorDto autorDto,
                                        @RequestPart(value = "foto", required = false) MultipartFile foto) {

        try {
            this.autorServicio.crearAutor(autorDto, foto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/autores")
    public ResponseEntity<?> listarAutores() {

        List<Autor> autores = this.autorServicio.listarAutores();
        if (autores.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        List<AutorResDto> autoresDto = autorMapper.toDTOs(autores);

        return new ResponseEntity<>(autoresDto, HttpStatus.OK);
    }

    @PutMapping("/autor/{autorId}")
    public ResponseEntity<?> editarAutor(@PathVariable("autorId") String autorId,
                                         @ModelAttribute @Valid AutorDto autorDto,
                                         @RequestPart(value = "foto", required = false) MultipartFile foto) {
        try {
            Autor autor = this.autorServicio.buscarAutorPorId(autorId);
            if (autor == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            this.autorServicio.editarAutor(autor, autorDto, foto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException ioEX) {
            return new ResponseEntity<>(ioEX.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/autor/{autorId}")
    public ResponseEntity<?> eliminarAutor(@PathVariable("autorId") String autorId) {
        this.autorServicio.eliminarAutorPorId(autorId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}