import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EventScene extends VBox {
    private Event event;
    private GameJourney journey;
    private Label descLabel;
    private VBox optionsBox;
    private VBox messageBox;
    private Runnable onEventResolved;
    private Stage stage;
    

    public EventScene(Event event, GameJourney journey, Runnable onEventResolved, Stage stage) {
        this.event = event;
        this.journey = journey;
        this.onEventResolved = onEventResolved;
        this.stage = stage;
        setStyle("-fx-background-color: black;");
		FontLibrary.addFont("Quintessential", "fonts/Quintessential-Regular.ttf");
		FontLibrary.addFont("Sancreek", "fonts/Sancreek-Regular.ttf");
        setPadding(new Insets(40));
        setSpacing(24);
        descLabel = new Label(event.getDescription());
        descLabel.setStyle("-fx-font-family: 'Sancreek'; -fx-font-size: 32px; -fx-text-fill: limegreen;");
        Text details = new Text(event.getPenaltyDescription());
        details.setFill(Color.LIMEGREEN);
        details.setStyle("-fx-font-family: 'Quintessential'; -fx-font-size: 20px; -fx-fill: limegreen;");
        getChildren().addAll(descLabel, details);
        optionsBox = new VBox(16);
        optionsBox.setPadding(new Insets(20, 0, 0, 0));
        getChildren().add(optionsBox);
        addOptions();
    }

    private void showMessage(String message) {
        getChildren().remove(optionsBox);
        messageBox = new VBox(16);
        messageBox.setPadding(new Insets(20, 0, 0, 0));
        Label msgLabel = new Label(message);
        msgLabel.setStyle("-fx-font-family: 'Quintessential'; -fx-font-size: 22px; -fx-text-fill: limegreen;");
        Button continueBtn = new Button("Continue");
        continueBtn.setStyle("-fx-font-family: 'Sancreek'; -fx-font-size: 20px; -fx-text-fill: limegreen; -fx-background-color: black; -fx-border-color: limegreen; -fx-border-width: 2px;");
        continueBtn.setOnAction(e -> {
            if (onEventResolved != null) onEventResolved.run();
        });
        messageBox.getChildren().addAll(msgLabel, continueBtn);
        getChildren().add(messageBox);
    }

    private void addOptions() {
        Supplies supplies = journey.getPlayer().getSupplies();
        boolean hasOption = false;
        String eventType = event.getEventType();
        for (String item : event.getRequiredItems().keySet()) {
            int qty = event.getRequiredItems().get(item);
            boolean canUse = false;
            switch (item.toLowerCase()) {
                case "food": canUse = supplies.getFood() >= qty; break;
                case "water": canUse = supplies.getWater() >= qty; break;
                case "medicine": canUse = supplies.getMedicine() >= qty; break;
                case "ammo": canUse = supplies.getAmmo() >= qty; break;
                case "coin": canUse = supplies.getCoin() >= qty; break;
                case "spare part": canUse = supplies.getSpareParts() >= qty; break;
            }
            Button optionBtn = new Button("Use " + qty + " " + item);
            optionBtn.setStyle("-fx-font-family: 'Sancreek'; -fx-font-size: 20px; -fx-text-fill: limegreen; -fx-background-color: black; -fx-border-color: limegreen; -fx-border-width: 2px;");
            if (!canUse) {
                optionBtn.setDisable(true);
                optionBtn.setOpacity(0.4);
            } else {
                hasOption = true;
                final String usedItem = item.toLowerCase();
                optionBtn.setOnAction(e -> {
                    event.handleOption(usedItem, qty, supplies, journey);
                    String msg = event.getOutcomeMessage(usedItem, "");
                    showMessage(msg);
                });
            }
            optionsBox.getChildren().add(optionBtn);
        }
        // Always add 'Do nothing' button for every event
        Button skipBtn = new Button("Do nothing");
        skipBtn.setStyle("-fx-font-family: 'Sancreek'; -fx-font-size: 20px; -fx-text-fill: limegreen; -fx-background-color: black; -fx-border-color: limegreen; -fx-border-width: 2px;");
        skipBtn.setOnAction(e -> {
            String msg = event.handleDoNothing(supplies, journey);
            showMessage(msg);
        });
        optionsBox.getChildren().add(skipBtn);
        if (eventType.equals("stranger")) {
            Button approachBtn = new Button("Approach the merchant");
            approachBtn.setStyle("-fx-font-family: 'Sancreek'; -fx-font-size: 20px; -fx-text-fill: limegreen; -fx-background-color: black; -fx-border-color: limegreen; -fx-border-width: 2px;");
            approachBtn.setOnAction(e -> {
                Town town = journey.getCurrentTown();
                Player player = journey.getPlayer();
                stage.getScene().setRoot(new GeneralStorePane(journey, town, player, stage, () -> {
                    // Return to journey pane after exiting store
                    stage.getScene().setRoot(new JourneyPane(journey));
                }));
            });
            optionsBox.getChildren().add(approachBtn);
        }
    }
}