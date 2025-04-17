package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Connection.DatabaseConnection;
import DTO.ImportInvoiceDetailDTO;
import GUI.Utils;

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
                        rs.getString("SupplierID"),
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

    public ArrayList<ImportInvoiceDetailDTO> getImportInvoiceDetailByImportID(String id){
        ArrayList<ImportInvoiceDetailDTO> importInvoiceDetailList = new ArrayList<>();
        String query = "SELECT * FROM import_invoice_detail where ImportID = ?";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(query)){
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                importInvoiceDetailList.add(new ImportInvoiceDetailDTO(
                    rs.getString("ImportID"),
                    rs.getString("ProductID"),
                    rs.getString("SupplierID"),
                    rs.getInt("Quantity"),
                    rs.getDouble("Price"), 
                    rs.getDouble("TotalPrice") 
                ));
            }
        } catch (SQLException es) {
            es.printStackTrace();
        }
        return importInvoiceDetailList;
    }
    
    public boolean insert(ImportInvoiceDetailDTO importInvoiceDetail){
        boolean result = false;
        String sql = "Insert into import_invoice_detail(ImportID, ProductID, SupplierID, Quantity, Price, ToTalPrice) values(?,?,?,?,?,?)";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql)){
            pst.setString(1, importInvoiceDetail.getImportID());
            pst.setString(2, importInvoiceDetail.getProductID());
            pst.setString(3, importInvoiceDetail.getSupplierID());
            pst.setInt(4, importInvoiceDetail.getQuantity());
            pst.setDouble(5, importInvoiceDetail.getPrice());
            pst.setDouble(6, importInvoiceDetail.getTotalPrice());
            System.out.println("supplierID: " + importInvoiceDetail.getSupplierID());
            if(pst.executeUpdate()>=1)
                result = true;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<Object[]> loadImportDetails(String importID) {
        ArrayList<Object[]> details = new ArrayList<>();
        String query = "SELECT * " +
                       "FROM import_invoice_detail " +
                       "JOIN product ON import_invoice_detail.ProductID = product.ProductID " +
                       "WHERE import_invoice_detail.ImportID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, importID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int quantity = rs.getInt("import_invoice_detail.Quantity");
                double price = rs.getDouble("import_invoice_detail.Price");
                double rowTotal = rs.getDouble("import_invoice_detail.TotalPrice");
                details.add(new Object[]{
                    rs.getString("ProductID"),
                    rs.getString("ProductName"),
                    quantity,
                    Utils.formatCurrency(price),
                    Utils.formatCurrency(rowTotal)
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return details;
    }
    
}
