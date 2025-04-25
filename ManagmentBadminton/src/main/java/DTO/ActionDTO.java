/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Thang Nguyen
 */
public class ActionDTO {
    String ID;
    String Name;
    String vnName;

    public ActionDTO(String ID, String Name, String vnName) {
        this.ID = ID;
        this.Name = Name;
        this.vnName = vnName;
    }
    public ActionDTO(){
        
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

    public String getVnName() {
        return vnName;
    }

    public void setVnName(String vnName) {
        this.vnName = vnName;
    }
    
}
