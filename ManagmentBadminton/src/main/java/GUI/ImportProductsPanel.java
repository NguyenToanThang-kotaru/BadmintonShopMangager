package GUI;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.*;
import java.awt.*;

public class ImportProductsPanel extends JPanel {
    private JTable productsTable;
    private DefaultTableModel importTableModel;

    public ImportProductsPanel() {
        setLayout(new BorderLayout());
        setBorder(new CompoundBorder(new TitledBorder("Danh sách sản phẩm nhập"), new EmptyBorder(5, 5, 5, 5)));
        setBackground(Color.WHITE);

        String[] columns = {"Mã SP", "Tên SP", "Số lượng", "Đơn giá", "Thành tiền"};
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

        JScrollPane scrollPane = new JScrollPane(productsTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    public JTable getProductsTable() {
        return productsTable;
    }

    public DefaultTableModel getImportTableModel() {
        return importTableModel;
    }
}