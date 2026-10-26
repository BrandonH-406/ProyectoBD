import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('Biblioteca');

    Administradorexiste: boolean = false;
  
  constructor(){

    let usuario:any = localStorage.getItem("administrador");
    this.Administradorexiste =usuario?true:false;


  }

  logout(){
    localStorage.clear();
  }
}
