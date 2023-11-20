package com.mendozanews.apinews.controllers;

import com.mendozanews.apinews.mapper.ImagenesMapper;
import com.mendozanews.apinews.mapper.ImagenesToHeaders;
import com.mendozanews.apinews.model.dto.request.ImagenDto;
import com.mendozanews.apinews.model.entidades.*;
import com.mendozanews.apinews.servicios.impl.AutorServicio;
import com.mendozanews.apinews.servicios.impl.ImagenServicio;
import com.mendozanews.apinews.servicios.impl.SeccionServicio;
import com.mendozanews.apinews.servicios.impl.UsuarioServicio;
import com.mendozanews.apinews.servicios.interfaces.IAutor;
import com.mendozanews.apinews.servicios.interfaces.IImagen;
import com.mendozanews.apinews.servicios.interfaces.ISeccion;
import com.mendozanews.apinews.servicios.interfaces.IUsuario;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/imagen")
public class ImagenControlador {
    private final IAutor autorServicio;
    private final IUsuario usuarioServicio;
    private final IImagen imagenServicio;
    private final ISeccion seccionServicio;
    private final ImagenesToHeaders imagenesToHeaders;
    private final ImagenesMapper imagenesMapper;

    public ImagenControlador(AutorServicio autorServicio, ImagenServicio imagenServicio, SeccionServicio seccionServicio,
                             ImagenesToHeaders imagenesToHeaders, ImagenesMapper imagenesMapper, UsuarioServicio usuarioServicio) {
        this.imagenServicio = imagenServicio;
        this.autorServicio = autorServicio;
        this.seccionServicio = seccionServicio;
        this.imagenesToHeaders = imagenesToHeaders;
        this.imagenesMapper = imagenesMapper;
        this.usuarioServicio = usuarioServicio;
    }

    @GetMapping("/autor/{id}")
    public ResponseEntity<?> obtenerImagenPorAutor(@PathVariable("id") String id) {
        Autor autor = this.autorServicio.buscarAutorPorId(id);
        if (autor == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Imagen imagenAutor = this.imagenServicio.buscarImagenPorId(autor.getFoto().getImagenId());
        ImagenDto imagenDto = imagenesMapper.toDTO(imagenAutor);
        HttpHeaders headers = imagenesToHeaders.generarHeaders(imagenDto);

        return ResponseEntity.ok()
                .headers(headers)
                .body(imagenDto.getContenido());
    }

    // No entiendo que hace esto
    @GetMapping("/seccion/{id}")
    public ResponseEntity<?> obtenerImagenPorSeccion(@PathVariable("id") String id) {
        Seccion seccion = this.seccionServicio.buscarSeccionPorId(id);
        if (seccion == null) {
            return new ResponseEntity<>("Seccion no encontrada", HttpStatus.NOT_FOUND);
        }

        IconoSeccion iconoSeccion = this.seccionServicio.buscarIconoPorSeccionId(seccion.getIcono().getIconoSeccionId());
        ImagenDto imagenDto = imagenesMapper.toDTO(iconoSeccion);
        HttpHeaders headers = imagenesToHeaders.generarHeaders(imagenDto);

        return ResponseEntity.ok()
                .headers(headers)
                .body(imagenDto.getContenido());
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<?> obtenerImagenPorUsuario(@PathVariable("id") String id) {
        Usuario usuario = this.usuarioServicio.buscarUsuarioPorId(id);
        if (usuario == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Imagen imagen = imagenServicio.buscarImagenPorId(usuario.getFoto().getImagenId());
        ImagenDto imagenDto = imagenesMapper.toDTO(imagen);
        HttpHeaders headers = imagenesToHeaders.generarHeaders(imagenDto);

        return ResponseEntity.ok()
                .headers(headers)
                .body(imagenDto.getContenido());
    }
}
