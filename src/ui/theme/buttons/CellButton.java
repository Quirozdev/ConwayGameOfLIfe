package ui.theme.buttons;

import core.Cell;

import javax.swing.*;
import java.awt.*;

public class CellButton extends JButton {

    public CellButton(Cell cell) {
            this.setBackground(cell.isAlive() ? Color.BLACK : Color.WHITE);
            this.addActionListener(e -> {
                cell.setAlive(!cell.isAlive());
                this.setBackground(cell.isAlive() ? Color.BLACK : Color.WHITE);
            });
    }
}
