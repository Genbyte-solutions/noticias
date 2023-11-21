package com.mendozanews.apinews.model.dto.response;

import com.mendozanews.apinews.model.entidades.Imagen;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class AutorResDto implements Serializable {
    private String AutorId;
    private String nombre;
    private String apellido;

    public AutorResDto(String autorId, String nombre, String apellido) {
        AutorId = autorId;
        this.nombre = nombre;
        this.apellido = apellido;
    }
}
