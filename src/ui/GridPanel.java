package ui;

import core.GameGrid;
import core.GameGridEvent;
import interfaces.Subscriber;

import javax.swing.*;
import java.awt.*;

public class GridPanel extends JPanel implements Subscriber<GameGridEvent> {

    private GameGrid gameGrid;
    private JButton[][] cellButtons;
    private JLabel populationLabel;

    public GridPanel(GameGrid gameGrid, JLabel populationLabel) {
        this.gameGrid = gameGrid;
        this.gameGrid.addSubscriber(this);
        this.populationLabel = populationLabel;
        this.cellButtons = new JButton[gameGrid.getCells().length][gameGrid.getCells()[0].length];
        this.setLayout(new GridLayout(gameGrid.getCells().length, gameGrid.getCells()[0].length));
        this.generateCellButtons();
    }

    private void generateCellButtons() {
        boolean[][] cells = gameGrid.getCells();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                JButton cellButton = new JButton();
                cellButton.setBackground(cells[i][j] ? Color.BLACK : Color.WHITE);
                int finalI = i;
                int finalJ = j;
                cellButton.addActionListener(e -> {
                    gameGrid.toggleCellState(finalI, finalJ);
                });
                this.cellButtons[i][j] = cellButton;
                this.add(cellButton);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        boolean[][] cells = gameGrid.getCells();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                JButton button = this.cellButtons[i][j];
                if (cells[i][j]) {
                    button.setBackground(Color.BLACK);
                } else {
                    button.setBackground(Color.WHITE);
                }
            }
        }
        super.paintComponent(g);
    }

    @Override
    public void update(GameGridEvent event, int row, int col) {
        if (event == GameGridEvent.CELL_LIVE) {
            populationLabel.setText("Population: " + gameGrid.getLiveCells());
            this.cellButtons[row][col].setBackground(Color.BLACK);
        } else if (event == GameGridEvent.CELL_DIE) {
            populationLabel.setText("Population: " + gameGrid.getLiveCells());
            this.cellButtons[row][col].setBackground(Color.WHITE);
        }
    }
}
