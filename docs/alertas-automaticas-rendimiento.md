# Plan de alertas automáticas de rendimiento

## 1. Objetivo

Este documento describe cómo configurar alertas automáticas para detectar degradaciones en el rendimiento del servicio de compras.

El propósito es que una ejecución automática pueda comparar las métricas obtenidas por JMeter con límites previamente definidos. Si una métrica supera su límite, GitHub Actions debe mostrar una alerta, marcar el workflow como fallido y notificar al equipo responsable.

## 2. Métricas supervisadas

Las alertas se basarán en las métricas principales del dashboard de Apache JMeter.

| Métrica | Qué permite detectar |
|---|---|
| Error % | Solicitudes fallidas o respuestas que no cumplen la validación |
| Percentil 95 | Degradación experimentada por la mayoría de los usuarios |
| APDEX | Disminución general de la satisfacción del usuario |
| Respuestas HTTP correctas | Respuestas que no cumplen el código HTTP esperado |
| Throughput | Reducción en la cantidad de solicitudes procesadas |
| Tiempo máximo | Respuestas aisladas excesivamente lentas |

## 3. Umbrales definidos

Los límites principales se basan en los criterios de aceptación de la prueba de rendimiento.

| Métrica | Estado correcto | Advertencia | Alerta crítica |
|---|---:|---:|---:|
| Error % | Igual o inferior a 0,50 % | Superior a 0,50 % y hasta 1 % | Superior a 1 % |
| Percentil 95 | Inferior a 400 ms | Desde 400 ms y menor que 500 ms | Igual o superior a 500 ms |
| APDEX | Igual o superior a 0,97 | Desde 0,95 y menor que 0,97 | Inferior a 0,95 |
| Respuestas HTTP correctas | 100 % | No aplica | Inferior a 100 % |

El throughput y el tiempo máximo deben compararse con un historial de ejecuciones. Una variación aislada puede estar relacionada con el entorno, mientras que una degradación repetida puede indicar un problema real.

## 4. Niveles de alerta

### Estado correcto

La ejecución cumple todos los criterios. GitHub Actions muestra el workflow en color verde y permite continuar con la integración.

### Advertencia

La métrica se acerca al límite crítico. El workflow puede continuar, pero debe mostrar una anotación amarilla y registrar el resultado en el resumen de la ejecución.

Ejemplos:

- Error de 0,70 %.
- Percentil 95 de 450 ms.
- APDEX de 0,96.

### Alerta crítica

Una o más métricas incumplen los criterios de aceptación. GitHub Actions debe:

1. Mostrar una anotación de error.
2. Marcar el job como fallido.
3. Bloquear la fusión si el check es obligatorio.
4. Conservar los resultados y el dashboard como artefactos.
5. Notificar a los responsables.

Ejemplos:

- Error superior a 1 %.
- Percentil 95 igual o superior a 500 ms.
- APDEX inferior a 0,95.
- Una respuesta distinta de HTTP 200.

## 5. Flujo automático propuesto

1. GitHub Actions inicia la prueba de rendimiento.
2. Spring Boot se ejecuta con el perfil `performance`.
3. JMeter ejecuta el archivo `performance/compra-service.jmx`.
4. Se genera el archivo de resultados JTL y el dashboard HTML.
5. Un paso automático obtiene Error %, percentil 95 y APDEX.
6. Las métricas se comparan con los umbrales definidos.
7. Si se detecta una advertencia, se genera una anotación amarilla.
8. Si se detecta una alerta crítica, el workflow finaliza con error.
9. Los reportes se publican como artefactos para su revisión.
10. GitHub envía la notificación según la configuración del usuario.

## 6. Ejemplo conceptual en GitHub Actions

El siguiente ejemplo representa el paso encargado de comparar las métricas. Antes de ejecutarlo, otro paso debe extraer los valores del archivo JTL o del reporte de JMeter y almacenarlos en variables de entorno.

```yaml
- name: Validar umbrales de rendimiento
  shell: bash
  run: |
    estado=0

    if awk "BEGIN { exit !(${ERROR_RATE} > 1) }"; then
      echo "::error title=Porcentaje de errores elevado::Error ${ERROR_RATE}% superior al límite de 1%"
      estado=1
    elif awk "BEGIN { exit !(${ERROR_RATE} > 0.5) }"; then
      echo "::warning title=Porcentaje de errores en advertencia::Error ${ERROR_RATE}% cercano al límite"
    fi

    if awk "BEGIN { exit !(${P95} >= 500) }"; then
      echo "::error title=Percentil 95 elevado::P95 de ${P95} ms incumple el límite de 500 ms"
      estado=1
    elif awk "BEGIN { exit !(${P95} >= 400) }"; then
      echo "::warning title=Percentil 95 en advertencia::P95 de ${P95} ms cercano al límite"
    fi

    if awk "BEGIN { exit !(${APDEX} < 0.95) }"; then
      echo "::error title=APDEX insuficiente::APDEX ${APDEX} inferior al mínimo de 0.95"
      estado=1
    elif awk "BEGIN { exit !(${APDEX} < 0.97) }"; then
      echo "::warning title=APDEX en advertencia::APDEX ${APDEX} cercano al mínimo"
    fi

    {
      echo "## Validación de rendimiento"
      echo ""
      echo "| Métrica | Resultado |"
      echo "|---|---:|"
      echo "| Error | ${ERROR_RATE}% |"
      echo "| Percentil 95 | ${P95} ms |"
      echo "| APDEX | ${APDEX} |"
    } >> "$GITHUB_STEP_SUMMARY"

    exit "$estado" 
```

