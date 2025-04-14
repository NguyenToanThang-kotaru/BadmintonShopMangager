/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.EmployeeDAO;
import DTO.EmployeeDTO;
import java.util.ArrayList;

/**
 *
 * @author Thang Nguyen
 */
public class EmployeeBUS {
    public static EmployeeDTO getEmployeeByPhone(String phone){
        return EmployeeDAO.getEmployeeByPhone(phone);
    }
    public static ArrayList<EmployeeDTO> getAllEmployees(){
        return EmployeeDAO.getAllEmployees();
    }
    public static Boolean deletedEmployee(String ID){
        return EmployeeDAO.delete_Employee(ID);
    }
}
