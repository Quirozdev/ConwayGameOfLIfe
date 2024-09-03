package core;

import interfaces.Publisher;
import interfaces.Subscriber;

import java.util.ArrayList;
import java.util.List;

public class GameGrid implements Publisher<GameGridEvent> {
    private int generations;
    private boolean[][] cells;
    private int liveCells;
    private List<Subscriber<GameGridEvent>> subscribers;

    public GameGrid(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("width and height must be greater than 0");
        }
        this.generations = 0;
        this.liveCells = 0;
        this.subscribers = new ArrayList<>();
        this.cells = new boolean[height][width];
        for (int i = 0; i < this.cells.length; i++) {
            for (int j = 0; j < this.cells[i].length; j++) {
                this.cells[i][j] = false;
            }
        }
    }

    public GameGrid() {
        this(48, 48);
    }

    public void toggleCellState(int row, int col) {
        if (!this.cells[row][col]) {
            this.reviveCell(row, col);
        } else {
            this.killCell(row, col);
        }
    }

    public void reviveCell(int row, int col) {
        if (!this.cells[row][col]) {
            this.liveCells++;
        }
        this.cells[row][col] = true;
        this.notifySubscribers(GameGridEvent.CELL_LIVE, row, col);
    }

    public void killCell(int row, int col) {
        if (this.cells[row][col]) {
            this.liveCells--;
        }
        this.cells[row][col] = false;
        this.notifySubscribers(GameGridEvent.CELL_DIE, row, col);
    }

    public void setWidth(int width) {
        if (width <= 0) {
            throw new IllegalArgumentException("width must be greater than 0");
        }
        this.cells = new boolean[this.cells[0].length][width];
        this.notifySubscribers(GameGridEvent.WIDTH_CHANGED, 0, 0);
    }

    public void setHeight(int height) {
        if (height <= 0) {
            throw new IllegalArgumentException("height must be greater than 0");
        }
        this.cells = new boolean[height][this.cells.length];
        this.notifySubscribers(GameGridEvent.HEIGHT_CHANGED, 0, 0);
    }

    public void reset() {
        for (int i = 0; i < this.cells.length; i++) {
            for (int j = 0; j < this.cells[i].length; j++) {
                this.killCell(i, j);
            }
        }
        this.generations = 0;
        this.liveCells = 0;
        this.notifySubscribers(GameGridEvent.RESET, 0, 0);
    }

public void generateRandomLiveCells() {
        this.liveCells = 0;
        for (int i = 0; i < this.cells.length; i++) {
            for (int j = 0; j < this.cells[i].length; j++) {
                this.cells[i][j] = false;
                if (Math.random() < 0.3) {
                    this.reviveCell(i, j);
                    this.liveCells++;
                }
            }
        }
        this.notifySubscribers(GameGridEvent.RANDOMIZATION, 0, 0);
    }

    public boolean[][] getCells() {
        return cells;
    }

    public int getGenerations() {
        return this.generations;
    }

    private boolean[][] getCurrentStateCopy() {
        boolean[][] copy = new boolean[this.cells.length][this.cells[0].length];
        for (int i = 0; i < this.cells.length; i++) {
            for (int j = 0; j < this.cells[i].length; j++) {
                copy[i][j] = this.cells[i][j];
            }
        }
        return copy;
    }

    public boolean areCellsAlive() {
        return this.liveCells > 0;
    }

    public int getLiveCells() {
        return this.liveCells;
    }

    private int getTotalLiveNeighbours(int x, int y) {
        int neighbours = 0;
        int startingX = x == 0 ? 0 : x - 1;
        int endingX = x == cells[0].length - 1 ? cells[0].length - 1 : x + 1;
        int startingY = y == 0 ? 0 : y - 1;
        int endingY = y == cells.length - 1 ? cells.length - 1 : y + 1;
        for (int i = startingY; i <= endingY; i++) {
            for (int j = startingX; j <= endingX; j++) {
                if (j == x && i == y) {
                    continue;
                }
                if (cells[i][j]) {
                    neighbours++;
                }
            }
        }
        return neighbours;
    }

    public void advanceGeneration() {
        boolean[][] nextState = this.getCurrentStateCopy();
        for (int i = 0; i < this.cells.length; i++) {
            for (int j = 0; j < this.cells[i].length; j++) {
                int liveNeighbours = this.getTotalLiveNeighbours(j, i);
                boolean cell = this.cells[i][j];
                if (!cell && liveNeighbours == 3) {
                    // reproduction
                    nextState[i][j] = true;
                    this.liveCells++;
                } else if (cell && liveNeighbours < 2) {
                    // underpopulation
                    nextState[i][j] = false;
                    this.liveCells--;
                } else if (cell && liveNeighbours > 3) {
                    // overpopulation
                    nextState[i][j] = false;
                    this.liveCells--;
                }
            }
        }
        this.cells = nextState;
        this.generations++;
        this.notifySubscribers(GameGridEvent.ADVANCE_GENERATION, 0, 0);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Generation: ").append(this.getGenerations()).append("\n");
        sb.append("Population: ").append(this.getLiveCells()).append("\n");
        for (boolean[] cellsRow : this.cells) {
            for (boolean cell : cellsRow) {
                    sb.append(cell ? "O" : ".").append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public void addSubscriber(Subscriber<GameGridEvent> subscriber) {
        this.subscribers.add(subscriber);
    }

    @Override
    public void removeSubscriber(Subscriber<GameGridEvent> subscriber) {
        this.subscribers.remove(subscriber);
    }

    @Override
    public void notifySubscribers(GameGridEvent event, int row, int col) {
        for (Subscriber<GameGridEvent> subscriber: this.subscribers) {
            subscriber.update(event, row, col);
        }
    }
}
