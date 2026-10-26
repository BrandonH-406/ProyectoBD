package com.biblioteca.bibliotecabd.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.biblioteca.bibliotecabd.Model.Entity.Rol;
import com.biblioteca.bibliotecabd.Model.Repository.RolRepository;

@Service 
public class RolService {

    private final RolRepository rolRepository;

    public RolService(RolRepository rolRepository){
        this.rolRepository = rolRepository;
    }

    public List<Rol> getAllRoles(){
        return rolRepository.findAll();
    }

    public Optional<Rol> findById(Integer id){
        return rolRepository.findById(id);
    }

    public Rol save(Rol rol){
        return rolRepository.save(rol);
    }

    public void delete(Rol rol){
        rolRepository.delete(rol);
    }
}
