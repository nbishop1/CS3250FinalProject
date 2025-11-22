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
        setPadding(new Insets(40));
        setSpacing(24);
        descLabel = new Label(event.getDescription());
        descLabel.setStyle("-fx-font-family: 'Rockwell'; -fx-font-size: 32px; -fx-text-fill: limegreen;");
        Text details = new Text(event.getPenaltyDescription());
        details.setFill(Color.LIMEGREEN);
        details.setStyle("-fx-font-size: 20px; -fx-fill: limegreen;");
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
        msgLabel.setStyle("-fx-font-family: 'Rockwell'; -fx-font-size: 22px; -fx-text-fill: limegreen;");
        Button continueBtn = new Button("Continue");
        continueBtn.setStyle("-fx-font-family: 'Rockwell'; -fx-font-size: 20px; -fx-text-fill: limegreen; -fx-background-color: black; -fx-border-color: limegreen; -fx-border-width: 2px;");
        continueBtn.setOnAction(e -> {
            if (onEventResolved != null) onEventResolved.run();
        });
        messageBox.getChildren().addAll(msgLabel, continueBtn);
        getChildren().add(messageBox);
    }

    private String getOutcomeMessage(String eventType, String option, String outcome) {
        switch (eventType) {
            case "outlaw":
                switch (option) {
                    case "ammo": return "You fire two shots in the air and the outlaws go running.";
                    case "food": return "You share hardtack with the outlaws and they leave peacefully.";
                    case "water": return "You offer the outlaws some jugs of water which they take and leave peacefully.";
                    case "coin": return "You pay the outlaws to continue your journey.";
                    case "none":
                        switch (outcome) {
                            case "hurt": return "A family member was grazed by a bullet! They need medical attention.";
                            case "talk": return "It seems there's been a misunderstanding, the outlaws hear you out and leave you to continue the journey.";
                            case "dead": return "TODO: Game Over Pane (player killed by outlaws).";
                        }
                }
                break;
            case "wheel":
                switch (option) {
                    case "coin": return "You buy a spare wheel off a passerby.";
                    case "spare part": return "You made the repair.";
                    case "none":
                        switch (outcome) {
                            case "sick": return "A family member is sick from sleeping on the cold ground.";
                            case "help": return "A friendly stranger repaired your wagon.";
                        }
                }
                break;
            case "snake":
                switch (option) {
                    case "medicine": return "A family member was bit but you gave them an antidote.";
                    case "food": return "You lure the snake away with some jerky.";
                    case "ammo": return "You blast the snake. It is no more.";
                    case "none":
                        switch (outcome) {
                            case "bit": return "A family member was bit! They need medical attention!";
                            case "away": return "The snake slithers away causing no harm.";
                        }
                }
                break;
            case "stranger":
                switch (option) {
                    case "approach": return ""; // handled by store pane
                    case "ignore": return "You decide to ignore the stranger.";
                }
                break;
        }
        return "";
    }

    private void addOptions() {
        Supplies supplies = journey.getPlayer().getSupplies();
        boolean hasOption = false;
        String desc = event.getDescription().toLowerCase();
        final String eventType;
        if (desc.contains("outlaw")) eventType = "outlaw";
        else if (desc.contains("wheel")) eventType = "wheel";
        else if (desc.contains("snake")) eventType = "snake";
        else if (desc.contains("stranger")) eventType = "stranger";
        else eventType = "";
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
            optionBtn.setStyle("-fx-font-family: 'Rockwell'; -fx-font-size: 20px; -fx-text-fill: limegreen; -fx-background-color: black; -fx-border-color: limegreen; -fx-border-width: 2px;");
            if (!canUse) {
                optionBtn.setDisable(true);
                optionBtn.setOpacity(0.4);
            } else {
                hasOption = true;
                final String usedItem = item.toLowerCase();
                optionBtn.setOnAction(e -> {
                    // Remove item and skip penalty
                    switch (usedItem) {
                        case "food": supplies.eatFood(qty); break;
                        case "water": supplies.drinkWater(qty); break;
                        case "medicine": supplies.useMedicine(qty); break;
                        case "ammo": supplies.useAmmo(qty); break;
                        case "coin": supplies.spendCoin(qty); break;
                        case "spare part": supplies.setSpareParts(supplies.getSpareParts() - qty); break;
                    }
                    String msg = getOutcomeMessage(eventType, usedItem, "");
                    showMessage(msg);
                });
            }
            optionsBox.getChildren().add(optionBtn);
        }
        // Always add 'Do nothing' button for every event
        Button skipBtn = new Button("Do nothing");
        skipBtn.setStyle("-fx-font-family: 'Rockwell'; -fx-font-size: 20px; -fx-text-fill: limegreen; -fx-background-color: black; -fx-border-color: limegreen; -fx-border-width: 2px;");
        skipBtn.setOnAction(e -> {
            // Random outcome logic
            String msg = "";
            if (eventType.equals("outlaw")) {
                double roll = Math.random();
                if (roll < 0.5) {
                    msg = getOutcomeMessage(eventType, "none", "talk");
                } else if (roll < 0.95) {
                    event.applyPenalty();
                    msg = getOutcomeMessage(eventType, "none", "hurt");
                } else {
                    journey.endJourney();
                    msg = getOutcomeMessage(eventType, "none", "dead");
                }
            } else if (eventType.equals("wheel")) {
                double roll = Math.random();
                if (roll < 0.75) {
                    msg = getOutcomeMessage(eventType, "none", "help");
                } else {
                    event.getAffectedMember().setSickOrInjured(true);
                    msg = getOutcomeMessage(eventType, "none", "sick");
                }
            } else if (eventType.equals("snake")) {
                double roll = Math.random();
                if (roll < 0.5) {
                    msg = getOutcomeMessage(eventType, "none", "away");
                } else {
                    event.applyPenalty();
                    msg = getOutcomeMessage(eventType, "none", "bit");
                }
            } else if (eventType.equals("stranger")) {
                msg = getOutcomeMessage(eventType, "ignore", "");
            }
            showMessage(msg);
        });
        optionsBox.getChildren().add(skipBtn);
        if (eventType.equals("stranger")) {
            Button approachBtn = new Button("Approach the merchant");
            approachBtn.setStyle("-fx-font-family: 'Rockwell'; -fx-font-size: 20px; -fx-text-fill: limegreen; -fx-background-color: black; -fx-border-color: limegreen; -fx-border-width: 2px;");
            approachBtn.setOnAction(e -> {
                // Open store pane
                Town town = journey.getCurrentTown();
                Player player = journey.getPlayer();
                stage.getScene().setRoot(new GeneralStorePane(journey, town, player, stage));
            });
            optionsBox.getChildren().add(approachBtn);
        }
    }

    private void resolveEvent(boolean usedItem) {
        // Deprecated: now handled in addOptions with showMessage
    }
}