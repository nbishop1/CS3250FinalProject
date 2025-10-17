import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SaloonPane extends BorderPane {
    public SaloonPane(GameJourney journey, Stage primaryStage) {
        this.setStyle("-fx-background-color: black;");
        Button backBtn = new Button("Back");
        backBtn.setStyle("-fx-font-size: 22px; -fx-background-color: black; -fx-text-fill: limegreen; -fx-border-color: limegreen; -fx-border-radius: 5px;");
        backBtn.setOnAction(event -> primaryStage.getScene().setRoot(new TownPane(journey, primaryStage)));
        setCenter(backBtn);
        BorderPane.setAlignment(backBtn, Pos.CENTER);
    }
}
