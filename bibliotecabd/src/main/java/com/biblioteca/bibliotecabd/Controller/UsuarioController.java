package com.biblioteca.bibliotecabd.Controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.bibliotecabd.Model.Entity.Usuario;
import com.biblioteca.bibliotecabd.Service.RolService;
import com.biblioteca.bibliotecabd.Service.UsuarioService;

@CrossOrigin
@RestController
@RequestMapping("/Usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final RolService rolService;

    public UsuarioController(UsuarioService usuarioService, RolService rolService) {
        this.usuarioService = usuarioService;
        this.rolService = rolService;
    }

    @GetMapping("/listaUsuarios")
    public List<Usuario> getAll() {
        return usuarioService.getAllUsuarios();
    }

    @GetMapping("/usuarioId/{id}")
    public Optional<Usuario> getById(@PathVariable("id") Integer id) {
        return usuarioService.findById(id);
    }

    @PostMapping("/createUsuario")
    public Usuario postMethodUsuario(@RequestBody Usuario entity) {
        if (entity != null && entity.getUsername() != null) {
            // Validamos si el username ya está registrado en la base de datos
            if (usuarioService.findByUsername(entity.getUsername()).isPresent()) {
                return null; // O retornar ResponseEntity.badRequest()
            }
        }
        return usuarioService.save(entity);
    }

    @PutMapping("/update/{par_id}")
    public Usuario updateUsuario(@PathVariable("par_id") Integer UsuarioId, @RequestBody Usuario updateUsuario) {

        Optional<Usuario> usuarioOptional = usuarioService.findById(UsuarioId);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setRol(updateUsuario.getRol());
            usuario.setDpi(updateUsuario.getDpi());
            usuario.setNombres(updateUsuario.getNombres());
            usuario.setApellidos(updateUsuario.getApellidos());
            usuario.setFechaNacimiento(updateUsuario.getFechaNacimiento());
            usuario.setEmail(updateUsuario.getEmail());
            usuario.setTelefono(updateUsuario.getTelefono());
            usuario.setDireccion(updateUsuario.getDireccion());
            usuario.setUsername(updateUsuario.getUsername());
            usuario.setPasswordHash(updateUsuario.getPasswordHash());
            usuario.setIntentosFallidos(updateUsuario.getIntentosFallidos());
            usuario.setUltimoAcceso(updateUsuario.getUltimoAcceso());
            usuario.setEstado(updateUsuario.getEstado());
            usuario.setFechaRegistro(updateUsuario.getFechaRegistro());
            return usuarioService.save(usuario);
        } else {
            return null;
        }
    }

    @PatchMapping("/update_partial_usuario/{par_id}")
    public Usuario partialUpdateUsuario(@PathVariable Integer par_id, @RequestBody Map<String, Object> updates) {
        Optional<Usuario> UsuarioOptional = usuarioService.findById(par_id);

        if (UsuarioOptional.isPresent()) {
            Usuario usuario = UsuarioOptional.get();

            updates.forEach((key, value) -> {
                switch (key) {
                    case "idRol":
                        rolService.findById(((Number) value).intValue()).ifPresent(usuario::setRol);
                        break;
                    case "dpi":
                        usuario.setDpi((String) value);
                        break;
                    case "nombres":
                        usuario.setNombres((String) value);
                        break;
                    case "apellidos":
                        usuario.setApellidos((String) value);
                        break;
                    case "fechaNacimiento":
                        usuario.setFechaNacimiento(Date.valueOf((String) value));
                        break;
                    case "email":
                        usuario.setEmail((String) value);
                        break;
                    case "telefono":
                        usuario.setTelefono((String) value);
                        break;
                    case "direccion":
                        usuario.setDireccion((String) value);
                        break;
                    case "username":
                        usuario.setUsername((String) value);
                        break;
                    case "passwordHash":
                        usuario.setPasswordHash((String) value);
                        break;
                    case "intentosFallidos":
                        usuario.setIntentosFallidos(((Number) value).intValue());
                        break;
                    case "ultimoAcceso":
                        usuario.setUltimoAcceso(Timestamp.valueOf((String) value));
                        break;
                    case "estado":
                        usuario.setEstado((String) value);
                        break;
                    case "fechaRegistro":
                        usuario.setFechaRegistro(Date.valueOf((String) value));
                        break;
                }
            });

            return usuarioService.save(usuario);
        } else {
            return null;
        }
    }

    @DeleteMapping("/deleteUsuario/{id}")
    public void DeleteUsuario(@PathVariable("id") int id) {
        Optional<Usuario> UsuarioOptional = usuarioService.findById(id);
        if (UsuarioOptional.isPresent()) {
            usuarioService.delete(UsuarioOptional.get());
        }
    }

    @PostMapping("/login")
    public Usuario loginAdmin(@RequestBody Usuario Admin) {
        Optional<Usuario> Administrador = usuarioService.findByUsername(Admin.getUsername());

        if (Administrador.isEmpty()) {
            return null;
        }

        Usuario user = Administrador.get();

        if (!user.getPasswordHash().equals(Admin.getPasswordHash())) {
            return null;
        }

        return user;
    }
}
