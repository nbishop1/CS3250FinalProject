public class BadEnding implements EndingType {
    @Override
    public String getType() {
        return "Bad";
    }
    @Override
    public String getDescription() {
        return "You have yee'd your last haw...";
    }
    @Override
    public int[] getHeadstoneIndices() {
        return new int[]{2}; // Only Headstone2.png
    }
}
