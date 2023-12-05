package com.mendozanews.apinews.service.interfaces;

import com.mendozanews.apinews.model.dto.request.UsuarioDto;
import com.mendozanews.apinews.model.entity.Usuario;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IUsuario {

    Usuario crearUsuario(UsuarioDto usuarioDto, MultipartFile foto) throws IOException;

    Usuario editarUsuario(Usuario usuario, UsuarioDto usuarioDto, MultipartFile foto) throws IOException;

    List<Usuario> listarUsuarios();

    Usuario buscarUsuarioPorId(String usuarioId);

    Usuario buscarUsuario(String entrada);

    void cambiarEstadoDeAlta(String usuarioId);

    void eliminarUsuarioPorId(String usuarioId);
}
