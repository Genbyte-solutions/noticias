package com.mendozanews.apinews.controllers;

import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.mendozanews.apinews.excepciones.MiException;
import com.mendozanews.apinews.model.dto.NoticiaRequestDto;
import com.mendozanews.apinews.model.entidades.Autor;
import com.mendozanews.apinews.model.entidades.Noticia;
import com.mendozanews.apinews.model.entidades.Portada;
import com.mendozanews.apinews.model.entidades.Seccion;
import com.mendozanews.apinews.repositorios.NoticiaRepositorio;
import com.mendozanews.apinews.repositorios.SeccionRepositorio;
import com.mendozanews.apinews.servicios.NoticiaServicio;
import com.mendozanews.apinews.repositorios.AutorRepositorio;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Validated
public class ControllerNoticias {
    private final NoticiaServicio noticiaServicio;
    private final SeccionRepositorio seccionRepositorio;
    private final AutorRepositorio autorRepositorio;
    private final NoticiaRepositorio noticiaRepositorio;

    public ControllerNoticias(NoticiaServicio noticiaServicio, SeccionRepositorio seccionRepositorio,
            AutorRepositorio autorRepositorio, NoticiaRepositorio noticiaRepositorio) {
        this.noticiaServicio = noticiaServicio;
        this.seccionRepositorio = seccionRepositorio;
        this.autorRepositorio = autorRepositorio;
        this.noticiaRepositorio = noticiaRepositorio;
    }

    @PostMapping(value = "/noticias")
    public ResponseEntity<?> cargarNoticia(@ModelAttribute @Valid NoticiaRequestDto request,
            @RequestParam("imagenes") MultipartFile[] imagenes,
            @RequestParam("portada") MultipartFile portada) {
        String idPortada = null;
        try {

            Seccion seccion = seccionRepositorio.findById(request.getIdSeccion().getId())
                    .orElseThrow(() -> new MiException("Sección no encontrada"));

            Autor autor = autorRepositorio.findById(request.getIdAutor().getId())
                    .orElseThrow(() -> new MiException("Autor no encontrado"));

            Noticia noticiaCargada = noticiaServicio.cargarNoticia(imagenes, portada, request.getTitulo(),
                    request.getSubtitulo(), request.getParrafos(), request.getEtiquetas(), seccion, request.getIdAutor());

            idPortada = noticiaCargada.getPortada().getId(); // Corregir acceso al ID de la portada
    
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
               headers.setContentType(MediaType.parseMediaType(portada.getMime()));  
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
        
       noticiaServicio.listarNoticiasRecientesDto(1,48);
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
