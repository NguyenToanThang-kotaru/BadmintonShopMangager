package GUI;

import BUS.ImportInvoiceBUS;
import DTO.ImportInvoiceDTO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class GUI_Import extends JPanel {
    private final ImportInvoiceBUS importBUS;
    private final DefaultTableModel tableModel;
    private final JTable importTable;
    private final CustomSearch searchField;
    private final JLabel importIdLabel;
    private final JLabel employeeIdLabel;
    private final JLabel supplierIdLabel;
    private final JLabel totalMoneyLabel;
    private final JLabel receiptDateLabel;
    private ImportInvoiceDTO selectedImport;

    public GUI_Import() {
        this.importBUS = new ImportInvoiceBUS();
        // String currentUsername = GUI_Login.getCurrentUsername();
        String currentUsername = "admin";

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(new Color(200, 200, 200));

        // Panel trên cùng: Thanh tìm kiếm và nút Thêm
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setPreferredSize(new Dimension(0, 60));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(Color.WHITE);

        searchField = new CustomSearch(275, 20);
        searchField.setBackground(Color.WHITE);
        searchField.setSearchListener(e -> searchImport());
        topPanel.add(searchField, BorderLayout.CENTER);

        CustomButton addButton = new CustomButton("+ Thêm Phiếu Nhập");
        addButton.addActionListener(e -> {
            Form_Import form = new Form_Import(this, currentUsername);
            form.setVisible(true);
        });
        topPanel.add(addButton, BorderLayout.EAST);

        // Bảng hiển thị danh sách phiếu nhập
        String[] columnNames = {"Mã PN", "Mã NV", "Mã NCC", "Tổng Tiền", "Ngày Nhập"};
        CustomTable customTable = new CustomTable(columnNames);
        importTable = customTable.getImportTable();
        tableModel = customTable.getTableModel();

        JPanel midPanel = new JPanel(new BorderLayout());
        midPanel.setBackground(Color.WHITE);
        midPanel.add(new CustomScrollPane(importTable), BorderLayout.CENTER);

        // Panel chi tiết phiếu nhập
        JPanel botPanel = new JPanel(new GridBagLayout());
        botPanel.setBackground(Color.WHITE);
        botPanel.setBorder(BorderFactory.createTitledBorder("Hóa Đơn Nhập"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        botPanel.add(new JLabel("Mã Phiếu Nhập: "), gbc);
        gbc.gridx = 1;
        importIdLabel = new JLabel("Chọn Hóa Đơn Nhập");
        botPanel.add(importIdLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        botPanel.add(new JLabel("Mã Nhân Viên: "), gbc);
        gbc.gridx = 1;
        employeeIdLabel = new JLabel("");
        botPanel.add(employeeIdLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        botPanel.add(new JLabel("Mã Nhà Cung Cấp: "), gbc);
        gbc.gridx = 1;
        supplierIdLabel = new JLabel("");
        botPanel.add(supplierIdLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        botPanel.add(new JLabel("Tổng Tiền: "), gbc);
        gbc.gridx = 1;
        totalMoneyLabel = new JLabel("");
        botPanel.add(totalMoneyLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        botPanel.add(new JLabel("Ngày Nhập: "), gbc);
        gbc.gridx = 1;
        receiptDateLabel = new JLabel("");
        botPanel.add(receiptDateLabel, gbc);

        // Panel chứa các nút Xóa và Xem Chi Tiết
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        buttonPanel.setOpaque(false);

        CustomButton detailButton = new CustomButton("Xem Chi Tiết Hóa Đơn Nhập");
        detailButton.setCustomColor(new Color(0, 120, 215));
        detailButton.addActionListener(e -> showImportDetail());
        buttonPanel.add(detailButton);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        botPanel.add(buttonPanel, gbc);

        // Thêm các panel vào giao diện chính
        add(topPanel);
        add(Box.createVerticalStrut(10));
        add(midPanel);
        add(Box.createVerticalStrut(10));
        add(botPanel);

        // Tải danh sách phiếu nhập
        loadImport();

        // Thêm sự kiện chọn hàng trong bảng
        importTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = importTable.getSelectedRow();
            if (selectedRow != -1) {
                String importID = (String) importTable.getValueAt(selectedRow, 0);
                String employeeID = (String) importTable.getValueAt(selectedRow, 1);
                String supplierID = (String) importTable.getValueAt(selectedRow, 2);
                String receiptDate = (String) importTable.getValueAt(selectedRow, 4);

                double calculatedTotal = importBUS.calculateImportTotal(importID);
                selectedImport = new ImportInvoiceDTO(importID, employeeID, supplierID, receiptDate, calculatedTotal);

                importIdLabel.setText(importID);
                employeeIdLabel.setText(employeeID);
                supplierIdLabel.setText(supplierID);
                totalMoneyLabel.setText(Utils.formatCurrency(calculatedTotal));
                receiptDateLabel.setText(receiptDate);
            }
        });
    }

    // Tải danh sách phiếu nhập vào bảng
    public void loadImport() { 
        ArrayList<ImportInvoiceDTO> importList = ImportInvoiceBUS.getAllImportInvoice();
        tableModel.setRowCount(0);
        for (ImportInvoiceDTO importDTO : importList) {
            double calculatedTotal = importBUS.calculateImportTotal(importDTO.getImportID());
            tableModel.addRow(new Object[]{
                    importDTO.getImportID(),
                    importDTO.getEmployeeID(),
                    importDTO.getSupplierID(),
                    Utils.formatCurrency(calculatedTotal),
                    importDTO.getDate()
            });
        }
    }

    // Tìm kiếm phiếu nhập theo từ khóa
    private void searchImport() {
        String keyword = searchField.getText().trim().toLowerCase();
        ArrayList<ImportInvoiceDTO> importList = ImportInvoiceBUS.getAllImportInvoice();
        tableModel.setRowCount(0);

        for (ImportInvoiceDTO importDTO : importList) {
            if (importDTO.getImportID().toLowerCase().contains(keyword) ||
                importDTO.getEmployeeID().toLowerCase().contains(keyword) ||
                importDTO.getSupplierID().toLowerCase().contains(keyword)) {
                double calculatedTotal = importBUS.calculateImportTotal(importDTO.getImportID());
                tableModel.addRow(new Object[]{
                        importDTO.getImportID(),
                        importDTO.getEmployeeID(),
                        importDTO.getSupplierID(),
                        Utils.formatCurrency(calculatedTotal),
                        importDTO.getDate()
                });
            }
        }
    }


    // Hiển thị chi tiết phiếu nhập
    private void showImportDetail() {
        if (selectedImport == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu nhập để xem chi tiết!");
            return;
        }

        GUI_Import_Detail detailDialog = new GUI_Import_Detail(
                (JFrame) SwingUtilities.getWindowAncestor(this),
                selectedImport
        );
        detailDialog.setVisible(true);
    }
}