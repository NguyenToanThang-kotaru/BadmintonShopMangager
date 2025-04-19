package DTO;

import java.sql.Date;

public class PromotionProductDTO extends PromotionDTO{
    public enum TypePromotion { GIAM_GIA, TANG_QUA }
    
    private String idProduct;
    private TypePromotion type;
    
    private double money;
    private double percent;
    
    private String gift;

    public PromotionProductDTO(int id, String name, Date startDate, Date endDate, String idProduct, TypePromotion type, double money, double percent, String gift) {
        super(id, name, startDate, endDate);
        this.idProduct = idProduct;
        this.type = type;
        this.money = money;
        this.percent = percent;
        this.gift = gift;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public TypePromotion getType() {
        return type;
    }

    public void setType(TypePromotion type) {
        this.type = type;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public String getGift() {
        return gift;
    }

    public void setGift(String gift) {
        this.gift = gift;
    }
    
    

   
    


//    @Override
//    public boolean apDung(Object sanPham, Object hoaDon) {
//        return sp.getMaSP().equals(idProduct);
//    }
//
//    @Override
//    public void thucHienKhuyenMai(Object hoaDon) {
//        for (SanPham sp : hd.getDanhSachSanPham()) {
//            if (apDung(sp, hd)) {
//                if (type == TypePromotion.GIAM_GIA) {
//                    double tienGiam = (money > 0) ? money :
//                                      sp.getGia() * (percent / 100.0);
//                    sp.setGia(sp.getGia() - tienGiam);
//                } else if (type == TypePromotion.TANG_QUA) {
//                    hd.themSanPham(gift);
//                }
//            }
//        }
//    }

    public PromotionProductDTO() {
    }
    
    
    
}
