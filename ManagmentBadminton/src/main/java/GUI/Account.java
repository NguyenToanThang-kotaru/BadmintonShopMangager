package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.table.TableColumnModel;

public class Account extends JPanel {

    private JPanel midPanel, topPanel, botPanel;
    private JTable accountTable;
    private CustomTable tableModel;
    private JComboBox<String> roleComboBox;
    private CustomButton saveButton, addButton;
    private CustomSearch searchField;

    public Account() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(new Color(200, 200, 200));

        // ========== PANEL TRÊN CÙNG ==========
        topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setPreferredSize(new Dimension(0, 60));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding trên-dưới 10px
        topPanel.setBackground(Color.WHITE);
        // Thanh tìm kiếm (70%)
        searchField = new CustomSearch(250, 30);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.setBackground(Color.WHITE);
//        searchField.setPreferredSize(new Dimension(0, 30));
        topPanel.add(searchField, BorderLayout.CENTER);

        // Nút "Thêm tài khoản" (30%)
        addButton = new CustomButton("+ Thêm tài khoản");
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.setPreferredSize(new Dimension(150, 30));
        topPanel.add(addButton, BorderLayout.EAST);

//        // ========== BẢNG HIỂN THỊ ==========
        midPanel = new JPanel(new BorderLayout());
        midPanel.setBackground(Color.WHITE);
        String[] columnNames = {"STT", "Nhân viên", "Tài khoản", "Mật khẩu", "Quyền"};
        tableModel = new CustomTable(columnNames);
        accountTable=tableModel.getAccountTable();
        TableColumnModel columnModel = accountTable.getColumnModel();
        columnModel.getColumn(1).setPreferredWidth(200); // Tăng độ rộng cột "Nhân viên"
        columnModel.getColumn(2).setPreferredWidth(150); // Cột "Tài khoản"
        columnModel.getColumn(3).setPreferredWidth(150); // Cột "Mật khẩu"
        columnModel.getColumn(4).setPreferredWidth(100); // Cột "Quyền

        // ScrollPane để bảng có thanh cuộn
        CustomScrollPane scrollPane = new CustomScrollPane(accountTable);

        midPanel.add(scrollPane, BorderLayout.CENTER);

        // ========== PANEL CHI TIẾT TÀI KHOẢN ==========  
        botPanel = new JPanel(new GridBagLayout());
        botPanel.setBackground(Color.WHITE);
        botPanel.setBorder(BorderFactory.createTitledBorder("Chi tiết tài khoản"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

// Nhãn Mã nhân viên
        gbc.gridx = 0;
        gbc.gridy = 0;
        botPanel.add(new JLabel("Mã nhân viên: "), gbc);

        gbc.gridx = 1;
        JLabel employeeLabel = new JLabel("Chọn tài khoản");
        botPanel.add(employeeLabel, gbc);

// Nhãn Tài khoản
        gbc.gridx = 0;
        gbc.gridy = 1;
        botPanel.add(new JLabel("Tài khoản: "), gbc);

        gbc.gridx = 1;
        JLabel usernameLabel = new JLabel("");
        botPanel.add(usernameLabel, gbc);

// Nhãn Mật khẩu
        gbc.gridx = 0;
        gbc.gridy = 2;
        botPanel.add(new JLabel("Mật khẩu: "), gbc);

        gbc.gridx = 1;
        JLabel passwordLabel = new JLabel("");
        botPanel.add(passwordLabel, gbc);

        // Chọn quyền
        gbc.gridx = 0;
        gbc.gridy = 3;
        botPanel.add(new JLabel("Quyền tài khoản: "), gbc);

        gbc.gridx = 1;
        roleComboBox = new JComboBox<>(new String[]{"ADMIN1", "ADMIN2", "QUANLY1", "NHANVIEN1"});
        roleComboBox.setPreferredSize(new Dimension(150, 25));
        botPanel.add(roleComboBox, gbc);

        // Nút lưu
        gbc.gridx = 1;
        gbc.gridy = 4;
        saveButton = new CustomButton("💾 Lưu");
        saveButton.setPreferredSize(new Dimension(80, 30));
        botPanel.add(saveButton, gbc);

        accountTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = accountTable.getSelectedRow();
            if (selectedRow != -1) {
                // Lấy dữ liệu từ bảng
                String maNhanVien = (String) accountTable.getValueAt(selectedRow, 0);
                String tenNhanVien = (String) accountTable.getValueAt(selectedRow, 1);
                String taiKhoan = (String) accountTable.getValueAt(selectedRow, 2);
                String matKhau = (String) accountTable.getValueAt(selectedRow, 3);
                String quyen = (String) accountTable.getValueAt(selectedRow, 4);

                // Cập nhật giao diện
                employeeLabel.setText(maNhanVien + " - " + tenNhanVien);
                usernameLabel.setText(taiKhoan);
                passwordLabel.setText(matKhau);
                roleComboBox.setSelectedItem(quyen);
            }
        });

