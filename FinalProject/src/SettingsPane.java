import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SettingsPane extends BorderPane {
    private Stage primaryStage;
    private javafx.scene.Parent previousPane;

    public SettingsPane(Stage primaryStage, javafx.scene.Parent previousPane) {
        this.primaryStage = primaryStage;
        this.previousPane = previousPane;
        this.setStyle("-fx-background-color: black;");
		FontLibrary.addFont("Quintessential", "fonts/Quintessential-Regular.ttf");
		FontLibrary.addFont("Sancreek", "fonts/Sancreek-Regular.ttf");

        Label settingsLabel = new Label("Game Play");
        settingsLabel.setStyle("-fx-font-family: 'Sancreek'; -fx-font-size: 48px; -fx-font-weight: bold; -fx-text-fill: limegreen;");
        this.setStyle("-fx-background-color: black;");

        String gameplay = "Each family member requires food and water in different intervals:\n" +
        					"Luke and Jessie require water every day and food every other.\n" +
        					"Uncle and Mary require water every two days and food every third.\n" +
        					"The player requires water every three days and food every fourth.\n" +
        					"Ignoring someone's needs for three days straight will result in their death.";

        Label introLabel = new Label(gameplay);
        introLabel.setStyle("-fx-text-fill: limegreen; -fx-font-size: 22px; -fx-font-family: 'Quintessential';");
        introLabel.setWrapText(true);

        Button backBtn = new Button("Back");
        backBtn.setStyle("-fx-font-size: 24px; -fx-background-color: limegreen; -fx-text-fill: black; -fx-font-family: 'Sancreek';");
        backBtn.setOnAction(event -> {
            if (previousPane != null) {
                primaryStage.getScene().setRoot(previousPane);
            } else {
                StartUpPane startUpPane = new StartUpPane(primaryStage);
                primaryStage.getScene().setRoot(startUpPane);
            }
        });

        VBox centerBox = new VBox(30, settingsLabel, introLabel, backBtn);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(60, 60, 60, 60));
        setCenter(centerBox);
    }
}