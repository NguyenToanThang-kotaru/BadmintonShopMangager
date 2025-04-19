package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class AllProductsPanel extends JPanel {
    private JTable allProductsTable;
    private DefaultTableModel productTableModel;
    private JButton btnNewProduct;

    public AllProductsPanel() {
        setLayout(new BorderLayout());
        setBorder(new CompoundBorder(new TitledBorder("Danh sách sản phẩm"), new EmptyBorder(5, 5, 5, 5)));
        setBackground(Color.WHITE);

        // Tạo bảng
        String[] columns = {"Mã SP", "Tên SP", "Giá nhập", "Kho"};
        productTableModel = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };

        allProductsTable = new JTable(productTableModel);
        allProductsTable.setRowHeight(30);
        allProductsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Căn giữa cột
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel columnModel = allProductsTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(100);
        columnModel.getColumn(1).setPreferredWidth(150);
        columnModel.getColumn(2).setPreferredWidth(150);
        columnModel.getColumn(3).setPreferredWidth(50);
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(allProductsTable);
        add(scrollPane, BorderLayout.CENTER);

        // Panel chứa nút
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(Color.WHITE);
        btnNewProduct = new CustomButton("Sản phẩm mới");
        bottomPanel.add(btnNewProduct);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public JTable getAllProductsTable() {
        return allProductsTable;
    }

    public DefaultTableModel getProductTableModel() {
        return productTableModel;
    }

    public JButton getBtnNewProduct() {
        return btnNewProduct;
    }
}