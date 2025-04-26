package DAO;

import Connection.DatabaseConnection;
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
import java.util.Comparator;
import java.util.List;

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
                + "    SELECT DISTINCT MONTH(Date) AS month FROM import_invoice WHERE YEAR(Date) = ? "
                + "    UNION "
                + "    SELECT DISTINCT MONTH(Date) AS month FROM sales_invoice WHERE YEAR(Date) = ? "
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
            stmt.setInt(3, year);
            stmt.setInt(4, year);
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
        List<Integer> last6MonthNumbers = getLast6MonthNumbers();

        int currentYear = YearMonth.now().getYear();
        int previousYear = currentYear - 1;

        // Lấy dữ liệu từ cả năm hiện tại và năm trước
        ArrayList<StatisticsByMonthDTO> allData = new ArrayList<>();
        allData.addAll(getStatisticsByMonth(currentYear));
        allData.addAll(getStatisticsByMonth(previousYear));

        // Lọc theo danh sách tháng
        ArrayList<StatisticsByMonthDTO> filteredData = new ArrayList<>();
        for (StatisticsByMonthDTO dto : allData) {
            if (last6MonthNumbers.contains(dto.getMonth())) {
                filteredData.add(dto);
            }
        }

        // Sắp xếp theo thứ tự tháng từ bé đến lớn
        filteredData.sort(Comparator.comparingInt(StatisticsByMonthDTO::getMonth));

        return filteredData;
    }

    public List<Integer> getLast6MonthNumbers() {
        List<Integer> result = new ArrayList<>();
        YearMonth current = YearMonth.now();

        for (int i = 1; i <= 6; i++) {
            result.add(current.minusMonths(i).getMonthValue());
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

}
