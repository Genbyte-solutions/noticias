package com.mendozanews.apinews.service.interfaces;

import com.mendozanews.apinews.model.dto.request.AutorDto;
import com.mendozanews.apinews.model.entity.Autor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IAutor {
    Autor crearAutor(AutorDto autorDto, MultipartFile foto) throws IOException;

    List<Autor> listarAutores();

    Autor editarAutor(Autor autor, AutorDto autorDto, MultipartFile foto) throws IOException;

    Autor buscarAutorPorId(String id);

    Autor buscarAutor(AutorDto autorDto);

    void eliminarAutorPorId(String autorId);
}
