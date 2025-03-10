package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conn {
    // Cấu hình kết nối
    private static final String URL = "jdbc:mysql://localhost:3306/qlshopcaulong"; 
    private static final String USER = "root";  
    private static final String PASSWORD = "";  

    // Hàm kết nối đến MySQL
    public static Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // Load MySQL JDBC Driver
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Kết nối MySQL thành công!");
            return conn;
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Không tìm thấy Driver!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Lỗi kết nối Database!");
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        connect(); // Thử kết nối database
    }
}
