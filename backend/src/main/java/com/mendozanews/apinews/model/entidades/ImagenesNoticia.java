package com.mendozanews.apinews.model.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "imagenes_noticia")
public class ImagenesNoticia {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "imagen_noticia_id")
    private String imagenNoticiaId;

    @Column(name = "tipo_mime")
    private String tipoMime;

    @Column(name = "nombre_archivo")
    private String nombreArchivo;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "contenido", nullable = false, columnDefinition = "LONGBLOB")
    private byte[] contenido;

    @ManyToOne
    @JoinColumn(name = "noticia", referencedColumnName = "noticia_id")
    private Noticia noticia;
}
