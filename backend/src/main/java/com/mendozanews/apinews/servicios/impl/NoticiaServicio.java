package com.mendozanews.apinews.servicios;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mendozanews.apinews.excepciones.MiException;
import com.mendozanews.apinews.model.dto.NoticiaResponseDto;
import com.mendozanews.apinews.model.entidades.Autor;
import com.mendozanews.apinews.model.entidades.Imagen;
import com.mendozanews.apinews.model.entidades.Noticia;
import com.mendozanews.apinews.model.entidades.Portada;
import com.mendozanews.apinews.model.entidades.Seccion;
import com.mendozanews.apinews.repositorios.AutorRepositorio;
import com.mendozanews.apinews.repositorios.NoticiaRepositorio;
import com.mendozanews.apinews.repositorios.PortadaRepositorio;
import com.mendozanews.apinews.repositorios.SeccionRepositorio;

import com.mendozanews.apinews.repositorios.ImagenRepositorio;

@Service
public class NoticiaServicio {

    @Autowired
    private PortadaRepositorio portadaRepositorio;

    @Autowired
    private ImagenRepositorio imagenRepositorio;

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Autowired
    private SeccionRepositorio seccionRepositorio;

    @Autowired
    private NoticiaRepositorio noticiaRepositorio;

    @Transactional
    public Noticia cargarNoticia(MultipartFile[] imagenes, MultipartFile portada, String titulo, String subtitulo,
                                 List<String> parrafos, List<String> etiquetas, Seccion seccion, Autor autor) {
        try {
            validar(titulo, subtitulo, seccion.getId(), autor.getId());

            Autor autorEncontrado = autorRepositorio.findById(autor.getId())
                    .orElseThrow(() -> new MiException("Autor no encontrado"));
            Seccion seccionEncontrada = seccionRepositorio.findById(seccion.getId())
                    .orElseThrow(() -> new MiException("Sección no encontrada"));

            Noticia noticia = new Noticia();
            noticia.setTitulo(titulo);
            noticia.setSubtitulo(subtitulo);
            noticia.setParrafos(parrafos);
            noticia.setEtiquetas(etiquetas);
            noticia.setAutor(autorEncontrado);
            noticia.setSeccion(seccionEncontrada);
            noticia.setFechaPublicacion(new Date()); 
            noticia.setActiva(true);

            if (portada != null) {
                Portada portadaGuardada = guardarPortadaYDevolverNoticia(portada, noticia);
                noticia.setPortada(portadaGuardada);
            }

            Noticia noticiaGuardada = noticiaRepositorio.save(noticia);

            for (MultipartFile imagen : imagenes) {
                if (imagen != null) {
                    Imagen imagenGuardada = guardarImagenYDevolverNoticia(imagen, noticiaGuardada);
                    if (noticiaGuardada.getImagenes() == null) {
                        noticiaGuardada.setImagenes(new ArrayList<>());
                    }
                    noticiaGuardada.getImagenes().add(imagenGuardada);
                }
            }

            noticiaGuardada = noticiaRepositorio.save(noticiaGuardada);

            return noticiaGuardada;
        } catch (MiException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MiException("Error al cargar la noticia: " + e.getMessage());
        }
    }


    private Portada guardarPortadaYDevolverNoticia(MultipartFile portada, Noticia noticia) {
        Portada portadaGuardada = new Portada();
        try {
            byte[] portadaBytes = portada.getBytes();
            portadaGuardada.setImagen(portadaBytes);
            portadaGuardada.setNombre(portada.getOriginalFilename());
            portadaGuardada.setMime(portada.getContentType());
            portadaGuardada.setNoticia(noticia);
            portadaRepositorio.save(portadaGuardada);
        } catch (IOException e) {
            e.printStackTrace();
            throw new MiException("Error al guardar la portada: " + e.getMessage());
        }
        return portadaGuardada;
    }

