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
import java.sql.SQLException;

/**
 *
 * @author Thang Nguyen
 */
public class EmployeeDAO {

    public static Boolean addEmployee(EmployeeDTO emp) {
        String query = "INSERT INTO `employee` "
                + "(`EmployeeID`, `FullName`, `Age`, `Phone`, `Email`, `Address`, `Gender`, `IsDeleted`) "
                + " VALUES (?,?,?,?,?,?,?,0)";
        try {
            Connection conn = DatabaseConnection.getConnection();

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, generateNewEmployeeID());
            stmt.setString(2, emp.getFullName());
            stmt.setString(3, emp.getAge());
            stmt.setString(4, emp.getPhone());
            stmt.setString(5, emp.getEmail());
            stmt.setString(6, emp.getAddress());
            stmt.setString(7, emp.getGender());
            int rs = stmt.executeUpdate();
            return rs > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

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
        String query = "UPDATE employee "
                + "SET IsDeleted = 1 "
                + "WHERE EmployeeID = ?;";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, ID);
            int row = stmt.executeUpdate();
            return row > 0;

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

    public static Boolean updateEmployee(EmployeeDTO emp) {
        String query = "UPDATE employee SET "
                + "FullName = ?, "
                + "Age = ?, "
                + "Phone = ?, "
                + "Email = ?, "
                + "Address = ?, "
                + "Gender = ? "
                + "WHERE EmployeeID = ?";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, emp.getFullName());
            stmt.setString(2, emp.getAge());
            stmt.setString(3, emp.getPhone());
            stmt.setString(4, emp.getEmail());
            stmt.setString(5, emp.getAddress());
            stmt.setString(6, emp.getGender());
            stmt.setString(7, emp.getEmployeeID()); // cần có để WHERE

            int rs = stmt.executeUpdate();
            return rs > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String generateNewEmployeeID() {
        String query = "SELECT EmployeeID FROM employee ORDER BY EmployeeID DESC LIMIT 1";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                String lastID = rs.getString("EmployeeID"); // Ví dụ: "NV005"

                // Cắt bỏ "NV", chỉ lấy số
                int number = Integer.parseInt(lastID.substring(2));

                // Tạo ID mới với định dạng EXX
                return String.format("E%02d", number + 1); // Ví dụ: "E02"
            }

        } catch (SQLException e) {
            System.out.println("Lỗi khi tạo mã nhân viên mới: " + e.getMessage());
            e.printStackTrace();
        }

        return "E01"; // Nếu không có nhân viên nào, bắt đầu từ "NV001"
    }

}
