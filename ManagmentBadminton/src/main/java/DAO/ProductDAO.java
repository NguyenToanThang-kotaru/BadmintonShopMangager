package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Lớp này dùng để kết nối database và lấy dữ liệu sản phẩm
public class ProductDAO {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/badmintonshopmanager";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    // Phương thức kết nối database
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    // Phương thức lấy danh sách sản phẩm từ database
    public static List<String[]> getProductData() {
        List<String[]> productList = new ArrayList<>();
        String query = "SELECT ProductID, ProductName, Quantity, SupplierID, TypeID FROM product";

        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String[] product = new String[]{
                    rs.getString("ProductID"),
                    rs.getString("ProductName"),
                    rs.getString("Quantity"),
                    rs.getString("SupplierID"),
                    rs.getString("TypeID"),};
                productList.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public static String getProductImage(String productID) {
        String imagePath = null;
        String query = "SELECT ProductImg FROM product WHERE ProductID = ?";

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, productID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                imagePath = rs.getString("ProductImg"); // Lấy tên file ảnh
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return imagePath;
    }
}
