package com.biblioteca.bibliotecabd.Model.Dto;

import java.util.List;

// Datos que manda Angular para crear un préstamo (Integer y no int: con Jackson 3 un int faltante truena)
public record PrestamoRequest(String codigo, Integer idBibliotecario, List<Integer> ejemplares, String observaciones) {
}
