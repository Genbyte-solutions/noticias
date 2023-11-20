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

        Imagen fotoGuardada = null;
        if (foto != null) fotoGuardada = imagenServicio.guardarImagen(foto);

        Autor autor = new Autor();
        autor.setNombre(autorDto.getNombre());
        autor.setApellido(autorDto.getApellido());
        if (fotoGuardada != null) autor.setFoto(fotoGuardada);

        autorRepositorio.save(autor);
    }

    // MODIFICA UN AUTOR ENTERO
    @Transactional
    public void editarAutor(Autor autor, AutorDto autorDto, MultipartFile foto) throws IOException {
        Imagen imagen = null;
        if (foto != null) imagen = imagenServicio.actualizarImagen(foto, autor.getFoto().getImagenId());

        autor.setNombre(autorDto.getNombre());
        autor.setApellido(autorDto.getApellido());
        if (imagen != null) autor.setFoto(imagen);

        autorRepositorio.save(autor);
    }

    // LISTA TODOS LOS AUTORES
    @Transactional(readOnly = true)
    public List<Autor> listarAutores() {
        return autorRepositorio.findAll();
    }

    // OBTIENE UN AUTOR POR ID
    @Transactional(readOnly = true)
    public Autor buscarAutorPorId(String id) {
        return autorRepositorio.findById(id).orElse(null);
    }

    // OBTIENE UN AUTOR POR NOMBRE COMPLETO
    @Transactional(readOnly = true)
    public Autor buscarAutor(AutorDto autorDto) {
        return autorRepositorio.buscarPorNombreCompleto(autorDto.getNombre(), autorDto.getApellido());
    }

    // ELIMINA AUTOR POR ID
    @Transactional
    public void eliminarAutorPorId(String id) {

        Autor autor = autorRepositorio.findById(id).orElse(null);
        if (autor != null && autor.getFoto().getImagenId() != null) {
            imagenServicio.eliminarImagenPorId(autor.getFoto().getImagenId());
        }
        autorRepositorio.deleteById(id);
    }
}
