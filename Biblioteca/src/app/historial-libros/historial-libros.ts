import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-historial-libros',
  imports: [FormsModule, RouterLink, CommonModule],
  templateUrl: './historial-libros.html',
  styleUrl: './historial-libros.css',
})
export class HistorialLibros implements OnInit {
  private readonly api = 'http://localhost:8080';

  readonly http: HttpClient;
  usuarioActual: any = {};

  listaLibros: any[] = [];
  Libro: any = this.nuevoLibro();
  errorLibro = '';

  ListaCategorias: any[] = [];
  ListaEditorial: any[] = [];

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
    }
  }

  // ---------------------------------------------------------------
  // Utilidades
  // ---------------------------------------------------------------

  /** Fecha de hoy en formato YYYY-MM-DD (hora local). */
  private hoy(): string {
    return new Date().toLocaleDateString('en-CA');
  }

  private irAlFormulario(id: string) {
    document.getElementById(id)?.scrollIntoView({ behavior: 'smooth', block: 'start' });
  }

  /**
   * Para que el <select> preseleccione la categoría/editorial al editar
   * (el objeto copiado no es la misma referencia que el de la lista).
   */
  compararPorNombre(a: any, b: any): boolean {
    return a && b ? a.nombre === b.nombre : a === b;
  }

  // ---------------------------------------------------------------
  // CARGA DE DATOS
  // ---------------------------------------------------------------

  buscarLibros() {
    this.http.get<any[]>(`${this.api}/Libro/listaLibro`).subscribe({
      next: (data) => (this.listaLibros = data ?? []),
      error: (err) => console.error('Error al listar libros:', err),
    });
  }

  buscarCategorias() {
    this.http.get<any[]>(`${this.api}/Categoria/listaCategoria`).subscribe({
      next: (data) => (this.ListaCategorias = data ?? []),
      error: (err) => console.error('Error al cargar categorías:', err),
    });
  }

  buscarEditorial() {
    this.http.get<any[]>(`${this.api}/editorial/listaEditorial`).subscribe({
      next: (data) => (this.ListaEditorial = data ?? []),
      error: (err) => console.error('Error al cargar editoriales:', err),
    });
  }

  // ---------------------------------------------------------------
  // CREAR / EDITAR
  // ---------------------------------------------------------------

  private validarLibro(): string {
    const l = this.Libro;
    if (!l.titulo || !l.anioPublicacion || !l.edicion || !l.numPaginas || !l.categoria || !l.editorial) {
      return 'Completa título, año de publicación, edición, número de páginas, categoría y editorial.';
    }
    if (!/^[0-9]{4}$/.test(String(l.anioPublicacion))) {
      return 'El año de publicación debe tener 4 dígitos.';
    }
    if (Number(l.numPaginas) <= 0) {
      return 'El número de páginas debe ser mayor que cero.';
    }
    return '';
  }

  guardarLibro() {
    this.errorLibro = this.validarLibro();
    if (this.errorLibro) return;

    if (this.Libro.idLibro) {
      // ----- EDICIÓN -----
      this.http.put(`${this.api}/Libro/update/${this.Libro.idLibro}`, this.Libro).subscribe({
        next: (data) => this.finalizarGuardar(data, 'actualizado'),
        error: (err) => this.fallaLibro(err),
      });
    } else {
      // ----- CREACIÓN -----
      const hoy = this.hoy();
      const nuevo = {
        ...this.Libro,
        // Se envían ambos nombres por si tu entidad usa uno u otro; deja solo el correcto.
        fechaRegistro: hoy,
        fechaRegistrado: hoy,
        registradoPor: { idUsuario: this.usuarioActual.idUsuario },
      };

      this.http.post(`${this.api}/Libro/createLibro`, nuevo).subscribe({
        next: (data) => this.finalizarGuardar(data, 'creado'),
        error: (err) => this.fallaLibro(err),
      });
    }
  }

  private finalizarGuardar(data: any, accion: string) {
    if (data) {
      alert(`Libro ${accion} con éxito`);
      this.cancelar();
      this.buscarLibros();
    } else {
      this.errorLibro = 'No se pudo guardar el libro. Es posible que ya exista o que no se haya encontrado.';
    }
  }

  private fallaLibro(err: any) {
    console.error('Error al guardar libro:', err);
    this.errorLibro =
      'No se pudo guardar el libro. Revisa los datos e intenta de nuevo (el usuario en sesión debe ser un empleado registrado).';
  }

  editar(libro: any) {
    this.Libro = {
      ...libro,
      editorial: libro.editorial ? { ...libro.editorial } : null,
      categoria: libro.categoria ? { ...libro.categoria } : null,
    };
    this.errorLibro = '';
    this.irAlFormulario('form-registro');
  }

  nuevoLibro() {
    return { estado: 'A', editorial: null, categoria: null };
  }

  cancelar() {
    this.Libro = this.nuevoLibro();
    this.errorLibro = '';
  }

  // ---------------------------------------------------------------
  // ELIMINAR
  // ---------------------------------------------------------------

  Eliminar(libro: any) {
    if (!libro?.idLibro) return;
    if (!confirm(`¿Seguro que deseas eliminar el libro "${libro.titulo}"?`)) return;

    this.http.delete(`${this.api}/Libro/deleteLibro/${libro.idLibro}`).subscribe({
      next: () => {
        alert('Libro eliminado con éxito');
        if (this.Libro.idLibro === libro.idLibro) {
          this.cancelar();
        }
        this.buscarLibros();
      },
      error: (err) => {
        console.error('Error al eliminar libro:', err);
        alert('No se pudo eliminar el libro. Puede tener ejemplares o préstamos asociados.');
      },
    });
  }

  logout() {
    localStorage.clear();
    location.href = '/';
  }
}