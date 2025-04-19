package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import BUS.ProductSoldBUS;
import DTO.ProductSoldDTO;

// import java.util.Date;

public class GUI_SerialNumber extends JFrame {
    private JTable detailTable;
    private DefaultTableModel tableModel;
    private ProductSoldBUS productBUS;

    private String productId;
    private String detailSaleInvoiceID;


    public GUI_SerialNumber(String productId, String detailSaleInvoiceID) {
        this.productId = productId;
        this.detailSaleInvoiceID = detailSaleInvoiceID;
        productBUS = new ProductSoldBUS();

        setTitle("Bảng Chi Tiết Mã Serial");
        setSize(650, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(Color.WHITE);

        // Tiêu đề
        JLabel titleLabel = new JLabel("Chi Tiết Mã Serial Của SP: " + productId, JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(50, 50, 50));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel chứa thông tin hóa đơn + bảng chi tiết
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(Color.WHITE);

        // Panel bảng chi tiết
        String [] columnNames = {"Mã CTHĐ", "Mã Serial"};
        tableModel = new DefaultTableModel(columnNames, 0);
        detailTable = new JTable(tableModel);
        detailTable.setFont(new Font("Arial", Font.PLAIN, 14));
        detailTable.setRowHeight(25);
        detailTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        detailTable.getTableHeader().setBackground(new Color(30, 144, 255));
        detailTable.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(detailTable);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Panel chứa nút đóng
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);

        JButton closeButton = new JButton("Đóng");
        closeButton.setFont(new Font("Arial", Font.BOLD, 14));
        closeButton.setBackground(new Color(255, 69, 0));
        closeButton.setForeground(Color.WHITE);
        closeButton.setPreferredSize(new Dimension(100, 35));
        closeButton.addActionListener(e -> dispose());

        buttonPanel.add(closeButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
        setUndecorated(true);
        loadDetailOrder();

    }

    private void loadDetailOrder() {
        List<ProductSoldDTO> details = productBUS.getByProductIDAndDetailSaleInvoiceID(productId, detailSaleInvoiceID);
        tableModel.setRowCount(0);
        for (ProductSoldDTO detail : details) {
            tableModel.addRow(new Object[]{
                detail.getDetailSaleInvoiceID(),
                detail.getSeries()
            });
        }
    }

    // private JPanel createInfoRow(String label, String value) {
    //     JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    //     rowPanel.setBackground(Color.WHITE);

    //     JLabel rowLabel = new JLabel(label);
    //     rowLabel.setFont(new Font("Arial", Font.BOLD, 14));

    //     JLabel rowValue = new JLabel(value);
    //     rowValue.setFont(new Font("Arial", Font.PLAIN, 14));

    //     rowPanel.add(rowLabel);
    //     rowPanel.add(rowValue);

    //     return rowPanel;
    // }
}
