/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.util.ArrayList;

/**
 *
 * @author Thang Nguyen
 */
public class PermissionDTO {
    String ID;
    String Name;
    String nameUnsinged;
    String totalFunction;       
    String totalAccount;            
    ArrayList<FunctionActionDTO> Function;
    
    
    public PermissionDTO(String ID, String Name, String nameUnsinged,String slTK,String totalFunction, ArrayList<FunctionActionDTO> Function) {
        this.ID = ID;
        this.Name = Name;
        this.nameUnsinged = nameUnsinged;
        this.Function = Function;
        this.totalFunction = totalFunction;
        this.totalAccount = slTK;
    }

    public String getTotalFunction() {
        return totalFunction;
    }

    public String getTotalAccount() {
        return totalAccount;
    }

    public PermissionDTO() {}

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getnameUnsinged() {
        return nameUnsinged;
    }

    public void setnameUnsinged(String nameUnsinged) {
        this.nameUnsinged = nameUnsinged;
    }

    public ArrayList<FunctionActionDTO> getFunction() {
        return Function;
    }
    

    public void setTotalFunction(String totalFunction) {
        this.totalFunction = totalFunction;
    }

    public void setTotalAccount(String totalAccount) {
        this.totalAccount = totalAccount;
    }

    public void setFunction(ArrayList<FunctionActionDTO> Function) {
        this.Function = Function;
    }
    
}
