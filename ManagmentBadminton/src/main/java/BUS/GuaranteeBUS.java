/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.GuaranteeDAO;
import DTO.GuaranteeDTO;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author ADMIN
 */
public class GuaranteeBUS {

    public static GuaranteeDTO getGuarantee(String BaohanhID) {
        GuaranteeDAO dao = new GuaranteeDAO();
        return dao.getGuarantee(BaohanhID);
    }

    public static ArrayList<GuaranteeDTO> getAllGuarantee() {
        return GuaranteeDAO.getAllGuarantee();
    }

    public static void updateGuarantee(GuaranteeDTO guarantee) {
        GuaranteeDAO dao = new GuaranteeDAO();
        dao.updateGuarantee(guarantee);
    }

    public static Boolean addGuarantee(String Series) {
        return GuaranteeDAO.addGuarantee(Series);
    }

    public static ArrayList<GuaranteeDTO> searchGuarantees(String keyword) {
        return GuaranteeDAO.searchGuarantees(keyword);
    }
}
