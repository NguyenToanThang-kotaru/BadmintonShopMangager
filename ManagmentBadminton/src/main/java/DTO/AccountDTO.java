package DTO;

public class AccountDTO {

    private String username;
    private String password;
    private String employeeID;
    private String fullName;  // Thêm trường fullName
    private PermissionDTO permission;

    public AccountDTO() {
    }

    public AccountDTO(String username, String password, String employeeID, String fullName, PermissionDTO permission) {
        this.username = username;
        this.password = password;
        this.employeeID = employeeID;
        this.fullName = fullName;
        this.permission = permission;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getFullName() {  // Getter cho fullName
        return fullName;
    }

    public void setFullName(String fullName) {  // Setter cho fullName
        this.fullName = fullName;
    }

    public PermissionDTO getRankID() {
        return permission;
    }

    public void setRankID(PermissionDTO permission) {
        this.permission = permission;
    }

    public PermissionDTO getPermission() {
        return permission;
    }

    public void setPermission(PermissionDTO permission) {
        this.permission = permission;
    }
    
    
}