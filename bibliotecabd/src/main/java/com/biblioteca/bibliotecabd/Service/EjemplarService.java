package com.biblioteca.bibliotecabd.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.biblioteca.bibliotecabd.Model.Entity.Ejemplar;
import com.biblioteca.bibliotecabd.Model.Repository.EjemeplarRepository;

@Service
public class EjemplarService {

    private final EjemeplarRepository ejemeplarRepository;

    public EjemplarService(EjemeplarRepository ejemeplarRepository) {
        this.ejemeplarRepository = ejemeplarRepository;
    }

    public List<Ejemplar> getAllEjemplares() {
        return (List<Ejemplar>) ejemeplarRepository.findAll();
    }

    public Optional<Ejemplar> findById(int id) {
        return ejemeplarRepository.findById(id);
    }

    public Ejemplar save(Ejemplar ejemplar) {
        return ejemeplarRepository.save(ejemplar);
    }

    public void delete(Ejemplar ejemplar) {
        ejemeplarRepository.delete(ejemplar);
    }

    public int CountEjemplares(int idLibro, String estado){
        return ejemeplarRepository.CantidadEjemplares(idLibro, estado);
    }

}
