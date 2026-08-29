# Interpretación del dashboard de JMeter

## 1. Objetivo

Este documento explica las métricas obtenidas durante la prueba básica de rendimiento realizada con Apache JMeter sobre el endpoint de simulación de compras.

La prueba permite observar el comportamiento del servicio frente a solicitudes concurrentes y comprobar si cumple los criterios de aceptación definidos.

## 2. Configuración de la prueba

| Parámetro | Valor |
|---|---:|
| Usuarios virtuales | 20 |
| Ramp-up | 5 segundos |
| Repeticiones por usuario | 10 |
| Solicitudes totales | 200 |
| Método HTTP | POST |
| Endpoint | `/api/rendimiento/compras/simular?cantidad=2` |
| Código esperado | HTTP 200 |
| Entorno | Local |
| Herramienta | Apache JMeter 5.6.3 |

Los 20 usuarios virtuales se iniciaron progresivamente durante un periodo de 5 segundos. Cada usuario ejecutó 10 solicitudes, produciendo un total esperado de 200 solicitudes.

## 3. Resumen de resultados

| Métrica | Resultado |
|---|---:|
| Solicitudes ejecutadas | 200 |
| Solicitudes con error | 0 |
| Porcentaje de error | 0,00 % |
| Tiempo promedio | 1,07 ms |
| Tiempo mínimo | 0 ms |
| Tiempo máximo | 23 ms |
| Mediana | 1 ms |
| Percentil 90 | 2 ms |
| Percentil 95 | 2 ms |
| Percentil 99 | 3 ms |
| Throughput | 42,50 solicitudes por segundo |
| APDEX | 1,000 |
| Datos recibidos | 8,72 KB/s |
| Datos enviados | 10,09 KB/s |

## 4. Interpretación de las métricas

### 4.1 Cantidad de muestras

La métrica **Samples** representa la cantidad total de solicitudes procesadas por JMeter.

El resultado fue de 200 muestras, lo que coincide con la configuración de 20 usuarios virtuales y 10 repeticiones por usuario:

`20 usuarios × 10 repeticiones = 200 solicitudes`

Esto confirma que la prueba ejecutó completamente la carga planificada.

### 4.2 Porcentaje de errores

La métrica **Error %** indica qué porcentaje de solicitudes no cumplió correctamente su ejecución o validación.

El resultado fue de 0,00 %, con 0 errores de 200 solicitudes. Todas las solicitudes recibieron el código HTTP 200 esperado y superaron la aserción configurada en JMeter.

Este resultado cumple el criterio de aceptación establecido, que permite como máximo un 1 % de errores.

### 4.3 Tiempo promedio

La métrica **Average** muestra el tiempo promedio utilizado por el servicio para responder.

El promedio obtenido fue de 1,07 ms. Esto indica que, en el entorno local de la prueba, las respuestas fueron procesadas rápidamente.

Este valor no debe interpretarse como el rendimiento definitivo de un entorno productivo, porque la aplicación y JMeter se ejecutaron en el mismo equipo.

### 4.4 Tiempo mínimo y máximo

El tiempo mínimo registrado fue de 0 ms y el máximo fue de 23 ms.

El valor mínimo de 0 ms se debe a la precisión y al redondeo utilizado para registrar respuestas extremadamente rápidas; no significa necesariamente que la operación haya tomado literalmente cero tiempo.

El máximo de 23 ms fue considerablemente mayor que el promedio, pero continúa siendo bajo y se encuentra dentro de los límites definidos para esta prueba.

### 4.5 Mediana

La mediana fue de 1 ms. Esto significa que al menos el 50 % de las solicitudes respondió en 1 ms o menos.

La mediana es útil porque representa el comportamiento central de las respuestas y se ve menos afectada por valores extremos que el promedio.

### 4.6 Percentiles 90, 95 y 99

Los percentiles indican el tiempo máximo dentro del cual respondió un porcentaje determinado de solicitudes.

- Percentil 90 de 2 ms: el 90 % de las solicitudes respondió en 2 ms o menos.
- Percentil 95 de 2 ms: el 95 % respondió en 2 ms o menos.
- Percentil 99 de 3 ms: el 99 % respondió en 3 ms o menos.

El percentil 95 es especialmente importante porque permite analizar la experiencia de la mayoría de los usuarios sin depender solamente del promedio.

El criterio de aceptación establecía un percentil 95 inferior a 500 ms. El resultado de 2 ms cumple ampliamente ese límite.

### 4.7 Throughput

El **Throughput** representa la cantidad de solicitudes procesadas por unidad de tiempo.

El dashboard registró 42,50 solicitudes por segundo. Esto significa que durante esta ejecución JMeter procesó aproximadamente 42 solicitudes cada segundo.

La consola mostró aproximadamente 41,9 solicitudes por segundo. La pequeña diferencia se debe al cálculo y redondeo empleado en cada resumen.

Este resultado describe la ejecución realizada, pero no representa la capacidad máxima del sistema. Para conocer el límite real sería necesario aumentar la carga progresivamente y utilizar un entorno similar al de producción.

### 4.8 APDEX

El **APDEX**, o Application Performance Index, resume el nivel de satisfacción de las respuestas utilizando un valor entre 0 y 1.

Para este reporte se utilizaron los umbrales predeterminados:

- Respuesta satisfactoria: hasta 500 ms.
- Respuesta tolerable: hasta 1.500 ms.
- Respuesta frustrante: superior a 1.500 ms.

