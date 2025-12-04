public class StrangerEvent extends Event {
    public StrangerEvent() {
        super("Hello, Stranger: A mysterious caravan playing soft music on a gramophone sits on the path's edge... Approach?", 0);
        setPenaltyDescription("A merchant appears. You may approach to trade, or pass by without incident.");
    }
    @Override
    public String getEventType() { return "stranger"; }
    @Override
    public String getOutcomeMessage(String option, String outcome) {
        switch (option) {
            case "approach": return ""; // handled by store pane
            case "ignore": return "You decide to ignore the stranger.";
        }
        return "";
    }
    @Override
    public String handleDoNothing(Supplies supplies, GameJourney journey) {
        return getOutcomeMessage("ignore", "");
    }
}