import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { mensajeError } from '../operaciones';

@Component({
  selector: 'app-prestamos',
  imports: [FormsModule],
  templateUrl: './prestamos.html',
  styleUrl: '../formularios.css',
})
export class Prestamos {
  readonly http: HttpClient;
  readonly api = 'http://localhost:8080';

  codigo: string = '';
  Resumen: any = null;
  Ejemplares: any[] = [];
  Seleccionados: any[] = [];
  buscarLibro: string = '';
  observaciones: string = '';
  Realizado: any[] = [];
  usuario: any = JSON.parse(localStorage.getItem('administrador') || '{}');

  constructor(http: HttpClient) {
    this.http = http;
    this.cargarEjemplares();
  }

  cargarEjemplares() {
    this.http.get<any[]>(this.api + '/Ejemplar/listaEjemplares').subscribe(data => this.Ejemplares = data);
  }

  buscarUsuario() {
    if (!this.codigo.trim()) {
      alert('Ingrese el carné o código de empleado');
      return;
    }
    this.Resumen = null;
    this.Seleccionados = [];
    this.Realizado = [];
    this.http.get(this.api + '/Operacion/usuario/' + encodeURIComponent(this.codigo.trim())).subscribe({
      next: data => this.Resumen = data,
      error: e => alert(mensajeError(e)),
    });
  }

  disponibles() {
    let texto = this.buscarLibro.toLowerCase();
    return this.Ejemplares.filter(e => e.estado == 'DISPONIBLE'
      && !this.Seleccionados.includes(e)
      && e.libro.titulo.toLowerCase().includes(texto));
  }

  // Cuántos libros más se puede llevar según el máximo de su rol
  cupo() {
    return this.Resumen.usuario.rol.maxPrestamos - this.Resumen.prestados - this.Seleccionados.length;
  }

  agregar(ejemplar: any) {
    if (this.cupo() <= 0) {
      alert('El usuario ya llegó al máximo de ' + this.Resumen.usuario.rol.maxPrestamos + ' libros prestados');
      return;
    }
    this.Seleccionados.push(ejemplar);
  }

  quitar(ejemplar: any) {
    this.Seleccionados = this.Seleccionados.filter(e => e != ejemplar);
  }

  prestar() {
    if (this.Seleccionados.length == 0) {
      alert('Agregue al menos un ejemplar');
      return;
    }
    let datos = {
      codigo: this.codigo.trim(),
      idBibliotecario: this.usuario.idUsuario,
      ejemplares: this.Seleccionados.map(e => e.idEjemplar),
      observaciones: this.observaciones,
    };
    this.http.post<any[]>(this.api + '/Operacion/prestar', datos).subscribe({
      next: data => {
        this.Realizado = data;
        this.Resumen = null;
        this.Seleccionados = [];
        this.observaciones = '';
        this.cargarEjemplares();
      },
      error: e => alert(mensajeError(e)),
    });
  }

  limpiar() {
    this.codigo = '';
    this.Resumen = null;
    this.Seleccionados = [];
    this.buscarLibro = '';
    this.observaciones = '';
    this.Realizado = [];
  }
}
