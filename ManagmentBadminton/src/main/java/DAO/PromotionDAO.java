package DAO;

import Connection.DatabaseConnection;
import DTO.PromotionDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PromotionDAO {

    // Lấy tất cả khuyến mãi
    public static ArrayList<PromotionDTO> getAllPromotions() {
        ArrayList<PromotionDTO> list = new ArrayList<>();
        String query = "SELECT * FROM Promotion";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                PromotionDTO promo = new PromotionDTO(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date"),
                        rs.getDouble("discount_rate")
                );
                list.add(promo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Lấy khuyến mãi theo ID
    public static PromotionDTO getPromotionById(int id) {
        String query = "SELECT * FROM Promotion WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new PromotionDTO(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getDate("start_date"),
                            rs.getDate("end_date"),
                            rs.getDouble("discount_rate")
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
        String query = "INSERT INTO Promotion (id, name, discount_rate, start_date, end_date) VALUES (?, ?, ?, ?, ?)";
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
        String query = "UPDATE Promotion SET name = ?, discount_rate = ?, start_date = ?, end_date = ? WHERE id = ?";
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
    public static boolean deletePromotionById(int id) {
        String query = "DELETE FROM Promotion WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
