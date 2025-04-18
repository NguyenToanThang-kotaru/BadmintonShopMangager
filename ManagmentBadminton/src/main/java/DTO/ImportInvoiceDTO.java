package DTO;

public class ImportInvoiceDTO {
    private String importID;
    private String employeeID;
    private String date;
    private double totalPrice;

    public ImportInvoiceDTO() {
    }


    public ImportInvoiceDTO(String importID, String employeeID,  String date, double totalPrice) {
        this.importID = importID;
        this.employeeID = employeeID;
        this.date = date;
        this.totalPrice = totalPrice;
    }

    public String getImportID() {
        return importID;
    }

    public void setImportID(String importID) {
        this.importID = importID;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
}
