package com.proyecto.compras.exception;

public class ProductoInexistenteException extends RuntimeException {

    public ProductoInexistenteException(String mensaje) {
        super(mensaje);
    }
}