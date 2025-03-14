package DAO;

import Connection.DatabaseConnection;
import DTO.AccountDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountDAO {
    public static AccountDTO getAccount(String username, String password) {
        String query = "SELECT * FROM account WHERE Username = ? AND Password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new AccountDTO(
                        rs.getString("Username"),
                        rs.getString("Password"),
                        rs.getString("EmployeeID"),
                        rs.getString("RankID")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Không tìm thấy tài khoản
    }
}
