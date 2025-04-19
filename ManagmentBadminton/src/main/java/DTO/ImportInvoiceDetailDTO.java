package DTO;

public class ImportInvoiceDetailDTO {
    private String importID;
    private String productID;
    private String supplierID;
    private int Quantity;
    private double price;
    private double totalPrice;

    public ImportInvoiceDetailDTO() {
    }

    public ImportInvoiceDetailDTO(String importID, String productID, String supplierID, int Quantity, double price, double totalPrice) {
        this.importID = importID;
        this.productID = productID;
        this.supplierID = supplierID;
        this.Quantity = Quantity;
        this.price = price;
        this.totalPrice = totalPrice;
    }

    public String getImportID() {
        return importID;
    }

    public void setImportID(String importID) {
        this.importID = importID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }
    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    
}
