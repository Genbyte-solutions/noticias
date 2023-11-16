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
    public List<com.mendozanews.apinews.model.entidades.Autor> listarAutores() {
        return this.autorRepositorio.findAll();
    }

    // MODIFICA UN AUTOR ENTERO
    @Transactional
    public void modificarAutor(com.mendozanews.apinews.model.entidades.Autor autor, String nombre, String apellido, Imagen foto) {
        autor.setNombre(nombre);
        autor.setApellido(apellido);
        autor.setFoto(foto);
        this.autorRepositorio.save(autor);
    }

    // OBTIENE UN AUTOR POR ID
    @Transactional
    public com.mendozanews.apinews.model.entidades.Autor buscarAutorPorId(String idAutor) {
        return this.autorRepositorio.findById(idAutor).orElse(null);
    }

    // OBTIENE UN AUTOR POR NOMBRE COMPLETO
    @Transactional
    public com.mendozanews.apinews.model.entidades.Autor buscarAutor(String nombre, String apellido) {
        return this.autorRepositorio.findByNombreCompleto(nombre, apellido);
    }

    // ELIMINA AUTOR POR ID
    @Transactional
    public void eliminarAutorPorId(String idAutor) {
        com.mendozanews.apinews.model.entidades.Autor autor = this.buscarAutorPorId(idAutor);
        Imagen imagenAutor = this.imagenServicio.getOne(autor.getFoto().getImagenId());
        this.autorRepositorio.deleteById(idAutor);
        this.imagenServicio.eliminarImagenId(imagenAutor.getImagenId());
    }
}
