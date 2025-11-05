import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
    private Button betBtn, hitBtn, standBtn, exitBtn;
    private BlackJackGame game;
    private boolean inProgress;
    private Label coinsLabel, standingLabel;
    private javafx.scene.control.TextField betField;
    private int currentBet = 0;
    private Player player;

    public SaloonPane(GameJourney journey, Stage primaryStage) {
        setStyle("-fx-background-color: black;");
        if (cardBack == null) cardBack = new Image(CARD_BACK_PATH);
        player = journey.getPlayer();
        game = new BlackJackGame(player);
        inProgress = false;

        Label dealerLabel = new Label("Dealer");
        dealerLabel.setFont(Font.font("Rockwell", 25)); 
        dealerLabel.setTextFill(Color.LIMEGREEN);
        dealerCards = new HBox(10);
        dealerCards.setAlignment(Pos.CENTER);
        VBox dealerBox = new VBox(5, dealerLabel, dealerCards); 
        dealerBox.setAlignment(Pos.CENTER);

        Label playerLabel = new Label("Player");
        playerLabel.setFont(Font.font("Rockwell", 25));
        playerLabel.setTextFill(Color.LIMEGREEN);
        playerCards = new HBox(10); 
        playerCards.setAlignment(Pos.CENTER);
        VBox playerBox = new VBox(5, playerLabel, playerCards);
        playerBox.setAlignment(Pos.CENTER);

        statusLabel = new Label("");
        statusLabel.setFont(Font.font("Rockwell", 20)); 
        statusLabel.setTextFill(Color.LIMEGREEN);
        statusLabel.setAlignment(Pos.CENTER);

        betBtn = new Button("BET");
        hitBtn = new Button("HIT");
        standBtn = new Button("STAND");
        exitBtn = new Button("EXIT");
        styleGameButton(betBtn);
        styleGameButton(hitBtn);
        styleGameButton(standBtn);
        styleGameButton(exitBtn);
        HBox buttonBox = new HBox(20, betBtn, hitBtn, standBtn, exitBtn); 
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 0, 10, 0)); 

        coinsLabel = new Label();
        standingLabel = new Label();
        styleInfoLabel(coinsLabel);
        styleInfoLabel(standingLabel);
        betField = new javafx.scene.control.TextField();
        betField.setText("0");
        betField.setPrefWidth(50); 
        betField.setFont(Font.font("Rockwell", 20)); 
        betField.setStyle("-fx-background-color: black; -fx-text-fill: limegreen; -fx-border-color: limegreen; -fx-border-width: 1px;");
        betField.textProperty().addListener((obs, oldVal, newVal) -> validateBetInput());
        HBox betControlBox = new HBox(20, betField, betBtn); 
        betControlBox.setAlignment(Pos.CENTER_LEFT);
        HBox infoBox = new HBox(20, coinsLabel, betControlBox, standingLabel); 
        infoBox.setAlignment(Pos.CENTER);
        infoBox.setPadding(new Insets(5, 0, 10, 0)); 

        VBox mainBox = new VBox(15, dealerBox, playerBox, statusLabel, buttonBox, infoBox); 
        mainBox.setAlignment(Pos.TOP_CENTER);
        mainBox.setPadding(new Insets(10, 0, 0, 0)); 
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
        btn.setFont(Font.font("Rockwell", 20)); 
        btn.setStyle("-fx-background-color: black; -fx-text-fill: limegreen; -fx-border-color: limegreen; -fx-border-radius: 5px;");
        btn.setPrefWidth(100);
    }
    private void styleInfoLabel(Label lbl) {
        lbl.setFont(Font.font("Rockwell", 20)); 
        lbl.setTextFill(Color.LIMEGREEN);
    }
    private void setButtonVisibility(boolean bet, boolean hit, boolean stand) {
        betBtn.setVisible(bet);
        hitBtn.setVisible(hit);
        standBtn.setVisible(stand);
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
        updateAllDisplays();
        statusLabel.setText("Your move: Hit or Stand?");
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
        updateAllDisplays();
        int playerValue = game.getPlayerHand().getBestValue();
        int dealerValue = game.getDealerHand().getBestValue();
        String result = game.getGameResult();
        statusLabel.setText(String.format("Player: %d  Dealer: %d\n%s", playerValue, dealerValue, result));
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
    private ImageView getCardBackImageView() {
        ImageView iv = new ImageView(cardBack);
        iv.setFitWidth(90);
        iv.setFitHeight(130);
        iv.setPreserveRatio(true);
        return iv;
    }
}