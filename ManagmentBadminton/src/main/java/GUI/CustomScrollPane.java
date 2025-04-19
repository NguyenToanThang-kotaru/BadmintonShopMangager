package GUI;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class CustomScrollPane extends JScrollPane {

    public CustomScrollPane(Component view) {
        super(view);
        setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        getVerticalScrollBar().setUnitIncrement(10); // Cuộn mượt hơn
        setBorder(BorderFactory.createEmptyBorder());

        // Tùy chỉnh thanh cuộn
        customizeScrollBar();
    }

    private void customizeScrollBar() {
        JScrollBar verticalScrollBar = getVerticalScrollBar();
        verticalScrollBar.setPreferredSize(new Dimension(0, 0)); // Giảm độ rộng
        verticalScrollBar.setUnitIncrement(10);
        verticalScrollBar.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 1));

        verticalScrollBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(100, 100, 100); // Màu của phần cuộn
                this.trackColor = new Color(220, 220, 220); // Màu nền của thanh cuộn
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setVisible(false);
                return button;
            }
        });
    }
}
