package BUS;

import java.util.ArrayList;

import DAO.ImportInvoiceDAO;
import DAO.ImportInvoiceDetailDAO;
import DAO.ProductDAO;
import DAO.SupplierDAO;
import DTO.ImportInvoiceDTO;
import DTO.ImportInvoiceDetailDTO;
import DTO.ProductDTO;
import GUI.Utils;

public class ImportInvoiceBUS {
    private final ImportInvoiceDAO importDAO = new ImportInvoiceDAO();
    private final ImportInvoiceDetailDAO importDetailDAO = new ImportInvoiceDetailDAO();
    private final SupplierDAO supplierDAO = new SupplierDAO();
    private final ProductDAO productDAO = new ProductDAO();
    public static ArrayList<ImportInvoiceDTO> getAllImportInvoice() {
        return ImportInvoiceDAO.getAllImportInvoice();
    }

    public ImportInvoiceDTO getImportInvoiceByID(String id){
        return importDAO.getImportInvoiceByID(id);
    }
    
    public boolean insert(ImportInvoiceDTO importInvoice, ArrayList<Object[]> importDetails) {
        boolean result = true;
        if(importDAO.insert(importInvoice)){
            for (Object[] detail : importDetails) {
                System.out.println("Detail: " + detail[0] + ", " + detail[1] + ", " + detail[2] + ", " + detail[3] + ", " + detail[4]);
                String productID = (String) detail[0];
                int quantity = Integer.parseInt((String) detail[1]);
                String supplierID = (String) detail[2];
                double price = (Double) detail[3]; // không cần parse lại từ String
                double totalPrice = (Double) detail[4]; // không cần parse lại từ String
                ImportInvoiceDetailDTO importDetail = new ImportInvoiceDetailDTO(importInvoice.getImportID(), productID, supplierID, quantity, price, totalPrice);
                if (!importDetailDAO.insert(importDetail)) {
                    result = false;
                    break;
                }
            }
        } else {
            result = false;
        }
        return result;
    }

    public double calculateImportTotal(String importID){
        return importDAO.calculateImportTotal(importID);
    }

    public String generateNextImportID() {
        return importDAO.generateNextImportID();
    }

    public String getEmployeeNameByImportID(String id) {
        return importDAO.getEmployeeNameByImportID(id);
    }

    public String getSupplierNameByImportID(String id) {
        return importDAO.getSupplierNameByImportID(id);
    }

     // Gọi từ SuppliersDAO
     public String getSupplierIDByProduct(String productId) {
        return supplierDAO.getSupplierIDByProduct(productId);
    }

    public boolean updateProductQuantity(String productId, int quantity) {
        return productDAO.updateProductQuantity(productId, quantity);
    }

    // Gọi từ ProductDAO
    public Object[] getProductDetails(String productId) {
        ProductDTO product = ProductDAO.getProduct(productId);
        if (product != null) {
            return new Object[]{
                product.getProductID(),
                product.getProductName(),
                Utils.formatCurrency(Double.parseDouble(product.getGia())),
                product.getMaNCC(),
                product.getTL(),
                product.getAnh()
            };
        }
        return null;
    }

    // Gọi từ ProductDAO
    public ArrayList<Object[]> loadAllProducts() {
        ArrayList<Object[]> products = new ArrayList<>();
        ArrayList<ProductDTO> productList = ProductDAO.getAllProducts();
        for (ProductDTO product : productList) {
            products.add(new Object[]{
                product.getProductID(),
                product.getProductName(),
                Utils.formatCurrency(Double.parseDouble(product.getGia()))
            });
        }
        return products;
    }

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
