package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Connection.DatabaseConnection;
import DTO.PermissionDTO;
import DTO.SupplierDTO;

public class SupplierDAO {

    public static ArrayList<SupplierDTO> getAllSupplier() {
        ArrayList<SupplierDTO> supplierList = new ArrayList<>();
        String query = "SELECT * FROM supplier WHERE IsDeleted = 0";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    supplierList.add(new SupplierDTO(
                        rs.getString("SupplierID"),
                        rs.getString("SupplierName"),
                        rs.getString("Phone"),
                        rs.getString("Email"),
                        rs.getString("Address"),
                        rs.getInt("IsDeleted")
                         )
                    );
                }
            }
        } catch (SQLException es) {
            es.printStackTrace();
        }
        return supplierList;
    }

    public static SupplierDTO getSupplierByID(String id){
        String sql = "SELECT * FROM supplier WHERE SupplierID = ? AND IsDeleted = 0";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql)){
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                SupplierDTO supplier = new SupplierDTO();
                supplier.setSupplierID(rs.getString("SupplierID"));
                supplier.setSupplierName(rs.getString("SupplierName"));
                supplier.setPhone(rs.getString("Phone"));
                supplier.setEmail(rs.getString("Email"));
                supplier.setAddress(rs.getString("Address"));
                supplier.setIsDeleted(rs.getInt("IsDeleted"));
                return supplier;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insert(SupplierDTO supplier) {
        boolean result = false;
        String sql = "Insert into supplier(SupplierID, SupplierName, Phone, Email, Address, IsDeleted) values(?,?,?,?,?,?)";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql)){
            pst.setString(1, supplier.getSupplierID());
            pst.setString(2, supplier.getSupplierName());
            pst.setString(3, supplier.getPhone());
            pst.setString(4, supplier.getEmail());
            pst.setString(5, supplier.getAddress());

            pst.setInt(6, supplier.getIsDeleted());
            
            if(pst.executeUpdate()>=1)
                result = true;
            }
        catch (SQLException e) {
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
        + "IsDeleted=? "
        + "Where SupplierID=?";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql)){
                pst.setInt(1, 1);
                pst.setString(2, supplier.getSupplierID());
                if(pst.executeUpdate()>=1)
                    result = true;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public String getSupplierIDByProduct(String productId) {
        String query = "SELECT SupplierID FROM product WHERE ProductID = ? AND IsDeleted = 0";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, productId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("SupplierID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
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
    public String generateSupplierID() {
        String newID = "S01";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT MAX(SupplierID) AS maxID FROM supplier")) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String maxID = rs.getString("maxID");
                if (maxID != null) {
                    int number = Integer.parseInt(maxID.replaceAll("[^0-9]", "")) + 1;
                    newID = String.format("S%02d", number);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newID;
    }
    public boolean isPhoneExists(String phone) {
        String query = "SELECT * FROM supplier WHERE Phone = ? AND IsDeleted = 0";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, phone);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Nếu có kết quả trả về thì số điện thoại đã tồn tại
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean isEmailExists(String email) {
        String query = "SELECT * FROM supplier WHERE Email = ? AND IsDeleted = 0";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Nếu có kết quả trả về thì email đã tồn tại
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean isNameExists(String name) {
        String query = "SELECT * FROM supplier WHERE SupplierName = ? AND IsDeleted = 0";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Nếu có kết quả trả về thì tên nhà cung cấp đã tồn tại
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean isPhoneExistsUpdate(String phone, String id) {
        String query = "SELECT * FROM supplier WHERE Phone = ? AND SupplierID != ? AND IsDeleted = 0";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, phone);
            stmt.setString(2, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Nếu có kết quả trả về thì số điện thoại đã tồn tại
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean isEmailExistsUpdate(String email, String id) {
        String query = "SELECT * FROM supplier WHERE Email = ? AND SupplierID != ? AND IsDeleted = 0";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Nếu có kết quả trả về thì email đã tồn tại
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean isNameExistsUpdate(String name, String id) {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        String query = "SELECT * FROM supplier WHERE SupplierName = ? AND SupplierID != ? AND IsDeleted = 0";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Nếu có kết quả trả về thì tên nhà cung cấp đã tồn tại
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static ArrayList<SupplierDTO> searchSupplier(String keyword) {
        ArrayList<SupplierDTO> accounts = new ArrayList<>();

        String accountQuery = "SELECT * FROM supplier "
                   + "WHERE IsDeleted = 0 "
                   + "AND (SupplierID LIKE ? OR SupplierName LIKE ? OR Phone LIKE ? "
                   + "OR Email LIKE ? OR Address LIKE ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement accountStmt = conn.prepareStatement(accountQuery)) {

            String searchKeyword = "%" + keyword + "%";
            accountStmt.setString(1, searchKeyword);
            accountStmt.setString(2, searchKeyword);
            accountStmt.setString(3, searchKeyword);
            accountStmt.setString(4, searchKeyword);
            accountStmt.setString(5, searchKeyword);
            try (ResultSet rs = accountStmt.executeQuery()) {
                while (rs.next()) {
                    accounts.add(new SupplierDTO(
                        rs.getString("SupplierID"),
                        rs.getString("SupplierName"),
                        rs.getString("Phone"),
                        rs.getString("Email"),
                        rs.getString("Address"),
                        rs.getInt("IsDeleted")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accounts;
    }

}
