package com.biblioteca.bibliotecabd.Model.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.biblioteca.bibliotecabd.Model.Entity.Estudiante;

public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {

    @Query("SELECT u FROM Estudiante u JOIN FETCH u.usuario")
    List<Estudiante> findAllConUsuario();

}
