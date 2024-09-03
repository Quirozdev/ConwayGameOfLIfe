package ui;

import core.GameGrid;
import ui.theme.labels.Label;

import javax.swing.*;
import java.awt.*;

public class Ui {

    private GameGrid gameGrid;
    private JLabel generationsLabel;
    private JLabel populationLabel;

    public Ui(GameGrid gameGrid) {
        this.gameGrid = gameGrid;
        this.generationsLabel = new Label("Generations: " + gameGrid.getGenerations());
        this.populationLabel = new Label("Population: " + gameGrid.getLiveCells());
    }

    public void start() {
        JFrame frame = new JFrame();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setMinimumSize(new Dimension(400, 400));

        GridPanel gridPanel = new GridPanel(this.gameGrid, populationLabel);
        frame.getContentPane().add(BorderLayout.CENTER, gridPanel);
        frame.getContentPane().add(BorderLayout.EAST, new MenuSectionPanel(this.gameGrid, gridPanel, generationsLabel, populationLabel));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Game Of Life");
        frame.setVisible(true);
    }
}