    @Transactional
    public void modificarNoticia(String titulo, String subtitulo, List<String> parrafos, List<String> etiquetas,
            String idSeccion, String idAutor, MultipartFile portada, String idNoticia) throws MiException {
        validar(titulo, subtitulo, idSeccion, idAutor);

        Optional<Noticia> respuesta = noticiaRepositorio.findById(idNoticia);
        Optional<Autor> respuestaAutor = autorRepositorio.findById(idAutor);
        Optional<Seccion> respuestaSeccion = seccionRepositorio.findById(idSeccion);

        Autor autor = respuestaAutor.orElseThrow(() -> new MiException("Autor no encontrado"));
        Seccion seccion = respuestaSeccion.orElseThrow(() -> new MiException("Sección no encontrada"));

        Noticia noticia = respuesta.orElseThrow(() -> new MiException("Noticia no encontrada"));
        validarTituloNuevo(noticia.getTitulo(), titulo);

        noticia.setTitulo(titulo);
        noticia.setSubtitulo(subtitulo);
        noticia.setParrafos(parrafos);
        noticia.setEtiquetas(etiquetas);
        noticia.setSeccion(seccion);
        noticia.setAutor(autor);
        noticia.setFechaEdicion(new Date());

    }

    private Imagen guardarImagenYDevolverNoticia(MultipartFile imagen, Noticia noticia) {
        Imagen imagenGuardada = new Imagen();
        try {
            imagenGuardada.setNombre(imagen.getOriginalFilename());
            imagenGuardada.setContenido(imagen.getBytes());
            imagenGuardada.setMime(imagen.getContentType());
            imagenGuardada = imagenRepositorio.save(imagenGuardada);
            imagenGuardada.setNoticiaId(noticia); 
            // el objeto con el ID generado
        } catch (IOException e) {
            e.printStackTrace();
            throw new MiException("Error al guardar la imagen: " + e.getMessage());
        }
        return imagenGuardada;
    }

    // public Noticia obtenerNoticiaPorPortadaId(String id) {
    //     try {
    //         return noticiaRepositorio.findById(id).orElse(null);
    //     } catch (Exception e) {
    //         throw new MiException("Error al obtener la noticia por el ID de la portada: " + e.getMessage());
    //     }
    // }

    // LISTA TODAS LAS NOTICIAS
    public List<Noticia> listarNoticias() {
        List<Noticia> noticias = noticiaRepositorio.findAll();
        return noticias;
    }

    // BUSCA UNA o MAS NOTICIAS QUE CONTENGAN EL STRING QUE SE LE PASA
    public List<Noticia> buscarPorTitulo(String titulo) {
        List<Noticia> noticias = noticiaRepositorio.buscarPorTitulo(titulo);
        return noticias;
    }

    // BUSCA NOTICIAS POR AUTOR
    public List<Noticia> buscarPorAutor(String idAutor) {
        List<Noticia> noticias = noticiaRepositorio.buscarPorAutor(idAutor);
        return noticias;
    }

    // BUSCA NOTICIAS POR SECCION
    public List<Noticia> buscarPorSeccion(String idSeccion) {
        List<Noticia> noticias = noticiaRepositorio.buscarPorSeccion(idSeccion);
        return noticias;
    }

    // BUSCA 6 NOTICIAS POR SECCION
    public List<Noticia> buscar6PorSeccion(String idSeccion) {
        List<Noticia> noticias = noticiaRepositorio.findTop6BySeccionId(idSeccion);
        return noticias;
    }

    // BUSCA 1 NOTICIA DE CADA SECCION
    public List<Noticia> unaPorSeccion() {
        List<Seccion> secciones = (List<Seccion>) seccionRepositorio.findAll();
        List<Noticia> noticias = new ArrayList<>();

        for (Seccion seccion : secciones) {
            Noticia noticia = noticiaRepositorio.findFirstBySeccionId(seccion.getId());
            if (noticia != null) {
                noticias.add(noticia);
            }
        }
        return noticias;
    }

    // OBTIENE 1 NOTICIA POR ID
    public Noticia getOne(String id) {
        return noticiaRepositorio.getReferenceById(id);
    }

    // ELIMINA NOTICIA POR ID
    @Transactional
    public void eliminarNoticiaId(String id) throws MiException {
        Optional<Noticia> respuesta = noticiaRepositorio.findById(id);
        if (respuesta.isPresent()) {
            noticiaRepositorio.deleteById(id);
        } else {
            throw new MiException("No se encontro la noticia");
        }
    }

