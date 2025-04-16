package BUS;

import java.util.ArrayList;

import DAO.ImportInvoiceDetailDAO;
import DTO.ImportInvoiceDetailDTO;

public class ImportInvoiceDetailBUS {
    private final ImportInvoiceDetailDAO importDetailDAO = new ImportInvoiceDetailDAO();
    public static ArrayList<ImportInvoiceDetailDTO> getAllImportInvoiceDetail() {
        return ImportInvoiceDetailDAO.getAllImportInvoiceDetail();
    }

    public ImportInvoiceDetailDTO getImportInvoiceDetailByID(String id){
        return importDetailDAO.getImportInvoiceDetailByID(id);
    }
    
    public boolean insert(ImportInvoiceDetailDTO importInvoice){
        return importDetailDAO.insert(importInvoice);
    }
}
