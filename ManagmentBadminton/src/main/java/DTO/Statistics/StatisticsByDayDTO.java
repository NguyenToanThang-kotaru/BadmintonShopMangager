package DTO.Statistics;

import java.sql.Date;

public class StatisticsByDayDTO {

    private Date day;
    private int cost;
    private int income;
    private int profit;

    public StatisticsByDayDTO(Date day, int cost, int income, int profit) {
        this.day = day;
        this.cost = cost;
        this.income = income;
        this.profit = profit;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
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
