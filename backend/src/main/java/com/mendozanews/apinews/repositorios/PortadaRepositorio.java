package com.mendozanews.apinews.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mendozanews.apinews.model.entidades.Portada;

public interface PortadaRepositorio extends JpaRepository<Portada, String> {
    @Query("SELECT p FROM Portada p WHERE p.imagen = :contenido")
    public Portada buscarPorContenido(@Param("contenido") byte[] contenido);
}