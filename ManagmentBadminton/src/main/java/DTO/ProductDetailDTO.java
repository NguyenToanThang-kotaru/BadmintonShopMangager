package DTO;

public class ProductDetailDTO {
    private String series;
    private String productID;
    private String importDate;
    private String status;

    public ProductDetailDTO() {
    }

    public ProductDetailDTO(String series, String productID, String importDate, String status) {
        this.series = series;
        this.productID = productID;
        this.importDate = importDate;
        this.status = status;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getImportDate() {
        return importDate;
    }

    public void setImportDate(String importDate) {
        this.importDate = importDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
