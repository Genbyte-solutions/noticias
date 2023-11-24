package com.mendozanews.apinews.servicios.interfaces;

import com.mendozanews.apinews.model.entidades.Portada;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IPortada {

    Portada guardarPortada(MultipartFile portada) throws IOException;

    Portada actualizarPortada(MultipartFile portada, String id) throws IOException;
}
