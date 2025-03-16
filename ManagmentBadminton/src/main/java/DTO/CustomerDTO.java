package DTO;

public class CustomerDTO {
    private String customerID;
    private String fullname;
    private String phone;
    private double totalSpending;

    public CustomerDTO() {}

    public CustomerDTO(String customerID, String fullname, String phone, double totalSpending) {
        this.customerID = customerID;
        this.fullname = fullname;
        this.phone = phone;
        this.totalSpending = totalSpending;
    }

    public String getCustomerID() {
        return this.customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getFullname() {
        return this.fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getTotalSpending() {
        return this.totalSpending;
    }

    public void setTotalSpending(double spending) {
        this.totalSpending = spending;
    }
}
