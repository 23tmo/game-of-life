/**
 * Represents a single cell in the grid.
 */
public class Cell {
    private final int x;
    private final int y;
    private final int size;
    private CellState cellState;
    private final int row;
    private final int column;
    private final Rules rules;
    private int timeAlive;

    public Cell(int x, int y, int size, int row, int column, CellState cellState, Rules rules, int timeAlive) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.row = row;
        this.column = column;
        this.cellState = cellState;
        this.rules = rules;
        this.timeAlive = timeAlive;
    }

    public void handleClick() {
        if (cellState == CellState.ALIVE) {
            cellState = CellState.DEAD;
        } else {
            cellState = CellState.ALIVE;
        }
    }

    public void applyRules(Cell[][] cells) {
        int neighbors = countLiveNeighbors(cells);
        cellState = rules.applyRules(cellState, neighbors);
    }

    public void evolve() {
        timeAlive += 50;
        if (cellState == CellState.WILL_REVIVE) {
            cellState = CellState.ALIVE;
        } else if (cellState == CellState.WILL_DIE) {
            cellState = CellState.DEAD;
            timeAlive = 0;
        }
    }

    public CellState getCellState() {
        return cellState;
    }

    public int getTimeAlive() {
        return timeAlive;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    private int countLiveNeighbors(Cell[][] cells) {
        int counter = 0;
        for (int i = row - 1; i < row + 2; i++) {
            for (int j = column - 1; j < column + 2; j++) {
                if (cells[i][j].cellState == CellState.WILL_DIE || cells[i][j].cellState == CellState.ALIVE) {
                    counter++;
                }
            }
        }
        if (this.cellState == CellState.ALIVE) {
            counter--;
        }
        return counter;
    }
}
