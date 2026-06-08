# Chilled Windows Java

Versión de la aplicación **Chilled Windows** desarrollada íntegramente en **Java** utilizando la plataforma **JavaFX**.

## Descripción

**Chilled Windows Java** es una aplicación de escritorio diseñada para ofrecer una experiencia multimedia dinámica. Utiliza transformaciones visuales avanzadas, como rotaciones y efectos de volteo (flipping), sincronizados con eventos temporales y de teclado para crear una interfaz interactiva y visualmente atractiva.

## Características Principales

- **Multiplataforma:** Compatible con Windows, macOS y Linux gracias a JavaFX.
- **Interfaz Dinámica:** Implementada con FXML para una separación clara entre diseño y lógica.
- **Animaciones Fluidas:** Sistema de temporización optimizado para transformaciones en tiempo real.

## Tecnologías Utilizadas

- **Java 17+**
- **JavaFX 17** (Gráficos, UI y Multimedia)
- **Maven** (Gestión de dependencias y automatización de construcción)

## Cómo Ejecutar

Para compilar y ejecutar el proyecto en tu máquina local, asegúrate de tener instalado el JDK 17 (o superior) y Maven.

```bash
# Clonar el repositorio
git clone https://github.com/guerrerooliver183-source/chilled-windows-java.git

# Entrar al directorio
cd chilled-windows-java

# Ejecutar la aplicación
mvn clean javafx:run
```

## Estructura del Proyecto

- `src/main/java/com/chilledwindows/Main.java`: Punto de entrada de la aplicación.
- `src/main/java/com/chilledwindows/MainWindowController.java`: Lógica de control, animaciones y gestión de eventos.
- `src/main/resources/com/chilledwindows/MainWindow.fxml`: Diseño de la interfaz de usuario.
- `src/main/resources/com/chilledwindows/`: Recursos multimedia y visuales.

---
Desarrollado con JavaFX.
