package com.mendozanews.apinews.mapper;

import com.mendozanews.apinews.model.dto.request.ImagenDto;
import com.mendozanews.apinews.model.entity.*;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public class ImagenesMapper {
    public ImagenDto toDTO(IconoSeccion iconoSeccion) {
        return ImagenDto.builder()
                .tipoMime(iconoSeccion.getTipoMime())
                .nombreArchivo(iconoSeccion.getNombreArchivo())
                .contenido(iconoSeccion.getContenido())
                .build();
    }
    public ImagenDto toDTO(Imagen imagen) {
        return ImagenDto.builder()
                .tipoMime(imagen.getTipoMime())
                .nombreArchivo(imagen.getNombreArchivo())
                .contenido(imagen.getContenido())
                .build();
    }
    public ImagenDto toDTO(ImagenesNoticia imagenesNoticia) {
        return ImagenDto.builder()
                .tipoMime(imagenesNoticia.getTipoMime())
                .nombreArchivo(imagenesNoticia.getNombreArchivo())
                .contenido(imagenesNoticia.getContenido())
                .build();
    }
    public ImagenDto toDTO(Portada portada) {
        return ImagenDto.builder()
                .tipoMime(portada.getTipoMime())
                .nombreArchivo(portada.getNombreArchivo())
                .contenido(portada.getContenido())
                .build();
    }
}
