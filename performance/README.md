# Prueba básica de rendimiento con Apache JMeter

## Objetivo

Evaluar el comportamiento del registro de compras ante múltiples
solicitudes concurrentes y comprobar que el servicio mantenga tiempos
de respuesta bajos y no genere errores.

## Herramienta

- Apache JMeter 5.6.3
- Java 17
- Spring Boot 4.1.1
- Entorno local Windows 10
- Endpoint: `POST /api/rendimiento/compras/simular?cantidad=2`

## Configuración de la carga

| Parámetro | Valor |
|---|---:|
| Usuarios virtuales | 20 |
| Ramp-up | 5 segundos |
| Repeticiones por usuario | 10 |
| Solicitudes esperadas | 200 |
| Respuesta esperada | HTTP 200 |

El plan se encuentra en `performance/compra-service.jmx`.

## Comando utilizado

```bash
jmeter -n \
  -t performance/compra-service.jmx \
  -l target/jmeter/resultados-final.jtl \
  -e \
  -o target/jmeter/reporte-final
  ```