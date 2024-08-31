import core.Grid;
import ui.Ui;

public class Main {
    public static void main(String[] args) {
        Grid grid = new Grid();

        Ui ui = new Ui(grid);
        ui.start();
    }
}