package com.mendozanews.apinews.servicios.impl;

import java.io.IOException;
import java.util.List;

import com.mendozanews.apinews.model.dto.request.NoticiaDto;
import com.mendozanews.apinews.model.entidades.*;
import com.mendozanews.apinews.model.enums.Orden;
import com.mendozanews.apinews.repositorios.*;
import com.mendozanews.apinews.servicios.interfaces.INoticia;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class NoticiaServicio implements INoticia {
    private final PortadaRepositorio portadaRepo;
    private final ImagenesNoticaRepositorio imagenesNoticiaRepo;
    private final ImagenRepositorio imagenRepo;
    private final AutorRepositorio autorRepo;
    private final SeccionRepositorio seccionRepo;
    private final NoticiaRepositorio noticiaRepo;

    public NoticiaServicio(PortadaRepositorio portadaRepo, ImagenesNoticaRepositorio imagenesNoticiaRepo,
                           ImagenRepositorio imagenRepo, AutorRepositorio autorRepo,
                           SeccionRepositorio seccionRepo, NoticiaRepositorio noticiaRepo) {
        this.portadaRepo = portadaRepo;
        this.imagenesNoticiaRepo = imagenesNoticiaRepo;
        this.imagenRepo = imagenRepo;
        this.autorRepo = autorRepo;
        this.seccionRepo = seccionRepo;
        this.noticiaRepo = noticiaRepo;
    }

    @Transactional
    public Portada guardarPortada(MultipartFile portada) throws IOException {
        return portadaRepo.save(
                Portada.builder()
                        .tipoMime(portada.getContentType())
                        .nombreArchivo(portada.getOriginalFilename())
                        .imagen(portada.getBytes())
                        .build()
        );
    }

    @Transactional
    public void guardarImagenesNoticia(List<MultipartFile> imagenes, Noticia noticia) throws IOException {
        for (MultipartFile imagen : imagenes) {
            imagenesNoticiaRepo.save(
                    ImagenesNoticia.builder()
                            .tipoMime(imagen.getContentType())
                            .nombreArchivo(imagen.getOriginalFilename())
                            .contenido(imagen.getBytes())
                            .noticia(noticia)
                            .build()
            );
        }
    }

    @Transactional
    @Override
    public String crearNoticia(NoticiaDto noticiaDto, Autor autor, Seccion seccion,
                               List<MultipartFile> imagenes, MultipartFile portada) throws IOException {

        Portada portadaGuardada = guardarPortada(portada);
        Noticia noticiaGuardada = noticiaRepo.save(Noticia.builder()
                .titulo(noticiaDto.getTitulo())
                .subtitulo(noticiaDto.getSubtitulo())
                .parrafos(noticiaDto.getParrafos())
                .etiquetas(noticiaDto.getEtiquetas())
                .portada(portadaGuardada)
                .seccion(seccion)
                .autor(autor)
                .build());

        guardarImagenesNoticia(imagenes, noticiaGuardada);

        return noticiaGuardada.getNoticiaId();
    }

    // BUSCA NOTICIA POR ID
    @Transactional(readOnly = true)
    @Override
    public Noticia buscarNoticiaPorId(String noticiaId) {
        return noticiaRepo.findById(noticiaId).orElse(null);
    }

    // ELIMINA NOTICIA POR ID
    @Transactional
    @Override
    public void eliminarNoticiaPorId(String noticiaId) {
        noticiaRepo.deleteById(noticiaId);
    }

    // LISTA TODAS LAS NOTICIAS
    @Transactional(readOnly = true)
    @Override
    public List<Noticia> listarNoticias(Integer offset, Integer limit, Orden orden) {
        return noticiaRepo.buscarRecientes(PageRequest.of(offset, limit));
    }

    // BUSCA NOTICIAS EN LAS QUE EL TITULO CONTENGA EL ARGUMENTO
    @Transactional(readOnly = true)
    @Override
    public List<Noticia> buscarPorTitulo(String titulo) {
        return noticiaRepo.buscarPorTitulo(titulo);
    }

    // BUSCA NOTICIAS POR SECCION
    @Transactional(readOnly = true)
    @Override
    public List<Noticia> buscarPorSeccion(String seccion, Integer offset, Integer limit, Orden orden) {

        return noticiaRepo.buscarPorSeccion(seccion, PageRequest.of(offset, limit));
    }

    // BUSCA NOTICIAS POR AUTOR
    @Transactional(readOnly = true)
    @Override
    public List<Noticia> buscarPorAutor(String nombre, String apellido, Integer offset, Integer limit, Orden orden) {

        return noticiaRepo.buscarPorAutor(nombre, apellido, PageRequest.of(offset, limit));
    }

/*    // BUSCA 6 NOTICIAS POR SECCION
    public List<Noticia> buscar6PorSeccion(String idSeccion) {
        List<Noticia> noticias = noticiaRepositorio.findTop6BySeccionId(idSeccion);
        return noticias;
    }

    // LISTA LAS 3 NOTICIAS PRINCIPALES
    public List<Noticia> listarPrincipales() throws MiException {
        try {
            List<Noticia> noticias = noticiaRepositorio.listar3principales();
            return noticias;
        } catch (Exception e) {
            throw new MiException("Error al listar las 3 noticias principales");
        }
    }

    // PRECARGA NOTICIAS SI LA BASE DE DATOS ESTA VACIA
    @Transactional
    public String iniciarPreloads(Integer cantidad) throws MiException {
        if (cantidad <= 0) {
            throw new MiException("La cantidad debe ser un número positivo mayor que cero");
        }

        long cantidadNoticias = noticiaRepositorio.count();
        if (cantidadNoticias < cantidad) {
            for (int i = 1; i <= cantidad; i++) {
                Noticia noticia = new Noticia();

                Autor autor = new Autor();
                autor.setNombre("Autor");
                autor.setApellido(Integer.toString(i));
                autor.setAutorId("autor_" + i);
                autor = autorRepositorio.save(autor);

                Seccion seccion = new Seccion();
                seccion.setSeccionId("seccion_" + i);
                seccion.setNombre("Sección " + i);
                seccion = seccionRepositorio.save(seccion);

                noticia.setTitulo("titulo " + i);
                noticia.setSubtitulo("subtitulo " + i);

                List<String> parrafos = new ArrayList<>();
                parrafos.add("parrafo 1 de noticia " + i);
                parrafos.add("parrafo 2 de noticia " + i);
                noticia.setParrafos(parrafos);

                List<String> etiquetas = new ArrayList<>();
                etiquetas.add("etiquetas 1 N" + i);
                etiquetas.add("etiquetas 2 N" + i);
                noticia.setEtiquetas(etiquetas);

                noticia.setSeccion(seccion);
                noticia.setAutor(autor);
                noticia.setFechaPublicacion(new Date()); // Utiliza LocalDateTime.now() para la fecha actual
                noticia.setActiva(true);

                noticiaRepositorio.save(noticia);
            }
            return "Se cargó la lista porque ingresaste un número de noticias mayor al existente en la base de datos";
        } else {
            return "Error al cargar: Ya hay más noticias guardadas de las que deseas";
        }
    }

    public Noticia obtenerNoticiaPorPortadaId(String id) {
        Optional<Portada> portada = portadaRepositorio.findById(id);
        if (portada.isPresent()) {
            Portada portadaEncontrada = portada.get();
            // Accede al campo noticia en la entidad Portada
            Noticia noticiaRelacionada = portadaEncontrada.getNoticia();
            return noticiaRelacionada;
        } else {
            // Manejo en caso de que no se encuentre la portada con el ID especificado
            return null;
        }
    }*/
}