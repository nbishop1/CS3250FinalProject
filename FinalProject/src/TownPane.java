import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TownPane extends BorderPane {
    public TownPane(GameJourney journey, Stage primaryStage) {
        this.setStyle("-fx-background-color: black;");

        // Top: Town image with day label overlay (cropped 20px top/bottom)
        Image townImg = new Image(getClass().getResourceAsStream("images/TownTown.png"));
        ImageView townImage = new ImageView(townImg);
        townImage.setPreserveRatio(true);
        townImage.setSmooth(true);
        townImage.fitWidthProperty().bind(this.widthProperty());
        Label dayLabel = new Label("Day " + journey.getDay());
        dayLabel.setStyle("-fx-font-size: 48px; -fx-font-family: 'Rockwell'; -fx-font-weight: bold; -fx-text-fill: limegreen; -fx-background-color: rgba(0,0,0,0.5);");

        // Sprite at bottom center
        Image spriteImg = new Image(getClass().getResourceAsStream("images/player.png"));
        ImageView spriteView = new ImageView(spriteImg);
        spriteView.setPreserveRatio(true);
        spriteView.setSmooth(true);
        // Create imageStack first
        StackPane imageStack = new StackPane(townImage, dayLabel, spriteView);
        StackPane.setAlignment(dayLabel, Pos.TOP_CENTER);
        StackPane.setAlignment(spriteView, Pos.BOTTOM_CENTER);
        // Responsive resizing: bind fitHeight to 20% of imageStack height, with min/max
        spriteView.fitHeightProperty().bind(imageStack.heightProperty().multiply(0.25));
        imageStack.heightProperty().addListener((obs, oldVal, newVal) -> {
            double h = newVal.doubleValue() * 0.25;
            if (h < 60) spriteView.setFitHeight(60);
            else if (h > 220) spriteView.setFitHeight(220);
        });

        // Buttons row: left, center, right
        Button saloonBtn = new Button("Enter Saloon");
        Button leaveBtn = new Button("Leave Town");
        Button storeBtn = new Button("Enter General Store");
        saloonBtn.setStyle("-fx-font-size: 22px; -fx-background-color: black; -fx-text-fill: limegreen; -fx-border-color: limegreen; -fx-border-radius: 5px;");
        leaveBtn.setStyle("-fx-font-size: 22px; -fx-background-color: black; -fx-text-fill: limegreen; -fx-border-color: limegreen; -fx-border-radius: 5px;");
        storeBtn.setStyle("-fx-font-size: 22px; -fx-background-color: black; -fx-text-fill: limegreen; -fx-border-color: limegreen; -fx-border-radius: 5px;");
        Region leftSpacer = new Region();
        Region rightSpacer = new Region();
        HBox.setHgrow(leftSpacer, javafx.scene.layout.Priority.ALWAYS);
        HBox.setHgrow(rightSpacer, javafx.scene.layout.Priority.ALWAYS);
        HBox buttonBox = new HBox(20, saloonBtn, leftSpacer, leaveBtn, rightSpacer, storeBtn);
        buttonBox.setAlignment(Pos.BOTTOM_CENTER);
        buttonBox.setPadding(new Insets(0, 0, 10, 0));
        buttonBox.prefWidthProperty().bind(this.widthProperty());

        // Button actions
        leaveBtn.setOnAction(event -> {
            journey.nextDay();
            primaryStage.getScene().setRoot(new JourneyPane(journey));
        });
        saloonBtn.setOnAction(event -> {
            // Animate sprite to left (saloon door)
            double targetX = -imageStack.getWidth() / 2 + imageStack.getWidth() * 0.20; // 20% from left edge
            TranslateTransition tt = new TranslateTransition(Duration.millis(1500), spriteView);
            tt.setToX(targetX);
            tt.setOnFinished(e -> primaryStage.getScene().setRoot(new SaloonPane(journey, primaryStage)));
            tt.play();
        });
        storeBtn.setOnAction(event -> {
            // Animate sprite to right (general store door)
            double targetX = imageStack.getWidth() / 2 - imageStack.getWidth() * 0.18; // 18% from right edge
            TranslateTransition tt = new TranslateTransition(Duration.millis(1500), spriteView);
            tt.setToX(targetX);
            tt.setOnFinished(e -> primaryStage.getScene().setRoot(new GeneralStorePane(journey, primaryStage)));
            tt.play();
        });

        VBox topBox = new VBox(imageStack, buttonBox);
        topBox.setAlignment(Pos.TOP_CENTER);
        setTop(topBox);

    }
}