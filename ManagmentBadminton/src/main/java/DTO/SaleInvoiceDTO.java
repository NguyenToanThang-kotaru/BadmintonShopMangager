package DTO;

import java.time.LocalDate;

public class SaleInvoiceDTO {
    private String id;
    private String customerId;
    private String employeeId;
    private LocalDate date;
    private int promotionId;
    private double totalPrice;

    public SaleInvoiceDTO(String id, String customerId, String employeeId, LocalDate date, int promotionId, double totalPrice) {
        this.id = id;
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.date = date;
        this.promotionId = promotionId;
        this.totalPrice = totalPrice;
    }

    public SaleInvoiceDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getPromotionId() {
        return promotionId;
    }
    public void setPromotionId(int promotionId) {
        this.promotionId = promotionId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
