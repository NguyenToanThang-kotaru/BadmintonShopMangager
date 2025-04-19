package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import BUS.CustomerBUS;
import BUS.DetailSaleInvoiceBUS;
import BUS.EmployeeBUS;
import BUS.ProductBUS;
import BUS.ProductDetailBUS;
import BUS.ProductSoldBUS;
import BUS.SaleInvoiceBUS;
import DAO.ProductDAO;
import DTO.AccountDTO;
import DTO.CustomerDTO;
import DTO.DetailSaleInvoiceDTO;
import DTO.EmployeeDTO;
import DTO.ProductDTO;
import DTO.ProductDetailDTO;
import DTO.ProductSoldDTO;
import DTO.SaleInvoiceDTO;

public class GUI_Form_Order extends JDialog {
    private JLabel lblMaHoaDon, lblNgayXuat, lblNhanVien, lblTongTien;
    private JTable productsTable, allProductsTable;
    private DefaultTableModel orderTableModel, productTableModel;
    private CustomButton btnThemSP, btnLuu, btnHuy;
    private JLabel lblProductImage, lblProductId, lblProductName, lblCategory, lblPrice;
    private JTextField txtQuantity, txtMaKhachHang, txtTenKhachHang, txtSoDienThoai;
    private SaleInvoiceBUS orderBUS;
    private SaleInvoiceDTO currentOrder;
    private AccountDTO currentAccount;
    private int totalAmount;
    private ProductBUS productBUS = new ProductBUS();
    private CustomerBUS customerBUS = new CustomerBUS();

    public GUI_Form_Order(JPanel parent, SaleInvoiceDTO order, AccountDTO account) {
        
        super((Frame) SwingUtilities.getWindowAncestor(parent), "Tạo Hóa Đơn", true);
        this.orderBUS = new SaleInvoiceBUS();
        this.currentOrder = order;
        this.currentAccount = account;
        this.totalAmount = 0;

        setSize(1100, 750);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Color.WHITE);

        JPanel infoPanel = createInfoPanel();
        infoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        mainPanel.add(infoPanel);

        JPanel centerPanel = new JPanel(new BorderLayout(15, 15));
        centerPanel.setBackground(Color.WHITE);

        JPanel allProductsPanel = createAllProductsPanel();
        allProductsPanel.setPreferredSize(new Dimension(600, 300));
        centerPanel.add(allProductsPanel, BorderLayout.WEST);

        JPanel orderProductsPanel = createOrderProductsPanel();
        orderProductsPanel.setPreferredSize(new Dimension(400, 300));
        centerPanel.add(orderProductsPanel, BorderLayout.CENTER);

        mainPanel.add(centerPanel);

