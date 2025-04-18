package BUS;

import java.util.ArrayList;

import DAO.DetailSaleInvoiceDAO;
import DTO.DetailSaleInvoiceDTO;

public class DetailSaleInvoiceBUS {
    public ArrayList<DetailSaleInvoiceDTO> getAll() {
        return DetailSaleInvoiceDAO.getAll();
    }
    public ArrayList<DetailSaleInvoiceDTO> getBySalesID(String saleID) {
        return DetailSaleInvoiceDAO.getBySalesID(saleID);
    }
    public boolean add(DetailSaleInvoiceDTO detailSaleInvoice) {
        return DetailSaleInvoiceDAO.add(detailSaleInvoice);
    }
}
