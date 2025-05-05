package DAO;

import Connection.DatabaseConnection;
import DTO.AccountDTO;
import DTO.PermissionDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    public static ArrayList<AccountDTO> searchAccounts(String keyword) {
        ArrayList<AccountDTO> accounts = new ArrayList<>();

        String accountQuery = "SELECT a.*, e.* FROM account a "
                + "JOIN employee e ON a.EmployeeID = e.EmployeeID "
                + "WHERE a.IsDeleted = 0 "
                + "AND (a.Username LIKE ? OR a.Password LIKE ? OR a.EmployeeID LIKE ? "
                + "OR a.RankID LIKE ? OR e.FullName LIKE ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement accountStmt = conn.prepareStatement(accountQuery)) {

            String searchKeyword = "%" + keyword + "%";
            accountStmt.setString(1, searchKeyword);
            accountStmt.setString(2, searchKeyword);
            accountStmt.setString(3, searchKeyword);
            accountStmt.setString(4, searchKeyword);
            accountStmt.setString(5, searchKeyword);
            try (ResultSet rs = accountStmt.executeQuery()) {
                while (rs.next()) {
                    PermissionDTO permission = PermissionDAO.getPermissionByID(rs.getString("RankID"));
                    accounts.add(new AccountDTO(
                            rs.getString("Username"),
                            rs.getString("Password"),
                            rs.getString("EmployeeID"),
                            rs.getString("FullName"), // Lấy tên nhân viên
                            permission
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public static String countAccountFromPermission(String permissionID) {
        String total = "0";
        String query = "SELECT COUNT(*) AS total FROM account WHERE rankID = ?";

        try (Connection conn = DatabaseConnection.getConnection(); // Giả sử có lớp DatabaseHelper để kết nối
                 PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, permissionID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    total = rs.getString("total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Xử lý exception theo nhu cầu
        }

        return total;
    }

    public static AccountDTO getAccountByUsername(String username) {
        String query = "SELECT a.Username, a.Password, a.EmployeeID, e.FullName, a.RankID "
                + "FROM account a "
                + "JOIN employee e ON a.EmployeeID = e.EmployeeID "
                + "WHERE a.Username = ? ";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    PermissionDTO permission = PermissionDAO.getPermissionByID(rs.getString("RankID"));
                    return new AccountDTO(
                            rs.getString("Username"),
                            rs.getString("Password"),
                            rs.getString("EmployeeID"),
                            rs.getString("FullName"), // Lấy thêm tên nhân viên
                            permission
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Không tìm thấy tài khoản
    }

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
                    PermissionDTO permission = PermissionDAO.getPermissionByID(rs.getString("RankID"));
                    return new AccountDTO(
                            rs.getString("Username"),
                            rs.getString("Password"),
                            rs.getString("EmployeeID"),
                            rs.getString("FullName"), // Lấy thêm tên nhân viên
                            permission
                    );
                }
            }
        } catch (Exception e) {
            System.out.print("DDjt me bij looix goafi nhaayr doo hamf catch luoon goif fack");
            e.printStackTrace();
        }
        return null; // Không tìm thấy tài khoản
    }

    public static Boolean delete_Account(String username) {
        String query = "DELETE FROM `account` WHERE Username = ?;";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            int row = stmt.executeUpdate();
            if (row > 0) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Boolean delete_AccountByEmployee(String id) {
        String query = "DELETE FROM `account` WHERE EmployeeID = ?;";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, id);
            int row = stmt.executeUpdate();
            if (row > 0) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateAccount(AccountDTO account) {
        String query = "UPDATE account SET Password = ?, RankID = ? WHERE Username = ? ";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, account.getPassword());
            stmt.setString(2, account.getPermission().getID());
            stmt.setString(3, account.getUsername());
//            System.out.println(account.);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("okkkkk");

                return true;
            }
            System.out.println("khong co rows");

            return false;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("sai roi");
            return false;
        }
    }

    public static boolean addAccount(AccountDTO account) {
        String query = "INSERT INTO account (Username, Password, EmployeeID, RankID, IsDeleted) VALUES (?, ?, ?, ?, 0)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, account.getUsername());
            stmt.setString(2, account.getPassword());
            stmt.setString(3, account.getEmployeeID());
            stmt.setString(4, account.getPermission().getID());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy danh sách tài khoản cho bảng GUI
    public static ArrayList<AccountDTO> getAllAccounts() {
        ArrayList<AccountDTO> accounts = new ArrayList<>();
        String query = "SELECT a.Username, a.Password, a.EmployeeID, e.FullName, a.RankID "
                + "FROM account a "
                + "JOIN employee e ON a.EmployeeID = e.EmployeeID "
                + "WHERE a.IsDeleted = 0;";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                PermissionDTO permission = PermissionDAO.getPermissionByID(rs.getString("RankID"));
                accounts.add(new AccountDTO(
                        rs.getString("Username"),
                        rs.getString("Password"),
                        rs.getString("EmployeeID"),
                        rs.getString("FullName"), // Lấy tên nhân viên
                        permission
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accounts;
    }

}
