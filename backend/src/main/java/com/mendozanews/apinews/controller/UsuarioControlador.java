package com.mendozanews.apinews.controller;

import com.mendozanews.apinews.exception.ResourceBadRequestException;
import com.mendozanews.apinews.exception.ResourceNotFoundException;
import com.mendozanews.apinews.mapper.UsuarioMapper;
import com.mendozanews.apinews.model.dto.request.UsuarioDto;
import com.mendozanews.apinews.model.dto.response.UsuarioResDto;
import com.mendozanews.apinews.model.entity.Usuario;
import com.mendozanews.apinews.model.enums.Rol;
import com.mendozanews.apinews.model.payload.ResponseMessage;
import com.mendozanews.apinews.service.interfaces.IUsuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Usuario")
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

    @Operation(
            summary = "Crea un usuario",
            responses = {
                    @ApiResponse(responseCode = "201",
                            description = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello"),
                    @ApiResponse(responseCode = "400",
                            description = "El servidor no pudo interpretar la solicitud dada una sintaxis inválida"),
                    @ApiResponse(responseCode = "500",
                            description = "El servidor ha encontrado una situación que no sabe cómo manejarla")
            }
    )
    @PostMapping("/usuario")
    public ResponseEntity<?> crearUsuario(@ModelAttribute @Valid UsuarioDto usuarioDto,
                                          @RequestPart(value = "foto", required = false) MultipartFile foto) {

        if (!usuarioDto.getPassword().equals(usuarioDto.getConfirmPassword())) {
            return new ResponseEntity<>(ResponseMessage.builder()
                    .mensaje("Las contraseñas no coinciden")
                    .build(), HttpStatus.BAD_REQUEST);
        }

        try {
            Usuario usuario = usuarioServicio.crearUsuario(usuarioDto, foto);
            UsuarioResDto usuarioResDto = usuarioMapper.toDTO(usuario);

            return new ResponseEntity<>(ResponseMessage.builder()
                    .mensaje("usuario añadido")
                    .recurso(usuarioResDto)
                    .build(), HttpStatus.CREATED);
        } catch (IOException ioException) {
            throw new ResourceBadRequestException(ioException.getMessage());
        }
    }

    @GetMapping("/usuario/defecto")
    public ResponseEntity<?> crearUsuarioPorDefectoEndpoint(
            @RequestParam("contrasena") String contrasena) {

        Usuario usuarioExistente = usuarioServicio.buscarUsuario("admin");
        if (usuarioExistente != null) {
            return new ResponseEntity<>("El usuario por defecto ya existe", HttpStatus.BAD_REQUEST);
        }

        UsuarioDto usuarioDto = UsuarioDto.builder()
                .nombre("admin")
                .apellido("admin")
                .nombreUsuario("admin")
                .email("admin@admin.com")
                .password(contrasena)
                .confirmPassword(contrasena)
                .rol(Rol.ADMIN)
                .telefono("123456789")
                .build();

        try {
            usuarioServicio.crearUsuario(usuarioDto, null);
        } catch (IOException ioException) {
            return new ResponseEntity<>("Error al crear el usuario por defecto", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Usuario por defecto creado exitosamente", HttpStatus.CREATED);
    }


    @Operation(
            summary = "Edita un usuario por ID",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "La solicitud ha tenido éxito"),
                    @ApiResponse(responseCode = "404",
                            description = "El servidor no pudo encontrar el contenido solicitado"),
                    @ApiResponse(responseCode = "400",
                            description = "El servidor no pudo interpretar la solicitud dada una sintaxis inválida"),
                    @ApiResponse(responseCode = "500",
                            description = "El servidor ha encontrado una situación que no sabe cómo manejarla")
            }
    )
    @PutMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> editarUsuario(@PathVariable("usuarioId") String usuarioId,
                                           @ModelAttribute @Valid UsuarioDto usuarioDto,
                                           @RequestPart(value = "foto", required = false) MultipartFile foto) {

        try {
            Usuario usuario = usuarioServicio.buscarUsuarioPorId(usuarioId);
            if (usuario == null) throw new ResourceNotFoundException("Usuario", "id", usuarioId);

            if (!passwordEncoder.matches(usuarioDto.getPassword(), usuario.getPassword())) {
                return new ResponseEntity<>(ResponseMessage.builder()
                        .mensaje("Contraseña incorrecta")
                        .build(), HttpStatus.BAD_REQUEST);
            }

            usuarioServicio.editarUsuario(usuario, usuarioDto, foto);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException ioException) {
            throw new ResourceBadRequestException(ioException.getMessage());
        }
    }

    @Operation(
            summary = "Busca un usuario por ID",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "La solicitud ha tenido éxito"),
                    @ApiResponse(responseCode = "404",
                            description = "El servidor no pudo encontrar el contenido solicitado"),
                    @ApiResponse(responseCode = "500",
                            description = "El servidor ha encontrado una situación que no sabe cómo manejarla")
            }
    )
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> buscarUsuarioPorId(@PathVariable("usuarioId") String usuarioId) {

        Usuario usuario = usuarioServicio.buscarUsuarioPorId(usuarioId);
        if (usuario == null) throw new ResourceNotFoundException("Usuario", "id", usuarioId);

        UsuarioResDto usuarioResDtoDto = usuarioMapper.toDTO(usuario);
        return new ResponseEntity<>(usuarioResDtoDto, HttpStatus.OK);
    }

    @Operation(
            summary = "Busca un usuario por Email o Nombre de usuario",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "La solicitud ha tenido éxito"),
                    @ApiResponse(responseCode = "404",
                            description = "El servidor no pudo encontrar el contenido solicitado"),
                    @ApiResponse(responseCode = "500",
                            description = "El servidor ha encontrado una situación que no sabe cómo manejarla")
            }
    )
    @GetMapping("/usuario")
    public ResponseEntity<?> buscarUsuarioPorEmailONombreUsuario(@RequestParam("buscar") String buscar) {

        Usuario usuario = usuarioServicio.buscarUsuario(buscar);
        if (usuario == null) throw new ResourceNotFoundException("Usuario", "Email/Nombre de usuario", buscar);

        UsuarioResDto usuarioResDtoDto = usuarioMapper.toDTO(usuario);
        return new ResponseEntity<>(usuarioResDtoDto, HttpStatus.OK);
    }

    @Operation(
            summary = "Lista de usuarios",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "La solicitud ha tenido éxito"),
                    @ApiResponse(responseCode = "404",
                            description = "El servidor no pudo encontrar el contenido solicitado"),
                    @ApiResponse(responseCode = "500",
                            description = "El servidor ha encontrado una situación que no sabe cómo manejarla")
            }
    )
    @GetMapping("/usuarios")
    public ResponseEntity<?> listarUsuarios() {

        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        if (usuarios.isEmpty()) throw new ResourceNotFoundException("usuarios");

        List<UsuarioResDto> usuariosResDto = usuarioMapper.toDTOs(usuarios);
        return new ResponseEntity<>(usuariosResDto, HttpStatus.OK);
    }

    @Operation(
            summary = "Cambia estado de usuario (activo - inactivo) por ID",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "La solicitud ha tenido éxito"),
                    @ApiResponse(responseCode = "404",
                            description = "El servidor no pudo encontrar el contenido solicitado"),
                    @ApiResponse(responseCode = "500",
                            description = "El servidor ha encontrado una situación que no sabe cómo manejarla")
            }
    )
    @PatchMapping("usuario/cambio_estado/{usuarioId}")
    public ResponseEntity<?> cambiarEstadoDeAlta(@PathVariable("usuarioId") String usuarioId) {

        Usuario usuario = usuarioServicio.buscarUsuarioPorId(usuarioId);
        if (usuario == null) throw new ResourceNotFoundException("Usuario", "id", usuarioId);

        usuarioServicio.cambiarEstadoDeAlta(usuarioId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Elimina un usuario por ID",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "La solicitud ha tenido éxito"),
                    @ApiResponse(responseCode = "500",
                            description = "El servidor ha encontrado una situación que no sabe cómo manejarla")
            }
    )
    @DeleteMapping("usuario/{usuarioId}")
    public ResponseEntity<?> eliminarUsuarioPorId(@PathVariable("usuarioId") String usuarioId) {
        usuarioServicio.eliminarUsuarioPorId(usuarioId);
        return new ResponseEntity<>(ResponseMessage.builder()
                .mensaje("Usuario eliminado")
                .build(), HttpStatus.OK);
    }
}
