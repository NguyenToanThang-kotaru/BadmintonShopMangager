package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import Connection.DatabaseConnection;
import DTO.ImportInvoiceDTO;
import DTO.SupplierDTO;

public class ImportInvoiceDAO {
    public static ArrayList<ImportInvoiceDTO> getAllImportInvoice() {
        ArrayList<ImportInvoiceDTO> importInvoiceList = new ArrayList<>();
        String query = "SELECT * FROM import_invoice";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    importInvoiceList.add(new ImportInvoiceDTO(
                        rs.getString("ImportID"),
                        rs.getString("EmployeeID"),
                        rs.getString("Date"),
                        rs.getDouble("TotalPrice")
                    ));
                }
            }
        } catch (SQLException es) {
            es.printStackTrace();
        }
        return importInvoiceList;
    }

    public ImportInvoiceDTO getImportInvoiceByID(String id){
        String sql = "SELECT * FROM import_invoice WHERE ImportID = ?";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql)){
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                ImportInvoiceDTO importInvoice = new ImportInvoiceDTO();
                importInvoice.setImportID(rs.getString("ImportID"));
                importInvoice.setEmployeeID(rs.getString("EmployeeID"));
                importInvoice.setDate(rs.getString("Date"));
                importInvoice.setTotalPrice(rs.getDouble("TotalPrice"));
                return importInvoice;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean insert(ImportInvoiceDTO importInvoice){
        boolean result = false;
        String sql = "Insert into import_invoice(ImportID, EmployeeID, Date, TotalPrice) values(?,?,?,?)";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql)){
            pst.setString(1, importInvoice.getImportID());
            pst.setString(2, importInvoice.getEmployeeID());

            pst.setString(3, importInvoice.getDate());
            pst.setDouble(4, importInvoice.getTotalPrice());
            
            if(pst.executeUpdate()>=1)
                result = true;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return result;
    }


    public String generateNextImportID() {
        String query = "SELECT ImportID FROM import_invoice ORDER BY ImportID DESC LIMIT 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                String lastID = rs.getString("ImportID");
                int num = Integer.parseInt(lastID.substring(2)) + 1;
                return String.format("I%02d", num);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "I01";
    }

    public double calculateImportTotal(String importID) {
        String query = "SELECT TotalPrice FROM import_invoice_detail WHERE ImportID = ?";
        double allTotalPrice = 0;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, importID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    double totalPrice = rs.getDouble("TotalPrice");
                    allTotalPrice += totalPrice;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allTotalPrice;
    }

    public String getEmployeeNameByImportID(String id) {
        String sql = "SELECT FullName FROM import_invoice JOIN employee ON import_invoice.EmployeeID=employee.EmployeeID WHERE ImportID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getString("FullName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getSupplierNameByImportID(String id) {
        String sql = "SELECT SupplierName FROM import_invoice JOIN supplier ON import_invoice.SupplierID=supplier.SupplierID WHERE ImportID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getString("SupplierName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<ImportInvoiceDTO> searchImportInvoice(String keyword) {
        ArrayList<ImportInvoiceDTO> imports = new ArrayList<>();

        String accountQuery = "SELECT * FROM import_invoice "
        + "WHERE CAST(ImportID AS CHAR) LIKE ? "
        + "OR CAST(EmployeeID AS CHAR) LIKE ? "
        + "OR CAST(Date AS CHAR) LIKE ? "
        + "OR CAST(TotalPrice AS CHAR) LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement accountStmt = conn.prepareStatement(accountQuery)) {

            String searchKeyword = "%" + keyword + "%";
            accountStmt.setString(1, searchKeyword);
            accountStmt.setString(2, searchKeyword);
            accountStmt.setString(3, searchKeyword);
            accountStmt.setString(4, searchKeyword);
            try (ResultSet rs = accountStmt.executeQuery()) {
                while (rs.next()) {
                    imports.add(new ImportInvoiceDTO(
                            rs.getString("ImportID"),
                            rs.getString("EmployeeID"),
                            rs.getString("Date"),
                            rs.getDouble("TotalPrice")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imports;
    }
}
