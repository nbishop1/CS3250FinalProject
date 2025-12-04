public class SnakeEvent extends Event {
    public SnakeEvent() {
        super("Snake Bite: A rattlesnake was found in the wagon!", 15);
        addRequiredItem("medicine", 1);
        addRequiredItem("food", 2);
        addRequiredItem("ammo", 1);
        setPenaltyDescription("Failure to get rid of the snake can get someone hurt...");
    }
    @Override
    public String getEventType() { return "snake"; }
    @Override
    public String getOutcomeMessage(String option, String outcome) {
        switch (option) {
            case "medicine": return "A family member was bit but you gave them an antidote.";
            case "food": return "You lure the snake away with some jerky.";
            case "ammo": return "You blast the snake. It is no more.";
            case "none":
                switch (outcome) {
                    case "bit": return "A family member was bit! They need medical attention!";
                    case "away": return "The snake slithers away causing no harm.";
                }
        }
        return "";
    }
    @Override
    public String handleDoNothing(Supplies supplies, GameJourney journey) {
        double roll = Math.random();
        if (roll < 0.5) {
            return getOutcomeMessage("none", "away");
        } else {
            applyPenalty();
            return getOutcomeMessage("none", "bit");
        }
    }
}