package GUI;

import DTO.EmployeeDTO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import BUS.EmployeeBUS;
import BUS.PermissionBUS;
import DAO.EmployeeDAO;
import java.util.ArrayList;

public class GUI_Employee extends JPanel {

    // Khai báo các thành phần giao diện
    private JPanel midPanel, topPanel, botPanel;
    private JTable employeeTable;
    private DefaultTableModel tableModel;
    private JLabel nameLabel,addressLabel,phoneLabel,genderLabel,ageLabel;
    private CustomButton deleteButton, addButton, editButton, reloadButton;
    private CustomSearch searchField;
    private EmployeeBUS employeeBUS;
    private EmployeeDTO employeeChoosing;
    private EmployeeDAO EmployeeDAO;

    public GUI_Employee() {
        employeeBUS = new EmployeeBUS(); // Khởi tạo đối tượng BUS để lấy dữ liệu tài khoản
        // Cấu hình layout chính
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(new Color(200, 200, 200));

        // ========== PANEL TRÊN CÙNG (Thanh tìm kiếm & nút thêm) ==========
        topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setPreferredSize(new Dimension(0, 60));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(Color.WHITE);

        reloadButton = new CustomButton("Tải lại trang");
        topPanel.add(reloadButton, BorderLayout.WEST);

        searchField = new CustomSearch(275, 20); // Ô nhập tìm kiếm
        searchField.setBackground(Color.WHITE);
        topPanel.add(searchField, BorderLayout.CENTER);

        addButton = new CustomButton("+ Thêm Tài Khoản"); // Nút thêm tài khoản
        topPanel.add(addButton, BorderLayout.EAST);

        // ========== BẢNG HIỂN THỊ DANH SÁCH TÀI KHOẢN ==========
        midPanel = new JPanel(new BorderLayout());
        midPanel.setBackground(Color.WHITE);

        // Định nghĩa tiêu đề cột
        String[] columnNames = {"STT", "Nhân viên", "Địa chỉ", "Số điện thoại", "Giới tính"};
        CustomTable customTable = new CustomTable(columnNames);
        employeeTable = customTable.getAccountTable(); // Lấy JTable từ CustomTable
        tableModel = customTable.getTableModel(); // Lấy model của bảng

        midPanel.add(customTable, BorderLayout.CENTER);
        CustomScrollPane scrollPane = new CustomScrollPane(employeeTable);
        // ========== PANEL CHI TIẾT TÀI KHOẢN ==========
        botPanel = new JPanel(new GridBagLayout());
        botPanel.setBackground(Color.WHITE);
        botPanel.setBorder(BorderFactory.createTitledBorder("Chi Tiết Tài Khoản"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Nhãn hiển thị thông tin tài khoản
        gbc.gridx = 0;
        gbc.gridy = 0;
        botPanel.add(new JLabel("Tên Nhân Viên: "), gbc);
        gbc.gridx = 1;
        nameLabel = new JLabel("Chọn nhân viên");
        botPanel.add(nameLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        botPanel.add(new JLabel("Địa chỉ: "), gbc);
        gbc.gridx = 1;
        addressLabel = new JLabel("");
        botPanel.add(addressLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        botPanel.add(new JLabel("Số điện thoại: "), gbc);
        gbc.gridx = 1;
        phoneLabel = new JLabel("");
        botPanel.add(phoneLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        botPanel.add(new JLabel("Tuổi: "), gbc);
        gbc.gridx = 1;
        ageLabel = new JLabel("");
        botPanel.add(ageLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        botPanel.add(new JLabel("Giới tính: "), gbc);
        gbc.gridx = 1;
        genderLabel = new JLabel("");
        botPanel.add(genderLabel, gbc);
        // Tạo panel chứa hai nút
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setOpaque(false); // Để không ảnh hưởng đến màu nền

        // Nút xóa (căn trái)
        deleteButton = new CustomButton("Xoá");
        buttonPanel.add(deleteButton, BorderLayout.WEST);

        // Nút sửa (căn phải)
        editButton = new CustomButton("Sửa");
        buttonPanel.add(editButton, BorderLayout.EAST);

        // Thêm panel vào `botPanel`
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2; // Trải dài 2 cột
        gbc.fill = GridBagConstraints.HORIZONTAL; // Căn chỉnh full chiều ngang

        // Xử lý sự kiện chọn tài khoản trong bảng
        employeeTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = employeeTable.getSelectedRow();
            if (selectedRow != -1) {
                // Lấy dữ liệu từ bảng và chuyển đổi sang String một cách an toàn

                String tenNhanVien = (String) employeeTable.getValueAt(selectedRow, 1);
                String diachi = (String) employeeTable.getValueAt(selectedRow, 2);
                String sdt = (String) employeeTable.getValueAt(selectedRow, 3);
                String gender = (String) employeeTable.getValueAt(selectedRow, 4);
//                PermissionDTO temp = PermissionDAO.getPermissionByName(quyen);
                employeeChoosing = EmployeeBUS.getEmployeeByPhone(sdt);
                // Hiển thị dữ liệu trên giao diện
                nameLabel.setText(tenNhanVien);
                addressLabel.setText(diachi);
                phoneLabel.setText(sdt);
                ageLabel.setText(employeeChoosing.getAge());
                genderLabel.setText(gender);
                botPanel.add(buttonPanel, gbc);
            }
        });

        // Thêm các panel vào giao diện chính
        add(topPanel);
        add(Box.createVerticalStrut(10));

        add(scrollPane);
        add(Box.createVerticalStrut(10));
        add(botPanel);

        // Tải dữ liệu tài khoản lên bảng
        loadEmployees();

        addButton.addActionListener(e -> {

//                    JOptionPane.showMessageDialog(this, "Chức năng thêm nhân viên chưa được triển khai!");
            Form_Employee FE = new Form_Employee(this, null);
            FE.setVisible(true);
        });

        editButton.addActionListener(e -> {
            Form_Employee FE = new Form_Employee(this, employeeChoosing);
            FE.setVisible(true);
        });

        reloadButton.addActionListener(e -> {
            loadEmployees();
        });

        deleteButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có chắc chắn muốn xóa nhân viên này?",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (result == JOptionPane.YES_OPTION) {
                if (EmployeeBUS.deletedEmployee(employeeChoosing.getEmployeeID())) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công");
                    loadEmployees();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        searchField.setSearchListener(e -> {
//            String keyword = searchField.getText();
//            ArrayList<EmployeeDTO> ketQua = EmployeeDAO.searchEmployees(keyword);
//            capNhatBangTaiKhoan(ketQua); // Hiển thị kết quả tìm được trên bảng
        });

    }

    // Phương thức tải danh sách tài khoản từ database lên bảng
    public void loadEmployees() {
        ArrayList<EmployeeDTO> employees = EmployeeBUS.getAllEmployees(); // Lấy danh sách tài khoản
        tableModel.setRowCount(0); // Xóa dữ liệu cũ trước khi cập nhật
        int index = 1;
        for (EmployeeDTO emp : employees) {
            tableModel.addRow(new Object[]{index++, emp.getFullName(), emp.getAddress(), emp.getPhone(), emp.getGender()});
        }
        nameLabel.setText("");
        addressLabel.setText("");
        phoneLabel.setText("");
        ageLabel.setText("");
        genderLabel.setText("");
    }

}
