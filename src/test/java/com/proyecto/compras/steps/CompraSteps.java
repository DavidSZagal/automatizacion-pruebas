package com.proyecto.compras.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import com.proyecto.compras.exception.CantidadInvalidaException;
import com.proyecto.compras.exception.ProductoInexistenteException;
import com.proyecto.compras.exception.StockInsuficienteException;
import com.proyecto.compras.model.Producto;
import com.proyecto.compras.service.CompraService;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CompraSteps {

    private final CompraService compraService = new CompraService();

    private Producto producto;
    private BigDecimal total;
    private String resultado;

    @Given("que existe el producto {string} con precio {int} y stock {int}")
    public void existeProducto(
            String nombre,
            int precio,
            int stock) {

        producto = new Producto(
                1L,
                nombre,
                BigDecimal.valueOf(precio),
                stock);
    }

    @Given("que el producto {string} no existe")
    public void productoNoExiste(String nombre) {
        producto = null;
    }

    @When("el cliente compra {int} unidades")
    public void clienteCompraUnidades(int cantidad) {
        ejecutarCompra(cantidad);
    }

    @When("el cliente intenta comprar {int} unidad del producto")
    public void clienteIntentaComprar(int cantidad) {
        ejecutarCompra(cantidad);
    }

    private void ejecutarCompra(int cantidad) {
        try {
            total = compraService.realizarCompra(producto, cantidad);
            resultado = "APROBADA";
        } catch (CantidadInvalidaException exception) {
            resultado = "CANTIDAD_INVALIDA";
        } catch (StockInsuficienteException exception) {
            resultado = "STOCK_INSUFICIENTE";
        } catch (ProductoInexistenteException exception) {
            resultado = "PRODUCTO_INEXISTENTE";
        }
    }

    @Then("el resultado de la compra debe ser {string}")
    public void verificarResultado(String resultadoEsperado) {
        assertEquals(resultadoEsperado, resultado);
    }

    @Then("el total de la compra debe ser {int}")
    public void verificarTotal(int totalEsperado) {
        assertEquals(
                BigDecimal.valueOf(totalEsperado),
                total);
    }

    @Then("el stock restante debe ser {int}")
    public void verificarStock(int stockEsperado) {
        assertEquals(stockEsperado, producto.getStock());
    }
}