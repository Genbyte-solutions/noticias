package com.mendozanews.apinews.model.dto.request;

import java.io.Serializable;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import com.mendozanews.apinews.model.entidades.Autor;
import com.mendozanews.apinews.model.entidades.Seccion;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NoticiaDto implements Serializable {

    @NotNull
    private String titulo;
    @NotNull
    private String subtitulo;
    @NotEmpty
    private List<String> parrafos;
    @NotEmpty
    private List<String> etiquetas;
    @NotNull
    private String seccionId;
    @NotNull
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
