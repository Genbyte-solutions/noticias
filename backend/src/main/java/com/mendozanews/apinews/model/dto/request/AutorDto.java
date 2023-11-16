package com.mendozanews.apinews.model.dto.request;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class AutorDto implements Serializable {
    private String nombre;
    private String apellido;

    public AutorDto(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }
}
