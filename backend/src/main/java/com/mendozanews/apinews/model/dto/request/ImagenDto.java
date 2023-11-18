package com.mendozanews.apinews.model.dto.request;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
@Data
@Builder
public class ImagenDto implements Serializable {
    private String tipoMime;
    private String nombreArchivo;
    private byte[] contenido;

    public ImagenDto(String tipoMime, String nombreArchivo, byte[] contenido) {
        this.tipoMime = tipoMime;
        this.nombreArchivo = nombreArchivo;
        this.contenido = contenido;
    }
}
