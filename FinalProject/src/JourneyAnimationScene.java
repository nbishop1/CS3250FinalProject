// JourneyAnimationScene.java
// This class was the original animation scene for the journey, but is now deprecated in favor of JourneyAnimationView.
// It is not used in the current UI, but is kept for reference or legacy purposes.
// The logic here is similar to JourneyAnimationView, but as a Scene instead of a Node.
// If you are not using this class, it can be safely deleted.

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class JourneyAnimationScene extends Scene {
    private static final String BG_PATH = "/images/background.png";
    private static final String WAGON_PATH = "/images/wagon.png";
    private static final String WHEEL_PATH = "/images/wheel.png";
    private static final String HORSE_PATH = "/images/horse.png";
    private static final int HORSE_FRAMES = 14;
    private static final int HORSE_SPRITE_COLS = 7;
    private static final int HORSE_SPRITE_ROWS = 2;
    private static final int HORSE_SPRITE_WIDTH = 256;
    private static final int HORSE_SPRITE_HEIGHT = 256;
    private static final double BACKGROUND_SCALE = 0.33;
    private static final double BACKGROUND_TOP_MARGIN = 30;
    private static final double WAGON_WIDTH_RATIO = 0.26;
    private static final double WAGON_HEIGHT_RATIO = 0.40;
    private static final double WAGON_MARGIN_RIGHT_RATIO = 0.04;
    private static final double HORSE_WIDTH_RATIO = 0.10;
    private static final double HORSE_HEIGHT_RATIO = 0.22;
    private static final double HORSE_MARGIN_RATIO = 0.03;
    private static final double WHEEL_BACK_OFFSET_X_RATIO = 0.50;
    private static final double WHEEL_BACK_OFFSET_Y_RATIO = 0.92;
    private static final double WHEEL_BACK_SIZE_RATIO = 0.45;
    private static final double WHEEL_FRONT_OFFSET_X_RATIO = 0.18;
    private static final double WHEEL_FRONT_OFFSET_Y_RATIO = 0.98;
    private static final double WHEEL_FRONT_SIZE_RATIO = 0.32;

    private Image bgImg, wagonImg, wheelImg, horseImg;
    private double bgOffset = 0;
    private double wheelAngle = 0;
    private int horseFrame = 0;
    private Canvas canvas;
    private long lastUpdate = 0;
    private static final long HORSE_DELAY = 30_000_000;

    public JourneyAnimationScene(double width, double height) {
        super(new Group(), width, height);
        bgImg = new Image(getClass().getResourceAsStream(BG_PATH));
        wagonImg = new Image(getClass().getResourceAsStream(WAGON_PATH));
        wheelImg = new Image(getClass().getResourceAsStream(WHEEL_PATH));
        horseImg = new Image(getClass().getResourceAsStream(HORSE_PATH));
        canvas = new Canvas(width, height);
        ((Group) getRoot()).getChildren().add(canvas);
        widthProperty().addListener((obs, oldVal, newVal) -> {
            canvas.setWidth(newVal.doubleValue());
            draw();
        });
        heightProperty().addListener((obs, oldVal, newVal) -> {
            canvas.setHeight(newVal.doubleValue());
            draw();
        });
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update(now);
                draw();
            }
        };
        timer.start();
    }

    private void update(long now) {
        double scaledBgWidth = bgImg.getWidth() * BACKGROUND_SCALE;
        bgOffset -= 2;
        if (bgOffset <= -scaledBgWidth) bgOffset += scaledBgWidth;
        wheelAngle -= 4;
        if (wheelAngle < 0) wheelAngle += 360;
        if (now - lastUpdate >= HORSE_DELAY) {
            horseFrame = (horseFrame + 1) % HORSE_FRAMES;
            lastUpdate = now;
        }
    }

    private void draw() {
        double width = canvas.getWidth();
        double height = canvas.getHeight();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, width, height);
        double scaledBgWidth = bgImg.getWidth() * BACKGROUND_SCALE;
        double scaledBgHeight = bgImg.getHeight() * BACKGROUND_SCALE;
        double bgY = BACKGROUND_TOP_MARGIN;
        double x = bgOffset;
        while (x < width) {
            gc.drawImage(bgImg, x, bgY, scaledBgWidth, scaledBgHeight);
            x += scaledBgWidth;
        }
        double wagonWidth = width * WAGON_WIDTH_RATIO;
        double wagonHeight = height * WAGON_HEIGHT_RATIO;
        double wagonX = Math.max(width - wagonWidth - width * WAGON_MARGIN_RIGHT_RATIO, 0);
        double wagonY = bgY + scaledBgHeight - wagonHeight - 10;
        double horseWidth = width * HORSE_WIDTH_RATIO;
        double horseHeight = height * HORSE_HEIGHT_RATIO;
        double horseX = Math.max(wagonX - horseWidth - width * HORSE_MARGIN_RATIO, 0);
        double horseY = bgY + scaledBgHeight - horseHeight - 8;
        int col = horseFrame % HORSE_SPRITE_COLS;
        int row = horseFrame / HORSE_SPRITE_COLS;
        gc.drawImage(horseImg, col * HORSE_SPRITE_WIDTH, row * HORSE_SPRITE_HEIGHT, HORSE_SPRITE_WIDTH, HORSE_SPRITE_HEIGHT,
            horseX, horseY, horseWidth, horseHeight);
        gc.drawImage(wagonImg, wagonX, wagonY, wagonWidth, wagonHeight);
        double backWheelX = wagonX + wagonWidth * WHEEL_FRONT_OFFSET_X_RATIO;
        double backWheelY = wagonY + wagonHeight * WHEEL_FRONT_OFFSET_Y_RATIO;
        double backWheelSize = wagonHeight * WHEEL_BACK_SIZE_RATIO;
        gc.save();
        gc.translate(backWheelX + backWheelSize / 2, backWheelY + backWheelSize / 2);
        gc.rotate(wheelAngle);
        gc.drawImage(wheelImg, -backWheelSize / 2, -backWheelSize / 2, backWheelSize, backWheelSize);
        gc.restore();
        double frontWheelX = wagonX + wagonWidth * WHEEL_BACK_OFFSET_X_RATIO;
        double frontWheelY = wagonY + wagonHeight * WHEEL_BACK_OFFSET_Y_RATIO;
        double frontWheelSize = wagonHeight * WHEEL_FRONT_SIZE_RATIO;
        gc.save();
        gc.translate(frontWheelX + frontWheelSize / 2, frontWheelY + frontWheelSize / 2);
        gc.rotate(wheelAngle);
        gc.drawImage(wheelImg, -frontWheelSize / 2, -frontWheelSize / 2, frontWheelSize, frontWheelSize);
        gc.restore();
    }
}