package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Connection.DatabaseConnection;
import DTO.ImportInvoiceDetailDTO;

public class ImportInvoiceDetailDAO {
    public static ArrayList<ImportInvoiceDetailDTO> getAllImportInvoiceDetail() {
        ArrayList<ImportInvoiceDetailDTO> importInvoiceDetailList = new ArrayList<>();
        String query = "SELECT * FROM import_invoice_detail";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    importInvoiceDetailList.add(new ImportInvoiceDetailDTO(
                        rs.getString("ImportID"),
                        rs.getString("ProductID"),
                        rs.getInt("Quantity"),
                        rs.getDouble("Price"), 
                        rs.getDouble("TotalPrice") 
                    ));
                }
            }
        } catch (SQLException es) {
            es.printStackTrace();
        }
        return importInvoiceDetailList;
    }

    public ImportInvoiceDetailDTO getImportInvoiceDetailByID(String id){
        String sql = "SELECT * FROM import_invoice_detail WHERE ImportID = ?";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql)){
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                ImportInvoiceDetailDTO importInvoiceDetail = new ImportInvoiceDetailDTO();
                importInvoiceDetail.setImportID(rs.getString("ImportID"));
                importInvoiceDetail.setProductID(rs.getString("ProductID"));
                importInvoiceDetail.setQuantity(rs.getInt("Quatity"));
                importInvoiceDetail.setPrice(rs.getDouble("Price"));
                importInvoiceDetail.setTotalPrice(rs.getDouble("TotalPrice"));
                return importInvoiceDetail;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean insert(ImportInvoiceDetailDTO importInvoiceDetail){
        boolean result = false;
        String sql = "Insert into import_invoice_detail(ImportID, ProductID, Quantity, Price, ToTalPrice) values(?,?,?,?,?,?)";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql)){
            pst.setString(1, importInvoiceDetail.getImportID());
            pst.setString(2, importInvoiceDetail.getProductID());
            pst.setInt(3, importInvoiceDetail.getQuantity());
            pst.setDouble(4, importInvoiceDetail.getPrice());
            pst.setDouble(5, importInvoiceDetail.getTotalPrice());
            
            if(pst.executeUpdate()>=1)
                result = true;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return result;
    }
}
