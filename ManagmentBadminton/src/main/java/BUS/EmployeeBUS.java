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
import javax.swing.JOptionPane;

/**
 *
 * @author Thang Nguyen
 */
public class EmployeeBUS {

    public static Boolean validationEmployee(EmployeeDTO e) {
        // Regex pattern
        String namePattern = "^[\\p{L} ]+$"; // chữ cái unicode và khoảng trắng
        String addressPattern = "^[\\p{L}0-9 /]+$";
        String phonePattern = "^(\\+84|0)(\\d{9,10})$";
        String emailPattern = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";

        // Validate name
        if (e.getFullName() == null || !e.getFullName().matches(namePattern)) {
            JOptionPane.showMessageDialog(null, "Tên không hợp lệ (chỉ chứa chữ và khoảng trắng)", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validate age > 18
        if (e.getAge().isEmpty()||Integer.parseInt(e.getAge()) <= 18) {
            JOptionPane.showMessageDialog(null, "Tuổi phải lớn hơn 18", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validate address
        if (e.getAddress() == null || !e.getAddress().matches(addressPattern)) {
            JOptionPane.showMessageDialog(null, "Địa chỉ không hợp lệ (chỉ chứa chữ, số, khoảng trắng và dấu /)", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validate phone
        if (e.getPhone() == null || !e.getPhone().matches(phonePattern)) {
            JOptionPane.showMessageDialog(null, "Số điện thoại không hợp lệ (phải bắt đầu bằng +84 hoặc 0, và theo sau là 9–10 chữ số)", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validate email
        if (e.getEmail() == null || !e.getEmail().matches(emailPattern)) {
            JOptionPane.showMessageDialog(null, "Email không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true; // Tất cả đều hợp lệ
    }

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
        AccountDAO.delete_AccountByEmployee(ID);
        return EmployeeDAO.delete_Employee(ID);
    }

    public static Boolean addEmployee(EmployeeDTO emp) {
        if (validationEmployee(emp)) {
            return EmployeeDAO.addEmployee(emp);
        }
        return false;
    }

    public static Boolean updateEmployee(EmployeeDTO emp) {
        if(validationEmployee(emp)){
            return EmployeeDAO.updateEmployee(emp);
        }
        return false;
    }

    public static ArrayList<EmployeeDTO> searchEmployee(String keyword) {
        return EmployeeDAO.searchEmployee(keyword);
    }
}
