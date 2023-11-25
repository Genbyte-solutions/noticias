package com.mendozanews.apinews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mendozanews.apinews.model.entity.Seccion;

@Repository
public interface SeccionRepositorio extends JpaRepository<Seccion, String> {

    @Query("SELECT s FROM Seccion s WHERE s.nombre = :nombre")
    public Seccion buscarSeccionPorNombre(@Param("nombre") String nombre);

}
