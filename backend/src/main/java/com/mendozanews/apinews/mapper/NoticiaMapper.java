package com.mendozanews.apinews.mapper;

import com.mendozanews.apinews.model.dto.request.NoticiaDto;
import com.mendozanews.apinews.model.dto.response.NoticiaResDto;
import com.mendozanews.apinews.model.entidades.Noticia;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public class NoticiaMapper {
    public NoticiaResDto toDTO(Noticia noticia) {
        return NoticiaResDto.builder()
                .titulo(noticia.getTitulo())
                .subtitulo(noticia.getSubtitulo())
                .parrafos(noticia.getParrafos())
                .etiquetas(noticia.getEtiquetas())
                .seccion(noticia.getSeccion())
                .autor(noticia.getAutor())
                .portada(noticia.getPortada())
                .imagenes(noticia.getImagenesNoticia())
                .build();
    }

    public List<NoticiaResDto> toDTOs(List<Noticia> noticias) {
        if (noticias == null) return null;

        List<NoticiaResDto> list = new ArrayList<>();

        for (Noticia noticia : noticias) {
            list.add(toDTO(noticia));
        }

        return list;
    }

}
