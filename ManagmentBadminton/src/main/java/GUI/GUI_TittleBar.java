/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

/**
 *
 * @author Thang Nguyen
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


class GUI_TittleBar extends JPanel {
    private int mouseX, mouseY;
    public GUI_TittleBar(JFrame parentFrame){
        setLayout(new BorderLayout());
        setBackground(new Color(50, 50, 50));
        setPreferredSize(new Dimension(parentFrame.getWidth(), 40));

        // Xử lý sự kiện kéo cửa sổ
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // Lấy vị trí của cửa sổ và di chuyển nó
                int x = parentFrame.getLocation().x + e.getX() - mouseX;
                int y = parentFrame.getLocation().y + e.getY() - mouseY;
                parentFrame.setLocation(x, y);
            }
        });

        // ====== Left Panel (Icon + Title) ======
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        leftPanel.setOpaque(false);

        // Load icon ứng dụng
        String iconLogo = "src/main/resources/images/LOGOAPP.png";
        ImageIcon appIcon = new ImageIcon(iconLogo);
        Image imgLogo = appIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        appIcon = new ImageIcon(imgLogo);

        JLabel iconLabel = new JLabel(appIcon);
        JLabel titleLabel = new JLabel(" Quản Lý Cửa Hàng Cầu Lông");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Thêm icon + tiêu đề vào leftPanel
        leftPanel.add(iconLabel);
        leftPanel.add(titleLabel);

        // ====== Right Panel (Nút minimize + close) ======
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        rightPanel.setOpaque(false);

        JButton minimizeButton = new JButton("–");
        minimizeButton.setForeground(Color.WHITE);
        minimizeButton.setBackground(new Color(100, 100, 100));
        minimizeButton.setFocusPainted(false);
        minimizeButton.addActionListener(e -> parentFrame.setState(JFrame.ICONIFIED));

        JButton closeButton = new JButton("X");
        closeButton.setForeground(Color.WHITE);
        closeButton.setBackground(Color.RED);
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> System.exit(0));

        // Thêm nút vào rightPanel
        rightPanel.add(minimizeButton);
        rightPanel.add(closeButton);

        // ====== Thêm vào Title Bar ======
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
    }

}
