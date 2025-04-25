package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Connection.DatabaseConnection;
import DTO.GuaranteeDTO;

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
                    status = "Không";
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

    public static void updateGuarantee(GuaranteeDTO guarantee) {
        String updateSql = "UPDATE warranty SET Series = ?, WarrantyReason = ?, Status = ? WHERE WarrantyID = ?";
        String deleteSql = "DELETE FROM warranty WHERE WarrantyID = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(updateSql)) {
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
        } catch (SQLException e) {
            System.out.println("Lỗi cập nhật/xóa bảo hành: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String generateNewGuaranteeID() {
        String query = "SELECT WarrantyID FROM warranty ORDER BY WarrantyID DESC LIMIT 1";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                String lastID = rs.getString("WarrantyID"); // Ví dụ: "NV005"

                // Cắt bỏ "TK", chỉ lấy số
                int number = Integer.parseInt(lastID.substring(2));

                // Tạo ID mới với định dạng NVXXX
                return String.format("W%03d", number + 1);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi khi tạo mã bảo hành mới: " + e.getMessage());
            e.printStackTrace();
        }

        return "W01"; // Nếu không có nhân viên nào, bắt đầu từ "NV001"
    }

    public static boolean addGuarantee(String Series) {
        String sql = "INSERT INTO `warranty`(`WarrantyID`, `Series`, `WarrantyReason`, `Status`) VALUES (?, ?, '', 'Không') ";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            String newID = generateNewGuaranteeID(); // Tạo ID mới

            stmt.setString(1, newID);
            stmt.setString(2, Series);
            stmt.executeUpdate();
            System.out.println("Thêm sản phẩm bảo hành thành công với ID: " + newID);
            return true;

        } catch (SQLException e) {
            System.out.println("Lỗi thêm sản phẩm: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}
