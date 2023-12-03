package com.mendozanews.apinews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mendozanews.apinews.model.entity.Imagen;

@Repository
public interface ImagenRepositorio extends JpaRepository<Imagen, String> {

}
