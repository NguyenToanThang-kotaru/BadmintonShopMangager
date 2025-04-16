package BUS;

import java.util.ArrayList;

import DAO.SupplierDAO;
import DTO.SupplierDTO;

public class SupplierBUS {
    private final SupplierDAO supplierDAO = new SupplierDAO();
    private ArrayList<SupplierDTO> supplierList = new ArrayList<>();

    public SupplierBUS() {
        supplierList = SupplierDAO.getAllSupplier();
    }

    public ArrayList<SupplierDTO> getAllSupplier(){
        return SupplierDAO.getAllSupplier();
    }

    public boolean insert(SupplierDTO supplier){
        if(supplierDAO.insert(supplier)){
            supplierList.add(supplier);
            return true;
        }
        return false;
    }

    public boolean update(SupplierDTO supplier){
        if(supplier == null) return false;
        if(supplierDAO.update(supplier)){
            supplierList.set(getIndex(supplier.getSupplierID()), supplier);
            return true;
        }
        return false;
    }

    public boolean remove(SupplierDTO supplier){
        if(supplier == null) return false;
        if(supplierDAO.remove(supplier)){
            supplierList.remove(supplier);
            return true;
        }
        return false;
    }

    public boolean remove(String id){
        return remove(getSupplierByID(id));
    }

    public int getIndex(String id){
        for(int i = 0; i < supplierList.size(); i++)
            if(supplierList.get(i).getSupplierID().equals(id))
                return i;
        return -1;
    }

    public SupplierDTO getSupplierByID(String id){
        return supplierDAO.getSupplierByID(id);
    }

    public String generateSupplierID(){
        return supplierDAO.generateSupplierID();
    }
}
