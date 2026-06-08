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

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MainWindowController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private MediaView mediaView;
    @FXML
    private javafx.scene.shape.Rectangle bgRectangle;
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

        // Capture screen similar to C# CopyFromScreen
        try {
            Robot robot = new Robot();
            java.awt.Rectangle screenRect = new java.awt.Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage screenFullImage = robot.createScreenCapture(screenRect);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(screenFullImage, "png", baos);
            byte[] bytes = baos.toByteArray();
            firstBg.setImage(new Image(new ByteArrayInputStream(bytes)));
        } catch (AWTException | IOException e) {
            System.err.println("Error capturing screen: " + e.getMessage());
            // Fallback to default image if screen capture fails
            firstBg.setImage(new Image(getClass().getResourceAsStream("/com/chilledwindows/Image1.jpg")));
        }

        // Setup window properties similar to C#
        javafx.application.Platform.runLater(() -> {
            javafx.stage.Stage stage = (javafx.stage.Stage) rootPane.getScene().getWindow();
            stage.initStyle(javafx.stage.StageStyle.UNDECORATED);
            stage.setAlwaysOnTop(true);
            stage.setMaximized(true);
        });

        // Load images
        bg2.setImage(new Image(getClass().getResourceAsStream("/com/chilledwindows/ja.png")));
        // Load and play video
        try {
            String videoPath = getClass().getResource("/com/chilledwindows/chilledwindows.mp4").toExternalForm();
            javafx.scene.media.Media media = new javafx.scene.media.Media(videoPath);
            javafx.scene.media.MediaPlayer mediaPlayer = new javafx.scene.media.MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            mediaPlayer.setCycleCount(javafx.scene.media.MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        } catch (Exception e) {
            System.err.println("Could not load video: " + e.getMessage());
        }

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
            fFlipTrans.setX(fFlipTrans.getX() == -1.0 ? 1.0 : -1.0);
        }
    }

    private void dt_Tick() {
        if (mediaView.getMediaPlayer() != null) {
            frameIndex = (int) Math.floor(mediaView.getMediaPlayer().getCurrentTime().toMillis() / 33.33333);
        } else {
            frameIndex++;
        }
        
        label.setText("Frame:" + frameIndex);

        if (frameIndex == 438) {
            refreshFirstFlips = false;
            firstBg.setVisible(false);
            twoGrid.setVisible(true);
        }
        if (frameIndex == 585) {
            refreshSecondFlips = false;
        }
        if (frameIndex == 622) {
            bgRectangle.setVisible(false);
            double num = screenWidth * 0.13817330210772832;
            double num2 = screenHeight * 0.3541666666666667;
            
            // Initialize transformations for twoGrid
            twoGrid.getTransforms().clear();
            twoGrid.getTransforms().addAll(gTransTransform, gScaleTransform);

            // Animate the transformations
            javafx.animation.Timeline timeline = new javafx.animation.Timeline(
                new javafx.animation.KeyFrame(javafx.util.Duration.millis(500),
                    new javafx.animation.KeyValue(gTransTransform.xProperty(), num * 3.0, javafx.animation.Interpolator.EASE_BOTH),
                    new javafx.animation.KeyValue(gTransTransform.yProperty(), num2 * 3.0, javafx.animation.Interpolator.EASE_BOTH),
                    new javafx.animation.KeyValue(gScaleTransform.xProperty(), 0.3, javafx.animation.Interpolator.EASE_BOTH),
                    new javafx.animation.KeyValue(gScaleTransform.yProperty(), 0.3, javafx.animation.Interpolator.EASE_BOTH)
                )
            );
            timeline.play();
        }
        if (frameIndex == 665) {
            twoGrid.setVisible(false);
        }
        if (frameIndex == 1260) {
            javafx.application.Platform.exit();
            System.exit(0);
        }

        if (refreshFirstFlips) {
            if (flipIndex < flipTimes.length && flipTimes[flipIndex] <= frameIndex && flipTimes[flipIndex] != 0) {
                flipIndex++;
                fFlipTrans.setX(fFlipTrans.getX() == -1.0 ? 1.0 : -1.0);
            }
            if (frameIndex == 286) {
                fRotateTrans.setAngle(-20.0);
                return;
            }
        } else if (refreshSecondFlips) {
            if (flipIndex1 < flipTimes1.length && flipTimes1[flipIndex1] <= frameIndex && flipTimes1[flipIndex1] != 0) {
                flipIndex1++;
                flipTrans1.setX(flipTrans1.getX() == -1.0 ? 1.0 : -1.0);
            }
            if (flipIndex2 < flipTimes2.length && flipTimes2[flipIndex2] <= frameIndex && flipTimes2[flipIndex2] != 0) {
                flipIndex2++;
                flipTrans2.setX(flipTrans2.getX() == -1.0 ? 1.0 : -1.0);
            }
        }
    }

    // The BitmapToImageSource method from C# is not directly translatable as JavaFX Image handles loading differently.
    // If there's a need to convert AWT Bitmap to JavaFX Image, it would involve different steps.
}
