package com.mendozanews.apinews.model.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class AutorDto implements Serializable {

    @NotEmpty(message = "Requerido")
    @Size(min = 2, message = "Minimo 2 caracteres")
    private String nombre;

    private String apellido;

    public AutorDto(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }
}
