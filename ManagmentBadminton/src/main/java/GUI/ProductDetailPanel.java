package GUI;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import BUS.ImportInvoiceBUS;

import java.awt.*;
import java.awt.event.*;

public class ProductDetailPanel extends JPanel {
    private JLabel lblProductImage, lblProductId, lblProductName, lblSupplier, lblPrice, lblTotal;
    private JTextField txtQuantity;
    private CustomButton btnThemSP;

    public ProductDetailPanel(ActionListener addProductListener, ImportInvoiceBUS bus) {
        setLayout(new BorderLayout(15, 15));
        setBorder(new CompoundBorder(new TitledBorder("Thông tin sản phẩm đang chọn"), new EmptyBorder(10, 10, 10, 10)));
        setBackground(Color.WHITE);

        lblProductImage = new JLabel("", JLabel.CENTER);
        lblProductImage.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.LIGHT_GRAY, 1), new EmptyBorder(5, 5, 5, 5)));
        loadDefaultImage();

        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setPreferredSize(new Dimension(200, 200));
        imagePanel.setBackground(Color.WHITE);
        imagePanel.add(lblProductImage, BorderLayout.CENTER);

        JPanel detailPanel = new JPanel(new GridBagLayout());
        detailPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0; detailPanel.add(new JLabel("Mã sản phẩm:"), gbc);
        gbc.gridx = 1; lblProductId = new JLabel("Chọn sản phẩm từ danh sách"); detailPanel.add(lblProductId, gbc);

        gbc.gridx = 0; gbc.gridy = 1; detailPanel.add(new JLabel("Tên sản phẩm:"), gbc);
        gbc.gridx = 1; lblProductName = new JLabel(); detailPanel.add(lblProductName, gbc);

        gbc.gridx = 0; gbc.gridy = 2; detailPanel.add(new JLabel("Nhà cung cấp:"), gbc);
        gbc.gridx = 1; lblSupplier = new JLabel(); detailPanel.add(lblSupplier, gbc);

        gbc.gridx = 0; gbc.gridy = 3; detailPanel.add(new JLabel("Đơn giá:"), gbc);
        gbc.gridx = 1; lblPrice = new JLabel(); detailPanel.add(lblPrice, gbc);

        gbc.gridx = 0; gbc.gridy = 4; detailPanel.add(new JLabel("Số lượng:"), gbc);
        gbc.gridx = 1; txtQuantity = new JTextField(5); txtQuantity.setHorizontalAlignment(JTextField.RIGHT); detailPanel.add(txtQuantity, gbc);

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

    private void updateTotal(ImportInvoiceBUS bus) {
        lblTotal.setText(bus.calculateTotal(lblPrice.getText(), txtQuantity.getText()));
    }

    // Getters
    public JLabel getLblProductImage() { return lblProductImage; }
    public JLabel getLblProductId() { return lblProductId; }
    public JLabel getLblProductName() { return lblProductName; }
    public JLabel getLblSupplier() { return lblSupplier; }
    public JLabel getLblPrice() { return lblPrice; }
    public JTextField getTxtQuantity() { return txtQuantity; }
    public JLabel getLblTotal() { return lblTotal; }
}