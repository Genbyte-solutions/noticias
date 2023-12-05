package com.mendozanews.apinews.model.dto.request;

import com.mendozanews.apinews.model.enums.Rol;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

@Data
@Builder
public class UsuarioDto implements Serializable {

    @NotEmpty(message = "Requerido")
    @Size(min = 2, message = "Minimo 2 caracteres")
    private String nombre;

    private String apellido;

    @NotEmpty(message = "Requerido")
    @Size(min = 2, message = "Minimo 2 caracteres")
    private String nombreUsuario;

    @NotEmpty(message = "Requerido")
    @Email
    private String email;

    @NotEmpty(message = "Requerido")
    @Size(min = 6, message = "Minimo 6 caracteres")
    private String password;

    @NotEmpty(message = "Requerido")
    @Size(min = 6, message = "Minimo 6 caracteres")
    private String confirmPassword;

    @Enumerated(EnumType.STRING)
    private Rol rol;

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
