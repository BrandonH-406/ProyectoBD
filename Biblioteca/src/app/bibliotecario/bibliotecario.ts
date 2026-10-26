import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-bibliotecario',
  imports: [FormsModule, RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './bibliotecario.html',
  styleUrl: './bibliotecario.css',
})
export class Bibliotecario {
  readonly http: HttpClient;
  Bibliotecario: any = {};

  constructor(http: HttpClient) {
    let usuario: any = localStorage.getItem('administrador');
    this.http = http;
    if (usuario) {
      this.Bibliotecario = JSON.parse(usuario);
    } else {
      location.href = '/';
    }
  }

  logout() {
    localStorage.clear();
    location.href = '/';
  }
}
