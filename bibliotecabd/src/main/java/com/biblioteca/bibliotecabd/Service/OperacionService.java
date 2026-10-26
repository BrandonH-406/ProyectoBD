package com.biblioteca.bibliotecabd.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biblioteca.bibliotecabd.Model.Dto.DevolucionRequest;
import com.biblioteca.bibliotecabd.Model.Dto.PrestamoRequest;
import com.biblioteca.bibliotecabd.Model.Entity.Devolucion;
import com.biblioteca.bibliotecabd.Model.Entity.Ejemplar;
import com.biblioteca.bibliotecabd.Model.Entity.Multa;
import com.biblioteca.bibliotecabd.Model.Entity.PerBiblioteca;
import com.biblioteca.bibliotecabd.Model.Entity.Prestamo;
import com.biblioteca.bibliotecabd.Model.Entity.PrestamoDetalle;
import com.biblioteca.bibliotecabd.Model.Entity.Renovacion;
import com.biblioteca.bibliotecabd.Model.Entity.Rol;
import com.biblioteca.bibliotecabd.Model.Entity.Usuario;
import com.biblioteca.bibliotecabd.Model.Repository.DevolucionRepository;
import com.biblioteca.bibliotecabd.Model.Repository.EjemeplarRepository;
import com.biblioteca.bibliotecabd.Model.Repository.MultaRepository;
import com.biblioteca.bibliotecabd.Model.Repository.PerBibliotecaRepository;
import com.biblioteca.bibliotecabd.Model.Repository.PrestamoDetalleRepository;
import com.biblioteca.bibliotecabd.Model.Repository.PrestamoRepository;
import com.biblioteca.bibliotecabd.Model.Repository.RenovacionRepository;
import com.biblioteca.bibliotecabd.Model.Repository.UsuarioRepository;

// Lógica de préstamos, renovaciones y devoluciones.
// Cada método es una transacción: si una regla falla (IllegalArgumentException) no se guarda nada.
@Service
public class OperacionService {

    private final UsuarioRepository usuarioRepository;
    private final PerBibliotecaRepository perBibliotecaRepository;
    private final EjemeplarRepository ejemplarRepository;
    private final PrestamoRepository prestamoRepository;
    private final PrestamoDetalleRepository detalleRepository;
    private final RenovacionRepository renovacionRepository;
    private final DevolucionRepository devolucionRepository;
    private final MultaRepository multaRepository;

    public OperacionService(UsuarioRepository usuarioRepository, PerBibliotecaRepository perBibliotecaRepository,
            EjemeplarRepository ejemplarRepository, PrestamoRepository prestamoRepository,
            PrestamoDetalleRepository detalleRepository, RenovacionRepository renovacionRepository,
            DevolucionRepository devolucionRepository, MultaRepository multaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.perBibliotecaRepository = perBibliotecaRepository;
        this.ejemplarRepository = ejemplarRepository;
        this.prestamoRepository = prestamoRepository;
        this.detalleRepository = detalleRepository;
        this.renovacionRepository = renovacionRepository;
        this.devolucionRepository = devolucionRepository;
        this.multaRepository = multaRepository;
    }

    // Datos del usuario para mostrar antes de prestar: rol, cuántos libros tiene y si debe multas
    public Map<String, Object> resumenUsuario(String codigo) {
        Usuario usuario = buscarUsuario(codigo);
        Map<String, Object> resumen = new LinkedHashMap<>();
        resumen.put("usuario", usuario);
        resumen.put("prestados", detalleRepository.CantidadPrestados(usuario.getIdUsuario()));
        resumen.put("multasPendientes", multaRepository.CantidadPendientes(usuario.getIdUsuario()));
        return resumen;
    }

    public List<PrestamoDetalle> detallesUsuario(String codigo) {
        Usuario usuario = buscarUsuario(codigo);
        return detalleRepository.DetallesUsuario(usuario.getIdUsuario());
    }

