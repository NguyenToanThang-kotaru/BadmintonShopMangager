
package BUS;

import DAO.StatisticsDAO;
import DTO.Statistics.ProductStatisticsDTO;
import DTO.Statistics.StatisticsByDayDTO;
import DTO.Statistics.StatisticsByMonthDTO;
import DTO.Statistics.StatisticsByYearDTO;
import DTO.Statistics.StatisticsDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StatisticsBUS {
    StatisticsDAO statisticDAO = new StatisticsDAO();

    public StatisticsBUS() {
    }
    
    public ArrayList<StatisticsByYearDTO> getStatisticsByYear(){
        return this.statisticDAO.getStatisticsByYear();
    }
    
    public ArrayList<StatisticsByMonthDTO> getStatisticsByMonth(int year){
        return this.statisticDAO.getStatisticsByMonth(year);
    }
    
    public StatisticsDTO getStatistics(){
        return this.statisticDAO.getStatistics();
    }
    
    public ArrayList<StatisticsByMonthDTO> getStatisticsBy6Month(){
        return this.statisticDAO.getLast6MonthsStatistics();
    }
    
    public ArrayList<StatisticsByDayDTO> getStatisticsByDay(){
        return this.statisticDAO.getStatisticsLast7Days();
    }
    
    public HashMap<String, List<ProductStatisticsDTO>> filterTonKho(String text, int month, int year) {
        HashMap<String, List<ProductStatisticsDTO>> result = StatisticsDAO.getInventoryStatistics(text, month, year);
        return result;
    }

    public int[] getAmount(List<ProductStatisticsDTO> list) {
        int[] result = {0, 0, 0, 0};
        for (int i = 0; i < list.size(); i++) {
            result[0] += list.get(i).getInventoryStartsMonth();
            result[1] += list.get(i).getImportsInMonth();
            result[2] += list.get(i).getExportInMonth();
            result[3] += list.get(i).getInventoryEndMonth();
        }
        return result;
    }
    
}
