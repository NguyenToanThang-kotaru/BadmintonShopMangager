package BUS;

import java.util.List;

import javax.swing.JOptionPane;

import DAO.ProductDAO;
import DTO.ProductDTO;

public class ProductBUS {

    public List<ProductDTO> getAllProducts() {
        return ProductDAO.getAllProducts();
    }

    public void addProduct(ProductDTO product) {
        ProductDAO.addProduct(product);
    }

    public void updateProduct(ProductDTO product) {
        ProductDAO dao = new ProductDAO();
        dao.updateProduct(product);
    }

    public boolean validateProduct(ProductDTO product) {
        String productName = product.getProductName().trim();
        String gia = product.getGia().trim();
        String soluong = product.getSoluong().trim();

        // Không cho tên chỉ toàn số
        if (productName.matches("\\d+")) {
            JOptionPane.showMessageDialog(null,
                    "Tên sản phẩm không được chỉ chứa số.",
                    "Lỗi nhập liệu",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Không chứa ký tự đặc biệt (chỉ cho phép chữ cái, số và khoảng trắng)
        if (!productName.matches("^[\\p{L}0-9\\s+\\-\\.]+$")) {
            JOptionPane.showMessageDialog(null,
                    "Tên sản phẩm không được chứa ký tự đặc biệt.",
                    "Lỗi nhập liệu",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!gia.matches("^\\d+$")) {
            JOptionPane.showMessageDialog(null,
                    "Giá chỉ được chứa số.",
                    "Lỗi nhập liệu",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        List<ProductDTO> productList = ProductDAO.getAllProducts();
        for (ProductDTO p : productList) {
            if (p.getProductName().trim().equalsIgnoreCase(productName)
                    && (product.getProductID() == null || !product.getProductID().equals(p.getProductID()))) {
                JOptionPane.showMessageDialog(null,
                        "Tên sản phẩm đã tồn tại!",
                        "Lỗi nhập liệu",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        return true;
    }

    public boolean canDeleteProduct(ProductDTO product) {
        try {
            int quantity = Integer.parseInt(product.getSoluong());
            if (quantity > 0) {
                JOptionPane.showMessageDialog(null,
                        "Không thể xóa sản phẩm vì số lượng còn lớn hơn 0.",
                        "Xóa sản phẩm",
                        JOptionPane.WARNING_MESSAGE);
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "Dữ liệu số lượng sản phẩm không hợp lệ.",
                    "Lỗi dữ liệu",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    //Chỗ Tiến sử dụng để làm:
    public String getProductImage(String productID) {
        return ProductDAO.getProductImage(productID);
    }
    
    public ProductDTO getProductByID(String id) {
        ProductDAO dao = new ProductDAO();
        return dao.getProduct(id);
    }

    public String generateNewProductID() {
        return ProductDAO.generateNewProductID();
    }

    public void insert(ProductDTO product) {
        ProductDAO dao = new ProductDAO();
        dao.insert(product);
    }
}