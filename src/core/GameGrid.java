package core;

import interfaces.Publisher;
import interfaces.Subscriber;

import java.util.ArrayList;
import java.util.List;

public class GameGrid implements Publisher<GameGridEvent> {
    private int generations;
    private Cell[][] cells;
    private List<Subscriber<GameGridEvent>> subscribers;

    public GameGrid(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("width and height must be greater than 0");
        }
        this.generations = 0;
        this.subscribers = new ArrayList<>();
        this.cells = new Cell[height][width];
        for (int i = 0; i < this.cells.length; i++) {
            for (int j = 0; j < this.cells[i].length; j++) {
                this.cells[i][j] = new Cell();
            }
        }
    }

    public GameGrid() {
        this(48, 48);
    }

    public void reset() {
        for (int i = 0; i < this.cells.length; i++) {
            for (int j = 0; j < this.cells[i].length; j++) {
                this.cells[i][j].setAlive(false);
            }
        }
        this.generations = 0;
        this.notifySubscribers(GameGridEvent.RESET);
    }

public void generateRandomLiveCells() {
        for (int i = 0; i < this.cells.length; i++) {
            for (int j = 0; j < this.cells[i].length; j++) {
                this.cells[i][j].setAlive(false);
                if (Math.random() < 0.3) {
                    this.cells[i][j].live();
                }
            }
        }
        this.notifySubscribers(GameGridEvent.RANDOMIZATION);
    }

    public Cell[][] getCells() {
        return cells;
    }

    public int getGenerations() {
        return this.generations;
    }

    private Cell[][] getCurrentStateCopy() {
        Cell[][] currentState = new Cell[this.cells.length][this.cells[0].length];
        for (int i = 0; i < this.cells.length; i++) {
            for (int j = 0; j < this.cells[i].length; j++) {
                currentState[i][j] = new Cell();
                currentState[i][j].setAlive(this.cells[i][j].isAlive());
            }
        }
        return currentState;
    }

    public boolean areCellsAlive() {
        for (int i = 0; i < this.cells.length; i++) {
            for (int j = 0; j < this.cells[i].length; j++) {
                if (this.cells[i][j].isAlive()) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getLiveCells() {
        int liveCells = 0;
        for (int i = 0; i < this.cells.length; i++) {
            for (int j = 0; j < this.cells[i].length; j++) {
                if (this.cells[i][j].isAlive()) {
                    liveCells++;
                }
            }
        }
        return liveCells;
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
                if (cells[i][j].isAlive()) {
                    neighbours++;
                }
            }
        }
        return neighbours;
    }

    public void advanceGeneration() {
        Cell[][] nextState = this.getCurrentStateCopy();
        for (int i = 0; i < this.cells.length; i++) {
            for (int j = 0; j < this.cells[i].length; j++) {
                int liveNeighbours = this.getTotalLiveNeighbours(j, i);
                Cell cell = this.cells[i][j];
                if (!cell.isAlive() && liveNeighbours == 3) {
                    // reproduction
                    nextState[i][j].live();
                } else if (cell.isAlive() && liveNeighbours < 2) {
                    // underpopulation
                    nextState[i][j].die();
                } else if (cell.isAlive() && liveNeighbours > 3) {
                    // overpopulation
                    nextState[i][j].die();
                }
            }
        }
        // I had:
        // this.cells = nextState
        // there was a problem with that, the Cells references were new and
        // "lost", so the buttons associated with them (CellButton) lost the connection
        // with the Cell, so now I change each cell state without recreating another one
        for (int i = 0; i < nextState.length; i++) {
            for (int j = 0; j < nextState[i].length; j++) {
                this.cells[i][j].setAlive(nextState[i][j].isAlive());
            }
        }
        this.generations++;
        this.notifySubscribers(GameGridEvent.ADVANCE_GENERATION);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Generation: ").append(this.getGenerations()).append("\n");
        sb.append("Population: ").append(this.getLiveCells()).append("\n");
        for (Cell[] cellsRow : this.cells) {
            for (Cell cell : cellsRow) {
                    sb.append(cell).append(" ");
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
    public void notifySubscribers(GameGridEvent event) {
        for (Subscriber<GameGridEvent> subscriber: this.subscribers) {
            subscriber.update(event);
        }
    }
}
