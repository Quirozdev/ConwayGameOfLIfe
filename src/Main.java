import core.GameGrid;
import ui.Ui;

public class Main {
    public static void main(String[] args) {
        GameGrid gameGrid = new GameGrid(48, 48);

        Ui ui = new Ui(gameGrid);
        ui.start();
    }
}