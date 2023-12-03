package com.mendozanews.apinews.mapper;

import com.mendozanews.apinews.model.dto.request.ImagenDto;
import org.mapstruct.Mapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Mapper(componentModel = "spring")
public class ImagenesToHeaders {

    public HttpHeaders generarHeaders (ImagenDto imagenDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(imagenDto.getTipoMime()));
        headers.setContentLength(imagenDto.getContenido().length);
        headers.set("Content-Disposition", "inline; filename=" + imagenDto.getNombreArchivo());
        return headers;
    }
}
