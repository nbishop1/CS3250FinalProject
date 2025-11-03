import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SaloonPane extends BorderPane {
    private static final int CARD_COLS = 13;
    private static final int CARD_ROWS = 4;
    private static final int CARD_WIDTH = 1897; // px
    private static final int CARD_HEIGHT = 1877; // px
    private static final String CARD_SHEET_PATH = "images/cardDeck.png";

    private HBox dealerCards;
    private HBox playerCards;
    private Label statusLabel;
    private Button hitBtn, standBtn, dealBtn;
    private Image cardSheet;

    private BlackJackGame game;
    private CardHand playerHand;
    private boolean inProgress;

    public SaloonPane(GameJourney journey, Stage primaryStage) {
        this.setStyle("-fx-background-color: black;");
        VBox mainBox = new VBox(20);
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setPadding(new Insets(30));

        Label title = new Label("Blackjack");
        title.setFont(Font.font("Consolas", 36));
        title.setTextFill(Color.LIMEGREEN);

        dealerCards = new HBox(10);
        dealerCards.setAlignment(Pos.CENTER);
        Label dealerLabel = new Label("Dealer");
        dealerLabel.setTextFill(Color.LIMEGREEN);
        VBox dealerBox = new VBox(5, dealerLabel, dealerCards);
        dealerBox.setAlignment(Pos.CENTER);

        playerCards = new HBox(10);
        playerCards.setAlignment(Pos.CENTER);
        Label playerLabel = new Label("Player");
        playerLabel.setTextFill(Color.LIMEGREEN);
        VBox playerBox = new VBox(5, playerLabel, playerCards);
        playerBox.setAlignment(Pos.CENTER);

        statusLabel = new Label("");
        statusLabel.setFont(Font.font("Consolas", 18));
        statusLabel.setTextFill(Color.LIMEGREEN);
        statusLabel.setAlignment(Pos.CENTER);

        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        dealBtn = new Button("Deal");
        hitBtn = new Button("Hit");
        standBtn = new Button("Stand");
        styleGameButton(dealBtn);
        styleGameButton(hitBtn);
        styleGameButton(standBtn);
        buttonBox.getChildren().addAll(dealBtn, hitBtn, standBtn);

        mainBox.getChildren().addAll(title, dealerBox, playerBox, statusLabel, buttonBox);
        setCenter(mainBox);

        Button backBtn = new Button("Back");
        backBtn.setStyle("-fx-font-size: 22px; -fx-background-color: black; -fx-text-fill: limegreen; -fx-border-color: limegreen; -fx-border-radius: 5px;");
        backBtn.setOnAction(event -> primaryStage.getScene().setRoot(new TownPane(journey, primaryStage)));
        setBottom(backBtn);
        BorderPane.setAlignment(backBtn, Pos.CENTER);
        BorderPane.setMargin(backBtn, new Insets(20));

        // Load card sheet
        cardSheet = new Image(CARD_SHEET_PATH);

        // Initialize game logic
        game = new BlackJackGame();
        playerHand = new CardHand();
        inProgress = false;

        // Button actions
        dealBtn.setOnAction(e -> startNewGame());
        hitBtn.setOnAction(e -> playerHit());
        standBtn.setOnAction(e -> playerStand());
        hitBtn.setDisable(true);
        standBtn.setDisable(true);

        // Initial placeholder cards
        updateCardDisplay(dealerCards, java.util.Collections.emptyList());
        updateCardDisplay(playerCards, java.util.Collections.emptyList());
        statusLabel.setText("Press Deal to start!");
    }

    private void styleGameButton(Button btn) {
        btn.setStyle("-fx-font-size: 20px; -fx-background-color: black; -fx-text-fill: limegreen; -fx-border-color: limegreen; -fx-border-radius: 5px;");
        btn.setPrefWidth(100);
    }

    /**
     * Returns an ImageView for a card at the given row (suit) and column (rank) in the card sheet.
     */
    private ImageView getCardImageView(int row, int col) {
        WritableImage cardImg = new WritableImage(cardSheet.getPixelReader(),
                col * CARD_WIDTH, row * CARD_HEIGHT, CARD_WIDTH, CARD_HEIGHT);
        ImageView iv = new ImageView(cardImg);
        iv.setFitWidth(90);
        iv.setFitHeight(130);
        iv.setPreserveRatio(true);
        return iv;
    }

    /**
     * Returns the (row, col) indices for the card sheet image based on the Card object.
     */
    private int[] getCardSheetPosition(Card card) {
        int row;
        switch (card.getSuit()) {
            case CLUBS: row = 0; break;
            case DIAMONDS: row = 1; break;
            case HEARTS: row = 2; break;
            case SPADES: row = 3; break;
            default: row = 0; // fallback
        }
        int col;
        switch (card.getCardRank()) {
            case "10": col = 0; break;
            case "2": col = 1; break;
            case "3": col = 2; break;
            case "4": col = 3; break;
            case "5": col = 4; break;
            case "6": col = 5; break;
            case "7": col = 6; break;
            case "8": col = 7; break;
            case "9": col = 8; break;
            case "A": col = 9; break;
            case "J": col = 10; break;
            case "K": col = 11; break;
            case "Q": col = 12; break;
            default: col = 0; // fallback
        }
        return new int[] {row, col};
    }

    /**
     * Returns an ImageView for the given Card object.
     */
    private ImageView getCardImageView(Card card) {
        int[] pos = getCardSheetPosition(card);
        return getCardImageView(pos[0], pos[1]);
    }

    /**
     * Updates the given HBox to show the cards in the given hand.
     */
    private void updateCardDisplay(HBox cardBox, java.util.List<Card> hand) {
        cardBox.getChildren().clear();
        for (Card card : hand) {
            cardBox.getChildren().add(getCardImageView(card));
        }
    }

    private void startNewGame() {
        game.getDeck().reset();
        playerHand.clear();
        game.getDealer().getHand().clear();
        game.getDealer().startDeal(game.getDeck(), playerHand);
        inProgress = true;
        updateCardDisplay(playerCards, playerHand.getCards());
        updateCardDisplay(dealerCards, game.getDealer().getHand().getCards());
        statusLabel.setText("Your move: Hit or Stand?");
        hitBtn.setDisable(false);
        standBtn.setDisable(false);
        dealBtn.setDisable(true);
    }

    private void playerHit() {
        if (!inProgress) return;
        playerHand.addCard(game.getDeck().draw());
        updateCardDisplay(playerCards, playerHand.getCards());
        if (playerHand.isBust()) {
            endGame();
        }
    }

    private void playerStand() {
        if (!inProgress) return;
        game.getDealer().dealersTurn(game.getDeck());
        updateCardDisplay(dealerCards, game.getDealer().getHand().getCards());
        endGame();
    }

    private void endGame() {
        inProgress = false;
        hitBtn.setDisable(true);
        standBtn.setDisable(true);
        dealBtn.setDisable(false);
        int playerValue = playerHand.getBestValue();
        int dealerValue = game.getDealer().getHand().getBestValue();
        boolean playerBust = playerHand.isBust();
        boolean dealerBust = game.getDealer().getHand().isBust();
        String result;
        if (playerBust) {
            result = "You bust! Dealer wins.";
        } else if (dealerBust) {
            result = "Dealer busts! You win!";
        } else if (playerValue > dealerValue) {
            result = "You win!";
        } else if (playerValue < dealerValue) {
            result = "Dealer wins.";
        } else {
            result = "Push (tie).";
        }
        statusLabel.setText(String.format("Player: %d  Dealer: %d\n%s", playerValue, dealerValue, result));
    }
}