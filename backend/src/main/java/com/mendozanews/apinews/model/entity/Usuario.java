package com.mendozanews.apinews.model.entity;

import com.mendozanews.apinews.model.enums.Rol;

import jakarta.persistence.*;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
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

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "nombre_usuario", nullable = false, unique = true)
    private String nombreUsuario;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "alta")
    private Boolean alta;

    @Column(name = "telefono", unique = true)
    private String telefono;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol")
    private Rol rol;

    @CreationTimestamp
    @Column(name = "fecha_registro", updatable = false)
    private Timestamp fechaRegistro;

    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "foto", referencedColumnName = "imagen_id")
    private Imagen foto;
}