        // ========== THÊM MỌI THỨ VÀO MAINPANEL ==========
        add(topPanel);
        add(Box.createVerticalStrut(10)); // Thêm khoảng cách 10px
        add(midPanel);
        add(Box.createVerticalStrut(10)); // Thêm khoảng cách 10px
        add(botPanel);
        // ========== DỮ LIỆU MẪU ==========
        addSampleData();
    }

    private void addSampleData() {
        tableModel.addRow(new Object[]{"1", "Nguyễn Ngọc Huy", "huynguyen", "123456", "ADMIN2"});
        tableModel.addRow(new Object[]{"2", "Trần Ngọc Thảo Ngân", "thaongan", "abcdef", "ADMIN2"});
        tableModel.addRow(new Object[]{"3", "Trương Thị Cẩm Tú", "camtutruong", "123abc", "NHANVIEN1"});
        tableModel.addRow(new Object[]{"4", "Tăng Hồng Nguyên Đán", "nguyendan", "password", "null"});
        tableModel.addRow(new Object[]{"5", "Hồ Đỗ Hoàng Khang", "hodokhang", "hoang123", "QUANLY1"});
        tableModel.addRow(new Object[]{"6", "Đỗ Thị Cẩm Tiên", "camtiendo", "pass123", "null"});
        tableModel.addRow(new Object[]{"7", "Nguyễn Thị Diễm My", "diemmy", "my123", "null"});
        tableModel.addRow(new Object[]{"8", "Nguyễn Thị Mỹ Nương", "mynuong", "nuongpass", "ADMIN1"});
        tableModel.addRow(new Object[]{"9", "Võ Phát Thành", "phatthanh", "thanhpass", "null"});
        tableModel.addRow(new Object[]{"10", "Nguyễn Thành Thắng", "thanhnguyen", "nguyen123", "ADMIN2"});
        tableModel.addRow(new Object[]{"5", "Hồ Đỗ Hoàng Khang", "hodokhang", "hoang123", "QUANLY1"});
        tableModel.addRow(new Object[]{"6", "Đỗ Thị Cẩm Tiên", "camtiendo", "pass123", "null"});
        tableModel.addRow(new Object[]{"7", "Nguyễn Thị Diễm My", "diemmy", "my123", "null"});
        tableModel.addRow(new Object[]{"8", "Nguyễn Thị Mỹ Nương", "mynuong", "nuongpass", "ADMIN1"});
        tableModel.addRow(new Object[]{"9", "Võ Phát Thành", "phatthanh", "thanhpass", "null"});
        tableModel.addRow(new Object[]{"10", "Nguyễn Thành Thắng", "thanhnguyen", "nguyen123", "ADMIN2"});
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            JFrame frame = new JFrame("Quản lý tài khoản");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setSize(800, 600); // Đặt kích thước cửa sổ
//            frame.setLocationRelativeTo(null); // Căn giữa màn hình
//            frame.setContentPane(new Account()); // Đặt JPanel Account vào JFrame
//            frame.setVisible(true);
//        });
//    }
}
