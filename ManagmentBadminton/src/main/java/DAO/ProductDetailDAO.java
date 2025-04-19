package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Connection.DatabaseConnection;
import DTO.ProductDetailDTO;

public class ProductDetailDAO {
    public static ArrayList<ProductDetailDTO> getAllProductDetail() {
        ArrayList<ProductDetailDTO> importInvoiceDetailList = new ArrayList<>();
        String query = "SELECT * FROM product_detail";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    importInvoiceDetailList.add(new ProductDetailDTO(
                        rs.getString("Series"),
                        rs.getString("ProductID"),
                        rs.getString("ImportDate"),
                        rs.getString("Status")
                    ));
                }
            }
        } catch (SQLException es) {
            es.printStackTrace();
        }
        return importInvoiceDetailList;
    }

    public static ArrayList<ProductDetailDTO> getProductDetailByProductID(String id){
        ArrayList<ProductDetailDTO> importInvoiceDetailList = new ArrayList<>();
        String query = "SELECT * FROM product_detail where ProductID = ?";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(query)){
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                importInvoiceDetailList.add(new ProductDetailDTO(
                    rs.getString("Series"),
                    rs.getString("ProductID"),
                    rs.getString("ImportDate"),
                    rs.getString("Status")
                ));
            }
        } catch (SQLException es) {
            es.printStackTrace();
        }
        return importInvoiceDetailList;
    }
    
    public boolean insert(ProductDetailDTO importInvoiceDetail){
        boolean result = false;
        String sql = "Insert into product_detail(Series, ProductID, ImportDate, Status) values(?,?,?,?)";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql)){
            pst.setString(1, importInvoiceDetail.getSeries());
            pst.setString(2, importInvoiceDetail.getProductID());
            pst.setString(3, importInvoiceDetail.getImportDate());
            pst.setString(4, importInvoiceDetail.getStatus());
            if(pst.executeUpdate()>=1)
                result = true;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public String generateNextSeries() {
        String query = "SELECT Series FROM product_detail ORDER BY Series DESC LIMIT 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                String lastID = rs.getString("Series");
                int num = Integer.parseInt(lastID.substring(2)) + 1;
                return String.format("I%08d", num);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "SE00000001";
    }




    public String getLastSeries() {
        String query = "SELECT Series FROM product_detail ORDER BY Series DESC LIMIT 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getString("Series");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "SE00000001";
    }
    
}
