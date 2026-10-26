import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-libros',
  imports: [FormsModule],
  templateUrl: './libros.html',
  styleUrl: '../formularios.css',
})
export class Libros {
  readonly http: HttpClient;
  readonly api = 'http://localhost:8080';

  Libros: any[] = [];
  Editoriales: any[] = [];
  Categorias: any[] = [];
  Libro: any = this.nuevoLibro();
  buscar: string = '';
  usuario: any = JSON.parse(localStorage.getItem('administrador') || '{}');

  constructor(http: HttpClient) {
    this.http = http;
    this.cargarLibros();
    this.http.get<any[]>(this.api + '/editorial/listaEditorial').subscribe(data => this.Editoriales = data);
    this.http.get<any[]>(this.api + '/Categoria/listaCategoria').subscribe(data => this.Categorias = data);
  }

  cargarLibros() {
    this.http.get<any[]>(this.api + '/Libro/listaLibro').subscribe(data => this.Libros = data);
  }

  nuevoLibro() {
    return { estado: 'A', editorial: {}, categoria: {} };
  }

  librosFiltrados() {
    let texto = this.buscar.toLowerCase();
    return this.Libros.filter(l => l.titulo.toLowerCase().includes(texto));
  }

  editar(libro: any) {
    this.Libro = { ...libro, editorial: { ...libro.editorial }, categoria: { ...libro.categoria } };
  }

  cancelar() {
    this.Libro = this.nuevoLibro();
  }

  guardar() {
    if (!this.Libro.titulo || !this.Libro.editorial.idEditorial || !this.Libro.categoria.idCategoria) {
      alert('Título, editorial y categoría son obligatorios');
      return;
    }
    if (vacio(this.Libro.anioPublicacion) || vacio(this.Libro.edicion) || vacio(this.Libro.numPaginas)) {
      alert('Año de publicación, edición y número de páginas son obligatorios');
      return;
    }

    // Jackson 3 usa el constructor de la entidad: los campos que no vienen llegan como null
    // y truenan en los int/char, por eso se manda la editorial completa y no solo el id
    let libro = {
      ...this.Libro,
      editorial: this.Editoriales.find(e => e.idEditorial == this.Libro.editorial.idEditorial),
      categoria: this.Categorias.find(c => c.idCategoria == this.Libro.categoria.idCategoria),
    };

    if (libro.idLibro) {
      this.http.put(this.api + '/Libro/update/' + libro.idLibro, libro).subscribe(data => this.guardado(data));
    } else {
      libro.fechaRegistrado = hoy();
      libro.registradoPor = { idUsuario: this.usuario.idUsuario };
      this.http.post(this.api + '/Libro/createLibro', libro).subscribe(data => this.guardado(data));
    }
  }

  guardado(data: any) {
    if (data) {
      alert('Libro guardado');
      this.Libro = this.nuevoLibro();
      this.cargarLibros();
    } else {
      alert('No se pudo guardar el libro');
    }
  }
}

export function vacio(valor: any) {
  return valor === null || valor === undefined || valor === '';
}

export function hoy() {
  let d = new Date();
  return d.getFullYear() + '-' + String(d.getMonth() + 1).padStart(2, '0') + '-' + String(d.getDate()).padStart(2, '0');
}
