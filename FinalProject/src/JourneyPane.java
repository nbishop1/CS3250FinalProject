import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class JourneyPane extends VBox {
    private GameJourney journey;
    private Label dayLabel;
    private HBox familyStatusBox;

    public JourneyPane(GameJourney journey) {
        this.journey = journey;
        this.setStyle("-fx-background-color: black;");
        this.setSpacing(0);

        // Top half: HBox split into supplies (33%) and day/animation (66%)
        HBox topHalf = new HBox();
        topHalf.setPrefHeight(350);
        topHalf.setSpacing(0);

        // Supplies box (left 33%)
        VBox suppliesBox = new VBox(10);
        suppliesBox.setPadding(new Insets(30, 30, 30, 30));
        suppliesBox.setStyle("-fx-font-family: 'Rockwell'; -fx-border-color: limegreen; -fx-border-width: 3px; -fx-background-color: black;");
        suppliesBox.setPrefWidth(400);
        suppliesBox.setAlignment(javafx.geometry.Pos.TOP_LEFT);
        Label suppliesTitle = new Label("Supplies");
        suppliesTitle.setStyle("-fx-font-family: 'Rockwell'; -fx-font-size: 30px; -fx-text-fill: limegreen;");
        Label suppliesLabel = new Label();
        suppliesLabel.setStyle("-fx-font-family: 'Rockwell'; -fx-font-size: 22px; -fx-text-fill: limegreen;");
        suppliesLabel.setWrapText(true);
        suppliesLabel.setMaxWidth(Double.MAX_VALUE);
        suppliesLabel.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(suppliesLabel, Priority.ALWAYS);
        updateSuppliesLabel(suppliesLabel);
        suppliesBox.getChildren().addAll(suppliesTitle, suppliesLabel);
        topHalf.getChildren().add(suppliesBox);

        // Right 66%: VBox for day label, animation placeholder, and Next Day button
        VBox rightTop = new VBox();
        rightTop.setPadding(new Insets(20, 40, 0, 0));
        rightTop.setPrefWidth(800);
        rightTop.setAlignment(javafx.geometry.Pos.TOP_RIGHT);
        dayLabel = new Label();
        dayLabel.setStyle("-fx-font-family: 'Rockwell'; -fx-font-size: 48px; -fx-text-fill: limegreen;");
        updateDayLabel();
        rightTop.getChildren().add(dayLabel);
        Region animationSpace = new Region();
        VBox.setVgrow(animationSpace, Priority.ALWAYS);
        rightTop.getChildren().add(animationSpace);
        Button nextDayButton = new Button("Next Day");
        nextDayButton.setStyle("-fx-font-family: 'Rockwell'; -fx-font-size: 20px; -fx-text-fill: limegreen; -fx-background-color: black; -fx-border-color: limegreen; -fx-border-width: 2px;");
        nextDayButton.setOnAction(event -> {
            journey.nextDay();
            updateDayLabel();
            updateSuppliesLabel(suppliesLabel);
            updateFamilyStatusBox();
        });
        HBox buttonBox = new HBox(nextDayButton);
        buttonBox.setAlignment(javafx.geometry.Pos.BOTTOM_RIGHT);
        buttonBox.setPadding(new Insets(20, 0, 20, 0));
        rightTop.getChildren().add(buttonBox);
        topHalf.getChildren().add(rightTop);
        HBox.setHgrow(rightTop, Priority.ALWAYS);

        // Limegreen line separator
        Rectangle line = new Rectangle();
        line.setHeight(3);
        line.setFill(Color.LIMEGREEN);
        line.widthProperty().bind(this.widthProperty());

        // Bottom half: HBox for family statuses only
        familyStatusBox = new HBox();
        familyStatusBox.setPadding(new Insets(40, 0, 0, 0));
        familyStatusBox.setAlignment(javafx.geometry.Pos.CENTER);
        updateFamilyStatusBox();

        // Add to root VBox
        this.getChildren().addAll(topHalf, line, familyStatusBox);
        VBox.setVgrow(topHalf, Priority.ALWAYS);
        VBox.setVgrow(familyStatusBox, Priority.ALWAYS);
    }

    private void updateDayLabel() {
        dayLabel.setText("Day " + journey.getDay());
    }

    private void updateSuppliesLabel(Label suppliesLabel) {
        Supplies s = journey.getPlayer().getSupplies();
        StringBuilder sb = new StringBuilder();
        if (s.getFood() > 0) sb.append("Food: ").append(s.getFood()).append("\n");
        if (s.getWater() > 0) sb.append("Water: ").append(s.getWater()).append("\n");
        if (s.getMedicine() > 0) sb.append("Medicine: ").append(s.getMedicine()).append("\n");
        if (s.getAmmo() > 0) sb.append("Ammo: ").append(s.getAmmo()).append("\n");
        if (s.getSpareParts() > 0) sb.append("Spare Parts: ").append(s.getSpareParts()).append("\n");
        if (s.getCoin() > 0) sb.append("Coins: ").append(s.getCoin()).append("\n");
        suppliesLabel.setText(sb.toString().trim());
    }

    private void updateFamilyStatusBox() {
        familyStatusBox.getChildren().clear();
        for (FamilyMember member : journey.getFamily().getMembers()) {
            VBox cardBox = new VBox(10);
            cardBox.setAlignment(javafx.geometry.Pos.CENTER);
            cardBox.setPadding(new Insets(10));
            cardBox.setStyle("-fx-font-family: 'Rockwell'; -fx-border-color: limegreen; -fx-border-width: 3px; -fx-background-color: black; -fx-border-radius: 12px; -fx-background-radius: 12px;");
            cardBox.setMaxWidth(Double.MAX_VALUE);
            cardBox.setMinHeight(80);

            Label statusLabel = new Label(member.getName() + " " + member.getStatusText());
            statusLabel.setStyle("-fx-font-family: 'Rockwell'; -fx-font-size: 20px; -fx-text-fill: limegreen;");
            statusLabel.setWrapText(true);
            statusLabel.setMaxWidth(Double.MAX_VALUE);
            VBox.setVgrow(statusLabel, Priority.ALWAYS);
            cardBox.getChildren().add(statusLabel);

            VBox buttonBox = new VBox(5);
            buttonBox.setAlignment(javafx.geometry.Pos.CENTER);
            if (member.isAlive()) {
                if (journey.canFeed(member)) {
                    Button feedBtn = new Button("Feed");
                    feedBtn.setStyle("-fx-font-family: 'Rockwell'; -fx-font-size: 16px; -fx-text-fill: limegreen; -fx-background-color: black; -fx-border-color: limegreen; -fx-border-width: 2px;");
                    feedBtn.setOnAction(event -> {
                        journey.feedMember(member);
                        updateSuppliesLabel(((Label)((VBox)((HBox)getChildren().get(0)).getChildren().get(0)).getChildren().get(1)));
                        updateFamilyStatusBox();
                    });
                    buttonBox.getChildren().add(feedBtn);
                }
                if (journey.canGiveWater(member)) {
                    Button waterBtn = new Button("Give Water");
                    waterBtn.setStyle("-fx-font-family: 'Rockwell'; -fx-font-size: 16px; -fx-text-fill: limegreen; -fx-background-color: black; -fx-border-color: limegreen; -fx-border-width: 2px;");
                    waterBtn.setOnAction(event -> {
                        journey.giveWaterToMember(member);
                        updateSuppliesLabel(((Label)((VBox)((HBox)getChildren().get(0)).getChildren().get(0)).getChildren().get(1)));
                        updateFamilyStatusBox();
                    });
                    buttonBox.getChildren().add(waterBtn);
                }
                if (journey.canHeal(member)) {
                    Button healBtn = new Button("Heal");
                    healBtn.setStyle("-fx-font-family: 'Rockwell'; -fx-font-size: 16px; -fx-text-fill: limegreen; -fx-background-color: black; -fx-border-color: limegreen; -fx-border-width: 2px;");
                    healBtn.setOnAction(event -> {
                        journey.healMember(member);
                        updateSuppliesLabel(((Label)((VBox)((HBox)getChildren().get(0)).getChildren().get(0)).getChildren().get(1)));
                        updateFamilyStatusBox();
                    });
                    buttonBox.getChildren().add(healBtn);
                }
            }
            if (!buttonBox.getChildren().isEmpty()) {
                cardBox.getChildren().add(buttonBox);
            }
            familyStatusBox.getChildren().add(cardBox);
            HBox.setHgrow(cardBox, Priority.ALWAYS);
        }
    }
}