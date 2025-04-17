package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import BUS.CustomerBUS;
import BUS.EmployeeBUS;
import DAO.CustomerDAO;
import DTO.CustomerDTO;

public class GUI_Customer extends JPanel {
        // Khai báo các thành phần giao diện
    private JPanel midPanel, topPanel, botPanel;
    private JTable customerTable;
    private DefaultTableModel tableModel;
//    private JComboBox<String> roleComboBox;
    private CustomButton deleteButton, addButton, editButton, reloadButton;
    private CustomSearch searchField;
    private CustomerBUS customerBUS;
    private CustomerDTO customerChoosing;
    private CustomerDAO customerDAO;

    public GUI_Customer() {
        customerBUS = new CustomerBUS(); // Khởi tạo đối tượng BUS để lấy dữ liệu tài khoản
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
        String[] columnNames = {"STT", "Khách hàng", "Số điện thoại", "Tổng chi tiêu"};
        CustomTable customTable = new CustomTable(columnNames);
        customerTable = customTable.getAccountTable(); // Lấy JTable từ CustomTable
        tableModel = customTable.getTableModel(); // Lấy model của bảng

        midPanel.add(customTable, BorderLayout.CENTER);
        CustomScrollPane scrollPane = new CustomScrollPane(customerTable);
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
        botPanel.add(new JLabel("Tên khách hàng: "), gbc);
        gbc.gridx = 1;
        JLabel nameLabel = new JLabel("Chọn nhân viên");
        botPanel.add(nameLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        botPanel.add(new JLabel("Số điện thoại: "), gbc);
        gbc.gridx = 1;
        JLabel phoneLabel = new JLabel("");
        botPanel.add(phoneLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        botPanel.add(new JLabel("Tổng chi tiêu: "), gbc);
        gbc.gridx = 1;
        JLabel spendingLabel = new JLabel("");
        botPanel.add(spendingLabel, gbc);

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
        customerTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = customerTable.getSelectedRow();
            if (selectedRow != -1) {
                // Lấy dữ liệu từ bảng và chuyển đổi sang String một cách an toàn

                String id = String.valueOf(customerTable.getValueAt(selectedRow, 0));
                if (id.length() == 1) {
                    id = "C0" + id;
                }
                else {
                    id = "C" + id;
                }
                String ten = (String) customerTable.getValueAt(selectedRow, 1);
                String sdt = (String) customerTable.getValueAt(selectedRow, 2);
                String tongchi = String.valueOf(customerTable.getValueAt(selectedRow, 3));
//                PermissionDTO temp = PermissionDAO.getPermissionByName(quyen);
                customerChoosing = customerBUS.getById(id);
                // Hiển thị dữ liệu trên giao diện
                nameLabel.setText(ten);
                phoneLabel.setText(sdt);
                spendingLabel.setText(tongchi);
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

        
            // JOptionPane.showMessageDialog(this, "Chức năng thêm nhân viên chưa được triển khai!");
           GUI_Form_Customer GFC = new GUI_Form_Customer(this, null);
           GFC.setVisible(true);
        });

        editButton.addActionListener(e -> {
            if (customerChoosing == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để sửa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            GUI_Form_Customer GFC = new GUI_Form_Customer(this, customerChoosing);
            GFC.setVisible(true);
        });

        reloadButton.addActionListener(e -> {
            loadEmployees();
        });

        deleteButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc chắn muốn xóa khách hàng này?",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (result == JOptionPane.YES_OPTION) {
                if (customerBUS.delete(customerChoosing.getId())) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công");
                    loadEmployees();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        searchField.setSearchListener(e -> {
//            String keyword = searchField.getText();
//            ArrayList<CustomerDTO> ketQua = customerDAO.searchEmployees(keyword);
//            capNhatBangTaiKhoan(ketQua); // Hiển thị kết quả tìm được trên bảng
        });

    }

    // Phương thức tải danh sách tài khoản từ database lên bảng
    private void loadEmployees() {
        ArrayList<CustomerDTO> customers = customerBUS.getAll(); // Lấy danh sách tài khoản
        tableModel.setRowCount(0); // Xóa dữ liệu cũ trước khi cập nhật
        int index = 1;
        for (CustomerDTO cus : customers) {
            tableModel.addRow(new Object[]{index++, cus.getName(), cus.getPhone(), cus.getSpending()});
        }
    }
}
