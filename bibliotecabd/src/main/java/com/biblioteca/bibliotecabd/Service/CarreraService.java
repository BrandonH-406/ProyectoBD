package com.biblioteca.bibliotecabd.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.biblioteca.bibliotecabd.Model.Entity.Carrera;
import com.biblioteca.bibliotecabd.Model.Repository.CarreraRepository;

@Service 
public class CarreraService {

    private final CarreraRepository carreraRepository;

    public CarreraService(CarreraRepository carreraRepository){
        this.carreraRepository = carreraRepository;
    }

    public List<Carrera> getAllCarreras(){
        return carreraRepository.findAll();
    }

    public Optional<Carrera> findById(Integer id){
        return carreraRepository.findById(id);
    }

    public Carrera save(Carrera carrera){
        return carreraRepository.save(carrera);
    }

    public void delete(Carrera carrera){
        carreraRepository.delete(carrera);
    }
}
