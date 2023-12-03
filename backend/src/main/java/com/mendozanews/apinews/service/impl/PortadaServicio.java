package com.mendozanews.apinews.service.impl;

import com.mendozanews.apinews.service.interfaces.IPortada;
import org.springframework.web.multipart.MultipartFile;

import com.mendozanews.apinews.exception.MiException;
import com.mendozanews.apinews.model.entity.Portada;
import com.mendozanews.apinews.repository.PortadaRepositorio;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PortadaServicio implements IPortada {

    private final PortadaRepositorio portadaRepo;

    public PortadaServicio(PortadaRepositorio portadaRepo) {
        this.portadaRepo = portadaRepo;
    }

    @Override
    public Portada guardarPortada(MultipartFile portada) throws IOException {

        return portadaRepo.save(
                Portada.builder()
                        .contenido(portada.getBytes())
                        .tipoMime(portada.getContentType())
                        .nombreArchivo(portada.getOriginalFilename())
                        .build()
        );
    }

    @Override
    @Transactional
    public Portada actualizarPortada(MultipartFile portada, String id) throws MiException, IOException {
        Portada portadaActualizada = portadaRepo.findById(id).orElse(null);
        if (portadaActualizada == null)
            return null;

        portadaActualizada.setTipoMime(portada.getContentType());
        portadaActualizada.setNombreArchivo(portada.getOriginalFilename());
        portadaActualizada.setContenido(portada.getBytes());

        return portadaRepo.save(portadaActualizada);
    }

}
