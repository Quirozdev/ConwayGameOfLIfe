package ui;

import core.GameGrid;
import ui.theme.buttons.Button;
import ui.theme.labels.Label;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuSectionPanel extends JPanel {

    private GameGrid gameGrid;
    private GridPanel gridPanel;
    private JLabel generationsLabel;
    private JLabel populationLabel;

    public MenuSectionPanel(GameGrid gameGrid, GridPanel gridPanel) {
        this.gameGrid = gameGrid;
        this.gridPanel = gridPanel;
        this.setUp();
    }

    private void setUp() {
        generationsLabel = new Label("Generations: " + gameGrid.getGenerations());
        populationLabel = new Label("Population: " + gameGrid.getLiveCells());

        GridLayout layout = new GridLayout(4, 1);
        layout.setHgap(20);
        this.setLayout(layout);

        this.setBorder(new EmptyBorder(20, 20, 20, 20));

        this.add(generationsLabel);
        this.add(populationLabel);
        this.add(new ButtonsPanel());
    }

    class ButtonsPanel extends JPanel {

        public ButtonsPanel() {
            this.setUp();
        }

        private void setUp() {
            JButton startButton = new Button("Start", new Color(87, 204, 153));
            JButton stopButton = new Button("Stop", new Color(230, 57, 70));
            JButton randomizeButton = new Button("Randomize", new Color(255, 130, 37));
            JButton resetButton = new Button("Clear", new Color(22, 50, 91));


            ActionListener startListener = e -> {
                gameGrid.advanceGeneration();
                generationsLabel.setText("Generations: " + gameGrid.getGenerations());
                populationLabel.setText("Population: " + gameGrid.getLiveCells());
                gridPanel.repaint();
            };

            Timer timer = new Timer(100, startListener);
            timer.setRepeats(true);

            startButton.addActionListener(e -> {
                timer.start();
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
            });


            stopButton.addActionListener(e -> {
                timer.stop();
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
            });

            randomizeButton.addActionListener(e -> {
                gameGrid.generateRandomLiveCells();
                gridPanel.repaint();
            });

            resetButton.addActionListener(e -> {
               gameGrid.clearCells();
               gridPanel.repaint();
            });

            this.add(startButton);
            this.add(stopButton);
            this.add(randomizeButton);
            this.add(resetButton);
        }
    }
}
