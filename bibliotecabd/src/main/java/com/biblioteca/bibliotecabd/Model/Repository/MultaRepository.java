package com.biblioteca.bibliotecabd.Model.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biblioteca.bibliotecabd.Model.Entity.Multa;

public interface MultaRepository extends JpaRepository<Multa,Integer>{

    @Query("SELECT COUNT(m) FROM Multa m WHERE m.usuario.idUsuario = :idUsuario AND m.estado = 'PENDIENTE'")
    public int CantidadPendientes(@Param("idUsuario") int idUsuario);

}
