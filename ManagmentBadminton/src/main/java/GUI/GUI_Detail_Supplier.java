package GUI;

import BUS.SupplierBUS;
import DAO.ProductDAO;
import DTO.ProductDTO;
// import DAO.ProductDAO;
// import DTO.ProductDTO;
import DTO.SupplierDTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GUI_Detail_Supplier extends JDialog {
    
    private SupplierBUS supplierBUS;
    private SupplierDTO supplier;
    private JTable productTable;
    private DefaultTableModel productTableModel;
    private JLabel imageLabel;
    private JLabel productIDLabel, nameLabel, priceLabel, quantityLabel, tsktLabel, supplierNameLabel, categoryLabel, totalLabel;
    private JPanel detailPanel, placeholderPanel, mainPanel;

    public GUI_Detail_Supplier(GUI_Supplier parent, SupplierDTO supplier) {
        super((Frame) SwingUtilities.getWindowAncestor(parent), "Danh Sách Sản Phẩm Nhà Cung Cấp", true);
        this.supplier = supplier;
        supplierBUS = new SupplierBUS();

        // Tăng chiều cao cửa sổ để hiển thị nhiều sản phẩm hơn
        setSize(900, 800);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        // Main panel để chứa các thành phần
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(Color.WHITE);

        // Supplier Info Panel
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Thông Tin Nhà Cung Cấp"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        infoPanel.add(new JLabel("Mã Nhà Cung Cấp:"), gbc);
        gbc.gridx = 1;
        infoPanel.add(new JLabel(supplier.getSupplierID()), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        infoPanel.add(new JLabel("Tên Nhà Cung Cấp:"), gbc);
        gbc.gridx = 1;
        infoPanel.add(new JLabel(supplier.getSupplierName()), gbc);

        // Product Table
        String[] columnNames = {"Mã SP", "Tên SP", "Giá", "Số Lượng"}; // Bỏ cột "Tổng Tiền"
        productTableModel = new DefaultTableModel(columnNames, 0);
        productTable = new JTable(productTableModel);
        
        // Tăng kích thước font chữ của bảng
        productTable.setFont(new Font("Arial", Font.PLAIN, 14));
        productTable.setRowHeight(25); // Tăng chiều cao hàng

        // Điều chỉnh độ rộng cột
        TableColumnModel columnModel = productTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(80);  // Mã SP
        columnModel.getColumn(1).setPreferredWidth(250); // Tên SP
        columnModel.getColumn(2).setPreferredWidth(100); // Giá
        columnModel.getColumn(3).setPreferredWidth(80);  // Số Lượng

        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Danh Sách Sản Phẩm"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        loadProducts();

        // Placeholder panel (khoảng trắng ban đầu)
        placeholderPanel = new JPanel();
        placeholderPanel.setBackground(Color.WHITE);
        placeholderPanel.setPreferredSize(new Dimension(0, 200)); // Chừa sẵn khoảng trắng cao 200px

        // Panel hiển thị chi tiết sản phẩm (sẽ hiển thị khi chọn sản phẩm)
        detailPanel = new JPanel(new BorderLayout(20, 0));
        detailPanel.setBackground(Color.WHITE);
        detailPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Chi Tiết Sản Phẩm"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Phần hiển thị hình ảnh
        JPanel leftPanel = new JPanel(null);
        leftPanel.setPreferredSize(new Dimension(310, 200));
        leftPanel.setBackground(Color.WHITE);

        imageLabel = new JLabel();
        imageLabel.setBounds(30, 10, 230, 180);
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        leftPanel.add(imageLabel);

        // Phần hiển thị thông tin chi tiết
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Color.WHITE);

        GridBagConstraints gbcInfo = new GridBagConstraints();
        gbcInfo.insets = new Insets(5, 5, 5, 5);
        gbcInfo.anchor = GridBagConstraints.WEST;

        gbcInfo.gridx = 0;
        gbcInfo.gridy = 0;
        rightPanel.add(new JLabel("Mã sản phẩm: "), gbcInfo);
        gbcInfo.gridx = 1;
        productIDLabel = new JLabel("Chọn sản phẩm");
        rightPanel.add(productIDLabel, gbcInfo);

        gbcInfo.gridx = 0;
        gbcInfo.gridy = 1;
        rightPanel.add(new JLabel("Tên sản phẩm: "), gbcInfo);
        gbcInfo.gridx = 1;
        nameLabel = new JLabel("");
        nameLabel.setPreferredSize(new Dimension(300, 20));
        rightPanel.add(nameLabel, gbcInfo);

        gbcInfo.gridx = 0;
        gbcInfo.gridy = 2;
        rightPanel.add(new JLabel("Giá: "), gbcInfo);
        gbcInfo.gridx = 1;
        priceLabel = new JLabel("");
        rightPanel.add(priceLabel, gbcInfo);

        gbcInfo.gridx = 0;
        gbcInfo.gridy = 3;
        rightPanel.add(new JLabel("Số lượng: "), gbcInfo);
        gbcInfo.gridx = 1;
        quantityLabel = new JLabel("");
        rightPanel.add(quantityLabel, gbcInfo);

        gbcInfo.gridx = 0;
        gbcInfo.gridy = 4;
        rightPanel.add(new JLabel("Tổng tiền: "), gbcInfo); // Thêm dòng Tổng tiền
        gbcInfo.gridx = 1;
        totalLabel = new JLabel("");
        rightPanel.add(totalLabel, gbcInfo);

        gbcInfo.gridx = 0;
        gbcInfo.gridy = 5;
        rightPanel.add(new JLabel("Tên NCC: "), gbcInfo);
        gbcInfo.gridx = 1;
        supplierNameLabel = new JLabel("");
        rightPanel.add(supplierNameLabel, gbcInfo);

        gbcInfo.gridx = 0;
        gbcInfo.gridy = 6;
        rightPanel.add(new JLabel("Tên loại: "), gbcInfo);
        gbcInfo.gridx = 1;
        categoryLabel = new JLabel("");
        rightPanel.add(categoryLabel, gbcInfo);

        detailPanel.add(leftPanel, BorderLayout.WEST);
        detailPanel.add(rightPanel, BorderLayout.CENTER);

        // Thêm sự kiện khi chọn hàng trong bảng
        productTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) { // Tránh sự kiện được gọi nhiều lần
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow != -1) {
                    String productID = (String) productTable.getValueAt(selectedRow, 0);

                    ProductDTO product = ProductDAO.getProduct(productID);

                    if (product != null) {
                        // Cập nhật thông tin chi tiết
                        productIDLabel.setText(product.getProductID());
                        nameLabel.setText(product.getProductName());
                        priceLabel.setText(Utils.formatCurrency(product.getGia()) + " VND"); // Thêm VND
                        quantityLabel.setText(String.valueOf(product.getSoluong()));
                        totalLabel.setText(Utils.formatCurrency(Utils.parseCurrency(product.getGia()) * Utils.parseCurrency(product.getSoluong())) + " VND");
                        supplierNameLabel.setText(product.gettenNCC());
                        categoryLabel.setText(product.getTL());

                        // Cập nhật hình ảnh
                        String imageFileName = product.getAnh();
                        String imagePath = "/images/" + (imageFileName != null ? imageFileName : "default_product.png");
                        URL imageUrl = getClass().getResource(imagePath);
                        ImageIcon icon = (imageUrl != null) ? new ImageIcon(imageUrl) : null;
                        imageLabel.setIcon(
                            icon != null ? new ImageIcon(icon.getImage().getScaledInstance(230, 180, Image.SCALE_SMOOTH)) : null
                        );
                        imageLabel.setText(icon == null ? "Không có ảnh" : "");

                        // Thay thế placeholderPanel bằng detailPanel
                        mainPanel.remove(placeholderPanel);
                        mainPanel.add(detailPanel, BorderLayout.SOUTH);
                        mainPanel.revalidate();
                        mainPanel.repaint();
                    } else {
                        // Thông báo lỗi nếu không tìm thấy sản phẩm
                        JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm với ID: " + productID, "Lỗi", JOptionPane.ERROR_MESSAGE);
                        // Hiển thị lại placeholderPanel
                        mainPanel.remove(detailPanel);
                        mainPanel.add(placeholderPanel, BorderLayout.SOUTH);
                        mainPanel.revalidate();
                        mainPanel.repaint();
                    }
                } else {
                    // Nếu không có hàng nào được chọn, hiển thị lại placeholderPanel
                    mainPanel.remove(detailPanel);
                    mainPanel.add(placeholderPanel, BorderLayout.SOUTH);
                    mainPanel.revalidate();
                    mainPanel.repaint();
                }
            }
        });

        // Thêm các thành phần vào mainPanel
        mainPanel.add(infoPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(placeholderPanel, BorderLayout.SOUTH); // Ban đầu hiển thị khoảng trắng

        // Thêm mainPanel vào cửa sổ
        add(mainPanel, BorderLayout.CENTER);

        // Close Button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        CustomButton btnClose = new CustomButton("Đóng");
        btnClose.addActionListener(e -> dispose());
        buttonPanel.add(btnClose);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadProducts() {
        ArrayList<ProductDTO> products = supplierBUS.getProductsBySupplier(supplier.getSupplierID());
        productTableModel.setRowCount(0);
        for (ProductDTO product : products) {
            productTableModel.addRow(new Object[]{
                product.getProductID(),
                product.getProductName(),
                Utils.formatCurrency(product.getGia()) + " VND", // Thêm VND
                product.getSoluong()
            });
        }
    }
}