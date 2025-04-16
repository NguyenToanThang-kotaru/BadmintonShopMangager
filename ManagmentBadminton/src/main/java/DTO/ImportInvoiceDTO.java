package DTO;

import java.sql.Date;

public class ImportInvoiceDTO {
    private String importID;
    private String employeeID;
    private String supplierID;
    private Date date;
    private double totalPrice;

    public ImportInvoiceDTO() {
    }

    public ImportInvoiceDTO(String importID, String employeeID, String supplierID, Date date, double totalPrice) {
        this.importID = importID;
        this.employeeID = employeeID;
        this.supplierID = supplierID;
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

    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
}
