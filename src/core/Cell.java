package core;

public class Cell {
    private boolean isAlive;

    public Cell() {
        this.isAlive = false;
    }

    public boolean isAlive() {
        return this.isAlive;
    }

    public void setAlive(boolean alive) {
        this.isAlive = alive;
    }

    public void live() {
        this.isAlive = true;
    }

    public void die() {
        this.isAlive = false;
    }

    @Override
    public String toString() {
        return isAlive ? "O" : ".";
    }
}
