package com.mendozanews.apinews.mapper;

import com.mendozanews.apinews.model.dto.request.AutorDto;
import com.mendozanews.apinews.model.dto.response.AutorResDto;
import com.mendozanews.apinews.model.entidades.Autor;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public class AutorMapper {
    public AutorResDto toDTO(Autor autor) {
        return AutorResDto.builder()
                .AutorId(autor.getAutorId())
                .nombre(autor.getNombre())
                .apellido(autor.getApellido())
                .foto(autor.getFoto())
                .build();
    }

    public List<AutorResDto> toDTOs(List<Autor> autores) {
        if (autores == null) return null;

        List<AutorResDto> list = new ArrayList<>();

        for (Autor autor : autores) {
            list.add(toDTO(autor));
        }

        return list;
    }
}
