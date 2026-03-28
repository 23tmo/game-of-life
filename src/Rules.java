/**
 * Defines the birth and survival rules for the automaton.
 */
public abstract class Rules {
    public abstract boolean shouldBeBorn(int liveNeighbors);
    public abstract boolean shouldSurvive(int liveNeighbors);

    /**
     * Returns the transitional state for the next generation.
     */
    public CellState applyRules(CellState cellState, int liveNeighbors){
        if (cellState == CellState.DEAD && shouldBeBorn(liveNeighbors)) {
            return CellState.WILL_REVIVE;
        } else if (cellState == CellState.ALIVE && !shouldSurvive(liveNeighbors)) {
            return CellState.WILL_DIE;
        } else {
            return cellState;
        }
    }
}
