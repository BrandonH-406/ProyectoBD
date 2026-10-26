package com.biblioteca.bibliotecabd.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.biblioteca.bibliotecabd.Model.Entity.Editorial;
import com.biblioteca.bibliotecabd.Model.Repository.EditorialRepository;

@Service
public class EditorialService {

    private final EditorialRepository editorialRepository;

    public EditorialService(EditorialRepository editorialRepository) {
        this.editorialRepository = editorialRepository;
    }

    public List<Editorial> getAllEditorial() {
        return (List<Editorial>) editorialRepository.findAll();
    }

    public Optional<Editorial> findById(Integer id) {
        return editorialRepository.findById(id);
    }

    public Editorial save(Editorial editorial) {
        return editorialRepository.save(editorial);
    }

    public void delete(Editorial editorial) {
        editorialRepository.delete(editorial);
    }
}
