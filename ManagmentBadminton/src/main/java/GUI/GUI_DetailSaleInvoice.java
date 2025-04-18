package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import BUS.DetailSaleInvoiceBUS;
import BUS.ProductBUS;
import DTO.DetailSaleInvoiceDTO;
import DTO.ProductDTO;

// import java.util.Date;

public class GUI_DetailSaleInvoice extends JFrame {
    private JTable detailTable;
    private DefaultTableModel tableModel;
    private DetailSaleInvoiceBUS detailOrderBUS;
    private ProductBUS productBUS;

    private String id;
    private String employeeName;
    private String customerName;
    private String date;
    private String totalMoney;

    public GUI_DetailSaleInvoice(String id, String employeeName, String customerName, String date, String totalMoney) {
        this.id = id;
        this.employeeName = employeeName;
        this.customerName = customerName;
        this.date = date;
        this.totalMoney = totalMoney;
        detailOrderBUS = new DetailSaleInvoiceBUS();

        setTitle("Chi Tiết Hóa Đơn");
        setSize(650, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(Color.WHITE);

        // Tiêu đề
        JLabel titleLabel = new JLabel("Chi Tiết Hóa Đơn: " + id, JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(50, 50, 50));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel chứa thông tin hóa đơn + bảng chi tiết
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(Color.WHITE);

        // Panel thông tin hóa đơn
        JPanel infoPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createTitledBorder("Thông Tin Hóa Đơn"));

        JLabel array_label[] = new JLabel[4];
        JLabel array_value[] = new JLabel[4];
        String txt_label[] = {"Tên Khách Hàng:", "Tên nhân viên:", "Tổng Tiền:", "Ngày Xuất:"};
        String txt_value[] = {customerName, employeeName, totalMoney, date};

        for (int i = 0; i < 3; i++) {
            array_label[i] = new JLabel(txt_label[i]);
            array_value[i] = new JLabel(txt_value[i]);
            array_label[i].setFont(new Font("Arial", Font.BOLD, 14));
            array_value[i].setFont(new Font("Arial", Font.PLAIN, 14));
            infoPanel.add(createInfoRow(txt_label[i], txt_value[i]));
        }

        centerPanel.add(infoPanel, BorderLayout.NORTH);

        // Panel bảng chi tiết
        String [] columnNames = {"Mã SP", "Tên SP", "Số Lượng", "Giá"};
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
        List<DetailSaleInvoiceDTO> details = detailOrderBUS.getBySalesID(id);
        tableModel.setRowCount(0);
        for (DetailSaleInvoiceDTO detail : details) {
            ProductDTO product = productBUS.getProductByID(detail.getProduct_id());
            tableModel.addRow(new Object[]{
                product.getProductID(),
                product.getProductName(),
                detail.getQuantity(),
                detail.getPrice()
            });
        }
    }


    private JPanel createInfoRow(String label, String value) {
        JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rowPanel.setBackground(Color.WHITE);

        JLabel rowLabel = new JLabel(label);
        rowLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel rowValue = new JLabel(value);
        rowValue.setFont(new Font("Arial", Font.PLAIN, 14));

        rowPanel.add(rowLabel);
        rowPanel.add(rowValue);

        return rowPanel;
    }
}
