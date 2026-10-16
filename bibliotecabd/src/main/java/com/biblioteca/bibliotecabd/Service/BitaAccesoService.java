package com.biblioteca.bibliotecabd.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.biblioteca.bibliotecabd.Model.Entity.BitaAcceso;
import com.biblioteca.bibliotecabd.Model.Repository.BitaAccesoRepository;

@Service 
public class BitaAccesoService {

    private final BitaAccesoRepository bitaAccesoRepository;

    public BitaAccesoService(BitaAccesoRepository bitaAccesoRepository){
        this.bitaAccesoRepository = bitaAccesoRepository;
    }

    public List<BitaAcceso> getAllBitaAccesos(){
        return bitaAccesoRepository.findAll();
    }

    public Optional<BitaAcceso> findById(long id){
        return bitaAccesoRepository.findById(id);
    }

    public BitaAcceso save(BitaAcceso bitaAcceso){
        return bitaAccesoRepository.save(bitaAcceso);
    }

    public void delete(BitaAcceso bitaAcceso){
        bitaAccesoRepository.delete(bitaAcceso);
    }
}
