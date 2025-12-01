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

        Image townImg = new Image(getClass().getResourceAsStream("images/TownTown.png"));
        ImageView townImage = new ImageView(townImg);
        townImage.setPreserveRatio(true);
        townImage.setSmooth(true);
        townImage.fitWidthProperty().bind(this.widthProperty());
        Label dayLabel = new Label("Day " + journey.getDay());
        dayLabel.setStyle("-fx-font-size: 48px; -fx-font-family: 'Sancreek'; -fx-font-weight: bold; -fx-text-fill: limegreen; -fx-background-color: rgba(0,0,0,0.5);");

        Image spriteImg = new Image(getClass().getResourceAsStream("images/player.png"));
        ImageView spriteView = new ImageView(spriteImg);
        spriteView.setPreserveRatio(true);
        spriteView.setSmooth(true);
        StackPane imageStack = new StackPane(townImage, dayLabel, spriteView);
        StackPane.setAlignment(dayLabel, Pos.TOP_CENTER);
        StackPane.setAlignment(spriteView, Pos.BOTTOM_CENTER);
        // Responsive resizing for sprite
        spriteView.fitHeightProperty().bind(imageStack.heightProperty().multiply(0.25));
        imageStack.heightProperty().addListener((obs, oldVal, newVal) -> {
            double h = newVal.doubleValue() * 0.25;
            if (h < 60) spriteView.setFitHeight(60);
            else if (h > 220) spriteView.setFitHeight(220);
        });

        // Show coin popup if player has 0 coins
        if (journey.getPlayer().getSupplies().getCoin() == 0) {
            showFoundCoinsPopup(journey.getPlayer().getSupplies(), imageStack);
        }

        Button saloonBtn = new Button("Enter Saloon");
        Button leaveBtn = new Button("Leave Town");
        Button storeBtn = new Button("Enter General Store");
        saloonBtn.setStyle("-fx-font-size: 22px; -fx-font-family: 'Sancreek'; -fx-background-color: black; -fx-text-fill: limegreen; -fx-border-color: limegreen; -fx-border-radius: 5px;");
        leaveBtn.setStyle("-fx-font-size: 22px; -fx-font-family: 'Sancreek'; -fx-background-color: black; -fx-text-fill: limegreen; -fx-border-color: limegreen; -fx-border-radius: 5px;");
        storeBtn.setStyle("-fx-font-size: 22px; -fx-font-family: 'Sancreek'; -fx-background-color: black; -fx-text-fill: limegreen; -fx-border-color: limegreen; -fx-border-radius: 5px;");
        Region leftSpacer = new Region();
        Region rightSpacer = new Region();
        HBox.setHgrow(leftSpacer, javafx.scene.layout.Priority.ALWAYS);
        HBox.setHgrow(rightSpacer, javafx.scene.layout.Priority.ALWAYS);
        HBox buttonBox = new HBox(20, saloonBtn, leftSpacer, leaveBtn, rightSpacer, storeBtn);
        buttonBox.setAlignment(Pos.BOTTOM_CENTER);
        buttonBox.setPadding(new Insets(0, 0, 10, 0));
        buttonBox.prefWidthProperty().bind(this.widthProperty());

        leaveBtn.setOnAction(event -> {
            primaryStage.getScene().setRoot(new JourneyPane(journey));
        });
        saloonBtn.setOnAction(event -> {
            // Animate sprite to left (saloon door)
            double targetX = -imageStack.getWidth() / 2 + imageStack.getWidth() * 0.20;
            TranslateTransition tt = new TranslateTransition(Duration.millis(1500), spriteView);
            tt.setToX(targetX);
            tt.setOnFinished(e -> primaryStage.getScene().setRoot(new SaloonPane(journey, primaryStage)));
            tt.play();
        });
        storeBtn.setOnAction(event -> {
            // Animate sprite to right (general store door)
            double targetX = imageStack.getWidth() / 2 - imageStack.getWidth() * 0.18;
            TranslateTransition tt = new TranslateTransition(Duration.millis(1500), spriteView);
            tt.setToX(targetX);
            tt.setOnFinished(e -> {
                if (journey.getCurrentTown() == null) {
                    if (!journey.getTowns().isEmpty()) {
                        journey.setCurrentTown(journey.getTowns().get(0));
                        journey.resetEventStretch();
                    } else {
                        System.out.println("No towns available. Cannot open GeneralStorePane.");
                        return;
                    }
                }
                primaryStage.getScene().setRoot(
                    new GeneralStorePane(journey, journey.getCurrentTown(), journey.getPlayer(), primaryStage, () -> {
                        primaryStage.getScene().setRoot(new TownPane(journey, primaryStage));
                    })
                );
            });
            tt.play();
        });

        // Add main content to pane
        VBox topBox = new VBox(imageStack, buttonBox);
        topBox.setAlignment(Pos.TOP_CENTER);
        setTop(topBox);
    }

    private void showFoundCoinsPopup(Supplies supplies, StackPane imageStack) {
        VBox popup = new VBox(24);
        popup.setAlignment(Pos.CENTER);
        popup.setStyle("-fx-background-color: black; -fx-border-color: limegreen; -fx-border-width: 4px; -fx-border-radius: 16px; -fx-background-radius: 16px;");
        popup.setPadding(new Insets(32, 32, 32, 32)); 
        Label msg = new Label("You found 5 coins on the ground");
        msg.setStyle("-fx-font-family: 'Sancreek'; -fx-font-size: 32px; -fx-text-fill: limegreen;");
        msg.setWrapText(true);
        msg.setMaxWidth(500); 
        Button contBtn = new Button("Continue");
        contBtn.setStyle("-fx-font-family: 'Sancreek'; -fx-font-size: 22px; -fx-text-fill: limegreen; -fx-background-color: black; -fx-border-color: limegreen; -fx-border-width: 2px; -fx-border-radius: 8px;");
        contBtn.setOnAction(e -> {
            supplies.setCoin(supplies.getCoin() + 5);
            imageStack.getChildren().remove(popup);
        });
        popup.getChildren().addAll(msg, contBtn);
        popup.setMaxWidth(540); 
        popup.setMaxHeight(300); 
        imageStack.getChildren().add(popup);
        StackPane.setAlignment(popup, Pos.TOP_CENTER);
        popup.translateYProperty().bind(imageStack.heightProperty().multiply(0.13)); // Position below dayLabel
    }
}