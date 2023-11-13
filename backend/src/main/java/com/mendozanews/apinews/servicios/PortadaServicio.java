package com.mendozanews.apinews.servicios;

import org.springframework.web.multipart.MultipartFile;

import com.mendozanews.apinews.excepciones.MiException;
import com.mendozanews.apinews.model.entidades.Portada;
import com.mendozanews.apinews.repositorios.PortadaRepositorio;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;
import java.util.Optional;

@Service
public class PortadaServicio {

    @Autowired
    private PortadaRepositorio portadaRepositorio;

    public String portadaId(String id) {
        Optional<Portada> portada = portadaRepositorio.findById(id);
        if (portada.isPresent()) {
            Portada portadaEncontrada = portada.get();
            if (portadaEncontrada.getId() != null) { // Reemplazar con el método de obtención de ID correcto
                return portadaEncontrada.getId();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Transactional
    public String actualizarPortada(MultipartFile archivo, String id) throws MiException {
        archivo = validar(archivo);
        if (archivo != null) {
            try {
                Optional<Portada> respuesta = portadaRepositorio.findById(id);
                Portada portada = null;
                if (respuesta.isPresent()) {
                    portada = respuesta.get();
                }
                portada.setMime(archivo.getContentType());
                portada.setNombre(archivo.getOriginalFilename());
                portada.setImagen(archivo.getBytes());
                portadaRepositorio.save(portada);
                return portada.getId();
            } catch (IOException e) {
                throw new MiException("No se pudo actualizar la portada: " + e.getMessage());
            }
        }
        if (id != null) {
            portadaRepositorio.deleteById(id);
        }
        return null;
    }

    private MultipartFile validar(MultipartFile archivo) {
        if (archivo == null) {
            throw new IllegalArgumentException("El archivo no puede ser nulo");
        }
        if (archivo.isEmpty()) {
            throw new IllegalArgumentException("El archivo no puede estar vacío");
        }
        return archivo;
    }

    public Portada buscarPorContenido(MultipartFile archivo) throws MiException {
        try {
            byte[] contenido = archivo.getBytes();
            Portada imagen = portadaRepositorio.buscarPorContenido(contenido);
            return imagen;
        } catch (IOException ex) {
            throw new MiException("Error al buscar imagen por contenido: " + ex.getMessage());
        }
    }
}
