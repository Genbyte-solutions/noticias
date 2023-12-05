package com.mendozanews.apinews.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "noticia")
public class Noticia {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "noticia_id")
    private String noticiaId;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "subtitulo", length = 1500, nullable = false)
    private String subtitulo;

    @ElementCollection
    @CollectionTable(name = "parrafos", joinColumns = @JoinColumn(name = "noticia_id"))
    @Column(name = "parrafo", length = 3000)
    private List<String> parrafos;

    @ElementCollection
    @CollectionTable(name = "etiquetas", joinColumns = @JoinColumn(name = "noticia_id"))
    @Column(name = "etiqueta", length = 30)
    private List<String> etiquetas;

    @Column(name = "vistas")
    private Integer vistas;

    @Column(name = "activa")
    private boolean activa;

    @CreationTimestamp
    @Column(name = "fecha_publicacion", updatable = false)
    private Timestamp fechaPublicacion;

    @UpdateTimestamp
    @Column(name = "fecha_edicion")
    private Timestamp fechaEdicion;

    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "portada", referencedColumnName = "portada_id")
    private Portada portada;

    @ManyToOne
    @JoinColumn(name = "seccion", referencedColumnName = "seccion_id", nullable = false)
    private Seccion seccion;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "autor", referencedColumnName = "autor_id", nullable = false)
    private Autor autor;

    @OneToMany(mappedBy = "noticia", cascade = CascadeType.ALL)
    private List<ImagenesNoticia> imagenesNoticia;
}
