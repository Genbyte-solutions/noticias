package com.mendozanews.apinews.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mendozanews.apinews.model.entidades.Autor;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor, String> {

}
