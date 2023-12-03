package com.mendozanews.apinews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mendozanews.apinews.model.entity.Portada;
import org.springframework.stereotype.Repository;

@Repository
public interface PortadaRepositorio extends JpaRepository<Portada, String> {
    @Query("SELECT p FROM Portada p WHERE p.contenido = :contenido")
    public Portada buscarPorContenido(@Param("contenido") byte[] contenido);
}