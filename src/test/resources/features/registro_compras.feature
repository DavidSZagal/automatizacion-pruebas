@bdd @compras
Feature: Registro de compras
  Como cliente
  quiero comprar productos disponibles
  para completar una compra sin superar el stock existente

  @positivo
  Scenario: Compra aprobada con stock disponible
    Given que existe el producto "Teclado" con precio 25000 y stock 10
    When el cliente compra 2 unidades
    Then el resultado de la compra debe ser "APROBADA"
    And el total de la compra debe ser 50000
    And el stock restante debe ser 8

  @validaciones
  Scenario Outline: Rechazo de compras con cantidades inválidas o sin stock
    Given que existe el producto "Teclado" con precio 25000 y stock <stock>
    When el cliente compra <cantidad> unidades
    Then el resultado de la compra debe ser "<resultado>"
    And el stock restante debe ser <stock_final>

    Examples:
      | stock | cantidad | resultado           | stock_final |
      | 10    | 0        | CANTIDAD_INVALIDA   | 10          |
      | 10    | -2       | CANTIDAD_INVALIDA   | 10          |
      | 5     | 8        | STOCK_INSUFICIENTE  | 5           |

  @limite
  Scenario: Compra de todas las unidades disponibles
    Given que existe el producto "Monitor" con precio 120000 y stock 3
    When el cliente compra 3 unidades
    Then el resultado de la compra debe ser "APROBADA"
    And el total de la compra debe ser 360000
    And el stock restante debe ser 0

  @producto-inexistente
  Scenario: Rechazo de compra de un producto inexistente
    Given que el producto "Impresora" no existe
    When el cliente intenta comprar 1 unidad del producto
    Then el resultado de la compra debe ser "PRODUCTO_INEXISTENTE"