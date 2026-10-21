package com.biblioteca.bibliotecabd.Model.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biblioteca.bibliotecabd.Model.Entity.Ejemplar;

public interface EjemeplarRepository extends JpaRepository<Ejemplar,Integer>{

    @Query("SELECT COUNT(e) FROM Ejemplar e WHERE e.libro.id = :idLibro AND e.estado = :estado")
    public int CantidadEjemplares(@Param ("idLibro") int idLibro, @Param ("estado") String estado);

}
