import java.util.ArrayList;

public class Town {
    private static int storeCounter = 0; // Unique storeId for each town
    private String name;
    private GeneralStore store;
    private BlackJackGame blackjackGame;
    private ArrayList<Event> events;

    // default constructor
    public Town(String name, BlackJackGame blackjackGame, GameJourney journey) {
        this.setName(name);
        this.store = new GeneralStore(storeCounter++); // Unique store per town
        this.blackjackGame = blackjackGame;
        journey.getTowns().add(this);
        if (journey.getCurrentTown() == null) {
            journey.setCurrentTown(this);
            journey.resetEventStretch();
        }
    }


    // setters and getters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public GeneralStore getStore() {
        return store;
    }

    public BlackJackGame getBlackjackGame() {
        return blackjackGame;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void resetStoreStock() {
        store.resetStock();
    }

}