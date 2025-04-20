/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DTO.PermissionDTO;
import DAO.PermissionDAO;
import DTO.ActionDTO;
import DTO.FunctionActionDTO;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 *
 * @author Thang Nguyen
 */
public class PermissionBUS {
    public static String generateNewPermissionID() {
        return PermissionDAO.generateNewPermissionID();
    }
    public static Boolean update_Permission(PermissionDTO per) {
        delete_FunctionAction(per);
        return PermissionDAO.add_FunctionAction(per);
    }
    public static Boolean delete_FunctionAction(PermissionDTO per) {
        return PermissionDAO.delete_FunctionAction(per.getID());
    }
    public static Boolean add_Permisison(PermissionDTO per) {
        return PermissionDAO.add_Permission(per);
    }
    public static Boolean add_FunctionAction(PermissionDTO per) {
        return PermissionDAO.add_FunctionAction(per);
    }
    public static ArrayList<PermissionDTO> getAllPermissions(){
        return PermissionDAO.getAllPermissions();
    }
    public static PermissionDTO getPermissionByName(String Name) {
        return PermissionDAO.getPermissionByName(Name);
    }
    public static Boolean delete_Permission(String rankID){
        return PermissionDAO.delete_Permission(rankID);
    }
    public static ArrayList<ActionDTO> getAllAction() {
        return PermissionDAO.getAllAction();
    }
    public static ArrayList<FunctionActionDTO> getAllFunctionAction() {
        return PermissionDAO.getAllFunctionAction();
    }
    public static ArrayList<String> getAllFunctionName(){
        return PermissionDAO.getAllFunctionName();
    }
}
