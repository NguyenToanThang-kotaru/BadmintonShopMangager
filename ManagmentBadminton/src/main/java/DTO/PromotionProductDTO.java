package DTO;

public class PromotionProductDTO extends PromotionDTO{
    public enum TypePromotion { GIAM_GIA, TANG_QUA }
    
    private String idProduct;
    private TypePromotion type;
    
    private double money;
    private double percent;
    
    private String gift;


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
    
    
    
}
