# Chilled Windows Java

A version of the **Chilled Windows** application developed entirely in **Java** using the **JavaFX** platform.

## Description

**Chilled Windows Java** is a desktop application designed to provide a dynamic multimedia experience. It utilizes advanced visual transformations, such as rotations and flipping effects, synchronized with time and keyboard events to create an interactive and visually engaging interface.

## Key Features

- **Cross-Platform:** Compatible with Windows, macOS, and Linux thanks to JavaFX.
- **Dynamic Interface:** Implemented with FXML for a clear separation between design and logic.
- **Smooth Animations:** Optimized timing system for real-time transformations.

## Technologies Used

- **Java 17+**
- **JavaFX 17** (Graphics, UI, and Multimedia)
- **Maven** (Dependency management and build automation)

## How to Run

To compile and run the project on your local machine, ensure you have JDK 17 (or higher) and Maven installed.

```bash
# Clone the repository
git clone https://github.com/guerrerooliver183-source/chilled-windows-java.git

# Enter the directory
cd chilled-windows-java

# Run the application
mvn clean javafx:run
```

## Project Structure

- `src/main/java/com/chilledwindows/Main.java`: Application entry point.
- `src/main/java/com/chilledwindows/MainWindowController.java`: Control logic, animations, and event management.
- `src/main/resources/com/chilledwindows/MainWindow.fxml`: User interface layout.
- `src/main/resources/com/chilledwindows/`: Multimedia and visual resources.

---
Developed with JavaFX.
