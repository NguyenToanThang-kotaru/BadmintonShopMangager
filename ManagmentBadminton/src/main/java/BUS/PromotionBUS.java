
package BUS;

import DAO.PromotionDAO;
import DTO.PromotionDTO;
import java.util.List;


public class PromotionBUS {
    
    private PromotionDAO promotionDAO;

    public PromotionBUS() {
        promotionDAO = new PromotionDAO();
    }
    
    
    public List<PromotionDTO> getAllPromotion() {
        return PromotionDAO.getAllPromotions();
    }

    public PromotionDTO getPromotionByID(int maKM) {
        return PromotionDAO.getPromotionById(maKM);
    }

    public boolean addPromotion(PromotionDTO km) {
        return PromotionDAO.addPromotion(km);
    }

    public boolean updatePromotion(PromotionDTO km) {
        return PromotionDAO.updatePromotion(km);
    }

    public boolean deletePromotion(int maKM) {
        return PromotionDAO.deletePromotionById(maKM);
    }
    
}