import { DatePipe } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';

@Component({
  selector: 'app-perfil',
  imports: [DatePipe],
  templateUrl: './perfil.html',
  styleUrl: '../formularios.css',
})
export class Perfil {
  readonly http: HttpClient;
  readonly api = 'http://localhost:8080';

  usuario: any = JSON.parse(localStorage.getItem('administrador') || '{}');
  // Código de empleado, puesto y fecha de contratación están en per_biblioteca
  Personal: any = {};

  constructor(http: HttpClient) {
    this.http = http;
    this.http.get(this.api + '/PerBiblioteca/perBibliotecaId/' + this.usuario.idUsuario).subscribe(data => {
      if (data) {
        this.Personal = data;
      }
    });
  }
}
