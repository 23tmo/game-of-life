import processing.core.PApplet;

/**
 * GameOfLifeApp is the main class for this program. It has the structure for everything and just calls the other
 * classes for the details. GameOfLifeApp sets up the cell array, runs draw to display, deals with mouse clicks and key
 * presses, and iterates over every non border cell to call evolve and apply rules (from Cell class). It tells the
 * program where to start and also has a getter so other classes can access the GameOfLife app. The historical extension
 * changes the color of the cell by an increment of 50 each time the cell is alive and evolves. The cells timeAlive gets
 * reset when it dies. I also added an additional life-like rule, seeds, which births a new cell when it is surrounded
 * by 2 neighbors and continuously dies.
 */
public class GameOfLifeApp extends PApplet{
    private static GameOfLifeApp app;
    private Cell[][] cells;
    private boolean evolve;
    private static final int CELL_SIZE = 10;

    /**
     * main is where every java program starts and tells it where to go from there. This says to start at gamOfLifeApp.
     * @param args is arguments in an array of type string that we don't use (command line parameters)
     */
    public static void main(String[] args) {
        app = new GameOfLifeApp(); // assigning new object into instance variable and runs itself
        app.runSketch();
    }

    /**
     * GameOfLife constructor, declares evolve false.
     */
    public GameOfLifeApp() {
        evolve = false;
    }


    /**
     * calls super class settings and establishes size of canvas
     */
    @Override
    // PApplet has all these public methods, we need to define the method. Its overriding the settings
    // method from the PApplet class
    public void settings() {
        super.settings();
        size(1000, 500);
    }

    /**
     * calls super class setup, creates 2D array called cells, fills array with new cell objects for every grid
     * location
     */
    @Override
    public void setup() {
        super.setup();
        frameRate(5);
        Rules rules = new MooreRules(new int[]{3}, new int[]{2, 3}); // Life
        // Rules rules = new MooreRules(new int[]{2}, new int[]{}); // seeds
        cells = new Cell[height/CELL_SIZE][width/CELL_SIZE];
        for(int r = 0; r < cells.length; r++){
            for(int c = 0; c < cells[r].length; c++) { // if on border of canvas, set cellstate to dead
                if (r == cells.length - 1 ||
                        r == 0 ||
                        c == cells[0].length - 1 ||
                        c == 0) {
                    CellState cellState = CellState.DEAD;
                    Cell newCell = new Cell(c * CELL_SIZE, r * CELL_SIZE, CELL_SIZE, r, c, cellState, rules, 0);
                    cells[r][c] = newCell;
                }
                else {
                    CellState cellState = CellState.DEAD;
                    Cell newCell = new Cell(c * CELL_SIZE, r * CELL_SIZE, CELL_SIZE, r, c, cellState, rules, 0);
                    cells[r][c] = newCell;
                }
            }
        }
    }

    /**
     * continuously running, Draw displays the cell objects and when boolean evolve is true, calls evolve and apply rules
     */
    @Override
    public void draw() {
        if (evolve){
            applyRules();
            evolve();
        }
        display(); // displays cell objects
    }

    /**
     * calls super class method, establishes what row and column the cell clicked was, calls handleMouseClicked for
     * the cell in clicked row and column
     */
    @Override
    public void mouseClicked() { // figures out where you clicked and sends to handleClick
        super.mouseClicked(); // calling PApplet version of the same methods
        int col = mouseX/CELL_SIZE; // mouseX 450/10 = 45
        int row = mouseY/CELL_SIZE; // mouseY 115, 115/10 = 11
        cells[row][col].handleClick(); // having Cell objects handle clicks
    }

    /**
     * calls super class method, changes the state of boolean evolve to turn on or off cell evolution
     */
    @Override
    public void keyPressed() {
        super.keyPressed();
        evolve = !evolve; // pausing and restarting Cell evolution
    }

    /**
     * iterates over all objects in 2D array and calls Cell's apply rules for them each
     */
    private void applyRules(){
        for(int r = 1; r < cells.length - 1 ; r++){
            for(int c = 1; c < cells[0].length - 1; c++){
                cells[r][c].applyRules(cells); // goes over each cell and apply rules to each cell in nested for loop
            }
        }
    }

    /**
     * iterates over all objects in 2D array and calls Cell's evolve for them each
     */
    private void evolve(){
        for(int i = 1; i < cells.length -1 ; i++){
            for(int j = 1; j < cells[0].length - 1; j++) {
                cells[i][j].evolve(); // iterates over each cell and tells it to evolve
            }
        }
    }

    /**
     *  iterates over all objects in 2D array and calls Cell's display method for them each
     */
    private void display(){
        for(int i = 1; i < cells.length - 1; i++){
            for(int j = 1; j < cells[0].length -1; j++) {
                cells[i][j].display(); // iterates over 2D array and calls display on each cell object
            }
        }
    }

    /**
     * getter for gameOfLifeApp, the whole class, other methods (cell) call this when they need to draw itself,
     * it needs to call rect and needs an instance of the PApplet, this is the method that helps it instead of
     * calling "this" can call this method - GameOfLifeApp.getApp();
     * @return app, an object of PApplet established to be reference to itself
     */
    public static GameOfLifeApp getApp(){
        return app;
    }
}
