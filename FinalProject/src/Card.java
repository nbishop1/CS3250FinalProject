import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Card {
	public enum Suit { HEARTS, DIAMONDS, CLUBS, SPADES }
	private Suit suit;
	private String cardRank;
	private int value;
	
	// default constructor
	public Card(Suit suit, String cardRank, int value) {
		this.suit = suit;
		this.cardRank = cardRank;
		this.value = value;
	}
	
	// setters and getters
	public Suit getSuit() {
		return suit;
	}
	
	public String getCardRank() {
		return cardRank;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public Node getCardNode(boolean faceUp) {
        double cardWidth = 90;
        double cardHeight = 130;
        double arc = 15;
        StackPane pane = new StackPane();
        if (!faceUp) {
            try {
                Image cardBack = new Image("images/CardBack.png");
                ImageView iv = new ImageView(cardBack);
                iv.setFitWidth(cardWidth);
                iv.setFitHeight(cardHeight);
                iv.setPreserveRatio(true);
                pane.getChildren().add(iv);
            } catch (Exception e) {
                System.out.println("Error loading card back image: " + e.getMessage());
            }
            return pane;
        }
        Rectangle rect = new Rectangle(cardWidth, cardHeight);
        rect.setArcWidth(arc);
        rect.setArcHeight(arc);
        rect.setFill(Color.BLACK);
        rect.setStroke(Color.LIMEGREEN);
        rect.setStrokeWidth(2);
        // Rank top-left
        Text rankTL = new Text(cardRank);
        rankTL.setFont(Font.font("Consolas", 18));
        rankTL.setFill(Color.LIMEGREEN);
        rankTL.setTranslateX(-cardWidth/2 + 18);
        rankTL.setTranslateY(-cardHeight/2 + 22);
        // Rank bottom-right
        Text rankBR = new Text(cardRank);
        rankBR.setFont(Font.font("Consolas", 18));
        rankBR.setFill(Color.LIMEGREEN);
        rankBR.setTranslateX(cardWidth/2 - 18);
        rankBR.setTranslateY(cardHeight/2 - 22);
        // Suit symbol center
        Text suitText = new Text(getSuitSymbol());
        suitText.setFont(Font.font("Consolas", 38));
        suitText.setFill(Color.LIMEGREEN);
        pane.getChildren().addAll(rect, rankTL, rankBR, suitText);
        return pane;
	}

	private String getSuitSymbol() {
	    switch (suit) {
	        case HEARTS: return "\u2665";
	        case DIAMONDS: return "♦";
	        case CLUBS: return "♣";
	        case SPADES: return "♠";
	        default: return "?";
	    }
	}
}