/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import BUS.AccountBUS;
import Connection.DatabaseConnection;
import DTO.ActionDTO;
import DTO.FunctionActionDTO;
import DTO.PermissionDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thang Nguyen
 */
public class PermissionDAO {

    public static Boolean delete_Permission(String rankID) {
        try {
            String sql = "UPDATE `employee_rank` SET `IsDeleted` = 1 WHERE rankID = ?;";
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, rankID);  // Gán giá trị cho dấu hỏi

            int affectedRows = ps.executeUpdate();  // Trả về số dòng bị ảnh hưởng

            return affectedRows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static PermissionDTO getPermissionByName(String Name) {
        String sql = "SELECT er.rankID, er.rankName, er.baseSalary, "
                + "f.functionID, f.functionName, f.functionNameUnsigned, "
                + "a.actionID, a.actionName, a.actionNameVN "
                + "FROM function_detail fd "
                + "JOIN employee_rank er ON fd.rankID = er.rankID "
                + "JOIN `function` f ON fd.functionID = f.functionID "
                + "JOIN action a ON fd.actionID = a.actionID "
                + "WHERE er.RankName = ? "
                + "ORDER BY f.functionID, a.actionID";

        PermissionDTO permission = null;

        try (
                Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, Name);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    if (permission == null) {
                        permission = new PermissionDTO();
                        permission.setID(rs.getString("rankID"));
                        permission.setName(rs.getString("rankName"));
                        permission.setFunction(new ArrayList<>());
                    }

                    String functionId = rs.getString("functionID");
                    FunctionActionDTO function = null;

                    for (FunctionActionDTO f : permission.getFunction()) {
                        if (f.getID().equals(functionId)) {
                            function = f;
                            break;
                        }
                    }

                    if (function == null) {
                        function = new FunctionActionDTO();
                        function.setID(functionId);
                        function.setName(rs.getString("functionName"));
                        function.setNameUnsigned(rs.getString("functionNameUnsigned"));
                        function.setAction(new ArrayList<>());
                        permission.getFunction().add(function);
                    }

                    ActionDTO action = new ActionDTO();
                    action.setID(rs.getString("actionID"));
                    action.setName(rs.getString("actionName"));
                    action.setVnName(rs.getString("actionNameVN"));
                    function.getAction().add(action);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // hoặc log lỗi tùy hệ thống logging bạn dùng
        }

        return permission; // null nếu không tìm thấy
    }

    public static PermissionDTO getPermissionByID(String rankId) throws SQLException {
        String sql = "SELECT er.rankID, er.rankName, er.baseSalary, "
                + "f.functionID, f.functionName, f.functionNameUnsigned, "
                + "a.actionID, a.actionName, a.actionNameVN "
                + "FROM function_detail fd "
                + "JOIN employee_rank er ON fd.rankID = er.rankID "
                + "JOIN `function` f ON fd.functionID = f.functionID "
                + "JOIN action a ON fd.actionID = a.actionID "
                + "WHERE er.rankID = ? "
                + "ORDER BY f.functionID, a.actionID";

        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, rankId);
        ResultSet rs = ps.executeQuery();

        PermissionDTO permission = null;

        while (rs.next()) {
            if (permission == null) {
                permission = new PermissionDTO();
                permission.setID(rs.getString("rankID"));
                permission.setName(rs.getString("rankName"));
                permission.setFunction(new ArrayList<>());
            }

            String functionId = rs.getString("functionID");

            // Tìm function trong permission
            FunctionActionDTO function = null;
            for (FunctionActionDTO f : permission.getFunction()) {
                if (f.getID().equals(functionId)) {
                    function = f;
                    break;
                }
            }

            if (function == null) {
                function = new FunctionActionDTO();
                function.setID(functionId);
                function.setName(rs.getString("functionName"));
                function.setNameUnsigned(rs.getString("functionNameUnsigned"));
                function.setAction(new ArrayList<>());
                permission.getFunction().add(function);
            }

            // Thêm action vào function
            ActionDTO action = new ActionDTO();
            action.setID(rs.getString("actionID"));
            action.setName(rs.getString("actionName"));
            action.setVnName(rs.getString("actionNameVN"));
            function.getAction().add(action);
        }

        rs.close();
        ps.close();
        conn.close();

        return permission; // Nếu không có quyền thì sẽ trả về null
    }

    public static ArrayList<PermissionDTO> getAllPermissions() {
        try {
            String sql = "SELECT er.*, "
                    + "f.*, "
                    + "a.* "
                    + "FROM function_detail fd "
                    + "JOIN employee_rank er ON fd.rankID = er.rankID "
                    + "JOIN `function` f ON fd.functionID = f.functionID "
                    + "JOIN action a ON fd.actionID = a.actionID "
                    + "WHERE er.IsDeleted = 0 AND fd.IsDeleted = 0 "
                    + "ORDER BY er.rankID, f.functionID, a.actionID";

            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            ArrayList<PermissionDTO> permissions = new ArrayList<>();

            while (rs.next()) {
                String rankId = rs.getString("rankID");
                String functionId = rs.getString("functionID");

                // === Tìm hoặc tạo PermissionDTO theo rankID ===
                PermissionDTO permission = null;
                for (PermissionDTO p : permissions) {
                    if (p.getID().equals(rankId)) {
                        permission = p;
                        break;
                    }
                }

                if (permission == null) {
                    permission = new PermissionDTO();
                    permission.setID(rankId);
                    permission.setName(rs.getString("rankName"));
                    permission.setnameUnsinged(rs.getString("RankNameUnsigned"));
                    permission.setTotalAccount(AccountBUS.countAccountFromPermission(permission.getID()));
                    permission.setTotalFunction("0");
                    permission.setFunction(new ArrayList<>());
                    permissions.add(permission);

                }

                // === Tìm hoặc tạo FunctionActionDTO theo functionID ===
                FunctionActionDTO function = null;
                for (FunctionActionDTO f : permission.getFunction()) {
                    if (f.getID().equals(functionId)) {
                        function = f;
                        break;
                    }
                }

                if (function == null) {
                    function = new FunctionActionDTO();
                    function.setID(functionId);
                    function.setName(rs.getString("functionName"));
                    function.setNameUnsigned(rs.getString("functionNameUnsigned"));
                    function.setAction(new ArrayList<>());
                    permission.getFunction().add(function);
                }

                // === Thêm action vào function ===
                ActionDTO action = new ActionDTO();
                action.setID(rs.getString("actionID"));
                action.setName(rs.getString("actionName"));
                action.setVnName(rs.getString("actionNameVN"));
                function.getAction().add(action);
                int currentTotal = Integer.parseInt(permission.getTotalFunction());
                permission.setTotalFunction(String.valueOf(currentTotal + 1));

            }
            for (PermissionDTO permission : permissions) {
                System.out.println("Permission: " + permission.getnameUnsinged());
                System.out.println("Permission total: " + permission.getTotalFunction());
                System.out.println("Permission totalAccount: " + permission.getTotalAccount());

                for (FunctionActionDTO f : permission.getFunction()) {
                    System.out.println(f.getNameUnsigned() + "----");
                    for (ActionDTO a : f.getAction()) {
                        System.out.println(a.getName());
                    }
                }
            }
            rs.close();
            ps.close();
            conn.close();

            return permissions;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }     
    }

    public static ActionDTO getActionByID(String id) {
        String query = "SELECT * From action WHERE ActionID = ?;";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new ActionDTO(
                            rs.getString("ActionID"),
                            rs.getString("ActionName"),
                            rs.getString("ActionNameVN")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
