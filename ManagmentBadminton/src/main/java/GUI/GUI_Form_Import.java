package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Image;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import BUS.AccountBUS;
import BUS.ImportInvoiceBUS;
import BUS.ProductBUS;
import BUS.SupplierBUS;
import BUS.TypeProductBUS;
import DTO.ImportInvoiceDTO;
import DTO.ProductDTO;

public class GUI_Form_Import extends JDialog {
    private InfoPanel infoPanel;
    private ImportProductsPanel importProductsPanel;
    private ProductDetailPanel productDetailPanel;
    private AllProductsPanel allProductsPanel;
    private CustomButton btnLuu, btnHuy;
    
    private ImportInvoiceBUS bus;
    private String currentUser;
    private GUI_Import parentImportPanel;
    private int totalAmount = 0;

    public GUI_Form_Import(GUI_Import parentImportPanel, String username) {
        super((Frame) SwingUtilities.getWindowAncestor(parentImportPanel), "Nhập Hàng Mới", true);
        this.parentImportPanel = parentImportPanel;
        this.currentUser = username;
        this.bus = new ImportInvoiceBUS();
        
        initializeUI();
        loadAllProducts();
    }

    private void initializeUI() {
        setSize(1100, 750);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Color.WHITE);

        // Info Panel
        infoPanel = new InfoPanel(bus.generateNextImportID(), currentUser, bus);
        infoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        mainPanel.add(infoPanel);

        // Center Panel (All Products + Import Products)
        JPanel centerPanel = new JPanel(new BorderLayout(15, 15));
        centerPanel.setBackground(Color.WHITE);

        allProductsPanel = new AllProductsPanel();
        allProductsPanel.setPreferredSize(new Dimension(400, 300));
        centerPanel.add(allProductsPanel, BorderLayout.WEST);
        
