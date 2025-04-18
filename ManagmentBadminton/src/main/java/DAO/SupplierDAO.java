package DAO;

import Connection.DatabaseConnection;
import DTO.SupplierDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierDAO {

    public static ArrayList<SupplierDTO> getAllSupplier() {
        ArrayList<SupplierDTO> supplierList = new ArrayList<>();
        String query = "SELECT * FROM supplier";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    supplierList.add(new SupplierDTO(
                            rs.getString("SupplierID"),
                            rs.getString("SupplierName"),
                            rs.getString("Phone"),
                            rs.getString("Email"),
                            rs.getString("Address"),
                            rs.getString("Status")
                    )
                    );
                }
            }
        } catch (SQLException es) {
            es.printStackTrace();
        }
        return supplierList;
    }

    public SupplierDTO getSupplierByID(String id) {
        String sql = "SELECT * FROM supplier WHERE SupplierID = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                SupplierDTO supplier = new SupplierDTO();
                supplier.setSupplierID(rs.getString("SupplierID"));
                supplier.setSupplierName(rs.getString("SupplierName"));
                supplier.setPhone(rs.getString("Phone"));
                supplier.setEmail(rs.getString("Email"));
                supplier.setAddress(rs.getString("Address"));
                supplier.setStatus(rs.getString("Status"));
                return supplier;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insert(SupplierDTO supplier) {
        boolean result = false;
        String sql = "Insert into supplier(SupplierID, SupplierName, Phone, Email, Address, Status) values(?,?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, supplier.getSupplierID());
            pst.setString(2, supplier.getSupplierName());
            pst.setString(3, supplier.getPhone());
            pst.setString(4, supplier.getEmail());
            pst.setString(5, supplier.getAddress());
            pst.setString(6, supplier.getStatus());

            if (pst.executeUpdate() >= 1) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean update(SupplierDTO supplier) {
        boolean result = false;
        String sql = "Update supplier Set "
                + "SupplierName=?, "
                + "Phone=?, "
                + "Email=?, "
                + "Address=? "
                + "Where SupplierID=?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, supplier.getSupplierName());
            pst.setString(2, supplier.getPhone());
            pst.setString(3, supplier.getEmail());
            pst.setString(4, supplier.getAddress());
            pst.setString(5, supplier.getSupplierID());
            if (pst.executeUpdate() >= 1) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean remove(SupplierDTO supplier) {
        boolean result = false;
        String sql = "Update supplier Set "
                + "Status=? "
                + "Where SupplierID=?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, "Ẩn");
            pst.setString(2, supplier.getSupplierID());
            if (pst.executeUpdate() >= 1) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ArrayList<String> getAllNCCNames() {
        ArrayList<String> NCCList = new ArrayList<>();
        String query = "SELECT SupplierName FROM supplier WHERE IsDeleted = 0";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                NCCList.add(rs.getString("SupplierName"));  // Lưu tên loại vào danh sách
            }
        } catch (SQLException e) {
            System.out.println("Lỗi lấy danh sách nhà cung cấp: " + e.getMessage());
            e.printStackTrace();
        }
        return NCCList;
    }
}
