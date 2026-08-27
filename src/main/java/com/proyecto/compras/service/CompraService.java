package com.proyecto.compras.service;

import java.math.BigDecimal;

import com.proyecto.compras.exception.CantidadInvalidaException;
import com.proyecto.compras.exception.ProductoInexistenteException;
import com.proyecto.compras.exception.StockInsuficienteException;
import com.proyecto.compras.model.Producto;

public class CompraService {

    public BigDecimal realizarCompra(Producto producto, int cantidad) {

        if (producto == null) {
            throw new ProductoInexistenteException(
                    "El producto no existe");
        }

        if (cantidad <= 0) {
            throw new CantidadInvalidaException(
                    "La cantidad debe ser mayor que cero");
        }

        if (cantidad > producto.getStock()) {
            throw new StockInsuficienteException(
                    "No existe stock suficiente");
        }

        BigDecimal total = producto.getPrecio()
                .multiply(BigDecimal.valueOf(cantidad));

        producto.reducirStock(cantidad);

        return total;
    }
}