package GUI;

import DTO.ProductDTO;
import DAO.ProductDAO;
import BUS.ProductBUS;
import DAO.SupplierDAO;    
import java.awt.*;
import java.io.File;
import java.nio.file.Files; 
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import javax.swing.*;   
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class GUI_Form_FixProduct extends JDialog {

    private JTextField nameField, priceField, soluongField;
    private CustomCombobox TLField, NCCField;
    private CustomButton saveButton, cancelButton, btnChooseImage;
    private JLabel imageLabel;
    private File selectedImageFile = null;
    private ProductDTO product;
    private GUI_Product parentGUI;

    public GUI_Form_FixProduct(JFrame parent, GUI_Product parentGUI, ProductDTO product) {
        super(parent, "Sửa sản phẩm", true);
        this.parentGUI = parentGUI;
        this.product = product;
        setSize(400, 560);      
        setLayout(new GridBagLayout());
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Mã sản phẩm: "), gbc);
        gbc.gridx = 1;
        JLabel ProductID = new JLabel(String.valueOf(product.getProductID()));
        add(ProductID, gbc);

        addComponent("Tên sản phẩm:", nameField = new JTextField(20), gbc, 1);
        nameField.setText(product.getProductName());
        addComponent("Giá:", priceField = new JTextField(20), gbc, 2);
        priceField.setText(String.valueOf(product.getGia()));
//        addComponent("Mã NCC:", maNCCField = new JTextField(20), gbc, 3);
//        maNCCField.setText(String.valueOf(product.getMaNCC()));
//        addComponent("Số lượng:", soluongField = new JTextField(20), gbc, 4);
//        soluongField.setText(String.valueOf(product.getSoluong()));
//        addComponent("Thông số kỹ thuật:", tsktField = new JTextField(20), gbc, 5);
//        tsktField.setText(product.getTSKT());

        gbc.gridx = 0;
        gbc.gridy = 6;
        add(new JLabel("Tên loại:"), gbc);
        gbc.gridx = 1;
        ArrayList<String> categoryList = ProductDAO.getAllCategoryNames();
        String[] categoryNames = categoryList.toArray(new String[0]);
        TLField = new CustomCombobox(categoryNames);
        TLField.setSelectedItem(product.getTL());
        add(TLField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        add(new JLabel("Nhà cung cấp:"), gbc);
        gbc.gridx = 1;
        ArrayList<String> NCCList = SupplierDAO.getAllNCCNames();
        String[] NCCNames = NCCList.toArray(new String[0]);
        NCCField = new CustomCombobox(NCCNames);
        NCCField.setSelectedItem(product.gettenNCC());
        add(NCCField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        add(new JLabel("Ảnh sản phẩm:"), gbc);

        gbc.gridx = 1;
        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(200, 200));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        add(imageLabel, gbc);

        if (product.getAnh() != null && !product.getAnh().isEmpty()) {
            displayImage("images/" + product.getAnh());
        }

        gbc.gridy = 9;
        gbc.gridx = 1;
        btnChooseImage = new CustomButton("Chọn ảnh");
        btnChooseImage.addActionListener(e -> chooseImage());
        add(btnChooseImage, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        saveButton = new CustomButton("Cập Nhật");
        cancelButton = new CustomButton("Hủy");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridy = 10;
        gbc.gridx = 1;
        add(buttonPanel, gbc);

        cancelButton.addActionListener(e -> dispose());
        saveButton.addActionListener(e -> saveProduct());
    }

    private void chooseImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn ảnh sản phẩm");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Hình ảnh", "jpg", "png", "jpeg"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedImageFile = fileChooser.getSelectedFile();
            displayImage(selectedImageFile.getAbsolutePath());
        }
    }

    private void displayImage(String imagePath) {
        if (imagePath != null && !imagePath.isEmpty()) {
            File imageFile = new File(imagePath);

            // Nếu ảnh không tồn tại trong đường dẫn tuyệt đối, thử lấy từ thư mục "images"
            if (!imageFile.exists()) {
                imageFile = new File("images/" + imagePath);
            }

            if (imageFile.exists()) {
                // Thay vì lấy width và height của imageLabel, sử dụng kích thước cố định
                ImageIcon imageIcon = new ImageIcon(new ImageIcon(imageFile.getAbsolutePath()).getImage()
                        .getScaledInstance(230, 200, Image.SCALE_SMOOTH)); // Sử dụng kích thước cố định
                imageLabel.setIcon(imageIcon);
            } else {
                System.out.println("Không tìm thấy ảnh: " + imageFile.getAbsolutePath());
            }
        }
    }

    private void saveProduct() {
        String name = nameField.getText().trim();
        String price = priceField.getText().trim();
//        String soluong = soluongField.getText().trim();
//        String tskt = tsktField.getText().trim();
        String tenLoai = (String) TLField.getSelectedItem();
        String tenNCC = (String) NCCField.getSelectedItem();
        String anh = product.getAnh();

        if (name.isEmpty() || price.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (selectedImageFile != null) {
            anh = saveImage(selectedImageFile);
        }

        product.setProductName(name);
        product.setGia(price);
        product.settenNCC(tenNCC);
//        product.setSoluong(soluong);
//        product.setTSKT(tskt);
        product.setTL(tenLoai);
        product.setAnh(anh);

        ProductBUS bus = new ProductBUS();
        if (!bus.validateProduct(product)) {
            return; // Nếu không hợp lệ thì không tiếp tục cập nhật
        }

        ProductDAO.updateProduct(product);
        JOptionPane.showMessageDialog(this, "Cập nhật sản phẩm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        parentGUI.loadProductData();
        dispose();
    }

    private String saveImage(File imageFile) {
        String imageDir = System.getProperty("user.dir") + "/images";
        File dir = new File(imageDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String newImageName = System.currentTimeMillis() + "_" + imageFile.getName();
        File newImageFile = new File(imageDir, newImageName);

        try {
            Files.copy(imageFile.toPath(), newImageFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return newImageName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void addComponent(String label, JComponent component, GridBagConstraints gbc, int gridy) {
        gbc.gridx = 0;
        gbc.gridy = gridy;
        add(new JLabel(label), gbc);
        gbc.gridx = 1;
        add(component, gbc);
    }

}
