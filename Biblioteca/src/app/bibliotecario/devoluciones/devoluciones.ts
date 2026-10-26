import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { diasAtraso, estadoDetalle, mensajeError } from '../operaciones';

@Component({
  selector: 'app-devoluciones',
  imports: [FormsModule],
  templateUrl: './devoluciones.html',
  styleUrl: '../formularios.css',
})
export class Devoluciones {
  readonly http: HttpClient;
  readonly api = 'http://localhost:8080';
  readonly estadoDetalle = estadoDetalle;
  readonly diasAtraso = diasAtraso;

  codigo: string = '';
  Usuario: any = null;
  Prestados: any[] = [];
  Detalle: any = null;
  condicion: string = '';
  observaciones: string = '';
  Resultado: any = null;
  usuario: any = JSON.parse(localStorage.getItem('administrador') || '{}');

  constructor(http: HttpClient) {
    this.http = http;
  }

  buscarUsuario() {
    if (!this.codigo.trim()) {
      alert('Ingrese el carné o código de empleado');
      return;
    }
    this.Usuario = null;
    this.Detalle = null;
    this.Resultado = null;
    let codigo = encodeURIComponent(this.codigo.trim());
    this.http.get<any>(this.api + '/Operacion/usuario/' + codigo).subscribe({
      next: data => {
        this.Usuario = data.usuario;
        this.cargarPrestados(codigo);
      },
      error: e => alert(mensajeError(e)),
    });
  }

  limpiar() {
    this.codigo = '';
    this.Usuario = null;
    this.Prestados = [];
    this.Detalle = null;
    this.Resultado = null;
  }

  cargarPrestados(codigo: string) {
    this.http.get<any[]>(this.api + '/Operacion/detallesUsuario/' + codigo)
      .subscribe(data => this.Prestados = data.filter(d => d.estado == 'PRESTADO'));
  }

  seleccionar(detalle: any) {
    this.Detalle = detalle;
    this.condicion = detalle.ejemplar.condicion;
    this.observaciones = '';
    this.Resultado = null;
  }

  multaEstimada() {
    return diasAtraso(this.Detalle) * this.Usuario.rol.multaPorDia;
  }

  devolver() {
    let datos = {
      idBibliotecario: this.usuario.idUsuario,
      condicion: this.condicion,
      observaciones: this.observaciones,
    };
    this.http.post<any>(this.api + '/Operacion/devolver/' + this.Detalle.idDetalle, datos).subscribe({
      next: data => {
        this.Resultado = { ...data, titulo: this.Detalle.ejemplar.libro.titulo };
        alert(data.mensaje);
        this.Detalle = null;
        this.cargarPrestados(encodeURIComponent(this.codigo.trim()));
      },
      error: e => alert(mensajeError(e)),
    });
  }
}
