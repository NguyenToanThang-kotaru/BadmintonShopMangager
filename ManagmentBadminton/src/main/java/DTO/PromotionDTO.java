package DTO;

import java.sql.Date;

public class PromotionDTO {
    private int id;
    private String name;
    private Date startDate;
    private Date endDate;
    private double discountRate; 

    public PromotionDTO() {
    }

    public PromotionDTO(int id, String name, Date startDate, Date endDate, double discountRate) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.discountRate = discountRate;
    }
    
    public PromotionDTO(String name, Date startDate, Date endDate, double discountRate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.discountRate = discountRate;
    }
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    // Tính giá sau khi giảm
    public double tinhGiaSauGiam(double giaGoc) {
        return giaGoc * (1 - discountRate);
    }

    // Kiểm tra giảm giá còn hiệu lực không (theo ngày hiện tại)
    public boolean conHieuLuc() {
        Date today = new Date(System.currentTimeMillis());
        return (today.equals(startDate) || today.after(startDate)) &&
               (today.equals(endDate) || today.before(endDate));
    }
}