El resultado fue de 1,000, lo que indica que todas las solicitudes estuvieron dentro del tiempo considerado satisfactorio.

El criterio de aceptación requería un APDEX igual o superior a 0,95, por lo que la prueba cumple este requisito.

### 4.9 Tráfico de red

Las métricas de red muestran la cantidad de información recibida y enviada durante la prueba:

- Datos recibidos: 8,72 KB/s.
- Datos enviados: 10,09 KB/s.

Los valores son bajos porque el endpoint utiliza una solicitud pequeña y devuelve una respuesta JSON breve. Estas métricas serían más relevantes en operaciones que transfieran archivos o respuestas de mayor tamaño.

## 5. Gráficos principales del dashboard

### Response Times Over Time

Este gráfico muestra cómo cambian los tiempos de respuesta durante la ejecución y permite identificar aumentos, variaciones o periodos de degradación.

En esta prueba apareció un único punto cercano a 1,075 ms. Esto se debe a que la ejecución duró aproximadamente 5 segundos, mientras que el gráfico utilizó una granularidad de 1 minuto. Todas las respuestas quedaron agrupadas dentro del mismo intervalo.

El punto observado es consistente con el tiempo promedio general de 1,07 ms.

### Response Time Percentiles

Este gráfico presenta la distribución acumulada de los tiempos de respuesta y permite comprobar qué porcentaje de solicitudes se encuentra debajo de un tiempo determinado.

La curva obtenida muestra que:

- Una parte de las solicitudes fue registrada como 0 ms debido al redondeo.
- La mayoría respondió aproximadamente en 1 ms.
- El 90 % respondió en 2 ms o menos.
- El 95 % respondió en 2 ms o menos.
- El 99 % respondió en 3 ms o menos.
- Una cantidad mínima de solicitudes alcanzó el máximo de 23 ms.

El salto final de la curva representa esas pocas respuestas más lentas y no una degradación general del servicio.

### Transactions per Second

Este gráfico muestra cuántas transacciones fueron procesadas por segundo dentro de cada intervalo temporal.

El gráfico presentó aproximadamente 3,33 transacciones por segundo, mientras que la tabla Statistics mostró un throughput general de 42,50 solicitudes por segundo.

La diferencia se explica porque el gráfico utiliza una granularidad de 1 minuto y distribuyó las 200 solicitudes dentro de un intervalo de 60 segundos:

`200 solicitudes ÷ 60 segundos = 3,33 solicitudes por segundo`

En cambio, la tabla Statistics calcula el throughput utilizando la duración real de la ejecución, que fue de aproximadamente 5 segundos.

Debido a la corta duración de esta prueba, el gráfico no permite analizar una tendencia temporal de throughput. Para hacerlo sería necesario ejecutar la carga durante varios minutos.

### Active Threads Over Time

Este gráfico representa la cantidad de usuarios virtuales activos durante la ejecución.

Aunque se configuraron 20 usuarios virtuales, el gráfico mostró aproximadamente 1 hilo activo en el punto registrado. Los usuarios se iniciaron progresivamente durante 5 segundos y cada uno completó sus 10 solicitudes muy rápidamente.

Por esta razón, un usuario podía finalizar sus iteraciones antes de que comenzara el siguiente. La configuración de 20 usuarios representa el total de hilos creados, pero no garantiza que los 20 estuvieran activos simultáneamente.

Para evaluar concurrencia real se debería utilizar un ramp-up más corto, una prueba de mayor duración o pausas controladas que mantengan los hilos activos durante más tiempo.

### Response Time Distribution

Este gráfico agrupa las solicitudes según sus tiempos de respuesta.

Las 200 respuestas quedaron agrupadas dentro del intervalo de 0 a 100 ms. Esto se debe a que JMeter utiliza intervalos de distribución de 100 ms y el tiempo máximo registrado fue de solamente 23 ms.

El gráfico no indica que todas las respuestas hayan tardado 100 ms. Indica que todas estuvieron por debajo de ese límite.

### Errors

Esta sección resume los tipos y cantidades de errores detectados.

En esta ejecución no aparecieron registros en las tablas Errors ni Top 5 Errors by Sampler, porque las 200 solicitudes fueron exitosas y superaron la validación HTTP 200.

## 6. Evaluación de los criterios de aceptación

| Criterio | Límite definido | Resultado | Estado |
|---|---:|---:|---|
| Porcentaje de errores | Igual o inferior a 1 % | 0,00 % | Cumple |
| Percentil 95 | Inferior a 500 ms | 2 ms | Cumple |
| APDEX | Igual o superior a 0,95 | 1,000 | Cumple |
| Respuestas HTTP correctas | 100 % | 100 % | Cumple |

## 7. Conclusión

La prueba básica de rendimiento cumplió todos los criterios de aceptación definidos. Las 200 solicitudes fueron procesadas correctamente, sin errores, con un percentil 95 de 2 ms y un APDEX de 1,000.

Los resultados indican un comportamiento estable y rápido bajo la carga básica configurada. Sin embargo, corresponden a un entorno local y a un endpoint de simulación, por lo que no deben considerarse como una medición de la capacidad máxima de un sistema productivo.

Como trabajo futuro se recomienda ejecutar pruebas con mayor cantidad de usuarios, diferentes niveles de carga, una duración más extensa y un entorno separado para JMeter y la aplicación.

## 8. Referencia

- Apache JMeter. [Generating Report Dashboard](https://jmeter.apache.org/usermanual/generating-dashboard.html).