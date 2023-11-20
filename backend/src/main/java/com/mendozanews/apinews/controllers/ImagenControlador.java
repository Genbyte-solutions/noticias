package com.mendozanews.apinews.controllers;

import com.mendozanews.apinews.mapper.ImagenesMapper;
import com.mendozanews.apinews.mapper.ImagenesToHeaders;
import com.mendozanews.apinews.model.dto.request.ImagenDto;
import com.mendozanews.apinews.model.entidades.*;
import com.mendozanews.apinews.servicios.impl.*;
import com.mendozanews.apinews.servicios.interfaces.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ImagenControlador {
    private final IAutor autorServicio;
    private final IUsuario usuarioServicio;
    private final IImagen imagenServicio;
    private final ISeccion seccionServicio;
    private final INoticia noticiaServicio;
    private final ImagenesToHeaders imagenesToHeaders;
    private final ImagenesMapper imagenesMapper;

    public ImagenControlador(AutorServicio autorServicio, ImagenServicio imagenServicio, SeccionServicio seccionServicio,
                             ImagenesToHeaders imagenesToHeaders, ImagenesMapper imagenesMapper, UsuarioServicio usuarioServicio,
                             NoticiaServicio noticiaServicio) {
        this.imagenServicio = imagenServicio;
        this.autorServicio = autorServicio;
        this.seccionServicio = seccionServicio;
        this.imagenesToHeaders = imagenesToHeaders;
        this.imagenesMapper = imagenesMapper;
        this.usuarioServicio = usuarioServicio;
        this.noticiaServicio = noticiaServicio;
    }

    @GetMapping("imagen/autor/{id}")
    public ResponseEntity<?> obtenerImagenPorAutor(@PathVariable("id") String id) {
        Autor autor = this.autorServicio.buscarAutorPorId(id);
        if (autor == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Imagen fotoAutor = this.imagenServicio.buscarImagenPorId(autor.getFoto().getImagenId());
        ImagenDto imagenDto = imagenesMapper.toDTO(fotoAutor);
        HttpHeaders headers = imagenesToHeaders.generarHeaders(imagenDto);

        return ResponseEntity.ok()
                .headers(headers)
                .body(imagenDto.getContenido());
    }

    // No entiendo que hace esto
    @GetMapping("icono/seccion/{id}")
    public ResponseEntity<?> obtenerIconoPorSeccion(@PathVariable("id") String id) {
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

    @GetMapping("imagen/usuario/{id}")
    public ResponseEntity<?> obtenerImagenPorUsuario(@PathVariable("id") String id) {
        Usuario usuario = this.usuarioServicio.buscarUsuarioPorId(id);
        if (usuario == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Imagen fotoUsuario = imagenServicio.buscarImagenPorId(usuario.getFoto().getImagenId());
        ImagenDto imagenDto = imagenesMapper.toDTO(fotoUsuario);
        HttpHeaders headers = imagenesToHeaders.generarHeaders(imagenDto);

        return ResponseEntity.ok()
                .headers(headers)
                .body(imagenDto.getContenido());
    }

    @GetMapping("portada/noticia/{id}")
    public ResponseEntity<?> obtenerPortadaPorNoticia(@PathVariable("id") String id) {
        Noticia noticia = noticiaServicio.buscarNoticiaPorId(id);
        if (noticia == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Portada portadaNoticia = noticiaServicio.buscarPortadaPorId(noticia.getPortada().getPortadaId());
        ImagenDto imagenDto = imagenesMapper.toDTO(portadaNoticia);
        HttpHeaders headers = imagenesToHeaders.generarHeaders(imagenDto);

        return ResponseEntity.ok()
                .headers(headers)
                .body(imagenDto.getContenido());
    }

    @GetMapping("imagen/noticia/{id}")
    public ResponseEntity<?> obtenerImagenPorNoticia(@PathVariable("id") String id) {

        ImagenesNoticia imagenesNoticia = noticiaServicio.buscarImagenNoticiaPorId(id);
        ImagenDto imagenDto = imagenesMapper.toDTO(imagenesNoticia);
        HttpHeaders headers = imagenesToHeaders.generarHeaders(imagenDto);

        return ResponseEntity.ok()
                .headers(headers)
                .body(imagenDto.getContenido());
    }

    @GetMapping("imagenes/noticia/{id}")
    public ResponseEntity<?> obtenerImagenesPorNoticia(@PathVariable("id") String id) {
        Noticia noticia = noticiaServicio.buscarNoticiaPorId(id);
        if (noticia == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<String> imagenesNoticia = noticia.getImagenesNoticia().stream()
                .map(ImagenesNoticia::getImagenNoticiaId)
                .toList();
        return new ResponseEntity<>(imagenesNoticia,HttpStatus.OK);
    }
}
