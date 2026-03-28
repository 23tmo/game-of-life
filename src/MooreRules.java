/**
 * Rule set for a Moore neighborhood with configurable birth and survival counts.
 */
public class MooreRules extends Rules {
    private static final int NUM_NEIGHBORS = 9;
    private final boolean[] birthRules;
    private final boolean[] survivalRules;

    /**
     * Creates a rule set such as Conway Life's {@code B3/S23}.
     */
    public MooreRules(int[] birthNeighbors, int[] survivalNeighbors) {
        birthRules = new boolean[NUM_NEIGHBORS];
        survivalRules = new boolean[NUM_NEIGHBORS];

        for (int neighbors : birthNeighbors) {
            birthRules[neighbors] = true;
        }

        for (int neighbors : survivalNeighbors) {
            survivalRules[neighbors] = true;
        }
    }

    @Override
    public boolean shouldBeBorn(int liveNeighbors) {
        return birthRules[liveNeighbors];
    }

    @Override
    public boolean shouldSurvive(int liveNeighbors) {
        return survivalRules[liveNeighbors];
    }
}
