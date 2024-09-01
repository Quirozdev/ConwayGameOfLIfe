package ui;

import core.GameGrid;

import javax.swing.*;
import java.awt.*;

public class Ui {

    private GameGrid gameGrid;

    public Ui(GameGrid gameGrid) {
        this.gameGrid = gameGrid;
    }

    public void start() {
        JFrame frame = new JFrame();
        frame.setMinimumSize(new Dimension(400, 400));

        GridPanel gridPanel = new GridPanel(this.gameGrid);
        frame.getContentPane().add(BorderLayout.CENTER, gridPanel);
        frame.getContentPane().add(BorderLayout.EAST, new MenuSectionPanel(this.gameGrid, gridPanel));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Game Of Life");
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}
