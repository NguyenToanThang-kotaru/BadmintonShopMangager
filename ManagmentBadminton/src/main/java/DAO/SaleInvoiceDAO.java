package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Connection.DatabaseConnection;
import GUI.SaleInvoiceDTO;

public class SaleInvoiceDAO {
    public static ArrayList<SaleInvoiceDTO> getAll() {
        String sql = "select * from sale_invoice;";
        ArrayList <SaleInvoiceDTO> saleInvoices = new ArrayList<>();
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                SaleInvoiceDTO saleInvoice = new SaleInvoiceDTO();
                saleInvoice.setId(rs.getString("SaleInvoiceID"));
                saleInvoice.setCustomerId(rs.getString("CustomerID"));
                saleInvoice.setEmployeeId(rs.getString("EmployeeID"));
                saleInvoice.setDate(new java.util.Date(rs.getDate("Date").getTime())); // date.util.Date vs date.sql.Date
                saleInvoice.setTotalPrice(Double.parseDouble(rs.getString("TotalPrice")));
                saleInvoices.add(saleInvoice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return saleInvoices;
    }

    public static ArrayList<SaleInvoiceDTO> getById(String id) {
        String sql = "select * from sale_invoice where SaleInvoiceID = ?;";
        ArrayList <SaleInvoiceDTO> saleInvoices = new ArrayList<>();
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                SaleInvoiceDTO saleInvoice = new SaleInvoiceDTO();
                saleInvoice.setId(rs.getString("SaleInvoiceID"));
                saleInvoice.setCustomerId(rs.getString("CustomerID"));
                saleInvoice.setEmployeeId(rs.getString("EmployeeID"));
                saleInvoice.setDate(new java.util.Date(rs.getDate("Date").getTime())); // date.util.Date vs date.sql.Date
                saleInvoice.setTotalPrice(Double.parseDouble(rs.getString("TotalPrice")));
                saleInvoices.add(saleInvoice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return saleInvoices;
    }

    public static ArrayList<SaleInvoiceDTO> getByCustomerId(String customerId) {
        String sql = "select * from sale_invoice where CustomerID = ?;";
        ArrayList <SaleInvoiceDTO> saleInvoices = new ArrayList<>();
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, customerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                SaleInvoiceDTO saleInvoice = new SaleInvoiceDTO();
                saleInvoice.setId(rs.getString("SaleInvoiceID"));
                saleInvoice.setCustomerId(rs.getString("CustomerID"));
                saleInvoice.setEmployeeId(rs.getString("EmployeeID"));
                saleInvoice.setDate(new java.util.Date(rs.getDate("Date").getTime())); // date.util.Date vs date.sql.Date
                saleInvoice.setTotalPrice(Double.parseDouble(rs.getString("TotalPrice")));
                saleInvoices.add(saleInvoice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return saleInvoices;
    }
    
    public static ArrayList<SaleInvoiceDTO> getByEmployeeId(String employeeId) {
        String sql = "select * from sale_invoice where EmployeeID = ?;";
        ArrayList <SaleInvoiceDTO> saleInvoices = new ArrayList<>();
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, employeeId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                SaleInvoiceDTO saleInvoice = new SaleInvoiceDTO();
                saleInvoice.setId(rs.getString("SaleInvoiceID"));
                saleInvoice.setCustomerId(rs.getString("CustomerID"));
                saleInvoice.setEmployeeId(rs.getString("EmployeeID"));
                saleInvoice.setDate(new java.util.Date(rs.getDate("Date").getTime())); // date.util.Date vs date.sql.Date
                saleInvoice.setTotalPrice(Double.parseDouble(rs.getString("TotalPrice")));
                saleInvoices.add(saleInvoice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return saleInvoices;
    }
    
    public static ArrayList<SaleInvoiceDTO> getByDate(java.util.Date date) {
        String sql = "select * from sale_invoice where Date = ?;";
        ArrayList <SaleInvoiceDTO> saleInvoices = new ArrayList<>();
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDate(1, new java.sql.Date(date.getTime())); // date.util.Date vs date.sql.Date
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                SaleInvoiceDTO saleInvoice = new SaleInvoiceDTO();
                saleInvoice.setId(rs.getString("SaleInvoiceID"));
                saleInvoice.setCustomerId(rs.getString("CustomerID"));
                saleInvoice.setEmployeeId(rs.getString("EmployeeID"));
                saleInvoice.setDate(new java.util.Date(rs.getDate("Date").getTime())); // date.util.Date vs date.sql.Date
                saleInvoice.setTotalPrice(Double.parseDouble(rs.getString("TotalPrice")));
                saleInvoices.add(saleInvoice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return saleInvoices;
    }
    
    public static boolean add(SaleInvoiceDTO saleInvoice) {
        String sql = "insert into sale_invoice (SaleInvoiceID, CustomerID, EmployeeID, Date) values (?, ?, ?, ?);";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, saleInvoice.getId());
            stmt.setString(2, saleInvoice.getCustomerId());
            stmt.setString(3, saleInvoice.getEmployeeId());
            stmt.setDate(4, new java.sql.Date(saleInvoice.getDate().getTime())); // date.util.Date vs date.sql.Date
            stmt.setDouble(5, saleInvoice.getTotalPrice());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean update(SaleInvoiceDTO saleInvoice) {
        String sql = "update sale_invoice set CustomerID = ?, EmployeeID = ?, Date = ? where SaleInvoiceID = ?;";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, saleInvoice.getCustomerId());
            stmt.setString(2, saleInvoice.getEmployeeId());
            stmt.setDate(3, new java.sql.Date(saleInvoice.getDate().getTime())); // date.util.Date vs date.sql.Date
            stmt.setString(4, saleInvoice.getId());
            stmt.setDouble(5, saleInvoice.getTotalPrice());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
