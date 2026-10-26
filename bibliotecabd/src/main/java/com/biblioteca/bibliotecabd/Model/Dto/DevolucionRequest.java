package com.biblioteca.bibliotecabd.Model.Dto;

// Datos que manda Angular para registrar una devolución
public record DevolucionRequest(Integer idBibliotecario, String condicion, String observaciones) {
}
