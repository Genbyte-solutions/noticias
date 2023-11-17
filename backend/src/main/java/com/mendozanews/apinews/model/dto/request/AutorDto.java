package com.mendozanews.apinews.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class AutorDto implements Serializable {

    @NotNull
    private String nombre;
    @NotNull
    private String apellido;

    public AutorDto(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }
}
