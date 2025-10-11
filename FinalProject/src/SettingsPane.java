import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SettingsPane extends BorderPane {
    private Stage primaryStage;

    public SettingsPane(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.setStyle("-fx-background-color: black;");

        Label settingsLabel = new Label("Game Settings");
        settingsLabel.setStyle("-fx-font-family: 'Rockwell'; -fx-font-size: 48px; -fx-font-weight: bold; -fx-text-fill: limegreen;");

        Button backBtn = new Button("Back");
        backBtn.setStyle("-fx-border-color: limegreen; -fx-text-fill: limegreen; -fx-background-color: black; -fx-border-radius: 5px; -fx-font-size: 16px;");
        backBtn.setOnAction(e -> {
            StartUpPane startUpPane = new StartUpPane(primaryStage);
            primaryStage.getScene().setRoot(startUpPane);
        });

        VBox centerBox = new VBox(40, settingsLabel, backBtn);
        centerBox.setAlignment(Pos.CENTER);
        setCenter(centerBox);
    }
}
