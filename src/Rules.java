/**
 * The Rules class defines the method applyRules but doesn't implement it. The applyRules
 * method is overridden in Cell and GameOfLifeApp and is, essentially, a container for the evolution logic
 * of the game.
 */
public abstract class Rules {
    // The subclass MooreRules implements shouldBeBorn and shouldSurvive
    public abstract boolean shouldBeBorn(int liveNeighbors);
    public abstract boolean shouldSurvive(int liveNeighbors);

    /**
     * The applyRules method is of type CellState and changes the state of the cell depending on if the cell's
     * live neighbors are enough to stay alive or to birth another cell.
     * @param cellState the state of the cell of type CellState
     * @param liveNeighbors the amount of alive neighbors around the cell, determined from the countLiveNeighbors method
     * @return returns the new state of the cell after applying the shouldBeBorn or shouldSurvive rules
     */
    public CellState applyRules(CellState cellState, int liveNeighbors){
        if (cellState == CellState.DEAD && shouldBeBorn(liveNeighbors) == true){
            return CellState.WILL_REVIVE;
        } else if (cellState == CellState.ALIVE && shouldSurvive(liveNeighbors) == false){
            return CellState.WILL_DIE;
        } else {
            return cellState;
        }
    }
}
