package com.mendozanews.apinews.model.entidades;

import com.mendozanews.apinews.model.enums.Rol;

import jakarta.persistence.*;

import java.util.Date;

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
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "usuario_id")
    private String usuarioId;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "nombre_usuario")
    private String nombreUsuario;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "alta")
    private Boolean alta;

    @Column(name = "telefono")
    private String telefono;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol")
    private Rol rol;

    @Column(name = "fecha_alta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;

    @OneToOne
    @JoinColumn(name = "imagen", referencedColumnName = "imagen_id")
    private Imagen imagen;
}