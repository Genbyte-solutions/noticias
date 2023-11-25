package com.mendozanews.apinews.controller;

import com.mendozanews.apinews.exception.ResourceBadRequestException;
import com.mendozanews.apinews.exception.ResourceNotFoundException;
import com.mendozanews.apinews.mapper.SeccionMapper;
import com.mendozanews.apinews.model.dto.request.SeccionDto;
import com.mendozanews.apinews.model.dto.response.SeccionResDto;
import com.mendozanews.apinews.model.entity.Seccion;
import com.mendozanews.apinews.model.payload.ResponseMessage;
import com.mendozanews.apinews.service.interfaces.ISeccion;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@Validated
@RequestMapping("api/v1")
public class SeccionControlador {

    private final ISeccion seccionService;
    private final SeccionMapper seccionMapper;

    public SeccionControlador(ISeccion seccionService, SeccionMapper seccionMapper) {
        this.seccionService = seccionService;
        this.seccionMapper = seccionMapper;
    }

    @PostMapping("/seccion")
    public ResponseEntity<?> crearSeccion(
            @ModelAttribute @Valid SeccionDto seccionDto,
            @RequestPart(value = "icono", required = false) MultipartFile icono) throws IOException {

        Seccion seccion = seccionService.crearSeccion(seccionDto, icono);
        SeccionResDto seccionResDto = seccionMapper.toDTO(seccion);

        return new ResponseEntity<>(ResponseMessage.builder()
                .mensaje("Sección añadida")
                .recurso(seccionResDto)
                .build(), HttpStatus.CREATED);
    }

    @PutMapping("/seccion/{seccionId}")
    public ResponseEntity<?> editarSeccion(@PathVariable("seccionId") String seccionId,
                                           @ModelAttribute @Valid SeccionDto seccionDto,
                                           @RequestPart(value = "icono", required = false) MultipartFile icono) {

        try {
            Seccion seccion = seccionService.buscarSeccionPorId(seccionId);
            if (seccion == null) throw new ResourceNotFoundException("seccion", "id", seccionId);

            Seccion seccionEditada = seccionService.editarSeccion(seccion, seccionDto, icono);
            SeccionResDto seccionResDto = seccionMapper.toDTO(seccionEditada);

            return new ResponseEntity<>(ResponseMessage.builder()
                    .mensaje("Sección actualizada")
                    .recurso(seccionResDto)
                    .build(), HttpStatus.OK);
        } catch (IOException ioException) {
            throw new ResourceBadRequestException(ioException.getMessage());
        }
    }

    @GetMapping("/seccion/porid/{seccionId}")
    public ResponseEntity<?> buscarSeccionPorId(@PathVariable("seccionId") String seccionId) {

        Seccion seccion = seccionService.buscarSeccionPorId(seccionId);
        if (seccion == null) throw new ResourceNotFoundException("seccion", "id", seccionId);

        SeccionResDto seccionResDto = seccionMapper.toDTO(seccion);
        return new ResponseEntity<>(seccionResDto, HttpStatus.OK);
    }

    @GetMapping("/seccion/{nombre}")
    public ResponseEntity<?> buscarSeccionPorNombre(@PathVariable("nombre") String nombre) {

        Seccion seccion = seccionService.buscarSeccionPorNombre(nombre);
        if (seccion == null) throw new ResourceNotFoundException("seccion", "nombre", nombre);

        SeccionResDto seccionResDto = seccionMapper.toDTO(seccion);
        return new ResponseEntity<>(seccionResDto, HttpStatus.OK);
    }

    @GetMapping("/secciones")
    public ResponseEntity<?> Secciones() {

        List<Seccion> secciones = seccionService.listarSecciones();
        if (secciones.isEmpty()) throw new ResourceNotFoundException("secciones");

        List<SeccionResDto> seccionesResDto = seccionMapper.toDTOs(secciones);
        return new ResponseEntity<>(seccionesResDto, HttpStatus.OK);
    }

    @DeleteMapping("/seccion/{seccionId}")
    public ResponseEntity<?> eliminarSeccionPorId(@PathVariable("seccionId") String seccionId) {
        seccionService.eliminarSeccionPorId(seccionId);
        return new ResponseEntity<>(ResponseMessage.builder()
                .mensaje("Seccion eliminada")
                .build(), HttpStatus.OK);
    }
}
