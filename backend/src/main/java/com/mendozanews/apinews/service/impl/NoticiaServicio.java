package com.mendozanews.apinews.service.impl;

import java.io.IOException;
import java.util.List;

import com.mendozanews.apinews.model.dto.request.NoticiaDto;
import com.mendozanews.apinews.model.entity.*;
import com.mendozanews.apinews.repository.*;
import com.mendozanews.apinews.service.interfaces.INoticia;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class NoticiaServicio implements INoticia {
    private final PortadaRepositorio portadaRepo;

    private final PortadaServicio portadaServicio;
    private final ImagenesNoticaRepositorio imagenesNoticiaRepo;
    private final NoticiaRepositorio noticiaRepo;

    public NoticiaServicio(PortadaRepositorio portadaRepo, ImagenesNoticaRepositorio imagenesNoticiaRepo,
                           NoticiaRepositorio noticiaRepo, PortadaServicio portadaServicio) {
        this.portadaRepo = portadaRepo;
        this.imagenesNoticiaRepo = imagenesNoticiaRepo;
        this.noticiaRepo = noticiaRepo;
        this.portadaServicio = portadaServicio;
    }

    @Transactional
    public Portada guardarPortada(MultipartFile portada) throws IOException {
        return portadaRepo.save(
                Portada.builder()
                        .tipoMime(portada.getContentType())
                        .nombreArchivo(portada.getOriginalFilename())
                        .contenido(portada.getBytes())
                        .build());
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
                            .build());
        }
    }

    @Transactional
    @Override
    public Noticia crearNoticia(NoticiaDto noticiaDto, Autor autor, Seccion seccion,
                                List<MultipartFile> imagenes, MultipartFile portada) throws IOException {

        Portada portadaGuardada = null;
        if (portada != null) portadaGuardada = guardarPortada(portada);

        Noticia noticiaGuardada = noticiaRepo.save(Noticia.builder()
                .titulo(noticiaDto.getTitulo())
                .subtitulo(noticiaDto.getSubtitulo())
                .parrafos(noticiaDto.getParrafos())
                .etiquetas(noticiaDto.getEtiquetas())
                .vistas(0)
                .portada(portadaGuardada)
                .seccion(seccion)
                .autor(autor)
                .build());

        if (imagenes != null) guardarImagenesNoticia(imagenes, noticiaGuardada);

        return noticiaGuardada;
    }

    @Transactional
    @Override
    public Noticia actualizarNoticia(Noticia noticia, NoticiaDto noticiaDto, Autor autor, Seccion seccion,
                                     List<MultipartFile> imagenes, MultipartFile portada) throws IOException {
        Portada portadaActualizada = portadaServicio.actualizarPortada(portada, noticia.getPortada().getPortadaId());
        noticia.setTitulo(noticiaDto.getTitulo());
        noticia.setSubtitulo(noticiaDto.getSubtitulo());
        noticia.setParrafos(noticiaDto.getParrafos());
        noticia.setEtiquetas(noticiaDto.getEtiquetas());
        if (portada != null) noticia.setPortada(portadaActualizada);
        noticia.setSeccion(seccion);
        noticia.setAutor(autor);
        if (imagenes != null) {
            eliminarImagenesNoticia(noticia.getImagenesNoticia());
            guardarImagenesNoticia(imagenes, noticia);
        }


        return noticiaRepo.save(noticia);
    }


    @Transactional
    public void eliminarImagenesNoticia(List<ImagenesNoticia> imagenesNoticia) {
        for (ImagenesNoticia imagen : imagenesNoticia) {
            imagenesNoticiaRepo.delete(imagen);
        }
    }

    // BUSCA NOTICIA POR ID
    @Transactional
    @Override
    public Noticia buscarNoticiaPorId(String noticiaId) {

        Noticia noticia = noticiaRepo.findById(noticiaId).orElse(null);
        if (noticia != null) {
            noticia.setVistas(noticia.getVistas() + 1);
            noticia = noticiaRepo.save(noticia);
        }

        return noticia;
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
    public List<Noticia> buscarNoticias(Integer offset, Integer limit) {
        return noticiaRepo.buscarNoticias(PageRequest.of(offset, limit));
    }

    // BUSCA NOTICIAS EN LAS QUE EL TITULO CONTENGA EL ARGUMENTO
    @Transactional(readOnly = true)
    @Override
    public List<Noticia> buscarNoticiaPorTitulo(String titulo) {
        return noticiaRepo.buscarNoticiaPorTitulo(titulo);
    }

    // BUSCA NOTICIAS POR SECCION
    @Transactional(readOnly = true)
    @Override
    public List<Noticia> buscarNoticiasPorSeccion(String seccion, Integer offset, Integer limit) {
        return noticiaRepo.buscarNoticiasPorSeccion(seccion, PageRequest.of(offset, limit));
    }

    // BUSCA NOTICIAS POR AUTOR
    @Transactional(readOnly = true)
    @Override
    public List<Noticia> buscarNoticiasPorAutor(String nombre, String apellido, Integer offset, Integer limit) {
        return noticiaRepo.buscarNoticiasPorAutor(nombre, apellido, PageRequest.of(offset, limit));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Noticia> buscarPopularesPorSeccion(String seccion, Integer offset, Integer limit) {
        return noticiaRepo.buscarNoticiasPopularesPorSeccion(seccion, PageRequest.of(offset, limit));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Noticia> buscarNoticiasMasPopulares(Integer offset, Integer limit) {
        return noticiaRepo.buscarNoticiasMasPopulares(PageRequest.of(offset, limit));
    }

    @Transactional
    @Override
    public Portada buscarPortadaPorId(String portadaId) {
        return portadaRepo.findById(portadaId).orElse(null);
    }

    @Transactional
    @Override
    public ImagenesNoticia buscarImagenNoticiaPorId(String imagenNoticiaId) {
        return imagenesNoticiaRepo.findById(imagenNoticiaId).orElse(null);
    }

    @Transactional
    public Portada actualizarPortada(MultipartFile portada, String portadaId) throws IOException {
        return (portada != null) ? portadaServicio.actualizarPortada(portada, portadaId) : null;
    }
}