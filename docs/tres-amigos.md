# Sesión Three Amigos: registro de una compra

## Información de la sesión

- Proyecto: Automatización de pruebas de compras.
- Funcionalidad analizada: Registro de una compra.
- Modalidad: Simulación académica de una sesión Three Amigos.
- Participante: David Sandoval, representando los roles de negocio,
  desarrollo y QA.

## Objetivo

Definir colaborativamente el comportamiento esperado del registro de una
compra antes de implementar los escenarios automatizados con Cucumber.

## Historia de usuario

Como cliente,
quiero comprar una cantidad determinada de un producto,
para adquirir productos disponibles sin superar el stock existente.

## Roles participantes

| Rol | Responsabilidad durante la sesión |
|---|---|
| Negocio / Product Owner | Explicar la necesidad y definir el valor de la funcionalidad |
| Desarrollo | Analizar la implementación y las restricciones técnicas |
| QA | Proponer ejemplos, casos límite y resultados verificables |

## Perspectiva de negocio

El cliente debe poder comprar un producto existente siempre que solicite
una cantidad válida y exista stock suficiente.

Una compra aprobada debe calcular correctamente el total y descontar del
inventario solamente las unidades compradas.

Las solicitudes inválidas deben rechazarse sin modificar el stock.

## Perspectiva de desarrollo

El servicio de compras recibirá un producto y una cantidad solicitada.

Antes de aprobar la operación deberá comprobar:

1. Que el producto exista.
2. Que la cantidad sea mayor que cero.
3. Que exista stock suficiente.
4. Que el total corresponda al precio multiplicado por la cantidad.

La lógica permanecerá dentro de `CompraService`. Las definiciones de pasos
de Cucumber utilizarán este servicio real y no duplicarán sus reglas.

## Perspectiva de QA

QA propone comprobar tanto el flujo exitoso como los siguientes límites:

- Cantidad igual a cero.
- Cantidad negativa.
- Cantidad superior al stock.
- Cantidad exactamente igual al stock.
- Producto inexistente.
- Cálculo correcto del total.
- Conservación del stock cuando la compra es rechazada.

## Reglas de negocio

1. El producto debe existir.
2. La cantidad solicitada debe ser mayor que cero.
3. La cantidad solicitada no puede superar el stock disponible.
4. El total se calcula multiplicando el precio por la cantidad.
5. Una compra aprobada debe reducir el stock.
6. Una compra rechazada no debe modificar el stock.
7. Comprar una cantidad exactamente igual al stock debe estar permitido.
8. El resultado debe ser determinista y repetible.

## Criterios de aceptación

### Criterio 1: compra aprobada

Dado que existe un producto con stock suficiente,
cuando el cliente solicita una cantidad válida,
entonces la compra debe aprobarse,
el total debe calcularse correctamente
y el stock debe disminuir.

### Criterio 2: cantidad inválida

Dado que existe un producto,
cuando el cliente solicita una cantidad igual o menor que cero,
entonces la compra debe rechazarse por cantidad inválida
y el stock debe conservarse.

### Criterio 3: stock insuficiente

Dado que existe un producto,
cuando el cliente solicita una cantidad superior al stock,
entonces la compra debe rechazarse por stock insuficiente
y el inventario no debe modificarse.

### Criterio 4: producto inexistente

Dado que el producto solicitado no existe,
cuando el cliente intenta realizar una compra,
entonces la operación debe rechazarse
y se debe informar que el producto no existe.

### Criterio 5: compra de todo el stock

Dado que la cantidad solicitada es igual al stock disponible,
cuando el cliente realiza la compra,
entonces la operación debe aprobarse
y el stock restante debe ser cero.

## Ejemplos discutidos

| Producto | Precio | Stock | Cantidad | Resultado esperado | Stock final |
|---|---:|---:|---:|---|---:|
| Teclado | 25000 | 10 | 2 | Compra aprobada, total 50000 | 8 |
| Teclado | 25000 | 10 | 0 | Cantidad inválida | 10 |
| Teclado | 25000 | 10 | -2 | Cantidad inválida | 10 |
| Mouse | 15000 | 5 | 8 | Stock insuficiente | 5 |
| Monitor | 120000 | 3 | 3 | Compra aprobada, total 360000 | 0 |
| Producto inexistente | 0 | 0 | 1 | Producto inexistente | 0 |

## Preguntas discutidas

### ¿Se permite comprar cero unidades?

No. La cantidad debe ser estrictamente mayor que cero.

### ¿Se permite comprar una cantidad igual al stock?

Sí. La compra se aprueba y el stock queda en cero.

### ¿Debe cambiar el stock cuando ocurre un error?

No. El stock solamente se modifica después de superar todas las
validaciones.

### ¿Dónde se implementarán las reglas?

Las reglas permanecerán en `CompraService`. Cucumber solamente preparará
los datos, ejecutará el servicio y verificará los resultados.

### ¿Cómo se representarán los errores?

Se utilizarán los resultados o excepciones ya definidos en el proyecto:

- `CantidadInvalidaException`
- `StockInsuficienteException`
- `ProductoInexistenteException`

## Decisiones tomadas

- Utilizar Java 17, JUnit 5 y Cucumber.
- Escribir los escenarios en español.
- Reutilizar el servicio real del proyecto.
- Mantener los escenarios independientes.
- Reiniciar el contexto antes de cada escenario.
- Incluir al menos un `Scenario` y un `Scenario Outline`.
- Generar un reporte HTML navegable de Cucumber.
- Ejecutar los escenarios automáticamente con `mvn verify`.
- Integrar las pruebas BDD al pipeline de GitHub Actions.

## Criterio de finalización

La funcionalidad se considerará terminada cuando:

- Los escenarios de compra válida e inválida estén automatizados.
- El `Scenario Outline` ejecute todos sus ejemplos.
- Todas las pruebas unitarias continúen aprobadas.
- El reporte HTML de Cucumber sea generado.
- El pipeline de integración continua termine correctamente.