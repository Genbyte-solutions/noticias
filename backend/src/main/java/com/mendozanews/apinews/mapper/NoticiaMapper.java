package com.mendozanews.apinews.mapper;

import com.mendozanews.apinews.model.dto.response.AutorResDto;
import com.mendozanews.apinews.model.dto.response.NoticiaResDto;
import com.mendozanews.apinews.model.dto.response.SeccionResDto;
import com.mendozanews.apinews.model.entity.Noticia;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public class NoticiaMapper {
    public NoticiaResDto toDTO(Noticia noticia) {
        return NoticiaResDto.builder()
                .noticiaId(noticia.getNoticiaId())
                .titulo(noticia.getTitulo())
                .subtitulo(noticia.getSubtitulo())
                .parrafos(noticia.getParrafos())
                .etiquetas(noticia.getEtiquetas())
                .vistas(noticia.getVistas())
                .seccionResDto(SeccionResDto.builder()
                        .seccionId(noticia.getSeccion().getSeccionId())
                        .nombre(noticia.getSeccion().getNombre())
                        .build())
                .autorResDto(AutorResDto.builder()
                        .autorId(noticia.getAutor().getAutorId())
                        .nombre(noticia.getAutor().getNombre())
                        .apellido(noticia.getAutor().getApellido())
                        .build())
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
