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
public class FunctionActionDTO {
    String ID;
    String Name;
    String nameUnsigned;
    ArrayList<ActionDTO> action;

    public FunctionActionDTO(){
        
    }
    
    public FunctionActionDTO(String ID, String Name, String nameUnsigned, ArrayList<ActionDTO> action) {
        this.ID = ID;
        this.Name = Name;
        this.nameUnsigned = nameUnsigned;
        this.action = action;
    }
    
    public String getNameUnsigned() {
        return nameUnsigned;
    }

    

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

    public ArrayList<ActionDTO> getAction() {
        return action;
    }
    
    public void setNameUnsigned(String nameUnsigned) {
        this.nameUnsigned = nameUnsigned;
    }

    public void setAction(ArrayList<ActionDTO> action) {
        this.action = action;
    }
}
