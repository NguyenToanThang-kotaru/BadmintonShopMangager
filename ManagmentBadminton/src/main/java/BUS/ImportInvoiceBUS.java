package BUS;

import java.util.ArrayList;

import DAO.ImportInvoiceDAO;
import DTO.ImportInvoiceDTO;

public class ImportInvoiceBUS {
    private final ImportInvoiceDAO importDetailDAO = new ImportInvoiceDAO();
    public static ArrayList<ImportInvoiceDTO> getAllImportInvoice() {
        return ImportInvoiceDAO.getAllImportInvoice();
    }

    public ImportInvoiceDTO getImportInvoiceByID(String id){
        return importDetailDAO.getImportInvoiceByID(id);
    }
    
    public boolean insert(ImportInvoiceDTO importInvoice){
        return importDetailDAO.insert(importInvoice);
    }
}