        JPanel productDetailPanel = createProductDetailPanel();
        productDetailPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));
        mainPanel.add(productDetailPanel);

        JPanel buttonPanel = createButtonPanel();
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        mainPanel.add(buttonPanel);

        add(mainPanel);

        loadAllProducts();

        if (currentOrder != null) {
            loadOrderData();
        }
    }

    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(new CompoundBorder(new TitledBorder("Thông tin hóa đơn"), new EmptyBorder(10, 10, 10, 10)));
        panel.setBackground(Color.WHITE);

        JPanel maHoaDonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        maHoaDonPanel.add(new JLabel("Mã hóa đơn:"));
        lblMaHoaDon = new JLabel(getNextOrderID());
        lblMaHoaDon.setFont(new Font("Segoe UI", Font.BOLD, 14));
        maHoaDonPanel.add(lblMaHoaDon);
        panel.add(maHoaDonPanel);

        JPanel nhanVienPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        nhanVienPanel.add(new JLabel("Nhân viên:"));
        lblNhanVien = new JLabel(getEmployeeNameByID(currentAccount.getEmployeeID()));
        lblNhanVien.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nhanVienPanel.add(lblNhanVien);
        panel.add(nhanVienPanel);

        JPanel ngayXuatPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ngayXuatPanel.add(new JLabel("Ngày xuất:"));
        lblNgayXuat = new JLabel(LocalDate.now().toString());
        lblNgayXuat.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        ngayXuatPanel.add(lblNgayXuat);
        panel.add(ngayXuatPanel);

        JPanel tongTienPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tongTienPanel.add(new JLabel("Tổng tiền:"));
        lblTongTien = new JLabel("0");
        lblTongTien.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTongTien.setForeground(new Color(0, 100, 0));
        tongTienPanel.add(lblTongTien);
        panel.add(tongTienPanel);

        return panel;
    }

    private JPanel createAllProductsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new CompoundBorder(new TitledBorder("Danh sách sản phẩm"), new EmptyBorder(5, 5, 5, 5)));
        panel.setBackground(Color.WHITE);

        String[] columns = {"Mã SP", "Tên SP", "Loại", "Đơn giá", "Tồn kho"};
        productTableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };

        allProductsTable = new JTable(productTableModel);
        allProductsTable.setRowHeight(30);
        allProductsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        allProductsTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        TableColumnModel columnModel = allProductsTable.getColumnModel();
        for (int i = 0; i < allProductsTable.getColumnCount(); i++) {
            columnModel.getColumn(i).setCellRenderer(centerRenderer);
        }

        allProductsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = allProductsTable.getSelectedRow();
                if (selectedRow >= 0) {
                    displayProductDetails(
                        productTableModel.getValueAt(selectedRow, 0).toString(),
                        productTableModel.getValueAt(selectedRow, 1).toString(),
                        productTableModel.getValueAt(selectedRow, 2).toString(),
                        productTableModel.getValueAt(selectedRow, 3).toString()
                    );
                }
            }
        });

        panel.add(new JScrollPane(allProductsTable), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createOrderProductsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new CompoundBorder(new TitledBorder("Danh sách sản phẩm trong hóa đơn"), new EmptyBorder(5, 5, 5, 5)));
        panel.setBackground(Color.WHITE);

        String[] columns = {"Mã SP", "Tên SP", "Loại", "Số lượng", "Đơn giá", "Thành tiền"};
        orderTableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };

        productsTable = new JTable(orderTableModel);
        productsTable.setRowHeight(30);
        productsTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        productsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = productsTable.getSelectedRow();
                if (selectedRow >= 0) {
                    String productId = orderTableModel.getValueAt(selectedRow, 0).toString();
                    String productName = orderTableModel.getValueAt(selectedRow, 1).toString();
                    String category = orderTableModel.getValueAt(selectedRow, 2).toString();
                    String quantity = orderTableModel.getValueAt(selectedRow, 3).toString();
                    String price = orderTableModel.getValueAt(selectedRow, 4).toString();

                    lblProductId.setText(productId);
                    lblProductName.setText(productName);
                    lblCategory.setText(category);
                    lblPrice.setText(price);
                    txtQuantity.setText(quantity);

                    String img = productBUS.getProductImage(productId);
                    if (img != null && !img.isEmpty()) {
                        lblProductImage.setIcon(new ImageIcon(new ImageIcon("images/" + img).getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH)));
                        lblProductImage.setText("");
                    } else {
                        lblProductImage.setIcon(null);
                        lblProductImage.setText("Không có ảnh");
                    }
                }
            }
        });

        TableColumnModel columnModel = productsTable.getColumnModel();
        for (int i = 0; i < productsTable.getColumnCount(); i++) {
            columnModel.getColumn(i).setCellRenderer(new DefaultTableCellRenderer() {{
                setHorizontalAlignment(JLabel.CENTER);
            }});
        }

        panel.add(new JScrollPane(productsTable), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createProductDetailPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(new CompoundBorder(new TitledBorder("Thông tin sản phẩm đang chọn"), new EmptyBorder(10, 10, 10, 10)));
        panel.setBackground(Color.WHITE);

        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setPreferredSize(new Dimension(200, 200));
        imagePanel.setBackground(Color.WHITE);

        lblProductImage = new JLabel("Không có ảnh", SwingConstants.CENTER);
        lblProductImage.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.LIGHT_GRAY, 1), new EmptyBorder(5, 5, 5, 5)));
        imagePanel.add(lblProductImage);

        JPanel detailPanel = new JPanel(new GridBagLayout());
        detailPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0; detailPanel.add(new JLabel("Mã sản phẩm:"), gbc);
        gbc.gridx = 1; lblProductId = new JLabel("Chọn sản phẩm từ danh sách"); detailPanel.add(lblProductId, gbc);
        gbc.gridx = 0; gbc.gridy = 1; detailPanel.add(new JLabel("Tên sản phẩm:"), gbc);
        gbc.gridx = 1; lblProductName = new JLabel(); detailPanel.add(lblProductName, gbc);
        gbc.gridx = 0; gbc.gridy = 2; detailPanel.add(new JLabel("Loại sản phẩm:"), gbc);
        gbc.gridx = 1; lblCategory = new JLabel(); detailPanel.add(lblCategory, gbc);
        gbc.gridx = 0; gbc.gridy = 3; detailPanel.add(new JLabel("Đơn giá:"), gbc);
        gbc.gridx = 1; lblPrice = new JLabel(); detailPanel.add(lblPrice, gbc);
        gbc.gridx = 0; gbc.gridy = 4; detailPanel.add(new JLabel("Số lượng:"), gbc);
        gbc.gridx = 1; txtQuantity = new JTextField(10); detailPanel.add(txtQuantity, gbc);
        gbc.gridx = 0; gbc.gridy = 5; detailPanel.add(new JLabel("Số điện thoại KH:"), gbc);
        gbc.gridx = 1; txtSoDienThoai = new JTextField(25); detailPanel.add(txtSoDienThoai, gbc);

        txtSoDienThoai.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String phone = txtSoDienThoai.getText().trim();
                if (!phone.matches("(02|03|05|07|08|09)\\d{8}")) {
                    txtMaKhachHang.setText("Số điện thoại không hợp lệ");
                    txtTenKhachHang.setText("");
                    txtTenKhachHang.setEditable(false); // cho nhập tên nếu không hợp lệ
                } 
                else {
                    CustomerDTO customer = customerBUS.getByPhone(phone);
                    if (customer != null) {
                        System.out.println(customer.getName() + " - " + customer.getId());
                        txtMaKhachHang.setText(customer.getId());
                        txtTenKhachHang.setText(customer.getName());
                        txtTenKhachHang.setEditable(false); // khóa lại nếu tìm thấy
                    } else {
                        txtMaKhachHang.setText(getNextCustomerID()); // gán mã mới
                        txtTenKhachHang.setText("");
                        txtTenKhachHang.setEditable(true); // cho nhập tên nếu chưa tồn tại
                    }
                }
            }
        });

        gbc.gridx = 0; gbc.gridy = 6; detailPanel.add(new JLabel("Mã khách hàng:"), gbc);
        gbc.gridx = 1; txtMaKhachHang = new JTextField(25); txtMaKhachHang.setEditable(false); detailPanel.add(txtMaKhachHang, gbc);

        gbc.gridx = 0; gbc.gridy = 7; detailPanel.add(new JLabel("Tên khách hàng:"), gbc);
        gbc.gridx = 1; txtTenKhachHang = new JTextField(25); txtTenKhachHang.setEditable(false); detailPanel.add(txtTenKhachHang, gbc);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        bottomPanel.setBackground(Color.WHITE);
        CustomButton btnSuaSL = new CustomButton("Sửa SL");
        btnSuaSL.setCustomColor(new Color(0, 128, 255));
        btnSuaSL.addActionListener(e -> updateQuantityInOrder());
        bottomPanel.add(btnSuaSL);
        btnThemSP = new CustomButton("Thêm SP");
        btnThemSP.addActionListener(e -> addProductToOrder());
        bottomPanel.add(btnThemSP);
        CustomButton btnXoaSP = new CustomButton("Xóa SP");
        btnXoaSP.setCustomColor(new Color(255, 140, 0));
        btnXoaSP.addActionListener(e -> removeSelectedProductFromOrder());
        bottomPanel.add(btnXoaSP);

        panel.add(imagePanel, BorderLayout.WEST);
        panel.add(detailPanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setBackground(Color.WHITE);
        btnLuu = new CustomButton("Lưu hóa đơn");
        btnLuu.setPreferredSize(new Dimension(150, 35));
        btnLuu.addActionListener(e -> saveOrder());
        btnHuy = new CustomButton("Hủy");
        btnHuy.setPreferredSize(new Dimension(150, 35));
        btnHuy.addActionListener(e -> dispose());
        panel.add(btnLuu);
        panel.add(btnHuy);
        return panel;
    }

    private void displayProductDetails(String productId, String productName, String category, String price) {
        lblProductId.setText(productId);
        lblProductName.setText(productName);
        lblCategory.setText(category);
        lblPrice.setText(price + " VND");

        String img = productBUS.getProductImage(productId);
        if (img != null && !img.isEmpty()) {
            lblProductImage.setIcon(new ImageIcon(new ImageIcon("images/" + img).getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH)));
            lblProductImage.setText("");
        } else {
            lblProductImage.setIcon(null);
            lblProductImage.setText("Không có ảnh");
        }
    }

    private void updateQuantityInOrder() {
        try {
            int selectedRow = productsTable.getSelectedRow();
            if (selectedRow >= 0) {
                int oldQuantity = Integer.parseInt(orderTableModel.getValueAt(selectedRow, 3).toString());
                int oldPrice = Integer.parseInt(orderTableModel.getValueAt(selectedRow, 4).toString().replaceAll("[^0-9]", ""));
                int oldTotal = oldQuantity * oldPrice;

                int newQuantity = Integer.parseInt(txtQuantity.getText().trim());
                int newTotal = oldPrice * newQuantity;

                // Cập nhật lại dòng
                orderTableModel.setValueAt(newQuantity, selectedRow, 3);
                orderTableModel.setValueAt(formatCurrency(newTotal), selectedRow, 5);

                // Cập nhật tổng tiền
                totalAmount = totalAmount - oldTotal + newTotal;
                lblTongTien.setText(formatCurrency(totalAmount));

            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm trong giỏ hàng để sửa.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi sửa số lượng.");
        }
    }

    private void addProductToOrder() {
        try {
            String phone = txtSoDienThoai.getText().trim();
            if (phone.isEmpty() || phone.length() < 10) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại khách hàng hợp lệ trước khi thêm sản phẩm.");
                return;
            }

            int selectedRow = allProductsTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm từ danh sách.");
                return;
            }

            String quantityText = txtQuantity.getText().trim();
            if (quantityText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng.");
                return;
            }

            int quantity = Integer.parseInt(quantityText);
            if (quantity <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0.");
                return;
            }
            int tonKho = Integer.parseInt(productTableModel.getValueAt(selectedRow, 4).toString());
            if (quantity > tonKho) {
                JOptionPane.showMessageDialog(this, "Số lượng vượt quá tồn kho!");
                return;
            }

            String productId = lblProductId.getText();
            String productName = lblProductName.getText();
            String category = lblCategory.getText();
            int price = Integer.parseInt(lblPrice.getText().replaceAll("[^0-9]", ""));
            int total = price * quantity;
            orderTableModel.addRow(new Object[]{productId, productName, category, quantity, formatCurrency(price), formatCurrency(total)});
            totalAmount += total;
            lblTongTien.setText(formatCurrency(totalAmount));
            // Khóa thông tin khách
            txtSoDienThoai.setEditable(false);
            txtTenKhachHang.setEditable(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm sản phẩm.");
        }
    }

    private void removeSelectedProductFromOrder() {
        int selectedRow = productsTable.getSelectedRow();
        if (selectedRow >= 0) {
            int thanhTien = Integer.parseInt(orderTableModel.getValueAt(selectedRow, 5).toString().replaceAll("[^0-9]", ""));
            totalAmount -= thanhTien;
            lblTongTien.setText(formatCurrency(totalAmount));
            orderTableModel.removeRow(selectedRow);
            // Mở lại nhập thông tin khách nếu giỏ trống
            if (orderTableModel.getRowCount() == 0) {
                txtSoDienThoai.setEditable(true);
                txtTenKhachHang.setEditable(true);
            }
        }
    }


    private void loadAllProducts() {
        productTableModel.setRowCount(0);
        List<ProductDTO> list = ProductDAO.getAllProducts();
        for (ProductDTO p : list) {
            productTableModel.addRow(new Object[]{
                p.getProductID(),
                p.getProductName(),
                p.getTL(),
                p.getGia(),
                p.getSoluong()
            });
        }
    }

    private void loadOrderData() {
        if (currentOrder != null) {
            DetailSaleInvoiceBUS detailOrderBUS = new DetailSaleInvoiceBUS();
            List<DetailSaleInvoiceDTO> chiTietList = detailOrderBUS.getBySalesID(currentOrder.getId());
            for (DetailSaleInvoiceDTO ct : chiTietList) {
                ProductDTO sp = productBUS.getProductByID(ct.getProduct_id());
                if (sp != null) {
                    String tenSP = sp.getProductName();
                    String loai = sp.getTL();
                    int soLuong = ct.getQuantity();
                    double donGia = ct.getPrice();
                    double thanhTien = (double) (donGia * soLuong);
                    orderTableModel.addRow(new Object[]{
                        ct.getProduct_id(),
                        tenSP,
                        loai,
                        soLuong,
                        formatCurrency((int) donGia),
                        formatCurrency((int) thanhTien)
                    });
                    totalAmount += thanhTien;
                }
            }
            lblTongTien.setText(formatCurrency(totalAmount));
            // Hiển thị thông tin khách hàng từ currentOrder
            CustomerDTO kh = customerBUS.getById(currentOrder.getCustomerId());
            if (kh != null) {
                txtMaKhachHang.setText(kh.getId());
                txtTenKhachHang.setText(kh.getName());
                txtSoDienThoai.setText(kh.getPhone());
                txtTenKhachHang.setEditable(false);
                txtSoDienThoai.setEditable(false);
            }
        }
    }

    private void saveOrder() {
        if (currentAccount != null)
            System.out.println(currentAccount.getEmployeeID() + currentAccount.getUsername());
        else System.out.println("NV null");
        String orderID = lblMaHoaDon.getText();
        String maKH = txtMaKhachHang.getText().trim();
        String tenKH = txtTenKhachHang.getText().trim();
        String sdt = txtSoDienThoai.getText().trim();

        // 1. Nếu khách hàng chưa tồn tại, thêm vào database
        CustomerDTO existingCustomer = customerBUS.getByPhone(sdt);
        if (existingCustomer == null) {
            // Trường hợp khách mới => thêm vào
            CustomerDTO newCustomer = new CustomerDTO(maKH, tenKH, sdt, 0); // Có thể thêm email nếu có
            customerBUS.add(newCustomer);  // Bạn cần tạo hàm addCustomer trong BUS và DAO
        }

        // 2. Lưu hóa đơn
        SaleInvoiceDTO dto = new SaleInvoiceDTO();
        dto.setId(orderID);
        dto.setEmployeeId(currentAccount.getEmployeeID());
        dto.setCustomerId(maKH);
        dto.setTotalPrice(totalAmount);
        dto.setDate(LocalDate.now());

        orderBUS.add(dto);

        // Save detail invoice
        // Nếu hóa đơn đã tồn tại thì xóa chi tiết cũ
        // Nếu không thì thêm mới
        // DetailSaleInvoiceBUS detailOrderBUS = new DetailSaleInvoiceBUS();
        // detailOrderBUS.add(dto);


        // 3. Lưu chi tiết hóa đơn
        DetailSaleInvoiceBUS detailOrderBUS = new DetailSaleInvoiceBUS();

        for (int i = 0; i < orderTableModel.getRowCount(); i++) {
            DetailSaleInvoiceDTO detail = new DetailSaleInvoiceDTO();
            detail.setDetailSaleInvoiceID(getNextDetailOrderID());
            detail.setSale_id(orderID);
            detail.setProduct_id(orderTableModel.getValueAt(i, 0).toString());
            detail.setQuantity(Integer.parseInt(String.valueOf(orderTableModel.getValueAt(i, 3))));
            String priceStr = orderTableModel.getValueAt(i, 4).toString().replaceAll("[^0-9]", "");
            detail.setPrice(Double.parseDouble(priceStr));

            detailOrderBUS.add(detail);

            // 4. Lưu sản phẩm đã bán
            ProductSoldBUS productSoldBUS = new ProductSoldBUS();
            ProductDetailBUS productDetailBUS = new ProductDetailBUS();

            String productId = orderTableModel.getValueAt(i, 0).toString();
            int quantity = Integer.parseInt(orderTableModel.getValueAt(i, 3).toString());
            // Lấy danh sách chi tiết sản phẩm theo mã sản phẩm và số lượng sản phẩm

            List<ProductDetailDTO> productDetails = productDetailBUS.getProductDetailByProductID(productId);
            for (int j = 0; j < quantity; j++) {
                ProductDetailDTO productDetail = productDetails.get(i);
                ProductSoldDTO productSold = new ProductSoldDTO();
                productSold.setDetailSaleInvoiceID(detail.getDetailSaleInvoiceID());
                productSold.setProductId(productId);
                productSold.setSeries(productDetail.getSeries());
                productSoldBUS.add(productSold);
                productDetailBUS.delete(productDetail.getSeries());
            }
        }

        // 5. Cập nhật lại số lượng tồn kho
        for (int i = 0; i < orderTableModel.getRowCount(); i++) {
            String productId = orderTableModel.getValueAt(i, 0).toString();
            int quantity = Integer.parseInt(orderTableModel.getValueAt(i, 3).toString());
            ProductDTO product = productBUS.getProductByID(productId);
            if (product != null) {
                product.setSoluong(String.valueOf(Integer.parseInt(product.getSoluong()) - quantity));
                productBUS.updateProduct(product);
            }
        }

        JOptionPane.showMessageDialog(this, "Lưu hóa đơn thành công!");
        dispose();
    }

    private String formatCurrency(int amount) {
        return String.format("%,d VND", amount);
    }

    private String getNextDetailOrderID() {
        ArrayList<DetailSaleInvoiceDTO> detailOrderIDs = new DetailSaleInvoiceBUS().getAll();
        if (detailOrderIDs == null) {
            return "SID01";
        }
        String lastID = detailOrderIDs.get(detailOrderIDs.size() - 1).getDetailSaleInvoiceID();
        int number = Integer.parseInt(lastID.substring(3));
        return String.format("SID%02d", number + 1);
    }

    private String getNextOrderID() {
        ArrayList<SaleInvoiceDTO> orderIDs = orderBUS.getAll();
        if (orderIDs == null) {
            return "SI01";
        }
        String lastID = orderIDs.get(orderIDs.size() - 1).getId();
        int number = Integer.parseInt(lastID.substring(2));
        return String.format("SI%02d", number + 1);
    }

    private String getEmployeeNameByID(String employeeID) {
        EmployeeBUS empBUS = new EmployeeBUS();
        EmployeeDTO emp = empBUS.getEmployeeByID(employeeID);
        return emp != null ? emp.getFullName() : "Không tìm thấy";
    }

    private String getNextCustomerID() {
        ArrayList<CustomerDTO> customerIDs = customerBUS.getAll();
        if (customerIDs.isEmpty()) {
            return "C01";
        }
        String lastID = customerIDs.get(customerIDs.size() - 1).getId();
        int number = Integer.parseInt(lastID.substring(2));
        return String.format("C%02d", number + 1);
    }
}
