package BUS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import Connection.DatabaseConnection;
import DAO.ImportInvoiceDetailDAO;
import DTO.ImportInvoiceDetailDTO;
import GUI.Utils;

public class ImportInvoiceDetailBUS {
    private final ImportInvoiceDetailDAO importDetailDAO = new ImportInvoiceDetailDAO();
    public static ArrayList<ImportInvoiceDetailDTO> getAllImportInvoiceDetail() {
        return ImportInvoiceDetailDAO.getAllImportInvoiceDetail();
    }

    public ArrayList<ImportInvoiceDetailDTO> getImportInvoiceDetailByImportID(String id){
        return importDetailDAO.getImportInvoiceDetailByImportID(id);
    }
    
    public boolean insert(ImportInvoiceDetailDTO importInvoice){
        return importDetailDAO.insert(importInvoice);
    }

    public ArrayList<Object[]> loadImportDetails(String importID) {
        return importDetailDAO.loadImportDetails(importID);
    }
}
