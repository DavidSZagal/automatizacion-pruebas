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