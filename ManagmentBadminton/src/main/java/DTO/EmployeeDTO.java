package DTO;
import java.util.Date;

public class EmployeeDTO {
    private String employeeID;
    private String fullName;
    private String age;
    private String phone;
    private String email;
    private String address;
    private String gender;
    private Date startDate;

    // Constructor không tham số
    public EmployeeDTO() {}

    // Constructor đầy đủ
    public EmployeeDTO(String employeeID, String fullName, String age, String phone, String email,
                       String address, String gender, Date startDate) {
        this.employeeID = employeeID;
        this.fullName = fullName;
        this.age = age;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.gender = gender;
        this.startDate = startDate;
    }

    // Getter & Setter
    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    // toString (tùy chọn)
    @Override
    public String toString() {
        return "EmployeeDTO{" +
                "employeeID='" + employeeID + '\'' +
                ", fullName='" + fullName + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", gender='" + gender + '\'' +
                ", startDate=" + startDate +
                '}';
    }
}
