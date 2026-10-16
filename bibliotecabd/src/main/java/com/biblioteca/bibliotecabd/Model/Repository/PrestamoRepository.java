package com.biblioteca.bibliotecabd.Model.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.bibliotecabd.Model.Entity.Prestamo;

public interface PrestamoRepository extends JpaRepository<Prestamo,Integer>{

}
