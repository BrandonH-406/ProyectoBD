import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { estadoDetalle, mensajeError } from '../operaciones';

@Component({
  selector: 'app-renovaciones',
  imports: [FormsModule],
  templateUrl: './renovaciones.html',
  styleUrl: '../formularios.css',
})
export class Renovaciones {
  readonly http: HttpClient;
  readonly api = 'http://localhost:8080';
  readonly estadoDetalle = estadoDetalle;

  codigo: string = '';
  Resumen: any = null;
  Prestados: any[] = [];
  usuario: any = JSON.parse(localStorage.getItem('administrador') || '{}');

  constructor(http: HttpClient) {
    this.http = http;
  }

  buscarUsuario() {
    if (!this.codigo.trim()) {
      alert('Ingrese el carné o código de empleado');
      return;
    }
    this.Resumen = null;
    this.Prestados = [];
    let codigo = encodeURIComponent(this.codigo.trim());
    this.http.get(this.api + '/Operacion/usuario/' + codigo).subscribe({
      next: data => {
        this.Resumen = data;
        this.cargarPrestados(codigo);
      },
      error: e => alert(mensajeError(e)),
    });
  }

  limpiar() {
    this.codigo = '';
    this.Resumen = null;
    this.Prestados = [];
  }

  cargarPrestados(codigo: string) {
    this.http.get<any[]>(this.api + '/Operacion/detallesUsuario/' + codigo)
      .subscribe(data => this.Prestados = data.filter(d => d.estado == 'PRESTADO'));
  }

  renovar(detalle: any) {
    if (!confirm('¿Renovar "' + detalle.ejemplar.libro.titulo + '"?')) {
      return;
    }
    this.http.post<any>(this.api + '/Operacion/renovar/' + detalle.idDetalle + '/' + this.usuario.idUsuario, {}).subscribe({
      next: data => {
        alert('Renovado. Nueva fecha de devolución: ' + data.fechaDevolucionPrevista);
        this.cargarPrestados(encodeURIComponent(this.codigo.trim()));
      },
      error: e => alert(mensajeError(e)),
    });
  }
}
