package GUI;

import javax.swing.*;
import javax.swing.border.*;
import BUS.ImportInvoiceBUS;
import BUS.SupplierBUS;
import BUS.TypeProductBUS;
import DTO.SupplierDTO;
import DTO.TypeProductDTO;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ProductDetailPanel extends JPanel {
    private JLabel lblProductImage;
    private JTextField txtProductId, txtProductName, txtPrice, txtQuantity;
    private JComboBox<String> cmbSupplier, cmbProductType;
    private JLabel lblTotal;
    private CustomButton btnThemSP;
    private JTextField txtSupplierId, txtProductTypeId;
    private JTextField txtImageFilename; // Ảnh được chọn
    CustomButton btnUpload;

    ArrayList<SupplierDTO> suppliers;
    ArrayList<TypeProductDTO> typeList;

    public ProductDetailPanel(ActionListener addProductListener, ImportInvoiceBUS bus) {
        setLayout(new BorderLayout(15, 15));
        setBorder(new CompoundBorder(new TitledBorder("Thông tin sản phẩm đang chọn"), new EmptyBorder(10, 10, 10, 10)));
        setBackground(Color.WHITE);

        suppliers = SupplierBUS.getAllSupplier();
        typeList = TypeProductBUS.getAllTypeProduct();

        // Ảnh sản phẩm
        lblProductImage = new JLabel("", JLabel.CENTER);
        lblProductImage.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.LIGHT_GRAY, 1), new EmptyBorder(5, 5, 5, 5)));
        loadDefaultImage();

        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setPreferredSize(new Dimension(200, 200));
        imagePanel.setBackground(Color.WHITE);
        imagePanel.add(lblProductImage, BorderLayout.CENTER);

        // Nút Upload ảnh
        btnUpload = new CustomButton("Upload");
        btnUpload.setBackground(new Color(0, 123, 255));
        btnUpload.setForeground(Color.WHITE);
        btnUpload.setFocusPainted(false);
        btnUpload.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnUpload.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        btnUpload.setEnabled(false);
        imagePanel.add(btnUpload, BorderLayout.SOUTH);

        // Sự kiện upload
        btnUpload.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                java.io.File selectedFile = fileChooser.getSelectedFile();
                String fileName = selectedFile.getName();
                txtImageFilename.setText(fileName);

                try {
                    // Thư mục đích để lưu ảnh
                    String destinationDir = "ManagmentBadminton/src/main/resources/images/";
                    java.io.File destinationFolder = new java.io.File(destinationDir);
                    if (!destinationFolder.exists()) {
                        destinationFolder.mkdirs();
                    }

                    // Tạo đường dẫn đích
                    java.io.File destFile = new java.io.File(destinationDir + fileName);

                    // Sao chép file
                    java.nio.file.Files.copy(
                        selectedFile.toPath(),
                        destFile.toPath(),
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING
                    );

                    // Hiển thị ảnh
                    ImageIcon icon = new ImageIcon(destFile.getAbsolutePath());
                    Image scaledImage = icon.getImage().getScaledInstance(140, 140, Image.SCALE_SMOOTH);
                    lblProductImage.setIcon(new ImageIcon(scaledImage));
                    lblProductImage.setText("");

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Không thể sao chép ảnh!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Thông tin chi tiết sản phẩm
        JPanel detailPanel = new JPanel(new GridBagLayout());
        detailPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0; detailPanel.add(new JLabel("Mã sản phẩm:"), gbc);
        gbc.gridx = 1; txtProductId = new JTextField(15); txtProductId.setEditable(false); detailPanel.add(txtProductId, gbc);

        gbc.gridx = 0; gbc.gridy = 1; detailPanel.add(new JLabel("Tên sản phẩm:"), gbc);
        gbc.gridx = 1; txtProductName = new JTextField(15); txtProductName.setEditable(false); detailPanel.add(txtProductName, gbc);

        gbc.gridx = 0; gbc.gridy = 2; detailPanel.add(new JLabel("Nhà cung cấp:"), gbc);
        gbc.gridx = 1;
        cmbSupplier = new JComboBox<>();
        cmbSupplier.setEnabled(false);
        detailPanel.add(cmbSupplier, gbc);

        txtSupplierId = new JTextField(15);
        txtSupplierId.setEditable(false);
        txtSupplierId.setVisible(false);
        detailPanel.add(txtSupplierId, gbc);

        gbc.gridx = 0; gbc.gridy = 3; detailPanel.add(new JLabel("Loại sản phẩm:"), gbc);
        gbc.gridx = 1;
        cmbProductType = new JComboBox<>();
        cmbProductType.setEnabled(false);
        detailPanel.add(cmbProductType, gbc);

        txtProductTypeId = new JTextField(15);
        txtProductTypeId.setEditable(false);
        txtProductTypeId.setVisible(false);
        detailPanel.add(txtProductTypeId, gbc);

        // Trường ẩn lưu tên file ảnh
        txtImageFilename = new JTextField(15);
        txtImageFilename.setEditable(false);
        txtImageFilename.setVisible(false);
        detailPanel.add(txtImageFilename, gbc);

        gbc.gridx = 0; gbc.gridy = 4; detailPanel.add(new JLabel("Đơn giá:"), gbc);
        gbc.gridx = 1; txtPrice = new JTextField(15); txtPrice.setEditable(false); detailPanel.add(txtPrice, gbc);

        gbc.gridx = 0; gbc.gridy = 5; detailPanel.add(new JLabel("Số lượng:"), gbc);
        gbc.gridx = 1; txtQuantity = new JTextField(5); detailPanel.add(txtQuantity, gbc);

        JPanel bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setBackground(Color.WHITE);
        GridBagConstraints gbcBottom = new GridBagConstraints();
        gbcBottom.insets = new Insets(5, 5, 5, 0);

        gbcBottom.gridx = 0; gbcBottom.gridy = 0; bottomPanel.add(new JLabel("THÀNH TIỀN:"), gbcBottom);
        gbcBottom.gridx = 1; lblTotal = new JLabel("0"); lblTotal.setForeground(new Color(0, 100, 0)); bottomPanel.add(lblTotal, gbcBottom);
        gbcBottom.gridx = 2; gbcBottom.weightx = 1.0; gbcBottom.anchor = GridBagConstraints.EAST;
        btnThemSP = new CustomButton("Thêm SP"); btnThemSP.addActionListener(addProductListener); bottomPanel.add(btnThemSP, gbcBottom);

        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBackground(Color.WHITE);
        infoPanel.add(detailPanel, BorderLayout.CENTER);
        infoPanel.add(bottomPanel, BorderLayout.SOUTH);

        txtQuantity.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override public void insertUpdate(javax.swing.event.DocumentEvent e) { updateTotal(bus); }
            @Override public void removeUpdate(javax.swing.event.DocumentEvent e) { updateTotal(bus); }
            @Override public void changedUpdate(javax.swing.event.DocumentEvent e) { updateTotal(bus); }
        });

        add(imagePanel, BorderLayout.WEST);
        add(infoPanel, BorderLayout.CENTER);

        cmbSupplier.addActionListener(e -> updateSupplierId());
        cmbProductType.addActionListener(e -> updateProductTypeId());
        loadSupplierList();
        loadTypeList();
    }

    private void loadDefaultImage() {
        java.net.URL imageUrl = getClass().getResource("/images/default_product.png");
        ImageIcon icon = (imageUrl != null) ? new ImageIcon(imageUrl) : null;
        if (icon != null) {
            lblProductImage.setIcon(new ImageIcon(icon.getImage().getScaledInstance(140, 140, Image.SCALE_SMOOTH)));
            lblProductImage.setText("");
        } else {
            lblProductImage.setText("Không có ảnh");
        }
    }

    private void loadSupplierList() {
        for (SupplierDTO supplier : suppliers) {
            cmbSupplier.addItem(supplier.getSupplierName());
        }
    }

    private void loadTypeList() {
        for (TypeProductDTO type : typeList) {
            cmbProductType.addItem(type.getTypeName());
        }
    }

    private void updateTotal(ImportInvoiceBUS bus) {
        lblTotal.setText(bus.calculateTotal(txtPrice.getText(), txtQuantity.getText()));
    }

    private void updateSupplierId() {
        int selectedSupplier = cmbSupplier.getSelectedIndex();
        if (selectedSupplier >= 0 && selectedSupplier < suppliers.size()) {
            txtSupplierId.setText(suppliers.get(selectedSupplier).getSupplierID());
        }
    }

    private void updateProductTypeId() {
        int selectedType = cmbProductType.getSelectedIndex();
        if (selectedType >= 0 && selectedType < typeList.size()) {
            txtProductTypeId.setText(typeList.get(selectedType).getTypeID());
        }
    }

    // Getters
    public JLabel getLblProductImage() { return lblProductImage; }
    public JTextField getTxtProductId() { return txtProductId; }
    public JTextField getTxtProductName() { return txtProductName; }
    public JComboBox<String> getCmbSupplier() { return cmbSupplier; }
    public JComboBox<String> getCmbProductType() { return cmbProductType; }
    public JTextField getTxtSupplierId() { return txtSupplierId; }
    public JTextField getTxtProductTypeId() { return txtProductTypeId; }
    public JTextField getTxtPrice() { return txtPrice; }
    public JTextField getTxtQuantity() { return txtQuantity; }
    public JLabel getLblTotal() { return lblTotal; }
    public CustomButton getBtnThemSP() { return btnThemSP; }
    public JTextField getTxtImageFilename() { return txtImageFilename; }
    public CustomButton getBtnUpload() { return btnUpload; }

    // Enable/disable các trường (trừ số lượng)
    public void setEditableFields(boolean enabled) {
        txtProductName.setEditable(enabled);
        btnUpload.setEnabled(enabled);
        cmbSupplier.setEnabled(enabled);
        cmbProductType.setEnabled(enabled);
        txtPrice.setEditable(enabled);
    }
}
