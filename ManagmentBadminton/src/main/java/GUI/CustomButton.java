package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

public class CustomButton extends JButton {

    private Color normalColor = new Color(0, 150, 255);
    private int cornerRadius = 15;

    public CustomButton(String text) {
        super(text);
        setFont(new Font("Arial", Font.BOLD, 14));
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setOpaque(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Chọn màu dựa trên trạng thái
        if (getModel().isPressed()) {
            g2.setColor(getPressedColor());
        } else if (getModel().isRollover()) {
            g2.setColor(getHoverColor());
        } else {
            g2.setColor(normalColor);
        }

        // Vẽ nền bo góc
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        g2.dispose();

        super.paintComponent(g);
    }

    public void setCustomColor(Color color) {
        this.normalColor = color;
        repaint();
    }

    // ✅ **Hàm linh hoạt để lấy màu hover dựa trên màu nền**
    public Color getHoverColor() {
        return normalColor.brighter(); // Làm sáng màu hiện tại
    }

    // ✅ **Hàm linh hoạt để lấy màu pressed dựa trên màu nền**
    public Color getPressedColor() {
        return normalColor.darker(); // Làm tối màu hiện tại
    }
}
