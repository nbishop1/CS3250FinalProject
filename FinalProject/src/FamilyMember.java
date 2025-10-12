public class FamilyMember {
    private String name;
    private int health;
    private int daysSinceLastFood;
    private int daysSinceLastWater;
    private boolean isSickOrInjured;
    private int daysSinceSickOrInjured;
    private int foodInterval;
    private int waterInterval;
    private int medicineInterval;
    private boolean alive;
    private int consecutiveHungryDays = 0;
    private int consecutiveThirstyDays = 0;
    private int consecutiveSickDays = 0;

    public FamilyMember(String name, int foodInterval, int waterInterval, int medicineInterval) {
        this.name = name;
        this.health = 100;
        this.daysSinceLastFood = 0;
        this.daysSinceLastWater = 0;
        this.isSickOrInjured = false;
        this.daysSinceSickOrInjured = 0;
        this.foodInterval = foodInterval;
        this.waterInterval = waterInterval;
        this.medicineInterval = medicineInterval;
        this.alive = true;
    }

    public String getName() { return name; }
    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }
    public boolean isAlive() { return alive; }
    public void setAlive(boolean alive) { this.alive = alive; }
    public boolean isSickOrInjured() { return isSickOrInjured; }
    public void setSickOrInjured(boolean sickOrInjured) { this.isSickOrInjured = sickOrInjured; }
    public int getDaysSinceSickOrInjured() { return daysSinceSickOrInjured; }
    public void setDaysSinceSickOrInjured(int days) { this.daysSinceSickOrInjured = days; }
    public int getFoodInterval() { return foodInterval; }
    public int getWaterInterval() { return waterInterval; }
    public int getMedicineInterval() { return medicineInterval; }
    public int getDaysSinceLastFood() { return daysSinceLastFood; }
    public void setDaysSinceLastFood(int days) { this.daysSinceLastFood = days; }
    public int getDaysSinceLastWater() { return daysSinceLastWater; }
    public void setDaysSinceLastWater(int days) { this.daysSinceLastWater = days; }

    public void nextDay() {
        daysSinceLastFood++;
        daysSinceLastWater++;
        if (isSickOrInjured) daysSinceSickOrInjured++;

        // Track consecutive hungry days
        if (needsFood()) {
            consecutiveHungryDays++;
        } else {
            consecutiveHungryDays = 0;
        }
        // Track consecutive thirsty days
        if (needsWater()) {
            consecutiveThirstyDays++;
        } else {
            consecutiveThirstyDays = 0;
        }
        // Track consecutive sick days
        if (isSickOrInjured() && needsMedicine()) {
            consecutiveSickDays++;
        } else {
            consecutiveSickDays = 0;
        }
        // Death check
        if (consecutiveHungryDays >= 3 || consecutiveThirstyDays >= 3 || consecutiveSickDays >= 3) {
            alive = false;
        }
    }

    public boolean needsFood() { return daysSinceLastFood >= foodInterval; }
    public boolean needsWater() { return daysSinceLastWater >= waterInterval; }
    public boolean needsMedicine() { return isSickOrInjured && daysSinceSickOrInjured >= medicineInterval; }

    public void feed() {
        daysSinceLastFood = 0;
        consecutiveHungryDays = 0;
    }
    public void giveWater() {
        daysSinceLastWater = 0;
        consecutiveThirstyDays = 0;
    }
    public void heal() {
        isSickOrInjured = false;
        daysSinceSickOrInjured = 0;
        consecutiveSickDays = 0;
    }

    public String getStatusText() {
        if (!alive) {
            return "has perished.";
        }
        boolean famished = needsFood();
        boolean parched = needsWater();
        boolean sick = isSickOrInjured();
        if (famished && parched) {
            return "is famished and parched.";
        } else if (famished) {
            return "is famished.";
        } else if (parched) {
            return "is parched.";
        } else if (sick) {
            return "is sick.";
        } else {
            return "feels fine.";
        }
    }
}