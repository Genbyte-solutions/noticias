package com.mendozanews.apinews.mapper;

import com.mendozanews.apinews.model.dto.response.UsuarioResDto;
import com.mendozanews.apinews.model.entity.Usuario;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public class UsuarioMapper {

    public UsuarioResDto toDTO(Usuario usuario){
        return UsuarioResDto.builder()
                .usuarioId(usuario.getUsuarioId())
                .nombre(usuario.getNombre())
                .nombreUsuario(usuario.getNombreUsuario())
                .apellido(usuario.getApellido())
                .rol(usuario.getRol())
                .email(usuario.getEmail())
                .telefono(usuario.getTelefono())
                .build();
    }

    public List<UsuarioResDto> toDTOs(List<Usuario> usuarios) {
        if (usuarios == null) return null;

        List<UsuarioResDto> list = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            list.add(toDTO(usuario));
        }

        return list;
    }
}
