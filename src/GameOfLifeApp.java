import processing.core.PApplet;

public class GameOfLifeApp extends PApplet {
    private static GameOfLifeApp app;
    private Cell[][] cells; // papplet has a cells instance variable

    public static void main(String[] args) {
        PApplet.main("GameOfLifeApp");
    }

    public GameOfLifeApp() {
        app = this;
    }

    @Override
    public void settings() {
        super.settings();
        size(1000, 500);
    }

    @Override
    public void setup() {
        super.setup();
        // instantiating Cell objects
    }
    @Override
    public void draw() {
        super.draw();
        // displaying Cell objects
    }

    @Override
    public void mouseClicked() {
        super.mouseClicked();
        // having Cell objects handle clicks
    }

    @Override
    public void keyPressed() {
        super.keyPressed();
        // pausing and restarting Cell evolution
    }

    public static GameOfLifeApp getApp(){
        return app;
    }
}
