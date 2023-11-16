package com.mendozanews.apinews.servicios.interfaces;

import com.mendozanews.apinews.model.dto.request.NoticiaDto;
import com.mendozanews.apinews.model.entidades.Autor;
import com.mendozanews.apinews.model.entidades.Noticia;
import com.mendozanews.apinews.model.entidades.Seccion;

import java.util.List;

public interface INoticia {
    Noticia guardarNoticia(NoticiaDto noticia, Autor autor, Seccion seccion);

    List<Noticia> buscarPorTitulo(String titulo);

    List<Noticia> buscarPorSeccion(String seccion);

    List<Noticia> buscarPorAutor(String nombre, String apellido);

    List<Noticia> buscarNoticias();
}
