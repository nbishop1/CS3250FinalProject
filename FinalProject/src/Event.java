import java.util.HashMap;

public class Event {
    private String description;
    private int impact;
    private HashMap<String, Integer> requiredItems; // item name -> quantity
    private FamilyMember affectedMember;
    private String penaltyDescription;

    public Event(String description, int impact) {
        this.setDescription(description);
        this.impact = impact;
        this.requiredItems = new HashMap<>();
        this.penaltyDescription = "";
    }

    public void addRequiredItem(String itemName, int quantity) {
        requiredItems.put(itemName, quantity);
    }

    public HashMap<String, Integer> getRequiredItems() {
        return requiredItems;
    }

    public void setAffectedMember(FamilyMember member) {
        this.affectedMember = member;
    }

    public FamilyMember getAffectedMember() {
        return affectedMember;
    }

    public void setPenaltyDescription(String desc) {
        this.penaltyDescription = desc;
    }

    public String getPenaltyDescription() {
        return penaltyDescription;
    }

    public boolean canClearEvent(Supplies supplies) {
        for (String item : requiredItems.keySet()) {
            int qty = requiredItems.get(item);
            // Check supplies for each item
            switch (item.toLowerCase()) {
                case "food": if (supplies.getFood() < qty) return false; break;
                case "water": if (supplies.getWater() < qty) return false; break;
                case "medicine": if (supplies.getMedicine() < qty) return false; break;
                case "ammo": if (supplies.getAmmo() < qty) return false; break;
                case "coin": if (supplies.getCoin() < qty) return false; break;
                case "spare part": if (supplies.getSpareParts() < qty) return false; break;
            }
        }
        return true;
    }

    public void applyPenalty() {
        if (affectedMember != null) {
            affectedMember.setHealth(affectedMember.getHealth() - impact);
            if (affectedMember.getHealth() <= 0) affectedMember.setAlive(false);
        }
        // Additional penalty logic can be added here
    }

    public void applyTo(Player player) {
        // change the state of the player or family
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}