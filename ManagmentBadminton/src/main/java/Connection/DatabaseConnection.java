    package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/badmintonshopmanager";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Phương thức mở kết nối
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL JDBC Driver
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("ket noi thanh cong!");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("loi ket noi: " + e.getMessage());
        }
        return conn;
    }

    // Phương thức đóng kết nối
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Dong ket noi thang cong!");
            } catch (SQLException e) {
                System.out.println("loi khi dang ket noi: " + e.getMessage());
            }
        }
    }
//    public static void main(String[] args) {
//        DatabaseConnection a = new DatabaseConnection();
//        Connection conn = getConnection();
//        if (conn != null) {
//            
//            DatabaseConnection.closeConnection(conn); // Đóng kết nối sau khi test
//        } else {
//            System.out.println("khong the ket noi!");
//        }
//    }
}
