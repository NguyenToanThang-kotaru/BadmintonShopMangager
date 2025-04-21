package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Connection.DatabaseConnection;
import DTO.ProductSoldDTO;


public class ProductSoldDAO {

    // Get ProductSoldDTO by DetailSaleInvoiceID
    public List<ProductSoldDTO> getByDetailSaleInvoiceID(String salesInvoiceDetailID) {
        List<ProductSoldDTO> productSoldList = new ArrayList<>();
        String query = "SELECT * FROM product_sold WHERE SalesInvoiceDetail = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, salesInvoiceDetailID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ProductSoldDTO ProductSoldDTO = new ProductSoldDTO();
                ProductSoldDTO.setDetailSaleInvoiceID(rs.getString("SalesInvoiceDetail"));
                ProductSoldDTO.setSeries(rs.getString("Series"));
                productSoldList.add(ProductSoldDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productSoldList;
    }

    // Get ProductSoldDTO by Series
    public ProductSoldDTO getBySeries(String series) {
        ProductSoldDTO ProductSoldDTO = null;
        String query = "SELECT * FROM product_sold WHERE Series = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, series);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ProductSoldDTO = new ProductSoldDTO();
                ProductSoldDTO.setDetailSaleInvoiceID(rs.getString("SalesInvoiceDetail"));
                ProductSoldDTO.setSeries(rs.getString("Series"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ProductSoldDTO;
    }
    
    // Add ProductSoldDTO
    public boolean add(ProductSoldDTO ProductSoldDTO) {
        String query = "INSERT INTO product_sold (SalesInvoiceDetail, Series) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, ProductSoldDTO.getDetailSaleInvoiceID());
            stmt.setString(2, ProductSoldDTO.getSeries());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
