# Chilled Windows Java (JavaFX)

Este proyecto es una conversión de la aplicación original [chilled-windows-java](https://github.com/guerrerooliver183-source/chilled-windows-java.git) de C#/WPF a Java/JavaFX.

## Descripción

La aplicación es un visor multimedia que realiza transformaciones visuales (rotaciones y volteos) en respuesta a eventos de tiempo y teclado.

## Tecnologías Utilizadas

- **Java 17**
- **JavaFX 17** (UI, Gráficos y Multimedia)
- **Maven** (Gestión de dependencias y construcción)

## Cómo Ejecutar

Para ejecutar el proyecto localmente, asegúrate de tener instalado Java 17+ y Maven. Luego, utiliza el siguiente comando:

```bash
mvn clean javafx:run
```

## Estructura del Proyecto

- `src/main/java/com/chilledwindows/Main.java`: Clase principal que inicia la aplicación JavaFX.
- `src/main/java/com/chilledwindows/MainWindowController.java`: Controlador que maneja la lógica de la UI y las animaciones.
- `src/main/resources/com/chilledwindows/MainWindow.fxml`: Definición de la interfaz de usuario en formato FXML.
- `src/main/resources/com/chilledwindows/`: Recursos de imagen utilizados por la aplicación.

---
Convertido automáticamente por Manus AI.
