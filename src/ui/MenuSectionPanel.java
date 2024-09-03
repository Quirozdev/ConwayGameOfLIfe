package ui;

import core.GameGrid;
import core.GameGridEvent;
import interfaces.Subscriber;
import ui.theme.buttons.Button;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuSectionPanel extends JPanel {

    private GameGrid gameGrid;
    private GridPanel gridPanel;
    private JLabel generationsLabel;
    private JLabel populationLabel;
    private Timer timer;

    public MenuSectionPanel(GameGrid gameGrid, GridPanel gridPanel, JLabel generationsLabel, JLabel populationLabel) {
        this.gameGrid = gameGrid;
        this.gridPanel = gridPanel;
        this.generationsLabel = generationsLabel;
        this.populationLabel = populationLabel;
        this.timer = new Timer(100, null);
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


            GridLayout layout = new GridLayout(4, 1);
            layout.setVgap(8);
            layout.setHgap(8);
            this.setLayout(layout);


            JLabel delayLabel = new JLabel("Delay: " + timer.getDelay() + " ms");
            JSlider delaySlider = new JSlider();
            delaySlider.setMinimum(1);
            delaySlider.setMaximum(10000);
            delaySlider.setMajorTickSpacing(9999);
            delaySlider.setPaintLabels(true);
            delaySlider.setValue(timer.getDelay());
            delaySlider.addChangeListener(e -> {
                JSlider source = (JSlider) e.getSource();
                if (!source.getValueIsAdjusting()) {
                    timer.setDelay(delaySlider.getValue());
                    delayLabel.setText("Delay: " + timer.getDelay() + " ms");
                }
            });


            this.add(startButton);
            this.add(stopButton);
            this.add(randomizeButton);
            this.add(resetButton);

            this.add(delaySlider);
            this.add(delayLabel);
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
                    break;
            }
        }
    }
}
