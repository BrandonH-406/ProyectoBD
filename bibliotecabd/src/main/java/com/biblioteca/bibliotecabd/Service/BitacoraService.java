package com.biblioteca.bibliotecabd.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.biblioteca.bibliotecabd.Model.Entity.Bitacora;
import com.biblioteca.bibliotecabd.Model.Repository.BitacoraRepository;

@Service 
public class BitacoraService {

    private final BitacoraRepository bitacoraRepository;

    public BitacoraService(BitacoraRepository bitacoraRepository){
        this.bitacoraRepository = bitacoraRepository;
    }

    public List<Bitacora> getAllBitacoras(){
        return bitacoraRepository.findAll();
    }

    public Optional<Bitacora> findById(long id){
        return bitacoraRepository.findById(id);
    }

    public Bitacora save(Bitacora bitacora){
        return bitacoraRepository.save(bitacora);
    }

    public void delete(Bitacora bitacora){
        bitacoraRepository.delete(bitacora);
    }
}
