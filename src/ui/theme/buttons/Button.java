package ui.theme.buttons;

import javax.swing.*;
import java.awt.*;

public class Button extends JButton {

    public Button(String text, Color color) {
        this.setText(text);
        this.setBackground(color);
        this.setForeground(Color.WHITE);
        this.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(12, 18, 12, 18)
        ));
    }
}
