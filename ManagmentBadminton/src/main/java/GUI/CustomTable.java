// Create table design

package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class CustomTable extends JPanel {

    private JTable CustomTable;
    private DefaultTableModel tableModel;

    public CustomTable(String[] columnNames) {
        setLayout(new BorderLayout()); // Đặt layout của JPanel là BorderLayout
        setBackground(Color.WHITE); // Đặt màu nền cho JPanel

        // Tạo model cho bảng với số cột truyền vào
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2 || column == 3 || column == 4; // Chỉ cho phép chỉnh sửa một số cột
            }
        };

        // Tạo bảng JTable
        CustomTable = new JTable(tableModel);
        CustomTable.setIntercellSpacing(new Dimension(0, 0)); // Xóa khoảng cách giữa các ô
        CustomTable.setBackground(Color.WHITE); // Đặt màu nền bảng
        CustomTable.setRowHeight(30); // Đặt chiều cao dòng
        CustomTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN); // Tự động điều chỉnh độ rộng cột cuối cùng
        CustomTable.setFont(new Font("Arial", Font.PLAIN, 13)); // Font chữ
        CustomTable.setGridColor(new Color(200, 200, 200)); // Màu đường kẻ ô
        CustomTable.setShowGrid(true); // Hiển thị lưới
        CustomTable.setShowVerticalLines(false); // Ẩn đường kẻ dọc
        CustomTable.setDefaultEditor(Object.class, null); // Vô hiệu hóa chỉnh sửa toàn bộ bảng
        CustomTable.setFocusable(false); // Ngăn bảng nhận focus khi click vào
        // Cho phép chọn cả hàng thay vì chỉ chọn từng ô
        CustomTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        CustomTable.setRowSelectionAllowed(true);
        CustomTable.setColumnSelectionAllowed(false);

        // Căn giữa nội dung các cột (trừ cột có index 2 và 3)
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < CustomTable.getColumnCount(); i++) {

            CustomTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);

        }

        // Tùy chỉnh UI phần tiêu đề bảng
        JTableHeader tableHeader = CustomTable.getTableHeader();
        tableHeader.setFont(new Font("Arial", Font.BOLD, 14));
        tableHeader.setBackground(new Color(230, 230, 230));

        // Thêm bảng vào JScrollPane
        JScrollPane scrollPane = new JScrollPane(CustomTable);
        add(scrollPane, BorderLayout.CENTER);
        
    }

    // Thêm hàng dữ liệu vào bảng
    public void addRow(Object[] rowData) {
        tableModel.addRow(rowData);
    }

    // Xóa toàn bộ dữ liệu trong bảng
    public void clearTable() {
        tableModel.setRowCount(0);
    }

    public JTable getAccountTable() {
        return CustomTable;
    }
    
    public JTable getCustomerTable() {
        return CustomTable;
    }
    
    public JTable getOrderTable() {
        return CustomTable;
    }
    
    public JTable getEmployeeTable() {
        return CustomTable;
    }
    
    public JTable getSupplierTable() {

        return CustomTable;
    }
    
    public JTable getImportTable() {
        return CustomTable;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }
}
