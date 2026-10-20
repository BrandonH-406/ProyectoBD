package com.biblioteca.bibliotecabd.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.biblioteca.bibliotecabd.Model.Entity.Devolucion;
import com.biblioteca.bibliotecabd.Model.Repository.DevolucionRepository;

@Service 
public class DevolucionService {

    private final DevolucionRepository devolucionRepository;

    public DevolucionService(DevolucionRepository devolucionRepository){
        this.devolucionRepository = devolucionRepository;
    }

        public List<Devolucion> getAllDevoluciones(){
        return devolucionRepository.findAll();
    }

    public Optional<Devolucion> findById(int id){
        return devolucionRepository.findById(id);
    }

    public Devolucion save(Devolucion devolucion){
        return devolucionRepository.save(devolucion);
    }

    public void delete(Devolucion devolucion){
        devolucionRepository.delete(devolucion);
    }

}