    // COPIA UNA NOTICIA Y LE ASIGNA EL ID NUEVO
    @Transactional
    public void asignarID(String id, String idNuevo) throws MiException {
        Noticia principal = getOne(id);
        principal.setId(idNuevo);
        noticiaRepositorio.save(principal);
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

    // VALIDA LOS ATRIBUTOS STRINGS DE UNA NOTICIA
    private void validar(String titulo, String subtitulo, String idSeccion, String idAutor) throws MiException {
        if (titulo == null || titulo.isEmpty()) {
            throw new MiException("El título no puede ser nulo o estar vacío");
        }
        if (subtitulo == null || subtitulo.isEmpty() || subtitulo.length() > 220) {
            throw new MiException("El subtítulo no puede ser nulo, estar vacío o tener más de 220 caracteres");
        }
        if (idAutor == null || idAutor.isEmpty()) {
            throw new MiException("Debe indicar un autor");
        }
        if (idSeccion == null || idSeccion.isEmpty()) {
            throw new MiException("Debe indicar la sección");
        }
    }

    // VALIDA QUE EL TITULO NO EXISTA YA
    private void validarTituloNuevo(String titulo, String tituloNuevo) throws MiException {
        if (!titulo.equalsIgnoreCase(tituloNuevo) && noticiaRepositorio.buscarPorTitulo(tituloNuevo) != null) {
            throw new MiException("Ya existe una noticia con ese título");
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
                autor.setId("autor_" + i);
                autor = autorRepositorio.save(autor);
    
                Seccion seccion = new Seccion();
                seccion.setId("seccion_" + i);
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

    @Transactional
    public void actualizarNoticia(Noticia noticia) {
        try {
            Optional<Noticia> noticiaExistente = noticiaRepositorio.findById(noticia.getId());
            if (noticiaExistente.isPresent()) {
                Noticia noticiaActualizada = noticiaExistente.get();
                noticiaActualizada.setTitulo(noticia.getTitulo());
                noticiaActualizada.setSubtitulo(noticia.getSubtitulo());
                noticiaActualizada.setParrafos(noticia.getParrafos());
                noticiaActualizada.setEtiquetas(noticia.getEtiquetas());
                noticiaActualizada.setSeccion(noticia.getSeccion());
                noticiaActualizada.setAutor(noticia.getAutor());
                noticiaActualizada.setFechaEdicion(new Date());

                noticiaRepositorio.save(noticiaActualizada);
            } else {
                throw new MiException("La noticia no existe en la base de datos");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Noticia guardarPortada(MultipartFile portada) {
        return null;
    }

    public void guardarLista(MultipartFile imagen) {
    }

    public void setTipo(String tipo) {
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
    }
    

    public List<NoticiaResponseDto> listarNoticiasRecientesDto(int cantidad, int horas) {
        Calendar calendar = Calendar.getInstance();
        Date fechaFin = calendar.getTime();
        calendar.add(Calendar.HOUR, -horas);
        Date fechaInicio = calendar.getTime();
    
        List<Noticia> noticiasRecientes = noticiaRepositorio.findUltimasNoticias48Horas(fechaInicio, fechaFin);
        // Si quieres limitar la cantidad de noticias, puedes hacerlo manualmente aquí
        if (noticiasRecientes.size() > cantidad) {
            noticiasRecientes = noticiasRecientes.subList(0, cantidad);
        }
        return convertirAListaDto(noticiasRecientes);
    }

    private List<NoticiaResponseDto> convertirAListaDto(List<Noticia> noticias) {
        return noticias.stream()
            .map(this::convertirADto)
            .collect(Collectors.toList());
    }
    
    private NoticiaResponseDto convertirADto(Noticia noticia) {
        NoticiaResponseDto dto = new NoticiaResponseDto();
        dto.setTitulo(noticia.getTitulo());
        dto.setSeccion(noticia.getSeccion()); // Utiliza getSeccionName en lugar de getSeccion().getNombre()
        dto.setId(noticia.getId());
        dto.setFechaPublicacion(noticia.getFechaPublicacion());
        dto.setAutor(noticia.getAutor());
        // Añade otros campos según tu requisito
        return dto;
    }


    public Noticia cargarNoticia(MultipartFile[] imagenes, MultipartFile portada, String titulo, String subtitulo,
            List<String> parrafos, List<String> etiquetas, Seccion seccion, String nombre) {
        return null;
    }

}



