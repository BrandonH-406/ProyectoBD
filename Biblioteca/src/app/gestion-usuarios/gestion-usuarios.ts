import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-gestion-usuarios',
  imports: [FormsModule, RouterLink, CommonModule],
  templateUrl: './gestion-usuarios.html',
  styleUrl: './gestion-usuarios.css',
})
export class GestionUsuarios {
  private readonly api = 'http://localhost:8080';

  // Endpoints de los catálogos (ajústalos si en tu backend se llaman distinto)
  private readonly urlCarreras = '/Carrera/listaCarreras';
  private readonly urlFacultades = '/Facultad/listaFacultades';

  // ===== CATÁLOGOS (para los combos) =====
  listaCarreras: any[] = [];
  listaFacultades: any[] = [];

  // ===== ESTUDIANTES =====
  UsuarioEst: any = {};
  Estudiante: any = {};
  idCarrera: number | null = null;
  listaEstudiantes: any[] = [];
  errorEst = '';

  // ===== PROFESORES =====
  UsuarioProf: any = {};
  Profesor: any = {};
  idFacultad: number | null = null;
  listaProfesores: any[] = [];
  errorProf = '';

  readonly http: HttpClient;

  constructor(http: HttpClient) {
    let usuario: any = localStorage.getItem('administrador');
    this.http = http;
    if (usuario) {
      this.buscarCarreras();
      this.buscarFacultades();
      this.buscarEstudiantes();
      this.buscarProfesores();
    } else {
      location.href = '';
    }
  }

  // ---------------------------------------------------------------
  // Utilidades
  // ---------------------------------------------------------------

  /** Convierte DD/MM/YYYY a YYYY-MM-DD (lo que espera Spring Boot). */
  private formatearFechaISO(fecha: string): string {
    if (!fecha) return fecha;
    if (fecha.includes('/')) {
      const partes = fecha.split('/');
      if (partes.length === 3) {
        const [dia, mes, anio] = partes;
        return `${anio}-${mes.padStart(2, '0')}-${dia.padStart(2, '0')}`;
      }
    }
    return fecha;
  }

  /** Fecha de hoy en formato YYYY-MM-DD (hora local). */
  private hoy(): string {
    return new Date().toLocaleDateString('en-CA');
  }

  /** Acepta 'Activo', 'ACTIVO', 'activo'... */
  esActivo(usuario: any): boolean {
    return (usuario?.estado ?? '').toString().toLowerCase() === 'activo';
  }

  private irAlFormulario(id: string) {
    document.getElementById(id)?.scrollIntoView({ behavior: 'smooth', block: 'start' });
  }

  // ---------------------------------------------------------------
  // CATÁLOGOS (combos de carrera y facultad)
  // ---------------------------------------------------------------

  buscarCarreras() {
    this.http.get<any[]>(`${this.api}${this.urlCarreras}`).subscribe({
      next: (data) => (this.listaCarreras = data ?? []),
      error: (err) => console.error('Error al cargar carreras:', err),
    });
  }

  buscarFacultades() {
    this.http.get<any[]>(`${this.api}${this.urlFacultades}`).subscribe({
      next: (data) => (this.listaFacultades = data ?? []),
      error: (err) => console.error('Error al cargar facultades:', err),
    });
  }

  // ---------------------------------------------------------------
  // ESTUDIANTES
  // ---------------------------------------------------------------

  buscarEstudiantes() {
    this.http.get<any[]>(`${this.api}/Estudiante/listaEstudianteUsuaurio`).subscribe({
      next: (data) => (this.listaEstudiantes = data ?? []),
      error: (err) => console.error('Error al listar estudiantes:', err),
    });
  }

  private validarEstudiante(): string {
    const u = this.UsuarioEst;
    const e = this.Estudiante;
    if (!u.nombres || !u.apellidos || !u.dpi || !u.email || !u.username || !u.passwordHash) {
      return 'Completa nombres, apellidos, DPI, correo, usuario y contraseña.';
    }
    if (!/^[0-9]{13}$/.test(String(u.dpi))) {
      return 'El DPI debe tener exactamente 13 dígitos.';
    }
    if (!e.carne || !this.idCarrera || !e.anioIngreso || !e.semestreActual) {
      return 'Completa carné, carrera, año de ingreso y semestre actual.';
    }
    return '';
  }

