package com.mendozanews.apinews.controllers;

import com.mendozanews.apinews.mapper.NoticiaMapper;
import com.mendozanews.apinews.model.dto.request.AutorDto;
import com.mendozanews.apinews.model.dto.response.NoticiaResDto;
import com.mendozanews.apinews.model.entidades.*;
import com.mendozanews.apinews.model.enums.Orden;
import com.mendozanews.apinews.servicios.interfaces.*;
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
@RequestMapping("/api/v1")
@Validated
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

    @PostMapping(value = "/noticia")
    public ResponseEntity<?> crearNoticia(@ModelAttribute @Valid NoticiaDto noticiaDto,
            @RequestPart(value = "imagenes", required = false) List<MultipartFile> imagenes,
            @RequestPart(value = "portada", required = false) MultipartFile portada) {
        try {
            Seccion seccion = this.seccionService.buscarSeccion(noticiaDto.getSeccionId());
            Autor autor = this.autorService.buscarAutorPorId(noticiaDto.getAutorId());

            if (autor == null || seccion == null)
                return new ResponseEntity<>(
                        "Autor o seccion no encontrada",
                        HttpStatus.NOT_FOUND);
            if (imagenes.isEmpty() || portada == null)
                return new ResponseEntity<>(
                        "Debe proporcionar la portada y almenos 1 imagen sobre la noticia",
                        HttpStatus.BAD_REQUEST);

            String noticiaId = noticiaService.crearNoticia(noticiaDto, autor, seccion, imagenes, portada);

            return new ResponseEntity<>(
                    "Noticia cargada con éxito. ID de la noticia: " + noticiaId,
                    HttpStatus.CREATED);
        } catch (IOException e) {

            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @GetMapping(value = "/noticia/{noticiaId}/portada")
    public ResponseEntity<?> obtenerPortada(@PathVariable("noticiaId") String noticiaId) {

        Portada portada = noticiaService.buscarNoticiaPorId(noticiaId).getPortada();
        if (portada == null)
            return new ResponseEntity<>(
                    "Noticia no encontrada",
                    HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(portada, HttpStatus.OK);
    }

    @GetMapping(value = "/noticias/recientes")
    public ResponseEntity<?> buscarNoticiasRecientes(
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit,
            @RequestParam("orden") Orden orden) {

        List<Noticia> noticias = noticiaService.buscarNoticiasRecientes(offset, limit);

        if (noticias.isEmpty())
            return new ResponseEntity<>(
                    "No se encontraron noticias",
                    HttpStatus.NOT_FOUND);
        List<NoticiaResDto> noticiasDtos = noticiaMapper.toDTOs(noticias);
        return new ResponseEntity<>(noticiasDtos, HttpStatus.OK);
    }

    @GetMapping("/noticia/{noticiaId}")
    public ResponseEntity<?> buscarNoticiaPorId(@PathVariable("noticiaId") String noticiaId) {

        Noticia noticia = noticiaService.buscarNoticiaPorId(noticiaId);

        if (noticia == null) {
            return new ResponseEntity<>("Noticia no encontrada", HttpStatus.NOT_FOUND);
        }

        NoticiaResDto noticiaDto = noticiaMapper.toDTO(noticia);

        return new ResponseEntity<>(noticiaDto, HttpStatus.OK);
    }

    @GetMapping("/noticia")
    public ResponseEntity<?> buscarNoticiaPorTitulo(@RequestParam("titulo") String titulo) {

        List<Noticia> noticias = this.noticiaService.buscarNoticiaPorTitulo(titulo);
        if (noticias.isEmpty())
            return new ResponseEntity<>(
                    "No se encontraron noticias",
                    HttpStatus.NOT_FOUND);
        List<NoticiaResDto> noticiasDtos = noticiaMapper.toDTOs(noticias);
        return new ResponseEntity<>(noticiasDtos, HttpStatus.OK);
    }

    @GetMapping("/noticias/{seccion}")
    public ResponseEntity<?> buscarNoticiasPorSeccion(@PathVariable("seccion") String seccion,
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit) {

        List<Noticia> noticias = noticiaService.buscarNoticiasPorSeccion(seccion, offset, limit);

        if (noticias.isEmpty())
            return new ResponseEntity<>(
                    "No se encontraron noticias",
                    HttpStatus.NOT_FOUND);
        List<NoticiaResDto> noticiasDtos = noticiaMapper.toDTOs(noticias);
        return new ResponseEntity<>(noticiasDtos, HttpStatus.OK);
    }

    @GetMapping("/noticias/autor")
    public ResponseEntity<?> buscarNoticiasPorAutor(@RequestParam AutorDto autorDto,
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit) {

        List<Noticia> noticias = noticiaService.buscarNoticiasPorAutor(autorDto.getNombre(), autorDto.getApellido(),
                offset, limit);

        if (noticias.isEmpty())
            return new ResponseEntity<>(
                    "No se encontraron noticias",
                    HttpStatus.NOT_FOUND);
        List<NoticiaResDto> noticiasDtos = noticiaMapper.toDTOs(noticias);
        return new ResponseEntity<>(noticiasDtos, HttpStatus.OK);
    }

    @GetMapping("/noticias_populares/{seccion}")
    public ResponseEntity<?> buscarPopularesPorSeccion(@PathVariable("seccion") String seccion,
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit) {

        List<Noticia> noticias = noticiaService.buscarPopularesPorSeccion(seccion, offset, limit);
        if (noticias.isEmpty())
            return new ResponseEntity<>(
                    String.format("No hay noticias populares en la seccion %s en ultimos 14 dias", seccion),
                    HttpStatus.NOT_FOUND);

        List<NoticiaResDto> noticiaDtos = noticiaMapper.toDTOs(noticias);
        return new ResponseEntity<>(noticiaDtos, HttpStatus.OK);
    }

    @GetMapping("/noticias_populares")
    public ResponseEntity<?> buscarNoticiasMasPopulares(@RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit) {
        List<Noticia> noticias = noticiaService.buscarNoticiasMasPopulares(offset, limit);
        if (noticias.isEmpty())
            return new ResponseEntity<>("No hay noticias populares de los ultimos 14 dias", HttpStatus.NOT_FOUND);

        List<NoticiaResDto> noticiaDtos = noticiaMapper.toDTOs(noticias);

        return new ResponseEntity<>(noticiaDtos, HttpStatus.OK);
    }

    @DeleteMapping("/noticia/{noticiaId}")
    public ResponseEntity<?> eliminarNoticiaPorId(@PathVariable("noticiaId") String noticiaId) {

        noticiaService.eliminarNoticiaPorId(noticiaId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
