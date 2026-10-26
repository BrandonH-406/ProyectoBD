import { hoy } from './libros/libros';

// Funciones compartidas por Préstamos, Renovaciones, Devoluciones e Historial

// El backend (/Operacion) responde 400 { mensaje } cuando una regla no se cumple
export function mensajeError(e: any) {
  if (e.status == 0) {
    return 'No hay conexión con el servidor';
  }
  return e.error?.mensaje || 'Ocurrió un error en el servidor';
}

// Las fechas vienen como 'YYYY-MM-DD', así que se pueden comparar como texto
export function estadoDetalle(detalle: any) {
  if (detalle.estado == 'DEVUELTO') {
    return 'DEVUELTO';
  }
  return detalle.fechaDevolucionPrevista < hoy() ? 'VENCIDO' : 'VIGENTE';
}

export function diasAtraso(detalle: any) {
  let dias = (new Date(hoy()).getTime() - new Date(detalle.fechaDevolucionPrevista).getTime()) / 86400000;
  return Math.max(0, Math.round(dias));
}
