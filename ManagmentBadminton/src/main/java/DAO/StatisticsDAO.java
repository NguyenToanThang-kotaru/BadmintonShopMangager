package DAO;

import Connection.DatabaseConnection;
import DTO.Statistics.StatisticsByMonthDTO;
import DTO.Statistics.StatisticsByYearDTO;
import DTO.Statistics.StatisticsDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StatisticsDAO {

    public ArrayList<StatisticsDTO> getStatistics() {
        ArrayList<StatisticsDTO> result = new ArrayList<>();
        String query = "SELECT "
                + "IFNULL((SELECT SUM(TotalPrice) FROM import_invoice_detail), 0) AS cost, "
                + "IFNULL((SELECT SUM(TotalPrice) FROM sales_invoice_detail), 0) AS income, "
                + "IFNULL((SELECT SUM(TotalPrice) FROM sales_invoice_detail), 0) - "
                + "IFNULL((SELECT SUM(TotalPrice) FROM import_invoice_detail), 0) AS profit";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    result.add(new StatisticsDTO(
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

}
