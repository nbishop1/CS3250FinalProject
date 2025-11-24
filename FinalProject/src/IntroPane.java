import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class IntroPane extends BorderPane {
    private GameJourney journey;
    private Stage primaryStage;
    private String playerName;

    public IntroPane(Stage primaryStage, GameJourney journey, String playerName) {
        this.primaryStage = primaryStage;
        this.journey = journey;
        this.playerName = playerName;
        this.setStyle("-fx-background-color: black;");
		FontLibrary.addFont("IMFell", "fonts/IMFellDWPicaSC-Regular.ttf");

        String introText = "Howdy, " + playerName + "... " +
                "You and your family are traveling to the west in search of riches in the form of gold. " +
                "You have no skills, no talents, and hardly any money, so you resort to luck. Every fifth day, you will reach a new town where you will gamble to earn coins to fund your travels... Or lose it all and starve to death.\n\n" +
                "Starting inventory: Coin: 20  Food: 10  Water: 15  Ammunition: 2\n\n" +
                "Every day, you must delegate food, water, or medicine depending on the needs of your family. " +
                "Events may occur, and you must use your supplies wisely to survive.\n\n" +
                "Reach the 10th town after 50 days to win. Good luck!";

        Label introLabel = new Label(introText);
        introLabel.setStyle("-fx-text-fill: limegreen; -fx-font-size: 22px;");
        introLabel.setFont(Font.font("IMFell"));
        introLabel.setWrapText(true);

        VBox centerBox = new VBox(30, introLabel);
        centerBox.setPadding(new Insets(60, 60, 60, 60));
        setCenter(centerBox);

        Button continueBtn = new Button("Continue");
        continueBtn.setStyle("-fx-font-size: 24px; -fx-background-color: limegreen; -fx-text-fill: black; -fx-font-family: 'Rockwell';");
        continueBtn.setOnAction(e -> {
            JourneyPane journeyPane = new JourneyPane(journey);
            primaryStage.getScene().setRoot(journeyPane);
        });
        setBottom(continueBtn);
        BorderPane.setMargin(continueBtn, new Insets(30));
    }
}