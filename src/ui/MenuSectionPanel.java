package ui;

import core.Grid;
import ui.theme.buttons.Button;
import ui.theme.labels.Label;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuSectionPanel extends JPanel {

    private Grid grid;
    private GridPanel gridPanel;
    private JLabel generationsLabel;
    private JLabel populationLabel;

    public MenuSectionPanel(Grid grid, GridPanel gridPanel) {
        this.grid = grid;
        this.gridPanel = gridPanel;
        this.setUp();
    }

    private void setUp() {
        generationsLabel = new Label("Generations: " + grid.getGenerations());
        populationLabel = new Label("Population: " + grid.getLiveCells());

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
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
            });


            stopButton.addActionListener(e -> {
                timer.stop();
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
            });

            this.add(startButton);
            this.add(stopButton);
        }
    }
}
