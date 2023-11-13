package com.mendozanews.apinews.controllers;

import com.mendozanews.apinews.servicios.UsuarioServicio;
import com.mendozanews.apinews.tokens.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseBody
@RequestMapping("/api/v1")
public class ControllerLogin {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/entrar")
    public ResponseEntity<?> authenticate(@RequestBody LoginRequest loginRequest) {
        try {
            String email = loginRequest.getEmail();
            String password = loginRequest.getPassword();
            System.out.println("Email recibido: " + email);
            System.out.println("Contraseña recibida: " + password);

            // Lógica de autenticación utilizando el servicio de usuario
            boolean isAuthenticated = usuarioServicio.authenticate(email, password);

            if (isAuthenticated) {
                // Obtener la contraseña almacenada en la base de datos para el usuario dado
                String storedPassword = usuarioServicio.getStoredPasswordByEmail(email);

                // Decodificar la contraseña almacenada utilizando BCryptPasswordEncoder
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                if (passwordEncoder.matches(password, storedPassword)) {
                    // Genera un token JWT si la autenticación es exitosa
                    String token = jwtUtil.generateToken(email);
                    System.out.println("JWT recibido: " + token);

                    // Devuelve el token en la respuesta
                    return ResponseEntity.ok(token);
                } else {
                    return ResponseEntity.status(401).body("Credenciales inválidas");
                }
            } else {
                return ResponseEntity.status(401).body("Credenciales inválidas");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error en la autenticación ControllerLogin");
        }
    }
}
