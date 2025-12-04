public class WheelEvent extends Event {
    public WheelEvent() {
        super("I Broke the Gosh-dang Wheel: The wheel of the wagon broke clean off!", 10);
        addRequiredItem("coin", 10);
        addRequiredItem("spare part", 1);
        setPenaltyDescription("If you don't make the repair, this is going to be a long night...");
    }
    @Override
    public String getEventType() { return "wheel"; }
    @Override
    public String getOutcomeMessage(String option, String outcome) {
        switch (option) {
            case "coin": return "You buy a spare wheel off a passerby.";
            case "spare part": return "You made the repair.";
            case "none":
                switch (outcome) {
                    case "sick": return "A family member is sick from sleeping on the cold ground.";
                    case "help": return "A friendly stranger repaired your wagon.";
                }
        }
        return "";
    }
    @Override
    public String handleDoNothing(Supplies supplies, GameJourney journey) {
        double roll = Math.random();
        if (roll < 0.75) {
            return getOutcomeMessage("none", "help");
        } else {
            if (affectedMember != null) affectedMember.setSickOrInjured(true);
            return getOutcomeMessage("none", "sick");
        }
    }
}