package com.mendozanews.apinews.servicios.interfaces;

import com.mendozanews.apinews.model.dto.request.AutorDto;
import com.mendozanews.apinews.model.entidades.Autor;
import com.mendozanews.apinews.model.entidades.Imagen;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IAutor {
    void crearAutor(AutorDto autorDto, MultipartFile foto) throws IOException;

    List<Autor> listarAutores();

    void modificarAutor(Autor autor, AutorDto autorDto, MultipartFile foto);

    Autor buscarAutorPorId(String autorId);

    Autor buscarAutor(AutorDto autorDto);

    void eliminarAutorPorId(String autorId);
}
