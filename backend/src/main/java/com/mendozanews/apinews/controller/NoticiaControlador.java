package com.mendozanews.apinews.controller;

import com.mendozanews.apinews.exception.ResourceBadRequestException;
import com.mendozanews.apinews.exception.ResourceNotFoundException;
import com.mendozanews.apinews.mapper.NoticiaMapper;
import com.mendozanews.apinews.model.dto.request.AutorDto;
import com.mendozanews.apinews.model.dto.response.NoticiaResDto;
import com.mendozanews.apinews.model.entity.*;
import com.mendozanews.apinews.model.payload.ResponseMessage;
import com.mendozanews.apinews.service.interfaces.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import com.mendozanews.apinews.model.dto.request.NoticiaDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/v1")
@Tag(name = "Noticia")
public class NoticiaControlador {
    private final INoticia noticiaService;
    private final ISeccion seccionService;
    private final IAutor autorService;
    private final NoticiaMapper noticiaMapper;

    public NoticiaControlador(INoticia noticiaService,
                              ISeccion seccionService, IAutor autorService,
                              NoticiaMapper noticiaMapper) {
        this.noticiaService = noticiaService;
        this.seccionService = seccionService;
        this.autorService = autorService;
        this.noticiaMapper = noticiaMapper;
    }

    @Operation(
            summary = "Crea una noticia",
            responses = {
                    @ApiResponse(responseCode = "201",
                            description = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello"),
                    @ApiResponse(responseCode = "400",
                            description = "El servidor no pudo interpretar la solicitud dada una sintaxis inválida"),
                    @ApiResponse(responseCode = "500",
                            description = "El servidor ha encontrado una situación que no sabe cómo manejarla")
            }
    )
    @PostMapping(value = "/noticia")
    public ResponseEntity<?> crearNoticia(@ModelAttribute @Valid NoticiaDto noticiaDto,
                                          @RequestPart(value = "imagenes", required = false) List<MultipartFile> imagenes,
                                          @RequestPart(value = "portada", required = false) MultipartFile portada) {
        try {
            Seccion seccion = seccionService.buscarSeccionPorId(noticiaDto.getSeccionId());
            if (seccion == null) throw new ResourceNotFoundException("Seccion", "id", noticiaDto.getAutorId());

            Autor autor = autorService.buscarAutorPorId(noticiaDto.getAutorId());
            if (autor == null) throw new ResourceNotFoundException("Autor", "id", noticiaDto.getAutorId());

            Noticia noticia = noticiaService.crearNoticia(noticiaDto, autor, seccion, imagenes, portada);
            NoticiaResDto noticiaResDto = noticiaMapper.toDTO(noticia);

            return new ResponseEntity<>(ResponseMessage.builder()
                    .mensaje("Noticia cargada")
                    .recurso(noticiaResDto)
                    .build(),
                    HttpStatus.CREATED);
        } catch (IOException ioException) {
            throw new ResourceBadRequestException(ioException.getMessage());
        }
    }

    @Operation(
            summary = "Edita una noticia por ID",
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
    @PutMapping("/noticia/{noticiaId}")
    public ResponseEntity<?> editarNoticia(@PathVariable("noticiaId") String noticiaId,
                                           @ModelAttribute @Valid NoticiaDto noticiaDto,
                                           @RequestPart(value = "imagenes", required = false) List<MultipartFile> imagenes,
                                           @RequestPart(value = "portada", required = false) MultipartFile portada) {
        try {
            Noticia noticiaExistente = noticiaService.buscarNoticiaPorId(noticiaId);
            if (noticiaExistente == null) throw new ResourceNotFoundException("Noticia", "id", noticiaId);

            Seccion seccion = seccionService.buscarSeccionPorId(noticiaDto.getSeccionId());
            if (seccion == null) throw new ResourceNotFoundException("Seccion", "id", noticiaDto.getAutorId());

            Autor autor = autorService.buscarAutorPorId(noticiaDto.getAutorId());
            if (autor == null) throw new ResourceNotFoundException("Autor", "id", noticiaDto.getAutorId());

            Noticia noticia = noticiaService.actualizarNoticia(noticiaExistente, noticiaDto, autor, seccion, imagenes, portada);

            NoticiaResDto noticiaResDto = noticiaMapper.toDTO(noticia);

            return new ResponseEntity<>(ResponseMessage.builder()
                    .mensaje("Noticia actualizada con éxito")
                    .recurso(noticiaResDto)
                    .build(), HttpStatus.OK);
        } catch (IOException ioException) {
            throw new ResourceBadRequestException(ioException.getMessage());
        }
    }

    @Operation(
            summary = "Busca una noticia por ID",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "La solicitud ha tenido éxito"),
                    @ApiResponse(responseCode = "404",
                            description = "El servidor no pudo encontrar el contenido solicitado"),
                    @ApiResponse(responseCode = "500",
                            description = "El servidor ha encontrado una situación que no sabe cómo manejarla")
            }
    )
    @GetMapping("/noticia/{noticiaId}")
    public ResponseEntity<?> buscarNoticiaPorId(@PathVariable("noticiaId") String noticiaId) {

        Noticia noticia = noticiaService.buscarNoticiaPorId(noticiaId);
        if (noticia == null) throw new ResourceNotFoundException("Noticia", "id", noticiaId);

        NoticiaResDto noticiaDto = noticiaMapper.toDTO(noticia);
        return new ResponseEntity<>(noticiaDto, HttpStatus.OK);
    }

