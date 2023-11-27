package com.mendozanews.apinews.controller;

import com.mendozanews.apinews.exception.ResourceNotFoundException;
import com.mendozanews.apinews.mapper.ImagenesMapper;
import com.mendozanews.apinews.mapper.ImagenesToHeaders;
import com.mendozanews.apinews.model.dto.request.ImagenDto;
import com.mendozanews.apinews.model.entity.*;
import com.mendozanews.apinews.service.impl.*;
import com.mendozanews.apinews.service.interfaces.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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


    @Operation(
            summary = "Busca la foto del autor por ID del autor",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "La solicitud ha tenido éxito"),
                    @ApiResponse(responseCode = "404",
                            description = "El servidor no pudo encontrar el contenido solicitado"),
                    @ApiResponse(responseCode = "500",
                            description = "El servidor ha encontrado una situación que no sabe cómo manejarla")
            }
    )
    @GetMapping("imagen/autor/{autorId}")
    public ResponseEntity<?> obtenerImagenPorAutor(@PathVariable("autorId") String autorId) {
        Autor autor = autorServicio.buscarAutorPorId(autorId);
        if (autor == null) throw new ResourceNotFoundException("Autor", "id", autorId);

        Imagen fotoAutor = imagenServicio.buscarImagenPorId(autor.getFoto().getImagenId());
        ImagenDto imagenDto = imagenesMapper.toDTO(fotoAutor);
        HttpHeaders headers = imagenesToHeaders.generarHeaders(imagenDto);

        return ResponseEntity.ok()
                .headers(headers)
                .body(imagenDto.getContenido());
    }

    @Operation(
            summary = "Busca el icono de la seccion por ID de seccion",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "La solicitud ha tenido éxito"),
                    @ApiResponse(responseCode = "404",
                            description = "El servidor no pudo encontrar el contenido solicitado"),
                    @ApiResponse(responseCode = "500",
                            description = "El servidor ha encontrado una situación que no sabe cómo manejarla")
            }
    )
    @GetMapping("icono/seccion/{seccionId}")
    public ResponseEntity<?> obtenerIconoPorSeccion(@PathVariable("seccionId") String seccionId) {
        Seccion seccion = seccionServicio.buscarSeccionPorId(seccionId);
        if (seccion == null) throw new ResourceNotFoundException("Seccion", "id", seccionId);

        IconoSeccion iconoSeccion = seccionServicio.buscarIconoPorSeccionId(seccion.getIcono().getIconoSeccionId());
        ImagenDto imagenDto = imagenesMapper.toDTO(iconoSeccion);
        HttpHeaders headers = imagenesToHeaders.generarHeaders(imagenDto);

        return ResponseEntity.ok()
                .headers(headers)
                .body(imagenDto.getContenido());
    }

    @Operation(
            summary = "Busca la foto del usuario por ID de usuario",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "La solicitud ha tenido éxito"),
                    @ApiResponse(responseCode = "404",
                            description = "El servidor no pudo encontrar el contenido solicitado"),
                    @ApiResponse(responseCode = "500",
                            description = "El servidor ha encontrado una situación que no sabe cómo manejarla")
            }
    )
    @GetMapping("imagen/usuario/{usuarioId}")
    public ResponseEntity<?> obtenerImagenPorUsuario(@PathVariable("usuarioId") String usuarioId) {
        Usuario usuario = usuarioServicio.buscarUsuarioPorId(usuarioId);
        if (usuario == null) throw new ResourceNotFoundException("Usuario", "id", usuarioId);

        Imagen fotoUsuario = imagenServicio.buscarImagenPorId(usuario.getFoto().getImagenId());
        ImagenDto imagenDto = imagenesMapper.toDTO(fotoUsuario);
        HttpHeaders headers = imagenesToHeaders.generarHeaders(imagenDto);

        return ResponseEntity.ok()
                .headers(headers)
                .body(imagenDto.getContenido());
    }

    @Operation(
            summary = "Busca la portada de la noticia por ID de noticia",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "La solicitud ha tenido éxito"),
                    @ApiResponse(responseCode = "404",
                            description = "El servidor no pudo encontrar el contenido solicitado"),
                    @ApiResponse(responseCode = "500",
                            description = "El servidor ha encontrado una situación que no sabe cómo manejarla")
            }
    )
    @GetMapping("portada/noticia/{noticiaId}")
    public ResponseEntity<?> obtenerPortadaPorNoticia(@PathVariable("noticiaId") String noticiaId) {
        Noticia noticia = noticiaServicio.buscarNoticiaPorId(noticiaId);
        if (noticia == null) throw new ResourceNotFoundException("Noticia", "id", noticiaId);

        Portada portadaNoticia = noticiaServicio.buscarPortadaPorId(noticia.getPortada().getPortadaId());
        ImagenDto imagenDto = imagenesMapper.toDTO(portadaNoticia);
        HttpHeaders headers = imagenesToHeaders.generarHeaders(imagenDto);

        return ResponseEntity.ok()
                .headers(headers)
                .body(imagenDto.getContenido());
    }

    @Operation(
            summary = "Busca una noticia por ID de la imagen",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "La solicitud ha tenido éxito"),
                    @ApiResponse(responseCode = "404",
                            description = "El servidor no pudo encontrar el contenido solicitado"),
                    @ApiResponse(responseCode = "500",
                            description = "El servidor ha encontrado una situación que no sabe cómo manejarla")
            }
    )
    @GetMapping("imagen/noticia/{imagenNoticiaId}")
    public ResponseEntity<?> obtenerImagenPorNoticia(@PathVariable("imagenNoticiaId") String imagenNoticiaId) {

        ImagenesNoticia imagenesNoticia = noticiaServicio.buscarImagenNoticiaPorId(imagenNoticiaId);
        if (imagenesNoticia == null) throw new ResourceNotFoundException("Imagen noticia", "id", imagenNoticiaId);

        ImagenDto imagenDto = imagenesMapper.toDTO(imagenesNoticia);
        HttpHeaders headers = imagenesToHeaders.generarHeaders(imagenDto);

        return ResponseEntity.ok()
                .headers(headers)
                .body(imagenDto.getContenido());
    }

    @Operation(
            summary = "Busca las imagenes de la noticia por el ID de noticia",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "La solicitud ha tenido éxito"),
                    @ApiResponse(responseCode = "404",
                            description = "El servidor no pudo encontrar el contenido solicitado"),
                    @ApiResponse(responseCode = "500",
                            description = "El servidor ha encontrado una situación que no sabe cómo manejarla")
            }
    )
    @GetMapping("imagenes/noticia/{noticiaId}")
    public ResponseEntity<?> obtenerImagenesPorNoticia(@PathVariable("noticiaId") String noticiaId) {
        Noticia noticia = noticiaServicio.buscarNoticiaPorId(noticiaId);
        if (noticia == null) throw new ResourceNotFoundException("Noticia", "id", noticiaId);

        List<String> imagenesNoticia = noticia.getImagenesNoticia().stream()
                .map(ImagenesNoticia::getImagenNoticiaId)
                .toList();
        return new ResponseEntity<>(imagenesNoticia, HttpStatus.OK);
    }
}
