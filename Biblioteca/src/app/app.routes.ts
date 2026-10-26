import { Routes } from '@angular/router';
import { Login } from './login/login';
import { Administrador } from './administrador/administrador';

export const routes: Routes = [
    { path: '', component: Login },
    { path: 'administrador', component: Administrador },
];
