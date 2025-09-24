import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class StartUpPane extends BorderPane {
	private Player player; // holds the player name

    public StartUpPane() {
        this.setStyle("-fx-background-color: black;");

        // Game title 
        Label title = new Label("Hit, Partner.");
        title.setStyle("-fx-font-family: 'Rockwell'; -fx-font-size: 120px; -fx-font-weight: bold; -fx-text-fill: limegreen;");

        HBox topPane = new HBox(title);
        topPane.setAlignment(Pos.CENTER_LEFT);
        topPane.setPrefHeight(120);
        topPane.setPadding(new Insets(0, 0, 0, 50));
        setTop(topPane);

        // Player inputs their name
        Label nameLabel = new Label("Enter your name:");
        nameLabel.setStyle("-fx-text-fill: limegreen; -fx-font-size: 18px;");
        TextField nameField = new TextField();
        nameField.setPrefWidth(250);

        HBox nameBox = new HBox(10, nameLabel, nameField);
        nameBox.setAlignment(Pos.CENTER_LEFT);
        nameBox.setPadding(new Insets(20, 0, 0, 50));

        // Menu buttons
        Button newGameBtn = new Button("New Game");
        Button continueBtn = new Button("Continue Game");
        Button settingsBtn = new Button("Settings");

        String buttonStyle = "-fx-border-radius: 5px; -fx-border-color: limegreen; "
                           + "-fx-font-size: 24px; -fx-font-family: 'Rockwell'; "
                           + "-fx-background-color: black; -fx-text-fill: limegreen;";
        newGameBtn.setStyle(buttonStyle);
        continueBtn.setStyle(buttonStyle);
        settingsBtn.setStyle(buttonStyle);

        newGameBtn.setPrefWidth(250);
        continueBtn.setPrefWidth(250);
        settingsBtn.setPrefWidth(250);
        
        // Menu Button actions
        newGameBtn.setOnAction(e -> {
        	String playerName = nameField.getText();
        	
        	// in case user clicked New Game without an input
        	if (playerName.isEmpty()) {
        		playerName = "Player";
        	}
        	
        	// Create a new Player with the default coins/health
        	player = new Player(playerName);
        	
            // Print to console for now (proof of interaction)
            System.out.println("New game started!");
            System.out.println("Player name: " + player.getName());
            System.out.println("Coins: " + player.getCoin());
            System.out.println("Health: " + player.getHealth());
        });
        
        // if Continue is clicked
        continueBtn.setOnAction(e -> {
            System.out.println("Continue Game button clicked (feature not implemented yet).");
        });
        
        // if Settings is clicked
        settingsBtn.setOnAction(e -> {
            System.out.println("Settings button clicked (feature not implemented yet).");
        });

        // Scene for menu layout
        VBox menuBox = new VBox(20, newGameBtn, continueBtn, settingsBtn);
        menuBox.setAlignment(Pos.CENTER_LEFT);
        menuBox.setPadding(new Insets(20, 0, 0, 50));

        // Combine input and menu for alignment
        VBox centerBox = new VBox(30, nameBox, menuBox);
        centerBox.setAlignment(Pos.TOP_LEFT);

        setCenter(centerBox);
    }
}
