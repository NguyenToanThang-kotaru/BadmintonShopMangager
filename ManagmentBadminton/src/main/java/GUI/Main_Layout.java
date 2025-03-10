package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Main_Layout extends JFrame {
    private JTable productTable;
    private DefaultTableModel tableModel;
    private JTextField txtId, txtName, txtQuantity;

    public Main_Layout() {
        setTitle("Quản Lý Kho Hàng");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        JButton thongke,sanpham,donhang,nhacungcap,khachhang,taikhoan;
        
        
        
        
        
        
        // ====== Thanh Chức Năng (Bắc - NORTH) ======
//        JPanel functionPanel = new JPanel();
//        functionPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
//        functionPanel.add(new JButton("Thêm"));
//        functionPanel.add(new JButton("Xóa"));
//        functionPanel.add(new JButton("Sửa"));
//        functionPanel.add(new JButton("Tìm Kiếm"));
//        add(functionPanel, BorderLayout.NORTH);

        // ====== Sidebar (Tây - WEST) ======
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new FlowLayout());
        sidebarPanel.add(new JButton("Trang Chủ"));
        sidebarPanel.add(new JButton("Sản Phẩm"));
        sidebarPanel.add(new JButton("Đơn Hàng"));
        sidebarPanel.add(new JButton("Khách Hàng"));
        sidebarPanel.add(new JButton("Báo Cáo"));
        add(sidebarPanel, BorderLayout.WEST);

        // ====== Bảng Sản Phẩm (Trung tâm - CENTER) ======
//        tableModel = new DefaultTableModel(new String[]{"ID", "Tên Sản Phẩm", "Số Lượng"}, 0);
//        productTable = new JTable(tableModel);  
//        add(new JScrollPane(productTable), BorderLayout.CENTER);
//        productTable.setFillsViewportHeight(true);
//        productTable.setBackground(Color.LIGHT_GRAY);
//        productTable.getTableHeader().setForeground(Color.BLUE);
//        productTable.getTableHeader().setBackground(Color.YELLOW);

        // ====== Form nhập dữ liệu (Nam - SOUTH) ======
//        JPanel inputPanel = new JPanel(new GridLayout(2, 4, 5, 5));
//        inputPanel.add(new JLabel("ID:"));
//        txtId = new JTextField();
//        inputPanel.add(txtId);
//
//        inputPanel.add(new JLabel("Tên Sản Phẩm:"));
//        txtName = new JTextField();
//        inputPanel.add(txtName);
//
//        inputPanel.add(new JLabel("Số Lượng:"));
//        txtQuantity = new JTextField();
//        inputPanel.add(txtQuantity);
//
//        inputPanel.add(new JButton("Lưu"));
//        inputPanel.add(new JButton("Hủy"));
//        add(inputPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
            Main_Layout a = new Main_Layout();
            a.setVisible(true);
    }
}