package ui;

import core.Cell;
import core.CellEvent;
import core.GameGrid;
import interfaces.Subscriber;
import ui.theme.buttons.CellButton;

import javax.swing.*;
import java.awt.*;

public class GridPanel extends JPanel implements Subscriber<CellEvent> {

    private GameGrid gameGrid;
    private CellButton[][] cellButtons;
    private JLabel populationLabel;

    public GridPanel(GameGrid gameGrid, JLabel populationLabel) {
        this.gameGrid = gameGrid;
        this.populationLabel = populationLabel;
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
                cells[i][j].addSubscriber(button);
                cells[i][j].addSubscriber(this);
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

    @Override
    public void update(CellEvent event) {
        if (event == CellEvent.LIVE) {
            populationLabel.setText("Population: " + gameGrid.getLiveCells());
        } else if (event == CellEvent.DIE) {
            populationLabel.setText("Population: " + gameGrid.getLiveCells());
        }
    }
}
