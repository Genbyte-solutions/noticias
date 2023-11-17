package com.mendozanews.apinews.model.dto.response;

import com.mendozanews.apinews.model.entidades.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class NoticiaResDto implements Serializable {
    private String titulo;
    private String subtitulo;
    private List<String> parrafos;
    private List<String> etiquetas;
    private Seccion seccion;
    private Autor autor;
    private Portada portada;
    private List<ImagenesNoticia> imagenes;
}
