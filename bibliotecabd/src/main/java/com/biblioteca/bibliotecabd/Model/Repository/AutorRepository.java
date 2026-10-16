package com.biblioteca.bibliotecabd.Model.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.bibliotecabd.Model.Entity.Autor;

public interface AutorRepository extends JpaRepository<Autor,Integer>{

}
