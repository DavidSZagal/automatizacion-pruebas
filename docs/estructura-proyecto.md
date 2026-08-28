# Estructura y diseño del proyecto

## Objetivo

El proyecto se organiza mediante paquetes con responsabilidades específicas,
buscando mantener alta cohesión, bajo acoplamiento y pruebas unitarias
independientes.

## Estructura principal

```text
src/
├── main/
│   ├── java/com/proyecto/compras/
│   │   ├── exception/
│   │   │   ├── CantidadInvalidaException.java
│   │   │   ├── ProductoInexistenteException.java
│   │   │   └── StockInsuficienteException.java
│   │   ├── model/
│   │   │   └── Producto.java
│   │   ├── service/
│   │   │   └── CompraService.java
│   │   └── ComprasApplication.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/proyecto/compras/
        ├── unit/
        │   └── CompraServiceTest.java
        └── ComprasApplicationTests.java