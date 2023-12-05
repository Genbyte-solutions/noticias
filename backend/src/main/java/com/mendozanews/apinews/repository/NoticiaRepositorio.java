package com.mendozanews.apinews.repository;

import java.util.List;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mendozanews.apinews.model.entity.Noticia;

@Repository
public interface NoticiaRepositorio extends JpaRepository<Noticia, String> {

    @Query("SELECT n FROM Noticia n ORDER BY n.fechaPublicacion DESC")
    public List<Noticia> buscarNoticias(Pageable pageable); // admin

    @Query("SELECT n FROM Noticia n WHERE n.titulo LIKE %:titulo% ORDER BY n.fechaPublicacion DESC")
    public List<Noticia> buscarNoticiaPorTitulo(@Param("titulo") String titulo);

    @Query("SELECT n FROM Noticia n WHERE n.seccion.nombre = :seccion ORDER BY n.fechaPublicacion DESC")
    public List<Noticia> buscarNoticiasPorSeccion(@Param("seccion") String seccion, Pageable pageable);

    @Query("SELECT n FROM Noticia n WHERE n.autor.nombre = :nombre AND n.autor.apellido = :apellido ORDER BY n.fechaPublicacion DESC")
    public List<Noticia> buscarNoticiasPorAutor(@Param("nombre") String nombre, @Param("apellido") String apellido, Pageable pageable);

    @Query("SELECT n FROM Noticia n WHERE (n.seccion.seccionId = :seccion OR n.seccion.nombre = :seccion) AND n.fechaPublicacion >= CURRENT_DATE() -  14 ORDER BY n.vistas DESC")
    public List<Noticia> buscarNoticiasPopularesPorSeccion(@Param("seccion") String seccion, Pageable pageable);

    @Query("SELECT n FROM Noticia n WHERE n.fechaPublicacion >= CURRENT_DATE() - 14 ORDER BY n.vistas DESC")
    List<Noticia> buscarNoticiasMasPopulares(Pageable pageable); //home
}