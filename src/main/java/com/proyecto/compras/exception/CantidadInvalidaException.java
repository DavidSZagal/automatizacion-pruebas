package com.proyecto.compras.exception;

public class CantidadInvalidaException extends RuntimeException {

    public CantidadInvalidaException(String mensaje) {
        super(mensaje);
    }
}