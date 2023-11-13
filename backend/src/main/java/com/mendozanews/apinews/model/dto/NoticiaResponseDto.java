package com.mendozanews.apinews.model.dto;
import java.util.Date;
import com.mendozanews.apinews.model.entidades.Autor;
import com.mendozanews.apinews.model.entidades.Seccion;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NoticiaResponseDto {
    private String titulo;
     private String id;
    private Seccion seccion;

   private Autor autor;
    private Date fechaPublicacion;
    public NoticiaResponseDto(String titulo, String id, Seccion seccion, Autor autor, Date fechaPublicacion) {
        this.titulo = titulo;
        this.id = id;
        this.seccion = seccion;
        this.autor = autor;
        this.fechaPublicacion = fechaPublicacion;
    }

    public NoticiaResponseDto() {
    }

    public void setFechaPublicacion(Date fechaPublicacion) { // Cambia el nombre del método a camelCase
        this.fechaPublicacion = fechaPublicacion;
    }

    public Date getFechaPublicacion() { // Cambia el nombre del método a camelCase
        return this.fechaPublicacion;
    }

}
