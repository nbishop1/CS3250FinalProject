import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EndGamePane extends BorderPane {
    private Stage primaryStage;
    private EndingType endingType;
    private GameJourney journey;
    private String playerName;

    public EndGamePane(Stage primaryStage, EndingType endingType, GameJourney journey, String playerName) {
        this.primaryStage = primaryStage;
        this.endingType = endingType;
        this.journey = journey;
        this.playerName = playerName;
        this.setStyle("-fx-background-color: black;");
        FontLibrary.addFont("Sancreek", "fonts/Sancreek-Regular.ttf");
        FontLibrary.addFont("Quintessential", "fonts/Quintessential-Regular.ttf");

        // Top: Ending type
        Label typeLabel = new Label(endingType.getType());
        typeLabel.setStyle("-fx-font-family: 'Sancreek'; -fx-font-size: 64px; -fx-text-fill: limegreen;");
        typeLabel.setAlignment(Pos.CENTER);

        // Description
        Label descLabel = new Label(endingType.getDescription());
        descLabel.setStyle("-fx-font-family: 'Quintessential'; -fx-font-size: 28px; -fx-text-fill: limegreen;");
        descLabel.setWrapText(true);
        descLabel.setAlignment(Pos.CENTER);

        VBox topBox = new VBox(10, typeLabel, descLabel);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(40, 0, 20, 0));
        setTop(topBox);

        // Headstones
        int[] headstoneIndices = endingType.getHeadstoneIndices();
        HBox headstoneBox = new HBox(20);
        headstoneBox.setAlignment(Pos.CENTER);
        for (int idx : headstoneIndices) {
            Image img = new Image("images/Headstones/Headstone" + idx + ".png");
            ImageView imgView = new ImageView(img);
            imgView.setFitHeight(120);
            imgView.setPreserveRatio(true);
            headstoneBox.getChildren().add(imgView);
        }
        VBox centerBox = new VBox(headstoneBox);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(20, 0, 0, 0));
        setCenter(centerBox);

        // Restart button
        Button restartBtn = new Button("Restart");
        restartBtn.setStyle("-fx-font-family: 'Sancreek'; -fx-font-size: 28px; -fx-background-color: limegreen; -fx-text-fill: black;");
        restartBtn.setOnAction(e -> {
            NameEntryPane nameEntryPane = new NameEntryPane(primaryStage);
            primaryStage.getScene().setRoot(nameEntryPane);
        });
        VBox bottomBox = new VBox(restartBtn);
        bottomBox.setAlignment(Pos.BOTTOM_RIGHT);
        bottomBox.setPadding(new Insets(0, 40, 40, 0));
        setBottom(bottomBox);
    }
}