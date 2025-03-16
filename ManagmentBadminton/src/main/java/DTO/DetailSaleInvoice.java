package DTO;

/**
 *
 * @author Tung Thien
 */

public class DetailSaleInvoice {
    private int detail_sale_id;
    private int sale_id;
    private int product_id;
    private int quantity;
    private double price;
    private double total;

    public DetailSaleInvoice() {
    }

    public DetailSaleInvoice(int detail_sale_id, int sale_id, int product_id, int quantity, double price, double total) {
        this.detail_sale_id = detail_sale_id;
        this.sale_id = sale_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
    }

    public int getDetail_sale_id() {
        return detail_sale_id;
    }

    public void setDetail_sale_id(int detail_sale_id) {
        this.detail_sale_id = detail_sale_id;
    }

    public int getSale_id() {
        return sale_id;
    }

    public void setSale_id(int sale_id) {
        this.sale_id = sale_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
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
        return total;
    }

    public void setTotalPrice(double price) {
        this.total = price;
    }
}
