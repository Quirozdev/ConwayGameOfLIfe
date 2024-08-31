package ui;

import core.Cell;
import core.Grid;

import javax.swing.*;
import java.awt.*;

public class GridPanel extends JPanel {

    private Grid grid;
    private JButton[][] cellButtons;

    public GridPanel(Grid grid) {
        this.grid = grid;
        this.cellButtons = new JButton[grid.getCells().length][grid.getCells()[0].length];
        this.setLayout(new GridLayout(grid.getCells().length, grid.getCells()[0].length));
        this.generateCellButtons();
    }

    private void generateCellButtons() {
        Cell[][] cells = grid.getCells();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                JButton button = new JButton();
                if (cells[i][j].isAlive()) {
                    button.setBackground(Color.BLACK);
                } else {
                    button.setBackground(Color.WHITE);
                }
                this.add(button);
                this.cellButtons[i][j] = button;
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Cell[][] cells = grid.getCells();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                JButton button = this.cellButtons[i][j];
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
