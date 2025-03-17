package GUI;

import DAO.DatabaseProductConnection;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class ProductManagement extends JFrame {

    private JTable productTable;
    private DefaultTableModel tableModel;

    public ProductManagement() {
        setTitle("Quản Lý Kho Hàng");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(40, 240, 240));

        //Thanh chuc nang

        JPanel functionPanel = new JPanel();
        functionPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        functionPanel.setBackground(new Color(224, 255, 255));

        // Danh sách chức năng
        String[] functionNames = {"Thêm", "Xóa", "Sửa","Nhập excel","Xuất excel"};
        String iconPath = "src/main/resources/images/";

        for (String function : functionNames) {
            String iconFile = null;
            switch (function) {
                case "Thêm":
                    iconFile = "add.png";
                    break;
                case "Xóa":
                    iconFile = "delete.png";
                    break;
                case "Sửa":
                    iconFile = "edit.png";
                    break;
                   case "Nhập excel":
                    iconFile = "importexcel.png";
                    break;
                case "Xuất excel":
                    iconFile = "exportexcel.png";
                    break;
            }

            // Tạo JLabel chứa icon
            JLabel label = new JLabel("", SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 12));
            label.setForeground(Color.BLACK);
            label.setCursor(new Cursor(Cursor.HAND_CURSOR));
            label.setPreferredSize(new Dimension(60, 50)); // Giữ kích thước cố định

            // Kiểm tra và gán icon nếu tồn tại
            if (iconFile != null) {
                String fullPath = iconPath + iconFile;
                if (new File(fullPath).exists()) {
                    ImageIcon originalIcon = new ImageIcon(fullPath);
                    ImageIcon smallIcon = new ImageIcon(originalIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
                    ImageIcon largeIcon = new ImageIcon(originalIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));

                    label.setIcon(smallIcon);

                    // Hiệu ứng hover phóng toooooooooooooooooo
                    label.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            label.setIcon(largeIcon); 
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            label.setIcon(smallIcon); 
                        }
                    });
                } else {
                    System.err.println("Không tìm thấy icon: " + fullPath);
                }
            }

            JPanel buttonPanel = new JPanel(new BorderLayout());
            buttonPanel.setOpaque(false);
            buttonPanel.add(label, BorderLayout.CENTER);

   
            if (!function.equals("Tìm Kiếm")) {
                JLabel textLabel = new JLabel(function, SwingConstants.CENTER);
                textLabel.setFont(new Font("Arial", Font.BOLD, 12));
                textLabel.setForeground(Color.BLACK);
                buttonPanel.add(textLabel, BorderLayout.SOUTH);
            }

            functionPanel.add(buttonPanel);
        }

        // thanh tìm kiếm
        JLabel searchLabel = new JLabel("Tìm kiếm:");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 12));

        JComboBox<String> searchOptions = new JComboBox<>(new String[]{"Tất cả", "Mã Sản Phẩm", "Tên Sản Phẩm"});
        JTextField searchField = new JTextField(20);
        // Tạo nút "Làm mới" với icon thay vì chữ
        JLabel refreshButton = new JLabel("", SwingConstants.CENTER);
        refreshButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        refreshButton.setPreferredSize(new Dimension(50, 50));

        String refreshIconPath = iconPath + "refresh.png";
        if (new File(refreshIconPath).exists()) {
            ImageIcon originalIcon = new ImageIcon(refreshIconPath);
            ImageIcon smallIcon = new ImageIcon(originalIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
            ImageIcon largeIcon = new ImageIcon(originalIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));

            refreshButton.setIcon(smallIcon);

            // Hiệu ứng hover phóng to icon
            refreshButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    refreshButton.setIcon(largeIcon);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    refreshButton.setIcon(smallIcon);
                }
            });
        } else {
            System.err.println("Không tìm thấy icon: " + refreshIconPath);
        }

//        functionPanel.add(Box.createHorizontalStrut(5)); // Khoảng cách
        functionPanel.add(searchLabel);
        functionPanel.add(searchOptions);
        functionPanel.add(searchField);
        functionPanel.add(refreshButton);

        add(functionPanel, BorderLayout.NORTH);

 
        String[] columnNames = {"Mã Sản Phẩm", "Tên Sản Phẩm", "Ảnh Sản Phẩm", "Số Lượng", "Mã Nhà CC", "Mã Loại"};
        tableModel = new DefaultTableModel(columnNames, 0);
        productTable = new JTable(tableModel);
        productTable.setFillsViewportHeight(true);
        productTable.setRowHeight(30);
        productTable.setFont(new Font("Arial", Font.PLAIN, 14));


        JTableHeader header = productTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 15));
        header.setForeground(Color.BLACK);
        header.setBackground(new Color(70, 130, 180));

  
        productTable.setBackground(Color.WHITE);
        productTable.setGridColor(new Color(200, 200, 200));
        productTable.setSelectionBackground(new Color(173, 216, 230));

        add(new JScrollPane(productTable), BorderLayout.CENTER);
        loadProductData();
        setVisible(true);
    }

    private void loadProductData() {
        List<String[]> productList = DatabaseProductConnection.getProductData();
        tableModel.setRowCount(0); // Xóa dữ liệu cũ trước khi thêm mới

        for (String[] product : productList) {
            tableModel.addRow(product);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(ProductManagement::new);
    }
}
