package DTO;

public class PromotionBillDTO extends PromotionDTO{
    
    private double minTotal;
    private double discount;
    private String gift;

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

}
