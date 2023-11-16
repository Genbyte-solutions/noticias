package com.mendozanews.apinews.servicios.interfaces;

import com.mendozanews.apinews.model.dto.request.NoticiaDto;
import com.mendozanews.apinews.model.entidades.*;
import org.springframework.web.multipart.MultipartFile;
import scala.Int;

import java.io.IOException;
import java.util.List;

public interface INoticia {
    String crearNoticia(NoticiaDto noticia, Autor autor, Seccion seccion, List<MultipartFile> imagenes, MultipartFile portada) throws IOException;

    Noticia buscarNoticiaPorId(String noticiaId);

    void eliminarNoticiaPorId(String noticiaId);

    List<Noticia> listarNoticias(Integer limit, Integer offset);

    List<Noticia> buscarPorTitulo(String titulo);

    List<Noticia> buscarPorSeccion(String seccion);

    List<Noticia> buscarPorAutor(String nombre, String apellido);
}
