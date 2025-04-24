package BUS;

import java.util.ArrayList;

import DAO.ProductDetailDAO;
import DTO.ProductDetailDTO;

public class ProductDetailBUS {
    ProductDetailDAO productDetailDAO = new ProductDetailDAO();
    public ArrayList<ProductDetailDTO> getAllProductDetail() {
        return ProductDetailDAO.getAllProductDetail();
    }
    public ArrayList<ProductDetailDTO> getProductDetailByProductID(String id) {
        return ProductDetailDAO.getProductDetailByProductID(id);
    }
    public boolean insert(ProductDetailDTO productDetail) {
        return productDetailDAO.insert(productDetail);
    }
    public boolean delete(String series) {
        return productDetailDAO.delete(series);
    }
    public String generateNewSeries() {
        return productDetailDAO.generateNextSeries();
    }
    
}
