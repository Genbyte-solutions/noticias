package com.mendozanews.apinews.servicios.impl;

import com.mendozanews.apinews.exception.MiException;
import com.mendozanews.apinews.model.entidades.Imagen;
import com.mendozanews.apinews.repositorios.ImagenRepositorio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mendozanews.apinews.servicios.interfaces.IImagen;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImagenServicio implements IImagen {

    private final ImagenRepositorio imagenRepo;

    public ImagenServicio(ImagenRepositorio imagenRepo) {
        this.imagenRepo = imagenRepo;
    }

    // GUARDA UNA IMAGEN (AUTOR O USUARIO)
    @Transactional
    public Imagen guardarImagen(MultipartFile foto) throws IOException {
        return imagenRepo.save(
                Imagen.builder()
                        .tipoMime(foto.getContentType())
                        .nombre(foto.getOriginalFilename())
                        .contenido(foto.getBytes())
                        .build()
        );
    }

    // ACTUALIZA UNA IMAGEN
    @Transactional
    public Imagen actualizar(MultipartFile archivo, String id) throws MiException {

        archivo = validar(archivo);

        if (archivo != null) {

            try {

                Imagen imagen = new Imagen();

                if (id != null) {

                    Optional<Imagen> respuesta = imagenRepo.findById(id);

                    if (respuesta.isPresent()) {
                        imagen = respuesta.get();
                    }
                }

                imagen.setTipoMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());

                return imagenRepo.save(imagen);

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

                    imagen.setTipoMime(archivo.getContentType());
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

    // CONVERTIR IMAGEN A FORMATO ACEPTADO POR EL FRONT
    public HttpHeaders buildImageResponseHeaders(Imagen imagen) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(imagen.getTipoMime()));
        headers.setContentLength(imagen.getContenido().length);
        headers.set("Content-Disposition", "inline; filename=" + imagen.getNombre());
        return headers;
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
