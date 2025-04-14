package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import BUS.AccountBUS;
import BUS.PermissionBUS;
import DTO.PermissionDTO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GUI_Permission extends JPanel {

    // Khai báo các thành phần giao diện
    private JPanel midPanel, topPanel, botPanel, buttonPanel;
    private JTable permissionTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> roleComboBox;
    private CustomButton editButton, addButton, deleteButton, detailButton;
    private CustomSearch searchField;
    private PermissionDTO permissionChoosing;

    public GUI_Permission() {
        // Cấu hình layout chính
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(new Color(200, 200, 200));

        // ========== PANEL TRÊN CÙNG (Thanh tìm kiếm & nút thêm) ==========
        topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setPreferredSize(new Dimension(0, 60));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(Color.WHITE);

        searchField = new CustomSearch(275, 40); // Ô nhập tìm kiếm
        searchField.setBackground(Color.WHITE);
        topPanel.add(searchField, BorderLayout.CENTER);

        addButton = new CustomButton("+ Thêm quyền"); // Nút thêm tài khoản
        topPanel.add(addButton, BorderLayout.EAST);

        // ========== BẢNG HIỂN THỊ DANH SÁCH TÀI KHOẢN ==========
        midPanel = new JPanel(new BorderLayout());
        midPanel.setBackground(Color.WHITE);

        // Định nghĩa tiêu đề cột
        String[] columnNames = {"STT", "Tên Quyền", "Số Lượng Chức Năng", "Số Lượng Tài Khoản"};
        CustomTable customTable = new CustomTable(columnNames);
        permissionTable = customTable.getAccountTable(); // Lấy JTable từ CustomTable
        tableModel = customTable.getTableModel(); // Lấy model của bảng

        midPanel.add(customTable, BorderLayout.CENTER);

        // ========== PANEL CHI TIẾT TÀI KHOẢN ==========
        botPanel = new JPanel(new GridBagLayout());
        botPanel.setBackground(Color.WHITE);
        botPanel.setBorder(BorderFactory.createTitledBorder("Chi tiết quyền"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Nhãn hiển thị thông tin tài khoản
        gbc.gridx = 0;
        gbc.gridy = 0;
        botPanel.add(new JLabel("Tên quyền: "), gbc);
        gbc.gridx = 1;
        JLabel permissionLabel = new JLabel("Chọn quyền");
        botPanel.add(permissionLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        botPanel.add(new JLabel("Số lượng chức năng "), gbc);
        gbc.gridx = 1;
        JLabel functionLabel = new JLabel("");
        botPanel.add(functionLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        botPanel.add(new JLabel("Số lượng tài khoản: "), gbc);
        gbc.gridx = 1;
        JLabel accountLabel = new JLabel("");
        botPanel.add(accountLabel, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        buttonPanel.setOpaque(false);

        deleteButton = new CustomButton("Xóa");

        editButton = new CustomButton("Sửa");

        detailButton = new CustomButton("Xem Chi Tiết Quyền");

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Xử lý sự kiện chọn tài khoản trong bảng
        permissionTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = permissionTable.getSelectedRow();
            if (selectedRow != -1) {
                // Lấy dữ liệu từ bảng và chuyển đổi sang String một cách an toàn
//                Object value = permissionTable.getValueAt(selectedRow, 0);
                String tenQuyen = (String) permissionTable.getValueAt(selectedRow, 1);
                String totalFunction = (String) permissionTable.getValueAt(selectedRow, 2);
                String totalPermission = (String) permissionTable.getValueAt(selectedRow, 3);
                permissionChoosing = PermissionBUS.getPermissionByName(tenQuyen);
                // Hiển thị dữ liệu trên giao diện
                permissionLabel.setText(tenQuyen);
                functionLabel.setText(totalFunction);
                accountLabel.setText(totalPermission);
                buttonPanel.add(deleteButton);
                buttonPanel.add(editButton);
                buttonPanel.add(detailButton);
                botPanel.add(buttonPanel, gbc);

            }
        });

        deleteButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có chắc chắn muốn xóa quyền này?",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (result == JOptionPane.YES_OPTION) {
                if (PermissionBUS.delete_Permission(permissionChoosing.getID())) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công");
                    loadPermissions();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        editButton.addActionListener(e -> {
            System.out.println("Da luu");
        });

        addButton.addActionListener(e -> {
            System.out.println("Da them");
        });

        // Thêm các panel vào giao diện chính
        add(topPanel);
        add(Box.createVerticalStrut(10));
        add(midPanel);
        add(Box.createVerticalStrut(10));
        add(botPanel);

        // Tải dữ liệu tài khoản lên bảng
        loadPermissions();
    }

    // Phương thức tải danh sách tài khoản từ database lên bảng
    private void loadPermissions(){
        ArrayList<PermissionDTO> permission = PermissionBUS.getAllPermissions();
        tableModel.setRowCount(0); // Xóa dữ liệu cũ trước khi cập nhật
        int index = 1;
        for (PermissionDTO per : permission) {
            tableModel.addRow(new Object[]{index++, per.getName(), per.getTotalFunction(), per.getTotalAccount()});
        }
    }
}
