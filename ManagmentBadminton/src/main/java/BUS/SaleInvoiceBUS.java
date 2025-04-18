package BUS;

import java.util.ArrayList;

import DAO.SaleInvoiceDAO;
import DTO.SaleInvoiceDTO;

public class SaleInvoiceBUS {
    public ArrayList<SaleInvoiceDTO> getAll() {
        return SaleInvoiceDAO.getAll();
    }
    public ArrayList<SaleInvoiceDTO> getById(String id) {
        return SaleInvoiceDAO.getById(id);
    }
    public ArrayList<SaleInvoiceDTO> getByCustomerId(String customerId) {
        return SaleInvoiceDAO.getByCustomerId(customerId);
    }
    public ArrayList<SaleInvoiceDTO> getByEmployeeId(String employeeId) {
        return SaleInvoiceDAO.getByEmployeeId(employeeId);
    }
    public ArrayList<SaleInvoiceDTO> getByDate(java.util.Date date) {
        return SaleInvoiceDAO.getByDate(date);
    }
    public boolean add(SaleInvoiceDTO saleInvoice) {
        return SaleInvoiceDAO.add(saleInvoice);
    }
    public boolean update(SaleInvoiceDTO saleInvoice) {
        return SaleInvoiceDAO.update(saleInvoice);
    }
}