        importProductsPanel = new ImportProductsPanel();
        importProductsPanel.setPreferredSize(new Dimension(600, 300));
        importProductsPanel.getProductsTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && importProductsPanel.getProductsTable().getSelectedRow() >= 0) {
                int row = importProductsPanel.getProductsTable().getSelectedRow();
                String productId = importProductsPanel.getImportTableModel().getValueAt(row, 0).toString();
                displayProductDetailsFromImportTable(productId);
            }
        });
        
        importProductsPanel.getBtnDeleteProduct().addActionListener(e -> deleteSelectedProduct());
        centerPanel.add(importProductsPanel, BorderLayout.CENTER);
        
        mainPanel.add(centerPanel);
        
        // Product Detail Panel
        productDetailPanel = new ProductDetailPanel(e -> addProductToImport(), bus);
        productDetailPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));
        mainPanel.add(productDetailPanel);
        allProductsPanel.getAllProductsTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && allProductsPanel.getAllProductsTable().getSelectedRow() >= 0) {
                int row = allProductsPanel.getAllProductsTable().getSelectedRow();
                productDetailPanel.setEditableFields(false);
                displayProductDetails(
                    allProductsPanel.getProductTableModel().getValueAt(row, 0).toString(),
                    allProductsPanel.getProductTableModel().getValueAt(row, 1).toString(),
                    allProductsPanel.getProductTableModel().getValueAt(row, 2).toString()
                );
            }
        });
        allProductsPanel.getBtnNewProduct().addActionListener(e -> {
            ProductBUS productBUS = new ProductBUS();
            resetProductDetail();

            String newProductId = productBUS.generateNewProductID();

            // Nếu tồn tại mã này trong bảng import thì tăng lên
            while (importProductsPanel.getAllProductID().contains(newProductId)) {
                // Tách prefix là phần chữ cái đầu, suffix là phần số ở cuối
                String prefix = newProductId.replaceAll("\\d+$", ""); // bỏ phần số ở cuối
                String numberPart = newProductId.replaceAll("\\D+", ""); // chỉ lấy phần số
            
                int number = 0;
                try {
                    number = Integer.parseInt(numberPart);
                } catch (NumberFormatException ex) {
                    number = 0;
                }
            
                number++;
                newProductId = prefix + String.format("%02d", number); // định dạng 2 chữ số
            }
            // Gán mã sản phẩm sau khi xử lý xong
            productDetailPanel.getTxtProductId().setText(newProductId);
            productDetailPanel.getTxtProductTypeId().setText("T01");
            productDetailPanel.getTxtSupplierId().setText("S01");
            productDetailPanel.setEditableFields(true);
        });

        // Button Panel
        JPanel buttonPanel = createButtonPanel();
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        mainPanel.add(buttonPanel);

        add(mainPanel);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setBackground(Color.WHITE);

        btnLuu = new CustomButton("Lưu phiếu nhập");
        btnLuu.setCustomColor(new Color(0, 120, 215));
        btnLuu.setPreferredSize(new Dimension(150, 35));
        btnLuu.addActionListener(e -> saveImport());

        btnHuy = new CustomButton("Hủy");
        btnHuy.setCustomColor(new Color(220, 0, 0));
        btnHuy.setPreferredSize(new Dimension(150, 35));
        btnHuy.addActionListener(e -> dispose());

        panel.add(btnLuu);
        panel.add(btnHuy);
        return panel;
    }

    private void loadAllProducts() {
        ArrayList<Object[]> products = bus.loadAllProducts();
        allProductsPanel.getProductTableModel().setRowCount(0);
        products.forEach(allProductsPanel.getProductTableModel()::addRow);
    }

    private void displayProductDetails(String productId, String productName, String price) {
        Object[] details = bus.getProductDetails(productId);
        if (details != null) {
            productDetailPanel.getTxtProductId().setText((String) details[0]);
            productDetailPanel.getTxtProductName().setText((String) details[1]);
            productDetailPanel.getTxtPrice().setText((String) details[2]);

            if (Integer.parseInt(((String) details[2]).replace(",", "")) == 0) 
                productDetailPanel.getTxtPrice().setEditable(true);
            else 
                productDetailPanel.getTxtPrice().setEditable(false);
            productDetailPanel.getTxtSupplierId().setText((String) details[3]);
            productDetailPanel.getCmbSupplier().setSelectedItem(SupplierBUS.getSupplierByID((String) details[3]).getSupplierName());
            productDetailPanel.getTxtProductTypeId().setText(TypeProductBUS.getTypeProductByName((String) details[4]).getTypeID());
            productDetailPanel.getCmbProductType().setSelectedItem((String) details[4]);
            loadProductImage((String) details[5]);
            productDetailPanel.getTxtImageFilename().setText((String) details[5]);
            productDetailPanel.getTxtQuantity().setText("");
            productDetailPanel.getLblTotal().setText("0");
        }
    }

    private void loadProductImage(String imageFileName) {
        String imagePath = "/images/" + (imageFileName != null ? imageFileName : "default_product.png");
        java.net.URL imageUrl = getClass().getResource(imagePath);
        ImageIcon icon = (imageUrl != null) ? new ImageIcon(imageUrl) : null;
        productDetailPanel.getLblProductImage().setIcon(
            icon != null ? new ImageIcon(icon.getImage().getScaledInstance(140, 140, Image.SCALE_SMOOTH)) : null
        );
        productDetailPanel.getLblProductImage().setText(icon == null ? "Không có ảnh" : "");
    }

    private void addProductToImport() {
        try {
            String productId = productDetailPanel.getTxtProductId().getText();
            String supplierID = productDetailPanel.getTxtSupplierId().getText();
            String productTypeID = productDetailPanel.getTxtProductTypeId().getText();
            String quantityText = productDetailPanel.getTxtQuantity().getText().trim();
            String validationError = bus.validateProductToAdd(productId, quantityText);
            String productImg = productDetailPanel.getTxtImageFilename().getText();
            String productName = productDetailPanel.getTxtProductName().getText();
            String price = productDetailPanel.getTxtPrice().getText();
            //Kiểm tra tất cả không để trống
            if (productName == null || productName.isEmpty() || price == null || price.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin sản phẩm", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            //Kiểm tra số lượng nhập hàng
            if (quantityText == null || quantityText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng sản phẩm", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            //Kiểm tra giá nhập hàng
            if (price == null || price.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng sản phẩm", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            //Kiem tra hinh anh
            if (productImg == null || productImg.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn hình ảnh sản phẩm", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            ProductBUS productBUS = new ProductBUS();
            if (productBUS.getProductByID(productId) == null && !bus.validateProductImport(productName, productImg)){
                return;
            }
            if (!bus.validateProductImport(price.replaceAll("[^0-9]", ""))){
                return;
            }
            int quantity = Integer.parseInt(quantityText);
            double priceImport = Double.parseDouble(price.replaceAll("[^0-9]", ""));
            double total = priceImport * quantity;
            if (validationError != null) {
                JOptionPane.showMessageDialog(this, validationError, "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DefaultTableModel model = importProductsPanel.getImportTableModel();
            boolean found = false;

            for (int i = 0; i < model.getRowCount(); i++) {
                String existingProductId = model.getValueAt(i, 0).toString();
                if (existingProductId.equals(productId)) {
                    // Tăng số lượng
                    int oldQuantity = Integer.parseInt(model.getValueAt(i, 2).toString());
                    int newQuantity = oldQuantity + quantity;
                    double newTotal = newQuantity * priceImport;

                    model.setValueAt(newQuantity, i, 2);
                    model.setValueAt(Utils.formatCurrency(price), i, 3);
                    model.setValueAt(Utils.formatCurrency(newTotal), i, 4);

                    found = true;
                    break;
                }
            }

            if (!found) {
                // Thêm mới nếu chưa có
                model.addRow(new Object[]{
                    productId, productName, quantity, Utils.formatCurrency(price), Utils.formatCurrency(total), supplierID, productTypeID, productImg
                });
            }

            totalAmount += total;
            infoPanel.getLblTongTien().setText(Utils.formatCurrency(totalAmount));

            
            resetProductDetail();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm sản phẩm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteSelectedProduct() {
        int selectedRow = importProductsPanel.getProductsTable().getSelectedRow();
        DefaultTableModel model = importProductsPanel.getImportTableModel();
    
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm để xóa", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
    
        // Lấy thành tiền từ dòng được chọn (cột 4)
        String totalStr = model.getValueAt(selectedRow, 4).toString().replaceAll("[^0-9]", "");
        int removedTotal = Integer.parseInt(totalStr);
    
        totalAmount -= removedTotal;
        if (totalAmount < 0) totalAmount = 0;
    
        // Cập nhật lại tổng tiền
        infoPanel.getLblTongTien().setText(Utils.formatCurrency(totalAmount));
    
        // Xóa dòng
        model.removeRow(selectedRow);
    }

    private void displayProductDetailsFromImportTable(String productId) {
        Object[] details = bus.getProductDetails(productId);
        if (details != null) {
            productDetailPanel.getTxtProductId().setText((String) details[0]);
            productDetailPanel.getTxtProductName().setText((String) details[1]);
            productDetailPanel.getTxtPrice().setText((String) details[2]);
            productDetailPanel.getCmbSupplier().setSelectedItem((String) details[3]);
            loadProductImage((String) details[4]);
    
            // Lấy số lượng đang có trong bảng
            DefaultTableModel model = importProductsPanel.getImportTableModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                if (model.getValueAt(i, 0).toString().equals(productId)) {
                    String quantityStr = model.getValueAt(i, 2).toString();
                    productDetailPanel.getTxtQuantity().setText(quantityStr);
    
                    double price = Integer.parseInt(details[2].toString().replaceAll("[^0-9]", ""));
                    int quantity = Integer.parseInt(quantityStr);
                    double total = price * quantity;
                    productDetailPanel.getLblTotal().setText(Utils.formatCurrency(total));
                    break;
                }
            }
        }
    }

    private void saveImport() {
        if (importProductsPanel.getImportTableModel().getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng thêm ít nhất một sản phẩm", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        String importID = infoPanel.getLblMaNhapHang().getText();
        String receiptDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String employeeID = AccountBUS.getAccountByUsername(currentUser).getEmployeeID();
        ArrayList<Object[]> productData = new ArrayList<>();
        for (int i = 0; i < importProductsPanel.getImportTableModel().getRowCount(); i++) {
            String productId = importProductsPanel.getImportTableModel().getValueAt(i, 0).toString();
            String productName = importProductsPanel.getImportTableModel().getValueAt(i, 1).toString();
            String quantityStr = importProductsPanel.getImportTableModel().getValueAt(i, 2).toString().replaceAll("[^0-9]", "");
            String priceStr = importProductsPanel.getImportTableModel().getValueAt(i, 3).toString().replaceAll("[^0-9]", "");
            String totalPriceStr = importProductsPanel.getImportTableModel().getValueAt(i, 4).toString().replaceAll("[^0-9]", "");
            String supplierID = importProductsPanel.getImportTableModel().getValueAt(i, 5).toString();
            String typeID = importProductsPanel.getImportTableModel().getValueAt(i, 6).toString();
            String productImg = importProductsPanel.getImportTableModel().getValueAt(i, 7).toString();
            productData.add(new Object[]{
                productId,
                productName,
                quantityStr,
                supplierID,
                Double.parseDouble(priceStr),
                Double.parseDouble(totalPriceStr),
                typeID,
                productImg
            });
            System.out.println("Product ID: " + productId + ", Quantity: " + quantityStr + ", Supplier ID: " + supplierID);
        }
    
        ImportInvoiceDTO importDTO = new ImportInvoiceDTO(importID, employeeID, receiptDate, totalAmount);
        if (bus.insert(importDTO, productData)) {
            JOptionPane.showMessageDialog(this, "Lưu phiếu nhập thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            parentImportPanel.loadImport();
            loadProductData();
            loadAllProducts();
            resetForm();
        } else {
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu phiếu nhập", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadProductData() {
        SwingUtilities.invokeLater(() -> {
            DefaultTableModel model = (DefaultTableModel) GUI_Product.getProductTable().getModel();
            model.setRowCount(0); // Xóa dữ liệu cũ

            List<ProductDTO> products = ProductBUS.getAllProducts();
            for (ProductDTO product : products) {
                model.addRow(new Object[]{
                    product.getProductID(),
                    product.getProductName(),
                    product.getGia(),
                    product.getSoluong()
                });
            }
        });
    }

    private void resetProductDetail() {
        allProductsPanel.getAllProductsTable().clearSelection();
        productDetailPanel.getTxtProductId().setText("Chọn sản phẩm từ danh sách");
        productDetailPanel.getTxtProductName().setText("");
        productDetailPanel.getCmbSupplier().setSelectedIndex(0);
        productDetailPanel.getTxtSupplierId().setText("");
        productDetailPanel.getCmbProductType().setSelectedIndex(0);
        productDetailPanel.getTxtProductTypeId().setText("");
        productDetailPanel.getTxtPrice().setText("");
        productDetailPanel.getLblProductImage().setIcon(null);
        productDetailPanel.getTxtImageFilename().setText("");
        productDetailPanel.getTxtQuantity().setText("");
        productDetailPanel.getLblTotal().setText("0");
    }

    private void resetForm() {
        importProductsPanel.getImportTableModel().setRowCount(0);
        totalAmount = 0;
        infoPanel.getLblTongTien().setText("0");
        dispose();
    }
}