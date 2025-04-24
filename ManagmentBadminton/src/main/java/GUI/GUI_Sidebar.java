package GUI;

import DTO.AccountDTO;
import DTO.FunctionActionDTO;
import DTO.PermissionDTO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.border.MatteBorder;

/**
 *
 * @author Thang Nguyen
 */
public class GUI_Sidebar extends JPanel {

    private ArrayList<String> menuItems;
    public JPanel statisticsPanel, productPanel, orderPanel,
            supplierPanel, importPanel, promotionPanel,
            customerPanel, accountPanel, rolePanel,
            repairPanel, employeePanel;
    private JLabel titleMenu;
    public JPanel panel1, panel2, panel3;
    public CustomScrollPane scrollPane;

    public GUI_Sidebar(JFrame login, JFrame Main_Layout, AccountDTO logined) {
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

        // Duyệt qua tất cả các quyền trong danh sách permission
        menuItems = new ArrayList<>();

        // Duyệt qua tất cả các quyền trong danh sách permission
        PermissionDTO permission = logined.getPermission();
        // Duyệt qua các chức năng trong PermissionDTO
        for (FunctionActionDTO function : permission.getFunction()) {
            String nameUnsigned = function.getNameUnsigned().toLowerCase(); // Chuyển thành chữ thường để dễ so sánh

            // Kiểm tra xem chức năng có chứa từ khóa không dấu trong tên chức năng
            if (nameUnsigned.contains("thong ke")) {
                menuItems.add("Thống kê");
            } else if (nameUnsigned.contains("san pham")) {
                menuItems.add("Sản Phẩm");
            } else if (nameUnsigned.contains("don hang")) {
                menuItems.add("Đơn Hàng");
            } else if (nameUnsigned.contains("nhan vien")) {
                menuItems.add("Nhân Viên");
            } else if (nameUnsigned.contains("nha cung cap")) {
                menuItems.add("Nhà Cung Cấp");
            } else if (nameUnsigned.contains("khach hang")) {
                menuItems.add("Khách Hàng");
            } else if (nameUnsigned.contains("tai khoan")) {
                menuItems.add("Tài Khoản");
            } else if (nameUnsigned.contains("bao hanh")) {
                menuItems.add("Bảo Hành");
            } else if (nameUnsigned.contains("hoa don nhap")) {
                menuItems.add("Hóa Đơn Nhập");
            } else if (nameUnsigned.contains("phan quyen")) {
                menuItems.add("Phân Quyền");
            } else if (nameUnsigned.contains("khuyen mai")) {
                menuItems.add("Khuyến Mãi");
            }
        }

        panel2 = new JPanel();

        panel2.setLayout(
                new BoxLayout(panel2, BoxLayout.Y_AXIS));
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
        scrollPane = new CustomScrollPane(panel2);

        // ====== Thêm JScrollPane vào sidebar ======
        // ====== panel3: Nút thoát ======
        panel3 = new JPanel();

        panel3.setBackground(Color.LIGHT_GRAY);

        panel3.setPreferredSize(
                new Dimension(this.getWidth(), 50));

        JButton logoutButton = new JButton("Đăng xuất");

        logoutButton.setForeground(Color.WHITE);

        logoutButton.setBackground(Color.RED);

        logoutButton.setFocusPainted(
                false);
        logoutButton.setFont(
                new Font("Arial", Font.BOLD, 14));

        logoutButton.addActionListener(
                (ActionEvent e) -> {
                    logout(login, Main_Layout); // Gọi hàm check khi bấm nút
                }
        );

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
