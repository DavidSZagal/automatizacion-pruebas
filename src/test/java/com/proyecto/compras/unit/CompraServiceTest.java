package com.proyecto.compras.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.proyecto.compras.exception.CantidadInvalidaException;
import com.proyecto.compras.exception.ProductoInexistenteException;
import com.proyecto.compras.exception.StockInsuficienteException;
import com.proyecto.compras.model.Producto;
import com.proyecto.compras.service.CompraService;

class CompraServiceTest {

    private CompraService compraService;

    @BeforeEach
    void prepararPrueba() {
        compraService = new CompraService();
    }

    @Test
    @DisplayName("Debe calcular correctamente el total de una compra")
    void deberiaCalcularCorrectamenteElTotal() {
        // Arrange
        Producto producto = new Producto(
                1L, "Teclado", new BigDecimal("20000"), 10);

        // Act
        BigDecimal total = compraService.realizarCompra(producto, 2);

        // Assert
        assertEquals(new BigDecimal("40000"), total);
    }

    @Test
    @DisplayName("Debe reducir el stock después de una compra")
    void deberiaReducirElStockDespuesDeComprar() {
        // Arrange
        Producto producto = new Producto(
                1L, "Teclado", new BigDecimal("20000"), 10);

        // Act
        compraService.realizarCompra(producto, 3);

        // Assert
        assertEquals(7, producto.getStock());
    }

    @Test
    @DisplayName("Debe rechazar una cantidad igual a cero")
    void deberiaRechazarCantidadIgualACero() {
        // Arrange
        Producto producto = new Producto(
                1L, "Teclado", new BigDecimal("20000"), 10);

        // Act y Assert
        assertThrows(
                CantidadInvalidaException.class,
                () -> compraService.realizarCompra(producto, 0));
    }

    @Test
    @DisplayName("Debe rechazar una cantidad negativa")
    void deberiaRechazarCantidadNegativa() {
        // Arrange
        Producto producto = new Producto(
                1L, "Teclado", new BigDecimal("20000"), 10);

        // Act y Assert
        assertThrows(
                CantidadInvalidaException.class,
                () -> compraService.realizarCompra(producto, -2));
    }

    @Test
    @DisplayName("Debe rechazar una compra sin stock suficiente")
    void deberiaRechazarCompraSinStockSuficiente() {
        // Arrange
        Producto producto = new Producto(
                1L, "Teclado", new BigDecimal("20000"), 5);

        // Act y Assert
        assertThrows(
                StockInsuficienteException.class,
                () -> compraService.realizarCompra(producto, 8));
    }

    @Test
    @DisplayName("Debe rechazar un producto inexistente")
    void deberiaRechazarProductoInexistente() {
        // Arrange
        Producto producto = null;

        // Act y Assert
        assertThrows(
                ProductoInexistenteException.class,
                () -> compraService.realizarCompra(producto, 1));
    }

    @Test
    @DisplayName("Debe permitir comprar todo el stock disponible")
    void deberiaPermitirComprarTodoElStockDisponible() {
        // Arrange
        Producto producto = new Producto(
                1L, "Teclado", new BigDecimal("20000"), 5);

        // Act
        compraService.realizarCompra(producto, 5);

        // Assert
        assertEquals(0, producto.getStock());
    }
}