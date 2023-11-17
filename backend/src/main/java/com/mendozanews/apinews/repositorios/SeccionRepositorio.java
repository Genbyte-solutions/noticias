package com.mendozanews.apinews.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mendozanews.apinews.model.entidades.Seccion;

@Repository
public interface SeccionRepositorio extends JpaRepository<Seccion, String> {

    @Query("SELECT s FROM Seccion s WHERE s.seccionId = :dato OR s.nombre = :dato OR s.codigo = :dato")
    public Seccion buscarSeccion(@Param("dato") String dato);

}
