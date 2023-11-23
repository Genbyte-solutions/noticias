package com.mendozanews.apinews.servicios.interfaces;

import com.mendozanews.apinews.model.dto.request.SeccionDto;
import com.mendozanews.apinews.model.entidades.IconoSeccion;
import com.mendozanews.apinews.model.entidades.Seccion;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ISeccion {

    void crearSeccion(SeccionDto seccionDto, MultipartFile icono) throws IOException;

    void editarSeccion(String seccionId, SeccionDto seccionDto, MultipartFile icono) throws IOException;

    Seccion buscarSeccion(String dato);

    Seccion buscarSeccionPorId(String id);

    List<Seccion> listarSecciones();

    void eliminarSeccionPorId(String seccionId);

    IconoSeccion buscarIconoPorSeccionId(String iconoId);
}
