package com.mendozanews.apinews.servicios;

import com.mendozanews.apinews.excepciones.MiException;
import com.mendozanews.apinews.model.entidades.Autor;
import com.mendozanews.apinews.model.entidades.Imagen;
import com.mendozanews.apinews.repositorios.AutorRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AutorServicio {

    @Autowired
    private AutorRepositorio ar;
    @Autowired
    private ImagenServicio is;

    // CREA UN AUTOR ENTERO
    @Transactional
    public void crearAutor(String nombre, String apellido, MultipartFile archivo) throws MiException {

        validar(nombre, apellido);

        Autor autor = new Autor();

        autor.setNombre(nombre);
        autor.setApellido(apellido);

        Imagen foto = is.guardar(archivo);

        autor.setFoto(foto);

        ar.save(autor);
    }

    // LISTA TODOS LOS AUTORES
    public List<Autor> listarAutores() {

        List<Autor> autores = new ArrayList<>();

        autores = ar.findAll();

        return autores;
    }

    // MODIFICA UN AUTOR ENTERO
    @Transactional
    public void modificarAutor(String id, String nombre, String apellido, MultipartFile archivo) throws MiException {

        validar(id, nombre, apellido);

        Optional<Autor> respuesta = ar.findById(id);

        if (respuesta.isPresent()) {

            Autor autor = respuesta.get();

            autor.setNombre(nombre);
            autor.setApellido(apellido);

            String idImg = null;

            if (autor.getFoto() != null) {
                idImg = autor.getFoto().getId();
            }

            Imagen foto = is.actualizar(archivo, idImg);

            autor.setFoto(foto);

            ar.save(autor);
        }
    }

    // OBTIENE UN AUTOR POR ID
    public Autor getOne(String id) {
        return ar.getReferenceById(id);
    }

    // ELIMINA AUTOR POR ID
    @Transactional
    public void eliminarAutorId(String id) throws MiException {
        Optional<Autor> respuesta = ar.findById(id);
        if (respuesta.isPresent()) {
            ar.deleteById(id);
        } else {
            throw new MiException("No se encontro el autor");
        }
    }

    // VALIDA STRINGS ID, EL NOMBRE Y APELLIDO
    public void validar(String id, String nombre, String apellido) throws MiException {
        if (id == null || id.isEmpty()) {
            throw new MiException("El id no puede ser nulo o estar vacío");
        }
        validar(nombre, apellido);
    }

    // VALIDA STRINGS NOMBRE Y APELLIDO
    public void validar(String nombre, String apellido) throws MiException {
        if (nombre == null || nombre.isEmpty()) {
            throw new MiException("El nombre no puede ser nulo o estar vacío");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new MiException("El apellido no puede ser nulo o estar vacío");
        }
    }
}
