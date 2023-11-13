package com.mendozanews.apinews.controllers;

import com.mendozanews.apinews.model.entidades.Seccion;
import com.mendozanews.apinews.servicios.SeccionServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/secciones")
public class ControllerSecciones {

    private SeccionServicio seccionServicio;

    public ControllerSecciones(SeccionServicio seccionServicio) {
        this.seccionServicio = seccionServicio;
    }

    @GetMapping
    public ResponseEntity<List<Seccion>> listarSecciones() {
        List<Seccion> secciones = seccionServicio.listarSecciones();
        if (secciones.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(secciones, HttpStatus.OK);
    }
}
