public class GoodEnding implements EndingType {
    @Override
    public String getType() {
        return "Good";
    }
    @Override
    public String getDescription() {
        return "Yee-haw Partner, you made it without anyone dyin'! Surely, that is a miracle.";
    }
    @Override
    public int[] getHeadstoneIndices() {
        return new int[0]; // No headstones
    }
}
