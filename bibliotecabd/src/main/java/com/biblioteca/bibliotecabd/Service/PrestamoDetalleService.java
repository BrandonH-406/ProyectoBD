package com.biblioteca.bibliotecabd.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.biblioteca.bibliotecabd.Model.Entity.PrestamoDetalle;
import com.biblioteca.bibliotecabd.Model.Repository.PrestamoDetalleRepository;

@Service
public class PrestamoDetalleService {

    private final PrestamoDetalleRepository prestamoDetalleRepository;

    public PrestamoDetalleService(PrestamoDetalleRepository prestamoDetalleRepository) {
        this.prestamoDetalleRepository = prestamoDetalleRepository;
    }

    public List<PrestamoDetalle> getAllCategorias() {
        return prestamoDetalleRepository.findAll();
    }

    public Optional<PrestamoDetalle> findById(int id) {
        return prestamoDetalleRepository.findById(id);
    }

    public PrestamoDetalle save(PrestamoDetalle prestamoDetalle) {
        return prestamoDetalleRepository.save(prestamoDetalle);
    }

    public void delete(PrestamoDetalle prestamoDetalle) {
        prestamoDetalleRepository.delete(prestamoDetalle);
    }

}
