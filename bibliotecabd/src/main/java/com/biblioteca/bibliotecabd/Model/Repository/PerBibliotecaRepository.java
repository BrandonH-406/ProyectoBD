package com.biblioteca.bibliotecabd.Model.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.biblioteca.bibliotecabd.Model.Entity.PerBiblioteca;

public interface PerBibliotecaRepository extends JpaRepository<PerBiblioteca, Integer> {
    Optional<PerBiblioteca> findByCodigoEmpleado(String codigoEmpleado);

    @Query("SELECT p FROM PerBiblioteca p JOIN FETCH p.usuario")
    List<PerBiblioteca> findAllConUsuario();
}
