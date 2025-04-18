package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class ImportProductsPanel extends JPanel {
    private JTable productsTable;
    private DefaultTableModel importTableModel;
    private CustomButton btnDeleteProduct;

    public ImportProductsPanel() {
        setLayout(new BorderLayout());
        setBorder(new CompoundBorder(new TitledBorder("Danh sách sản phẩm nhập"), new EmptyBorder(5, 5, 5, 5)));
        setBackground(Color.WHITE);

        String[] columns = {"Mã SP", "Tên SP", "Số lượng", "Đơn giá", "Thành tiền", "Mã NCC", "Mã loại", "Hình ảnh"};
        importTableModel = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };

        productsTable = new JTable(importTableModel);
        productsTable.setRowHeight(30);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel columnModel = productsTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(100);
        columnModel.getColumn(1).setPreferredWidth(200);
        columnModel.getColumn(2).setPreferredWidth(80);
        columnModel.getColumn(3).setPreferredWidth(120);
        columnModel.getColumn(4).setPreferredWidth(150);
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setCellRenderer(centerRenderer);
        }
        // Ẩn cột "Mã NCC" và "Mã loại"
        TableColumnModel tcm = productsTable.getColumnModel();
        tcm.getColumn(5).setMinWidth(0);
        tcm.getColumn(5).setMaxWidth(0);
        tcm.getColumn(5).setPreferredWidth(0);

        tcm.getColumn(6).setMinWidth(0);
        tcm.getColumn(6).setMaxWidth(0);
        tcm.getColumn(6).setPreferredWidth(0);

        tcm.getColumn(7).setMinWidth(0);
        tcm.getColumn(7).setMaxWidth(0);
        tcm.getColumn(7).setPreferredWidth(0);

        JScrollPane scrollPane = new JScrollPane(productsTable);
        add(scrollPane, BorderLayout.CENTER);

        // Nút xóa sản phẩm
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(Color.WHITE);
        btnDeleteProduct = new CustomButton("Xóa sản phẩm");
        btnDeleteProduct.setCustomColor(new Color(220, 0, 0));
        bottomPanel.add(btnDeleteProduct);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public JTable getProductsTable() {
        return productsTable;
    }

    public DefaultTableModel getImportTableModel() {
        return importTableModel;
    }

    public JButton getBtnDeleteProduct() {
        return btnDeleteProduct;
    }

    public ArrayList<String> getAllProductID() {
        ArrayList<String> productIDs = new ArrayList<>();
        for (int i = 0; i < importTableModel.getRowCount(); i++) {
            Object value = importTableModel.getValueAt(i, 0); // Cột 0 là Mã SP
            if (value != null) {
                productIDs.add(value.toString());
            }
        }
        return productIDs;
    }
}