package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import BUS.ImportInvoiceBUS;
import BUS.PermissionBUS;
import BUS.SupplierBUS;
import DTO.ImportInvoiceDTO;
import DTO.SupplierDTO;
import DTO.AccountDTO;
import DTO.ActionDTO;

public class GUI_Import extends JPanel {

    private final ImportInvoiceBUS importBUS;
    private final DefaultTableModel tableModel;
    private final JTable importTable;
    private final CustomSearch searchField;
    private final JLabel importIdLabel;
    private final JLabel employeeIdLabel;
    private final JLabel totalMoneyLabel;
    private final JLabel receiptDateLabel;
    private final CustomButton reloadButton;
    private ImportInvoiceDTO selectedImport;

    public GUI_Import(AccountDTO username) {
        this.importBUS = new ImportInvoiceBUS();
        String currentUsername = username.getUsername();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(new Color(200, 200, 200));

        // Panel trên cùng: Thanh tìm kiếm và nút Thêm
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setPreferredSize(new Dimension(0, 60));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(Color.WHITE);

        reloadButton = new CustomButton("Tải lại trang");
        topPanel.add(reloadButton, BorderLayout.WEST);
        reloadButton.addActionListener(e -> {
            loadImport();
        });

        searchField = new CustomSearch(275, 20);
        searchField.setBackground(Color.WHITE);
        topPanel.add(searchField, BorderLayout.CENTER);

        CustomButton addButton = new CustomButton("+ Thêm Phiếu Nhập");
        addButton.addActionListener(e -> {
            GUI_Form_Import form = new GUI_Form_Import(this, currentUsername);
            form.setVisible(true);
        });
        topPanel.add(addButton, BorderLayout.EAST);

        // Bảng hiển thị danh sách phiếu nhập
        String[] columnNames = {"Mã HDN", "Mã NV", "Tổng Tiền", "Ngày Nhập"};
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
        botPanel.add(new JLabel("Tổng Tiền: "), gbc);
        gbc.gridx = 1;
        totalMoneyLabel = new JLabel("");
        botPanel.add(totalMoneyLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        botPanel.add(new JLabel("Ngày Nhập: "), gbc);
        gbc.gridx = 1;
        receiptDateLabel = new JLabel("");
        botPanel.add(receiptDateLabel, gbc);

        // Panel chứa các nút Nhập xuất excel, in hóa đơn và Xem Chi Tiết
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        buttonPanel.setOpaque(false);

        CustomButton detailButton = new CustomButton("Xem Chi Tiết");
        detailButton.setCustomColor(new Color(0, 120, 215));
        detailButton.addActionListener(e -> showImportDetail());
        buttonPanel.add(detailButton);

        CustomButton printPdfButton = new CustomButton("In PDF");
        printPdfButton.setCustomColor(new Color(192, 0, 0)); // Đỏ đậm - màu thường thấy với PDF
        printPdfButton.addActionListener(e -> printInvoiceAsPDF());
        buttonPanel.add(printPdfButton);

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
                String receiptDate = (String) importTable.getValueAt(selectedRow, 3);

                double calculatedTotal = importBUS.calculateImportTotal(importID);
                selectedImport = new ImportInvoiceDTO(importID, employeeID, receiptDate, calculatedTotal);

                importIdLabel.setText(importID);
                employeeIdLabel.setText(employeeID);
                totalMoneyLabel.setText(Utils.formatCurrency(calculatedTotal));
                receiptDateLabel.setText(receiptDate);
            }
        });

        searchField.setSearchListener(e -> {
            String keyword = searchField.getText();
            ArrayList<ImportInvoiceDTO> ketQua = ImportInvoiceBUS.searchImportInvoice(keyword);
            capNhatBangImport(ketQua); // Hiển thị kết quả tìm được trên bảng
        });

        ArrayList<ActionDTO> actions = PermissionBUS.getPermissionActions(username, "Quan ly hoa dong nhap");

        boolean canAdd = false, canEdit = false, canDelete = false, canWatch = false;

        if (actions != null) {
            for (ActionDTO action : actions) {
                switch (action.getName()) {
                    case "Add" ->
                        canAdd = true;
                    case "Edit" ->
                        canEdit = true;
                    case "Delete" ->
                        canDelete = true;
                    case "Watch" ->
                        canWatch = true;
                }
            }
        }

        addButton.setVisible(canAdd);
//        editButton.setVisible(canEdit);
//        deleteButton.setVisible(canDelete);
//        scrollPane.setVisible(canWatch);
        reloadButton.setVisible(false);
    }

    // Tải danh sách phiếu nhập vào bảng
    public void loadImport() {
        ArrayList<ImportInvoiceDTO> importList = ImportInvoiceBUS.getAllImportInvoice();
        tableModel.setRowCount(0);
        for (ImportInvoiceDTO importDTO : importList) {
            tableModel.addRow(new Object[]{
                importDTO.getImportID(),
                importDTO.getEmployeeID(),
                Utils.formatCurrency(importDTO.getTotalPrice()),
                importDTO.getDate()
            });
        }
    }

    private void capNhatBangImport(ArrayList<ImportInvoiceDTO> imports) {
        tableModel.setRowCount(0); // Xóa dữ liệu cũ
        int index = 1;
        for (ImportInvoiceDTO importDTO : imports) {
            tableModel.addRow(new Object[]{importDTO.getImportID(), importDTO.getEmployeeID(), Utils.formatCurrency(importDTO.getTotalPrice()), importDTO.getDate()});
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

    private void printInvoiceAsPDF() {
        if (selectedImport == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu nhập để in PDF!");
            return;
        }

        new CustomImportInvoicePDF().export(
                selectedImport // Tổng tiền
        );
    }
}
