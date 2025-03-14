package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.border.MatteBorder;

/**
 *
 * @author Thang Nguyen
 */
public class Sidebar extends JPanel {

    private ArrayList<String> menuItems;
    public JPanel statisticsPanel, productPanel, orderPanel,
            supplierPanel, importPanel, promotionPanel,
            customerPanel, accountPanel, rolePanel,
            repairPanel, employeePanel;
    private JLabel titleMenu;
    public JPanel panel1, panel2, panel3;

    public Sidebar(JFrame login, JFrame Main_Layout) {
        setLayout(new BorderLayout());
        setBackground(Color.LIGHT_GRAY);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setPreferredSize(new Dimension(200, 600));

        panel1 = new JPanel();
        panel1.setBackground(Color.LIGHT_GRAY);
        panel1.setLayout(new FlowLayout(FlowLayout.CENTER));
        titleMenu = new JLabel("MENU");
        titleMenu.setFont(new Font("Roboto", Font.BOLD, 30));
        titleMenu.setForeground(new Color(0, 153, 153));
        titleMenu.setBorder(new MatteBorder(0, 0, 2, 0, new Color(0, 153, 100)));
        panel1.add(titleMenu);
        // ====== panel2: Danh sách menu ======

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
        panel2 = new JPanel();
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
        panel3 = new JPanel();
        panel3.setBackground(Color.LIGHT_GRAY);
        panel3.setPreferredSize(new Dimension(this.getWidth(), 50));

        JButton logoutButton = new JButton("Đăng xuất");
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBackground(Color.RED);
        logoutButton.setFocusPainted(false);
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));

        logoutButton.addActionListener((ActionEvent e) -> {
            logout(login,Main_Layout); // Gọi hàm check khi bấm nút
        });

        panel3.add(logoutButton);

        // ====== Sắp xếp các panel trong sidebar ======
        add(panel1, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panel3, BorderLayout.SOUTH); // Đặt ở dưới cùng
    }

    private void logout(JFrame login, JFrame Main_Layout) {
        login.setVisible(true);
        Main_Layout.setVisible(false);
    }

}
