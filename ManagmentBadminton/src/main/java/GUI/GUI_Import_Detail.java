package GUI;

import javax.swing.*;
import java.awt.*;

import BUS.ImportInvoiceBUS;
import BUS.ImportInvoiceDetailBUS;
import DTO.ImportInvoiceDTO;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class GUI_Import_Detail extends JDialog {
    private JTable productsTable;
    private JScrollPane scrollPane;
    private ImportInvoiceDetailBUS bus;
    private ImportInvoiceBUS importBUS = new ImportInvoiceBUS();


    public GUI_Import_Detail(JFrame parent, ImportInvoiceDTO importDTO) {
        super(parent, "Chi Tiết Phiếu Nhập", true);
        bus = new ImportInvoiceDetailBUS();
        setSize(700, 550);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Tạo panel chính với GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        mainPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        // Thêm tiêu đề
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel title = new JLabel("CHI TIẾT PHIẾU NHẬP");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(new Color(52, 73, 94));
        mainPanel.add(title, gbc);

        // Thêm thông tin cơ bản
        gbc.gridwidth = 1;
        gbc.gridy++;
        addDetailRow(mainPanel, gbc, "Mã phiếu nhập:", importDTO.getImportID());
        gbc.gridy++;
        addDetailRow(mainPanel, gbc, "Ngày nhập:", importDTO.getDate());
        gbc.gridy++;
        addDetailRow(mainPanel, gbc, "Nhân viên nhập:", importBUS.getEmployeeNameByImportID(importDTO.getImportID()));

        // Tạo bảng sản phẩm
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        String[] columnNames = {"Mã SP", "Tên SP", "Số lượng", "Đơn giá", "Thành tiền"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        productsTable = new JTable(model);
        productsTable.setRowHeight(25);

        // Căn giữa nội dung các cột
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < productsTable.getColumnCount(); i++) {
            productsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        scrollPane = new JScrollPane(productsTable);
        scrollPane.setPreferredSize(new Dimension(600, 200));
        mainPanel.add(scrollPane, gbc);

        // Tải chi tiết phiếu nhập và tính tổng tiền
        loadImportDetails(importDTO.getImportID(), model);

        // Hiển thị tổng tiền (chỉ thêm một lần)
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;
        addDetailRow(mainPanel, gbc, "Tổng tiền:", Utils.formatCurrency(importDTO.getTotalPrice()));

        // Thêm nút Đóng
        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        CustomButton btnClose = new CustomButton("Đóng");
        btnClose.setPreferredSize(new Dimension(100, 30));
        btnClose.addActionListener(e -> dispose());
        mainPanel.add(btnClose, gbc);

        add(mainPanel, BorderLayout.CENTER);
    }

    // Thêm một hàng thông tin (label-value) vào panel
    private void addDetailRow(JPanel panel, GridBagConstraints gbc, String label, String value) {
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;

        JLabel labelLbl = new JLabel(label);
        labelLbl.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(labelLbl, gbc);

        gbc.gridx = 1;
        JLabel valueLbl = new JLabel(value);
        valueLbl.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(valueLbl, gbc);
    }

    // Tải chi tiết phiếu nhập 
    private void loadImportDetails(String importID, DefaultTableModel model) {
        ArrayList<Object[]> details = bus.loadImportDetails(importID);
        model.setRowCount(0);  // Xóa dữ liệu cũ
    
        if (details.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm nào cho phiếu nhập " + importID,
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
        } else {
            // Thêm dữ liệu mới vào model
            for (Object[] row : details) {
                model.addRow(row);
            }
    
            // Cập nhật lại giao diện
            SwingUtilities.invokeLater(() -> {
                model.fireTableDataChanged();
                productsTable.revalidate();
                productsTable.repaint();
                scrollPane.revalidate();
                scrollPane.repaint();
            });
        }
    }
    
}