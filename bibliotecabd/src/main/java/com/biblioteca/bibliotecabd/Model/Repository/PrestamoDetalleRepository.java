package com.biblioteca.bibliotecabd.Model.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biblioteca.bibliotecabd.Model.Entity.PrestamoDetalle;

public interface PrestamoDetalleRepository extends JpaRepository<PrestamoDetalle, Integer> {

    @Query("SELECT COUNT(d) FROM PrestamoDetalle d WHERE d.prestamo.usuario.idUsuario = :idUsuario AND d.estado = 'PRESTADO'")
    public int CantidadPrestados(@Param("idUsuario") int idUsuario);

    @Query("SELECT COUNT(d) FROM PrestamoDetalle d WHERE d.prestamo.idPrestamo = :idPrestamo AND d.estado = 'PRESTADO'")
    public int CantidadPrestadosPrestamo(@Param("idPrestamo") int idPrestamo);

    @Query("SELECT d FROM PrestamoDetalle d WHERE d.prestamo.usuario.idUsuario = :idUsuario " +
            "ORDER BY d.prestamo.fechaPrestamo DESC, d.idDetalle DESC")
    public List<PrestamoDetalle> DetallesUsuario(@Param("idUsuario") int idUsuario);

    @Query("SELECT d FROM PrestamoDetalle d WHERE d.prestamo.idPrestamo = :idPrestamo " +
            "ORDER BY d.idDetalle DESC")
    public List<PrestamoDetalle> DetallesPrestamo(@Param("idPrestamo") int idPrestamo);
}
