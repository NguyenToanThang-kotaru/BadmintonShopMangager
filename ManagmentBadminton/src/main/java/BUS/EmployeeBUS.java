/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.AccountDAO;
import DAO.EmployeeDAO;
import DTO.EmployeeDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thang Nguyen
 */
public class EmployeeBUS {

    public static EmployeeDTO getEmployeeByPhone(String phone) {
        return EmployeeDAO.getEmployeeByPhone(phone);
    }

    public static EmployeeDTO getEmployeeByID(String phone) {
        return EmployeeDAO.getEmployeeByID(phone);
    }

    public static ArrayList<EmployeeDTO> getAllEmployees() {
        return EmployeeDAO.getAllEmployees();
    }

    public static Boolean deletedEmployee(String ID) {
        if (EmployeeDAO.delete_Employee(ID) == true) {
            return AccountDAO.delete_AccountByEmployee(ID);
        } else {
            return false;
        }
    }

    public static Boolean addEmployee(EmployeeDTO emp) {
        return EmployeeDAO.addEmployee(emp);
    }

    public static Boolean updateEmployee(EmployeeDTO emp) {
        return EmployeeDAO.updateEmployee(emp);
    }

    public static ArrayList<EmployeeDTO> searchEmployee(String keyword) {
        return EmployeeDAO.searchEmployee(keyword);
    }
}
