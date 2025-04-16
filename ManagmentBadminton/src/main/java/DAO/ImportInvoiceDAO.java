package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Connection.DatabaseConnection;
import DTO.ImportInvoiceDTO;

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
                        rs.getString("SupplierID"),
                        rs.getString("EmployeeID"),
                        rs.getDate("Date"),
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
                importInvoice.setSupplierID(rs.getString("SupplierID"));
                importInvoice.setDate(rs.getDate("Date"));
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
        String sql = "Insert into import_invoice(ImportID, EmployeeID, SupplierID, Date, TotalPrice) values(?,?,?,?,?)";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql)){
            pst.setString(1, importInvoice.getImportID());
            pst.setString(2, importInvoice.getEmployeeID());
            pst.setString(3, importInvoice.getSupplierID());
            pst.setDate(4, importInvoice.getDate());
            pst.setDouble(5, importInvoice.getTotalPrice());
            
            if(pst.executeUpdate()>=1)
                result = true;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return result;
    }
}
