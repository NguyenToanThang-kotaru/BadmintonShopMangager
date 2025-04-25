package DTO;

import java.sql.Date;

public abstract class PromotionDTO {
    protected int id;
    protected String name;
    protected Date startDate;
    protected Date endDate;

    public PromotionDTO() {
    }

    public PromotionDTO(int id, String name, Date startDate, Date endDate) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
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

//    public abstract boolean apDung(SanPham sanPham, HoaDon hoaDon);
//    public abstract void thucHienKhuyenMai(HoaDon hoaDon);

    public int getDiscountRate() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
