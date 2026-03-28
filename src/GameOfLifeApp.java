import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameOfLifeApp {
    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 500;
    private static final int CELL_SIZE = 10;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameOfLifeApp::showWindow);
    }

    private static void showWindow() {
        Rules rules = new MooreRules(new int[]{3}, new int[]{2, 3});
        GameOfLifePanel panel = new GameOfLifePanel(WINDOW_WIDTH, WINDOW_HEIGHT, CELL_SIZE, rules);

        JFrame frame = new JFrame("Game of Life");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setContentPane(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        panel.start();
        SwingUtilities.invokeLater(panel::requestFocusInWindow);
    }

    static class GameOfLifePanel extends JPanel {
        private static final int FRAME_DELAY_MS = 200;
        private static final Color DEAD_FILL = Color.BLACK;
        private static final Color DEAD_STROKE = new Color(0, 102, 204);
        private static final Color ALIVE_STROKE = Color.BLACK;

        private final Cell[][] cells;
        private final Timer timer;
        private boolean evolve;

        GameOfLifePanel(int width, int height, int cellSize, Rules rules) {
            cells = createCells(width, height, cellSize, rules);
            setPreferredSize(new Dimension(width, height));
            setBackground(Color.BLACK);
            setFocusable(true);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent event) {
                    toggleCellAt(event.getY() / cellSize, event.getX() / cellSize);
                    requestFocusInWindow();
                    repaint();
                }
            });

            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent event) {
                    evolve = !evolve;
                    repaint();
                }
            });

            timer = new Timer(FRAME_DELAY_MS, event -> {
                if (evolve) {
                    step();
                }
                repaint();
            });
        }

        public void start() {
            timer.start();
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            Graphics2D g2 = (Graphics2D) graphics.create();
            try {
                for (int row = 1; row < cells.length - 1; row++) {
                    for (int column = 1; column < cells[row].length - 1; column++) {
                        drawCell(g2, cells[row][column]);
                    }
                }
            } finally {
                g2.dispose();
            }
        }

        private void toggleCellAt(int row, int column) {
            if (!isInteriorCell(row, column)) {
                return;
            }
            cells[row][column].handleClick();
        }

        private void step() {
            applyRules();
            evolve();
        }

        private void applyRules() {
            for (int row = 1; row < cells.length - 1; row++) {
                for (int column = 1; column < cells[row].length - 1; column++) {
                    cells[row][column].applyRules(cells);
                }
            }
        }

        private void evolve() {
            for (int row = 1; row < cells.length - 1; row++) {
                for (int column = 1; column < cells[row].length - 1; column++) {
                    cells[row][column].evolve();
                }
            }
        }

        private void drawCell(Graphics2D graphics, Cell cell) {
            if (cell.getCellState() == CellState.ALIVE) {
                graphics.setColor(new Color(clampColor(cell.getTimeAlive()), 0, 255));
                graphics.fillRect(cell.getX(), cell.getY(), cell.getSize(), cell.getSize());
                graphics.setColor(ALIVE_STROKE);
                graphics.drawRect(cell.getX(), cell.getY(), cell.getSize(), cell.getSize());
                return;
            }

            graphics.setColor(DEAD_FILL);
            graphics.fillRect(cell.getX(), cell.getY(), cell.getSize(), cell.getSize());
            graphics.setColor(DEAD_STROKE);
            graphics.drawRect(cell.getX(), cell.getY(), cell.getSize(), cell.getSize());
        }

        private boolean isInteriorCell(int row, int column) {
            return row > 0 && row < cells.length - 1 && column > 0 && column < cells[row].length - 1;
        }

        private static Cell[][] createCells(int width, int height, int cellSize, Rules rules) {
            Cell[][] grid = new Cell[height / cellSize][width / cellSize];
            for (int row = 0; row < grid.length; row++) {
                for (int column = 0; column < grid[row].length; column++) {
                    grid[row][column] = new Cell(
                            column * cellSize,
                            row * cellSize,
                            cellSize,
                            row,
                            column,
                            CellState.DEAD,
                            rules,
                            0
                    );
                }
            }
            return grid;
        }

        private static int clampColor(int value) {
            return Math.max(0, Math.min(255, value));
        }
    }
}
