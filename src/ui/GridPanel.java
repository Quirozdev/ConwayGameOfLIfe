package ui;

import core.Cell;
import core.GameGrid;
import ui.theme.buttons.CellButton;

import javax.swing.*;
import java.awt.*;

public class GridPanel extends JPanel {

    private GameGrid gameGrid;
    private CellButton[][] cellButtons;

    public GridPanel(GameGrid gameGrid) {
        this.gameGrid = gameGrid;
        this.cellButtons = new CellButton[gameGrid.getCells().length][gameGrid.getCells()[0].length];
        this.setLayout(new GridLayout(gameGrid.getCells().length, gameGrid.getCells()[0].length));
        this.generateCellButtons();
    }

    private void generateCellButtons() {
        Cell[][] cells = gameGrid.getCells();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                CellButton button = new CellButton(cells[i][j]);
                this.add(button);
                this.cellButtons[i][j] = button;
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Cell[][] cells = gameGrid.getCells();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                CellButton button = this.cellButtons[i][j];
                if (cells[i][j].isAlive()) {
                    button.setBackground(Color.BLACK);
                } else {
                    button.setBackground(Color.WHITE);
                }
            }
        }
        super.paintComponent(g);
    }
}
