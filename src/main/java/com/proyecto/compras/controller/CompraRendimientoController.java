package com.proyecto.compras.controller;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.compras.model.Producto;
import com.proyecto.compras.service.CompraService;

@Profile("performance")
@RestController
@RequestMapping("/api/rendimiento/compras")
public class CompraRendimientoController {

    private final CompraService compraService = new CompraService();

    @PostMapping("/simular")
    public Map<String, Object> simularCompra(
            @RequestParam(defaultValue = "2") int cantidad) {

        Producto producto = new Producto(
                1L,
                "Teclado",
                new BigDecimal("25000"),
                10);

        BigDecimal total = compraService.realizarCompra(
                producto,
                cantidad);

        return Map.of(
                "resultado", "APROBADA",
                "total", total,
                "stockRestante", producto.getStock());
    }
}