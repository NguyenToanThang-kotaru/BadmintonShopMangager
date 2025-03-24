package GUI;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class CustomTable extends JPanel {
    private JTable accountTable;
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
        accountTable = new JTable(tableModel);
        accountTable.setIntercellSpacing(new Dimension(0, 0)); // Xóa khoảng cách giữa các ô
        accountTable.setBackground(Color.WHITE); // Đặt màu nền bảng
        accountTable.setRowHeight(30); // Đặt chiều cao dòng
        accountTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN); // Tự động điều chỉnh độ rộng cột cuối cùng
        accountTable.setFont(new Font("Arial", Font.PLAIN, 13)); // Font chữ
        accountTable.setGridColor(new Color(200, 200, 200)); // Màu đường kẻ ô
        accountTable.setShowGrid(true); // Hiển thị lưới
        accountTable.setShowVerticalLines(false); // Ẩn đường kẻ dọc
        accountTable.setDefaultEditor(Object.class, null); // Vô hiệu hóa chỉnh sửa toàn bộ bảng
        accountTable.setFocusable(false); // Ngăn bảng nhận focus khi click vào

        // Căn giữa nội dung các cột (trừ cột có index 2 và 3)
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < accountTable.getColumnCount(); i++) {
            if (i != 2 && i != 3) {
                accountTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }

        // Tùy chỉnh UI phần tiêu đề bảng
        JTableHeader tableHeader = accountTable.getTableHeader();
        tableHeader.setFont(new Font("Arial", Font.BOLD, 14));
        tableHeader.setBackground(new Color(230, 230, 230));

        // Thêm bảng vào JScrollPane
        JScrollPane scrollPane = new JScrollPane(accountTable);
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
        return accountTable;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }
//
//    // Test giao diện bảng với số cột truyền vào
//    public static void main(String[] args) {
//        JFrame frame = new JFrame("Bảng Tài Khoản");
//
//        // Khai báo các cột tùy ý
//        String[] customColumns = {"STT", "Họ và Tên", "Email", "Tài khoản", "Mật khẩu", "Quyền", "Ghi chú"};
//
//        // Tạo panel với số cột tùy chỉnh
//        CustomTable panel = new CustomTable(customColumns);
//
//        // Thêm dữ liệu mẫu
//        panel.addRow(new Object[]{"1", "Nguyễn Văn A", "a@email.com", "userA", "123456", "Admin", "Ghi chú 1"});
//        panel.addRow(new Object[]{"2", "Trần Thị B", "b@email.com", "userB", "654321", "User", "Ghi chú 2"});
//
//        frame.add(panel);
//        frame.setSize(800, 400);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//    }
}
