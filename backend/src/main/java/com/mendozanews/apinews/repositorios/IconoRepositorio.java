package com.mendozanews.apinews.repositorios;

import com.mendozanews.apinews.model.entidades.IconoSeccion;
import com.mendozanews.apinews.model.entidades.Seccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IconoRepositorio extends JpaRepository<IconoSeccion, String> {

}
