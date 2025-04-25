package DAO;

import Connection.DatabaseConnection;
import DTO.PromotionAllProductDTO;
import DTO.PromotionBillDTO;
import DTO.PromotionDTO;
import DTO.PromotionProductDTO;
import DTO.PromotionProductDTO.TypePromotion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

public class PromotionDAO {
    
    public static ArrayList<PromotionDTO> getAllPromotion() {
        ArrayList<PromotionDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM Promotion";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                String loaiKM = rs.getString("LoaiKM");
                PromotionDTO km = null;

                switch (loaiKM) {
                    case "tatca":
                        km = new PromotionAllProductDTO(
                                rs.getInt("MaKM"),
                                rs.getString("TenKM"),
                                rs.getDate("NgayBatDau"),
                                rs.getDate("NgayKetThuc"),
                                rs.getDouble("MucGiam")
                        );
                        break;

                    case "hoadon":
                        km = new PromotionBillDTO(
                                rs.getInt("MaKM"),
                                rs.getString("TenKM"),
                                rs.getDate("NgayBatDau"),
                                rs.getDate("NgayKetThuc"),
                                rs.getDouble("GiaGiam"),
                                rs.getDouble("MucGiam"),
                                rs.getString("QuaTang")
                        );
                        break;

                    case "sanpham":
                        TypePromotion type = TypePromotion.valueOf(rs.getString("PromotionStyle"));

                        km = new PromotionProductDTO(
                                rs.getInt("MaKM"),
                                rs.getString("TenKM"),
                                rs.getDate("NgayBatDau"),
                                rs.getDate("NgayKetThuc"),
                                rs.getString("MaSP"),
                                type, // "GIAM_GIA" hoặc "TANG_QUA"
                                rs.getDouble("GiaGiam"),
                                rs.getDouble("MucGiam"),
                                rs.getString("QuaTang")
                        );
                        break;
                }

                if (km != null) list.add(km);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static PromotionDTO getKhuyenMaiByID(String maKM) {
        String sql = "SELECT * FROM Promotion WHERE MaKM = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, maKM);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String loaiKM = rs.getString("LoaiKM");

                switch (loaiKM) {
                    case "tatca":
                        return new PromotionAllProductDTO(
                                rs.getInt("MaKM"),
                                rs.getString("TenKM"),
                                rs.getDate("NgayBatDau"),
                                rs.getDate("NgayKetThuc"),
                                rs.getDouble("MucGiam")
                        );
                    case "hoadon":
                        return new PromotionBillDTO(
                                rs.getInt("MaKM"),
                                rs.getString("TenKM"),
                                rs.getDate("NgayBatDau"),
                                rs.getDate("NgayKetThuc"),
                                rs.getDouble("GiaGiam"),
                                rs.getDouble("MucGiam"),
                                rs.getString("QuaTang")
                        );
                    case "sanpham":
                        TypePromotion type = TypePromotion.valueOf(rs.getString("PromotionStyle"));

                        return new PromotionProductDTO(
                                rs.getInt("MaKM"),
                                rs.getString("TenKM"),
                                rs.getDate("NgayBatDau"),
                                rs.getDate("NgayKetThuc"),
                                rs.getString("MaSP"),
                                type,
                                rs.getDouble("GiaGiam"),
                                rs.getDouble("MucGiam"),
                                rs.getString("QuaTang")
                        );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean insert(PromotionDTO km) {
        String sql = "INSERT INTO Promotion (MaKM, TenKM, NgayBatDau, NgayKetThuc, LoaiKM, MaSP, KieuKM, MucGiam, GiaGiam, QuaTang) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, km.getId());
            pst.setString(2, km.getName());
            pst.setDate(3, new java.sql.Date(km.getStartDate().getTime()));
            pst.setDate(4, new java.sql.Date(km.getEndDate().getTime()));

            if (km instanceof PromotionAllProductDTO) {
                PromotionAllProductDTO p = (PromotionAllProductDTO) km;
                pst.setString(5, "tatca");
                pst.setNull(6, Types.VARCHAR);
                pst.setNull(7, Types.VARCHAR);
                pst.setDouble(8, p.getReductionRate());
                pst.setNull(9, Types.DOUBLE);
                pst.setNull(10, Types.VARCHAR);

            } else if (km instanceof PromotionBillDTO) {
                PromotionBillDTO p = (PromotionBillDTO) km;
                pst.setString(5, "hoadon");
                pst.setNull(6, Types.VARCHAR);
                pst.setNull(7, Types.VARCHAR);
                pst.setDouble(8, p.getDiscount());
                pst.setDouble(9, p.getMinTotal());
                pst.setString(10, p.getGift());

            } else if (km instanceof PromotionProductDTO) {
                PromotionProductDTO p = (PromotionProductDTO) km;
                pst.setString(5, "sanpham");
                pst.setString(6, p.getIdProduct());
                pst.setString(7, p.getType().name());
                pst.setDouble(8, p.getPercent());
                pst.setDouble(9, p.getMoney());
                pst.setString(10, p.getGift());
            }

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean update(PromotionDTO km) {
        String sql = "UPDATE Promotion SET TenKM=?, NgayBatDau=?, NgayKetThuc=?, MaSP=?, KieuKM=?, MucGiam=?, GiaGiam=?, QuaTang=? WHERE MaKM=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, km.getName());
            pst.setDate(2, new java.sql.Date(km.getStartDate().getTime()));
            pst.setDate(3, new java.sql.Date(km.getEndDate().getTime()));

            if (km instanceof PromotionAllProductDTO) {
                PromotionAllProductDTO p = (PromotionAllProductDTO) km;
                pst.setNull(4, Types.VARCHAR);
                pst.setNull(5, Types.VARCHAR);
                pst.setDouble(6, p.getReductionRate());
                pst.setNull(7, Types.DOUBLE);
                pst.setNull(8, Types.VARCHAR);

            } else if (km instanceof PromotionBillDTO) {
                PromotionBillDTO p = (PromotionBillDTO) km;
                pst.setNull(4, Types.VARCHAR);
                pst.setNull(5, Types.VARCHAR);
                pst.setDouble(6, p.getDiscount());
                pst.setDouble(7, p.getMinTotal());
                pst.setString(8, p.getGift());

            } else if (km instanceof PromotionProductDTO) {
                PromotionProductDTO p = (PromotionProductDTO) km;
                pst.setString(4, p.getIdProduct());
                pst.setString(5, p.getType().name());
                pst.setDouble(6, p.getPercent());
                pst.setDouble(7, p.getMoney());
                pst.setString(8, p.getGift());
            }

            pst.setInt(9, km.getId());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean delete(String maKM) {
        String sql = "DELETE FROM Promotion WHERE MaKM = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, maKM);
            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
