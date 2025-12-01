public class MonsterEnding implements EndingType {
    @Override
    public String getType() {
        return "You Monster";
    }
    @Override
    public String getDescription() {
        return "Alone, you stride into town with an empty heart, a shell of the man you were before...";
    }
    @Override
    public int[] getHeadstoneIndices() {
        return new int[]{1, 3, 4, 5}; // Headstone1, 3, 4, 5
    }
}
