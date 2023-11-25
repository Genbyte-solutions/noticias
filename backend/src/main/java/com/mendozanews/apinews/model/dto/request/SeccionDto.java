package com.mendozanews.apinews.model.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class SeccionDto implements Serializable {

    @NotEmpty(message = "Requerido")
    @Size(min = 2, max = 20, message = "Minimo 2 caracteres y maximo 20 caracteres")
    private String nombre;

    public SeccionDto(String nombre) {
        this.nombre = nombre;
    }
}
