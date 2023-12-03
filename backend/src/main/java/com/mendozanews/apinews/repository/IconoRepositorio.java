package com.mendozanews.apinews.repository;

import com.mendozanews.apinews.model.entity.IconoSeccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IconoRepositorio extends JpaRepository<IconoSeccion, String> {

}
