package com.mendozanews.apinews.controller;

import com.mendozanews.apinews.exception.ResourceBadRequestException;
import com.mendozanews.apinews.exception.ResourceNotFoundException;
import com.mendozanews.apinews.mapper.UsuarioMapper;
import com.mendozanews.apinews.model.dto.request.UsuarioDto;
import com.mendozanews.apinews.model.dto.response.UsuarioResDto;
import com.mendozanews.apinews.model.entity.Usuario;
import com.mendozanews.apinews.model.payload.ResponseMessage;
import com.mendozanews.apinews.service.interfaces.IUsuario;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1")
public class UsuarioControlador {

    private final IUsuario usuarioServicio;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    public UsuarioControlador(IUsuario usuarioServicio, UsuarioMapper usuarioMapper,
                              PasswordEncoder passwordEncoder) {
        this.usuarioServicio = usuarioServicio;
        this.usuarioMapper = usuarioMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/usuario")
    public ResponseEntity<?> crearUsuario(@ModelAttribute @Valid UsuarioDto usuarioDto,
                                          @RequestPart(value = "foto", required = false) MultipartFile foto) {

        if (!usuarioDto.getPassword().equals(usuarioDto.getConfirmPassword())) {
            return new ResponseEntity<>(ResponseMessage.builder()
                    .mensaje("Las contraseñas no coinciden")
                    .build(), HttpStatus.BAD_REQUEST);
        }

        try {
            usuarioServicio.crearUsuario(usuarioDto, foto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IOException ioException) {
            throw new ResourceBadRequestException(ioException.getMessage());
        }
    }

    @PutMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> editarUsuario(@PathVariable("usuarioId") String usuarioId,
                                           @ModelAttribute @Valid UsuarioDto usuarioDto,
                                           @RequestParam("currentPassword") String currentPassword,
                                           @RequestPart(value = "foto", required = false) MultipartFile foto) {

        try {
            Usuario usuario = usuarioServicio.buscarUsuarioPorId(usuarioId);
            if (usuario == null) throw new ResourceNotFoundException("Usuario", "id", usuarioId);

            if (!passwordEncoder.matches(currentPassword, usuario.getPassword())) {
                return new ResponseEntity<>(ResponseMessage.builder()
                        .mensaje("Contraseña incorrecta")
                        .build(), HttpStatus.BAD_REQUEST);
            }

            if (!usuarioDto.getPassword().equals(usuarioDto.getConfirmPassword())) {
                return new ResponseEntity<>(ResponseMessage.builder()
                        .mensaje("Las contraseñas no coinciden")
                        .build(), HttpStatus.BAD_REQUEST);
            }

            usuarioServicio.editarUsuario(usuario, usuarioDto, foto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IOException ioException) {
            throw new ResourceBadRequestException(ioException.getMessage());
        }
    }

    @GetMapping("/usuarios")
    public ResponseEntity<?> obtenerUsuarios() {

        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        if (usuarios.isEmpty()) throw new ResourceNotFoundException("Usuarios");

        List<UsuarioResDto> usuariosResDto = usuarioMapper.toDTOs(usuarios);
        return new ResponseEntity<>(usuariosResDto, HttpStatus.OK);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> buscarUsuarioPorId(@PathVariable("usuarioId") String usuarioId) {

        Usuario usuario = usuarioServicio.buscarUsuarioPorId(usuarioId);
        if (usuario == null) throw new ResourceNotFoundException("Usuario", "id", usuarioId);

        UsuarioResDto usuarioResDtoDto = usuarioMapper.toDTO(usuario);
        return new ResponseEntity<>(usuarioResDtoDto, HttpStatus.OK);
    }

    @GetMapping("/usuario")
    public ResponseEntity<?> buscarUsuarioPorEmailONombreUsuario(@RequestParam("buscar") String buscar) {

        Usuario usuario = usuarioServicio.buscarUsuario(buscar);
        if (usuario == null) throw new ResourceNotFoundException("Usuario", "Email/Nombre de usuario", buscar);

        UsuarioResDto usuarioResDtoDto = usuarioMapper.toDTO(usuario);
        return new ResponseEntity<>(usuarioResDtoDto, HttpStatus.OK);
    }

    @PatchMapping("usuario/cambio_estado/{usuarioId}")
    public ResponseEntity<?> cambiarEstadoDeAlta(@PathVariable("usuarioId") String usuarioId) {

        Usuario usuario = usuarioServicio.buscarUsuarioPorId(usuarioId);
        if (usuario == null) throw new ResourceNotFoundException("Usuario", "id", usuarioId);

        usuarioServicio.cambiarEstadoDeAlta(usuarioId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("usuario/{usuarioId}")
    public ResponseEntity<?> eliminarUsuarioPorId(@PathVariable("usuarioId") String usuarioId) {
        usuarioServicio.eliminarUsuarioPorId(usuarioId);
        return new ResponseEntity<>(ResponseMessage.builder()
                .mensaje("Usuario eliminado")
                .build(), HttpStatus.OK);
    }
}
