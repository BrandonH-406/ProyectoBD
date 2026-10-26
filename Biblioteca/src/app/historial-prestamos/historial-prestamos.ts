import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-historial-prestamos',
  imports: [FormsModule,RouterLink],
  templateUrl: './historial-prestamos.html',
  styleUrl: './historial-prestamos.css',
})
export class HistorialPrestamos {
  readonly http: HttpClient;
  constructor(http: HttpClient) {
    let usuario: any = localStorage.getItem('administrador');
    this.http = http;
  }
}
