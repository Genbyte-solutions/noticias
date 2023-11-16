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
public class SeccionController {

    private final ISeccion seccionService;

    public SeccionController(ISeccion seccionService) {
        this.seccionService = seccionService;
    }

    @PostMapping("/seccion/guardar")
    public ResponseEntity<?> GuardarSeccion(
            @RequestBody SeccionDto seccionDto,
            @RequestParam("icono") MultipartFile icono) throws IOException {

        seccionService.crearSeccion(seccionDto, icono);

        return new ResponseEntity<>("Nueva seccion añadida", HttpStatus.CREATED);
    }

    @GetMapping("/seccion/")
    public ResponseEntity<?> buscarSeccion(@RequestParam("buscar") String buscar) {

        Seccion seccion = seccionService.buscarSeccion(buscar);
        if (seccion == null) {
            return new ResponseEntity<>("Seccion no encontrada", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(seccion, HttpStatus.OK);
    }

    @GetMapping("/secciones")
    public ResponseEntity<?> obtenerSecciones() {

        List<Seccion> secciones = seccionService.listarSecciones();

        if (secciones.isEmpty()) {
            return new ResponseEntity<>("No se encontraron secciones", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(secciones, HttpStatus.OK);
    }

    @DeleteMapping("/seccion/{id}")
    public ResponseEntity<?> eliminarSeccionPorId(@PathVariable("id") String id) {

        seccionService.eliminarSeccionPorId(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
