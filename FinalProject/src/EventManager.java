import java.util.*;

public class EventManager {
    private int lastTownDay = 0;
    private Set<String> occurredEvents = new HashSet<>();
    private Random random = new Random();

    public void resetForNewStretch(int currentDay) {
        occurredEvents.clear();
        lastTownDay = currentDay;
    }

    public boolean canTriggerEvent(int currentDay) {
        // No events in first 5 days
        return currentDay >= 6;
    }

    public Event getRandomEvent(int currentDay, Family family) {
        List<Event> possibleEvents = new ArrayList<>();
        if (!occurredEvents.contains("outlaws") && random.nextInt(100) < 5) {
            Event e = new Event(
                "Caught by Outlaws: A posy of outlaws have circled the wagon!",
                20
            );
            e.addRequiredItem("ammo", 2);
            e.addRequiredItem("food", 5);
            e.addRequiredItem("water", 4);
            e.addRequiredItem("coin", 10);
            e.setPenaltyDescription("Failure to fend them off may mean you pay the ultimate price...");
            possibleEvents.add(e);
        }
        if (!occurredEvents.contains("wheel") && random.nextInt(100) < 10) {
            Event e = new Event(
                "I Broke the Gosh-dang Wheel: The wheel of the wagon broke clean off!",
                10
            );
            e.addRequiredItem("coin", 10);
            e.addRequiredItem("spare part", 1);
            e.setPenaltyDescription("If you don't make the repair, this is going to be a long night...");
            possibleEvents.add(e);
        }
        if (!occurredEvents.contains("snake") && random.nextInt(100) < 10) {
            Event e = new Event(
                "Snake Bite: A rattlesnake was found in the wagon!",
                15
            );
            e.addRequiredItem("medicine", 1);
            e.addRequiredItem("food", 2);
            e.addRequiredItem("ammo", 1);
            e.setPenaltyDescription("Failure to get rid of the snake can get someone hurt...");
            possibleEvents.add(e);
        }
        if (!occurredEvents.contains("stranger") && random.nextInt(100) < 5) {
            Event e = new Event(
                "Hello, Stranger: A mysterious caravan playing soft music on a gramophone sits on the path's edge... Approach?",
                0
            );
            e.setPenaltyDescription("A merchant appears. You may approach to trade, or pass by without incident.");
            possibleEvents.add(e);
        }
        if (possibleEvents.isEmpty()) return null;
        Event chosen = possibleEvents.get(random.nextInt(possibleEvents.size()));
        occurredEvents.add(getEventKey(chosen));
        // Pick a random family member for penalty
        if (family != null && !family.getMembers().isEmpty()) {
            chosen.setAffectedMember(family.getMembers().get(random.nextInt(family.getMembers().size())));
        }
        return chosen;
    }

    private String getEventKey(Event event) {
        String desc = event.getDescription().toLowerCase();
        if (desc.contains("outlaw")) return "outlaws";
        if (desc.contains("wheel")) return "wheel";
        if (desc.contains("snake")) return "snake";
        if (desc.contains("stranger")) return "stranger";
        return desc;
    }
}