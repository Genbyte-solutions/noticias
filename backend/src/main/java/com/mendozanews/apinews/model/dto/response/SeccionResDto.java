package com.mendozanews.apinews.model.dto.response;


import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
@Data
@Builder
public class SeccionResDto implements Serializable {
    private String seccionId;
    private String codigo;
    private String nombre;

    public SeccionResDto(String seccionId, String codigo, String nombre) {
        this.seccionId = seccionId;
        this.codigo = codigo;
        this.nombre = nombre;
    }
}
