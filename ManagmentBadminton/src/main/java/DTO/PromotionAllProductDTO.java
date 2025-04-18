package DTO;

import java.sql.Date;

public class PromotionAllProductDTO extends PromotionDTO{
 
    private double reductionRate;

    public PromotionAllProductDTO(int id, String name, Date startDate, Date endDate, double reductionRate) {
        super(id, name, startDate, endDate);
        this.reductionRate = reductionRate;
    }

    public double getReductionRate() {
        return reductionRate;
    }

    public void setReductionRate(double reductionRate) {
        this.reductionRate = reductionRate;
    }


//    @Override
//    public boolean apDung(SanPham sanPham, HoaDon hoaDon) {
//        return true;
//    }
//
//    @Override
//    public void thucHienKhuyenMai(HoaDon hoaDon) {
//        for (SanPham sp : hd.getDanhSachSanPham()) {
//            double giaGiam = sp.getGia() * (reductionRate / 100.0);
//            sp.setGia(sp.getGia() - giaGiam);
//        }
//    }

    public PromotionAllProductDTO() {
    }
    

}
