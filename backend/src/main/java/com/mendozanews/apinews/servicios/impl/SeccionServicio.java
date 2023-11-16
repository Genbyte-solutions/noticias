package com.mendozanews.apinews.servicios.impl;

import com.mendozanews.apinews.model.dto.request.SeccionDto;
import com.mendozanews.apinews.model.entidades.IconoSeccion;
import com.mendozanews.apinews.model.entidades.Seccion;
import com.mendozanews.apinews.repositorios.IconoRepositorio;
import com.mendozanews.apinews.repositorios.SeccionRepositorio;

import java.io.IOException;
import java.util.List;

import com.mendozanews.apinews.servicios.interfaces.ISeccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SeccionServicio implements ISeccion {

    private final SeccionRepositorio seccionRepo;
    private final IconoRepositorio iconoRepo;

    public SeccionServicio(SeccionRepositorio seccionRepo, IconoRepositorio iconoRepo) {
        this.seccionRepo = seccionRepo;
        this.iconoRepo = iconoRepo;
    }

    // GUARDA EL ICONO DE LA SECCION
    @Transactional
    public IconoSeccion guardarIcono(MultipartFile icono) throws IOException {

        return iconoRepo.save(IconoSeccion.builder()
                .tipoMime(icono.getContentType())
                .nombreArchivo(icono.getOriginalFilename())
                .imagen(icono.getBytes())
                .build());
    }

    // CREA UNA SECCION
    @Transactional
    @Override
    public void crearSeccion(SeccionDto seccionDto, MultipartFile icono) throws IOException {

        IconoSeccion iconoSeccion = guardarIcono(icono);
        seccionRepo.save(Seccion.builder()
                .codigo(seccionDto.getCodigo())
                .nombre(seccionDto.getNombre())
                .icono(iconoSeccion)
                .build());
    }

    // LISTA TODAS LAS SECCIONES
    @Transactional(readOnly = true)
    @Override
    public List<Seccion> listarSecciones() {
        return seccionRepo.findAll();
    }

    // OBTIENE UNA SECCION POR ID, NOMBRE O CODIGO
    @Transactional(readOnly = true)
    @Override
    public Seccion buscarSeccion(String dato) {
        return seccionRepo.buscarSeccion(dato);
    }

    // ELIMINA SECCION POR ID
    @Transactional
    @Override
    public void eliminarSeccionPorId(String id) {
        seccionRepo.deleteById(id);
    }
}
