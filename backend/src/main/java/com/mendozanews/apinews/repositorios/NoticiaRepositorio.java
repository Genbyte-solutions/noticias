package com.mendozanews.apinews.repositorios;

import java.util.Date;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mendozanews.apinews.model.entidades.Noticia;

@Repository
public interface NoticiaRepositorio extends JpaRepository<Noticia, String> {

    @Query("SELECT n FROM Noticia n WHERE n.titulo LIKE :titulo")
    public List<Noticia> buscarPorTitulo(@Param("titulo") String titulo);

    @Query("SELECT n FROM Noticia n WHERE n.autor.id = :id")
    public List<Noticia> buscarPorAutor(@Param("id") String id);

    @Query("SELECT n FROM Noticia n WHERE n.seccion.id = :id")
    public List<Noticia> buscarPorSeccion(@Param("id") String id);

    @Query("SELECT n FROM Noticia n WHERE n.seccion.id = :id")
    public List<Noticia> findTop6BySeccionId(@Param("id") String id);

    @Query("SELECT n FROM Noticia n WHERE n.seccion.id = :id")
    public Noticia findFirstBySeccionId(@Param("id") String id);

    @Query("SELECT n FROM Noticia n WHERE n.id IN ('1', '2', '3')")
    List<Noticia> listar3principales();

    @Query("SELECT n FROM Noticia n WHERE n.fechaPublicacion BETWEEN :fechaInicio AND :fechaFin ORDER BY n.fechaPublicacion DESC")
    List<Noticia> findUltimasNoticias48Horas(@Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);
}