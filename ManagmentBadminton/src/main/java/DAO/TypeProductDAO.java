package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Connection.DatabaseConnection;
import DTO.TypeProductDTO;
public class TypeProductDAO {
    public static ArrayList<TypeProductDTO> getAllTypeProduct() {
        ArrayList<TypeProductDTO> typeProductList = new ArrayList<>();
        String query = "SELECT * FROM type_product";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    typeProductList.add(new TypeProductDTO(
                        rs.getString("TypeID"),
                        rs.getString("TypeName")
                    ));
                }
            }
        } catch (SQLException es) {
            es.printStackTrace();
        }
        return typeProductList;
    }

    public static TypeProductDTO getTypeProductByName(String name) {
        String sql = "SELECT * FROM type_product WHERE TypeName = ?";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql)){
            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                TypeProductDTO typeProduct = new TypeProductDTO();
                typeProduct.setTypeID(rs.getString("TypeID"));
                typeProduct.setTypeName(rs.getString("TypeName"));
                return typeProduct;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}