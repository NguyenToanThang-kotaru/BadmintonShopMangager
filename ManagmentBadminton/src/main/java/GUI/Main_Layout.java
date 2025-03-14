package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.border.MatteBorder;

public class Main_Layout extends JFrame {

    private int mouseX, mouseY;
    private ArrayList<String> menuItems;

    public Main_Layout() {
        setTitle("Quản Lý Kho Hàng");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0, 0));
        setUndecorated(true);

        // ====== Danh sách menu từ ArrayList ======
        menuItems = new ArrayList<>();
        menuItems.add("Thống kê");
        menuItems.add("Sản Phẩm");
        menuItems.add("Đơn Hàng");
        menuItems.add("Nhân Viên");
        menuItems.add("Nhà Cung Cấp");
        menuItems.add("Hóa Đơn Nhập");
        menuItems.add("Khuyến Mãi");
        menuItems.add("Khách Hàng");
        menuItems.add("Tài Khoản");
        menuItems.add("Bảo Hành");
        menuItems.add("Phân Quyền");

        // ================================ Title Bar ================================
        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(new Color(50, 50, 50));
        titleBar.setPreferredSize(new Dimension(getWidth(), 40));

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
        minimizeButton.addActionListener(e -> setState(JFrame.ICONIFIED));

        JButton closeButton = new JButton("X");
        closeButton.setForeground(Color.WHITE);
        closeButton.setBackground(Color.RED);
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> System.exit(0));

        // Thêm nút vào rightPanel
        rightPanel.add(minimizeButton);
        rightPanel.add(closeButton);

        // ====== Thêm vào Title Bar ======
        titleBar.add(leftPanel, BorderLayout.WEST);
        titleBar.add(rightPanel, BorderLayout.EAST);

        // ================================ Sidebar ================================
        JPanel sidebarPanel = new JPanel(new BorderLayout());
        sidebarPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        sidebarPanel.setBackground(Color.LIGHT_GRAY);
        sidebarPanel.setPreferredSize(new Dimension(200, 600));

        // ====== panel1: Tiêu đề MENU ======
        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.LIGHT_GRAY);
        panel1.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel titleMenu = new JLabel("MENU");
        titleMenu.setFont(new Font("Roboto", Font.BOLD, 30));
        titleMenu.setForeground(new Color(0, 153, 153));
        titleMenu.setBorder(new MatteBorder(0, 0, 2, 0, new Color(0, 153, 100)));
        panel1.add(titleMenu);

        // ====== panel2: Danh sách menu ======
        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        panel2.setBackground(Color.LIGHT_GRAY);

        for (String item : menuItems) {
            // Định nghĩa đường dẫn của icon tương ứng
            String iconPath = "src/main/resources/images/";
            switch (item) {
                case "Thống kê":
                    iconPath += "icontk.png";
                    break;
                case "Sản Phẩm":
                    iconPath += "icon_sanpham.png";
                    break;
                case "Đơn Hàng":
                    iconPath += "icon_donhang.png";
                    break;
                case "Nhân Viên":
                    iconPath += "icon_nhanvien.png";
                    break;
                case "Nhà Cung Cấp":
                    iconPath += "icon_supplier.png";
                    break;
                case "Khách Hàng":
                    iconPath += "icon_khachhang.png";
                    break;
                case "Tài Khoản":
                    iconPath += "icon_account.png";
                    break;
                case "Bảo Hành":
                    iconPath += "icon_baohanh.png";
                    break;
                case "Hóa Đơn Nhập":
                    iconPath += "hoadonnhap.png";
                    break;
                case "Phân Quyền":
                    iconPath += "icon_role.png";
                    break;
                case "Khuyến Mãi":
                    iconPath += "icon_khuyenmai.png";
                    break;
                default:
                    iconPath = null;
            }

            // Tạo JLabel có icon
            JLabel label;
            if (iconPath != null) {
                ImageIcon icon = new ImageIcon(iconPath);
                Image img = icon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
                icon = new ImageIcon(img);
                label = new JLabel(item, icon, JLabel.LEFT);
            } else {
                label = new JLabel(item, JLabel.LEFT);
            }

            label.setFont(new Font("Roboto", Font.BOLD, 19));
            label.setOpaque(true);
            label.setBackground(Color.LIGHT_GRAY);
            label.setForeground(Color.BLACK);
            label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            label.setPreferredSize(new Dimension(200, 40));

            // Hiệu ứng hover
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    label.setBackground(new Color(0, 153, 153));
                    label.setForeground(Color.WHITE);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    label.setBackground(Color.LIGHT_GRAY);
                    label.setForeground(Color.BLACK);
                }
            });

            panel2.add(label);
            panel2.add(Box.createVerticalStrut(5));
        }
        // ====== Tạo JScrollPane cho menu ======
        JScrollPane scrollPane = new JScrollPane(panel2);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10); // Tốc độ cuộn
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setPreferredSize(new Dimension(0, 0)); // Giảm độ rộng thanh cuộn
        verticalScrollBar.setUnitIncrement(10); // Cuộn mượt hơn
        verticalScrollBar.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 1)); // Viền mỏng màu nhạt

        // Tùy chỉnh màu sắc thanh cuộn
        verticalScrollBar.setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(100, 100, 100); // Màu của phần cuộn
                this.trackColor = new Color(220, 220, 220); // Màu nền của thanh cuộn
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton(); // Ẩn nút lên trên
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton(); // Ẩn nút xuống dưới
            }

            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0)); // Loại bỏ nút tăng/giảm
                button.setVisible(false);
                return button;
            }
        });

    // ====== Thêm JScrollPane vào sidebar ======
        // ====== panel3: Nút thoát ======
        JPanel panel3 = new JPanel();
        panel3.setBackground(Color.LIGHT_GRAY);
        panel3.setPreferredSize(new Dimension(getWidth(), 50));

        JButton exitButton = new JButton("Thoát");
        exitButton.setForeground(Color.WHITE);
        exitButton.setBackground(Color.RED);
        exitButton.setFocusPainted(false);
        exitButton.setFont(new Font("Arial", Font.BOLD, 14));
        exitButton.addActionListener(e -> System.exit(0));
        panel3.add(exitButton);

        // ====== Sắp xếp các panel trong sidebar ======
        sidebarPanel.add(panel1, BorderLayout.NORTH);
        sidebarPanel.add(scrollPane, BorderLayout.CENTER);
        sidebarPanel.add(panel3, BorderLayout.SOUTH); // Đặt ở dưới cùng
            
        add(titleBar, BorderLayout.NORTH);
        add(sidebarPanel, BorderLayout.WEST);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main_Layout frame = new Main_Layout();
            frame.setVisible(true);
        });
    }
}
