package com.mendozanews.apinews.repositorios;

import com.mendozanews.apinews.model.entidades.IconoSeccion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IconoRepositorio extends JpaRepository<IconoSeccion, String> {
}
