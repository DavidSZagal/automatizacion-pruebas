package com.proyecto.compras.model;

import java.math.BigDecimal;

public class Producto {

    private final Long id;
    private final String nombre;
    private final BigDecimal precio;
    private int stock;

    public Producto(Long id, String nombre, BigDecimal precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public void reducirStock(int cantidad) {
        this.stock -= cantidad;
    }
}