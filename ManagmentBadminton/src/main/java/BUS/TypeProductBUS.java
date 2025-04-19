package BUS;

import java.util.ArrayList;

import DAO.TypeProductDAO;
import DTO.TypeProductDTO;

public class TypeProductBUS {
    public static ArrayList<TypeProductDTO> getAllTypeProduct() {
        return TypeProductDAO.getAllTypeProduct();
    }

    public static TypeProductDTO getTypeProductByName(String id) {
        System.out.println("TypeProductBUS.getTypeProductByID: " + id);
        return TypeProductDAO.getTypeProductByName(id);
    }
}
