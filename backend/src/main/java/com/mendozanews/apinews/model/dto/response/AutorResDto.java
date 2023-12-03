package com.mendozanews.apinews.model.dto.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class AutorResDto implements Serializable {
    private String autorId;
    private String nombre;
    private String apellido;

    public AutorResDto(String autorId, String nombre, String apellido) {
        this.autorId = autorId;
        this.nombre = nombre;
        this.apellido = apellido;
    }
}
