import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-gestion-empleados',
  imports: [FormsModule, RouterLink, CommonModule],
  templateUrl: './gestion-empleados.html',
  styleUrl: './gestion-empleados.css',
})
export class GestionEmpleados {
  private readonly api = 'http://localhost:8080';

  Usuario: any = {};
  PerBiblioteca: any = {};
  listaEmpleados: any[] = [];
  errorEmp = '';

  readonly http: HttpClient;

  constructor(http: HttpClient) {
    let usuario: any = localStorage.getItem('administrador');
    this.http = http;
    if (usuario) {
      this.buscarEmpleados();
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
  // LISTADO
  // ---------------------------------------------------------------

  buscarEmpleados() {
    this.http.get<any[]>(`${this.api}/PerBiblioteca/listarEmpleados`).subscribe({
      next: (data) => (this.listaEmpleados = data ?? []),
      error: (err) => console.error('Error al listar empleados:', err),
    });
  }

  // ---------------------------------------------------------------
  // CREAR / EDITAR
  // ---------------------------------------------------------------

  private validarEmpleado(): string {
    const u = this.Usuario;
    const e = this.PerBiblioteca;
    if (!u.nombres || !u.apellidos || !u.dpi || !u.email || !u.username || !u.passwordHash) {
      return 'Completa nombres, apellidos, DPI, correo, usuario y contraseña.';
    }
    if (!/^[0-9]{13}$/.test(String(u.dpi))) {
      return 'El DPI debe tener exactamente 13 dígitos.';
    }
    if (!e.codigoEmpleado || !e.puesto) {
      return 'Completa el código de empleado y el puesto.';
    }
    return '';
  }

  guardarEmpleado() {
    this.errorEmp = this.validarEmpleado();
    if (this.errorEmp) return;

    const fechaNac = this.formatearFechaISO(this.Usuario.fechaNacimiento) || null;

    // Datos propios del empleado (sin el usuario anidado)
    const { usuario: _omitido, ...datosEmp } = this.PerBiblioteca;

    if (this.Usuario.idUsuario) {
      // ----- EDICIÓN: 1) usuario  2) empleado -----
      const id = this.Usuario.idUsuario;
      const usuario = { ...this.Usuario, fechaNacimiento: fechaNac };

      this.http.put(`${this.api}/Usuario/update/${id}`, usuario).subscribe({
        next: (usuarioActualizado: any) => {
          if (!usuarioActualizado) {
            this.errorEmp = 'No se encontró el usuario a actualizar.';
            return;
          }
          const empleado = { ...datosEmp, usuario: usuarioActualizado };
          this.http.put(`${this.api}/PerBiblioteca/update/${id}`, empleado).subscribe({
            next: () => {
              alert('Bibliotecario actualizado con éxito');
              this.cancelar();
              this.buscarEmpleados();
            },
            error: (err) => this.fallaEmpleado(err),
          });
        },
        error: (err) => this.fallaEmpleado(err),
      });
    } else {
      // ----- CREACIÓN: 1) usuario  2) empleado vinculado -----
      const nuevoUsuario = {
        ...this.Usuario,
        fechaNacimiento: fechaNac,
        rol: { idRol: 1 }, // 1 = Bibliotecario
        estado: 'Activo',
        intentosFallidos: 0,
        fechaRegistro: this.hoy(),
      };

      this.http.post(`${this.api}/Usuario/createUsuario`, nuevoUsuario).subscribe({
        next: (usuarioCreado: any) => {
          if (!usuarioCreado) {
            this.errorEmp = 'No se pudo crear el usuario. Es posible que el nombre de usuario ya exista.';
            return;
          }
          const empleado = {
            ...datosEmp,
            usuario: usuarioCreado,
            fechaContratacion: this.hoy(),
          };
          this.http.post(`${this.api}/PerBiblioteca/createPerBiblioteca`, empleado).subscribe({
            next: (data) => {
              if (data) {
                alert('Usuario y Bibliotecario creados con éxito');
                this.cancelar();
                this.buscarEmpleados();
              } else {
                this.errorEmp = 'El usuario se creó, pero el empleado ya existía y no se guardó.';
              }
            },
            error: (err) => {
              console.error('Error al crear empleado:', err);
              this.errorEmp =
                'El usuario se creó, pero no se pudo guardar el empleado. Revisa que el código de empleado no esté repetido.';
            },
          });
        },
        error: (err) => this.fallaEmpleado(err),
      });
    }
  }

  private fallaEmpleado(err: any) {
    console.error('Error al guardar empleado:', err);
    this.errorEmp =
      'No se pudo guardar el bibliotecario. Revisa que DPI, correo, usuario y código de empleado no estén repetidos.';
  }

  editar(emp: any) {
    if (emp?.idUsuario != null) {
      this.Usuario = { ...emp.usuario };
      this.PerBiblioteca = { ...emp };
      this.errorEmp = '';
      this.irAlFormulario('form-registro');
    }
  }

  cancelar() {
    this.Usuario = {};
    this.PerBiblioteca = {};
    this.errorEmp = '';
  }

  // ---------------------------------------------------------------
  // ESTADO
  // ---------------------------------------------------------------

  cambiarEstado(emp: any): void {
    const usuario = emp.usuario;

    if (!usuario || usuario.idUsuario == null) {
      console.error('No se encontró el ID del usuario:', emp);
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