package com.mendozanews.apinews.servicios.impl;

import com.mendozanews.apinews.excepciones.MiException;
import com.mendozanews.apinews.model.entidades.Autor;
import com.mendozanews.apinews.model.entidades.Imagen;
import com.mendozanews.apinews.repositorios.AutorRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mendozanews.apinews.servicios.interfaces.IAutorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AutorServicio implements IAutorServicio {


    private final AutorRepositorio autorRepositorio;

    private final ImagenServicio imagenServicio;

    public AutorServicio(AutorRepositorio autorRepositorio, ImagenServicio imagenServicio){
        this.autorRepositorio = autorRepositorio;
        this. imagenServicio = imagenServicio;
    }

    // CREA UN AUTOR ENTERO
    @Transactional
    public Autor crearAutor(Autor autor){
        return this.autorRepositorio.save(autor);
    }

    // LISTA TODOS LOS AUTORES
    @Transactional
    public List<Autor> listarAutores() {
        return this.autorRepositorio.findAll();
    }

    // MODIFICA UN AUTOR ENTERO
    @Transactional
    public void modificarAutor(Autor autor, String nombre, String apellido, Imagen foto) {
            autor.setNombre(nombre);
            autor.setApellido(apellido);
            autor.setFoto(foto);
            this.autorRepositorio.save(autor);
    }

    // OBTIENE UN AUTOR POR ID
    @Transactional
    public Autor getOne(String idAutor) {
        return this.autorRepositorio.findById(idAutor).orElse(null);
    }

    // OBTIENE UN AUTOR POR NOMBRE COMPLETO
    @Transactional
    public Autor findByNombreCompleto(String nombre, String apellido){
        return this.autorRepositorio.findByNombreCompleto(nombre,apellido);
    }

    // ELIMINA AUTOR POR ID
    @Transactional
    public void eliminarAutorId(String idAutor){
        Autor autor = this.getOne(idAutor);
        Imagen imagenAutor = this.imagenServicio.getOne(autor.getFoto().getId());
        this.autorRepositorio.deleteById(idAutor);
        this.imagenServicio.eliminarImagenId(imagenAutor.getId());
    }
}
