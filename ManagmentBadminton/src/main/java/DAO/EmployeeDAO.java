/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Connection.DatabaseConnection;
import DTO.AccountDTO;
import java.util.ArrayList;
import DTO.EmployeeDTO;
import com.mysql.cj.xdevapi.PreparableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Thang Nguyen
 */
public class EmployeeDAO {

    public static ArrayList<EmployeeDTO> getEmployeesWithoutAccount() {
        String sql = "SELECT * FROM employee e "
           + "WHERE e.IsDeleted = 0 "
           + "AND e.EmployeeID NOT IN ("
           + "    SELECT a.EmployeeID FROM account a WHERE a.IsDeleted = 0"
           + ");";
        ArrayList<EmployeeDTO> employees = new ArrayList<>();
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                EmployeeDTO employee = new EmployeeDTO();
                employee.setEmployeeID(rs.getString("EmployeeID"));
                employee.setFullName(rs.getString("FullName"));
                employee.setAge(String.valueOf(rs.getInt("Age")));
                employee.setPhone(rs.getString("Phone"));
                employee.setEmail(rs.getString("Email"));
                employee.setAddress(rs.getString("Address"));
                employee.setGender(rs.getString("Gender"));

                employees.add(employee);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return employees;
    }

    public static Boolean delete_Employee(String ID) {
        String query = "DELETE FROM `account` WHERE EmployeeID = ?;";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, ID);
            int row = stmt.executeUpdate();
            if (row > 0) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static EmployeeDTO getEmployeeByPhone(String phone) {
        String sql = "SELECT * FROM employee WHERE Phone = ?;";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, phone);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                EmployeeDTO employee = new EmployeeDTO();
                employee.setEmployeeID(rs.getString("EmployeeID"));
                employee.setFullName(rs.getString("FullName"));
                employee.setAge(String.valueOf(rs.getInt("Age")));
                employee.setPhone(rs.getString("Phone"));
                employee.setEmail(rs.getString("Email"));
                employee.setAddress(rs.getString("Address"));
                employee.setGender(rs.getString("Gender"));
                return employee;
            }
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static EmployeeDTO getEmployeeByID(String phone) {
        String sql = "SELECT * FROM employee WHERE EmployeeID = ?;";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, phone);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                EmployeeDTO employee = new EmployeeDTO();
                employee.setEmployeeID(rs.getString("EmployeeID"));
                employee.setFullName(rs.getString("FullName"));
                employee.setAge(String.valueOf(rs.getInt("Age")));
                employee.setPhone(rs.getString("Phone"));
                employee.setEmail(rs.getString("Email"));
                employee.setAddress(rs.getString("Address"));
                employee.setGender(rs.getString("Gender"));
                return employee;
            }
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<EmployeeDTO> getAllEmployees() {
        String sql = "SELECT * FROM `employee` WHERE IsDeleted = 0;";
        ArrayList<EmployeeDTO> employees = new ArrayList<>();
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                EmployeeDTO employee = new EmployeeDTO();
                employee.setEmployeeID(rs.getString("EmployeeID"));
                employee.setFullName(rs.getString("FullName"));
                employee.setAge(String.valueOf(rs.getInt("Age")));
                employee.setPhone(rs.getString("Phone"));
                employee.setEmail(rs.getString("Email"));
                employee.setAddress(rs.getString("Address"));
                employee.setGender(rs.getString("Gender"));

                employees.add(employee);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return employees;
    }

}
