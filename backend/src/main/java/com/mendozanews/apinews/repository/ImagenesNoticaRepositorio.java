package com.mendozanews.apinews.repository;

import com.mendozanews.apinews.model.entity.ImagenesNoticia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagenesNoticaRepositorio extends JpaRepository<ImagenesNoticia, String> {
}
