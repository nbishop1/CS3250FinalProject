import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class NameEntryPane extends BorderPane {
    private Stage primaryStage;

    public NameEntryPane(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.setStyle("-fx-background-color: black;");
		FontLibrary.addFont("Quintessential", "fonts/Quintessential-Regular.ttf");
		FontLibrary.addFont("Sancreek", "fonts/Sancreek-Regular.ttf");
        

        Label promptLabel = new Label("Enter your name:");
        promptLabel.setStyle("-fx-text-fill: limegreen; -fx-font-size: 50px; -fx-font-family: 'Sancreek';");

        TextField nameField = new TextField();
        nameField.setPrefWidth(150);
        nameField.setMaxWidth(300);
        nameField.setStyle("-fx-font-size: 50px; -fx-font-family: 'Quintessential'; -fx-background-color: black; -fx-text-fill: limegreen; -fx-border-color: limegreen; -fx-border-width: 3px; -fx-border-radius: 8px;");
        VBox.setVgrow(nameField, Priority.NEVER);

        Button continueBtn = new Button("Continue");
        continueBtn.setStyle("-fx-font-size: 30px; -fx-background-color: limegreen; -fx-text-fill: black; -fx-font-family: 'Sancreek';");
        continueBtn.setOnAction(event -> {
            String playerName = nameField.getText().trim();
            if (!playerName.isEmpty()) {
                Player player = new Player(playerName);
                Family family = new Family();
                family.addMember(player);
                family.addMember(new FamilyMember("Mary", 3, 2, 5));
                family.addMember(new FamilyMember("Uncle", 3, 2, 5));
                family.addMember(new FamilyMember("Luke", 2, 1, 3));
                family.addMember(new FamilyMember("Jessie", 2, 1, 3));
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