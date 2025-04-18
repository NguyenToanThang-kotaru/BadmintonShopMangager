package BUS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Connection.DatabaseConnection;
import DAO.ImportInvoiceDAO;
import DTO.ImportInvoiceDTO;
import GUI.Utils;

public class ImportInvoiceBUS {
    private final ImportInvoiceDAO importDetailDAO = new ImportInvoiceDAO();
    public static ArrayList<ImportInvoiceDTO> getAllImportInvoice() {
        return ImportInvoiceDAO.getAllImportInvoice();
    }

    public ImportInvoiceDTO getImportInvoiceByID(String id){
        return importDetailDAO.getImportInvoiceByID(id);
    }
    
    public boolean insert(ImportInvoiceDTO importInvoice){
        return importDetailDAO.insert(importInvoice);
    }

    public double calculateImportTotal(String importID){
        return importDetailDAO.calculateImportTotal(importID);
    }

    public String generateNextImportID() {
        return importDetailDAO.generateNextImportID();
    }

    public String getEmployeeNameByImportID(String id) {
        return importDetailDAO.getEmployeeNameByImportID(id);
    }

    public String getSupplierNameByImportID(String id) {
        return importDetailDAO.getSupplierNameByImportID(id);
    }

    // Gọi từ ProductDAO
    // public ArrayList<Object[]> loadAllProducts() {
    //     List<Object[]> products = new ArrayList<>();
    //     ArrayList<ProductDTO> productList = productDAO.getAllProducts();
    //     for (ProductDTO product : productList) {
    //         products.add(new Object[]{
    //             product.getProductID(),
    //             product.getProductName(),
    //             Utils.formatCurrency(Integer.parseInt(product.getGia()))
    //         });
    //     }
    //     return products;
    // }

    // Xác thực sản phẩm để thêm
    public String validateProductToAdd(String productId, String quantityText) {
        if (productId == null || productId.isEmpty() || productId.equals("Chọn sản phẩm từ danh sách")) {
            return "Vui lòng chọn một sản phẩm từ danh sách";
        }
    
        if (quantityText == null || quantityText.trim().isEmpty()) {
            return "Số lượng không được để trống";
        }
    
        try {
            int quantity = Integer.parseInt(quantityText);
            if (quantity <= 0) {
                return "Số lượng phải là số nguyên dương lớn hơn 0";
            }
        } catch (NumberFormatException e) {
            return "Số lượng phải là một số nguyên hợp lệ";
        }
    
        return null; // Không có lỗi
    }

    // Tính tổng tiền
    public String calculateTotal(String priceText, String quantityText) {
        try {
            double price = Double.parseDouble(priceText.replaceAll("[^0-9]", ""));
            int quantity = quantityText.isEmpty() ? 0 : Integer.parseInt(quantityText);
            return Utils.formatCurrency(price * quantity);
        } catch (NumberFormatException e) {
            return "0";
        }
    }
}
