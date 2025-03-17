package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Lớp này dùng để kết nối database và lấy dữ liệu sản phẩm
public class DatabaseProductConnection {
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
        String query = "SELECT ProductID, ProductName, ProductImg, Quantity, SupplierID, TypeID FROM product";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String[] product = new String[]{
                        rs.getString("ProductID"),
                        rs.getString("ProductName"),
                        rs.getString("ProductImg"),
                        rs.getString("Quantity"),
                        rs.getString("SupplierID"),
                        rs.getString("TypeID")
                };
                productList.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }
}
