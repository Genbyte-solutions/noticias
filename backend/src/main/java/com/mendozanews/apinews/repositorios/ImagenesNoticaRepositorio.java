package com.mendozanews.apinews.repositorios;

import com.mendozanews.apinews.model.entidades.ImagenesNoticia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagenesNoticaRepositorio extends JpaRepository<ImagenesNoticia, String> {
}
