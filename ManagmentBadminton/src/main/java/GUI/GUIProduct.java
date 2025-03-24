package GUI;

import DAO.ProductDAO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.table.TableColumnModel;
import java.util.List;

public class GUIProduct extends JPanel {

    private JPanel midPanel, topPanel, botPanel;
    private JTable productTable;
    private CustomTable tableModel;
    private JComboBox<String> roleComboBox;
    private CustomButton saveButton, addButton;
    private CustomSearch searchField;

    public GUIProduct() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(new Color(200, 200, 200));

        // ========== PANEL TRÊN CÙNG ==========
        topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setPreferredSize(new Dimension(0, 60));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding trên-dưới 10px
        topPanel.setBackground(Color.WHITE);
        // Thanh tìm kiếm (70%)
        searchField = new CustomSearch(250, 30);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.setBackground(Color.WHITE);
//        searchField.setPreferredSize(new Dimension(0, 30));
        topPanel.add(searchField, BorderLayout.CENTER);

        // Nút "Thêm tài khoản" (30%)
        addButton = new CustomButton("+ Thêm sản phẩm");
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.setPreferredSize(new Dimension(150, 30));
        topPanel.add(addButton, BorderLayout.EAST);

//        // ========== BẢNG HIỂN THỊ ==========
        midPanel = new JPanel(new BorderLayout());
        midPanel.setBackground(Color.WHITE);
        String[] columnNames = {"Mã Sản Phẩm", "Tên Sản Phẩm", "Số lượng", "Mã Nhà CC", "Mã Loại"};
        tableModel = new CustomTable(columnNames);
        productTable = tableModel.getAccountTable();
        TableColumnModel columnModel = productTable.getColumnModel();
        columnModel.getColumn(1).setPreferredWidth(200); // Tăng độ rộng cột "Nhân viên"
        columnModel.getColumn(2).setPreferredWidth(150); // Cột "Tài khoản"
        columnModel.getColumn(3).setPreferredWidth(150); // Cột "Mật khẩu"
        columnModel.getColumn(4).setPreferredWidth(100); // Cột "Quyền

        // ScrollPane để bảng có thanh cuộn
        CustomScrollPane scrollPane = new CustomScrollPane(productTable);

        midPanel.add(scrollPane, BorderLayout.CENTER);

        // ========== PANEL CHI TIẾT TÀI KHOẢN ==========  
        botPanel = new JPanel(new GridBagLayout());
        botPanel.setBackground(Color.WHITE);
        botPanel.setBorder(BorderFactory.createTitledBorder("Chi tiết sản phẩm"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

// Ảnh nằm riêng biệt bên trái
        JLabel imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(200, 200)); // Kích thước ảnh
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Viền đen

        gbc.gridx =0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST; // Căn trên, sát trái
        gbc.insets = new Insets(0, 0, 10, 50); // Tạo khoảng cách giữa ảnh và thông tin
        botPanel.add(imageLabel, gbc);

// Panel con chứa thông tin để căn giữa
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground(Color.WHITE);

        GridBagConstraints gbcInfo = new GridBagConstraints();
        gbcInfo.insets = new Insets(5, 5, 5, 5);
        gbcInfo.anchor = GridBagConstraints.WEST; // Căn giữa nội dung
        gbcInfo.gridx = 0;
        gbcInfo.gridy = 0;

// Thêm các nhãn vào infoPanel
        infoPanel.add(new JLabel("Mã sản phẩm: "), gbcInfo);
        gbcInfo.gridx = 1;
        JLabel productLabel = new JLabel("Chọn sản phẩm");
        infoPanel.add(productLabel, gbcInfo);

        gbcInfo.gridx = 0;
        gbcInfo.gridy = 1;
        infoPanel.add(new JLabel("Tên sản phẩm: "), gbcInfo);
        gbcInfo.gridx = 1;
        JLabel namePDLabel = new JLabel("");
        infoPanel.add(namePDLabel, gbcInfo);

        gbcInfo.gridx = 0;
        gbcInfo.gridy = 2;
        infoPanel.add(new JLabel("Số lượng: "), gbcInfo);
        gbcInfo.gridx = 1;
        JLabel quantityLabel = new JLabel("");
        infoPanel.add(quantityLabel, gbcInfo);

        gbcInfo.gridx = 0;
        gbcInfo.gridy = 3;
        infoPanel.add(new JLabel("Mã NCC: "), gbcInfo);
        gbcInfo.gridx = 1;
        JLabel MaNCCidLabel = new JLabel("");
        infoPanel.add(MaNCCidLabel, gbcInfo);

        gbcInfo.gridx = 0;
        gbcInfo.gridy = 4;
        infoPanel.add(new JLabel("Mã loại: "), gbcInfo);
        gbcInfo.gridx = 1;
        JLabel TypeidLabel = new JLabel("");
        infoPanel.add(TypeidLabel, gbcInfo);

// Nút lưu
        gbcInfo.gridx = 0;
        gbcInfo.gridy = 5;
        gbcInfo.gridwidth = 2;
        saveButton = new CustomButton("💾 Lưu");
        saveButton.setPreferredSize(new Dimension(80, 30));
        infoPanel.add(saveButton, gbcInfo);

// Thêm infoPanel vào botPanel ở vị trí cột 1 (bên phải ảnh)
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER; // Căn giữa nội dung thông tin
        botPanel.add(infoPanel, gbc);

        productTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow != -1) {
                // Lấy dữ liệu từ bảng
                String masanpham = (String) productTable.getValueAt(selectedRow, 0);
                String tensanpham = (String) productTable.getValueAt(selectedRow, 1);
                String soluongsanpham = (String) productTable.getValueAt(selectedRow, 2);
                String mancc = (String) productTable.getValueAt(selectedRow, 3);
                String maloai = (String) productTable.getValueAt(selectedRow, 4);

                String productImg = ProductDAO.getProductImage(masanpham);

                // Cập nhật giao diện
                productLabel.setText(masanpham);
                quantityLabel.setText(soluongsanpham);
                namePDLabel.setText(tensanpham);
                TypeidLabel.setText(maloai);
                MaNCCidLabel.setText(mancc);

                if (productImg != null && !productImg.isEmpty()) {
                    String imagePath = "/images/" + productImg; // Đường dẫn trong resources
                    java.net.URL imageUrl = getClass().getResource(imagePath);

                    if (imageUrl != null) {
                        ImageIcon productIcon = new ImageIcon(imageUrl);
                        Image img = productIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                        imageLabel.setIcon(new ImageIcon(img));
                    } else {
                        System.out.println("⚠ Không tìm thấy ảnh: " + imagePath);
                        imageLabel.setIcon(null);
                    }
                } else {
                    imageLabel.setIcon(null);
                }

            }
        });

        // ========== THÊM MỌI THỨ VÀO MAINPANEL ==========
        add(topPanel);
        add(Box.createVerticalStrut(10)); // Thêm khoảng cách 10px
        add(midPanel);
        add(Box.createVerticalStrut(10)); // Thêm khoảng cách 10px
        add(botPanel);
        loadProductData();
    }

    private void loadProductData() {
        List<String[]> productList = ProductDAO.getProductData();
        for (String[] product : productList) {
            tableModel.addRow(new Object[]{
                product[0], product[1], product[2], product[3], product[4]
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Quản lý sản phẩm");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 600); // Đặt kích thước cửa sổ
            frame.setLocationRelativeTo(null); // Căn giữa màn hình
            frame.setContentPane(new GUIProduct()); // Đặt JPanel Account vào JFrame
            frame.setVisible(true);
        });
    }
}
