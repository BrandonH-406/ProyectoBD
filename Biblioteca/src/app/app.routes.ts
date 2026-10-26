import { Routes } from '@angular/router';
import { Login } from './login/login';
import { Administrador } from './administrador/administrador';
import { GestionEmpleados } from './gestion-empleados/gestion-empleados';
import { HistorialLibros } from './historial-libros/historial-libros';
import { HistorialPrestamos } from './historial-prestamos/historial-prestamos';
import { Auditoria } from './auditoria/auditoria';

export const routes: Routes = [
    { path: '', component: Login },
    { path: 'administrador', component: Administrador },
    { path: 'gestion-empleados', component: GestionEmpleados },
    { path: 'historial-libros', component: HistorialLibros },
    { path: 'historial-prestamos', component: HistorialPrestamos },
    { path: 'auditoria', component: Auditoria }
];
