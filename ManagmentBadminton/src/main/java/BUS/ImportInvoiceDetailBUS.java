package BUS;

import java.util.ArrayList;

import DAO.ImportInvoiceDetailDAO;
import DTO.ImportInvoiceDetailDTO;

public class ImportInvoiceDetailBUS {
    private final ImportInvoiceDetailDAO importDetailDAO = new ImportInvoiceDetailDAO();
    public static ArrayList<ImportInvoiceDetailDTO> getAllImportInvoiceDetail() {
        return ImportInvoiceDetailDAO.getAllImportInvoiceDetail();
    }

    public static ArrayList<ImportInvoiceDetailDTO> getImportInvoiceDetailByImportID(String id){
        return ImportInvoiceDetailDAO.getImportInvoiceDetailByImportID(id);
    }
    
    public boolean insert(ImportInvoiceDetailDTO importInvoice){
        return importDetailDAO.insert(importInvoice);
    }

    public ArrayList<Object[]> loadImportDetails(String importID) {
        return importDetailDAO.loadImportDetails(importID);
    }
}
