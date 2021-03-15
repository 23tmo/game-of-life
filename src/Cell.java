/**
 * Returns a Cell object of a specified characteristics.
 * A Cell object displays after handling mouse clicked and depending on its state, evolves when a key is pressed,
 * counts the cells around it and their states, and is manipulated by it's neighbors states in applyRules. We need to
 * create a new cell in state ALIVE every time we click on a cell space.
 */
public class Cell { // represents ONE cell
    private final int x; // x, y are the coordinates
    private final int y;
    private final int size;
    private CellState cellState; // if alive or dead
    private final int row;
    private final int column;
    private Rules rules;
    private int timeAlive;

    /**
     * Cell constructor creates Cells that have an x, y position, size,
     * row/column location, cellState, an object rules to access the applyRules method, and a timeAlive
     * @param x exact x position of the Cell on the canvas
     * @param y exact y position of the Cell on the canvas
     * @param size the sidelength of the cell
     * @param row which row the cell's x, y position falls under
     * @param column which column the cell's x, y position falls under
     * @param cellState the state of the cell
     * @param rules object of type Rules that gives the cell access to the applyRules method
     * @param timeAlive the amount of time a cell is alive, incremented every evolution
     */
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

    /**
     * Display is continuously run to initialize an object of GameOfLife, set the fill and stroke
     * of each cell depending on its state, and makes each cell a rectangle with 2 sidelengths (size) and
     * the x, y position.
     */
    public void display() {
        GameOfLifeApp app = GameOfLifeApp.getApp(); // making a PApplet object
        if (this.cellState == CellState.ALIVE) {
            app.fill(0+timeAlive, 0, 255); // changes fill color for alive cell
            app.stroke(0); // changes border color of alive cell
        } else if (this.cellState == CellState.DEAD) {
            app.fill(0); // changes fill color of dead cell
            app.stroke(0, 102, 204); // changes border color of dead cell
        }
        app.rect(x, y, size, size);
    }

    /**
     * handleClick toggles each cell from different states. If
     * the cell's cellstate is alive, change to dead, otherwise change the cellState
     * to alive when clicked. The handleClick method is called in mouseClicked in GameOfLifeApp
     */
    public void handleClick() {
        if (cellState == CellState.ALIVE) {
            cellState = CellState.DEAD;
        } else {
            cellState = CellState.ALIVE;
        }
    }

    /**
     * Takes output countLiveNeighbors and calls the Rules' applyRules method on the 2D array of cells
     * @param cells 2D array of cell objects created in GameOfLifeApp
     */
    public void applyRules(Cell[][] cells) {
        int neighbors = countLiveNeighbors(cells);
        cellState = rules.applyRules(cellState, neighbors); // cellState from rules' applyRules method
    }

    /**
     * Lets the cells with cellStates "will" change state.
     */
    public void evolve() {
        timeAlive += 50;
        if (cellState == CellState.WILL_REVIVE) {
            cellState = CellState.ALIVE;
        }
        else if (cellState == CellState.WILL_DIE) {
            cellState = CellState.DEAD;
            timeAlive = 0;
        }
    }

    /**
     * Counts how many surrounding neighbors of the cell are alive or will die.
     * @param cells an array of cells given from the array of cells in GameOfLifeApp
     * @return number of alive neighbors or neighbors that will die (?)
     */
    private int countLiveNeighbors(Cell[][] cells) {
        int counter = 0;
        for (int i = row - 1; i < row + 2; i++) { // location of the first neighbor surrounding the cell (review this)
            for (int z = column - 1; z < column + 2; z++) {
                if (cells[i][z].cellState == CellState.WILL_DIE || cells[i][z].cellState == CellState.ALIVE) {
                    counter++;
                }
            }
        }
        if (this.cellState == cellState.ALIVE) { // doesn't count the cellState of the current cell, just the neighbors around it
            counter--;
        }
        return counter;
    }
}

