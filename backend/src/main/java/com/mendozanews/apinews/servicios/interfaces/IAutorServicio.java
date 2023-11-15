package com.mendozanews.apinews.servicios.interfaces;

import com.mendozanews.apinews.model.entidades.Autor;
import com.mendozanews.apinews.model.entidades.Imagen;

import java.util.List;

public interface IAutorServicio {
    Autor crearAutor(Autor autor);
    List<Autor> listarAutores();
    void modificarAutor(Autor autor, String nombre, String apellido, Imagen foto);
    Autor getOne(String autorId);
    Autor findByNombreCompleto(String nombre, String apellido);
    void eliminarAutorId(String autorId);
}
