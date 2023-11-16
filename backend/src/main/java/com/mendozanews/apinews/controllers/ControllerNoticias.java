package com.mendozanews.apinews.controllers;

import com.mendozanews.apinews.model.entidades.*;
import com.mendozanews.apinews.servicios.interfaces.IImagen;
import com.mendozanews.apinews.servicios.interfaces.INoticia;
import com.mendozanews.apinews.servicios.interfaces.IPortada;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.mendozanews.apinews.excepciones.MiException;
import com.mendozanews.apinews.model.dto.request.NoticiaDto;
import com.mendozanews.apinews.repositorios.NoticiaRepositorio;
import com.mendozanews.apinews.repositorios.SeccionRepositorio;
import com.mendozanews.apinews.repositorios.AutorRepositorio;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Validated
public class ControllerNoticias {
    private final INoticia noticiaService;
    private final IImagen imagenService;
    private final IPortada portadaService;
    private final SeccionRepositorio seccionRepositorio;
    private final AutorRepositorio autorRepositorio;
    private final NoticiaRepositorio noticiaRepositorio;

    public ControllerNoticias(INoticia noticiaService, IImagen imagenService, IPortada portadaService, SeccionRepositorio seccionRepositorio, AutorRepositorio autorRepositorio, NoticiaRepositorio noticiaRepositorio) {
        this.noticiaService = noticiaService;
        this.imagenService = imagenService;
        this.portadaService = portadaService;
        this.seccionRepositorio = seccionRepositorio;
        this.autorRepositorio = autorRepositorio;
        this.noticiaRepositorio = noticiaRepositorio;
    }

    @PostMapping(value = "/noticia")
    public ResponseEntity<?> guardarNoticia(@ModelAttribute @Valid NoticiaDto noticiaDto,
                                            @RequestParam("imagenes") MultipartFile[] imagenes,
                                            @RequestParam("portada") MultipartFile portada) {
        String idPortada = null;
        try {

            Seccion seccion = seccionRepositorio.findById(noticiaDto.getSeccionId())
                    .orElseThrow(() -> new MiException("Sección no encontrada"));

            Autor autor = autorRepositorio.findById(noticiaDto.getAutorId())
                    .orElseThrow(() -> new MiException("Autor no encontrado"));

            Portada portadaGuardada = portadaService.guardarPortada(portada);
            Noticia noticiaGuardada = noticiaService.guardarNoticia(noticiaDto, autor, seccion);

            List<Imagen> imagenGuardada = imagenService.guardarImagenes(imagenes, noticiaGuardada);

            idPortada = noticiaGuardada.getPortada().getPortadaId(); // Corregir acceso al ID de la portada

            return new ResponseEntity<>("Noticia cargada con éxito. ID de portada: " + idPortada, HttpStatus.OK);
        } catch (MiException e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>("Error al cargar la noticia: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping(value = "/noticias/{idNoticia}/portada")
    public ResponseEntity<byte[]> obtenerImagenPortada(@PathVariable String idNoticia) {
        try {
            Optional<Noticia> noticiaOptional = noticiaRepositorio.findById(idNoticia);

            if (noticiaOptional.isPresent()) {
                Noticia noticia = noticiaOptional.get();
                Portada portada = noticia.getPortada();

                if (portada == null) {
                    return ResponseEntity.notFound().build();
                }

                byte[] portadaImagen = portada.getImagen();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.parseMediaType(portada.getTipoMime()));
                headers.setContentLength(portadaImagen.length);

                return new ResponseEntity<>(portadaImagen, headers, HttpStatus.OK);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/noticias/recientes", produces = "application/json")
    public ResponseEntity<?> obtenerNoticiasRecientes() {
        try {

            noticiaServicio.listarNoticiasRecientesDto(1, 48);
            return new ResponseEntity<>(noticiaServicio.listarNoticiasRecientesDto(1, 48), HttpStatus.OK);
        } catch (MiException e) {
            String errorMessage = "Se produjo una excepción al obtener las noticias recientes: " + e.getMessage();
            System.err.println(errorMessage);
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            String errorMessage = "Se produjo un error desconocido al obtener las noticias recientes: " + e.getMessage();
            System.err.println(errorMessage);
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
