package com.mendozanews.apinews.model.dto.request;

import java.io.Serializable;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.mendozanews.apinews.model.entidades.Autor;
import com.mendozanews.apinews.model.entidades.Seccion;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NoticiaDto implements Serializable {

    private String titulo;
    private String subtitulo;
    private List<String> parrafos;
    private List<String> etiquetas;
    private String seccionId;
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
