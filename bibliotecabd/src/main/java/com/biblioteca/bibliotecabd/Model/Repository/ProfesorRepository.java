package com.biblioteca.bibliotecabd.Model.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.biblioteca.bibliotecabd.Model.Entity.Profesor;

public interface ProfesorRepository extends JpaRepository<Profesor, Integer> {

    @Query("SELECT p FROM Profesor p JOIN FETCH p.usuario")
    List<Profesor> findAllConUsuario();

}
