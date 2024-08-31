package ui;

import core.Grid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuSectionPanel extends JPanel {

    private Grid grid;
    private GridPanel gridPanel;

    public MenuSectionPanel(Grid grid, GridPanel gridPanel) {
        this.grid = grid;
        this.gridPanel = gridPanel;

        JLabel generationsLabel = new JLabel("Generations: " + grid.getGenerations());
        JLabel populationLabel = new JLabel("Population: " + grid.getLiveCells());

        JButton startButton = new JButton("Start");

        ActionListener startListener = e -> {
            grid.advanceGeneration();
            generationsLabel.setText("Generations: " + grid.getGenerations());
            populationLabel.setText("Population: " + grid.getLiveCells());
            gridPanel.repaint();
        };

        Timer timer = new Timer(100, startListener);
        timer.setRepeats(true);

        startButton.addActionListener(e -> {
                timer.start();
        });

        JButton stopButton = new JButton("Stop");

        stopButton.addActionListener(e -> {
            timer.stop();
        });

        this.add(generationsLabel);
        this.add(populationLabel);
        this.add(startButton);
        this.add(stopButton);
    }
}
