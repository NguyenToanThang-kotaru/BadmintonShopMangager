/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Thang Nguyen
 */
public class AccountDTO {

    private String username;
    private String password;
    private String employeeID;
    private String rankID;

    public AccountDTO() {
        username = "";
        password = "";
        employeeID = "";
        rankID = "";
    }

    public AccountDTO(String username, String password, String employeeID, String rankID) {
        this.username = username;
        this.password = password;
        this.employeeID = employeeID;
        this.rankID = rankID;
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

    public String getRankID() {
        return rankID;
    }

    public void setRankID(String rankID) {
        this.rankID = rankID;
    }

}
