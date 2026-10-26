import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { hoy, vacio } from '../libros/libros';

@Component({
  selector: 'app-ejemplares',
  imports: [FormsModule],
  templateUrl: './ejemplares.html',
  styleUrl: '../formularios.css',
})
export class Ejemplares {
  readonly http: HttpClient;
  readonly api = 'http://localhost:8080';

  Ejemplares: any[] = [];
  Libros: any[] = [];
  Ubicaciones: any[] = [];
  Ejemplar: any = this.nuevoEjemplar();
  libroFiltro: any = '';

  constructor(http: HttpClient) {
    this.http = http;
    this.cargarEjemplares();
    this.http.get<any[]>(this.api + '/Libro/listaLibro').subscribe(data => this.Libros = data);
    this.http.get<any[]>(this.api + '/Ubicaion/listaUbicacion').subscribe(data => this.Ubicaciones = data);
  }

  cargarEjemplares() {
    this.http.get<any[]>(this.api + '/Ejemplar/listaEjemplares').subscribe(data => this.Ejemplares = data);
  }

  nuevoEjemplar() {
    return { estado: 'DISPONIBLE', condicion: 'NUEVO', fechaAdquisicion: hoy(), libro: {}, ubicacion: {} };
  }

  ejemplaresFiltrados() {
    if (!this.libroFiltro) {
      return this.Ejemplares;
    }
    return this.Ejemplares.filter(e => e.libro.idLibro == this.libroFiltro);
  }

  editar(ejemplar: any) {
    this.Ejemplar = { ...ejemplar, libro: { ...ejemplar.libro }, ubicacion: { ...ejemplar.ubicacion } };
  }

  cancelar() {
    this.Ejemplar = this.nuevoEjemplar();
  }

  guardar() {
    if (!this.Ejemplar.libro.idLibro || !this.Ejemplar.ubicacion.idUbicacion) {
      alert('Libro y ubicación son obligatorios');
      return;
    }
    if (vacio(this.Ejemplar.costo)) {
      alert('El costo es obligatorio');
      return;
    }

    // Igual que en libros: se mandan el libro y la ubicación completos para que Jackson no reciba null en los int/char
    let ejemplar = {
      ...this.Ejemplar,
      libro: this.Libros.find(l => l.idLibro == this.Ejemplar.libro.idLibro),
      ubicacion: this.Ubicaciones.find(u => u.idUbicacion == this.Ejemplar.ubicacion.idUbicacion),
    };

    if (ejemplar.idEjemplar) {
      this.http.put(this.api + '/Ejemplar/update/' + ejemplar.idEjemplar, ejemplar).subscribe(data => this.guardado(data));
    } else {
      ejemplar.fechaRegistro = hoy();
      this.http.post(this.api + '/Ejemplar/createEjemplar', ejemplar).subscribe(data => this.guardado(data));
    }
  }

  guardado(data: any) {
    if (data) {
      alert('Ejemplar guardado');
      this.Ejemplar = this.nuevoEjemplar();
      this.cargarEjemplares();
    } else {
      alert('No se pudo guardar el ejemplar');
    }
  }
}
