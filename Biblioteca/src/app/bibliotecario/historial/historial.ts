import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { estadoDetalle, mensajeError } from '../operaciones';

@Component({
  selector: 'app-historial',
  imports: [FormsModule],
  templateUrl: './historial.html',
  styleUrl: '../formularios.css',
})
export class Historial {
  readonly http: HttpClient;
  readonly api = 'http://localhost:8080';
  readonly estadoDetalle = estadoDetalle;

  codigo: string = '';
  Usuario: any = null;
  Detalles: any[] = [];
  // idDetalle -> devolución, para mostrar cuándo se devolvió de verdad (PrestamoDetalle oculta la lista en el JSON)
  Devoluciones: any = {};
  estadoFiltro: string = '';

  constructor(http: HttpClient) {
    this.http = http;
  }

  buscarUsuario() {
    if (!this.codigo.trim()) {
      alert('Ingrese el carné o código de empleado');
      return;
    }
    this.Usuario = null;
    this.Detalles = [];
    let codigo = encodeURIComponent(this.codigo.trim());
    this.http.get<any>(this.api + '/Operacion/usuario/' + codigo).subscribe({
      next: data => {
        this.Usuario = data.usuario;
        this.http.get<any[]>(this.api + '/Operacion/detallesUsuario/' + codigo).subscribe(data => this.Detalles = data);
        this.cargarDevoluciones();
      },
      error: e => alert(mensajeError(e)),
    });
  }

  limpiar() {
    this.codigo = '';
    this.Usuario = null;
    this.Detalles = [];
    this.estadoFiltro = '';
  }

  cargarDevoluciones() {
    this.http.get<any[]>(this.api + '/Devoluciones/listaDevoluciones').subscribe(data => {
      this.Devoluciones = {};
      for (let d of data) {
        this.Devoluciones[d.detalle.idDetalle] = d;
      }
    });
  }

  detallesFiltrados() {
    if (!this.estadoFiltro) {
      return this.Detalles;
    }
    return this.Detalles.filter(d => estadoDetalle(d) == this.estadoFiltro);
  }

  contar(estado: string) {
    return this.Detalles.filter(d => estadoDetalle(d) == estado).length;
  }
}
