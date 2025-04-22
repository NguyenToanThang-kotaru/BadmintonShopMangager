package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Connection.DatabaseConnection;
import DTO.CustomerDTO;

public class CustomerDAO {
    public static ArrayList<CustomerDTO> getAll() {
        String sql = "select * from customer where IsDeleted = 0;";
        ArrayList <CustomerDTO> customers = new ArrayList<>();
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                CustomerDTO customer = new CustomerDTO();
                customer.setId(rs.getString("CustomerID"));
                customer.setName(rs.getString("FullName"));
                customer.setPhone(rs.getString("Phone"));
                customer.setSpending(rs.getDouble("TotalSpending"));

                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return customers;
    }

    public static CustomerDTO getById(String id) {
        String sql = "select * from customer where CustomerID = ? and IsDeleted = 0;";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next() && rs.getString("CustomerID").equals(id)) {
                CustomerDTO customer = new CustomerDTO();
                customer.setId(rs.getString("CustomerID"));
                customer.setName(rs.getString("FullName"));
                customer.setPhone(rs.getString("Phone"));
                customer.setSpending(rs.getDouble("TotalSpending"));
                return customer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static CustomerDTO getByName(String name) {
        String sql = "select * from customer where FullName = ? and IsDeleted = 0;";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            while (rs.next() && rs.getString("FullName").equals(name)) {
                CustomerDTO customer = new CustomerDTO();
                customer.setId(rs.getString("CustomerID"));
                customer.setName(rs.getString("FullName"));
                customer.setPhone(rs.getString("Phone"));
                customer.setSpending(rs.getDouble("TotalSpending"));
                return customer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static CustomerDTO getByPhone(String phone) {
        String sql = "select * from customer where Phone = ? and IsDeleted = 0;";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, phone);
            ResultSet rs = stmt.executeQuery();
            while (rs.next() && rs.getString("Phone").equals(phone)) {
                CustomerDTO customer = new CustomerDTO();
                customer.setId(rs.getString("CustomerID"));
                customer.setName(rs.getString("FullName"));
                customer.setPhone(rs.getString("Phone"));
                customer.setSpending(rs.getDouble("TotalSpending"));
                return customer;
            }
        } catch (SQLException e) {
            System.out.print("Không kiếm ra đc khách có sđt này");
            e.printStackTrace();
        }
        return null;
    }

    public static boolean add(CustomerDTO customer) {
        String sql = "insert into customer(CustomerID, FullName, Phone, TotalSpending, IsDeleted) values (?, ?, ?, ?, 0);";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, customer.getId());
            stmt.setString(2, customer.getName());
            stmt.setString(3, customer.getPhone());
            stmt.setDouble(4, customer.getSpending());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean update(CustomerDTO customer) {
        String sql = "update customer set FullName = ?, Phone = ?, TotalSpending = ? where CustomerID = ?;";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getPhone());
            stmt.setDouble(3, customer.getSpending());
            stmt.setString(4, customer.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean delete(String id) {
        String sql = "update customer set IsDeleted = true where CustomerID = ?;";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
