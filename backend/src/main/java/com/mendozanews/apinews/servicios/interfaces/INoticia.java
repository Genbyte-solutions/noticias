package com.mendozanews.apinews.servicios.interfaces;

import com.mendozanews.apinews.model.dto.request.NoticiaDto;
import com.mendozanews.apinews.model.entidades.*;
import com.mendozanews.apinews.model.enums.Orden;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface INoticia {

    String crearNoticia(NoticiaDto noticia, Autor autor, Seccion seccion, List<MultipartFile> imagenes, MultipartFile portada) throws IOException;

    Noticia buscarNoticiaPorId(String noticiaId);

    void eliminarNoticiaPorId(String noticiaId);

    List<Noticia> listarNoticias(Integer offset, Integer limit, Orden orden);

    List<Noticia> buscarPorTitulo(String titulo);

    List<Noticia> buscarPorSeccion(String seccion, Integer offset, Integer limit, Orden orden);

    List<Noticia> buscarPorAutor(String nombre, String apellido, Integer offset, Integer limit, Orden orden);
}
