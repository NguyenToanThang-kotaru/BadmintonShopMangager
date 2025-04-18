package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Connection.DatabaseConnection;
import DTO.ProductDTO;

// Lớp này dùng để kết nối database và lấy dữ liệu sản phẩm
public class ProductDAO {

    public static Boolean addProduct(ProductDTO product) {
        String findMaLoaiSQL = "SELECT TypeID FROM type_product WHERE TypeName = ?";
        String findMaNCCSQL = "SELECT SupplierID FROM supplier WHERE SupplierName = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement findMaLoaiStmt = conn.prepareStatement(findMaLoaiSQL); PreparedStatement findMaNCCStmt = conn.prepareStatement(findMaNCCSQL)) {

            findMaLoaiStmt.setString(1, product.getTL()); // TL là tên loại
            ResultSet rs = findMaLoaiStmt.executeQuery();
            String maLoai = null;

            findMaNCCStmt.setString(1, product.gettenNCC()); // tenNCC là tên nhà cung cấp
            ResultSet rs2 = findMaNCCStmt.executeQuery();
            String maNCC = null;

            if (rs.next()) {
                maLoai = rs.getString("TypeID");
            } else {
                System.out.println("Không tìm thấy mã loại cho tên loại: " + product.getTL());
                return false; // Dừng lại nếu không tìm thấy mã loại
            }

            if (rs2.next()) {
                maNCC = rs2.getString("SupplierID");
            } else {
                System.out.println("Không tìm thấy mã nhà cung cấp cho tên nhà cung cấp: " + product.gettenNCC());
                return false; // Dừng lại nếu không tìm thấy mã NCC
            }

            //Kiểm tra sản phẩm trùng tên đã bị xóa mềm ớ ớ á á
            String checkDeletedSQL = "SELECT ProductID FROM product WHERE ProductName = ? AND IsDeleted = 1";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkDeletedSQL)) {
                checkStmt.setString(1, product.getProductName());
                ResultSet checkRS = checkStmt.executeQuery();
                if (checkRS.next()) {
                    String existingID = checkRS.getString("ProductID");

                    // Lật cờ IsDeleted thành 0
                    String restoreSQL = "UPDATE product SET IsDeleted = 0 WHERE ProductID = ?";
                    try (PreparedStatement restoreStmt = conn.prepareStatement(restoreSQL)) {
                        restoreStmt.setString(1, existingID);
                        restoreStmt.executeUpdate();
                    }

                    System.out.println("Khôi phục sản phẩm đã bị xóa mềm với ID: " + existingID);
                    return true;
                }
            }

            // Tiếp tục thêm sản phẩm...
            String sql = "INSERT INTO product (ProductID, ProductName, Price, Quantity, SupplierID, TypeID, ProductImg, IsDeleted) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {

                String newID = generateNewProductID(); // Tạo ID mới

                stmt.setString(1, newID);
                stmt.setString(2, product.getProductName());
                stmt.setDouble(3, Double.parseDouble(product.getGia())); // Chuyển String thành Double
                stmt.setInt(4, Integer.parseInt(product.getSoluong())); // Chuyển String thành Int
                stmt.setString(5, maNCC);
                stmt.setString(6, maLoai);
                stmt.setString(7, product.getAnh());
                stmt.setInt(8, 0); // Gán mặc định là 0

                stmt.executeUpdate();
                System.out.println("Thêm sản phẩm thành công với ID: " + newID);
                return true;

            } catch (SQLException e) {
                System.out.println("Lỗi thêm sản phẩm: " + e.getMessage());
                e.printStackTrace();
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Lỗi truy vấn mã loại: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static Boolean deleteProduct(String productID) {
        String query = "UPDATE product SET IsDeleted = 1 WHERE ProductID = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, productID);
            stmt.executeUpdate();
            System.out.println("Xóa sản phẩm thành công");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String generateNewProductID() {
        String query = "SELECT ProductID FROM product ORDER BY ProductID DESC LIMIT 1";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                String lastID = rs.getString("ProductID"); // Ví dụ: "SP005"

                int number = Integer.parseInt(lastID.substring(2));

                // Tạo ID mới với định dạng SPXXX
                return String.format("P%02d", number + 1); // Ví dụ: "SP006"
            }

        } catch (SQLException e) {
            System.out.println("Lỗi khi tạo mã sản phẩm mới: " + e.getMessage());
            e.printStackTrace();
        }

        return "P01"; // Nếu không có sản phẩm nào, bắt đầu từ "SP001"
    }

    // Lấy thông tin của một sản phẩm
    public static ProductDTO getProduct(String ProductID) {
        String query = "SELECT sp.ProductID, sp.ProductName, sp.Price, sp.Quantity, sp.SupplierID, "
                + "sp.TypeID, lsp.TypeName, sp.ProductImg, ncc.SupplierName "
                + "FROM product sp "
                + "JOIN type_product lsp ON sp.TypeID = lsp.TypeID "
                + "LEFT JOIN supplier ncc ON sp.SupplierID = ncc.SupplierID "
                + "WHERE sp.ProductID = ? AND sp.IsDeleted = 0";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, ProductID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String supplierName = rs.getString("SupplierName");
                    if (supplierName == null) {
                        supplierName = "Nhà cung cấp đã xóa";
                    }
                    return new ProductDTO(
                            rs.getString("ProductID"),
                            rs.getString("ProductName"),
                            rs.getString("Price"),
                            rs.getString("Quantity"),
                            rs.getString("SupplierID"),
                            rs.getString("TypeID"),
                            rs.getString("TypeName"),
                            rs.getString("ProductImg"),
                            supplierName
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<String> getAllCategoryNames() {
        ArrayList<String> categoryList = new ArrayList<>();
        String query = "SELECT TypeName FROM type_product";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                categoryList.add(rs.getString("TypeName"));  // Lưu tên loại vào danh sách
            }
        } catch (SQLException e) {
            System.out.println("Lỗi lấy danh sách loại sản phẩm: " + e.getMessage());
            e.printStackTrace();
        }
        return categoryList;
    }

    public static ArrayList<String> getAllNCCNames() {
        ArrayList<String> NCCList = new ArrayList<>();
        String query = "SELECT SupplierName FROM supplier WHERE IsDeleted = 0";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                NCCList.add(rs.getString("SupplierName"));  // Lưu tên nhà cung cấp vào danh sách
            }
        } catch (SQLException e) {
            System.out.println("Lỗi lấy danh sách nhà cung cấp: " + e.getMessage());
            e.printStackTrace();
        }
        return NCCList;
    }

    // Lấy danh sách tất cả sản phẩm
    public static ArrayList<ProductDTO> getAllProducts() {
        ArrayList<ProductDTO> products = new ArrayList<>();
        String query = "SELECT sp.ProductID, sp.ProductName, sp.Price, sp.Quantity, sp.SupplierID, "
                + "sp.TypeID, lsp.TypeName, sp.ProductImg, ncc.SupplierName "
                + "FROM product sp "
                + "JOIN type_product lsp ON sp.TypeID = lsp.TypeID "
                + "LEFT JOIN supplier ncc ON sp.SupplierID = ncc.SupplierID "
                + "WHERE sp.IsDeleted = 0"; // Chỉ lọc sản phẩm chưa bị xóa
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String supplierName = rs.getString("SupplierName");
                if (supplierName == null) {
                    supplierName = "Nhà cung cấp đã xóa"; // Giá trị mặc định
                }
                products.add(new ProductDTO(
                        rs.getString("ProductID"),
                        rs.getString("ProductName"),
                        rs.getString("Price"),
                        rs.getString("Quantity"),
                        rs.getString("SupplierID"),
                        rs.getString("TypeID"),
                        rs.getString("TypeName"),
                        rs.getString("ProductImg"),
                        supplierName
                ));
            }
            System.out.println("Lấy danh sách sản phẩm thành công.");
        } catch (Exception e) {
            System.out.println("Lỗi lấy danh sách sản phẩm: " + e.getMessage());
            e.printStackTrace();
        }
        return products;
    }

    // Cập nhật thông tin sản phẩm
    public static void updateProduct(ProductDTO product) {
        String findMaLoaiSQL = "SELECT TypeID FROM type_product WHERE TypeName = ?";
        String findMaNCCSQL = "SELECT SupplierID FROM supplier WHERE SupplierName = ?";
        String updateProductSQL = "UPDATE product SET ProductName = ?, Price = ?, Quantity = ?, SupplierID = ?, TypeID = ?, ProductImg = ? WHERE ProductID = ? AND IsDeleted = 0";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement findMaLoaiStmt = conn.prepareStatement(findMaLoaiSQL); PreparedStatement findMaNCCStmt = conn.prepareStatement(findMaNCCSQL); PreparedStatement updateProductStmt = conn.prepareStatement(updateProductSQL)) {

            // Tìm TypeID từ TypeName
            findMaLoaiStmt.setString(1, product.getTL());
            ResultSet rs = findMaLoaiStmt.executeQuery();
            String maLoai = null;

            findMaNCCStmt.setString(1, product.gettenNCC());
            ResultSet rs2 = findMaNCCStmt.executeQuery();
            String maNCC = null;

            if (rs.next()) {
                maLoai = rs.getString("TypeID");  // Lấy TypeID dưới dạng String
            } else {
                System.out.println("Không tìm thấy mã loại cho tên loại: " + product.getTL());
                return; // Không tiếp tục cập nhật nếu không tìm thấy
            }

            if (rs2.next()) {
                maNCC = rs2.getString("SupplierID");
            } else {
                System.out.println("Không tìm thấy mã NCC cho tên NCC: " + product.gettenNCC());
                return; // Không tiếp tục cập nhật nếu không tìm thấy
            }

            updateProductStmt.setString(1, product.getProductName());
            updateProductStmt.setDouble(2, Double.parseDouble(product.getGia())); // Chuyển String thành Double
            updateProductStmt.setInt(3, Integer.parseInt(product.getSoluong())); // Chuyển String thành Int
            updateProductStmt.setString(4, maNCC);
            updateProductStmt.setString(5, maLoai); // Cập nhật TypeID tìm được
            updateProductStmt.setString(6, product.getAnh());
            updateProductStmt.setString(7, product.getProductID());

            int rowsUpdated = updateProductStmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Cập nhật sản phẩm thành công.");
            } else {
                System.out.println("Không có sản phẩm nào được cập nhật. Kiểm tra lại ID!");
            }

        } catch (SQLException e) {
            System.out.println("Lỗi cập nhật sản phẩm: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Lấy đường dẫn ảnh sản phẩm
    public static String getProductImage(String productID) {
        String imagePath = null;
        String query = "SELECT ProductImg FROM product WHERE ProductID = ? AND IsDeleted = 0";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, productID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    imagePath = rs.getString("ProductImg"); // Lấy tên file ảnh
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return imagePath;
    }

    public boolean updateProductQuantity(String productId, int quantity) {
        String query = "UPDATE product SET Quantity = Quantity + ? WHERE ProductID = ? AND IsDeleted = 0";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, quantity);
            stmt.setString(2, productId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Failed to update product quantity: " + e.getMessage());
            return false;
        }
    }

    public static ArrayList<ProductDTO> searchProducts(String keyword) {
        ArrayList<ProductDTO> products = new ArrayList<>();

        String query = "SELECT sp.ProductID, sp.ProductName, sp.Price, sp.Quantity, "
                + "sp.SupplierID, sp.TypeID, "
                + "lsp.TypeName, sp.ProductImg, ncc.SupplierName "
                + "FROM product sp "
                + "JOIN type_product lsp ON sp.TypeID = lsp.TypeID "
                + "JOIN supplier ncc ON sp.SupplierID = ncc.SupplierID "
                + "WHERE sp.IsDeleted = 0 AND "
                + "(sp.ProductID LIKE ? OR sp.ProductName LIKE ? OR lsp.TypeName LIKE ? OR ncc.SupplierName LIKE ?)";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            String searchKeyword = "%" + keyword + "%";
            stmt.setString(1, searchKeyword);
            stmt.setString(2, searchKeyword);
            stmt.setString(3, searchKeyword);
            stmt.setString(4, searchKeyword);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(new ProductDTO(
                            rs.getString("ProductID"),
                            rs.getString("ProductName"),
                            rs.getString("Price"),
                            rs.getString("Quantity"),
                            rs.getString("SupplierID"),
                            rs.getString("TypeID"),
                            rs.getString("TypeName"),
                            rs.getString("ProductImg"),
                            rs.getString("SupplierName")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    public static ArrayList<String> getSerialsForProduct(String productID) {
        ArrayList<String> serials = new ArrayList<>();
        String query = "SELECT Series FROM product_detail WHERE ProductID = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, productID);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    serials.add(rs.getString("Series"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi lấy danh sách serial: " + e.getMessage());
            e.printStackTrace();
        }
        return serials;
    }

    public boolean insert(ProductDTO product){
        boolean result = false;
        String sql = "Insert into product(ProductID, ProductName, ProductImg, Quantity, SupplierID, TypeID, Price, IsDeleted) values(?,?,?,?,?,?,?,?)";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql)){
            pst.setString(1, product.getProductID());
            pst.setString(2, product.getProductName());
            pst.setString(3, product.getAnh());
            pst.setInt(4, Integer.parseInt(product.getSoluong()));
            pst.setString(5, product.getMaNCC());
            pst.setString(6, product.getML());
            pst.setDouble(7, Double.parseDouble(product.getGia()));
            pst.setInt(8, 0);
            // In thu cau truy van de kiem tra
            System.out.println(pst.toString());

            if(pst.executeUpdate()>=1)
                result = true;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return result;
    }

}
