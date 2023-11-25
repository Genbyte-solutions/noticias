package com.mendozanews.apinews.service.interfaces;

import com.mendozanews.apinews.model.entity.Portada;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IPortada {

    Portada guardarPortada(MultipartFile portada) throws IOException;

    Portada actualizarPortada(MultipartFile portada, String id) throws IOException;
}
