import javafx.geometry.Insets;
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

        Label settingsLabel = new Label("Game Play");
        settingsLabel.setStyle("-fx-font-family: 'Rockwell'; -fx-font-size: 48px; -fx-font-weight: bold; -fx-text-fill: limegreen;");
        this.setStyle("-fx-background-color: black;");

        String gameplay = "Each family member requires food and water in different intervals:\n" +
        					"Luke and Jessie require water every day and food every other.\n" +
        					"Uncle and Mary require water every two days and food every third.\n" +
        					"The player requires water every three days and food every fourth.\n" +
        					"Ignoring someone's needs for three days straight will result in their death.";

        Label introLabel = new Label(gameplay);
        introLabel.setStyle("-fx-text-fill: limegreen; -fx-font-size: 22px; -fx-font-family: 'Rockwell';");
        introLabel.setWrapText(true);

        Button backBtn = new Button("Back");
        backBtn.setStyle("-fx-font-size: 24px; -fx-background-color: limegreen; -fx-text-fill: black; -fx-font-family: 'Rockwell';");
        backBtn.setOnAction(event -> {
            StartUpPane startUpPane = new StartUpPane(primaryStage);
            primaryStage.getScene().setRoot(startUpPane);
        });

        VBox centerBox = new VBox(30, settingsLabel, introLabel, backBtn);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(60, 60, 60, 60));
        setCenter(centerBox);
    }
}