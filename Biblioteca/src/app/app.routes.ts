import { Routes } from '@angular/router';
import { Login } from './login/login';
import { Administrador } from './administrador/administrador';
import { Bibliotecario } from './bibliotecario/bibliotecario';
import { Libros } from './bibliotecario/libros/libros';
import { Ejemplares } from './bibliotecario/ejemplares/ejemplares';
import { Prestamos } from './bibliotecario/prestamos/prestamos';
import { Renovaciones } from './bibliotecario/renovaciones/renovaciones';
import { Devoluciones } from './bibliotecario/devoluciones/devoluciones';
import { Historial } from './bibliotecario/historial/historial';
import { Perfil } from './bibliotecario/perfil/perfil';
import { GestionEmpleados } from './gestion-empleados/gestion-empleados';
import { HistorialLibros } from './historial-libros/historial-libros';
import { HistorialPrestamos } from './historial-prestamos/historial-prestamos';
import { Auditoria } from './auditoria/auditoria';
import { GestionUsuarios } from './gestion-usuarios/gestion-usuarios';

export const routes: Routes = [
    { path: '', component: Login },
    { path: 'administrador', component: Administrador },
    {
        path: 'bibliotecario', component: Bibliotecario, children: [
            { path: '', redirectTo: 'libros', pathMatch: 'full' },
            { path: 'libros', component: Libros },
            { path: 'ejemplares', component: Ejemplares },
            { path: 'prestamos', component: Prestamos },
            { path: 'renovaciones', component: Renovaciones },
            { path: 'devoluciones', component: Devoluciones },
            { path: 'historial', component: Historial },
            { path: 'perfil', component: Perfil },
        ]
    },
    { path: 'gestion-empleados', component: GestionEmpleados },
    { path: 'historial-libros', component: HistorialLibros },
    { path: 'historial-prestamos', component: HistorialPrestamos },
    { path: 'auditoria', component: Auditoria },
    { path: 'gestion-usuarios', component: GestionUsuarios }
];