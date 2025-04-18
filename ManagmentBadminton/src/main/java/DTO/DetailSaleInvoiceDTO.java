package DTO;

/**
 *
 * @author Tung Thien
 */

public class DetailSaleInvoiceDTO {
    private String detail_sale_id;
    private String sale_id;
    private String product_id;
    private int quantity;
    private double price;

    public DetailSaleInvoiceDTO() {
    }

    public DetailSaleInvoiceDTO(String detail_sale_id, String sale_id, String product_id, int quantity, double price) {
        this.detail_sale_id = detail_sale_id;
        this.sale_id = sale_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.price = price;
    }

    public String getDetail_sale_id() {
        return detail_sale_id;
    }

    public void setDetail_sale_id(String detail_sale_id) {
        this.detail_sale_id = detail_sale_id;
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
}
