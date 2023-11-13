
package com.mendozanews.apinews.model.entidades;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Portada {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Lob
    @Column(name = "imagen", columnDefinition = "LONGBLOB")
    private byte[] imagen;

    private String mime;

    private String nombre;

    @OneToOne
@JoinColumn(name = "noticia_id")
private Noticia noticia;

    
  


    public Portada(String id, byte[] imagen, String mime, String nombre, Portada noticiaId) {
        this.id = id;
        this.imagen = imagen;
        this.mime = mime;
        this.nombre = nombre;
       
    }

    public Portada() {
    }

    public String getId() {
        return id;
    }
   
    
}
