package com.mendozanews.apinews.controllers;

import com.mendozanews.apinews.mapper.SeccionMapper;
import com.mendozanews.apinews.model.dto.request.SeccionDto;
import com.mendozanews.apinews.model.dto.response.SeccionResDto;
import com.mendozanews.apinews.model.entidades.Seccion;
import com.mendozanews.apinews.servicios.interfaces.ISeccion;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class SeccionControlador {

    private final ISeccion seccionService;
    private final SeccionMapper seccionMapper;

    public SeccionControlador(ISeccion seccionService, SeccionMapper seccionMapper) {
        this.seccionService = seccionService;
        this.seccionMapper = seccionMapper;
    }

    @PostMapping("/seccion")
    public ResponseEntity<?> GuardarSeccion(
            @ModelAttribute @Valid SeccionDto seccionDto,
            @RequestPart(value = "icono") MultipartFile icono) throws IOException {

        this.seccionService.crearSeccion(seccionDto, icono);

        return new ResponseEntity<>("Nueva seccion añadida", HttpStatus.CREATED);
    }

    @PutMapping("/seccion/{seccionId}")
    public ResponseEntity<?> editarSeccion(@PathVariable("seccionId") String seccionId,
                                           @ModelAttribute @Valid SeccionDto seccionDto,
                                           @RequestPart(value = "icono", required = false) MultipartFile icono) {

        try {
            seccionService.editarSeccion(seccionId, seccionDto, icono);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException ioEx) {
            return new ResponseEntity<>(ioEx.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/seccion")
    public ResponseEntity<?> buscarSeccion(@RequestParam("buscar") String buscar) {

        Seccion seccion = this.seccionService.buscarSeccion(buscar);
        if (seccion == null)
            return new ResponseEntity<>("Seccion no encontrada", HttpStatus.NOT_FOUND);
        SeccionResDto seccionResDto = seccionMapper.toDTO(seccion);
        return new ResponseEntity<>(seccionResDto, HttpStatus.OK);
    }

    @GetMapping("/secciones")
    public ResponseEntity<?> Secciones() {

        List<Seccion> secciones = this.seccionService.listarSecciones();
        if (secciones.isEmpty())
            return new ResponseEntity<>("No se encontraron secciones", HttpStatus.NOT_FOUND);
        List<SeccionResDto> seccionesResDto = seccionMapper.toDTOs(secciones);
        return new ResponseEntity<>(seccionesResDto, HttpStatus.OK);
    }

    @DeleteMapping("/seccion/{id}")
    public ResponseEntity<?> eliminarSeccionPorId(@PathVariable("id") String id) {

        this.seccionService.eliminarSeccionPorId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
