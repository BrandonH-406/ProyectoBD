package com.biblioteca.bibliotecabd.Model.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biblioteca.bibliotecabd.Model.Entity.Prestamo;

public interface PrestamoRepository extends JpaRepository<Prestamo, Integer> {

    @Query("SELECT DISTINCT p FROM Prestamo p " +
            "JOIN p.usuario u " +
            "LEFT JOIN Estudiante e ON e.idUsuario = u.id " +
            "LEFT JOIN Profesor prof ON prof.idUsuario = u.id " +
            "WHERE e.carne = :identificador OR prof.codigoEmpleado = :identificador")
    List<Prestamo> findPrestamosPorCarneOCodigoEmpleado(@Param("identificador") String identificador);

}
