package DAO;

import Connection.DatabaseConnection;
import DTO.Statistics.ProductStatisticsDTO;
import DTO.Statistics.StatisticsByDayDTO;
import DTO.Statistics.StatisticsByMonthDTO;
import DTO.Statistics.StatisticsByYearDTO;
import DTO.Statistics.StatisticsDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StatisticsDAO {

    public StatisticsDTO getStatistics() {
        String query = "SELECT "
                + "IFNULL((SELECT SUM(TotalPrice) FROM import_invoice_detail), 0) AS cost, "
                + "IFNULL((SELECT SUM(TotalPrice) FROM sales_invoice_detail), 0) AS income, "
                + "IFNULL((SELECT SUM(TotalPrice) FROM sales_invoice_detail), 0) - "
                + "IFNULL((SELECT SUM(TotalPrice) FROM import_invoice_detail), 0) AS profit";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new StatisticsDTO(
                            rs.getLong("cost"),
                            rs.getLong("income"),
                            rs.getLong("profit")
                    );
                }
            }
        } catch (SQLException es) {
            es.printStackTrace();
        }
        return new StatisticsDTO(0, 0, 0);
    }

    public ArrayList<StatisticsByYearDTO> getStatisticsByYear() {
        ArrayList<StatisticsByYearDTO> result = new ArrayList<>();
        String query = "SELECT year_table.year, "
                + "       IFNULL(import_data.cost, 0) AS cost, "
                + "       IFNULL(sales_data.income, 0) AS income, "
                + "       IFNULL(sales_data.income, 0) - IFNULL(import_data.cost, 0) AS profit "
                + "FROM ( "
                + "    SELECT DISTINCT YEAR(Date) AS year FROM import_invoice "
                + "    UNION "
                + "    SELECT DISTINCT YEAR(Date) AS year FROM sales_invoice "
                + ") AS year_table "
                + "LEFT JOIN ( "
                + "    SELECT YEAR(ii.Date) AS year, SUM(iid.TotalPrice) AS cost "
                + "    FROM import_invoice ii "
                + "    JOIN import_invoice_detail iid ON ii.ImportID = iid.ImportID "
                + "    GROUP BY YEAR(ii.Date) "
                + ") AS import_data ON year_table.year = import_data.year "
                + "LEFT JOIN ( "
                + "    SELECT YEAR(si.Date) AS year, SUM(sid.TotalPrice) AS income "
                + "    FROM sales_invoice si "
                + "    JOIN sales_invoice_detail sid ON si.SalesID = sid.SalesID "
                + "    GROUP BY YEAR(si.Date) "
                + ") AS sales_data ON year_table.year = sales_data.year "
                + "ORDER BY year_table.year";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    result.add(new StatisticsByYearDTO(
                            rs.getInt("year"),
                            rs.getLong("cost"),
                            rs.getLong("income"),
                            rs.getLong("profit")
                    ));
                }
            }
        } catch (SQLException es) {
            es.printStackTrace();
        }
        return result;
    }

    public ArrayList<StatisticsByMonthDTO> getStatisticsByMonth(int year) {
        ArrayList<StatisticsByMonthDTO> result = new ArrayList<>();
        String query = "SELECT month_table.month, "
                + "       IFNULL(import_data.cost, 0) AS cost, "
                + "       IFNULL(sales_data.income, 0) AS income, "
                + "       IFNULL(sales_data.income, 0) - IFNULL(import_data.cost, 0) AS profit "
                + "FROM ( "
                + "    SELECT 1 AS month UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 "
                + "    UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10 UNION SELECT 11 UNION SELECT 12 "
                + ") AS month_table "
                + "LEFT JOIN ( "
                + "    SELECT MONTH(ii.Date) AS month, SUM(iid.TotalPrice) AS cost "
                + "    FROM import_invoice ii "
                + "    JOIN import_invoice_detail iid ON ii.ImportID = iid.ImportID "
                + "    WHERE YEAR(ii.Date) = ? "
                + "    GROUP BY MONTH(ii.Date) "
                + ") AS import_data ON month_table.month = import_data.month "
                + "LEFT JOIN ( "
                + "    SELECT MONTH(si.Date) AS month, SUM(sid.TotalPrice) AS income "
                + "    FROM sales_invoice si "
                + "    JOIN sales_invoice_detail sid ON si.SalesID = sid.SalesID "
                + "    WHERE YEAR(si.Date) = ? "
                + "    GROUP BY MONTH(si.Date) "
                + ") AS sales_data ON month_table.month = sales_data.month "
                + "ORDER BY month_table.month";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, year);
            stmt.setInt(2, year);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    result.add(new StatisticsByMonthDTO(
                            rs.getInt("month"),
                            rs.getInt("cost"),
                            rs.getInt("income"),
                            rs.getInt("profit")
                    ));
                }
            }
        } catch (SQLException es) {
            es.printStackTrace();
        }
        return result;
    }

    public ArrayList<StatisticsByMonthDTO> getLast6MonthsStatistics() {
        ArrayList<YearMonth> last6Months = getLast6YearMonths(); // danh sách 6 tháng gần nhất (đã có năm)
        ArrayList<StatisticsByMonthDTO> allData = new ArrayList<>();

        // Lấy danh sách các năm cần truy vấn
        Set<Integer> yearsToQuery = new HashSet<>();
        for (YearMonth ym : last6Months) {
            yearsToQuery.add(ym.getYear());
        }

        // Gộp tất cả dữ liệu các năm cần thiết
        for (int year : yearsToQuery) {
            allData.addAll(getStatisticsByMonth(year));
        }

        // Tạo Map<YearMonth, StatisticsByMonthDTO> để tra cứu nhanh
        Map<YearMonth, StatisticsByMonthDTO> dataMap = new HashMap<>();
        for (int year : yearsToQuery) {
            for (StatisticsByMonthDTO dto : getStatisticsByMonth(year)) {
                YearMonth ym = YearMonth.of(year, dto.getMonth());
                dataMap.put(ym, dto);
            }
        }

        // Lấy đúng 6 tháng gần nhất từ map, theo đúng thứ tự
        ArrayList<StatisticsByMonthDTO> result = new ArrayList<>();
        for (YearMonth ym : last6Months) {
            StatisticsByMonthDTO dto = dataMap.getOrDefault(
                    ym, new StatisticsByMonthDTO(ym.getMonthValue(), 0, 0, 0));
            result.add(dto);
        }

        return result;
    }

    public ArrayList<YearMonth> getLast6YearMonths() {
        ArrayList<YearMonth> result = new ArrayList<>();
        YearMonth current = YearMonth.now();
        for (int i = 5; i >= 0; i--) { // từ 5 tháng trước đến tháng hiện tại
            result.add(current.minusMonths(i));
        }
        return result;
    }

    public ArrayList<StatisticsByDayDTO> getStatisticsLast7Days() {
        ArrayList<StatisticsByDayDTO> result = new ArrayList<>();
        String query = "SELECT day_table.day, "
                + "       IFNULL(import_data.cost, 0) AS cost, "
                + "       IFNULL(sales_data.income, 0) AS income, "
                + "       IFNULL(sales_data.income, 0) - IFNULL(import_data.cost, 0) AS profit "
                + "FROM ( "
                + "    SELECT DISTINCT DATE(Date) AS day FROM import_invoice WHERE Date >= CURDATE() - INTERVAL 6 DAY "
                + "    UNION "
                + "    SELECT DISTINCT DATE(Date) AS day FROM sales_invoice WHERE Date >= CURDATE() - INTERVAL 6 DAY "
                + ") AS day_table "
                + "LEFT JOIN ( "
                + "    SELECT DATE(ii.Date) AS day, SUM(iid.TotalPrice) AS cost "
                + "    FROM import_invoice ii "
                + "    JOIN import_invoice_detail iid ON ii.ImportID = iid.ImportID "
                + "    WHERE ii.Date >= CURDATE() - INTERVAL 6 DAY "
                + "    GROUP BY DATE(ii.Date) "
                + ") AS import_data ON day_table.day = import_data.day "
                + "LEFT JOIN ( "
                + "    SELECT DATE(si.Date) AS day, SUM(sid.TotalPrice) AS income "
                + "    FROM sales_invoice si "
                + "    JOIN sales_invoice_detail sid ON si.SalesID = sid.SalesID "
                + "    WHERE si.Date >= CURDATE() - INTERVAL 6 DAY "
                + "    GROUP BY DATE(si.Date) "
                + ") AS sales_data ON day_table.day = sales_data.day "
                + "ORDER BY day_table.day";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    result.add(new StatisticsByDayDTO(
                            rs.getDate("day"),
                            rs.getInt("cost"),
                            rs.getInt("income"),
                            rs.getInt("profit")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static HashMap<String, List<ProductStatisticsDTO>> getInventoryStatistics(String txt, int month, int year) {
        HashMap<String, List<ProductStatisticsDTO>> result = new HashMap<>();
        String keyword = "%" + txt + "%";

        String query = "SELECT "
                + "    p.ProductID, "
                + "    p.ProductName, "
                + "    IFNULL(( "
                + "        (SELECT SUM(iid.Quantity) "
                + "         FROM import_invoice_detail iid "
                + "         JOIN import_invoice ii ON iid.ImportID = ii.ImportID "
                + "         WHERE iid.ProductID = p.ProductID AND MONTH(ii.Date) < ? AND YEAR(ii.Date) = ?) "
                + "        - "
                + "        (SELECT SUM(sid.Quantity) "
                + "         FROM sales_invoice_detail sid "
                + "         JOIN sales_invoice si ON sid.SalesID = si.SalesID "
                + "         WHERE sid.ProductID = p.ProductID AND MONTH(si.Date) < ? AND YEAR(si.Date) = ?) "
                + "    ), 0) AS inventoryStartsMonth, "
                + "    IFNULL(( "
                + "        SELECT SUM(iid.Quantity) "
                + "        FROM import_invoice_detail iid "
                + "        JOIN import_invoice ii ON iid.ImportID = ii.ImportID "
                + "        WHERE iid.ProductID = p.ProductID AND MONTH(ii.Date) = ? AND YEAR(ii.Date) = ? "
                + "    ), 0) AS importsInMonth, "
                + "    IFNULL(( "
                + "        SELECT SUM(sid.Quantity) "
                + "        FROM sales_invoice_detail sid "
                + "        JOIN sales_invoice si ON sid.SalesID = si.SalesID "
                + "        WHERE sid.ProductID = p.ProductID AND MONTH(si.Date) = ? AND YEAR(si.Date) = ? "
                + "    ), 0) AS exportInMonth "
                + "FROM product p "
                + "WHERE p.ProductName LIKE ? OR p.ProductID LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            // Set parameters
            stmt.setInt(1, month);
            stmt.setInt(2, year);
            stmt.setInt(3, month);
            stmt.setInt(4, year);
            stmt.setInt(5, month);
            stmt.setInt(6, year);
            stmt.setInt(7, month);
            stmt.setInt(8, year);
            stmt.setString(9, keyword);
            stmt.setString(10, keyword);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String id = rs.getString("ProductID");
                    String name = rs.getString("ProductName");
                    int inventoryStart = rs.getInt("inventoryStartsMonth");
                    int imported = rs.getInt("importsInMonth");
                    int exported = rs.getInt("exportInMonth");
                    int inventoryEnd = inventoryStart + imported - exported;

                    ProductStatisticsDTO dto = new ProductStatisticsDTO(id, name, inventoryStart, imported, exported, inventoryEnd);
                    result.computeIfAbsent(id, k -> new ArrayList<>()).add(dto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

}
