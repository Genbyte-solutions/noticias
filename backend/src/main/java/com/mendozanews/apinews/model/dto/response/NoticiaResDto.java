package com.mendozanews.apinews.model.dto.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class NoticiaResDto implements Serializable {
    private String noticiaId;
    private String titulo;
    private String subtitulo;
    private List<String> parrafos;
    private List<String> etiquetas;
    private Integer vistas;
    private SeccionResDto seccionResDto;
    private AutorResDto autorResDto;

    public NoticiaResDto(String noticiaId, String titulo, String subtitulo, List<String> parrafos, List<String> etiquetas, Integer vistas, SeccionResDto seccionResDto, AutorResDto autorResDto) {
        this.noticiaId = noticiaId;
        this.titulo = titulo;
        this.subtitulo = subtitulo;
        this.parrafos = parrafos;
        this.etiquetas = etiquetas;
        this.vistas = vistas;
        this.seccionResDto = seccionResDto;
        this.autorResDto = autorResDto;
    }
}
