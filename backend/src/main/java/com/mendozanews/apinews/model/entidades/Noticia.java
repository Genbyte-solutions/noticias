package com.mendozanews.apinews.model.entidades;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;
import org.hibernate.annotations.GenericGenerator;


@Builder
@AllArgsConstructor
@Table(name = "noticia")
@Entity
@Data
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
public class Noticia {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", updatable = true, nullable = false)
    private String id;

    private String titulo;

    @Column(length = 1500)
    private String subtitulo;

    @ManyToOne
    @JoinColumn(name = "portada_id")
    private Portada portada;

    @ManyToOne
    @JoinColumn(name = "seccion_id", referencedColumnName = "id")
    private Seccion seccion;

    @ManyToOne
    @JoinColumn(name = "autor_id", referencedColumnName = "id")
    private Autor autor;

    @ElementCollection
    @CollectionTable(name = "parrafos", joinColumns = @JoinColumn(name = "noticia_id"))
    @Column(name = "parrafo", length = 3000)
    private List<String> parrafos;

    @ElementCollection
    @CollectionTable(name = "etiquetas", joinColumns = @JoinColumn(name = "noticia_id"))
    @Column(name = "etiqueta", length = 30)
    private List<String> etiquetas;

    @Column(name = "fecha_publicacion", columnDefinition = "TIMESTAMP")
   
    private Date fechaPublicacion;

    @Column(name = "fecha_edicion")
    @Temporal(TemporalType.DATE)
    private Date fechaEdicion;
    

    private boolean activa;
    
    @OneToMany(mappedBy = "noticiaId")
    private List<Imagen> imagenes;
    

    public Noticia(String id, String titulo, String subtitulo, List<String> parrafos, List<String> etiquetas,
                 Seccion seccion, Autor autor, Date fechaPublicacion, Date fechaEdicion, boolean activa,
                   Portada portada ) {
        this.id = id;
        this.titulo = titulo;
        this.subtitulo = subtitulo;
        this.parrafos = parrafos;
        this.etiquetas = etiquetas;
        this.seccion = seccion;
        this.autor = autor;
        this.fechaPublicacion = fechaPublicacion;
        this.fechaEdicion = fechaEdicion;
        this.activa = activa;
        this.portada = portada;
       
      
       
    }

    public Noticia() {
    }


}
