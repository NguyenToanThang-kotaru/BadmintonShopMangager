package DTO.Statistics;

public class ProductStatisticsDTO {

    private String id_product;
    private String name_product;
    private int inventoryStartsMonth;
    private int importsInMonth;
    private int exportInMonth;
    private int inventoryEndMonth;

    public ProductStatisticsDTO() {
    }

    public ProductStatisticsDTO(String id_product, String name_product, int inventoryStartsMonth, int importsInMonth, int exportInMonth, int inventoryEndMonth) {
        this.id_product = id_product;
        this.name_product = name_product;
        this.inventoryStartsMonth = inventoryStartsMonth;
        this.importsInMonth = importsInMonth;
        this.exportInMonth = exportInMonth;
        this.inventoryEndMonth = inventoryEndMonth;
    }

    public String getId_product() {
        return id_product;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public String getName_product() {
        return name_product;
    }

    public void setName_product(String name_product) {
        this.name_product = name_product;
    }

    public int getInventoryStartsMonth() {
        return inventoryStartsMonth;
    }

    public void setInventoryStartsMonth(int inventoryStartsMonth) {
        this.inventoryStartsMonth = inventoryStartsMonth;
    }

    public int getImportsInMonth() {
        return importsInMonth;
    }

    public void setImportsInMonth(int importsInMonth) {
        this.importsInMonth = importsInMonth;
    }

    public int getExportInMonth() {
        return exportInMonth;
    }

    public void setExportInMonth(int exportInMonth) {
        this.exportInMonth = exportInMonth;
    }

    public int getInventoryEndMonth() {
        return inventoryEndMonth;
    }

    public void setInventoryEndMonth(int inventoryEndMonth) {
        this.inventoryEndMonth = inventoryEndMonth;
    }
}
