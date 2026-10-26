import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { Observable } from 'rxjs';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-gestion-empleados',
  imports: [FormsModule, RouterLink, CommonModule],
  templateUrl: './gestion-empleados.html',
  styleUrl: './gestion-empleados.css',
})
export class GestionEmpleados {
  Usuario: any = {};
  Estudiante: any = {};
  Profesor: any = {};
  PerBiblioteca: any = {};
  listaEmpleados: any = [];
  readonly http: HttpClient;
  constructor(http: HttpClient) {
    let usuario: any = localStorage.getItem('administrador');
    this.http = http;
        if (usuario) {
      this.buscarUsuarios();
    } else {
      location.href = '';
    }
  }

  CrearUsuario() {
    // 1. Asignaciones seguras
    this.Usuario.rol = { idRol: 1 };
    this.Usuario.estado = 'Activo';
    this.Usuario.intentosFallidos = 0;

    // Formato de fecha en YYYY-MM-DD para evitar fallos con java.sql.Date
    this.Usuario.fechaRegistro = new Date().toISOString().split('T')[0];

    if (this.Usuario && this.PerBiblioteca) {
      this.http.post('http://localhost:8080/Usuario/createUsuario', this.Usuario).subscribe({
        next: (data) => this.finalizarGuardar(data),
        error: (err) => console.error('Error al crear usuario:', err),
      });
    }
  }

  finalizarGuardar(data: any) {
    if (data) {
      console.log('Usuario creado con éxito:', data);

      // 2. Vinculamos el usuario recién creado con el registro de empleado
      this.PerBiblioteca.usuario = data;
      this.CrearEmpleado();
    }
  }

  CrearEmpleado() {
    this.PerBiblioteca.fechaContratacion = new Date().toISOString().split('T')[0];

    this.http
      .post('http://localhost:8080/PerBiblioteca/createPerBiblioteca', this.PerBiblioteca)
      .subscribe({
        next: (data) => this.finalizarGuardarEmpleado(data),
        error: (err) => console.error('Error al crear empleado:', err),
      });
  }

  finalizarGuardarEmpleado(data: any) {
    if (data) {
      console.log('Empleado registrado:', data);
      alert('Usuario y Bibliotecario creados con éxito');

      // Limpiar formulario opcional
      this.Usuario = { rol: { idRol: 1 } };
      this.PerBiblioteca = {};
    }
  }

  servicioBuscarUsuarios(): Observable<any> {
    return this.http.get<any>('http://localhost:8080/PerBiblioteca/listarEmpleados');
  }

  buscarUsuarios() {
    this.servicioBuscarUsuarios().subscribe((data) => (this.listaEmpleados = data));
  }
}
