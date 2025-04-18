package GUI;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import BUS.EmployeeBUS;
import BUS.ImportInvoiceDetailBUS;
import BUS.ProductBUS;
import BUS.SupplierBUS;
import DTO.ImportInvoiceDTO;
import DTO.ImportInvoiceDetailDTO;
public class CustomImportInvoicePDF {
    public void export(ImportInvoiceDTO invoice) {
        Document document = new Document(PageSize.A4);
        try {
            String fileName = "HoaDon_Custom_" + invoice.getImportID() + ".pdf";
            File pdfFile = new File(fileName);

            // Nếu file đã tồn tại, xóa trước
            if (pdfFile.exists()) {
                if (!pdfFile.delete()) {
                    System.out.println("Không thể xóa file cũ: " + fileName);
                    return;
                }
            }
            // Tạo file PDF mới
            PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            document.open();

            // Font Unicode hỗ trợ tiếng Việt
            BaseFont bf = BaseFont.createFont(getClass().getClassLoader().getResource("fonts/arial.ttf").toString(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font titleFont = new Font(bf, 18, Font.BOLD);
            Font headerFont = new Font(bf, 12, Font.BOLD);
            Font normalFont = new Font(bf, 12);

            // Tiêu đề
            Paragraph title = new Paragraph("HÓA ĐƠN NHẬP", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(10);
            document.add(title);

            // Ngày đặt
            Paragraph date = new Paragraph("Ngày nhập: " + invoice.getDate(), normalFont);
            date.setAlignment(Element.ALIGN_RIGHT);
            date.setSpacingAfter(10);
            document.add(date);

            // Thông tin khách hàng
            PdfPTable supplierTable = new PdfPTable(2);
            supplierTable.setWidthPercentage(100);
            supplierTable.setSpacingAfter(10);

            String employeeName = EmployeeBUS.getEmployeeByID(invoice.getEmployeeID()).getFullName();
            supplierTable.addCell(getCell("Tên nhân viên:", headerFont));
            supplierTable.addCell(getCell(employeeName, normalFont));

            document.add(supplierTable);

            // Bảng sản phẩm
            PdfPTable productTable = new PdfPTable(5);
            productTable.setWidthPercentage(100);
            productTable.setWidths(new float[]{3, 2, 1, 2, 2});
            productTable.setSpacingAfter(10);

            productTable.addCell(getCell("SẢN PHẨM", headerFont, PdfPCell.ALIGN_CENTER, 5));

            productTable.addCell(getCell("Tên sản phẩm", headerFont));
            productTable.addCell(getCell("Tên nhà cung cấp", headerFont));
            productTable.addCell(getCell("Số lượng", headerFont));
            productTable.addCell(getCell("Đơn giá", headerFont));
            productTable.addCell(getCell("Thành tiền", headerFont));
            ArrayList<ImportInvoiceDetailDTO> importDetailList = ImportInvoiceDetailBUS.getImportInvoiceDetailByImportID(invoice.getImportID());
            for (ImportInvoiceDetailDTO importDetailDTO : importDetailList) {
                ProductBUS productBUS = new ProductBUS();
                String productName = productBUS.getProductByID(importDetailDTO.getProductID()).getProductName(); // Lấy tên sản phẩm từ ID
                String supplierName = SupplierBUS.getSupplierByID(importDetailDTO.getSupplierID()).getSupplierName(); // Lấy tên nhà cung cấp từ ID
                String quantity = String.valueOf(importDetailDTO.getQuantity());
                String price = String.format("%,.0f VND", importDetailDTO.getPrice());
                String totalPrice = String.format("%,.0f VND", importDetailDTO.getTotalPrice());
                String[] row = {productName, supplierName, quantity, price, totalPrice};
                for (String cell : row) {
                    productTable.addCell(getCell(cell, normalFont));
                }
            }

            // Tổng cộng
            PdfPCell totalLabelCell = new PdfPCell(new Phrase("Tổng đơn:", headerFont));
            totalLabelCell.setColspan(4);
            totalLabelCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalLabelCell.setBorder(PdfPCell.NO_BORDER);
            totalLabelCell.setBorderWidthTop(1f);
            totalLabelCell.setBorderColorTop(BaseColor.BLACK);
            productTable.addCell(totalLabelCell);

            PdfPCell totalPriceCell = new PdfPCell(new Phrase(String.format("%,.0f VND", invoice.getTotalPrice()), headerFont));
            totalPriceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalPriceCell.setBorder(PdfPCell.NO_BORDER);
            totalPriceCell.setBorderWidthTop(1f);
            totalPriceCell.setBorderColorTop(BaseColor.BLACK);
            productTable.addCell(totalPriceCell);

            document.add(productTable);

            document.close();
            System.out.println("Đã in hóa đơn PDF thành công!");

            // Mở file sau khi tạo xong
            File pdfFileOpen = new File("HoaDon_Custom_" + invoice.getImportID() + ".pdf");
            if (pdfFileOpen.exists()) {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(pdfFileOpen);
                } else {
                    System.out.println("Desktop is not supported. Không thể mở file tự động.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private PdfPCell getCell(String text, Font font) {
        return getCell(text, font, Element.ALIGN_LEFT, 1);
    }

    private PdfPCell getCell(String text, Font font, int alignment, int colspan) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setColspan(colspan);
        return cell;
    }
}