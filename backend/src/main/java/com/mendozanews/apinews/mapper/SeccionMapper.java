package com.mendozanews.apinews.mapper;

import com.mendozanews.apinews.model.dto.response.SeccionResDto;
import com.mendozanews.apinews.model.entity.Seccion;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public class SeccionMapper {
    public SeccionResDto toDTO(Seccion seccion) {
        return SeccionResDto.builder()
                .seccionId(seccion.getSeccionId())
                .nombre(seccion.getNombre())
                .build();
    }

    public List<SeccionResDto> toDTOs(List<Seccion> secciones) {
        if (secciones == null) return null;

        List<SeccionResDto> list = new ArrayList<>();

        for (Seccion seccion : secciones) {
            list.add(toDTO(seccion));
        }

        return list;
    }
}
