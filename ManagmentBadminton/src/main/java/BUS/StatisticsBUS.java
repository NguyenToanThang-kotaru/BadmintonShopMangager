
package BUS;

import DAO.StatisticsDAO;
import DTO.Statistics.StatisticsByDayDTO;
import DTO.Statistics.StatisticsByMonthDTO;
import DTO.Statistics.StatisticsByYearDTO;
import DTO.Statistics.StatisticsDTO;
import java.util.ArrayList;

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
    
}
