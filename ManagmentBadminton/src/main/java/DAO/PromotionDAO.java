package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Connection.DatabaseConnection;
import DTO.PromotionDTO;

public class PromotionDAO {

    // Lấy tất cả khuyến mãi
    public static ArrayList<PromotionDTO> getAllPromotions() {
        ArrayList<PromotionDTO> list = new ArrayList<>();
        String query = "SELECT * FROM promotion";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                PromotionDTO promo = new PromotionDTO(
                        rs.getInt("PromotionID"),
                        rs.getString("PromotionName"),
                        rs.getDate("StartDate"),
                        rs.getDate("EndDate"),
                        rs.getDouble("DiscountRate")
                );
                list.add(promo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Lấy khuyến mãi theo ID
    public static PromotionDTO getPromotionById(int PromotionID) {
        String query = "SELECT * FROM promotion WHERE PromotionID = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, PromotionID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new PromotionDTO(
                            rs.getInt("PromotionID"),
                            rs.getString("PromotionName"),
                            rs.getDate("StartDate"),
                            rs.getDate("EndDate"),
                            rs.getDouble("DiscountRate")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Thêm khuyến mãi
    public static boolean addPromotion(PromotionDTO promo) {
        String query = "INSERT INTO promotion (PromotionID, PromotionName, DiscountRate, StartDate, EndDate) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, promo.getId());
            stmt.setString(2, promo.getName());
            stmt.setDouble(3, promo.getDiscountRate());
            stmt.setDate(4, promo.getStartDate());
            stmt.setDate(5, promo.getEndDate());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Cập nhật khuyến mãi
    public static boolean updatePromotion(PromotionDTO promo) {
        String query = "UPDATE promotion SET PromotionName = ?, DiscountRate = ?, StartDate = ?, EndDate = ? WHERE PromotionID = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, promo.getName());
            stmt.setDouble(2, promo.getDiscountRate());
            stmt.setDate(3, promo.getStartDate());
            stmt.setDate(4, promo.getEndDate());
            stmt.setInt(5, promo.getId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Xóa khuyến mãi theo ID
    public static boolean deletePromotionById(int PromotionID) {
        String query = "DELETE FROM promotion WHERE PromotionID = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, PromotionID);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}