package com.mendozanews.apinews.servicios.impl;

import com.mendozanews.apinews.model.dto.request.AutorDto;
import com.mendozanews.apinews.model.entidades.Autor;
import com.mendozanews.apinews.model.entidades.Imagen;
import com.mendozanews.apinews.repositorios.AutorRepositorio;

import java.io.IOException;
import java.util.List;

import com.mendozanews.apinews.servicios.interfaces.IAutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AutorServicio implements IAutor {
    private final AutorRepositorio autorRepositorio;
    private final ImagenServicio imagenServicio;

    public AutorServicio(AutorRepositorio autorRepositorio, ImagenServicio imagenServicio) {
        this.autorRepositorio = autorRepositorio;
        this.imagenServicio = imagenServicio;
    }

    // CREA UN AUTOR
    @Transactional
    @Override
    public void crearAutor(AutorDto autorDto, MultipartFile foto) throws IOException {

        Imagen fotoGuardada = imagenServicio.guardarImagen(foto);
        autorRepositorio.save(Autor.builder()
                .nombre(autorDto.getNombre())
                .apellido(autorDto.getApellido())
                .foto(fotoGuardada)
                .build());
    }

    // LISTA TODOS LOS AUTORES
    @Transactional
    public List<Autor> listarAutores() {
        return this.autorRepositorio.findAll();
    }

    // MODIFICA UN AUTOR ENTERO
    @Transactional
    public void modificarAutor(Autor autor, AutorDto autorDto, MultipartFile foto) {

        autor.setNombre(autorDto.getNombre());
        autor.setApellido(autorDto.getApellido());
        if (foto != null) {
            autor.setFoto(imagenServicio.actualizar(foto, autor.getFoto().getImagenId()));
        }
        autorRepositorio.save(autor);
    }

    // OBTIENE UN AUTOR POR ID
    @Transactional
    public Autor buscarAutorPorId(String idAutor) {
        return this.autorRepositorio.findById(idAutor).orElse(null);
    }

    // OBTIENE UN AUTOR POR NOMBRE COMPLETO
    @Transactional
    public Autor buscarAutor(AutorDto autorDto) {
        return this.autorRepositorio.findByNombreCompleto(autorDto.getNombre(), autorDto.getApellido());
    }

    // ELIMINA AUTOR POR ID
    @Transactional
    public void eliminarAutorPorId(String idAutor) {
        Autor autor = this.buscarAutorPorId(idAutor);
        Imagen imagenAutor = this.imagenServicio.getOne(autor.getFoto().getImagenId());
        this.autorRepositorio.deleteById(idAutor);
        this.imagenServicio.eliminarImagenId(imagenAutor.getImagenId());
    }
}
