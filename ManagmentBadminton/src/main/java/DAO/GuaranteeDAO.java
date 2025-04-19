package DAO;

import DTO.GuaranteeDTO;

import Connection.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// Lớp này dùng để kết nối database và lấy dữ liệu sản phẩm
public class GuaranteeDAO {

    // Lấy thông tin của một sản phẩm
    public static GuaranteeDTO getGuarantee(String warrantyID) {
        String query = "SELECT * FROM warranty WHERE WarrantyID = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, warrantyID);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new GuaranteeDTO(
                            rs.getString("WarrantyID"),
                            rs.getString("Series"),
                            rs.getString("WarrantyReason"),
                            rs.getString("Status")
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
        ArrayList<GuaranteeDTO> warranties = new ArrayList<>();

        String query = """
        SELECT w.*
        FROM warranty w
        JOIN product_sold ps ON w.Series = ps.Series
        JOIN sales_invoice_detail sid ON ps.SalesInvoiceDetail = sid.SalesDetailID
        JOIN sales_invoice si ON sid.SalesID = si.SalesID
        WHERE DATEDIFF(CURDATE(), si.Date) <= 12
    """;

        try (
                Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String status = rs.getString("Status");
                if (status == null || status.trim().isEmpty()) {
                    status = "Chưa bảo hành";
                }

                warranties.add(new GuaranteeDTO(
                        rs.getString("WarrantyID"),
                        rs.getString("Series"),
                        rs.getString("WarrantyReason"),
                        status
                ));
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy danh sách bảo hành: " + e.getMessage());
            e.printStackTrace();
        }

        return warranties;
    }

    // Cập nhật thông tin sản phẩm
    public static void updateGuarantee(GuaranteeDTO guarantee) {
        String updateSql = "UPDATE warranty SET Series = ?, WarrantyReason = ?, Status = ? WHERE WarrantyID = ?";
        String deleteSql = "DELETE FROM warranty WHERE WarrantyID = ?";

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
                    stmt.setString(3, guarantee.gettrangthai());
                    stmt.setString(4, guarantee.getBaohanhID());

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
//        String query = "SELECT hinh_anh_sp FROM san_pham WHERE ma_bao_hanh = ?";
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
