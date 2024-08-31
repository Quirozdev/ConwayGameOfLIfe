package ui;

import core.Grid;

import javax.swing.*;
import java.awt.*;

public class Ui {

    private Grid grid;

    public Ui(Grid grid) {
        this.grid = grid;
    }

    public void start() {
        JFrame frame = new JFrame();

        GridPanel gridPanel = new GridPanel(this.grid);
        frame.getContentPane().add(BorderLayout.CENTER, gridPanel);
        frame.getContentPane().add(BorderLayout.EAST, new MenuSectionPanel(this.grid, gridPanel));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Game Of Life");
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}
