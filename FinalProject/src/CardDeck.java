import java.util.ArrayList;
import java.util.Collections;

public class CardDeck {
    private ArrayList<Card> cards;

    // default constructor
    public CardDeck() {
        this.cards = new ArrayList<>();
        reset();
    }

    // setters and getters
    public ArrayList<Card> getCards() {
        return cards;
    }

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
}