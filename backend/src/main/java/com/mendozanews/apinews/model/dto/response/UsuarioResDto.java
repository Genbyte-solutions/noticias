package com.mendozanews.apinews.model.dto.response;

import com.mendozanews.apinews.model.enums.Rol;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class UsuarioResDto implements Serializable {
    private String usuarioId;
    private String nombre;
    private String apellido;
    private String nombreUsuario;
    private Rol rol;
    private String email;
    private String telefono;

    public UsuarioResDto(String usuarioId, String nombre, String apellido, String nombreUsuario, Rol rol, String email, String telefono) {
        this.usuarioId = usuarioId;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nombreUsuario = nombreUsuario;
        this.rol = rol;
        this.email = email;
        this.telefono = telefono;
    }
}
