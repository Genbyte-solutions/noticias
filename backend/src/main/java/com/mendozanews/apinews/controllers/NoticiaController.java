package com.mendozanews.apinews.controllers;

import com.mendozanews.apinews.mapper.NoticiaMapper;
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

    private final NoticiaMapper noticiaMapper;

    public NoticiaController(INoticia noticiaService, IPortada portadaService,
                             ISeccion seccionService, IAutor autorService,
                             NoticiaMapper noticiaMapper) {
        this.noticiaService = noticiaService;
        this.portadaService = portadaService;
        this.seccionService = seccionService;
        this.autorService = autorService;
        this.noticiaMapper = noticiaMapper;
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
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit) {

        try {

            List<Noticia> noticias = noticiaService.listarNoticias(offset, limit);
            if (noticias.isEmpty()) return new ResponseEntity<>(
                    "No se encontraron noticias",
                    HttpStatus.NOT_FOUND);
            List<NoticiaDto> noticiasDtos = noticiaMapper.toDTOs(noticias);
            return new ResponseEntity<>(noticiasDtos, HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/noticia/titulo")
    public ResponseEntity<?> buscarNoticiaPorTitulo(@RequestParam("titulo") String titulo,
                                                    @RequestParam("offset") Integer offset,
                                                    @RequestParam("limit") Integer limit) {

        List<Noticia> noticias = noticiaService.buscarPorTitulo(titulo,offset,limit);
        if (noticias.isEmpty()) return new ResponseEntity<>(
                "No se encontraron noticias",
                HttpStatus.NOT_FOUND);
        List<NoticiaDto> noticiasDtos = noticiaMapper.toDTOs(noticias);
        return new ResponseEntity<>(noticiasDtos, HttpStatus.OK);
    }

    @GetMapping("/noticia/autor")
    public ResponseEntity<?> buscarNoticiaPorAutor(@RequestParam("nombre") String nombre,
                                                   @RequestParam("apellido") String apellido,
                                                    @RequestParam("offset") Integer offset,
                                                    @RequestParam("limit") Integer limit) {

        List<Noticia> noticias = noticiaService.buscarPorAutor(nombre,apellido,offset,limit);
        if (noticias.isEmpty()) return new ResponseEntity<>(
                "No se encontraron noticias",
                HttpStatus.NOT_FOUND);
        List<NoticiaDto> noticiasDtos = noticiaMapper.toDTOs(noticias);
        return new ResponseEntity<>(noticiasDtos, HttpStatus.OK);
    }

    @GetMapping("/noticia/seccion")
    public ResponseEntity<?> buscarNoticiaPorSeccion(@RequestParam("seccion") String seccion,
                                                   @RequestParam("offset") Integer offset,
                                                   @RequestParam("limit") Integer limit) {

        List<Noticia> noticias = noticiaService.buscarPorSeccion(seccion,offset,limit);
        if (noticias.isEmpty()) return new ResponseEntity<>(
                "No se encontraron noticias",
                HttpStatus.NOT_FOUND);
        List<NoticiaDto> noticiasDtos = noticiaMapper.toDTOs(noticias);
        return new ResponseEntity<>(noticiasDtos, HttpStatus.OK);
    }


    @DeleteMapping("/noticia/{noticiaId}")
    public ResponseEntity<?> eliminarNoticiaPorId(@PathVariable("noticiaId") String noticiaId) {

        noticiaService.eliminarNoticiaPorId(noticiaId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
