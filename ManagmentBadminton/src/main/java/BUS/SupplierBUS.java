package BUS;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import DAO.ProductDAO;
import DAO.SupplierDAO;
import DTO.ProductDTO;
import DTO.SupplierDTO;

public class SupplierBUS {

    private final SupplierDAO supplierDAO = new SupplierDAO();
    private ArrayList<SupplierDTO> supplierList = new ArrayList<>();

    public SupplierBUS() {
        supplierList = SupplierDAO.getAllSupplier();
    }

    public static ArrayList<SupplierDTO> getAllSupplier(){
        return SupplierDAO.getAllSupplier();
    }

    public static ArrayList<String> getAllNCCNames() {
        return SupplierDAO.getAllNCCNames();
    }

    public boolean insert(SupplierDTO supplier) {
        if (supplierDAO.insert(supplier)) {
            supplierList.add(supplier);
            return true;
        }
        return false;
    }

    public boolean update(SupplierDTO supplier) {
        if (supplier == null) {
            return false;
        }
        if (supplierDAO.update(supplier)) {
            supplierList.set(getIndex(supplier.getSupplierID()), supplier);
            return true;
        }
        return false;
    }

    public boolean remove(SupplierDTO supplier) {
        if (supplier == null) {
            return false;
        }
        if (supplierDAO.remove(supplier)) {
            supplierList.remove(supplier);
            return true;
        }
        return false;
    }

    public boolean remove(String id) {
        return remove(getSupplierByID(id));
    }

    public int getIndex(String id) {
        for (int i = 0; i < supplierList.size(); i++) {
            if (supplierList.get(i).getSupplierID().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    public static SupplierDTO getSupplierByID(String id){
        return SupplierDAO.getSupplierByID(id);
    }

    public String generateSupplierID(){
        return supplierDAO.generateSupplierID();
    }

        public ArrayList<ProductDTO> getProductsBySupplier(String supplierID) {
        ArrayList<ProductDTO> allProducts = ProductDAO.getAllProducts();
        ArrayList<ProductDTO> supplierProducts = new ArrayList<>();
        for (ProductDTO product : allProducts) {
            if (product.getMaNCC().equals(supplierID)) {
                supplierProducts.add(product);
            }
        }
        return supplierProducts;
    }
    public boolean validateSupplier(SupplierDTO supplier) {
        String supplierName = supplier.getSupplierName().trim();
        String phoneNumber = supplier.getPhone().trim();
        String email = supplier.getEmail().trim();

        // Không cho tên chỉ toàn số
        if (supplierName.matches("\\d+")) {
            JOptionPane.showMessageDialog(null,
                    "Tên nhà cung cấp không được chỉ chứa số.",
                    "Lỗi nhập liệu",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Không chứa ký tự đặc biệt (chỉ cho phép chữ cái, số và khoảng trắng)
        if (!supplierName.matches("^[\\p{L}0-9\\s+\\-\\.]+$")) {
            JOptionPane.showMessageDialog(null,
                    "Tên nhà cung cấp không được chứa ký tự đặc biệt.",
                    "Lỗi nhập liệu",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Tên không được trùng
        if (supplierDAO.isNameExists(supplierName)) {
            JOptionPane.showMessageDialog(null,
                    "Tên nhà cung cấp đã tồn tại!",
                    "Lỗi nhập liệu",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra số điện thoại 10 số và bắt đầu bằng số 0
        if (!phoneNumber.matches("^0\\d{9}$")) {
            JOptionPane.showMessageDialog(null,
                    "Số điện thoại không hợp lệ!",
                    "Lỗi nhập liệu",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if( supplierDAO.isPhoneExists(phoneNumber)) {
            JOptionPane.showMessageDialog(null,
                    "Số điện thoại đã tồn tại!",
                    "Lỗi nhập liệu",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra email
        if (!email.matches("^[\\w-\\.]+@[\\w-]+\\.[a-z]{2,4}$")) {
            JOptionPane.showMessageDialog(null,
                    "Email không hợp lệ!",
                    "Lỗi nhập liệu",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (supplierDAO.isEmailExists(email)) {
            JOptionPane.showMessageDialog(null,
                    "Email đã tồn tại!",
                    "Lỗi nhập liệu",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
    public boolean validateSupplierUpdate(SupplierDTO supplier) {
        String supplierID = supplier.getSupplierID().trim();
        String supplierName = supplier.getSupplierName().trim();
        String phoneNumber = supplier.getPhone().trim();
        String email = supplier.getEmail().trim();

        // Không cho tên chỉ toàn số
        if (supplierName.matches("\\d+")) {
            JOptionPane.showMessageDialog(null,
                    "Tên nhà cung cấp không được chỉ chứa số.",
                    "Lỗi nhập liệu",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Không chứa ký tự đặc biệt (chỉ cho phép chữ cái, số và khoảng trắng)
        if (!supplierName.matches("^[\\p{L}0-9\\s+\\-\\.]+$")) {
            JOptionPane.showMessageDialog(null,
                    "Tên nhà cung cấp không được chứa ký tự đặc biệt.",
                    "Lỗi nhập liệu",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Tên không được trùng ngoại trừ tên hiện tại
        if (supplierDAO.isNameExistsUpdate(supplierName, supplierID)) {
            JOptionPane.showMessageDialog(null,
                    "Tên nhà cung cấp đã tồn tại!",
                    "Lỗi nhập liệu",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra số điện thoại 10 số và bắt đầu bằng số 0
        if (!phoneNumber.matches("^0\\d{9}$")) {
            JOptionPane.showMessageDialog(null,
                    "Số điện thoại không hợp lệ!",
                    "Lỗi nhập liệu",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if( supplierDAO.isPhoneExistsUpdate(phoneNumber, supplierID)) {
            JOptionPane.showMessageDialog(null,
                    "Số điện thoại đã tồn tại!",
                    "Lỗi nhập liệu",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra email
        if (!email.matches("^[\\w-\\.]+@[\\w-]+\\.[a-z]{2,4}$")) {
            JOptionPane.showMessageDialog(null,
                    "Email không hợp lệ!",
                    "Lỗi nhập liệu",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (supplierDAO.isEmailExistsUpdate(email, supplierID)) {
            JOptionPane.showMessageDialog(null,
                    "Email đã tồn tại!",
                    "Lỗi nhập liệu",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
}
