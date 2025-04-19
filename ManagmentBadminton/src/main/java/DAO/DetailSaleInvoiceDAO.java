package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Connection.DatabaseConnection;
import DTO.DetailSaleInvoiceDTO;

public class DetailSaleInvoiceDAO {
    public static ArrayList<DetailSaleInvoiceDTO> getAll() {
        String sql = "select * from sales_invoice_detail;";
        ArrayList <DetailSaleInvoiceDTO> detailSaleInvoices = new ArrayList<>();
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                DetailSaleInvoiceDTO detailSaleInvoice = new DetailSaleInvoiceDTO();
                detailSaleInvoice.setDetailSaleInvoiceID(rs.getString("SalesDetailID"));
                detailSaleInvoice.setSale_id(rs.getString("SalesID"));
                detailSaleInvoice.setProduct_id(rs.getString("ProductID"));
                detailSaleInvoice.setQuantity(rs.getInt("Quantity"));
                detailSaleInvoice.setPrice(rs.getDouble("Price"));
                detailSaleInvoice.setTotalPrice(rs.getDouble("TotalPrice"));
                detailSaleInvoices.add(detailSaleInvoice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return detailSaleInvoices;
    }

    public static ArrayList<DetailSaleInvoiceDTO> getBySalesID(String saleID) {
        String sql = "select * from sales_invoice_detail where SalesID = ?;";
        ArrayList <DetailSaleInvoiceDTO> detailSaleInvoices = new ArrayList<>();
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, saleID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                DetailSaleInvoiceDTO detailSaleInvoice = new DetailSaleInvoiceDTO();
                detailSaleInvoice.setDetailSaleInvoiceID(rs.getString("SalesDetailID"));
                detailSaleInvoice.setSale_id(rs.getString("SalesID"));
                detailSaleInvoice.setProduct_id(rs.getString("ProductID"));
                detailSaleInvoice.setQuantity(rs.getInt("Quantity"));
                detailSaleInvoice.setPrice(rs.getDouble("Price"));
                detailSaleInvoice.setTotalPrice(rs.getDouble("TotalPrice"));
                detailSaleInvoices.add(detailSaleInvoice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return detailSaleInvoices;
    }

    public static boolean add(DetailSaleInvoiceDTO detailSaleInvoice) {
        String sql = "insert into sales_invoice_detail (SalesDetailID, SalesID, ProductID, Quantity, Price, TotalPrice) values (?, ?, ?, ?, ?, ?);";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, detailSaleInvoice.getDetailSaleInvoiceID());
            stmt.setString(2, detailSaleInvoice.getSale_id());
            stmt.setString(3, detailSaleInvoice.getProduct_id());
            stmt.setInt(4, detailSaleInvoice.getQuantity());
            stmt.setDouble(5, detailSaleInvoice.getPrice());
            stmt.setDouble(6, detailSaleInvoice.getTotalPrice());
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
