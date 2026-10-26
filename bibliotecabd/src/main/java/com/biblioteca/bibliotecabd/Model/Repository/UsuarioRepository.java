package com.biblioteca.bibliotecabd.Model.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.bibliotecabd.Model.Entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario,Integer>{

    public Optional<Usuario> findByUsername(String username);

}
