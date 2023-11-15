package com.mendozanews.apinews.servicios;

import com.mendozanews.apinews.excepciones.MiException;
import com.mendozanews.apinews.model.entidades.Imagen;
import com.mendozanews.apinews.repositorios.ImagenRepositorio;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImagenServicio {

    @Autowired
    private ImagenRepositorio ir;

    // Guarda imagen en el repositorio de imagenes
    public List<Imagen> guardarImagenes(MultipartFile[] imagenesParaGuardar) {
        List<Imagen> imagenGuardada = new ArrayList<>();
        for (MultipartFile imagen : imagenesParaGuardar) {
            try {
                Imagen nuevaImagen = new Imagen();
                nuevaImagen.setNombre(imagen.getOriginalFilename());
                nuevaImagen.setContenido(imagen.getBytes());
                nuevaImagen.setMime(imagen.getContentType());
                imagenGuardada.add(ir.save(nuevaImagen));
            } catch (IOException e) {
                // Manejo de excepciones
            }
        }
        return imagenGuardada;
    }

    // GUARDA UNA IMAGEN
    @Transactional
    public Imagen guardar(MultipartFile archivo) throws MiException {

        if (archivo != null) {
            try {
                Imagen imagen = new Imagen();
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                imagen = ir.save(imagen); // Guarda la imagen en la base de datos
                return imagen;
            } catch (IOException e) {
                throw new MiException("No se pudo guardar la imagen");
            }
        }
        return null;
    }

    // ACTUALIZA UNA IMAGEN
    @Transactional
    public Imagen actualizar(MultipartFile archivo, String id) throws MiException {

        archivo = validar(archivo);

        if (archivo != null) {

            try {

                Imagen imagen = new Imagen();

                if (id != null) {

                    Optional<Imagen> respuesta = ir.findById(id);

                    if (respuesta.isPresent()) {
                        imagen = respuesta.get();
                    }
                }

                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());

                return ir.save(imagen);

            } catch (IOException e) {
                throw new MiException("No se pudo actualizar la imagen" + e.getMessage());
            }
        }

        if (id != null) {
            ir.deleteById(id);
        }

        return null;
    }

    // GUARDA UNA LISTA DE ARCHIVOS IMG Y DEVUELVE UNA LISTA DE IMAGENES CON ID
    @Transactional
    public List<Imagen> guardarLista(List<MultipartFile> archivos) throws MiException {
        List<Imagen> imagenesGuardadas = new ArrayList<>();

        for (MultipartFile archivo : archivos) {

            archivo = validar(archivo);

            if (archivo != null) {
                try {
                    Imagen imagen = new Imagen();

                    imagen.setMime(archivo.getContentType());
                    imagen.setNombre(archivo.getName());
                    imagen.setContenido(archivo.getBytes());

                    imagenesGuardadas.add(ir.save(imagen));
                } catch (IOException e) {
                    throw new MiException("No se pudo guardar la lista de imágenes: " + e.getMessage());
                }
            }
        }

        return imagenesGuardadas;
    }

    // OBTIENE UNA IMAGEN POR ID
    public Imagen getOne(String id) {
        return ir.getReferenceById(id);
    }

    // ELIMINA IMAGEN POR ID
    @Transactional
    public void eliminarImagenId(String id) throws MiException {
        Optional<Imagen> respuesta = ir.findById(id);
        if (respuesta.isPresent()) {
            ir.deleteById(id);
        } else {
            throw new MiException("No se encontro la imagen" + (id));
        }
    }

    // VALIDA QUE EL ARCHIVO NO SEA NULO O ESTE VACIO
    public MultipartFile validar(MultipartFile archivo) {

        // if (archivo != null && archivo.getBytes().length == 0) {
        // return null;
        // } // este es mas correcto //

        return archivo;
    }

    // BUSCA UNA IMAGEN POR CONTENIDO, DEVUELVE UN ERROR SI NO FUNCIONA (NO PROBADA)
    public Imagen buscarPorContenido(MultipartFile archivo) throws MiException {
        try {
            byte[] contenido = archivo.getBytes();
            Imagen imagen = ir.buscarPorContenido(contenido);
            return imagen;
        } catch (IOException ex) {
            throw new MiException("Error al buscar imagen por contenido" + ex.getMessage());
        }
    }

    // Método validarExistencia
    public Boolean validarExistencia(MultipartFile archivo) throws MiException {
        MultipartFile archivoValidado = validar(archivo);
        if (archivoValidado != null) {
            Imagen imagen = buscarPorContenido(archivoValidado);
            return imagen != null;
        }
        return false;
    }
}
