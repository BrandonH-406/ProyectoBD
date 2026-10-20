package com.biblioteca.bibliotecabd.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.biblioteca.bibliotecabd.Model.Entity.Multa;
import com.biblioteca.bibliotecabd.Model.Repository.MultaRepository;

@Service 
public class MultaService {

    private final MultaRepository multaRepository;

    public MultaService(MultaRepository multaRepository){
        this.multaRepository = multaRepository;
    }

        public List<Multa> getAllMulta() {
        return (List<Multa>) multaRepository.findAll();
    }

    public Optional<Multa> findById(int id) {
        return multaRepository.findById(id);
    }

    public Multa save(Multa multa) {
        return multaRepository.save(multa);
    }

    public void delete(Multa multa) {
        multaRepository.delete(multa);
    }

}
