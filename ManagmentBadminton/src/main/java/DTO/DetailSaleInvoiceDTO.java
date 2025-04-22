package DTO;

/**
 *
 * @author Tung Thien
 */

public class DetailSaleInvoiceDTO {
    private String DetailSaleInvoiceID;
    private String sale_id;
    private String product_id;
    private int quantity;
    private double price;
    private double total_price;

    public DetailSaleInvoiceDTO() {
    }

    public DetailSaleInvoiceDTO(String DetailSaleInvoiceID, String sale_id, String product_id, int quantity, double price, double total_price) {
        this.DetailSaleInvoiceID = DetailSaleInvoiceID;
        this.sale_id = sale_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.price = price;
        this.total_price = total_price;
    }

    public String getDetailSaleInvoiceID() {
        return DetailSaleInvoiceID;
    }

    public void setDetailSaleInvoiceID(String detailSaleInvoiceID) {
        DetailSaleInvoiceID = detailSaleInvoiceID;
    }

    public String getSale_id() {
        return sale_id;
    }

    public void setSale_id(String sale_id) {
        this.sale_id = sale_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalPrice() {
        return total_price;
    }

    public void setTotalPrice(double total_price) {
        this.total_price = total_price;
    }
}
