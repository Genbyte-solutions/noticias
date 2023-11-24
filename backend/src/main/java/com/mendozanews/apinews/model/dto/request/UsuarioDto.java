package com.mendozanews.apinews.model.dto.request;

import com.mendozanews.apinews.model.enums.Rol;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class UsuarioDto implements Serializable {
    @NotNull
    private String nombre;
    @NotNull
    private String apellido;
    @NotNull
    private String nombreUsuario;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String confirmPassword;
    @NotNull
    @Enumerated
    private Rol rol;
    @NotNull
    private String telefono;

    public UsuarioDto(String nombre, String apellido, String nombreUsuario, String email, String password, String confirmPassword, Rol rol, String telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.rol = rol;
        this.telefono = telefono;
    }
}
