package com.chilledwindows;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Screen;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private MediaView mediaView;
    @FXML
    private Rectangle bgRectangle;
    @FXML
    private ImageView firstBg;
    @FXML
    private Label label;
    @FXML
    private GridPane twoGrid;
    @FXML
    private ImageView bg2;
    @FXML
    private ImageView bg3;

    private Scale fFlipTrans = new Scale(1, 1);
    private Scale flipTrans1 = new Scale(1, 1);
    private Scale flipTrans2 = new Scale(1, 1);
    private Rotate fRotateTrans = new Rotate(0);
    private Translate gTransTransform = new Translate();
    private Scale gScaleTransform = new Scale(1, 1);

    private int screenWidth;
    private int screenHeight;

    private int[] flipTimes = new int[]{
            126, 129, 133, 136, 140, 143, 147, 150, 155, 158, 162, 165, 169, 172, 176, 179, 184, 187, 191, 194, 198, 201, 205, 208, 213, 216, 220, 223, 227, 230, 234, 237, 242, 245, 249, 252, 256, 259, 263, 266, 271, 274, 278, 281, 285, 286, 288, 292, 295, 297, 300, 303, 307, 310, 314, 317, 321, 324, 329, 332, 336, 339, 343, 346, 350, 353, 355, 358, 361, 365, 368, 372, 375, 379, 382, 387, 390, 394, 397, 401, 404, 408, 411, 416, 419, 423, 426, 430, 433, 437, 0, 0, 0, 0, 0, 0
    };
    private int[] flipTimes2 = new int[]{
            440, 443, 449, 454, 456, 460, 468, 476, 481, 486, 497, 501, 504, 506, 512, 514, 518, 522, 527, 531, 533, 536, 540, 548, 552, 557, 561, 563, 569, 572, 576, 580, 582, 585, 0, 0, 0, 0, 0, 0
    };
    private int[] flipTimes1 = new int[]{
            454, 476, 497, 512, 531, 548, 569, 585, 0, 0, 0, 0, 0, 0
    };

    private int flipIndex;
    private int flipIndex1;
    private int flipIndex2;
    private int frameIndex;
    private boolean refreshFirstFlips = true;
    private boolean refreshSecondFlips = true;

    private AnimationTimer animationTimer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        screenWidth = (int) Screen.getPrimary().getBounds().getWidth();
        screenHeight = (int) Screen.getPrimary().getBounds().getHeight();

        // Load images
        firstBg.setImage(new Image(getClass().getResourceAsStream("/com/chilledwindows/Image1.jpg")));
        bg2.setImage(new Image(getClass().getResourceAsStream("/com/chilledwindows/ja.png")));
        // bg3 will be set dynamically

        // Apply transforms to firstBg (similar to fFlipTrans in C#)
        firstBg.getTransforms().add(fFlipTrans);

        // Apply transforms to bg2 and bg3 (similar to FlipTrans1 and FlipTrans2 in C#)
        bg2.getTransforms().add(flipTrans1);
        bg3.getTransforms().add(flipTrans2);

        // Set up key event handler
        rootPane.addEventHandler(KeyEvent.KEY_PRESSED, this::handleKeyPress);

        // Initialize and start animation timer
        animationTimer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 1_000_000_000L / 60) { // Approximately 60 FPS
                    dt_Tick();
                    lastUpdate = now;
                }
            }
        };
        animationTimer.start();
    }

    private void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.SPACE) {
            fFlipTrans.setScaleX(fFlipTrans.getScaleX() == -1.0 ? 1.0 : -1.0);
        }
    }

    private void dt_Tick() {
        frameIndex++;

        if (frameIndex == 1) {
            // Initial setup, similar to C# code
            label.setVisible(true);
            label.setText("Chilled Windows");
            firstBg.setVisible(true);
            twoGrid.setVisible(false);
        }

        // Logic for flipTimes
        if (flipIndex < flipTimes.length && flipTimes[flipIndex] == frameIndex) {
            if (refreshFirstFlips) {
                firstBg.setVisible(false);
                twoGrid.setVisible(true);
                refreshFirstFlips = false;
            }
            flipTrans1.setScaleX(flipTrans1.getScaleX() == -1.0 ? 1.0 : -1.0);
            flipIndex++;
        }

        // Logic for flipTimes1
        if (flipIndex1 < flipTimes1.length && flipTimes1[flipIndex1] == frameIndex) {
            flipTrans1.setScaleX(flipTrans1.getScaleX() == -1.0 ? 1.0 : -1.0);
            flipIndex1++;
        }

        // Logic for flipTimes2
        if (flipIndex2 < flipTimes2.length && flipTimes2[flipIndex2] == frameIndex) {
            if (refreshSecondFlips) {
                // This part is a bit ambiguous in the original C# as to what exactly happens
                // Assuming it might involve refreshing bg3 or similar
                refreshSecondFlips = false;
            }
            flipTrans2.setScaleX(flipTrans2.getScaleX() == -1.0 ? 1.0 : -1.0);
            flipIndex2++;
        }

        // Reset logic if all flips are done (simplified, original C# had more complex reset)
        if (flipIndex >= flipTimes.length && flipIndex1 >= flipTimes1.length && flipIndex2 >= flipTimes2.length) {
            // Consider resetting frameIndex or stopping animation if it's a one-shot animation
            // For now, let it continue, but this might need adjustment based on desired behavior
        }
    }

    // The BitmapToImageSource method from C# is not directly translatable as JavaFX Image handles loading differently.
    // If there's a need to convert AWT Bitmap to JavaFX Image, it would involve different steps.
}
