package com.mendozanews.apinews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mendozanews.apinews.model.entity.Autor;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor, String> {
    @Query("SELECT a FROM Autor a WHERE a.nombre = :nombre AND a.apellido = :apellido")
    Autor buscarPorNombreCompleto(@Param("nombre") String nombre, @Param("apellido") String apellido);

}
