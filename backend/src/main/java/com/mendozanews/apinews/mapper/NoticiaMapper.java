package com.mendozanews.apinews.mapper;

import com.mendozanews.apinews.model.dto.request.NoticiaDto;
import com.mendozanews.apinews.model.entidades.Noticia;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public class NoticiaMapper {
    public NoticiaDto toDTO(Noticia noticia) {
        return NoticiaDto.builder()
                .titulo(noticia.getTitulo())
                .subtitulo(noticia.getSubtitulo())
                .parrafos(noticia.getParrafos())
                .etiquetas(noticia.getEtiquetas())
                .seccionId(noticia.getSeccion().getSeccionId())
                .autorId(noticia.getAutor().getAutorId())
                .build();
    }

    public List<NoticiaDto> toDTOs(List<Noticia> noticias) {
        if (noticias == null) {
            return null;
        }

        List<NoticiaDto> list = new ArrayList<>();

        for (Noticia noticia : noticias) {
            list.add(toDTO(noticia));
        }

        return list;
    }

}
