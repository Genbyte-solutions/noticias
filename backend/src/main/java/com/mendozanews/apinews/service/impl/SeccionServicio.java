package com.mendozanews.apinews.service.impl;

import com.mendozanews.apinews.model.dto.request.SeccionDto;
import com.mendozanews.apinews.model.entity.IconoSeccion;
import com.mendozanews.apinews.model.entity.Seccion;
import com.mendozanews.apinews.repository.IconoRepositorio;
import com.mendozanews.apinews.repository.SeccionRepositorio;

import java.io.IOException;
import java.util.List;

import com.mendozanews.apinews.service.interfaces.ISeccion;
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
                .contenido(icono.getBytes())
                .build());
    }

    // CREA UNA SECCION
    @Transactional
    @Override
    public Seccion crearSeccion(SeccionDto seccionDto, MultipartFile icono) throws IOException {
        IconoSeccion iconoSeccion = null;
        if (icono != null) iconoSeccion = guardarIcono(icono);

        return seccionRepo.save(Seccion.builder()
                .nombre(seccionDto.getNombre())
                .icono(iconoSeccion)
                .build());
    }

    @Transactional
    @Override
    public Seccion editarSeccion(Seccion seccion, SeccionDto seccionDto, MultipartFile icono) throws IOException {

        IconoSeccion iconoSeccion;
        seccion.setNombre(seccionDto.getNombre());

        if (icono != null) {
            iconoSeccion = iconoRepo.findById(seccion.getIcono().getIconoSeccionId()).orElse(null);
            if (iconoSeccion != null) {
                iconoSeccion.setTipoMime(icono.getContentType());
                iconoSeccion.setNombreArchivo(icono.getOriginalFilename());
                iconoSeccion.setContenido(icono.getBytes());
                iconoRepo.save(iconoSeccion);
            }
        }

        return seccionRepo.save(seccion);
    }

    @Transactional(readOnly = true)
    @Override
    public Seccion buscarSeccionPorId(String id) {
        return seccionRepo.findById(id).orElse(null);
    }

    // OBTIENE UNA SECCION POR NOMBRE
    @Transactional(readOnly = true)
    @Override
    public Seccion buscarSeccionPorNombre(String buscar) {
        return seccionRepo.buscarSeccionPorNombre(buscar);
    }

    // LISTA TODAS LAS SECCIONES
    @Transactional(readOnly = true)
    @Override
    public List<Seccion> listarSecciones() {
        return seccionRepo.findAll();
    }

    // ELIMINA SECCION POR ID
    @Transactional
    @Override
    public void eliminarSeccionPorId(String id) {
        seccionRepo.deleteById(id);
    }

    // OBTIENE ICONO POR ID DE SECCION
    @Transactional(readOnly = true)
    @Override
    public IconoSeccion buscarIconoPorSeccionId(String iconoId) {
        return iconoRepo.findById(iconoId).orElse(null);
    }
}
