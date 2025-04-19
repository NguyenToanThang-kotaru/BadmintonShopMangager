/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import BUS.AccountBUS;
import BUS.PermissionBUS;
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
 * @author Kieu Mai
 */
public class PermissionDAO {
    
    public static Boolean add_Permission(PermissionDTO per) {
        try {
            String sql = "INSERT INTO employee_rank (`RankID`, `RankName`, `RankNameUnsigned`, `IsDeleted`) VALUES (?, ?, ?, 0)";
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, generateNewPermissionID());
            ps.setString(2, per.getName());
            ps.setString(3, per.getnameUnsinged());
            int result = ps.executeUpdate();

            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Boolean add_FunctionAction(PermissionDTO per) {
        try {
            String sql = "INSERT INTO `function_detail` (`RankID`, `FunctionID`, `ActionID`, `IsDeleted`) VALUES (?, ?, ?, 0)";
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            int result = 0;
            for (FunctionActionDTO func : per.getFunction()) {
                for (ActionDTO action : func.getAction()) {
                    ps.setString(1, per.getID());
                    ps.setString(2, func.getID());
                    ps.setString(3, action.getID());
                    ps.executeUpdate(); // chạy từng lần
                    result++;
                }
            }

            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

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
        String sql = "SELECT er.rankID, er.rankName, "
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

    public static PermissionDTO getPermissionByID(String rankId) {
        PermissionDTO permission = null;

        try {
            String sql = "SELECT er.rankID, er.rankName,  "
                    + "f.functionID, f.functionName, f.functionNameUnsigned, "
                    + "a.actionID, a.actionName, a.actionNameVN "
                    + "FROM employee_rank er "
                    + "LEFT JOIN function_detail fd ON fd.rankID = er.rankID AND fd.IsDeleted = 0 "
                    + "LEFT JOIN `function` f ON fd.functionID = f.functionID "
                    + "LEFT JOIN action a ON fd.actionID = a.actionID "
                    + "WHERE er.rankID = ? AND er.IsDeleted = 0 "
                    + "ORDER BY f.functionID, a.actionID";

            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, rankId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (permission == null) {
                    permission = new PermissionDTO();
                    permission.setID(rs.getString("rankID"));
                    permission.setName(rs.getString("rankName"));
                    permission.setFunction(new ArrayList<>());
                }

                String functionId = rs.getString("functionID");
                if (functionId == null) {
                    continue;
                }

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

                String actionId = rs.getString("actionID");
                if (actionId == null) {
                    continue;
                }

                // Thêm action nếu có
                ActionDTO action = new ActionDTO();
                action.setID(actionId);
                action.setName(rs.getString("actionName"));
                action.setVnName(rs.getString("actionNameVN"));
                function.getAction().add(action);
            }

            rs.close();
            ps.close();
//            conn.close();

        } catch (Exception e) {
            e.printStackTrace(); // Hoặc log bằng logger nếu bạn dùng log4j/slf4j
        }

        return permission;
    }

    public static ArrayList<PermissionDTO> getAllPermissions() {
        try {
            Connection conn = DatabaseConnection.getConnection();

            // 1. Lấy danh sách tất cả quyền không bị xóa
            String rankSql = "SELECT * FROM employee_rank WHERE IsDeleted = 0";
            PreparedStatement psRank = conn.prepareStatement(rankSql);
            ResultSet rsRank = psRank.executeQuery();

            ArrayList<PermissionDTO> permissions = new ArrayList<>();

            while (rsRank.next()) {
                PermissionDTO permission = new PermissionDTO();
                permission.setID(rsRank.getString("rankID"));
                permission.setName(rsRank.getString("rankName"));
                permission.setnameUnsinged(rsRank.getString("RankNameUnsigned"));
                permission.setTotalAccount(AccountBUS.countAccountFromPermission(permission.getID()));
                permission.setFunction(new ArrayList<>());
                permission.setTotalFunction("0"); // mặc định là 0
                permissions.add(permission);
            }

            rsRank.close();
            psRank.close();

            // 2. Lấy tất cả function_detail để ghép vào quyền tương ứng
            String detailSql = "SELECT fd.rankID, f.functionID, f.functionName, f.functionNameUnsigned, "
                    + "a.actionID, a.actionName, a.actionNameVN "
                    + "FROM function_detail fd "
                    + "JOIN `function` f ON fd.functionID = f.functionID "
                    + "JOIN action a ON fd.actionID = a.actionID "
                    + "WHERE fd.IsDeleted = 0 "
                    + "ORDER BY fd.rankID, f.functionID, a.actionID";
            PreparedStatement psDetail = conn.prepareStatement(detailSql);
            ResultSet rsDetail = psDetail.executeQuery();

            while (rsDetail.next()) {
                String rankId = rsDetail.getString("rankID");
                String functionId = rsDetail.getString("functionID");

                // === Tìm permission theo rankID ===
                PermissionDTO permission = null;
                for (PermissionDTO p : permissions) {
                    if (p.getID().equals(rankId)) {
                        permission = p;
                        break;
                    }
                }

                if (permission == null) {
                    continue; // phòng hờ
                }
                // === Tìm hoặc tạo FunctionActionDTO ===
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
                    function.setName(rsDetail.getString("functionName"));
                    function.setNameUnsigned(rsDetail.getString("functionNameUnsigned"));
                    function.setAction(new ArrayList<>());
                    permission.getFunction().add(function);
                }

                // === Thêm action vào function ===
                ActionDTO action = new ActionDTO();
                action.setID(rsDetail.getString("actionID"));
                action.setName(rsDetail.getString("actionName"));
                action.setVnName(rsDetail.getString("actionNameVN"));
                function.getAction().add(action);

                // Cập nhật lại số lượng chức năng (sau khi có thêm function mới)
                permission.setTotalFunction(String.valueOf(permission.getFunction().size()));
            }

            rsDetail.close();
            psDetail.close();
            conn.close();

            return permissions;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<String> getAllFunctionName() {
        ArrayList<String> functionNames = new ArrayList<>();

        try {
            String sql = "SELECT functionName FROM function";
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                functionNames.add(rs.getString("functionName"));
            }

            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return functionNames;
    }

    public static ArrayList<FunctionActionDTO> getAllFunctionAction() {
        ArrayList<FunctionActionDTO> functions = new ArrayList<>();

        try {
            // 1. Lấy tất cả function
            String sqlFunction = "SELECT functionID, functionName, functionNameUnsigned FROM function";
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement psFunction = conn.prepareStatement(sqlFunction);
            ResultSet rsFunction = psFunction.executeQuery();

            // 2. Lấy tất cả action (1 lần duy nhất)
            ArrayList<ActionDTO> allActions = PermissionBUS.getAllAction(); // Giả sử đã có sẵn

            // 3. Tạo danh sách FunctionActionDTO
            while (rsFunction.next()) {
                FunctionActionDTO function = new FunctionActionDTO();
                function.setID(rsFunction.getString("functionID"));
                function.setName(rsFunction.getString("functionName"));
                function.setNameUnsigned(rsFunction.getString("functionNameUnsigned"));
                function.setAction(allActions);

                functions.add(function);
            }

            rsFunction.close();
            psFunction.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return functions;
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

    public static ArrayList<ActionDTO> getAllAction() {
        String query = "SELECT * From action;";
        ArrayList<ActionDTO> array = new ArrayList<ActionDTO>();
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ActionDTO acc = new ActionDTO(
                            rs.getString("ActionID"),
                            rs.getString("ActionName"),
                            rs.getString("ActionNameVN")
                    );
                    array.add(acc);
                }
                return array;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String generateNewPermissionID() {
        String query = "SELECT `RankID` FROM `employee_rank` ORDER BY `RankID` DESC LIMIT 1";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                String lastID = rs.getString("RankID"); // Ví dụ: "R05"

                // Cắt bỏ "R", chỉ lấy số
                int number = Integer.parseInt(lastID.substring(1));

                // Tạo ID mới với định dạng RXX
                return String.format("R%02d", number + 1); // Ví dụ: "R06"
            }

        } catch (SQLException e) {
            System.out.println("Lỗi khi tạo mã quyền mới: " + e.getMessage());
            e.printStackTrace();
        }

        return "R01"; // Nếu không có quyền nào, bắt đầu từ "R01"
    }

}
