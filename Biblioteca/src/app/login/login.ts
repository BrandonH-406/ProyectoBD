import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {

  Administrador:any = {};
  Usuario: String = '';

  readonly http: HttpClient;

  constructor(http: HttpClient) {
    this.http = http;
  }

  login(){
        if(this.Administrador.username != null && this.Administrador.passwordHash != null){
      this.http.post("http://localhost:8080/Usuario/login",this.Administrador).subscribe(
        data => this.validar(data)
      )
    }
  }

    validar(data:any){
    if(data){
      console.log(data);
      localStorage.setItem("administrador",JSON.stringify(data));
      if(data.rol.nombre == "Administrador"){
        location.href="/administrador";
      } else if(data.rol.nombre == "Bibliotecario"){
        location.href="/bibliotecario";
      } else{
        localStorage.clear();
        alert("Este usuario no tiene acceso");
      }
    } else{
      alert("Usuario o Password invalido");
    }
  }

}
