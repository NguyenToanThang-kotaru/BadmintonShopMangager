package DTO.Statistics;

public class StatisticsDTO {
    private long cost;
    private long income;
    private long profit;

    public StatisticsDTO() {
    }

    public StatisticsDTO(long cost, long income, long profit) {
        this.cost = cost;
        this.income = income;
        this.profit = profit;
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
