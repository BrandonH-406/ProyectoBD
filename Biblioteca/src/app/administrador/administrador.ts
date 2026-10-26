import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-administrador',
  imports: [FormsModule],
  templateUrl: './administrador.html',
  styleUrl: './administrador.css',
})
export class Administrador {
  readonly http: HttpClient;
  constructor(http: HttpClient) {
    let usuario: any = localStorage.getItem('administrador');
    this.http = http;
  }
}
