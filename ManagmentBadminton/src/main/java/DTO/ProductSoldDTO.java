package DTO;
public class ProductSoldDTO {
    private String series;
    private String productId;
    private String detailSaleInvoiceID;

    // Constructor
    public ProductSoldDTO(String series, String productId, String detailSaleInvoiceID) {
        this.series = series;
        this.productId = productId;
        this.detailSaleInvoiceID = detailSaleInvoiceID;
    }

    public ProductSoldDTO() {}

    // Getters
    public String getSeries() {
        return series;
    }

    public String getDetailSaleInvoiceID() {
        return detailSaleInvoiceID;
    }

    public String getProductId() {
        return productId;
    }

    // Setters
    public void setSeries(String series) {
        this.series = series;
    }

    public void setProductId(String id) {
        this.productId = id;
    }

    public void setDetailSaleInvoiceID(String detailSaleInvoiceID) {
        this.detailSaleInvoiceID = detailSaleInvoiceID;
    }
}