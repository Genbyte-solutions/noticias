package com.mendozanews.apinews.service.interfaces;

import com.mendozanews.apinews.model.dto.request.SeccionDto;
import com.mendozanews.apinews.model.entity.IconoSeccion;
import com.mendozanews.apinews.model.entity.Seccion;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ISeccion {

    Seccion crearSeccion(SeccionDto seccionDto, MultipartFile icono) throws IOException;

    Seccion editarSeccion(Seccion seccion, SeccionDto seccionDto, MultipartFile icono) throws IOException;

    Seccion buscarSeccionPorId(String id);

    Seccion buscarSeccionPorNombre(String nombre);

    List<Seccion> listarSecciones();

    void eliminarSeccionPorId(String seccionId);

    IconoSeccion buscarIconoPorSeccionId(String iconoId);
}
