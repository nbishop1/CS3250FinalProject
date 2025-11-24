import java.util.ArrayList;
import java.util.Collections;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CardDeck {
    private ArrayList<Card> cards;

    // default constructor
    public CardDeck() {
        this.cards = new ArrayList<>();
        reset();
        cardBackImage = new Image(getClass().getResourceAsStream(CARD_BACK_PATH));
    }

    // setters and getters
    public ArrayList<Card> getCards() {
        return cards;
    }

    private static final String CARD_BACK_PATH = "/images/CardBack.png";
    private Image cardBackImage;

    // method signatures

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card draw() {
        if (cards.isEmpty()) return null;
        return cards.remove(cards.size() - 1);
    }

    public void reset() {
        cards.clear();
        for (Card.Suit suit : Card.Suit.values()) {
            for (int i = 2; i <= 10; i++) {
                cards.add(new Card(suit, String.valueOf(i), i));
            }
            cards.add(new Card(suit, "J", 10));
            cards.add(new Card(suit, "Q", 10));
            cards.add(new Card(suit, "K", 10));
            cards.add(new Card(suit, "A", 11));
        }
        shuffle();
    }

    public ImageView getCardBackImageView() {
        ImageView iv = new ImageView(cardBackImage);
        iv.setFitWidth(90);
        iv.setFitHeight(130);
        iv.setPreserveRatio(true);
        return iv;
    }
}