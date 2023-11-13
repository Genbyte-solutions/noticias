package com.mendozanews.apinews.model.dto;
import java.util.Date; // Importa la clase Date


import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.mendozanews.apinews.model.entidades.Autor;
import com.mendozanews.apinews.model.entidades.Seccion;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NoticiaRequestDto {

    private Date fechaPublicacion;
    private String titulo;
    private String subtitulo;
    private List<String> parrafos;
    private List<String> etiquetas;
    private Seccion idSeccion;
    private Autor idAutor;
    private MultipartFile portada;
    private MultipartFile[] imagenes;

    public NoticiaRequestDto() {
    }

    public NoticiaRequestDto(Date fechaPublicacion, String titulo, String subtitulo, List<String> parrafos,
            List<String> etiquetas, Seccion idSeccion, Autor idAutor, MultipartFile portada, MultipartFile[] imagenes) {
        this.fechaPublicacion = fechaPublicacion;
        this.titulo = titulo;
        this.subtitulo = subtitulo;
        this.parrafos = parrafos;
        this.etiquetas = etiquetas;
        this.idSeccion = idSeccion;
        this.idAutor = idAutor;
        this.portada = portada;
        this.imagenes = imagenes;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public List<String> getParrafos() {
        return parrafos;
    }

    public List<String> getEtiquetas() {
        return etiquetas;
    }

    public Seccion getIdSeccion() {
        return idSeccion;
    }

    public Autor getIdAutor() {
        return idAutor;
    }

    public MultipartFile getPortada() {
        return portada;
    }

    public MultipartFile[] getImagenes() {
        return imagenes;
    }

    public Date getFechaPublicacion() { // Cambia el nombre del método a camelCase
        return this.fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) { // Cambia el nombre del método a camelCase
        this.fechaPublicacion = fechaPublicacion;
    }

}
