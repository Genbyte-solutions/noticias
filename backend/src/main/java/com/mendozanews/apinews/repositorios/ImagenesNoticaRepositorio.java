package com.mendozanews.apinews.repositorios;

import com.mendozanews.apinews.model.entidades.ImagenesNoticia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagenesNoticaRepositorio extends JpaRepository<ImagenesNoticia, String> {
}
