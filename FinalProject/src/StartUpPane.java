import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StartUpPane extends BorderPane {
    private Stage primaryStage;

    public StartUpPane(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.setStyle("-fx-background-color: black;");

        // Game title 
        Label title = new Label("Hit, Partner.");
        double initialFontSize = 120; // Title grows with window size, window is initially 800px wide
        title.setStyle("-fx-font-family: 'Rockwell'; -fx-font-size: " + initialFontSize + "px; -fx-font-weight: bold; -fx-text-fill: limegreen;");

        HBox topPane = new HBox(title);
        topPane.setAlignment(Pos.CENTER); // Center horizontally
        topPane.setPrefHeight(120);
        // Remove left padding for true centering
        setTop(topPane);
        
        // Title resizing logic
        // Responsive font size binding
        this.widthProperty().addListener((obs, oldVal, newVal) -> {
            double width = newVal.doubleValue();
            double scaleFactor = 0.15; // 15% of window width
            double minSize = 48; // Minimum font size
            double maxSize = 200; // Maximum font size
            double fontSize = Math.max(minSize, Math.min(maxSize, width * scaleFactor));
            title.setStyle("-fx-font-family: 'Rockwell'; -fx-font-size: " + fontSize + "px; -fx-font-weight: bold; -fx-text-fill: limegreen;");
        });

        // New Game button
        Button newGameBtn = new Button("New Game");
        newGameBtn.setStyle("-fx-font-size: 32px; -fx-background-color: limegreen; -fx-text-fill: black; -fx-font-family: 'Rockwell';");
        newGameBtn.setOnAction(e -> {
            NameEntryPane nameEntryPane = new NameEntryPane(primaryStage);
            primaryStage.getScene().setRoot(nameEntryPane);
        });

        // Settings button
        Button settingsBtn = new Button("Settings");
        settingsBtn.setStyle("-fx-font-size: 32px; -fx-background-color: limegreen; -fx-text-fill: black; -fx-font-family: 'Rockwell';");
        settingsBtn.setOnAction(e -> {
            SettingsPane settingsPane = new SettingsPane(primaryStage);
            primaryStage.getScene().setRoot(settingsPane);
        });

        VBox centerBox = new VBox(40, newGameBtn, settingsBtn);
        centerBox.setAlignment(Pos.CENTER);
        setCenter(centerBox);

    }
}