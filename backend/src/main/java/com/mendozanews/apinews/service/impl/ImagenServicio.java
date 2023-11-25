package com.mendozanews.apinews.service.impl;

import com.mendozanews.apinews.model.entity.Imagen;
import com.mendozanews.apinews.repository.ImagenRepositorio;

import java.io.IOException;

import com.mendozanews.apinews.service.interfaces.IImagen;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImagenServicio implements IImagen {
    private final ImagenRepositorio imagenRepo;

    public ImagenServicio(ImagenRepositorio imagenRepo) {
        this.imagenRepo = imagenRepo;
    }

    // GUARDA UNA IMAGEN (AUTOR O USUARIO)
    @Transactional
    public Imagen guardarImagen(MultipartFile foto) throws IOException {
        return imagenRepo.save(
                Imagen.builder()
                        .tipoMime(foto.getContentType())
                        .nombreArchivo(foto.getOriginalFilename())
                        .contenido(foto.getBytes())
                        .build());
    }

    // ACTUALIZA UNA IMAGEN
    @Transactional
    public Imagen actualizarImagen(MultipartFile archivo, String id) throws IOException {

        Imagen imagen = imagenRepo.findById(id).orElse(null);
        if (imagen == null)
            return null;

        imagen.setTipoMime(archivo.getContentType());
        imagen.setNombreArchivo(archivo.getOriginalFilename());
        imagen.setContenido(archivo.getBytes());

        return imagenRepo.save(imagen);
    }

    // OBTIENE UNA IMAGEN POR ID
    @Transactional(readOnly = true)
    @Override
    public Imagen buscarImagenPorId(String id) {
        return imagenRepo.findById(id).orElse(null);
    }

    // ELIMINA IMAGEN POR ID
    @Transactional
    public void eliminarImagenPorId(String id) {
        imagenRepo.deleteById(id);
    }
}
