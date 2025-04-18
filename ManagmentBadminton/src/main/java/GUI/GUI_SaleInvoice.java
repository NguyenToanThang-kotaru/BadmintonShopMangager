package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

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
import BUS.SaleInvoiceBUS;
import DTO.AccountDTO;
import DTO.CustomerDTO;
import DTO.EmployeeDTO;

public class GUI_SaleInvoice extends JPanel {
    private JPanel topPanel, midPanel, botPanel;
    private JTable orderTable;
    private DefaultTableModel tableModel;
    private CustomButton addButton, detailorderButton;
    private CustomSearch searchField;
    private SaleInvoiceBUS saleInvoiceBUS;
    JLabel[] array_label = new JLabel[5];

    public GUI_SaleInvoice(AccountDTO cn, List<String> t) {
        saleInvoiceBUS = new SaleInvoiceBUS();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(new Color(200, 200, 200));
        
        // ========== PANEL TRÊN CÙNG (Thanh tìm kiếm & nút thêm) ==========
        topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setPreferredSize(new Dimension(0, 60));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(Color.WHITE);

        searchField = new CustomSearch(275,20); // Ô nhập tìm kiếm
        searchField.setBackground(Color.WHITE);
        topPanel.add(searchField, BorderLayout.CENTER);

        addButton = new CustomButton("+ Thêm Hóa Đơn"); // Nút thêm hóa đơn
        topPanel.add(addButton, BorderLayout.EAST);

        // ========== BẢNG HIỂN THỊ DANH SÁCH HÓA ĐƠN ==========
        midPanel = new JPanel(new BorderLayout());
        midPanel.setBackground(Color.WHITE);
        
        // Định nghĩa tiêu đề cột
        String[] columnNames = {"Mã HĐ", "Mã NV", "Mã KH", "Tổng Tiền", "Ngày Xuất"};
        CustomTable customTable = new CustomTable(columnNames);
        orderTable = customTable.getOrderTable(); 
        tableModel = customTable.getTableModel(); 

        CustomScrollPane scrollPane = new CustomScrollPane(orderTable);
        midPanel.add(scrollPane, BorderLayout.CENTER);
        
        // ========== PANEL CHI TIẾT HÓA ĐƠN ==========
        botPanel = new JPanel(new GridBagLayout());
        botPanel.setBackground(Color.WHITE);
        botPanel.setBorder(BorderFactory.createTitledBorder("Hóa Đơn"));

        String txt_label[] = {"Mã Hóa Đơn: ", "Mã Nhân Viên: ", "Mã Khách Hàng: ", "Tổng Tiền: ", "Ngày Xuất: "};
        String txt_value[] = {"Chọn Hóa Đơn", "", "", "", ""};
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        for (int i = 0; i < txt_label.length; i++) {
            // Nhãn hiển thị thông tin hóa đơn
            gbc.gridx = 0;
            gbc.gridy = i;
            botPanel.add(new JLabel(txt_label[i]), gbc);
            gbc.gridx = 1;
            array_label[i] = new JLabel(txt_value[i]);
            botPanel.add(array_label[i], gbc);
        }

        // ========== PANEL BUTTON ==========
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        buttonPanel.setOpaque(false);
        
        detailorderButton = new CustomButton("Chi Tiết");
        detailorderButton.setCustomColor(new Color(0, 120, 215));
        buttonPanel.add(detailorderButton, BorderLayout.EAST);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        orderTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = orderTable.getSelectedRow();
            if (selectedRow != -1) {             
                // Hiển thị dữ liệu trên giao diện
                for (int i = 0; i < txt_label.length; i++) {
                    array_label[i].setText((String) orderTable.getValueAt(selectedRow, i));
                }
                botPanel.add(buttonPanel, gbc);
            }   
        });
        
        addButton.addActionListener(e -> {
//            JOptionPane.showMessageDialog(this, "Chức năng thêm nhân viên chưa được triển khai!");
            GUI_Form_Order GFO = new GUI_Form_Order(this, null, cn);
            GFO.setVisible(true);
        });
        
        // Thêm các panel vào giao diện chính
        add(topPanel);
        add(Box.createVerticalStrut(10));
        add(midPanel);
        add(Box.createVerticalStrut(10));
        add(botPanel);

        detailorderButton.addActionListener(e -> {
            int selectedRow = orderTable.getSelectedRow();
            if (selectedRow != -1) {
                // take the employee's id and customer's id, take the name of the employee and customer
                String employeeID = (String) orderTable.getValueAt(selectedRow, 1);
                String customerID = (String) orderTable.getValueAt(selectedRow, 2);

                EmployeeBUS empBUS = new EmployeeBUS();
                CustomerBUS cusBUS = new CustomerBUS();
                EmployeeDTO emp = empBUS.getEmployeeByID(employeeID);
                CustomerDTO cus = cusBUS.getById(customerID);

                String orderID = (String) orderTable.getValueAt(selectedRow, 0);
                String employeeName = emp.getFullName();
                String customerName = cus.getName();
                String totalMoney = (String) orderTable.getValueAt(selectedRow, 3);
                String issueDate = (String) orderTable.getValueAt(selectedRow, 4);

                GUI_DetailSaleInvoice GDO = new GUI_DetailSaleInvoice(orderID, employeeName, customerName, totalMoney, issueDate);
                GDO.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn trước!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });
    }
    public void loadOrder() {
        tableModel.setRowCount(0); // Xóa dữ liệu cũ trong bảng
        List<SaleInvoiceDTO> orderList = saleInvoiceBUS.getAll();
        for (SaleInvoiceDTO order : orderList) {
            String formattedDate = new java.text.SimpleDateFormat("dd-MM-yyyy").format(order.getDate());
            String[] rowData = {order.getId(), order.getEmployeeId(), order.getCustomerId(), String.valueOf(order.getTotalPrice()), formattedDate};
            tableModel.addRow(rowData);
        }
    }
}
