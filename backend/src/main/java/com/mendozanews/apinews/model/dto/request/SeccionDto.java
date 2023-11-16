package com.mendozanews.apinews.model.dto.request;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class SeccionDto implements Serializable {
    private String nombre;
    private String codigo;

    public SeccionDto(String nombre, String codigo) {
        this.nombre = nombre;
        this.codigo = codigo;
    }
}
