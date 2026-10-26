package com.biblioteca.bibliotecabd.Model.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biblioteca.bibliotecabd.Model.Entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario,Integer>{

    public Optional<Usuario> findByUsername(String username);

    @Query("SELECT u FROM Usuario u WHERE u.idUsuario IN (SELECT e.idUsuario FROM Estudiante e WHERE e.carne = :codigo) " +
            "OR u.idUsuario IN (SELECT p.idUsuario FROM Profesor p WHERE p.codigoEmpleado = :codigo)")
    public Optional<Usuario> findByCarneOCodigoEmpleado(@Param("codigo") String codigo);

}
