// Imagen.java
package com.mendozanews.apinews.model.entidades;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;



@Entity
@Getter
@Setter
@Data

@JsonIgnoreProperties({ "hibernateLazyInitializer" })
public class Imagen {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @Column(name = "mime")
    private String mime;
    @Column(name = "nombre")
    private String nombre;
    
    @ManyToOne
    @JoinColumn(name = "noticia_id" ) 
    private Noticia noticiaId;

 
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "contenido", nullable = false, columnDefinition = "LONGBLOB")
    private byte[] contenido;

    public Imagen(String id, String mime, String nombre, Noticia noticiaId, byte[] contenido) {
        this.id = id;
        this.mime = mime;
        this.nombre = nombre;
        this.noticiaId = noticiaId;
        this.contenido = contenido;
    }

    public Imagen() {
    }

}
