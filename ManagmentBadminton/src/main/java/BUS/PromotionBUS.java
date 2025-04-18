
package BUS;

import DAO.PromotionDAO;
import DTO.PromotionDTO;
import java.util.List;


public class PromotionBUS {
    public List<PromotionDTO> getAllPromotion() {
        return PromotionDAO.getAllPromotion();
    }

    public PromotionDTO getPromotionByID(String maKM) {
        return PromotionDAO.getKhuyenMaiByID(maKM);
    }

    public boolean addPromotion(PromotionDTO km) {
        return PromotionDAO.insert(km);
    }

    public boolean updatePromotion(PromotionDTO km) {
        return PromotionDAO.update(km);
    }

    public boolean deletePromotion(String maKM) {
        return PromotionDAO.delete(maKM);
    }
    
}
