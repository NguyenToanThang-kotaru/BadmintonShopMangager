package GUI;

import DTO.ProductDTO;
import DAO.ProductDAO;
import BUS.ProductBUS;
import BUS.SupplierBUS;
import DAO.SupplierDAO;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javax.swing.*;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class GUI_AddFormProduct extends JDialog {

    private JTextField nameField, priceField, maNCCField, soluongField, tsktField;
    private CustomCombobox TLField, NCCField;
    private CustomButton saveButton, cancelButton, btnChooseImage;
    private JLabel imageLabel;
    private File selectedImageFile = null;
    private GUI_Product parentGUI;

    public GUI_AddFormProduct(JFrame parent, GUI_Product parentGUI) {
        super(parent, "Thêm sản phẩm", true);
        this.parentGUI = parentGUI;
        setSize(400, 560);
        setLayout(new GridBagLayout());
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Các trường nhập liệu
        addComponent("Tên sản phẩm:", nameField = new JTextField(20), gbc, 0);
        addComponent("Giá:", priceField = new JTextField(20), gbc, 1);
//        addComponent("Mã NCC:", maNCCField = new JTextField(20), gbc, 2);
//        addComponent("Số lượng:", soluongField = new JTextField(20), gbc, 3);
        addComponent("Thông số kỹ thuật:", tsktField = new JTextField(20), gbc, 4);

        // Loại sản phẩm
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Tên loại:"), gbc);
        gbc.gridx = 1;
        // Lấy danh sách các loại sản phẩm từ database
        String[] categoryNames = ProductBUS.getAllCategoryNames().toArray(new String[0]);
        TLField = new CustomCombobox(categoryNames);
        add(TLField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        add(new JLabel("Nhà cung cấp:"), gbc);
        gbc.gridx = 1;
        String[] NCCNames = SupplierBUS.getAllNCCNames().toArray(new String[0]);
        NCCField = new CustomCombobox(NCCNames);
        add(NCCField, gbc);

        // Ảnh sản phẩm
        gbc.gridx = 0;
        gbc.gridy = 7;
        add(new JLabel("Ảnh sản phẩm:"), gbc);
        gbc.gridx = 1;
        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(200, 200));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        add(imageLabel, gbc);

        // Nút chọn ảnh
        gbc.gridy = 8;
        btnChooseImage = new CustomButton("Chọn ảnh");
        btnChooseImage.addActionListener(e -> chooseImage());
        add(btnChooseImage, gbc);

        // Các nút Save và Cancel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        saveButton = new CustomButton("Thêm sản phẩm");
        cancelButton = new CustomButton("Hủy");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridy = 9;
        gbc.gridx = 1;
        add(buttonPanel, gbc);

        // Hành động cho các nút
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
        String soluong = "0";
        String tskt = tsktField.getText().trim();
        String tenLoai = (String) TLField.getSelectedItem();
        String tenNCC = (String) NCCField.getSelectedItem();
        String anh = null;

        if (name.isEmpty() || price.isEmpty() || tskt.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Nếu đã chọn ảnh, lưu ảnh
        if (selectedImageFile != null) {
            anh = saveImage(selectedImageFile);
        }

        // Tạo sản phẩm mới, truyền vào productID là null vì đây là sản phẩm mới (sẽ tự động sinh ID khi thêm vào DB)
        ProductDTO newProduct = new ProductDTO(null, name, price, soluong, null, null, tenLoai, anh, tenNCC);
        
          ProductBUS bus = new ProductBUS();
        if (!bus.validateProduct(newProduct)) {
            return; // Dừng lại nếu không hợp lệ
        }
        
        boolean success = ProductBUS.addProduct(newProduct);
        if (success) {
            JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi thêm sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        parentGUI.loadProductData();

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
