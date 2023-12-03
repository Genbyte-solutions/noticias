package com.mendozanews.apinews.model.entity;

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
@Table(name = "imagen")
public class Imagen {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "imagen_id")
    private String imagenId;

    @Column(name = "tipo_mime")
    private String tipoMime;

    @Column(name = "nombre_archivo")
    private String nombreArchivo;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "contenido", nullable = false, columnDefinition = "LONGBLOB")
    private byte[] contenido;
}