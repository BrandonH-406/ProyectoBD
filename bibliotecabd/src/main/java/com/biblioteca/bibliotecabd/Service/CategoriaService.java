package com.biblioteca.bibliotecabd.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.biblioteca.bibliotecabd.Model.Entity.Categoria;
import com.biblioteca.bibliotecabd.Model.Repository.CategoriaRepository;

@Service 
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository){
        this.categoriaRepository = categoriaRepository;
    }


    public List<Categoria> getAllCategorias(){
        return categoriaRepository.findAll();
    }

    public Optional<Categoria> findById(int id){
        return categoriaRepository.findById(id);
    }

    public Categoria save(Categoria categoria){
        return categoriaRepository.save(categoria);
    }

    public void delete(Categoria categoria){
        categoriaRepository.delete(categoria);
    }

}
