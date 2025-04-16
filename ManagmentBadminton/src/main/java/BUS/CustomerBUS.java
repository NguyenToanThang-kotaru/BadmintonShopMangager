package BUS;

import DAO.CustomerDAO;
import DTO.CustomerDTO;

import java.util.ArrayList;

public class CustomerBUS {
    public CustomerDTO getById(String id) {
        return CustomerDAO.getById(id);
    }
    public CustomerDTO getByName(String name) {
        return CustomerDAO.getByName(name);
    }
    public ArrayList<CustomerDTO> getAll() {
        return CustomerDAO.getAll();
    }
    public CustomerDTO getByPhone(String phone) {
        return CustomerDAO.getByPhone(phone);
    }
    public boolean add(CustomerDTO customer) {
        return CustomerDAO.add(customer);
    }
    public boolean update(CustomerDTO customer) {
        return CustomerDAO.update(customer);
    }
    public boolean delete(String id) {
        return CustomerDAO.delete(id);
    }
}
