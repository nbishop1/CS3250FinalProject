import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class JourneyPane extends BorderPane {
    private GameJourney journey;
    private VBox familyBox;
    private Label dayLabel;
    private Label suppliesLabel;
    private Label feedbackLabel;

    public JourneyPane(GameJourney journey) {
        this.journey = journey;
        this.setStyle("-fx-background-color: black;");

        dayLabel = new Label();
        dayLabel.setStyle("-fx-font-size: 32px; -fx-text-fill: limegreen;");
        updateDayLabel();
        setTop(dayLabel);
        BorderPane.setMargin(dayLabel, new Insets(20,0,0,20));

        suppliesLabel = new Label();
        suppliesLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: limegreen;");
        updateSuppliesLabel();
        setLeft(suppliesLabel);
        BorderPane.setMargin(suppliesLabel, new Insets(20,0,0,20));

        familyBox = new VBox(10);
        familyBox.setPadding(new Insets(20));
        updateFamilyBox();
        setCenter(familyBox);

        Button nextDayBtn = new Button("Next Day");
        nextDayBtn.setStyle("-fx-font-size: 20px; -fx-background-color: limegreen; -fx-text-fill: black;");
        nextDayBtn.setOnAction(e -> {
            journey.nextDay();
            updateDayLabel();
            updateSuppliesLabel();
            updateFamilyBox();
            feedbackLabel.setText("Day progressed. Events and needs updated.");
        });
        setBottom(nextDayBtn);
        BorderPane.setMargin(nextDayBtn, new Insets(20));

        feedbackLabel = new Label("");
        feedbackLabel.setStyle("-fx-text-fill: limegreen; -fx-font-size: 16px;");
        setRight(feedbackLabel);
        BorderPane.setMargin(feedbackLabel, new Insets(20));
    }

    private void updateDayLabel() {
        dayLabel.setText("Day: " + journey.getDay());
    }

    private void updateSuppliesLabel() {
        Supplies s = journey.getPlayer().getSupplies();
        suppliesLabel.setText("Supplies:\nFood: " + s.getFood() + "\nWater: " + s.getWater() + "\nMedicine: " + s.getMedicine() + "\nAmmo: " + s.getAmmo() + "\nSpare Parts: " + s.getSpareParts() + "\nCoins: " + s.getCoin());
    }

    private void updateFamilyBox() {
        familyBox.getChildren().clear();
        for (FamilyMember member : journey.getFamily().getMembers()) {
            Label memberLabel = new Label(member.getName() + " - Health: " + member.getHealth() + (member.isSickOrInjured() ? " (Sick/Injured)" : ""));
            memberLabel.setStyle("-fx-text-fill: green; -fx-font-size: 18px;");
            HBox actions = new HBox(10);
            Button feedBtn = new Button("Feed");
            feedBtn.setStyle("-fx-border-color: limegreen; -fx-text-fill: limegreen; -fx-background-color: black; -fx-border-radius: 5px; -fx-font-size: 16px;");
            feedBtn.setOnAction(e -> {
                journey.getPlayer().getSupplies().eatFood(1);
                member.feed();
                updateSuppliesLabel();
                updateFamilyBox();
                feedbackLabel.setText(member.getName() + " was fed.");
            });
            Button waterBtn = new Button("Give Water");
            waterBtn.setStyle("-fx-border-color: limegreen; -fx-text-fill: limegreen; -fx-background-color: black; -fx-border-radius: 5px; -fx-font-size: 16px;");
            waterBtn.setOnAction(e -> {
                journey.getPlayer().getSupplies().drinkWater(1);
                member.giveWater();
                updateSuppliesLabel();
                updateFamilyBox();
                feedbackLabel.setText(member.getName() + " was given water.");
            });
            Button healBtn = new Button("Heal");
            healBtn.setStyle("-fx-border-color: limegreen; -fx-text-fill: limegreen; -fx-background-color: black; -fx-border-radius: 5px; -fx-font-size: 16px;");
            healBtn.setOnAction(e -> {
                journey.getPlayer().getSupplies().useMedicine(1);
                member.heal();
                updateSuppliesLabel();
                updateFamilyBox();
                feedbackLabel.setText(member.getName() + " was healed.");
            });
            actions.getChildren().addAll(feedBtn, waterBtn, healBtn);
            VBox memberBox = new VBox(5, memberLabel, actions);
            familyBox.getChildren().add(memberBox);
        }
    }
}