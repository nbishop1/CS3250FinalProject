import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NameEntryPane extends BorderPane {
    private Stage primaryStage;

    public NameEntryPane(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.setStyle("-fx-background-color: black;");

        Label promptLabel = new Label("Enter your name:");
        promptLabel.setStyle("-fx-text-fill: limegreen; -fx-font-size: 28px; -fx-font-family: 'Rockwell';");

        TextField nameField = new TextField();
        nameField.setPrefWidth(300);
        nameField.setStyle("-fx-font-size: 22px; -fx-font-family: 'Rockwell';");

        Button continueBtn = new Button("Continue");
        continueBtn.setStyle("-fx-font-size: 24px; -fx-background-color: limegreen; -fx-text-fill: black; -fx-font-family: 'Rockwell';");
        continueBtn.setOnAction(event -> {
            String playerName = nameField.getText().trim();
            if (!playerName.isEmpty()) {
                Player player = new Player(playerName);
                Family family = Family.createDefaultFamily(playerName);
                GameJourney journey = new GameJourney(player, family);
                IntroPane introPane = new IntroPane(primaryStage, journey, playerName);
                primaryStage.getScene().setRoot(introPane);
            } else {
                nameField.setPromptText("What's your name, partner?");
            }
        });

        VBox centerBox = new VBox(30, promptLabel, nameField, continueBtn);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(80, 80, 80, 80));
        setCenter(centerBox);
    }
}