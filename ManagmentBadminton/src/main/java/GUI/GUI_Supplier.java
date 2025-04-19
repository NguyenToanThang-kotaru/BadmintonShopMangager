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

import BUS.SupplierBUS;
import DTO.SupplierDTO;

public class GUI_Supplier extends JPanel {
    
    private JPanel topPanel, midPanel, botPanel;
    private JTable supplierTable;
    private DefaultTableModel tableModel;
    private CustomButton editButton, deleteButton, productListButton, reloadButton;
    private CustomButton addButton;
    private CustomSearch searchField;
    private SupplierBUS supplierBUS;
    private JLabel suppliernameLabel, supplieridLabel, addressLabel, phoneLabel, emailLabel;
    private JPanel buttonPanel;

    public GUI_Supplier() {
        supplierBUS = new SupplierBUS();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(new Color(200, 200, 200));
        
        topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setPreferredSize(new Dimension(0, 60));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(Color.WHITE);

        reloadButton = new CustomButton("Tải lại trang");
        topPanel.add(reloadButton, BorderLayout.WEST);
        reloadButton.addActionListener(e -> {
            loadSupplier();
        });

        searchField = new CustomSearch(275, 20);
        searchField.setBackground(Color.WHITE);
        searchField.setSearchListener(e -> searchSupplier());
        topPanel.add(searchField, BorderLayout.CENTER);

        midPanel = new JPanel(new BorderLayout());
        midPanel.setBackground(Color.WHITE);
        
        String[] columnNames = {"Mã NCC", "Tên NCC", "SĐT", "Email", "Địa chỉ"};
        CustomTable customTable = new CustomTable(columnNames);
        supplierTable = customTable.getSupplierTable();
        tableModel = customTable.getTableModel();
        
        midPanel.add(customTable, BorderLayout.CENTER);

        botPanel = new JPanel(new GridBagLayout());
        botPanel.setBackground(Color.WHITE);
        botPanel.setBorder(BorderFactory.createTitledBorder("Chi Tiết Nhà Cung Cấp"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        botPanel.add(new JLabel("Tên Nhà Cung Cấp: "), gbc);
        gbc.gridx = 1;
        suppliernameLabel = new JLabel("Chọn Nhà Cung Cấp");
        botPanel.add(suppliernameLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        botPanel.add(new JLabel("Mã Nhà Cung Cấp: "), gbc);
        gbc.gridx = 1;
        supplieridLabel = new JLabel("");
        botPanel.add(supplieridLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        botPanel.add(new JLabel("Số Điện Thoại: "), gbc);
        gbc.gridx = 1;
        phoneLabel = new JLabel("");
        botPanel.add(phoneLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        botPanel.add(new JLabel("Email: "), gbc);
        gbc.gridx = 1;
        emailLabel = new JLabel("");
        botPanel.add(emailLabel, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        botPanel.add(new JLabel("Địa Chỉ: "), gbc);
        gbc.gridx = 1;
        addressLabel = new JLabel("");
        botPanel.add(addressLabel, gbc);
        
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        buttonPanel.setOpaque(false);

        deleteButton = new CustomButton("Xóa");
        deleteButton.setCustomColor(new Color(220, 0, 0));
        buttonPanel.add(deleteButton);

        editButton = new CustomButton("Sửa");
        editButton.setCustomColor(new Color(0, 230, 0));
        buttonPanel.add(editButton);

        productListButton = new CustomButton("Danh sách SP");
        productListButton.setCustomColor(new Color(0, 0, 230));
        buttonPanel.add(productListButton);

        addButton = new CustomButton("+ Thêm Nhà Cung Cấp");
        topPanel.add(addButton, BorderLayout.EAST);

        addButton.addActionListener(e -> new Form_Supplier(this).setVisible(true));

        editButton.addActionListener(e -> {
            int selectedRow = supplierTable.getSelectedRow();
            if (selectedRow != -1) {
                String supplierID = (String) supplierTable.getValueAt(selectedRow, 0);
                String name = suppliernameLabel.getText();
                String address = addressLabel.getText();
                String phone = phoneLabel.getText();
                String email = emailLabel.getText();

                SupplierDTO supplier = new SupplierDTO(supplierID, name, phone, email, address, 0);
                GUI_Edit_Supplier editDialog = new GUI_Edit_Supplier(this, supplier);
                editDialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp để sửa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = supplierTable.getSelectedRow();
            if (selectedRow != -1) {
                String supplierID = (String) supplierTable.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa nhà cung cấp này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION && supplierBUS.remove(supplierID)) {
                    loadSupplier();
                    clearDetails();
                    JOptionPane.showMessageDialog(this, "Xóa nhà cung cấp thành công!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp để xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        productListButton.addActionListener(e -> {
            int selectedRow = supplierTable.getSelectedRow();
            if (selectedRow != -1) {
                String supplierID = (String) supplierTable.getValueAt(selectedRow, 0);
                String name = suppliernameLabel.getText();
                SupplierDTO supplier = new SupplierDTO(supplierID, name, phoneLabel.getText(), emailLabel.getText(), addressLabel.getText(), 0);
                new GUI_Detail_Supplier(this, supplier).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp để xem danh sách sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        supplierTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = supplierTable.getSelectedRow();
            if (selectedRow != -1) {
                String mancc = (String) supplierTable.getValueAt(selectedRow, 0);
                String hoten = (String) supplierTable.getValueAt(selectedRow, 1);
                String sdt = (String) supplierTable.getValueAt(selectedRow, 2);
                String email = (String) supplierTable.getValueAt(selectedRow, 3);
                String diachi = (String) supplierTable.getValueAt(selectedRow, 4);

                suppliernameLabel.setText(hoten);
                supplieridLabel.setText(mancc);
                phoneLabel.setText(sdt);
                emailLabel.setText(email);
                addressLabel.setText(diachi);

                botPanel.remove(buttonPanel);
                gbc.gridx = 0;
                gbc.gridy = 5;
                gbc.gridwidth = 2;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                botPanel.add(buttonPanel, gbc);

                botPanel.revalidate();
                botPanel.repaint();
            }
        });
        
        add(topPanel);
        add(Box.createVerticalStrut(10));
        add(midPanel);
        add(Box.createVerticalStrut(10));
        add(botPanel);

        loadSupplier();
    }

    public void loadSupplier() {
        List<SupplierDTO> supplier = supplierBUS.getAllSupplier();
        tableModel.setRowCount(0);
        for (SupplierDTO sps : supplier) {
            if (sps.getSupplierID() != null && !sps.getSupplierID().isEmpty()) {
                tableModel.addRow(new Object[]{sps.getSupplierID(), sps.getSupplierName(), sps.getPhone(), sps.getEmail(), sps.getAddress()});
            }
        }
    }

    private void searchSupplier() {
        String keyword = searchField.getText().trim().toLowerCase();
        List<SupplierDTO> supplierList = supplierBUS.getAllSupplier();
        tableModel.setRowCount(0);

        for (SupplierDTO supplier : supplierList) {
            if (supplier.getSupplierID().toLowerCase().contains(keyword) ||
                supplier.getSupplierName().toLowerCase().contains(keyword) ||
                supplier.getAddress().toLowerCase().contains(keyword) ||
                supplier.getPhone().toLowerCase().contains(keyword) ||
                supplier.getEmail().toLowerCase().contains(keyword)
                ) {
                tableModel.addRow(new Object[]{
                    supplier.getSupplierID(),
                    supplier.getSupplierName(),
                    supplier.getPhone(),
                    supplier.getEmail(),
                    supplier.getAddress()
                });
            }
        }
    }


    private void clearDetails() {
        suppliernameLabel.setText("Chọn Nhà Cung Cấp");
        supplieridLabel.setText("");
        addressLabel.setText("");
        phoneLabel.setText("");
        botPanel.remove(buttonPanel);
        botPanel.revalidate();
        botPanel.repaint();
    }
}