# Proyecto de automatización de pruebas

Proyecto Java desarrollado para implementar pruebas automatizadas,
integración continua, generación de reportes y gestión de versiones.

## Autor

David Sandoval

## Objetivo

Profesionalizar el proceso de pruebas de una aplicación Java mediante
pruebas unitarias, cobertura de código, reportes HTML navegables y un
pipeline de integración continua.

## Funcionalidades

La aplicación permite gestionar el registro de compras y contempla:

- Cálculo del total de una compra.
- Actualización del stock disponible.
- Validación de cantidades inválidas.
- Validación de stock insuficiente.
- Validación de productos inexistentes.
- Compra de todo el stock disponible.

## Tecnologías utilizadas

- Java 17.
- Maven.
- Spring Boot.
- JUnit 5.
- Maven Surefire.
- Maven Surefire Report Plugin.
- JaCoCo.
- Git y GitHub.
- GitHub Actions.

## Requisitos previos

Antes de ejecutar el proyecto es necesario disponer de:

- Java 17 o superior.
- Maven 3.9 o superior.
- Git.
- Un navegador web para visualizar los reportes HTML.

Para comprobar las instalaciones:

```bash
java -version
mvn -version
git --version
```

## Conclusiones

La automatización implementada permite detectar errores de forma temprana
y mantener resultados repetibles durante el desarrollo. La combinación de
JUnit, Maven, JaCoCo y GitHub Actions permite compilar, probar y medir la
cobertura automáticamente.

Además, el uso de ramas, pull requests y reglas de protección mejora la
trazabilidad de los cambios y evita integrar código que no haya superado
las pruebas automatizadas.

## Evidencias

### Ejecución local de las pruebas

Las pruebas se ejecutaron localmente mediante Maven. El resultado fue de
ocho pruebas aprobadas, sin fallos ni errores.

![Ejecución local de las pruebas](docs/evidencias/actividad-1/01-ejecucion-local-pruebas.png)

### Cobertura de código local

JaCoCo generó un reporte navegable con una cobertura total del 88 % y una
cobertura del 100 % para el servicio de compras.

![Cobertura local con JaCoCo](docs/evidencias/actividad-1/02-cobertura-jacoco-local.png)

### Ejecución de las pruebas en integración continua

GitHub Actions ejecutó automáticamente la compilación, las pruebas y la
generación de los reportes sobre la rama `develop`.

![Ejecución de pruebas en GitHub Actions](docs/evidencias/actividad-1/03-ejecucion-pruebas-en-ci.png)

### Reportes publicados como artefactos

El pipeline publicó los resultados de Surefire y el reporte de cobertura
JaCoCo como artefactos descargables.

![Artefactos publicados por el pipeline](docs/evidencias/actividad-1/04-artefactos-reportes-ci.png)