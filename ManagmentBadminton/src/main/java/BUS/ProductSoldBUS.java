package BUS;

import java.util.List;

import DAO.ProductSoldDAO;
import DTO.ProductSoldDTO;



public class ProductSoldBUS {
    private ProductSoldDAO productSoldDAO;

    public ProductSoldBUS() {
        productSoldDAO = new ProductSoldDAO();
    }

    // Get ProductSoldDTO by DetailSaleInvoiceID
    public List<ProductSoldDTO> getByDetailSaleInvoiceID(String salesInvoiceDetailID) {
        return productSoldDAO.getByDetailSaleInvoiceID(salesInvoiceDetailID);
    }

    // Get ProductSoldDTO by Series
    public ProductSoldDTO getBySeries(String series) {
        return productSoldDAO.getBySeries(series);
    }

    // Add ProductSoldDTO
    public boolean add(ProductSoldDTO productSoldDTO) {
        return productSoldDAO.add(productSoldDTO);
    }
}
