# Proyecto Firma Biométrica Java

Este proyecto es una aplicación de escritorio para Windows que permite la captura de firma biométrica (Topaz), huella digital y fotografía, integrando dispositivos compatibles y almacenamiento en SQL Server.

## Objetivos
- Captura de firma biométrica usando tabletas Topaz.
- Captura de huella digital (futuro módulo).
- Captura de fotografía mediante webcam.
- Almacenamiento seguro en base de datos SQL Server.

## Estructura del Proyecto
- **src/main/java/com/firmabiometrica/**: Código fuente principal.
- **src/main/resources/**: Recursos (imágenes, configuraciones).
- **src/test/java/com/firmabiometrica/**: Pruebas unitarias.
- **firma/**, **foto/**, **sql/**: Módulos para cada funcionalidad.

## Dependencias Iniciales
- Java 8+
- Maven
- SDK Topaz (Windows)
- Librería webcam (por definir)
- Conector JDBC SQL Server

## Uso
1. Clona el repositorio.
2. Ejecuta `mvn clean install` para compilar.
3. Corre la clase principal para iniciar la app.

## Notas
- El proyecto está optimizado para Windows por compatibilidad con drivers y SDKs.
- Se recomienda usar Visual Studio Code o IntelliJ IDEA.

---
