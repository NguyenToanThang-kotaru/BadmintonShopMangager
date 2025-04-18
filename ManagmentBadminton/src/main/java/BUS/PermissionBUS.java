/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DTO.PermissionDTO;
import DAO.PermissionDAO;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 *
 * @author Thang Nguyen
 */
public class PermissionBUS {
    public static ArrayList<PermissionDTO> getAllPermissions(){
        return PermissionDAO.getAllPermissions();
    }
    public static PermissionDTO getPermissionByName(String Name) {
        return PermissionDAO.getPermissionByName(Name);
    }
    public static Boolean delete_Permission(String rankID){
        return PermissionDAO.delete_Permission(rankID);
    }
}
