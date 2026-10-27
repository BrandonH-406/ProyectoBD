package com.biblioteca.bibliotecabd.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.biblioteca.bibliotecabd.Model.Entity.PerBiblioteca;
import com.biblioteca.bibliotecabd.Model.Repository.PerBibliotecaRepository;
import com.biblioteca.bibliotecabd.Model.Repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class PerBibliotecaService {

    private final PerBibliotecaRepository perBibliotecaRepository;
    private final UsuarioRepository usuarioRepository;

    public PerBibliotecaService(PerBibliotecaRepository perBibliotecaRepository, UsuarioRepository usuarioRepository) {
        this.perBibliotecaRepository = perBibliotecaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<PerBiblioteca> getAllPerBiblioteca() {
        return perBibliotecaRepository.findAll();
    }

    public Optional<PerBiblioteca> findById(Integer id) {
        return perBibliotecaRepository.findById(id);
    }

    public PerBiblioteca save(PerBiblioteca perBiblioteca) {
        return perBibliotecaRepository.save(perBiblioteca);
    }

    public void delete(PerBiblioteca perBiblioteca) {
        perBibliotecaRepository.delete(perBiblioteca);
    }

    public Optional<PerBiblioteca> findByCodigoEmpleado(String codigoEmpleado) {
        return perBibliotecaRepository.findByCodigoEmpleado(codigoEmpleado);
    }

    public List<PerBiblioteca> obtenerTodosLosEmpleados() {
        return perBibliotecaRepository.findAllConUsuario();
    }

@Transactional
    public void eliminarBibliotecarioYUsuario(Integer idUsuario) {
        // 1. Buscamos primero la entidad PerBiblioteca
        PerBiblioteca bibliotecario = perBibliotecaRepository.findById(idUsuario).orElse(null);

        // 2. BORRAMOS PRIMERO el registro de PerBiblioteca (la tabla hija)
        if (bibliotecario != null) {
            perBibliotecaRepository.delete(bibliotecario);
            // Hacemos un flush manual para asegurar que Oracle ejecute el DELETE de la tabla hija de inmediato
            perBibliotecaRepository.flush(); 
        }

        // 3. AHORA SÍ borramos el Usuario (la tabla padre)
        if (usuarioRepository.existsById(idUsuario)) {
            usuarioRepository.deleteById(idUsuario);
        }
    }
}
