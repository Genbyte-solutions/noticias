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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.RouterOperation;
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
@Tag(name = "Autor")
public class AutorControlador {

    private final IAutor autorServicio;
    private final AutorMapper autorMapper;

    public AutorControlador(AutorServicio autorServicio, AutorMapper autorMapper) {
        this.autorServicio = autorServicio;
        this.autorMapper = autorMapper;
    }

    @Operation(
            summary = "Crea un autor",
            responses = {
                    @ApiResponse(responseCode = "201",
                            description = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello"),
                    @ApiResponse(responseCode = "400",
                            description = "El servidor no pudo interpretar la solicitud dada una sintaxis inválida"),
                    @ApiResponse(responseCode = "500",
                            description = "El servidor ha encontrado una situación que no sabe cómo manejarla")
            }
    )
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

    @Operation(
            summary = "Edita un autor por ID",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "La solicitud ha tenido éxito"),
                    @ApiResponse(responseCode = "404",
                            description = "El servidor no pudo encontrar el contenido solicitado"),
                    @ApiResponse(responseCode = "400",
                            description = "El servidor no pudo interpretar la solicitud dada una sintaxis inválida"),
                    @ApiResponse(responseCode = "500",
                            description = "El servidor ha encontrado una situación que no sabe cómo manejarla")
            }
    )
    @PutMapping("/autor/{autorId}")
    public ResponseEntity<?> editarAutor(@PathVariable("autorId") String autorId,
                                         @ModelAttribute @Valid AutorDto autorDto,
                                         @RequestPart(value = "foto", required = false) MultipartFile foto) {
        try {
            Autor autor = autorServicio.buscarAutorPorId(autorId);
            if (autor == null) throw new ResourceNotFoundException("Autor", "id", autorId);

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

    @Operation(
            summary = "Busca un autor por ID",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "La solicitud ha tenido éxito"),
                    @ApiResponse(responseCode = "404",
                            description = "El servidor no pudo encontrar el contenido solicitado"),
                    @ApiResponse(responseCode = "500",
                            description = "El servidor ha encontrado una situación que no sabe cómo manejarla")
            }
    )
    @GetMapping("/autor/{autorId}")
    public ResponseEntity<?> buscarAutor(@PathVariable("autorId") String autorId) {

        Autor autor = autorServicio.buscarAutorPorId(autorId);
        if (autor == null) throw new ResourceNotFoundException("Autor", "id", autorId);

        AutorResDto autorResDto = autorMapper.toDTO(autor);
        return new ResponseEntity<>(autorResDto, HttpStatus.OK);
    }

    @Operation(
            summary = "Lista de autores",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "La solicitud ha tenido éxito"),
                    @ApiResponse(responseCode = "404",
                            description = "El servidor no pudo encontrar el contenido solicitado"),
                    @ApiResponse(responseCode = "500",
                            description = "El servidor ha encontrado una situación que no sabe cómo manejarla")
            }
    )
    @GetMapping("/autores")
    public ResponseEntity<?> listarAutores() {

        List<Autor> autores = autorServicio.listarAutores();
        if (autores.isEmpty()) throw new ResourceNotFoundException("autores");

        List<AutorResDto> autoresDto = autorMapper.toDTOs(autores);
        return new ResponseEntity<>(autoresDto, HttpStatus.OK);
    }

    @Operation(
            summary = "Elimina un autor por ID",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "La solicitud ha tenido éxito"),
                    @ApiResponse(responseCode = "500",
                            description = "El servidor ha encontrado una situación que no sabe cómo manejarla")
            }
    )
    @DeleteMapping("/autor/{autorId}")
    public ResponseEntity<?> eliminarAutor(@PathVariable("autorId") String autorId) {
        autorServicio.eliminarAutorPorId(autorId);
        return new ResponseEntity<>(ResponseMessage.builder()
                .mensaje("Autor eliminado")
                .build(), HttpStatus.OK);
    }
}