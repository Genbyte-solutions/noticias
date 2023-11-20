package com.mendozanews.apinews.servicios.impl;

import com.mendozanews.apinews.model.dto.request.SeccionDto;
import com.mendozanews.apinews.model.entidades.IconoSeccion;
import com.mendozanews.apinews.model.entidades.Seccion;
import com.mendozanews.apinews.repositorios.IconoRepositorio;
import com.mendozanews.apinews.repositorios.SeccionRepositorio;

import java.io.IOException;
import java.util.List;

import com.mendozanews.apinews.servicios.interfaces.ISeccion;
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
    public void crearSeccion(SeccionDto seccionDto, MultipartFile icono) throws IOException {

        IconoSeccion iconoSeccion = guardarIcono(icono);
        seccionRepo.save(Seccion.builder()
                .codigo(seccionDto.getCodigo())
                .nombre(seccionDto.getNombre())
                .icono(iconoSeccion)
                .build());
    }

    @Transactional
    @Override
    public void editarSeccion(String seccionId, SeccionDto seccionDto, MultipartFile icono) throws IOException {

        Seccion seccion = seccionRepo.findById(seccionId).orElse(null);
        IconoSeccion iconoSeccion;

        if (seccion != null) {
            seccion.setNombre(seccionDto.getNombre());
            seccion.setCodigo(seccionDto.getCodigo());
            iconoSeccion = iconoRepo.findById(seccion.getIcono().getIconoSeccionId()).orElse(null);
            if (icono != null && iconoSeccion != null) {
                iconoSeccion.setTipoMime(icono.getContentType());
                iconoSeccion.setNombreArchivo(icono.getOriginalFilename());
                iconoSeccion.setContenido(icono.getBytes());
                iconoRepo.save(iconoSeccion);
            }
        }
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
    public Seccion buscarSeccion(String buscar) {
        return seccionRepo.buscarSeccion(buscar);
    }

    @Transactional(readOnly = true)
    @Override
    public Seccion buscarSeccionPorId(String id) {return seccionRepo.findById(id).orElse(null);}

    @Transactional(readOnly = true)
    @Override
    public IconoSeccion buscarIconoPorSeccionId(String iconoId){
        return iconoRepo.findById(iconoId).orElse(null);
    }


    // ELIMINA SECCION POR ID
    @Transactional
    @Override
    public void eliminarSeccionPorId(String id) {
        seccionRepo.deleteById(id);
    }
}
