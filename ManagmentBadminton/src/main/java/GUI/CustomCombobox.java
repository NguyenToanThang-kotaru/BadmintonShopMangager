package GUI;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;

public class CustomCombobox<E> extends JComboBox<E> {
    public CustomCombobox(E[] items) {
        super(items);
        setUI(new CustomComboboxUI()); // Áp dụng UI tùy chỉnh
        setFont(new Font("Arial", Font.PLAIN, 14));
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        setMaximumRowCount(3);
    }

}

// Lớp CustomComboboxUI để tùy chỉnh UI
class CustomComboboxUI extends BasicComboBoxUI {
    @Override
    protected JButton createArrowButton() {
        JButton button = new JButton("▼");
        button.setBorder(null);
        button.setContentAreaFilled(false);
        button.setForeground(Color.DARK_GRAY);
        return button;
    }

    @Override
    public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
        g.setColor(Color.WHITE);
        g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
    }

}
