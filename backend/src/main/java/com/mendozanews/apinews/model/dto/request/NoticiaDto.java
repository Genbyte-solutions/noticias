package com.mendozanews.apinews.model.dto.request;

import java.io.Serializable;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NoticiaDto implements Serializable {

    @NotEmpty(message = "Requerido")
    @Size(min = 2, message = "Minimo 2 Caracteres")
    private String titulo;

    @NotEmpty(message = "Requerido")
    @Size(min = 2, message = "Minimo 2 Caracteres")
    private String subtitulo;

    @NotEmpty(message = "Requeridos")
    private List<String> parrafos;

    private List<String> etiquetas;

    @NotEmpty(message = "Requerida")
    @Size(min = 36, max = 36, message = "Debe tener 36 caracteres")
    private String seccionId;

    @NotEmpty(message = "Requerido")
    @Size(min = 36, max = 36, message = "Debe tener 36 caracteres")
    private String autorId;

    public NoticiaDto(String titulo, String subtitulo, List<String> parrafos, List<String> etiquetas, String seccionId, String autorId) {
        this.titulo = titulo;
        this.subtitulo = subtitulo;
        this.parrafos = parrafos;
        this.etiquetas = etiquetas;
        this.seccionId = seccionId;
        this.autorId = autorId;
    }

}
