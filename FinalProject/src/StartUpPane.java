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
		FontLibrary.addFont("Sancreek", "fonts/Sancreek-Regular.ttf");

        // Game title 
        Label title = new Label("Hit, Partner.");
        double initialFontSize = 150; // Title grows with window size, window is initially 800px wide
        title.setStyle("-fx-font-family: 'Sancreek'; -fx-font-size: " + initialFontSize + "px; -fx-text-fill: limegreen;");

        HBox topPane = new HBox(title);
        topPane.setAlignment(Pos.CENTER); // Center horizontally
        topPane.setPrefHeight(120);
        // Remove left padding for true centering
        setTop(topPane);
        
        // Title resizing logic: Got scaffolding from ChatGPT, adjusted to my needs
        // Responsive font size 
        this.widthProperty().addListener((obs, oldVal, newVal) -> {
            double width = newVal.doubleValue();
            double scaleFactor = 0.15; // 15% of window width
            double minSize = 40; // Minimum font size
            double maxSize = 250; // Maximum font size
            double fontSize = Math.max(minSize, Math.min(maxSize, width * scaleFactor));
            title.setStyle("-fx-font-family: 'Sancreek'; -fx-font-size: " + fontSize + "px; -fx-text-fill: limegreen;");
        });

        // New Game button
        Button newGameBtn = new Button("New Game");
        newGameBtn.setStyle("-fx-border-color: limegreen; -fx-text-fill: limegreen; -fx-background-color: black; -fx-border-radius: 5px; -fx-font-size: 32px; -fx-font-family: 'Sancreek';");
        newGameBtn.setOnAction(event -> {
            NameEntryPane nameEntryPane = new NameEntryPane(primaryStage);
            primaryStage.getScene().setRoot(nameEntryPane);
        });

        // Settings button
        Button settingsBtn = new Button("Settings");
        settingsBtn.setStyle("-fx-border-color: limegreen; -fx-text-fill: limegreen; -fx-background-color: black; -fx-border-radius: 5px; -fx-font-size: 32px; -fx-font-family: 'Sancreek';");
        settingsBtn.setOnAction(event -> {
            SettingsPane settingsPane = new SettingsPane(primaryStage, this);
            primaryStage.getScene().setRoot(settingsPane);
        });

        VBox centerBox = new VBox(40, newGameBtn, settingsBtn);
        centerBox.setAlignment(Pos.CENTER);
        setCenter(centerBox);

    }
}