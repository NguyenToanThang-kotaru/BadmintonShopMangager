package DTO;

public class CustomerDTO {
    private String id;
    private String name;
    private String phone;
    private double spending;
    private int is_deleted;

    public CustomerDTO() {}

    public CustomerDTO(String id, String name, String phone, double spending) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.spending = spending;
        this.is_deleted = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getSpending() {
        return spending;
    }

    public void setSpending(double spending) {
        this.spending = spending;
    }

    // toString (tùy chọn)
    @Override
    public String toString() {
        return "CustomerDTO{" +
                "CustomerID='" + id + '\'' +
                ", fullName='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", totalSpending=" + spending +
                '}';
    }
}
