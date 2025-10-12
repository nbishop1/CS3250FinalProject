import java.util.ArrayList;

public class Family {
    private ArrayList<FamilyMember> members;
    
    public static Family createDefaultFamily(String playerName) {
        Family family = new Family();
        family.addMember(new Player(playerName)); // Player: 1 food/4 days, 1 water/3 days, 1 medicine/7 days
        family.addMember(new FamilyMember("Mary", 3, 2, 5)); // Wife
        family.addMember(new FamilyMember("Uncle", 3, 2, 5)); // Uncle
        family.addMember(new FamilyMember("Luke", 2, 1, 3)); // Son
        family.addMember(new FamilyMember("Jessie", 2, 1, 3)); // Daughter
        return family;
    }

    public Family() {
        members = new ArrayList<>();
    }
    
    public void addMember(FamilyMember member) { 
        members.add(member);
    }

    public ArrayList<FamilyMember> getMembers() {
        return members;
    }
    
    public FamilyMember getMemberByName(String name) {
        for (FamilyMember m : members) {
            if (m.getName().equalsIgnoreCase(name)) return m;
        }
        return null;
    }

    public ArrayList<FamilyMember> getAliveMembers() {
        ArrayList<FamilyMember> alive = new ArrayList<>();
        for (FamilyMember m : members) {
            if (m.isAlive()) alive.add(m);
        }
        return alive;
    }

    public ArrayList<FamilyMember> getDeceasedMembers() {
        ArrayList<FamilyMember> deceased = new ArrayList<>();
        for (FamilyMember m : members) {
            if (!m.isAlive()) deceased.add(m);
        }
        return deceased;
    }

    public void nextDay() {
        for (FamilyMember m : members) {
            if (m.isAlive()) m.nextDay();
        }
    }
}
