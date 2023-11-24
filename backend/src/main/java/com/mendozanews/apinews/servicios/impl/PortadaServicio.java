package com.mendozanews.apinews.servicios.impl;

import com.mendozanews.apinews.model.entidades.Imagen;
import com.mendozanews.apinews.servicios.interfaces.IPortada;
import org.springframework.web.multipart.MultipartFile;

import com.mendozanews.apinews.exception.MiException;
import com.mendozanews.apinews.model.entidades.Portada;
import com.mendozanews.apinews.repositorios.PortadaRepositorio;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Optional;

@Service
public class PortadaServicio implements IPortada {

    @Override
    public Portada guardarPortada(MultipartFile portada) throws IOException {

        return portadaRepositorio.save(
                Portada.builder()
                        .contenido(portada.getBytes())
                        .tipoMime(portada.getContentType())
                        .nombreArchivo(portada.getOriginalFilename())
                        .build()
        );
    }

    @Autowired
    private PortadaRepositorio portadaRepositorio;

    @Override
    @Transactional
    public Portada actualizarPortada(MultipartFile portada, String id) throws MiException, IOException {
        Portada portadaActualizada = portadaRepositorio.findById(id).orElse(null);
        if (portadaActualizada == null)
            return null;

        portadaActualizada.setTipoMime(portada.getContentType());
        portadaActualizada.setNombreArchivo(portada.getOriginalFilename());
        portadaActualizada.setContenido(portada.getBytes());

        return portadaRepositorio.save(portadaActualizada);
    }

}
