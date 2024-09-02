package ui;

import core.GameGrid;
import core.GameGridEvent;
import interfaces.Subscriber;
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

    public MenuSectionPanel(GameGrid gameGrid, GridPanel gridPanel, JLabel generationsLabel, JLabel populationLabel) {
        this.gameGrid = gameGrid;
        this.gridPanel = gridPanel;
        this.generationsLabel = generationsLabel;
        this.populationLabel = populationLabel;
        this.setUp();
    }

    private void setUp() {
        GridLayout layout = new GridLayout(4, 1);
        layout.setHgap(20);
        this.setLayout(layout);

        this.setBorder(new EmptyBorder(20, 20, 20, 20));

        this.add(generationsLabel);
        this.add(populationLabel);
        this.add(new ButtonsPanel());
    }

    class ButtonsPanel extends JPanel implements Subscriber<GameGridEvent> {

        JButton startButton;
        JButton stopButton;
        JButton randomizeButton;
        JButton resetButton;


        public ButtonsPanel() {
            gameGrid.addSubscriber(this);
            this.setUp();
        }

        private void setUp() {
            this.startButton = new Button("Start", new Color(87, 204, 153));
            this.stopButton = new Button("Stop", new Color(230, 57, 70));
            this.stopButton.setEnabled(false);

            this.randomizeButton = new Button("Randomize", new Color(255, 130, 37));
            this.resetButton = new Button("Reset", new Color(22, 50, 91));
            this.resetButton.setEnabled(false);

            Timer timer = new Timer(100, null);
            ActionListener startListener = e -> {
                if (!gameGrid.areCellsAlive()) {
                    timer.stop();
                    startButton.setEnabled(true);
                    stopButton.setEnabled(false);
                    resetButton.setEnabled(true);
                } else {
                    gameGrid.advanceGeneration();
                }
            };
            timer.addActionListener(startListener);
            timer.setRepeats(true);

            startButton.addActionListener(e -> {
                timer.start();
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
                randomizeButton.setEnabled(false);
                resetButton.setEnabled(false);
            });


            stopButton.addActionListener(e -> {
                timer.stop();
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
                resetButton.setEnabled(true);
            });

            randomizeButton.addActionListener(e -> {
                gameGrid.generateRandomLiveCells();
            });

            resetButton.addActionListener(e -> {
               gameGrid.reset();
            });

            JSlider widthSlider = new JSlider();
            widthSlider.setMinimum(1);
            widthSlider.setMaximum(1000);
            widthSlider.setMajorTickSpacing(250);
            widthSlider.setMinorTickSpacing(50);
            widthSlider.setPaintTicks(true);
            widthSlider.setPaintLabels(true);
            widthSlider.addChangeListener(e -> {
                JSlider source = (JSlider) e.getSource();
                if (!source.getValueIsAdjusting()) {
                    System.out.println("XDD");
                }
            });

            GridLayout layout = new GridLayout(4, 1);
            layout.setVgap(8);
            this.setLayout(layout);

            this.add(startButton);
            this.add(stopButton);
            this.add(randomizeButton);
            this.add(resetButton);

            this.add(widthSlider);
        }

        @Override
        public void update(GameGridEvent event, int row, int col) {
            switch (event) {
                case ADVANCE_GENERATION:
                    generationsLabel.setText("Generations: " + gameGrid.getGenerations());
                    populationLabel.setText("Population: " + gameGrid.getLiveCells());
                    gridPanel.repaint();
                    break;
                case RANDOMIZATION:
                    generationsLabel.setText("Generations: " + gameGrid.getGenerations());
                    populationLabel.setText("Population: " + gameGrid.getLiveCells());
                    gridPanel.repaint();
                    this.resetButton.setEnabled(true);
                    break;
                case RESET:
                    generationsLabel.setText("Generations: " + gameGrid.getGenerations());
                    populationLabel.setText("Population: " + 0);
                    gridPanel.repaint();
                    randomizeButton.setEnabled(true);
                    this.resetButton.setEnabled(false);
            }
        }
    }
}
