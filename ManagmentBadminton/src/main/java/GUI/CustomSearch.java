package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CustomSearch extends JPanel {
    private RoundedTextField searchField;
    private JButton searchButton;

    public CustomSearch(int width, int height) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(width, height));
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Thêm khoảng cách

        // Tạo thanh tìm kiếm bo tròn
        searchField = new RoundedTextField(20); // Bo góc 20px
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.setPreferredSize(new Dimension(width - 40, height - 10));
        searchField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Tạo padding bên trong

        // Load icon kính lúp
        ImageIcon searchIcon = new ImageIcon(getClass().getResource("/images/search.png"));
        Image img = searchIcon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
        searchIcon = new ImageIcon(img);

        // Tạo nút tìm kiếm với icon kính lúp
        searchButton = new JButton(searchIcon);
        searchButton.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
        searchButton.setFocusPainted(false);
        searchButton.setContentAreaFilled(false);

        // Thêm thành phần vào panel
        add(searchField, BorderLayout.CENTER);
        add(searchButton, BorderLayout.EAST);
    }

    // Lấy nội dung nhập vào
    public String getText() {
        return searchField.getText();
    }

    // Đặt sự kiện khi nhấn nút tìm kiếm
    public void setSearchListener(ActionListener listener) {
        searchButton.addActionListener(listener);
    }

    private void setBackgound(Color WHITE) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    // Lớp JTextField tùy chỉnh với góc bo tròn
    private static class RoundedTextField extends JTextField {
        private int cornerRadius;

        public RoundedTextField(int cornerRadius) {
            this.cornerRadius = cornerRadius;
            setOpaque(false); // Để vẽ nền tùy chỉnh
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Vẽ nền bo tròn
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

            // Vẽ viền
            g2.setColor(Color.GRAY);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);

            g2.dispose();
            super.paintComponent(g);
        }
    }
}
