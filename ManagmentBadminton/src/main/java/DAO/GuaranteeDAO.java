package DAO;

import Connection.DatabaseConnection;
import DTO.GuaranteeDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// Lớp này dùng để kết nối database và lấy dữ liệu sản phẩm
public class GuaranteeDAO {

    // Lấy thông tin của một sản phẩm
    public static GuaranteeDTO getGuarantee(String BaohanhID) {
        String query = "SELECT * FROM guarantee WHERE guaranteeID = ? ";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, BaohanhID);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new GuaranteeDTO(
                            rs.getString("guaranteeID"),
                            rs.getString("serialID"),
                            rs.getString("guaranteeReason"),
                            String.valueOf(rs.getInt("guaranteeTime")),
                            rs.getString("status")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Lấy danh sách tất cả sản phẩm
    public static ArrayList<GuaranteeDTO> getAllGuarantee() {
        ArrayList<GuaranteeDTO> products = new ArrayList<>();

        String query = "SELECT * FROM guarantee WHERE guaranteeTime < 50";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String trangThai = rs.getString("status");
                if (trangThai == null || trangThai.trim().isEmpty()) {
                    trangThai = "Chưa bảo hành";
                }

                products.add(new GuaranteeDTO(
                        rs.getString("guaranteeID"),
                        rs.getString("serialID"),
                        rs.getString("guaranteeReason"),
                        String.valueOf(rs.getInt("guaranteeTime")),
                        trangThai
                ));
            }

            System.out.println("Lấy danh sách sản phẩm bảo hành thành công.");
        } catch (Exception e) {
            System.out.println("Lỗi lấy danh sách sản phẩm: " + e.getMessage());
            e.printStackTrace();
        }
        return products;
    }

    // Cập nhật thông tin sản phẩm
    public static void updateGuarantee(GuaranteeDTO guarantee) {
        String updateSql = "UPDATE guarantee SET serialID = ?, guaranteeReason = ?, guaranteeTime = ?, status = ? WHERE guaranteeID = ?";
        String deleteSql = "DELETE FROM guarantee WHERE guaranteeID = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            if ("Đã bảo hành".equalsIgnoreCase(guarantee.gettrangthai())) {
                // Nếu trạng thái là "Đã bảo hành", thực hiện DELETE
                try (PreparedStatement stmt = conn.prepareStatement(deleteSql)) {
                    stmt.setString(1, guarantee.getBaohanhID());
                    int rowsDeleted = stmt.executeUpdate();
                    if (rowsDeleted > 0) {
                        System.out.println("Đã xóa bảo hành với mã: " + guarantee.getBaohanhID());
                    } else {
                        System.out.println("Không tìm thấy bảo hành để xóa.");
                    }
                }
            } else {
                // Nếu không phải "Đã bảo hành", thực hiện UPDATE
                try (PreparedStatement stmt = conn.prepareStatement(updateSql)) {
                    stmt.setString(1, guarantee.getSerialID());
                    stmt.setString(2, guarantee.getLydo());
                    stmt.setString(3, guarantee.getTGBH());
                    stmt.setString(4, guarantee.gettrangthai());
                    stmt.setString(5, guarantee.getBaohanhID());

                    int rowsUpdated = stmt.executeUpdate();
                    if (rowsUpdated > 0) {
                        System.out.println("Cập nhật bảo hành thành công.");
                    } else {
                        System.out.println("Không tìm thấy bảo hành để cập nhật.");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi cập nhật/xóa bảo hành: " + e.getMessage());
            e.printStackTrace();
        }
    }

//
//    // Lấy đường dẫn ảnh sản phẩm
//    public static String getProductImage(String productID) {
//        String imagePath = null;
//        String query = "SELECT hinh_anh_sp FROM san_pham WHERE guaranteeID = ?";
//
//        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
//            stmt.setString(1, productID);
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (rs.next()) {
//                    imagePath = rs.getString("hinh_anh_sp"); // Lấy tên file ảnh
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return imagePath;
//    }
}