## 7. Ejecuciones que deben generar alertas

La validación de rendimiento puede ejecutarse en los siguientes momentos:

| Evento | Finalidad |
|---|---|
| Pull request hacia `develop` | Detectar una degradación antes de integrar cambios |
| Push en `develop` | Verificar el estado después de una fusión |
| Ejecución programada | Detectar cambios producidos por el entorno o las dependencias |
| Ejecución manual | Comprobar una corrección o investigar un incidente |

Para una prueba de rendimiento más extensa se recomienda utilizar una ejecución programada. Esto evita aumentar excesivamente el tiempo de todos los pull requests.

## 8. Canales de notificación

### GitHub Actions

Cuando el workflow falla, GitHub muestra el check en color rojo y permite revisar el paso exacto que generó la alerta.

### Notificación web o por correo

Cada integrante puede configurar GitHub para recibir notificaciones web o por correo electrónico. También puede seleccionar la opción que notifica solamente cuando un workflow falla.

### Slack o Microsoft Teams

Como ampliación futura, el workflow puede enviar una notificación a un canal del equipo utilizando un webhook almacenado como GitHub Secret.

La notificación debería incluir:

- Repositorio y rama.
- Número del pull request.
- Métrica que incumplió el límite.
- Valor obtenido y valor esperado.
- Enlace al workflow.
- Enlace al artefacto con el dashboard.

## 9. Protección de credenciales

Los tokens, webhooks y credenciales no deben escribirse directamente en el repositorio.

Deben almacenarse en:

`Settings → Secrets and variables → Actions`

El workflow debe acceder a ellos mediante expresiones como:

`${{ secrets.SLACK_WEBHOOK_URL }}`

Los registros tampoco deben imprimir el valor del secreto.

## 10. Procedimiento ante una alerta

Cuando se genera una alerta crítica, el equipo debe seguir este procedimiento:

1. Revisar la métrica y el umbral incumplido.
2. Descargar el archivo JTL y el dashboard HTML.
3. Revisar los logs de Spring Boot y GitHub Actions.
4. Comparar el resultado con ejecuciones anteriores.
5. Repetir la prueba una vez para descartar una falla temporal.
6. Identificar el cambio que produjo la degradación.
7. Corregir o revertir el cambio.
8. Ejecutar nuevamente la prueba.
9. Permitir la fusión solamente cuando los criterios vuelvan a cumplirse.

## 11. Prevención de alertas innecesarias

Para evitar falsos positivos o exceso de notificaciones se recomienda:

- Utilizar siempre una configuración de carga comparable.
- Ejecutar las pruebas en un entorno estable.
- Separar las advertencias de los fallos críticos.
- Confirmar una degradación mediante una segunda ejecución.
- Conservar un historial de resultados.
- Notificar inmediatamente los fallos de un pull request.
- Escalar una alerta programada cuando falle en dos ejecuciones consecutivas.

## 12. Evaluación del resultado actual

| Métrica | Resultado actual | Límite crítico | Estado |
|---|---:|---:|---|
| Error % | 0,00 % | Superior a 1 % | Correcto |
| Percentil 95 | 2 ms | Igual o superior a 500 ms | Correcto |
| APDEX | 1,000 | Inferior a 0,95 | Correcto |
| Respuestas HTTP correctas | 100 % | Inferior a 100 % | Correcto |

La ejecución actual no generaría alertas. Las 200 solicitudes fueron exitosas y las métricas se mantuvieron ampliamente dentro de los criterios de aceptación.

## 13. Limitaciones

El mecanismo propuesto depende de que las pruebas se ejecuten con una configuración estable y que las métricas sean extraídas correctamente.

Los resultados actuales corresponden a un entorno local y a una prueba de corta duración. Antes de aplicar estos umbrales a producción se deben realizar pruebas más extensas en un entorno representativo.

## 14. Referencias

- GitHub Docs. [Notifications for workflow runs](https://docs.github.com/en/actions/concepts/workflows-and-actions/notifications-for-workflow-runs).
- GitHub Docs. [Workflow commands for GitHub Actions](https://docs.github.com/en/actions/reference/workflows-and-actions/workflow-commands).
- Apache JMeter. [Generating Report Dashboard](https://jmeter.apache.org/usermanual/generating-dashboard.html).