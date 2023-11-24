package com.mendozanews.apinews.controller;

import com.mendozanews.apinews.exception.ResourceBadRequestException;
import com.mendozanews.apinews.exception.ResourceNotFoundException;
import com.mendozanews.apinews.mapper.AutorMapper;
import com.mendozanews.apinews.model.dto.request.AutorDto;
import com.mendozanews.apinews.model.dto.response.AutorResDto;
import com.mendozanews.apinews.model.entity.Autor;
import com.mendozanews.apinews.model.payload.ResponseMessage;
import com.mendozanews.apinews.service.impl.AutorServicio;
import com.mendozanews.apinews.service.interfaces.IAutor;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@Validated
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
            Autor autor = autorServicio.crearAutor(autorDto, foto);
            AutorResDto autorResDto = autorMapper.toDTO(autor);

            return new ResponseEntity<>(ResponseMessage.builder()
                    .mensaje("Autor añadido")
                    .recurso(autorResDto)
                    .build(), HttpStatus.CREATED);

        } catch (IOException ioException) {
            throw new ResourceBadRequestException(ioException.getMessage());
        }
    }

    @PutMapping("/autor/{autorId}")
    public ResponseEntity<?> editarAutor(@PathVariable("autorId") String autorId,
                                         @ModelAttribute @Valid AutorDto autorDto,
                                         @RequestPart(value = "foto", required = false) MultipartFile foto) {
        try {
            Autor autor = autorServicio.buscarAutorPorId(autorId);
            if (autor == null) throw new ResourceNotFoundException("autor", "id", autorId);

            Autor autorEditado = autorServicio.editarAutor(autor, autorDto, foto);
            AutorResDto autorResDto = autorMapper.toDTO(autorEditado);

            return new ResponseEntity<>(ResponseMessage.builder()
                    .mensaje("Autor actualizado")
                    .recurso(autorResDto)
                    .build(), HttpStatus.OK);
        } catch (IOException ioException) {
            throw new ResourceBadRequestException(ioException.getMessage());
        }
    }

    @GetMapping("/autor/{autorId}")
    public ResponseEntity<?> buscarAutor(@PathVariable("autorId") String autorId) {

        Autor autor = autorServicio.buscarAutorPorId(autorId);
        if (autor == null) throw new ResourceNotFoundException("autor", "id", autorId);

        AutorResDto autorResDto = autorMapper.toDTO(autor);
        return new ResponseEntity<>(autorResDto, HttpStatus.OK);
    }

    @GetMapping("/autores")
    public ResponseEntity<?> listarAutores() {

        List<Autor> autores = autorServicio.listarAutores();
        if (autores.isEmpty()) throw new ResourceNotFoundException("autores");

        List<AutorResDto> autoresDto = autorMapper.toDTOs(autores);
        return new ResponseEntity<>(autoresDto, HttpStatus.OK);
    }

    @DeleteMapping("/autor/{autorId}")
    public ResponseEntity<?> eliminarAutor(@PathVariable("autorId") String autorId) {
        autorServicio.eliminarAutorPorId(autorId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}