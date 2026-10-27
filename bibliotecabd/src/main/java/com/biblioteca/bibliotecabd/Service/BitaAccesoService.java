package com.biblioteca.bibliotecabd.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.biblioteca.bibliotecabd.Model.Entity.BitaAcceso;
import com.biblioteca.bibliotecabd.Model.Entity.Usuario;
import com.biblioteca.bibliotecabd.Model.Repository.BitaAccesoRepository;

@Service
public class BitaAccesoService {

    private final BitaAccesoRepository bitaAccesoRepository;

    public BitaAccesoService(BitaAccesoRepository bitaAccesoRepository) {
        this.bitaAccesoRepository = bitaAccesoRepository;
    }

    public List<BitaAcceso> getAllBitaAccesos() {
        return bitaAccesoRepository.findAll();
    }

    public Optional<BitaAcceso> findById(long id) {
        return bitaAccesoRepository.findById(id);
    }

    public BitaAcceso save(BitaAcceso bitaAcceso) {
        return bitaAccesoRepository.save(bitaAcceso);
    }

    public void delete(BitaAcceso bitaAcceso) {
        bitaAccesoRepository.delete(bitaAcceso);
    }

    public void registrar(Usuario usuario, String username, String resultado,
            String ip, String detalle) {
        BitaAcceso b = new BitaAcceso();
        b.setUsuario(usuario); // null si el username no existe
        b.setUsernameIntentado(username);
        b.setResultado(resultado); // máx. 15 caracteres
        b.setDireccionIp(ip);
        b.setDetalle(detalle);
        b.setFecha(new Timestamp(System.currentTimeMillis()));
        save(b);
    }
}