    @Operation(
            summary = "Busca una noticia por titulo",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "La solicitud ha tenido éxito"),
                    @ApiResponse(responseCode = "404",
                            description = "El servidor no pudo encontrar el contenido solicitado"),
                    @ApiResponse(responseCode = "500",
                            description = "El servidor ha encontrado una situación que no sabe cómo manejarla")
            }
    )
    @GetMapping("/noticia")
    public ResponseEntity<?> buscarNoticiaPorTitulo(@RequestParam("titulo") String titulo) {

        List<Noticia> noticias = noticiaService.buscarNoticiaPorTitulo(titulo);
        if (noticias.isEmpty()) throw new ResourceNotFoundException("Noticia", "titulo", titulo);

        List<NoticiaResDto> noticiasDtos = noticiaMapper.toDTOs(noticias);
        return new ResponseEntity<>(noticiasDtos, HttpStatus.OK);
    }

    @Operation(
            summary = "Lista de noticias por seccion",
            description = "offset = n°pág - 1, limit = 10 | " +
                    "offset es el número de la página que deseas obtener y limit es el número de registros por página",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "La solicitud ha tenido éxito"),
                    @ApiResponse(responseCode = "404",
                            description = "El servidor no pudo encontrar el contenido solicitado"),
                    @ApiResponse(responseCode = "500",
                            description = "El servidor ha encontrado una situación que no sabe cómo manejarla")
            }
    )
    @GetMapping("/noticias/{seccion}")
    public ResponseEntity<?> buscarNoticiasPorSeccion(@PathVariable("seccion") String seccion,
                                                      @RequestParam("offset") Integer offset,
                                                      @RequestParam("limit") Integer limit) {

        List<Noticia> noticias = noticiaService.buscarNoticiasPorSeccion(seccion, offset, limit);
        if (noticias.isEmpty()) throw new ResourceNotFoundException("Noticias", "seccion", seccion);

        List<NoticiaResDto> noticiasDtos = noticiaMapper.toDTOs(noticias);
        return new ResponseEntity<>(noticiasDtos, HttpStatus.OK);
    }

    @Operation(
            summary = "Lista de noticias por autor",
            description = "offset = n°pág - 1, limit = 10 | " +
                    "offset es el número de la página que deseas obtener y limit es el número de registros por página",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "La solicitud ha tenido éxito"),
                    @ApiResponse(responseCode = "404",
                            description = "El servidor no pudo encontrar el contenido solicitado"),
                    @ApiResponse(responseCode = "500",
                            description = "El servidor ha encontrado una situación que no sabe cómo manejarla")
            }
    )
    @GetMapping("/noticias/autor")
    public ResponseEntity<?> buscarNoticiasPorAutor(@ModelAttribute @Valid AutorDto autorDto,
                                                    @RequestParam("offset") Integer offset,
                                                    @RequestParam("limit") Integer limit) {

        List<Noticia> noticias = noticiaService.buscarNoticiasPorAutor(autorDto.getNombre(), autorDto.getApellido(),
                offset, limit);
        if (noticias.isEmpty())
            throw new ResourceNotFoundException("noticias", "autor", autorDto.getNombre() + autorDto.getApellido());

        List<NoticiaResDto> noticiasDtos = noticiaMapper.toDTOs(noticias);
        return new ResponseEntity<>(noticiasDtos, HttpStatus.OK);
    }

    @Operation(
            summary = "Lista de noticias populares por seccion de los ultimos 14 dias",
            description = "offset = n°pág - 1, limit = 10 | " +
                    "offset es el número de la página que deseas obtener y limit es el número de registros por página",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "La solicitud ha tenido éxito"),
                    @ApiResponse(responseCode = "404",
                            description = "El servidor no pudo encontrar el contenido solicitado"),
                    @ApiResponse(responseCode = "500",
                            description = "El servidor ha encontrado una situación que no sabe cómo manejarla")
            }
    )
    @GetMapping("/noticias_populares/{seccion}")
    public ResponseEntity<?> buscarPopularesPorSeccion(@PathVariable("seccion") String seccion,
                                                       @RequestParam("offset") Integer offset,
                                                       @RequestParam("limit") Integer limit) {

        List<Noticia> noticias = noticiaService.buscarPopularesPorSeccion(seccion, offset, limit);
        if (noticias.isEmpty()) throw new ResourceNotFoundException("noticias populares", "seccion", seccion, 14);

        List<NoticiaResDto> noticiaDtos = noticiaMapper.toDTOs(noticias);
        return new ResponseEntity<>(noticiaDtos, HttpStatus.OK);
    }

    @Operation(
            summary = "Lista de noticias mas populares de los ultimos 14 dias",
            description = "offset = n°pág - 1, limit = 10 | " +
                    "offset es el número de la página que deseas obtener y limit es el número de registros por página",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "La solicitud ha tenido éxito"),
                    @ApiResponse(responseCode = "404",
                            description = "El servidor no pudo encontrar el contenido solicitado"),
                    @ApiResponse(responseCode = "500",
                            description = "El servidor ha encontrado una situación que no sabe cómo manejarla")
            }
    )
    @GetMapping("/noticias_populares")
    public ResponseEntity<?> buscarNoticiasMasPopulares(@RequestParam("offset") Integer offset,
                                                        @RequestParam("limit") Integer limit) {
        List<Noticia> noticias = noticiaService.buscarNoticiasMasPopulares(offset, limit);
        if (noticias.isEmpty()) throw new ResourceNotFoundException("Noticias populares", 14);

        List<NoticiaResDto> noticiaDtos = noticiaMapper.toDTOs(noticias);
        return new ResponseEntity<>(noticiaDtos, HttpStatus.OK);
    }

    @Operation(
            summary = "Lista de noticias (de la mas reciente a la mas antigua)",
            description = "offset = n°pág - 1, limit = 10 | " +
                    "offset es el número de la página que deseas obtener y limit es el número de registros por página",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "La solicitud ha tenido éxito"),
                    @ApiResponse(responseCode = "404",
                            description = "El servidor no pudo encontrar el contenido solicitado"),
                    @ApiResponse(responseCode = "500",
                            description = "El servidor ha encontrado una situación que no sabe cómo manejarla")
            }
    )
    @GetMapping(value = "/noticias")
    public ResponseEntity<?> buscarNoticias(
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit) {

        List<Noticia> noticias = noticiaService.buscarNoticias(offset, limit);
        if (noticias.isEmpty()) throw new ResourceNotFoundException("Noticias");

        List<NoticiaResDto> noticiasDtos = noticiaMapper.toDTOs(noticias);
        return new ResponseEntity<>(noticiasDtos, HttpStatus.OK);
    }

    @Operation(
            summary = "Elimina una noticia por ID",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "La solicitud ha tenido éxito"),
                    @ApiResponse(responseCode = "500",
                            description = "El servidor ha encontrado una situación que no sabe cómo manejarla")
            }
    )
    @DeleteMapping("/noticia/{noticiaId}")
    public ResponseEntity<?> eliminarNoticiaPorId(@PathVariable("noticiaId") String noticiaId) {
        noticiaService.eliminarNoticiaPorId(noticiaId);
        return new ResponseEntity<>(ResponseMessage.builder()
                .mensaje("Noticia eliminada")
                .build(), HttpStatus.OK);
    }
}
