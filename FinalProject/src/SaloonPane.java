import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SaloonPane extends BorderPane {
    private static final String CARD_BACK_PATH = "images/CardBack.png";
    private static Image cardBack;

    private HBox dealerCards;
    private HBox playerCards;
    private Label statusLabel;
    private Button betBtn, hitBtn, standBtn, exitBtn, allInBtn;
    private BlackJackGame game;
    private boolean inProgress;
    private Label coinsLabel, standingLabel;
    private Label resultLabel; 
    private javafx.scene.control.TextField betField;
    private int currentBet = 0;
    private Player player;
    private StackPane actionArea; // Holds either buttons or result label
    private HBox buttonBox; 
    private HBox betControlBox; 

    public SaloonPane(GameJourney journey, Stage primaryStage) {
        setStyle("-fx-background-color: black;");
        if (cardBack == null) cardBack = new Image(CARD_BACK_PATH);
        player = journey.getPlayer();
        game = new BlackJackGame(player);
        inProgress = false;

        Label dealerLabel = new Label("Dealer");
        dealerLabel.setFont(Font.font("Sancreek", 40));
        dealerLabel.setTextFill(Color.LIMEGREEN);
        dealerCards = new HBox(30);
        dealerCards.setAlignment(Pos.CENTER);
        dealerCards.setPrefHeight(220);
        VBox dealerBox = new VBox(15, dealerLabel, dealerCards);
        dealerBox.setAlignment(Pos.CENTER);
        dealerBox.setPrefHeight(300);

        Label playerLabel = new Label("Player");
        playerLabel.setFont(Font.font("Sancreek", 40));
        playerLabel.setTextFill(Color.LIMEGREEN);
        playerCards = new HBox(30);
        playerCards.setAlignment(Pos.CENTER);
        playerCards.setPrefHeight(220);
        VBox playerBox = new VBox(15, playerLabel, playerCards);
        playerBox.setAlignment(Pos.CENTER);
        playerBox.setPrefHeight(300);

        statusLabel = new Label("");
        statusLabel.setFont(Font.font("Sancreek", 30));
        statusLabel.setTextFill(Color.LIMEGREEN);
        statusLabel.setAlignment(Pos.CENTER);
        statusLabel.setPrefHeight(60); 
        statusLabel.setWrapText(true);

        resultLabel = new Label(""); 
        resultLabel.setFont(Font.font("Quintessential", 32));
        resultLabel.setTextFill(Color.LIMEGREEN);
        resultLabel.setAlignment(Pos.CENTER);
        resultLabel.setPrefHeight(40);
        resultLabel.setWrapText(true);
        resultLabel.setVisible(false); // Initially hidden

        betBtn = new Button("BET");
        allInBtn = new Button("ALL IN");
        hitBtn = new Button("HIT");
        standBtn = new Button("STAND");
        exitBtn = new Button("EXIT");
        styleGameButton(betBtn);
        styleGameButton(allInBtn);
        styleGameButton(hitBtn);
        styleGameButton(standBtn);
        styleGameButton(exitBtn);
        allInBtn.setOnAction(e -> handleAllInBet());
        buttonBox = new HBox(40, hitBtn, standBtn);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20, 0, 20, 0));
        buttonBox.setPrefHeight(80);
        buttonBox.setStyle("-fx-font-family: 'Sancreek';");
        buttonBox.setVisible(false); // Initially hidden

        actionArea = new StackPane(buttonBox, resultLabel);
        actionArea.setPrefHeight(80);
        actionArea.setAlignment(Pos.CENTER);

        betField = new javafx.scene.control.TextField();
        betField.setPrefWidth(100);
        betField.setFont(Font.font("Sancreek", 24));
        betField.setStyle("-fx-background-color: black; -fx-text-fill: limegreen; -fx-border-color: limegreen; -fx-border-radius: 8px;");
        betField.setAlignment(Pos.CENTER);
        betField.setText("0");
        betField.textProperty().addListener((obs, oldVal, newVal) -> validateBetInput());
        betControlBox = new HBox(10, betField, betBtn, allInBtn);
        betControlBox.setAlignment(Pos.CENTER);
        betControlBox.setPrefHeight(60);

        coinsLabel = new Label("Coins: 0");
        styleInfoLabel(coinsLabel);
        standingLabel = new Label("Current Standing: ");
        styleInfoLabel(standingLabel);

        HBox infoBox = new HBox(40, coinsLabel, betControlBox, standingLabel, exitBtn);
        infoBox.setAlignment(Pos.CENTER);
        infoBox.setPadding(new Insets(10, 0, 20, 0));
        infoBox.setPrefHeight(60);

        VBox mainBox = new VBox(30, dealerBox, playerBox, statusLabel, actionArea, infoBox);
        mainBox.setAlignment(Pos.TOP_CENTER);
        mainBox.setPadding(new Insets(20, 0, 0, 0));
        mainBox.setPrefWidth(1200);
        mainBox.setPrefHeight(900);
        VBox.setVgrow(dealerBox, javafx.scene.layout.Priority.ALWAYS);
        VBox.setVgrow(playerBox, javafx.scene.layout.Priority.ALWAYS);
        setCenter(mainBox);

        betBtn.setOnAction(e -> startNewGame());
        hitBtn.setOnAction(e -> playerHit());
        standBtn.setOnAction(e -> playerStand());
        exitBtn.setOnAction(e -> primaryStage.getScene().setRoot(new TownPane(journey, primaryStage)));

        setButtonVisibility(true, false, false);
        updateAllDisplays();
        statusLabel.setText("Press BET to start!");
        validateBetInput();
    }

    private void styleGameButton(Button btn) {
        btn.setFont(Font.font("Sancreek", 30));
        btn.setStyle("-fx-background-color: black; -fx-text-fill: limegreen; -fx-border-color: limegreen; -fx-border-radius: 8px;");
        btn.setPrefWidth(140);
        btn.setPrefHeight(60);
    }
    private void styleInfoLabel(Label lbl) {
        lbl.setFont(Font.font("Sancreek", 30));
        lbl.setTextFill(Color.LIMEGREEN);
    }
    private void setButtonVisibility(boolean bet, boolean hit, boolean stand) {
        betBtn.setVisible(bet);
        allInBtn.setVisible(bet);
        buttonBox.setVisible(hit || stand); // Show buttonBox if hit or stand
        resultLabel.setVisible(false); // Hide resultLabel when showing buttons
    }

    private void validateBetInput() {
        String betText = betField.getText();
        int bet;
        try {
            bet = Integer.parseInt(betText);
        } catch (NumberFormatException e) {
            betBtn.setDisable(true);
            return;
        }
        betBtn.setDisable(!game.canPlaceBet(bet));
    }

    private void startNewGame() {
        String betText = betField.getText();
        int bet;
        try {
            bet = Integer.parseInt(betText);
        } catch (NumberFormatException e) {
            statusLabel.setText("Please enter a valid number for your bet.");
            return;
        }
        String error = game.getBetError(bet);
        if (error != null) {
            statusLabel.setText(error);
            return;
        }
        boolean started = game.startGameWithBet(bet);
        if (!started) {
            statusLabel.setText(game.getBetError(bet));
            return;
        }
        setButtonVisibility(false, true, true);
        buttonBox.setVisible(true); // Show buttons
        resultLabel.setVisible(false);
        updateAllDisplays();
        statusLabel.setText("Your move: Hit or Stand?");
        resultLabel.setText(""); // Clear previous round result
    }

    private void playerHit() {
        if (!game.isInProgress()) return;
        game.playerHit();
        updateAllDisplays();
        if (!game.isInProgress()) {
            endGame();
        }
    }
    private void playerStand() {
        if (!game.isInProgress()) return;
        game.playerStand();
        updateAllDisplays();
        endGame();
    }
    private void endGame() {
        setButtonVisibility(true, false, false);
        game.endGame();
        updateAllDisplays();
        int playerValue = game.getPlayerHand().getBestValue();
        int dealerValue = game.getDealerHand().getBestValue();
        String result = game.getGameResult();
        statusLabel.setText(String.format("Player: %d  Dealer: %d", playerValue, dealerValue));
        buttonBox.setVisible(false); // Hide buttons
        resultLabel.setVisible(true); // Show result
        if (result != null && !result.isEmpty()) {
            resultLabel.setText(result);
        } else {
            resultLabel.setText("(Game result unavailable)");
        }
    }

    private void updateAllDisplays() {
        updateDealerCardDisplay();
        updatePlayerCardDisplay();
        updateInfoLabels();
    }
    private void updateDealerCardDisplay() {
        dealerCards.getChildren().clear();
        var dealerHand = game.getDealerHand().getCards();
        if (game.isInProgress()) {
            for (int i = 0; i < dealerHand.size(); i++) {
                if (i == 0) {
                    dealerCards.getChildren().add(dealerHand.get(i).getCardNode(true));
                } else {
                    dealerCards.getChildren().add(dealerHand.get(i).getCardNode(false));
                }
            }
        } else {
            for (var card : dealerHand) {
                dealerCards.getChildren().add(card.getCardNode(true));
            }
        }
    }
    private void updatePlayerCardDisplay() {
        playerCards.getChildren().clear();
        var playerHand = game.getPlayerHand();
        if (playerHand == null || playerHand.getCards() == null) {
            return;
        }
        for (var card : playerHand.getCards()) {
            playerCards.getChildren().add(card.getCardNode(true));
        }
    }
    private void updateInfoLabels() {
        coinsLabel.setText("Coins: " + game.getPlayerCoins());
        validateBetInput();
        var playerHand = game.getPlayerHand();
        if (playerHand == null) {
            standingLabel.setText("Current Standing: ");
        } else {
            standingLabel.setText("Current Standing: " + playerHand.getBestValue());
        }
    }
    private void handleAllInBet() {
        int coins = game.getPlayerCoins();
        if (coins > 0) {
            betField.setText(String.valueOf(coins));
            startNewGame();
        } else {
            statusLabel.setText("You have no coins to go all in.");
        }
    }

}