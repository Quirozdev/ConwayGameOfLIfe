package core;

public class Grid {
    private int generations;
    private Cell[][] cells;

    public Grid() {
        this.generations = 0;
        this.cells = new Cell[48][48];
        for (int i = 0; i < this.cells.length; i++) {
            for (int j = 0; j < this.cells[i].length; j++) {
                this.cells[i][j] = new Cell();
            }
        }
        this.generateRandomLiveCells();
    }

    private void generateRandomLiveCells() {
        for (int i = 0; i < this.cells.length; i++) {
            for (int j = 0; j < this.cells[i].length; j++) {
                if (Math.random() < 0.3) {
                    this.cells[i][j].live();
                }
            }
        }
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
        this.cells = nextState;
        this.generations++;
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
}
