package DTO;

/**
 *
 * @author Tung Thien
 */

import java.time.LocalDate;

public class SaleInvoiceDTO {
    private int sale_id;
    private int employeeID;
    private int customerID;
    private LocalDate date_created;
    private float totalPrice;

    public SaleInvoiceDTO(int sale_id, int employeeID, int customerID, LocalDate date_created, float totalPrice) {
        this.sale_id = sale_id;
        this.employeeID = employeeID;
        this.customerID = customerID;
        this.date_created = date_created;
        this.totalPrice = totalPrice;
    }

    public int getSaleID() {
        return this.sale_id;
    }

    public void setSaleID(int sale_id) {
        this.sale_id = sale_id;
    }

    public int getEmployeeID() {
        return this.employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public int getCustomerID() {
        return this.customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public LocalDate getDateCreated() {
        return this.date_created;
    }

    public void setDateCreated(LocalDate date) {
        this.date_created = date;
    }

    public float getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(float price) {
        this.totalPrice = price;
    }
}