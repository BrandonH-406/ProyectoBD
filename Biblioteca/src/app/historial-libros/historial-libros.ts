import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { Observable } from 'rxjs';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-historial-libros',
  imports: [FormsModule, RouterLink, CommonModule],
  templateUrl: './historial-libros.html',
  styleUrl: './historial-libros.css',
})
export class HistorialLibros {
  readonly http: HttpClient;
  usuarioActual:any = {};
  listaLibros: any = [];
  Libro: any = {};

  ListaCategorias: any = [];
  ListaEditorial: any = [];


  constructor(http: HttpClient) {
    let usuario: any = localStorage.getItem('administrador');
    this.http = http;

    if (usuario) {
      this.buscarLibros();
      this.buscarCategorias();
      this.buscarEditorial();
    } else {
      location.href = '';
    }
  }

  ngOnInit() {
    const sesion = localStorage.getItem('administrador');
    if (sesion) {
      this.usuarioActual = JSON.parse(sesion);
      console.log('Usuario activo:', this.usuarioActual);
    }
  }

  servicioBuscarLibros(): Observable<any> {
    return this.http.get<any>('http://localhost:8080/Libro/listaLibro');
  }

  buscarLibros() {
    this.servicioBuscarLibros().subscribe((data) => (this.listaLibros = data));
  }

  servicioBuscarCategorias(): Observable<any> {
    return this.http.get<any>('http://localhost:8080/Categoria/listaCategoria');
  }

  buscarCategorias() {
    this.servicioBuscarCategorias().subscribe((data) => (this.ListaCategorias = data));
  }

  servicioBuscarEditorial(): Observable<any> {
    return this.http.get<any>('http://localhost:8080/editorial/listaEditorial');
  }

  buscarEditorial() {
    this.servicioBuscarEditorial().subscribe((data) => (this.ListaEditorial = data));
  }

  CrearLibro() {


    // Formato de fecha en YYYY-MM-DD para evitar fallos con java.sql.Date
    this.Libro.fechaRegistrado = new Date().toISOString().split('T')[0];
    this.Libro.registradoPor = this.usuarioActual;

    if (this.Libro != null) {
      console.log('Objeto enviado al backend:', JSON.stringify(this.Libro));
      this.http.post('http://localhost:8080/Libro/createLibro', this.Libro).subscribe({
        next: (data) => this.finalizarGuardar(data),
        error: (err) => console.error('Error al crear Libro:', err),
      });
    }
  }

  finalizarGuardar(data: any) {
    if (data) {
      console.log('Libro creado con éxito:', data);
      this.buscarLibros();
    }
  }
}