  guardarEstudiante() {
    this.errorEst = this.validarEstudiante();
    if (this.errorEst) return;

    const fechaNac = this.formatearFechaISO(this.UsuarioEst.fechaNacimiento) || null;

    // Datos propios del estudiante (sin el usuario anidado)
    const { usuario: _omitido, ...datosEst } = this.Estudiante;
    datosEst.carrera = { idCarrera: Number(this.idCarrera) };
    datosEst.anioIngreso = Number(datosEst.anioIngreso);
    datosEst.semestreActual = Number(datosEst.semestreActual);

    if (this.UsuarioEst.idUsuario) {
      // ----- EDICIÓN -----
      const id = this.UsuarioEst.idUsuario;
      const usuario = { ...this.UsuarioEst, fechaNacimiento: fechaNac };

      this.http.put(`${this.api}/Usuario/update/${id}`, usuario).subscribe({
        next: () => {
          this.http.put(`${this.api}/Estudiante/update/${id}`, datosEst).subscribe({
            next: () => {
              alert('Estudiante actualizado con éxito');
              this.cancelarEstudiante();
              this.buscarEstudiantes();
            },
            error: (err) => this.fallaEstudiante(err),
          });
        },
        error: (err) => this.fallaEstudiante(err),
      });
    } else {
      // ----- CREACIÓN: 1) usuario  2) estudiante vinculado -----
      const nuevoUsuario = {
        ...this.UsuarioEst,
        fechaNacimiento: fechaNac,
        rol: { idRol: 3 }, // 3 = Estudiante
        estado: 'Activo',
        intentosFallidos: 0,
        fechaRegistro: this.hoy(),
      };

      this.http.post(`${this.api}/Usuario/createUsuario`, nuevoUsuario).subscribe({
        next: (usuarioCreado: any) => {
          if (!usuarioCreado) {
            this.errorEst = 'No se pudo crear el usuario.';
            return;
          }
          const estudiante = { ...datosEst, usuario: usuarioCreado };
          this.http.post(`${this.api}/Estudiante/createEstudiante`, estudiante).subscribe({
            next: (data) => {
              if (data) {
                alert('Usuario y Estudiante creados con éxito');
                this.cancelarEstudiante();
                this.buscarEstudiantes();
              } else {
                this.errorEst = 'El usuario se creó, pero el estudiante ya existía y no se guardó.';
              }
            },
            error: (err) => this.fallaEstudiante(err),
          });
        },
        error: (err) => this.fallaEstudiante(err),
      });
    }
  }

  private fallaEstudiante(err: any) {
    console.error('Error al guardar estudiante:', err);
    this.errorEst =
      'No se pudo guardar el estudiante. Revisa que DPI, correo, usuario y carné no estén repetidos.';
  }

  editarEstudiante(est: any) {
    if (est?.idUsuario != null) {
      this.UsuarioEst = { ...est.usuario };
      this.Estudiante = { ...est };
      this.idCarrera = est.carrera?.idCarrera ?? null;
      this.errorEst = '';
      this.irAlFormulario('form-estudiante');
    }
  }

  cancelarEstudiante() {
    this.UsuarioEst = {};
    this.Estudiante = {};
    this.idCarrera = null;
    this.errorEst = '';
  }

  // ---------------------------------------------------------------
  // PROFESORES
  // ---------------------------------------------------------------

  buscarProfesores() {
    this.http.get<any[]>(`${this.api}/Profesor/listaProfesorUsuaurio`).subscribe({
      next: (data) => (this.listaProfesores = data ?? []),
      error: (err) => console.error('Error al listar profesores:', err),
    });
  }

  private validarProfesor(): string {
    const u = this.UsuarioProf;
    const p = this.Profesor;
    if (!u.nombres || !u.apellidos || !u.dpi || !u.email || !u.username || !u.passwordHash) {
      return 'Completa nombres, apellidos, DPI, correo, usuario y contraseña.';
    }
    if (!/^[0-9]{13}$/.test(String(u.dpi))) {
      return 'El DPI debe tener exactamente 13 dígitos.';
    }
    if (!p.codigoEmpleado || !this.idFacultad) {
      return 'Completa el código de empleado y la facultad.';
    }
    return '';
  }

