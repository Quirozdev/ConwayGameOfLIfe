package ui.theme.buttons;

import core.Cell;
import core.CellEvent;
import interfaces.Subscriber;

import javax.swing.*;
import java.awt.*;

public class CellButton extends JButton implements Subscriber<CellEvent> {

    public CellButton(Cell cell) {
            this.setBackground(cell.isAlive() ? Color.BLACK : Color.WHITE);
            this.addActionListener(e -> {
                cell.setAlive(!cell.isAlive());
            });
    }

    @Override
    public void update(CellEvent event) {
        if (event == CellEvent.LIVE) {
            this.setBackground(Color.BLACK);
        } else if (event == CellEvent.DIE) {
            this.setBackground(Color.WHITE);
        }
    }
}
