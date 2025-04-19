package DTO;

import java.sql.Date;

public class PromotionBillDTO extends PromotionDTO{
    
    private double minTotal;
    private double discount;
    private String gift;

    public PromotionBillDTO(int id, String name, Date startDate, Date endDate, double minTotal, double discount, String gift) {
        super(id, name, startDate, endDate);
        this.minTotal = minTotal;
        this.discount = discount;
        this.gift = gift;
    }

    public double getMinTotal() {
        return minTotal;
    }

    public void setMinTotal(double minTotal) {
        this.minTotal = minTotal;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getGift() {
        return gift;
    }

    public void setGift(String gift) {
        this.gift = gift;
    }

    
    

//    @Override
//    public boolean apDung(Object sanPham, Object hoaDon) {
//        return hd.tinhTongTien() >= minTotal;
//    }
//
//    @Override
//    public void thucHienKhuyenMai(Object hoaDon) {
//        if (hd.tinhTongTien() >= minTotal) {
//            if (discount > 0) {
//                hd.giamTruHoaDon(discount);
//            } else if (gift != null) {
//                hd.themSanPham(gift);
//            }
//        }
//    }

    public PromotionBillDTO() {
    }

}
