import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;


// TODO: Add horse animation if time allows

public class JourneyAnimationView extends StackPane {
    private static final String BG_PATH = "/images/background.png";
    private static final String WAGON_PATH = "/images/wagon.png";
    private static final String WHEEL_PATH = "/images/wheel.png";
    private static final double BACKGROUND_SCALE = 0.33;
    private static final double BACKGROUND_TOP_MARGIN = 30;
    private static final double WAGON_WIDTH_RATIO = 0.26;
    private static final double WAGON_HEIGHT_RATIO = 0.40;
    private static final double WAGON_MARGIN_RIGHT_RATIO = 0.04;
    private static final double WHEEL_BACK_OFFSET_X_RATIO = 0.60; 
    private static final double WHEEL_BACK_OFFSET_Y_RATIO = 0.70;
    private static final double WHEEL_BACK_SIZE_RATIO = 0.80; 
    private static final double WHEEL_FRONT_OFFSET_X_RATIO = 0.10; 
    private static final double WHEEL_FRONT_OFFSET_Y_RATIO = 0.90;
    private static final double WHEEL_FRONT_SIZE_RATIO = 0.60; 

    private Image bgImg, wagonImg, wheelImg;
    private double bgOffset = 0;
    private double wheelAngle = 0;
    private Canvas canvas;

    public JourneyAnimationView() {
        bgImg = new Image(getClass().getResourceAsStream(BG_PATH));
        wagonImg = new Image(getClass().getResourceAsStream(WAGON_PATH));
        wheelImg = new Image(getClass().getResourceAsStream(WHEEL_PATH));
        canvas = new Canvas(800, 300);
        this.getChildren().add(canvas);
        // Bind canvas size to the view size and redraw on resize
        widthProperty().addListener((obs, oldVal, newVal) -> {
            canvas.setWidth(newVal.doubleValue());
            draw();
        });
        heightProperty().addListener((obs, oldVal, newVal) -> {
            canvas.setHeight(newVal.doubleValue());
            draw();
        });
        canvas.widthProperty().bind(widthProperty());
        canvas.heightProperty().bind(heightProperty());
        // AnimationTimer drives the animation loop
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
        // Update background offset for scrolling effect
        double scaledBgWidth = bgImg.getWidth() * BACKGROUND_SCALE;
        bgOffset += 2; // Move background left as wagon moves right
        if (bgOffset >= scaledBgWidth) bgOffset -= scaledBgWidth;
        // Update wheel rotation
        wheelAngle -= 2;
        if (wheelAngle < 0) wheelAngle += 360;
    }

    private void draw() {
        // Draw and tile the background
        double width = canvas.getWidth();
        double height = canvas.getHeight();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, width, height); // Clear the entire canvas before drawing
        double scaledBgWidth = bgImg.getWidth() * BACKGROUND_SCALE;
        double scaledBgHeight = bgImg.getHeight() * BACKGROUND_SCALE;
        double bgY = BACKGROUND_TOP_MARGIN;
        double x = bgOffset;
        while (x < width) {
            gc.drawImage(bgImg, x, bgY, scaledBgWidth, scaledBgHeight);
            x += scaledBgWidth;
        }
        if (bgOffset > 0) {
            gc.drawImage(bgImg, bgOffset - scaledBgWidth, bgY, scaledBgWidth, scaledBgHeight);
        }
        // Calculate wagon positions/sizes
        double wagonWidth = width * WAGON_WIDTH_RATIO;
        double wagonHeight = height * WAGON_HEIGHT_RATIO;
        double wagonX = Math.max(width - wagonWidth - width * WAGON_MARGIN_RIGHT_RATIO, 0);
        double wagonY = bgY + scaledBgHeight - wagonHeight - 10;
        // Draw the wagon
        gc.drawImage(wagonImg, wagonX, wagonY, wagonWidth, wagonHeight);
        // Draw the wheels (back wheel is right/larger, front wheel is left/smaller)
        double backWheelX = wagonX + wagonWidth * WHEEL_BACK_OFFSET_X_RATIO;
        double backWheelY = wagonY + wagonHeight * WHEEL_BACK_OFFSET_Y_RATIO;
        double backWheelSize = wagonHeight * WHEEL_BACK_SIZE_RATIO;
        gc.save();
        gc.translate(backWheelX + backWheelSize / 2, backWheelY + backWheelSize / 2);
        gc.rotate(wheelAngle);
        gc.drawImage(wheelImg, -backWheelSize / 2, -backWheelSize / 2, backWheelSize, backWheelSize);
        gc.restore();
        double frontWheelX = wagonX + wagonWidth * WHEEL_FRONT_OFFSET_X_RATIO;
        double frontWheelY = wagonY + wagonHeight * WHEEL_FRONT_OFFSET_Y_RATIO;
        double frontWheelSize = wagonHeight * WHEEL_FRONT_SIZE_RATIO;
        gc.save();
        gc.translate(frontWheelX + frontWheelSize / 2, frontWheelY + frontWheelSize / 2);
        gc.rotate(wheelAngle);
        gc.drawImage(wheelImg, -frontWheelSize / 2, -frontWheelSize / 2, frontWheelSize, frontWheelSize);
        gc.restore();
    }
}