  guardarProfesor() {
    this.errorProf = this.validarProfesor();
    if (this.errorProf) return;

    const fechaNac = this.formatearFechaISO(this.UsuarioProf.fechaNacimiento) || null;

    // Datos propios del profesor (sin el usuario anidado)
    const { usuario: _omitido, ...datosProf } = this.Profesor;
    datosProf.facultad = { idFacultad: Number(this.idFacultad) };

    if (this.UsuarioProf.idUsuario) {
      // ----- EDICIÓN -----
      const id = this.UsuarioProf.idUsuario;
      const usuario = { ...this.UsuarioProf, fechaNacimiento: fechaNac };

      this.http.put(`${this.api}/Usuario/update/${id}`, usuario).subscribe({
        next: () => {
          this.http.put(`${this.api}/Profesor/update/${id}`, datosProf).subscribe({
            next: () => {
              alert('Profesor actualizado con éxito');
              this.cancelarProfesor();
              this.buscarProfesores();
            },
            error: (err) => this.fallaProfesor(err),
          });
        },
        error: (err) => this.fallaProfesor(err),
      });
    } else {
      // ----- CREACIÓN: 1) usuario  2) profesor vinculado -----
      const nuevoUsuario = {
        ...this.UsuarioProf,
        fechaNacimiento: fechaNac,
        rol: { idRol: 4 }, // 4 = Profesor
        estado: 'Activo',
        intentosFallidos: 0,
        fechaRegistro: this.hoy(),
      };

      this.http.post(`${this.api}/Usuario/createUsuario`, nuevoUsuario).subscribe({
        next: (usuarioCreado: any) => {
          if (!usuarioCreado) {
            this.errorProf = 'No se pudo crear el usuario.';
            return;
          }
          const profesor = { ...datosProf, usuario: usuarioCreado };
          this.http.post(`${this.api}/Profesor/createProfesor`, profesor).subscribe({
            next: (data) => {
              if (data) {
                alert('Usuario y Profesor creados con éxito');
                this.cancelarProfesor();
                this.buscarProfesores();
              } else {
                this.errorProf = 'El usuario se creó, pero el profesor ya existía y no se guardó.';
              }
            },
            error: (err) => this.fallaProfesor(err),
          });
        },
        error: (err) => this.fallaProfesor(err),
      });
    }
  }

  private fallaProfesor(err: any) {
    console.error('Error al guardar profesor:', err);
    this.errorProf =
      'No se pudo guardar el profesor. Revisa que DPI, correo, usuario y código de empleado no estén repetidos.';
  }

  editarProfesor(prof: any) {
    if (prof?.idUsuario != null) {
      this.UsuarioProf = { ...prof.usuario };
      this.Profesor = { ...prof };
      this.idFacultad = prof.facultad?.idFacultad ?? null;
      this.errorProf = '';
      this.irAlFormulario('form-profesor');
    }
  }

  cancelarProfesor() {
    this.UsuarioProf = {};
    this.Profesor = {};
    this.idFacultad = null;
    this.errorProf = '';
  }

  // ---------------------------------------------------------------
  // ESTADO (sirve para estudiantes y profesores)
  // ---------------------------------------------------------------

  cambiarEstado(item: any): void {
    const usuario = item.usuario;

    if (!usuario || usuario.idUsuario == null) {
      console.error('No se encontró el ID del usuario:', item);
      alert('No se encontró el ID del usuario');
      return;
    }

    const nuevoEstado = this.esActivo(usuario) ? 'Inactivo' : 'Activo';

    this.http
      .patch<any>(`${this.api}/Usuario/update_partial_usuario/${usuario.idUsuario}`, {
        estado: nuevoEstado,
      })
      .subscribe({
        next: (respuesta) => {
          usuario.estado = respuesta.estado;
          alert('Estado actualizado correctamente');
        },
        error: (error) => {
          console.error('Error al cambiar el estado:', error);
          alert('No se pudo cambiar el estado del usuario');
        },
      });
  }

  logout() {
    localStorage.clear();
    location.href = '/';
  }
}
