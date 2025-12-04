public class OutlawEvent extends Event {
    public OutlawEvent() {
        super("Caught by Outlaws: A posy of outlaws have circled the wagon!", 20);
        addRequiredItem("ammo", 2);
        addRequiredItem("food", 5);
        addRequiredItem("water", 4);
        addRequiredItem("coin", 10);
        setPenaltyDescription("Failure to fend them off may mean you pay the ultimate price...");
    }
    @Override
    public String getEventType() { return "outlaw"; }
    @Override
    public String getOutcomeMessage(String option, String outcome) {
        switch (option) {
            case "ammo": return "You fire two shots in the air and the outlaws go running.";
            case "food": return "You share hardtack with the outlaws and they leave peacefully.";
            case "water": return "You offer the outlaws some jugs of water which they take and leave peacefully.";
            case "coin": return "You pay the outlaws to continue your journey.";
            case "none":
                switch (outcome) {
                    case "hurt": return "A family member was grazed by a bullet! They need medical attention.";
                    case "talk": return "It seems there's been a misunderstanding, the outlaws hear you out and leave you to continue the journey.";
                    case "dead": return "You died by outlaws.";
                }
        }
        return "";
    }
    @Override
    public String handleDoNothing(Supplies supplies, GameJourney journey) {
        double roll = Math.random();
        if (roll < 0.5) {
            return getOutcomeMessage("none", "talk");
        } else if (roll < 0.95) {
            applyPenalty();
            return getOutcomeMessage("none", "hurt");
        } else {
            journey.endJourney();
            return getOutcomeMessage("none", "dead");
        }
    }
}