/**
 * The MooreRules class determines what the cell will look like in the next generation,
 * defines the Moore neighborhood, and implements the methods shouldBeBorn and shouldSurvive.
 */
public class MooreRules extends Rules {
    private static final int NUM_NEIGHBORS = 9;
    private boolean[] birthRules;
    private boolean[] survivalRules;

    /**
     * MooreRules constructor sets how many birthNeighbors and survivalNeighbors are
     * needed for a cell to stay alive or to be born
     * @param birthNeighbors number of live neighbors surrounding cell for a new cell to be born (3 in our case)
     * @param survivalNeighbors number of neighbors surrounding the cell that need to be alive
     * for the cell to stay alive (2 or 3)
     */

    public MooreRules(int[] birthNeighbors, int[] survivalNeighbors){ // need 3 alive to be born, 2/3 alive to survive
        super(); // calls the Rules superclass

        // Making an array of booleans with the length of num of neighbors (9)
        // this is the Moore neighborhood of the cell
        // that keeps track of the number of cells that can birth a new cell.
        // Sets all elements to false: {false, false, false, false, false, false, false, false, false}
        birthRules = new boolean[NUM_NEIGHBORS];

        // Makes a different Moore neighborhood to keep track of the number
        // of cells needed to stay alive
        // Sets all elements to false: {false, false, false, false, false, false, false, false, false}
        survivalRules = new boolean[NUM_NEIGHBORS];

        // This enhanced for loop goes through each of the elements that have been passed in birthNeighbors.
        // GameOfLife sets birthNeighbors to "3" but only has 1 element, so the enhanced for loop only happens once.
        // Sets the element at neighbors (3) of the birthRules array to true.
        // {false, false, false, true, false, false, false, false, false}
        for (int neighbors: birthNeighbors){ //
            birthRules[neighbors] = true;
        }

        // This enhanced for loop runs twice since there are 2 elements in the survivalRules array, and
        // sets the element at neighbors (2 and 3) of the survivalRules array to true and true.
        // {false, false, true, true, false, false, false, false, false}
        for (int neighbors: survivalNeighbors){
            survivalRules[neighbors] = true;
        }
    }

    /**
     * Method shouldBeBorn is a boolean that decides if a cell should be born or not.
     * @param liveNeighbors the number of neighbors alive around the cells, determined from the
     * countLiveNeighbors method
     * @return boolean result is true if the element at liveNeighbors in the birthRules boolean array is
     * true. The number of alive neighbors can only be 3 for a new cell to be born.
     */
    @Override
    public boolean shouldBeBorn(int liveNeighbors) {
        return birthRules[liveNeighbors];
    }

    /**
     * Method shouldSurvive is a boolean that decides if a cell should stay alive or not.
     * @param liveNeighbors the number of neighbors alive around the cells,
     * determined from the countLiveNeighbors method
     * @return boolean result is true if the element at liveNeighbors in the survivalRules boolean array is
     * true. The number of alive neighbors can only be 2 or 3 for a cell to stay alive.
     */
    @Override
    public boolean shouldSurvive(int liveNeighbors) {
        return survivalRules[liveNeighbors];
    }
}
