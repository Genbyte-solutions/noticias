package com.mendozanews.apinews.controllers;

import com.mendozanews.apinews.model.entidades.*;
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
public class NoticiaController {
    private final INoticia noticiaService;
    private final IPortada portadaService;
    private final ISeccion seccionService;
    private final IAutor autorService;

    public NoticiaController(INoticia noticiaService, IPortada portadaService, ISeccion seccionService, IAutor autorService) {
        this.noticiaService = noticiaService;
        this.portadaService = portadaService;
        this.seccionService = seccionService;
        this.autorService = autorService;
    }

    @PostMapping(value = "/noticia")
    public ResponseEntity<?> crearNoticia(@ModelAttribute @Valid NoticiaDto noticiaDto,
                                          @RequestParam("imagenes") List<MultipartFile> imagenes,
                                          @RequestParam("portada") MultipartFile portada) {
        try {
            Seccion seccion = seccionService.buscarSeccion(noticiaDto.getSeccionId());
            Autor autor = autorService.buscarAutorPorId(noticiaDto.getAutorId());

            if (imagenes.isEmpty() || portada == null) return new ResponseEntity<>(
                    "Debe proporcionar la portada e imagenes sobre la noticia",
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
        if (portada == null) return new ResponseEntity<>(
                "Noticia no encontrada",
                HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(portada, HttpStatus.OK);
    }

    @GetMapping(value = "/noticias/recientes")
    public ResponseEntity<?> obtenerNoticiasRecientes(
            @RequestParam("limit") Integer limit,
            @RequestParam("offset") Integer offset) {

        try {

            List<Noticia> noticias = noticiaService.listarNoticias(limit, offset);
            if (noticias.isEmpty()) return new ResponseEntity<>(
                    "No se encontraron noticias",
                    HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(noticias, HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/noticia")
    public ResponseEntity<?> buscarNoticiaPorTitulo(@RequestParam("titulo") String titulo) {

        List<Noticia> noticias = noticiaService.buscarPorTitulo(titulo);
        if (noticias.isEmpty()) return new ResponseEntity<>(
                "No se encontraron noticias",
                HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(noticias, HttpStatus.OK);
    }

    @DeleteMapping("/noticia/{noticiaId}")
    public ResponseEntity<?> eliminarNoticiaPorId(@PathVariable("noticiaId") String noticiaId) {

        noticiaService.eliminarNoticiaPorId(noticiaId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
