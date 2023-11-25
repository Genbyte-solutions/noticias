package com.mendozanews.apinews.mapper;

import com.mendozanews.apinews.model.dto.response.AutorResDto;
import com.mendozanews.apinews.model.entity.Autor;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public class AutorMapper {
    public AutorResDto toDTO(Autor autor) {
        return AutorResDto.builder()
                .autorId(autor.getAutorId())
                .nombre(autor.getNombre())
                .apellido(autor.getApellido())
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
