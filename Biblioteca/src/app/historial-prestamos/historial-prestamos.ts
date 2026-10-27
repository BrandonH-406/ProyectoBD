import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { Observable } from 'rxjs';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-historial-prestamos',
  imports: [FormsModule, RouterLink, CommonModule],
  templateUrl: './historial-prestamos.html',
  styleUrl: './historial-prestamos.css',
})
export class HistorialPrestamos {
  readonly http: HttpClient;
  listaPrestamo: any = [];
  listaDevoluciones: any = [];
  listaRenovaciones: any = [];
  listaMultas: any = [];

  ListaDetalle: any = null;

  prestamoSeleccionado: any = null;

  constructor(http: HttpClient) {
    let usuario: any = localStorage.getItem('administrador');
    this.http = http;
    if (usuario) {
      this.buscarPrestamo();
      this.buscarDevoluciones();
      this.buscarRenovaciones();
      this.buscarMultas();
    } else {
      location.href = '';
    }
  }

  servicioBuscarPrestamo(): Observable<any> {
    return this.http.get<any>('http://localhost:8080/Prestamo/listaPrestamo');
  }

  buscarPrestamo() {
    this.servicioBuscarPrestamo().subscribe((data) => (this.listaPrestamo = data));
  }

  servicioBuscarRenovaciones(): Observable<any> {
    return this.http.get<any>('http://localhost:8080/Renovacion/listaRenovaciones');
  }

  buscarRenovaciones() {
    this.servicioBuscarRenovaciones().subscribe((data) => (this.listaRenovaciones = data));
  }

  servicioBuscarDevoluciones(): Observable<any> {
    return this.http.get<any>('http://localhost:8080/Devoluciones/listaDevoluciones');
  }

  buscarDevoluciones() {
    this.servicioBuscarDevoluciones().subscribe((data) => (this.listaDevoluciones = data));
  }

  servicioBuscarMultas(): Observable<any> {
    return this.http.get<any>('http://localhost:8080/Multa/listaLibro');
  }

  buscarMultas() {
    this.servicioBuscarMultas().subscribe((data) => (this.listaMultas = data));
  }

  logout() {
    localStorage.clear();
    location.href = '/';
  }

  servicioBuscarDetalles(prestamo: any): Observable<any> {
    return this.http.get<any>(
      'http://localhost:8080/PrestamoDetalle/DetallesPrestamo/' + prestamo.idPrestamo,
    );
  }

  buscarDetalle(prestamo: any) {
    this.servicioBuscarDetalles(prestamo).subscribe((data) => (this.ListaDetalle = data));
  }

  abrirDetalles(prestamo: any): void {
    this.prestamoSeleccionado = prestamo;

    if (this.prestamoSeleccionado.idPrestamo != null) {
      this.buscarDetalle(this.prestamoSeleccionado);
    }
  }

  cerrarDetalles(): void {
    this.ListaDetalle = null;
    this.prestamoSeleccionado = null;
  }
}



// REVISAR TABLA DE DETALLES ESTA FALLAND0 