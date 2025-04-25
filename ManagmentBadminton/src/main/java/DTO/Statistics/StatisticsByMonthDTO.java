
package DTO.Statistics;


public class StatisticsByMonthDTO {
    private int month;
    private int cost;
    private int income;
    private int profit;

    public StatisticsByMonthDTO() {
    }

    public StatisticsByMonthDTO(int month, int cost, int income, int profit) {
        this.month = month;
        this.cost = cost;
        this.income = income;
        this.profit = profit;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }
    
}
