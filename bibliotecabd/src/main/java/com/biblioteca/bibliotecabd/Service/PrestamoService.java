package com.biblioteca.bibliotecabd.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.biblioteca.bibliotecabd.Model.Entity.Prestamo;
import com.biblioteca.bibliotecabd.Model.Repository.PrestamoRepository;

@Service 
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;

    public PrestamoService(PrestamoRepository prestamoRepository){
        this.prestamoRepository = prestamoRepository;
    }

    public List<Prestamo> getAllPrestamos() {
        return (List<Prestamo>) prestamoRepository.findAll();
    }

    public Optional<Prestamo> findById(int id) {
        return prestamoRepository.findById(id);
    }

    public Prestamo save(Prestamo prestamo) {
        return prestamoRepository.save(prestamo);
    }

    public void delete(Prestamo prestamo) {
        prestamoRepository.delete(prestamo);
    }

    public List<Prestamo> PrestamosUsuarios(String idenficador){
        return prestamoRepository.findPrestamosPorCarneOCodigoEmpleado(idenficador);
    }

}
