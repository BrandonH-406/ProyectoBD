import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink,  } from '@angular/router';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-administrador',
  imports: [FormsModule, RouterLink, DatePipe],
  templateUrl: './administrador.html',
  styleUrl: './administrador.css',
})
export class Administrador {
  readonly http: HttpClient;

  readonly api = 'http://localhost:8080';

  usuario: any = JSON.parse(localStorage.getItem('administrador') || '{}');
  // Código de empleado, puesto y fecha de contratación están en per_biblioteca
  Personal: any = {};

  constructor(http: HttpClient) {
    this.http = http;
    this.http
      .get(this.api + '/PerBiblioteca/perBibliotecaId/' + this.usuario.idUsuario)
      .subscribe((data) => {
        if (data) {
          this.Personal = data;
        }
      });
  }

  logout() {
    localStorage.clear();
    location.href = '/';
  }
}
