package com.chilledwindows.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class ChilledWindowsSwing extends JFrame implements KeyListener {

    private BufferedImage screenCapture;
    private BufferedImage image1;
    private BufferedImage jaImage;

    private JPanel mainPanel;
    private JPanel twoGridPanel;

    private int screenWidth;
    private int screenHeight;

    private int frameIndex = 0;
    private Timer timer;

    private double fFlipScaleX = 1.0;
    private double fRotateAngle = 0.0;

    private double flipTrans1ScaleX = 1.0;
    private double flipTrans2ScaleX = 1.0;

    private boolean refreshFirstFlips = true;
    private boolean refreshSecondFlips = true;

    private int[] flipTimes = new int[]{
            126, 129, 133, 136, 140, 143, 147, 150, 155, 158, 162, 165, 169, 172, 176, 179, 184, 187, 191, 194, 198, 201, 205, 208, 213, 216, 220, 223, 227, 230, 234, 237, 242, 245, 249, 252, 256, 259, 263, 266, 271, 274, 278, 281, 285, 286, 288, 292, 295, 297, 300, 303, 307, 310, 314, 317, 321, 324, 329, 332, 336, 339, 343, 346, 350, 353, 355, 358, 361, 365, 368, 372, 375, 379, 382, 387, 390, 394, 397, 401, 404, 408, 411, 416, 419, 423, 426, 430, 433, 437, 0, 0, 0, 0, 0, 0
    };
    private int[] flipTimes2 = new int[]{
            440, 443, 449, 454, 456, 460, 468, 476, 481, 486, 497, 501, 504, 506, 512, 514, 518, 522, 527, 531, 533, 536, 540, 548, 552, 557, 561, 563, 569, 572, 576, 580, 582, 585, 0, 0, 0, 0, 0, 0
    };
    private int[] flipTimes1 = new int[]{
            454, 476, 497, 512, 531, 548, 569, 585, 0, 0, 0, 0, 0, 0
    };

    private int flipIndex = 0;
    private int flipIndex1 = 0;
    private int flipIndex2 = 0;

    public ChilledWindowsSwing() {
        initUI();
        setupScreenCapture();
        setupTimer();
        minimizeAllWindows();
    }

    private void initUI() {
        setTitle("Chilled Windows Swing");
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setAlwaysOnTop(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(this);

        screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

        mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                // Draw firstBg (screenCapture) with transformations
                if (refreshFirstFlips && screenCapture != null) {
                    AffineTransform transform = new AffineTransform();
                    transform.translate(screenWidth / 2.0, screenHeight / 2.0);
                    transform.scale(fFlipScaleX, 1.0);
                    transform.rotate(Math.toRadians(fRotateAngle));
                    transform.translate(-screenCapture.getWidth() / 2.0, -screenCapture.getHeight() / 2.0);
                    g2d.drawImage(screenCapture, transform, null);
                }

                // Draw twoGridPanel (bg2 and bg3) with transformations
                if (!refreshFirstFlips && twoGridPanel.isVisible()) {
                    // This part is more complex as twoGridPanel is a separate component
                    // For simplicity, we'll just draw the images directly here for now
                    // A more robust solution would involve rendering twoGridPanel to an image and then transforming it
                    int halfWidth = screenWidth / 2;

                    // bg2
                    if (screenCapture != null) {
                        AffineTransform transform2 = new AffineTransform();
                        transform2.translate(halfWidth / 2.0, screenHeight / 2.0);
                        transform2.scale(flipTrans1ScaleX, 1.0);
                        transform2.translate(-screenCapture.getWidth() / 2.0, -screenCapture.getHeight() / 2.0);
                        g2d.drawImage(screenCapture, transform2, null);
                    }

                    // bg3
                    if (screenCapture != null) {
                        AffineTransform transform3 = new AffineTransform();
                        transform3.translate(halfWidth + halfWidth / 2.0, screenHeight / 2.0);
                        transform3.scale(flipTrans2ScaleX, 1.0);
                        transform3.translate(-screenCapture.getWidth() / 2.0, -screenCapture.getHeight() / 2.0);
                        g2d.drawImage(screenCapture, transform3, null);
                    }
                }
            }
        };
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setLayout(null); // Use null layout for absolute positioning or custom painting
        add(mainPanel);

        twoGridPanel = new JPanel();
        twoGridPanel.setBackground(Color.BLACK);
        twoGridPanel.setBounds(6, 6, screenWidth - 12, screenHeight - 12); // Similar to FXML margin
        twoGridPanel.setVisible(false);
        mainPanel.add(twoGridPanel);

        // Add a label for frame index, similar to C#
        JLabel frameLabel = new JLabel("Frame: 0");
        frameLabel.setForeground(Color.WHITE);
        frameLabel.setBounds(14, 14, 280, 59); // Position similar to FXML
        mainPanel.add(frameLabel);
    }

    private void setupScreenCapture() {
        try {
            Robot robot = new Robot();
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            screenCapture = robot.createScreenCapture(screenRect);
            // Load Image1.jpg as a fallback or initial image if needed
            URL image1Url = getClass().getResource("/Image1.jpg");
            if (image1Url != null) {
                image1 = ImageIO.read(image1Url);
            }
            URL jaImageUrl = getClass().getResource("/ja.png");
            if (jaImageUrl != null) {
                jaImage = ImageIO.read(jaImageUrl);
            }
        } catch (AWTException | IOException e) {
            System.err.println("Error capturing screen or loading images: " + e.getMessage());
            // Fallback to a black image if capture fails
            screenCapture = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = screenCapture.createGraphics();
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, screenWidth, screenHeight);
            g2d.dispose();
        }
    }

    private void setupTimer() {
        timer = new Timer(16, new ActionListener() { // Approximately 60 FPS (1000ms / 60 frames = 16.67ms)
            @Override
            public void actionPerformed(ActionEvent e) {
                dt_Tick();
                mainPanel.repaint(); // Redraw the panel
            }
        });
        timer.start();
    }

    private void dt_Tick() {
        frameIndex++;
        // Update frame label (assuming it's the first JLabel in mainPanel)
        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof JLabel) {
                ((JLabel) comp).setText("Frame: " + frameIndex);
                break;
            }
        }

        if (frameIndex == 438) {
            refreshFirstFlips = false;
            // Hide firstBg (mainPanel's painting logic handles this)
            twoGridPanel.setVisible(true);
        }
        if (frameIndex == 585) {
            refreshSecondFlips = false;
        }
        if (frameIndex == 622) {
            // bg.Visibility = Visibility.Hidden; (handled by mainPanel background)
            double num = screenWidth * 0.13817330210772832;
            double num2 = screenHeight * 0.3541666666666667;

            // Simulate animations for twoGridPanel (translation and scale)
            // In Swing, we'd typically animate properties directly or use a custom layout manager
            // For simplicity, we'll just set final positions/scales here for now
            // A proper animation would use a separate Swing Timer or Animator library
            twoGridPanel.setBounds((int) (6 + num * 3.0), (int) (6 + num2 * 3.0), (int) ((screenWidth - 12) * 0.3), (int) ((screenHeight - 12) * 0.3));
        }
        if (frameIndex == 665) {
            twoGridPanel.setVisible(false);
        }
        if (frameIndex == 1260) {
            timer.stop();
            dispose(); // Close the window
            System.exit(0);
        }

        if (refreshFirstFlips) {
            if (flipIndex < flipTimes.length && flipTimes[flipIndex] != 0 && flipTimes[flipIndex] <= frameIndex) {
                flipIndex++;
                fFlipScaleX = (fFlipScaleX == -1.0) ? 1.0 : -1.0;
            }
            if (frameIndex == 286) {
                fRotateAngle = -20.0;
            }
        } else if (refreshSecondFlips) {
            if (flipIndex1 < flipTimes1.length && flipTimes1[flipIndex1] != 0 && flipTimes1[flipIndex1] <= frameIndex) {
                flipIndex1++;
                flipTrans1ScaleX = (flipTrans1ScaleX == -1.0) ? 1.0 : -1.0;
            }
            if (flipIndex2 < flipTimes2.length && flipTimes2[flipIndex2] != 0 && flipTimes2[flipIndex2] <= frameIndex) {
                flipIndex2++;
                flipTrans2ScaleX = (flipTrans2ScaleX == -1.0) ? 1.0 : -1.0;
            }
        }
    }

    private void minimizeAllWindows() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_WINDOWS);
            robot.keyPress(KeyEvent.VK_D);
            robot.keyRelease(KeyEvent.VK_D);
            robot.keyRelease(KeyEvent.VK_WINDOWS);
            Thread.sleep(300);
        } catch (AWTException | InterruptedException e) {
            System.err.println("Error minimizing windows: " + e.getMessage());
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            fFlipScaleX = (fFlipScaleX == -1.0) ? 1.0 : -1.0;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args) {
        // Ensure Swing UI updates are done on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new ChilledWindowsSwing().setVisible(true);
        });
    }
}
