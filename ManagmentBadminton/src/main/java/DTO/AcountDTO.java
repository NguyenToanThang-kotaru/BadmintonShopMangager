/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Thang Nguyen
 */
public class AcountDTO {
    private String username;
    private String password;
    private String employeeID;
    private String rankID;
    
    public AcountDTO(){
        username="";
        password="";
        employeeID="";
        rankID="";
    }
    public AcountDTO(String username, String password, String employeeID, String rankID){
        this.username = username;
        this.password = password;
        this.employeeID = employeeID;
        this.rankID = rankID;
    }
    
}