    @Transactional
    public List<PrestamoDetalle> prestar(PrestamoRequest datos) {
        Usuario usuario = buscarUsuario(datos.codigo());
        PerBiblioteca bibliotecario = buscarBibliotecario(datos.idBibliotecario());
        Rol rol = usuario.getRol();

        if (!"ACTIVO".equals(usuario.getEstado())) {
            throw new IllegalArgumentException("El usuario está " + usuario.getEstado() + " y no puede prestar libros");
        }
        validarSinMultas(usuario);

        List<Integer> ids = datos.ejemplares();
        if (ids == null || ids.isEmpty()) {
            throw new IllegalArgumentException("Debe agregar al menos un ejemplar");
        }
        if (new HashSet<>(ids).size() != ids.size()) {
            throw new IllegalArgumentException("Hay ejemplares repetidos en el préstamo");
        }

        int prestados = detalleRepository.CantidadPrestados(usuario.getIdUsuario());
        if (prestados + ids.size() > rol.getMaxPrestamos()) {
            throw new IllegalArgumentException("El rol " + rol.getNombre() + " permite máximo " + rol.getMaxPrestamos()
                    + " libros prestados. El usuario ya tiene " + prestados + " y quiere llevar " + ids.size());
        }

        LocalDate hoy = LocalDate.now();
        Prestamo prestamo = prestamoRepository.save(new Prestamo(hoy, "ACTIVO", datos.observaciones(), usuario, bibliotecario));

        List<PrestamoDetalle> detalles = new ArrayList<>();
        for (Integer id : ids) {
            Ejemplar ejemplar = ejemplarRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("No existe el ejemplar #" + id));
            if (!"DISPONIBLE".equals(ejemplar.getEstado())) {
                throw new IllegalArgumentException("El ejemplar #" + id + " (" + ejemplar.getLibro().getTitulo()
                        + ") no está disponible, está " + ejemplar.getEstado());
            }
            ejemplar.setEstado("PRESTADO");
            ejemplarRepository.save(ejemplar);

            LocalDate devolucion = hoy.plusDays(rol.getDiasPrestamo());
            detalles.add(detalleRepository.save(new PrestamoDetalle(devolucion, 0, "PRESTADO", prestamo, ejemplar)));
        }
        return detalles;
    }

    @Transactional
    public PrestamoDetalle renovar(int idDetalle, int idBibliotecario) {
        PrestamoDetalle detalle = buscarDetallePrestado(idDetalle);
        PerBiblioteca bibliotecario = buscarBibliotecario(idBibliotecario);
        Usuario usuario = detalle.getPrestamo().getUsuario();
        Rol rol = usuario.getRol();
        LocalDate hoy = LocalDate.now();

        // Si ya se venció no se renueva: tiene que devolverlo y pagar la multa
        if (detalle.getFechaDevolucionPrevista().isBefore(hoy)) {
            throw new IllegalArgumentException("El préstamo venció el " + detalle.getFechaDevolucionPrevista()
                    + ". No se puede renovar, debe registrar la devolución");
        }
        if (detalle.getRenovaciones() >= rol.getMaxRenovaciones()) {
            throw new IllegalArgumentException("Ya alcanzó el máximo de " + rol.getMaxRenovaciones()
                    + " renovaciones para el rol " + rol.getNombre());
        }
        validarSinMultas(usuario);

        LocalDate anterior = detalle.getFechaDevolucionPrevista();
        LocalDate nueva = anterior.plusDays(rol.getDiasPrestamo());
        renovacionRepository.save(new Renovacion(hoy, anterior, nueva, bibliotecario, detalle));

        detalle.setFechaDevolucionPrevista(nueva);
        detalle.setRenovaciones(detalle.getRenovaciones() + 1);
        return detalleRepository.save(detalle);
    }

    @Transactional
    public Map<String, Object> devolver(int idDetalle, DevolucionRequest datos) {
        PrestamoDetalle detalle = buscarDetallePrestado(idDetalle);
        PerBiblioteca bibliotecario = buscarBibliotecario(datos.idBibliotecario());
        Usuario usuario = detalle.getPrestamo().getUsuario();
        Ejemplar ejemplar = detalle.getEjemplar();
        LocalDate hoy = LocalDate.now();

        int diasAtraso = (int) Math.max(0, ChronoUnit.DAYS.between(detalle.getFechaDevolucionPrevista(), hoy));
        String condicion = datos.condicion() != null ? datos.condicion() : ejemplar.getCondicion();
        devolucionRepository.save(new Devolucion(hoy, condicion, datos.observaciones(), diasAtraso, detalle, bibliotecario));

        detalle.setEstado("DEVUELTO");
        detalleRepository.save(detalle);

        // Un ejemplar que regresa dañado no vuelve a estar disponible
        ejemplar.setCondicion(condicion);
        ejemplar.setEstado("DAÑADO".equals(condicion) ? "MANTENIMIENTO" : "DISPONIBLE");
        ejemplarRepository.save(ejemplar);

        double monto = 0;
        if (diasAtraso > 0) {
            monto = diasAtraso * usuario.getRol().getMultaPorDia();
            // cobrado_por es obligatorio en la entidad: se pone al bibliotecario que recibió la devolución
            multaRepository.save(new Multa("ATRASO", monto, hoy, "PENDIENTE", null,
                    diasAtraso + " días de atraso", bibliotecario, detalle, usuario));
        }

        Prestamo prestamo = detalle.getPrestamo();
        if (detalleRepository.CantidadPrestadosPrestamo(prestamo.getIdPrestamo()) == 0) {
            prestamo.setEstado("FINALIZADO");
            prestamoRepository.save(prestamo);
        }

        Map<String, Object> resultado = new LinkedHashMap<>();
        resultado.put("diasAtraso", diasAtraso);
        resultado.put("multa", monto);
        resultado.put("mensaje", diasAtraso > 0
                ? "Devolución con " + diasAtraso + " días de atraso. Se generó una multa de Q" + String.format("%.2f", monto)
                : "Devolución a tiempo, sin multa");
        return resultado;
    }

    private Usuario buscarUsuario(String codigo) {
        return usuarioRepository.findByCarneOCodigoEmpleado(codigo)
                .orElseThrow(() -> new IllegalArgumentException("No existe un estudiante o profesor con el código " + codigo));
    }

    private PerBiblioteca buscarBibliotecario(Integer idBibliotecario) {
        if (idBibliotecario == null) {
            throw new IllegalArgumentException("Falta el bibliotecario");
        }
        return perBibliotecaRepository.findById(idBibliotecario)
                .orElseThrow(() -> new IllegalArgumentException("No existe el bibliotecario #" + idBibliotecario));
    }

    private PrestamoDetalle buscarDetallePrestado(int idDetalle) {
        PrestamoDetalle detalle = detalleRepository.findById(idDetalle)
                .orElseThrow(() -> new IllegalArgumentException("No existe el detalle de préstamo #" + idDetalle));
        if (!"PRESTADO".equals(detalle.getEstado())) {
            throw new IllegalArgumentException("Ese libro ya fue devuelto");
        }
        return detalle;
    }

    private void validarSinMultas(Usuario usuario) {
        int multas = multaRepository.CantidadPendientes(usuario.getIdUsuario());
        if (multas > 0) {
            throw new IllegalArgumentException("El usuario tiene " + multas + " multa(s) pendiente(s) de pago");
        }
    }
}
