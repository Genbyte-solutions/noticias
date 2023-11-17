package com.mendozanews.apinews.repositorios;

import com.mendozanews.apinews.model.entidades.IconoSeccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IconoRepositorio extends JpaRepository<IconoSeccion, String> {
}
