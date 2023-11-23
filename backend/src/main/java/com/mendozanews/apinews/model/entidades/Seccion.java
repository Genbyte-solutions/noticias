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
@Table(name = "seccion")
public class Seccion {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "seccion_id")
    private String seccionId;

    @Column(name = "codigo", unique = true)
    private String codigo;

    @Column(name = "nombre", unique = true)
    private String nombre;

    @OneToOne
    @JoinColumn(name = "icono", referencedColumnName = "icono_seccion_id")
    private IconoSeccion icono;
}
