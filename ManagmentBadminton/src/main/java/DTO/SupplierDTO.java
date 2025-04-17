package DTO;

public class SupplierDTO {
    private String supplierID;
    private String supplierName;
    private String phone;
    private String email;
    private String address;
    private String status;

    public SupplierDTO() {
    }

    public SupplierDTO(String supplierID, String supplierName, String phone, String email, String address, String status) {
        this.supplierID = supplierID;
        this.supplierName = supplierName;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.status = status;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }
    public String getStatus() {
        return status;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SupplierDTO{" + "supplierID=" + supplierID + ", supplierName=" + supplierName + ", phone=" + phone + ", email=" + email + ", address=" + address + ", status=" + status + '}';
    }
    
}
