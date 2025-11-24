import java.util.ArrayList;

public class GameJourney {

    private int day; // Current day of the journey
    private boolean gameOver; 
    private Player player; 
    private Family family;
    private ArrayList<Town> towns; // List of towns
    private Town currentTown;
    private EventManager eventManager;
    private Event currentEvent;

    // Constructor
    public GameJourney(Player player, Family family) {
        this.day = 1;
        this.gameOver = false;
        this.player = player;
        this.family = family;
        this.towns = new ArrayList<>();
        this.currentTown = null;
        this.eventManager = new EventManager();
        this.currentEvent = null;
        // Initialize 9 towns and 1 destination
        BlackJackGame blackjackGame = new BlackJackGame(player);
        for (int i = 1; i <= 9; i++) {
            new Town("Town " + i, blackjackGame, this);
        }
        new Town("Destination", blackjackGame, this);
        // The first town is set as current by Town constructor if currentTown is null
    }

    // Getters
    public int getDay() { return day; }
    public boolean isGameOver() { return gameOver; }
    public Player getPlayer() { return player; }
    public Family getFamily() { return family; }
    public ArrayList<Town> getTowns() { return towns; }
    public Town getCurrentTown() {
        return currentTown;
    }
    public EventManager getEventManager() { return eventManager; }
    public Event getCurrentEvent() { return currentEvent; }

    // Setters
    public void setCurrentTown(Town town) {
        this.currentTown = town;
    }
    public void setCurrentEvent(Event event) { this.currentEvent = event; }

    // Call this when arriving at a town to reset event stretch
    public void resetEventStretch() {
        eventManager.resetForNewStretch(day);
    }

    // Advance day and trigger event if needed
    public void nextDay() {
        day++;
        family.nextDay();
        // Only trigger event if allowed
        if (eventManager.canTriggerEvent(day)) {
            Event event = eventManager.getRandomEvent(day, family);
            if (event != null) {
                setCurrentEvent(event);
                // UI should now display the event scene and handle resolution
                return;
            }
        }
        setCurrentEvent(null); // No event today
    }

    // End the journey (set game over)
    public void endJourney() {
        gameOver = true;
    }

    // Action checks and methods for feeding, giving water, and healing family members
    public boolean canFeed(FamilyMember member) {
        return member.isAlive() && member.needsFood() && player.getSupplies().getFood() > 0;
    }

    public boolean canGiveWater(FamilyMember member) {
        return member.isAlive() && member.needsWater() && player.getSupplies().getWater() > 0;
    }

    public boolean canHeal(FamilyMember member) {
        return member.isAlive() && member.isSickOrInjured() && member.needsMedicine() && player.getSupplies().getMedicine() > 0;
    }

    public void feedMember(FamilyMember member) {
        if (canFeed(member)) {
            member.feed();
            player.getSupplies().setFood(player.getSupplies().getFood() - 1);
        }
    }

    public void giveWaterToMember(FamilyMember member) {
        if (canGiveWater(member)) {
            member.giveWater();
            player.getSupplies().setWater(player.getSupplies().getWater() - 1);
        }
    }

    public void healMember(FamilyMember member) {
        if (canHeal(member)) {
            member.heal();
            player.getSupplies().setMedicine(player.getSupplies().getMedicine() - 1);
        }
    }
    
}