package com.mendozanews.apinews.controllers;

import com.mendozanews.apinews.model.dto.request.UsuarioDto;
import com.mendozanews.apinews.model.entidades.Usuario;
import com.mendozanews.apinews.servicios.interfaces.IUsuario;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UsuarioControlador {

    private final IUsuario usuarioServicio;

    public UsuarioControlador(IUsuario usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    @PostMapping("/usuario")
    public ResponseEntity<?> crearUsuario(@ModelAttribute @Valid UsuarioDto usuarioDto,
                                          @RequestPart(value = "foto", required = false) MultipartFile foto) {

        if (!usuarioDto.getPassword().equals(usuarioDto.getConfirmPassword())) {
            return new ResponseEntity<>("Las contraseñas no coinciden", HttpStatus.BAD_REQUEST);
        }

        try {
            usuarioServicio.crearUsuario(usuarioDto, foto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IOException ioEx) {
            return new ResponseEntity<>(ioEx.getMessage(), HttpStatus.CREATED);
        }
    }

    @PutMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> editarUsuario(@PathVariable("usuarioId") String usuarioId,
                                           @ModelAttribute @Valid UsuarioDto usuarioDto,
                                           @RequestPart(value = "foto", required = false) MultipartFile foto) {
        try {

            Usuario usuario = usuarioServicio.buscarUsuarioPorId(usuarioId);
            if (usuario == null) {
                return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
            }

            usuarioServicio.editarUsuario(usuario, usuarioDto, foto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IOException ioEx) {
            return new ResponseEntity<>(ioEx.getMessage(), HttpStatus.CREATED);
        }
    }

    @GetMapping("/usuarios")
    public ResponseEntity<?> obtenerUsuarios() {

        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        if (usuarios.isEmpty()) {
            return new ResponseEntity<>("no se encontraron usuarios", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> buscarUsuarioPorId(@PathVariable("usuarioId") String usuarioId) {

        Usuario usuario = usuarioServicio.buscarUsuarioPorId(usuarioId);
        if (usuario == null) {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @GetMapping("/usuario")
    public ResponseEntity<?> buscarUsuarioPorEmailONombreUsuario(@RequestParam("buscar") String buscar) {

        Usuario usuario = usuarioServicio.buscarUsuario(buscar);
        if (usuario == null) {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @PatchMapping("usuario/cambio_estado/{usuarioId}")
    public ResponseEntity<?> cambiarEstadoDeAlta(@PathVariable("usuarioId") String usuarioId) {

        usuarioServicio.cambiarEstadoDeAlta(usuarioId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
