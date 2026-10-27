import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { Observable } from 'rxjs';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-auditoria',
  imports: [FormsModule, RouterLink, CommonModule],
  templateUrl: './auditoria.html',
  styleUrl: './auditoria.css',
})
export class Auditoria {
  ListaBitacora: any = [];
  ListaBitacoraAcceso: any = [];
  readonly http: HttpClient;
  constructor(http: HttpClient) {
    let usuario: any = localStorage.getItem('administrador');
    this.http = http;

    if (usuario) {
      this.buscarAuditoria();
      this.buscarAcesso();
    } else {
      location.href = '';
    }
  }

  servicioBuscarAuditoria(): Observable<any> {
    return this.http.get<any>('http://localhost:8080/Bitacora/listaBitacoras');
  }

  buscarAuditoria() {
    this.servicioBuscarAuditoria().subscribe((data) => (this.ListaBitacora = data));
  }

  servicioBuscarAcesso(): Observable<any> {
    return this.http.get<any>('http://localhost:8080/BitaAcceso/listaBitaAccesos');
  }

  buscarAcesso() {
    this.servicioBuscarAcesso().subscribe((data) => (this.ListaBitacoraAcceso = data));
  }

  logout() {
    localStorage.clear();
    location.href = '/';
  }
}
