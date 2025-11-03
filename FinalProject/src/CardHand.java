import java.util.ArrayList;

public class CardHand {
    private ArrayList<Card> cards;

    // default constructor
    public CardHand() {
        cards = new ArrayList<>();
    }

    //getters (no setters needed)
    public ArrayList<Card> getCards() {
        return cards;
    }

    // method signatures

    public void addCard(Card c) {
        cards.add(c);
    }

    public int getBestValue() {
        int total = 0;
        int aceCount = 0;
        for (Card c : cards) {
            total += c.getValue();
            if (c.getCardRank().equals("A")) aceCount++;
        }
        // Adjust for aces if bust
        while (total > 21 && aceCount > 0) {
            total -= 10;
            aceCount--;
        }
        return total;
    }

    public boolean isBust() {
        return getBestValue() > 21;
    }

    public void clear() {
        cards.clear();
    }
}