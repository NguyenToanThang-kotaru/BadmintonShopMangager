package DTO;
public class ProductSoldDTO {
    private String series;
    private String detailSaleInvoiceID;

    // Constructor
    public ProductSoldDTO(String series, String productId, String detailSaleInvoiceID) {
        this.series = series;
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

    // Setters
    public void setSeries(String series) {
        this.series = series;
    }

    public void setDetailSaleInvoiceID(String detailSaleInvoiceID) {
        this.detailSaleInvoiceID = detailSaleInvoiceID;
    }
}
