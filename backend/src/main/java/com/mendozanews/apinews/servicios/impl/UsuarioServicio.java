package com.mendozanews.apinews.servicios.impl;

import com.mendozanews.apinews.exception.MiException;
import com.mendozanews.apinews.model.dto.request.UsuarioDto;
import com.mendozanews.apinews.model.entidades.Imagen;
import com.mendozanews.apinews.model.entidades.Usuario;
import com.mendozanews.apinews.model.enums.Rol;
import com.mendozanews.apinews.repositorios.UsuarioRepositorio;
import com.mendozanews.apinews.servicios.interfaces.IUsuario;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioServicio implements UserDetailsService, IUsuario {

    private final UsuarioRepositorio usuarioRepo;
    private final ImagenServicio imagenServicio;

    public UsuarioServicio(UsuarioRepositorio usuarioRepo, ImagenServicio imagenServicio) {
        this.usuarioRepo = usuarioRepo;
        this.imagenServicio = imagenServicio;
    }

    // CARGA UN USUARIO
    @Transactional
    @Override
    public void crearUsuario(UsuarioDto usuarioDto, MultipartFile foto) throws IOException {

        Imagen fotoGuardada = null;
        if (foto != null) fotoGuardada = imagenServicio.guardarImagen(foto);

        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDto.getNombre());
        usuario.setApellido(usuarioDto.getApellido());
        usuario.setNombreUsuario(usuarioDto.getNombreUsuario());
        usuario.setEmail(usuarioDto.getNombreUsuario());
        usuario.setPassword(new BCryptPasswordEncoder().encode(usuarioDto.getPassword()));
        usuario.setAlta(true);
        usuario.setTelefono(usuarioDto.getTelefono());
        if (Rol.ADMIN.equals(usuarioDto.getRol())) {
            usuario.setRol(Rol.ADMIN);
        } else {
            usuario.setRol(Rol.USUARIO);
        }
        if (fotoGuardada != null) usuario.setFoto(fotoGuardada);

        usuarioRepo.save(usuario);
    }

    // MODIFICA UN USUARIO
    @Transactional
    @Override
    public void editarUsuario(Usuario usuario, UsuarioDto usuarioDto, MultipartFile foto) throws IOException {

        Imagen fotoActualizada = null;
        if (foto != null) fotoActualizada = imagenServicio.actualizarImagen(foto, usuario.getFoto().getImagenId());

        usuario.setNombre(usuarioDto.getNombre());
        usuario.setApellido(usuarioDto.getApellido());
        usuario.setNombreUsuario(usuarioDto.getNombreUsuario());
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setPassword(new BCryptPasswordEncoder().encode(usuarioDto.getPassword()));
        usuario.setTelefono(usuarioDto.getTelefono());
        if (fotoActualizada != null) usuario.setFoto(fotoActualizada);

        usuarioRepo.save(usuario);
    }

    // LISTA TODOS LOS USUARIOS
    @Transactional(readOnly = true)
    public List<Usuario> listarUsuarios() {
        List<Usuario> noticias = usuarioRepo.findAll();
        return noticias;
    }

    // OBTIENE UN USUARIO POR ID
    @Transactional(readOnly = true)
    @Override
    public Usuario buscarUsuarioPorId(String usuarioId) {
        return usuarioRepo.findById(usuarioId).orElse(null);
    }

    // BUSCA USUARIO POR EMAIL O NOMBRE DE USUARIO
    @Transactional(readOnly = true)
    @Override
    public Usuario buscarUsuario(String entrada) {
        return usuarioRepo.buscarPorEmailONombreUsuarios(entrada);
    }

    // CAMBIA ALTA UN USUARIO POR ID
    @Transactional
    @Override
    public void cambiarEstadoDeAlta(String usuarioId) {
        Usuario usuario = usuarioRepo.findById(usuarioId).orElse(null);
        if (usuario != null) {
            usuario.setAlta(!usuario.getAlta());
            usuarioRepo.save(usuario);
        }
    }

    // ELIMINA USUARIO POR ID
    @Transactional
    public void eliminarUsuarioPorId(String id) {
        Usuario usuario = usuarioRepo.findById(id).orElse(null);
        if (usuario != null && usuario.getFoto().getImagenId() != null) {
            imagenServicio.eliminarImagenPorId(usuario.getFoto().getImagenId());
        }
        usuarioRepo.deleteById(id);
    }

    // VALIDA EL USUARIO POR EMAIL
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepo.buscarPorEmailONombreUsuarios(username);

        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();
            if (usuario.getRol() == Rol.ADMIN) {
                permisos.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            } else {
                permisos.add(new SimpleGrantedAuthority("ROLE_USER"));
            }

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession();
            session.setAttribute("usuariosession", usuario);

            return new User(usuario.getNombreUsuario(), usuario.getPassword(), permisos);
        } else {
            throw new UsernameNotFoundException("Usuario no encontrado.");
        }
    }

    @Transactional
    public void usuarioAdmin() throws MiException {

        Usuario usuario = new Usuario();
        usuario.setNombre("admin");
        usuario.setApellido("admin");
        usuario.setEmail("admin@admin");
        usuario.setNombreUsuario("admin");
        usuario.setTelefono("123456789");
        usuario.setPassword(new BCryptPasswordEncoder().encode("123456"));
        usuario.setRol(Rol.ADMIN);
        usuario.setAlta(true);
        usuario.setFoto(null);
        usuarioRepo.save(usuario);
    }

    public boolean authenticate(String email, String password) {
        try {
            Usuario usuario = buscarUsuario(email);
            if (usuario != null) {
                String hashedPassword = usuario.getPassword();
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                if (passwordEncoder.matches(password, hashedPassword)) {
                    System.out.println("Autenticación exitosa para el usuario: " + usuario.getEmail());
                    return true;
                } else {
                    System.out.println("La contraseña no coincide para el usuario: " + usuario.getNombreUsuario());
                    return false;
                }
            } else {
                System.out.println("No se encontró ningún usuario con el email: " + email);
                return false;
            }
        } catch (Exception e) {
            // Manejo de la excepción
            System.err.println("Error en la autenticación: usuarioServicio" + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Otros métodos de tu servicio

    public String getStoredPasswordByEmail(String email) {
        Usuario usuario = usuarioRepo.buscarPorEmailONombreUsuarios(email);
        return usuario != null ? usuario.getPassword() : null;
    }
}