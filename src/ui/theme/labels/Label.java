package ui.theme.labels;

import javax.swing.*;
import java.awt.*;

public class Label extends JLabel {

    public Label(String text) {
        this.setText(text);
        this.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
    }
}
