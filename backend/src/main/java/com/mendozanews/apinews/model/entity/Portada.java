
package com.mendozanews.apinews.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "portada")
public class Portada {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "portada_id")
    private String portadaId;

    @Column(name = "tipo_mime")
    private String tipoMime;

    @Column(name = "nombre_archivo")
    private String nombreArchivo;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "contenido", columnDefinition = "LONGBLOB")
    private byte[] contenido;
}
