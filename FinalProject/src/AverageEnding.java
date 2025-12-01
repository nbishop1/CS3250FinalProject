public class AverageEnding implements EndingType {
    private int numDead;
    public AverageEnding(int numDead) {
        this.numDead = numDead;
    }
    @Override
    public String getType() {
        return "Average";
    }
    @Override
    public String getDescription() {
        return "Welp, you made it. Although some did pay the price along the way...";
    }
    @Override
    public int[] getHeadstoneIndices() {
        int[] indices = new int[numDead];
        for (int i = 0; i < numDead; i++) indices[i] = i + 1;
        return indices;
    }
}
