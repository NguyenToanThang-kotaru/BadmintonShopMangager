package DAO;

import Connection.DatabaseConnection;
import DTO.AccountDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AccountDAO {

    public static AccountDTO getAccount(String username, String password) {
        String query = "SELECT a.Username, a.Password, a.EmployeeID, e.FullName, a.RankID "
                + "FROM account a "
                + "JOIN employee e ON a.EmployeeID = e.EmployeeID "
                + "WHERE a.Username = ? AND a.Password = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new AccountDTO(
                            rs.getString("Username"),
                            rs.getString("Password"),
                            rs.getString("EmployeeID"),
                            rs.getString("FullName"), // Lấy thêm tên nhân viên
                            rs.getString("RankID")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Không tìm thấy tài khoản
    }

    // Lấy danh sách tài khoản cho bảng GUI
    public static ArrayList<AccountDTO> getAllAccounts() {
        ArrayList<AccountDTO> accounts = new ArrayList<>();
        String query = "SELECT a.Username, a.Password, a.EmployeeID, e.FullName, a.RankID "
                + "FROM account a "
                + "JOIN employee e ON a.EmployeeID = e.EmployeeID";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                accounts.add(new AccountDTO(
                        rs.getString("Username"),
                        rs.getString("Password"),
                        rs.getString("EmployeeID"),
                        rs.getString("FullName"), // Lấy tên nhân viên
                        rs.getString("RankID")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public void updateAccount(AccountDTO account) {
        String sql = "UPDATE accounts SET username = ?, password = ?, rankID = ? WHERE employeeID = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, account.getUsername());
            stmt.setString(2, account.getPassword());
            stmt.setString(3, account.getRankID());
            stmt.setString(4, account.getEmployeeID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}