package com.mendozanews.apinews.service.impl;

import com.mendozanews.apinews.model.dto.request.UsuarioDto;
import com.mendozanews.apinews.model.entity.Imagen;
import com.mendozanews.apinews.model.entity.Usuario;
import com.mendozanews.apinews.repository.UsuarioRepositorio;
import com.mendozanews.apinews.service.interfaces.IUsuario;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import scala.util.Using;

@Service
public class UsuarioServicio implements UserDetailsService, IUsuario {

    private final UsuarioRepositorio usuarioRepo;
    private final ImagenServicio imagenServicio;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServicio(UsuarioRepositorio usuarioRepo, ImagenServicio imagenServicio, PasswordEncoder passwordEncoder) {
        this.usuarioRepo = usuarioRepo;
        this.imagenServicio = imagenServicio;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public Usuario crearUsuario(UsuarioDto usuarioDto, MultipartFile foto) throws IOException {

        Imagen fotoGuardada = null;
        if (foto != null) fotoGuardada = imagenServicio.guardarImagen(foto);
        return usuarioRepo.save(Usuario.builder()
                .nombre(usuarioDto.getNombre())
                .apellido(usuarioDto.getApellido())
                .nombreUsuario(usuarioDto.getNombreUsuario())
                .email(usuarioDto.getEmail())
                .password(new BCryptPasswordEncoder().encode(usuarioDto.getPassword()))
                .alta(true)
                .telefono(usuarioDto.getTelefono())
                .rol(usuarioDto.getRol())
                .foto(fotoGuardada)
                .build());
    }

    @Transactional
    @Override
    public Usuario editarUsuario(Usuario usuario, UsuarioDto usuarioDto, MultipartFile foto) throws IOException {

        Imagen fotoActualizada = null;
        if (foto != null) fotoActualizada = imagenServicio.actualizarImagen(foto, usuario.getFoto().getImagenId());

        usuario.setNombre(usuarioDto.getNombre());
        usuario.setApellido(usuarioDto.getApellido());
        usuario.setNombreUsuario(usuarioDto.getNombreUsuario());
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setPassword(new BCryptPasswordEncoder().encode(usuarioDto.getConfirmPassword()));
        usuario.setTelefono(usuarioDto.getTelefono());
        usuario.setRol(usuarioDto.getRol());

        if (fotoActualizada!=null) usuario.setFoto(fotoActualizada);

        return usuarioRepo.save(usuario);
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarUsuarios() {
        return usuarioRepo.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Usuario buscarUsuarioPorId(String usuarioId) {
        return usuarioRepo.findById(usuarioId).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public Usuario buscarUsuario(String entrada) {
        return usuarioRepo.buscarPorEmailONombreUsuarios(entrada);
    }

    @Transactional
    @Override
    public void cambiarEstadoDeAlta(String usuarioId) {
        Usuario usuario = usuarioRepo.findById(usuarioId).orElse(null);
        if (usuario != null) {
            usuario.setAlta(!usuario.getAlta());
            usuarioRepo.save(usuario);
        }
    }

    @Transactional
    public void eliminarUsuarioPorId(String id) {
        usuarioRepo.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepo.buscarPorEmailONombreUsuarios(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado.");
        }
        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name())
        );
        HttpSession session = obtenerSessionActual();
        session.setAttribute("usuariosession", usuario);
        return new User(usuario.getNombreUsuario(), usuario.getPassword(), authorities);
    }

    private HttpSession obtenerSessionActual() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession();
    }

    public boolean authenticate(String email, String password) {
        try {
            Usuario usuario = buscarUsuario(email);
            return usuario != null && verificarContrasena(password, usuario.getPassword());
        } catch (Exception e) {
            manejarExcepcionAutenticacion(e);
            return false;
        }
    }

    public String getStoredPasswordByEmail(String email) {
        Usuario usuario = usuarioRepo.buscarPorEmailONombreUsuarios(email);
        return usuario != null ? usuario.getPassword() : null;
    }

    public boolean verificarContrasena(String password, String hashedPassword) {
        return passwordEncoder.matches(password, hashedPassword);
    }

    private void manejarExcepcionAutenticacion(Exception e) {
        System.err.println("Error en la autenticación: usuarioServicio" + e.getMessage());
        e.printStackTrace();
    }
}