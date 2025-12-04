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
            Event e = new OutlawEvent();
            possibleEvents.add(e);
        }
        if (!occurredEvents.contains("wheel") && random.nextInt(100) < 10) {
            Event e = new WheelEvent();
            possibleEvents.add(e);
        }
        if (!occurredEvents.contains("snake") && random.nextInt(100) < 10) {
            Event e = new SnakeEvent();
            possibleEvents.add(e);
        }
        if (!occurredEvents.contains("stranger") && random.nextInt(100) < 5) {
            Event e = new StrangerEvent();
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