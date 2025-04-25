
package DTO.Statistics;


public class StatisticsByYearDTO {
    private int year;
    private long cost;
    private long income;
    private long profit;

    public StatisticsByYearDTO() {
    }

    public StatisticsByYearDTO(int year, long cost, long income, long profit) {
        this.year = year;
        this.cost = cost;
        this.income = income;
        this.profit = profit;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public long getIncome() {
        return income;
    }

    public void setIncome(long income) {
        this.income = income;
    }

    public long getProfit() {
        return profit;
    }

    public void setProfit(long profit) {
        this.profit = profit;
    }
    
    
}
