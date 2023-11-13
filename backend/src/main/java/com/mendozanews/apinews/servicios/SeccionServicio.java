package com.mendozanews.apinews.servicios;

import com.mendozanews.apinews.excepciones.MiException;
import com.mendozanews.apinews.model.entidades.Imagen;
import com.mendozanews.apinews.model.entidades.Seccion;
import com.mendozanews.apinews.repositorios.SeccionRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SeccionServicio {

    @Autowired
    private SeccionRepositorio sr;
    @Autowired
    private ImagenServicio is;

    // CREA SECCION ENTERA
    @Transactional
    public void crearSeccion(String codigo, String nombre, MultipartFile archivo) throws MiException {

        validar(codigo, nombre);

        Seccion seccion = new Seccion();

        seccion.setCodigo(codigo);
        seccion.setNombre(nombre);

        Imagen icono = is.guardar(archivo);

        seccion.setIcono(icono);

        sr.save(seccion);
    }

    // LISTA TODAS LAS SECCIONES
    public List<Seccion> listarSecciones() {
        List<Seccion> secciones = sr.findAll();
        return secciones;
    }

    // OBTIENE UNA SECCION POR ID
    public Seccion getOne(String id) {
        return sr.getReferenceById(id);
    }

    // OBTIENE UNA SECCION POR CODIGO
    public Seccion buscarPorCodigo(String codigo) {
        return sr.buscarPorCodigo(codigo);
    }

    // OBTIENE UNA SECCION POR NOMBRE
    public Seccion buscarPorNombre(String nombre) {
        return sr.buscarPorNombre(nombre);
    }

    // MODIFICA SECCION ENTERA
    @Transactional
    public void modificarSeccion(String id, String codigo, String nombre, MultipartFile archivo) throws MiException {

        validar(id, codigo, nombre);

        Optional<Seccion> respuesta = sr.findById(id);

        if (respuesta.isPresent()) {

            Seccion seccion = respuesta.get();

            seccion.setCodigo(codigo);
            seccion.setNombre(nombre);

            String idImg = null;

            if (seccion.getIcono() != null) {
                idImg = seccion.getIcono().getId();
            }

            Imagen icono = is.actualizar(archivo, idImg);

            seccion.setIcono(icono);

            sr.save(seccion);
        }
    }

    // ELIMINA SECCION POR ID
    @Transactional
    public void eliminarSeccionId(String id) throws MiException {
        Optional<Seccion> respuesta = sr.findById(id);
        if (respuesta.isPresent()) {
            sr.deleteById(id);
        } else {
            throw new MiException("No se encontro la seccion");
        }
    }

    // VALIDA STRING ID, STRING CODIGO Y STRING NOMBRE
    public void validar(String id, String codigo, String nombre) throws MiException {
        if (id == null || id.isEmpty()) {
            throw new MiException("El id de la sección no puede ser nulo o estar vacío");
        }
        if (codigo == null || codigo.isEmpty()) {
            throw new MiException("El codigo de la sección no puede ser nulo o estar vacío");
        }
        if (nombre == null || nombre.isEmpty()) {
            throw new MiException("El nombre de la sección no puede ser nulo o estar vacío");
        }
    }

    // VALIDA STRING CODIGO Y STRING NOMBRE
    public void validar(String codigo, String nombre) throws MiException {
        if (codigo == null || codigo.isEmpty()) {
            throw new MiException("El codigo de la sección no puede ser nulo o estar vacío");
        }
        if (nombre == null || nombre.isEmpty()) {
            throw new MiException("El nombre de la sección no puede ser nulo o estar vacío");
        }
    }
}
