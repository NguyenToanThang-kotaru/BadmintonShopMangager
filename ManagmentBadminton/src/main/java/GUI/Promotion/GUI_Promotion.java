package GUI.Promotion;

import BUS.PromotionBUS;
import DAO.PromotionDAO;
import DTO.PromotionDTO;
import GUI.CustomButton;
import GUI.CustomSearch;
import GUI.CustomTable;
import GUI.CustomScrollPane;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class GUI_Promotion extends JPanel {

    private JPanel topPanel, midPanel, botPanel;
    private JTable promoTable;
    private DefaultTableModel tableModel;
    private CustomSearch searchField;
    private CustomButton addButton, editButton, deleteButton, reloadButton;
    private JLabel lblMaKM, lblTen, lblStart, lblEnd, lblDiscount;
    private PromotionBUS promotionBUS;
    private PromotionDTO selectedPromotion;

    public GUI_Promotion() {
        promotionBUS = new PromotionBUS();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(new Color(200, 200, 200));

        // ========== TOP PANEL ==========
        topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setPreferredSize(new Dimension(0, 60));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(Color.WHITE);

        reloadButton = new CustomButton("Tải lại trang");
        topPanel.add(reloadButton, BorderLayout.WEST);

        searchField = new CustomSearch(275, 20);
        searchField.setBackground(Color.WHITE);
        topPanel.add(searchField, BorderLayout.CENTER);

        addButton = new CustomButton("+ Thêm Khuyến Mãi");
        topPanel.add(addButton, BorderLayout.EAST);

        // ========== MID PANEL ==========
        midPanel = new JPanel(new BorderLayout());
        midPanel.setBackground(Color.WHITE);

        String[] columns = {"Mã KM", "Tên chương trình", "Mức giảm (%)", "Ngày bắt đầu", "Ngày kết thúc"};
        CustomTable customTable = new CustomTable(columns);
        promoTable = customTable.getAccountTable();
        tableModel = customTable.getTableModel();

        CustomScrollPane scrollPane = new CustomScrollPane(promoTable);
        midPanel.add(scrollPane, BorderLayout.CENTER);

        // ========== BOT PANEL ==========
        botPanel = new JPanel(new GridBagLayout());
        botPanel.setBackground(Color.WHITE);
        botPanel.setBorder(BorderFactory.createTitledBorder("Chi Tiết Khuyến Mãi"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        botPanel.add(new JLabel("Mã khuyến mãi: "), gbc);
        gbc.gridx = 1;
        lblMaKM = new JLabel("Chọn khuyến mãi");
        botPanel.add(lblMaKM, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        botPanel.add(new JLabel("Tên chương trình: "), gbc);
        gbc.gridx = 1;
        lblTen = new JLabel("");
        botPanel.add(lblTen, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        botPanel.add(new JLabel("Mức giảm (%): "), gbc);
        gbc.gridx = 1;
        lblDiscount = new JLabel("");
        botPanel.add(lblDiscount, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        botPanel.add(new JLabel("Ngày bắt đầu: "), gbc);
        gbc.gridx = 1;
        lblStart = new JLabel("");
        botPanel.add(lblStart, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        botPanel.add(new JLabel("Ngày kết thúc: "), gbc);
        gbc.gridx = 1;
        lblEnd = new JLabel("");
        botPanel.add(lblEnd, gbc);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setOpaque(false);

        deleteButton = new CustomButton("Xoá");
        editButton = new CustomButton("Sửa");

        buttonPanel.add(deleteButton, BorderLayout.WEST);
        buttonPanel.add(editButton, BorderLayout.EAST);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        botPanel.add(buttonPanel, gbc);

        // ========== SỰ KIỆN ==========
        promoTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = promoTable.getSelectedRow();
            if (selectedRow != -1) {
                // Lấy dữ liệu từ bảng và chuyển đổi sang String một cách an toàn
                String maKM = promoTable.getValueAt(selectedRow, 1).toString();
                String ten = promoTable.getValueAt(selectedRow, 2).toString();
                String discount = promoTable.getValueAt(selectedRow, 3).toString();
                String start = promoTable.getValueAt(selectedRow, 4).toString();
                String end = promoTable.getValueAt(selectedRow, 5).toString();

                int id = Integer.parseInt(maKM);
                // Lấy dữ liệu khuyến mãi từ DAO hoặc BUS
                selectedPromotion = promotionBUS.getPromotionByID(id);

                // Hiển thị dữ liệu trên giao diện
                lblMaKM.setText(maKM);
                lblTen.setText(ten);
                lblDiscount.setText(discount);
                lblStart.setText(start);
                lblEnd.setText(end);

                // Thêm button panel vào botPanel nếu cần
                botPanel.add(buttonPanel, gbc);
            }
        });

        add(topPanel);
        add(Box.createVerticalStrut(10));
        add(midPanel);
        add(Box.createVerticalStrut(10));
        add(botPanel);

        loadKhuyenMai();

        addButton.addActionListener(e -> {
            Form_Promotion form = new Form_Promotion(this, null);
            form.setVisible(true);
        });

        editButton.addActionListener(e -> {
            if (selectedPromotion != null) {
                Form_Promotion form = new Form_Promotion(this, selectedPromotion);
                form.setVisible(true);
            }
        });

        deleteButton.addActionListener(e -> {
            if (selectedPromotion != null) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Bạn có chắc chắn muốn xóa khuyến mãi này?",
                        "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean deleted = promotionBUS.deletePromotion(selectedPromotion.getId());
                    if (deleted) {
                        JOptionPane.showMessageDialog(this, "Xoá thành công!");
                        loadKhuyenMai();
                    } else {
                        JOptionPane.showMessageDialog(this, "Xoá thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        reloadButton.addActionListener(e -> {
            loadKhuyenMai();
        });

        searchField.setSearchListener(e -> {
            List<PromotionDTO> list = promotionBUS.search(searchField.getText());
            tableModel.setRowCount(0);
            for (PromotionDTO km : list) {
                tableModel.addRow(new Object[]{
                    km.getId(), km.getName(), km.getDiscountRate() * 100, km.getStartDate(), km.getEndDate()
                });
            }
            lblMaKM.setText("Chọn khuyến mãi");
            lblTen.setText("");
            lblDiscount.setText("");
            lblStart.setText("");
            lblEnd.setText("");
        });
    }

    public void loadKhuyenMai() {
        List<PromotionDTO> list = promotionBUS.getAllPromotion();
        tableModel.setRowCount(0);
        for (PromotionDTO km : list) {
            tableModel.addRow(new Object[]{
                km.getId(), km.getName(), km.getDiscountRate() * 100, km.getStartDate(), km.getEndDate()
            });
        }
        lblMaKM.setText("Chọn khuyến mãi");
        lblTen.setText("");
        lblDiscount.setText("");
        lblStart.setText("");
        lblEnd.setText("");
    }
}
