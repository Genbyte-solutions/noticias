package com.mendozanews.apinews.model.dto.response;

import com.mendozanews.apinews.model.entidades.*;
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

    public NoticiaResDto(String titulo, String subtitulo, List<String> parrafos, List<String> etiquetas,
                         Seccion seccion, Autor autor, Portada portada, List<ImagenesNoticia> imagenes) {
        this.titulo = titulo;
        this.subtitulo = subtitulo;
        this.parrafos = parrafos;
        this.etiquetas = etiquetas;
        this.seccion = seccion;
        this.autor = autor;
        this.portada = portada;
        this.imagenes = imagenes;
    }
}
