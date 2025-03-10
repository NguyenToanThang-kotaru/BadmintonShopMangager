package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProductManagement extends JFrame {
    private JTable productTable;
    private DefaultTableModel tableModel;
    private JTextField txtId, txtName, txtQuantity;

    public ProductManagement() {
        setTitle("Quản Lý Kho Hàng");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Bảng hiển thị sản phẩm
        tableModel = new DefaultTableModel(new String[]{"ID", "Tên Sản Phẩm", "Số Lượng"}, 0);
        productTable = new JTable(tableModel);
        add(new JScrollPane(productTable), BorderLayout.CENTER);

        // Form nhập liệu
        JPanel panelInput = new JPanel(new GridLayout(4, 2));
        panelInput.add(new JLabel("ID:"));
        txtId = new JTextField();
        panelInput.add(txtId);

        panelInput.add(new JLabel("Tên Sản Phẩm:"));
        txtName = new JTextField();
        panelInput.add(txtName);

        panelInput.add(new JLabel("Số Lượng:"));
        txtQuantity = new JTextField();
        panelInput.add(txtQuantity);

        JButton btnAdd = new JButton("Thêm");
        panelInput.add(btnAdd);

        JButton btnRemove = new JButton("Xóa");
        panelInput.add(btnRemove);

        add(panelInput, BorderLayout.SOUTH);

        setVisible(true);
    }

   public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                new ProductManagement();
            }
        });
        
    }
}
