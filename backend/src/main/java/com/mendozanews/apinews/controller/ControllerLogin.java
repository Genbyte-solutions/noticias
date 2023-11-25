package com.mendozanews.apinews.controller;

import com.mendozanews.apinews.model.dto.request.LoginDto;
import com.mendozanews.apinews.service.impl.UsuarioServicio;
import com.mendozanews.apinews.tokens.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ControllerLogin {

    private final UsuarioServicio usuarioServicio;
    private final JwtUtil jwtUtil;

    public ControllerLogin(UsuarioServicio usuarioServicio, JwtUtil jwtUtil) {
        this.usuarioServicio = usuarioServicio;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/entrar")
    public ResponseEntity<?> authenticate(@RequestBody LoginDto loginDto) {
        try {
            String email = loginDto.getEmail();
            String password = loginDto.getPassword();
            System.out.println("Email recibido: " + email);
            System.out.println("Contraseña recibida: " + password);

            // Lógica de autenticación utilizando el servicio de usuario
            if (usuarioServicio.authenticate(email, password)) {
                // Obtener la contraseña almacenada en la base de datos para el usuario dado
                String storedPassword = usuarioServicio.getStoredPasswordByEmail(email);

                // Verificar la contraseña utilizando el servicio de usuario
                if (this.usuarioServicio.verificarContrasena(password, storedPassword)) {
                    // Genera un token JWT si la autenticación es exitosa
                    String token = jwtUtil.generateToken(email);
                    System.out.println("JWT recibido: " + token);

                    // Devuelve el token en la respuesta
                    return ResponseEntity.ok(token);
                }
            }

            // Devuelve una respuesta 401 para credenciales inválidas
            return ResponseEntity.status(401).body("Credenciales inválidas");
        } catch (Exception e) {
            // Devuelve una respuesta 500 para errores en la autenticación
            return ResponseEntity.status(500).body("Error en la autenticación");
        }
    }
}
