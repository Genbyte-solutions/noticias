package com.mendozanews.apinews.controllers;

import com.mendozanews.apinews.model.dto.request.SeccionDto;
import com.mendozanews.apinews.model.entidades.Seccion;
import com.mendozanews.apinews.servicios.interfaces.ISeccion;
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

    public SeccionControlador(ISeccion seccionService) {
        this.seccionService = seccionService;
    }

    @PostMapping("/seccion")
    public ResponseEntity<?> GuardarSeccion(
            @RequestBody SeccionDto seccionDto,
            @RequestPart(value = "icono") MultipartFile icono) throws IOException {

        this.seccionService.crearSeccion(seccionDto, icono);

        return new ResponseEntity<>("Nueva seccion añadida", HttpStatus.CREATED);
    }

    @PutMapping("/seccion/{seccionId}")
    public ResponseEntity<?> editarSeccion(@PathVariable("seccionId") String seccionId,
                                           @RequestBody SeccionDto seccionDto,
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

        return new ResponseEntity<>(seccion, HttpStatus.OK);
    }

    @GetMapping("/secciones")
    public ResponseEntity<?> obtenerSecciones() {

        List<Seccion> secciones = this.seccionService.listarSecciones();
        if (secciones.isEmpty())
            return new ResponseEntity<>("No se encontraron secciones", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(secciones, HttpStatus.OK);
    }

    @DeleteMapping("/seccion/{id}")
    public ResponseEntity<?> eliminarSeccionPorId(@PathVariable("id") String id) {

        this.seccionService.eliminarSeccionPorId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
