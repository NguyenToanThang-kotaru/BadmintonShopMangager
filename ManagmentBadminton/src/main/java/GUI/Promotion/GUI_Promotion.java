
package GUI.Promotion;

import BUS.PromotionBUS;
import GUI.CustomButton;
import GUI.CustomSearch;
import GUI.CustomTable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;


public class GUI_Promotion extends JPanel{
    private JPanel topPanel, midPanel, botPanel;
    private JTable promoTable;
    private DefaultTableModel tableModel;
    private CustomSearch searchField;
    private CustomButton addButton, saveButton;
    private JTextField maKMField, tenKMField, mucGiamField, ngayBDField, ngayKTField;
    private PromotionBUS promotionBUS;

    public GUI_Promotion() {
        promotionBUS = new PromotionBUS();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(new Color(200, 200, 200));

        // ========== TOP ==========
        topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setPreferredSize(new Dimension(0, 60));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(Color.WHITE);

        searchField = new CustomSearch(275, 40);
        searchField.setBackground(Color.WHITE);
        topPanel.add(searchField, BorderLayout.CENTER);

        addButton = new CustomButton("+ Thêm khuyến mãi");
        topPanel.add(addButton, BorderLayout.EAST);

        // ========== MID ==========
        midPanel = new JPanel(new BorderLayout());
        midPanel.setBackground(Color.WHITE);

        String[] columns = {"STT", "Mã KM", "Tên chương trình", "Ngày bắt đầu", "Ngày kết thúc"};
        CustomTable customTable = new CustomTable(columns);
        promoTable = customTable.getAccountTable();
        tableModel = customTable.getTableModel();

        midPanel.add(customTable, BorderLayout.CENTER);

        // ========== BOT ==========
        botPanel = new JPanel(new GridBagLayout());
        botPanel.setBackground(Color.WHITE);
        botPanel.setBorder(BorderFactory.createTitledBorder("Chi tiết khuyến mãi"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        botPanel.add(new JLabel("Mã khuyến mãi: "), gbc);
        gbc.gridx = 1;
        JLabel lblMaKM = new JLabel("Chọn khuyến mãi");
        botPanel.add(lblMaKM, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        botPanel.add(new JLabel("Tên chương trình: "), gbc);
        gbc.gridx = 1;
        JLabel lblTen = new JLabel("");
        botPanel.add(lblTen, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        botPanel.add(new JLabel("Ngày bắt đầu: "), gbc);
        gbc.gridx = 1;
        JLabel lblStart = new JLabel("");
        botPanel.add(lblStart, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        botPanel.add(new JLabel("Ngày kết thúc: "), gbc);
        gbc.gridx = 1;
        JLabel lblEnd = new JLabel("");
        botPanel.add(lblEnd, gbc);

        gbc.gridx = 1; gbc.gridy = 5;
        saveButton = new CustomButton("💾 Lưu");
        botPanel.add(saveButton, gbc);

        // Sự kiện chọn hàng
        promoTable.getSelectionModel().addListSelectionListener(e -> {
            int row = promoTable.getSelectedRow();
            if (row != -1) {
                maKMField.setText((String) promoTable.getValueAt(row, 1));
                tenKMField.setText((String) promoTable.getValueAt(row, 2));
                mucGiamField.setText(String.valueOf(promoTable.getValueAt(row, 3)));
                ngayBDField.setText((String) promoTable.getValueAt(row, 4));
                ngayKTField.setText((String) promoTable.getValueAt(row, 5));
            }
        });

        // Thêm panel
        add(topPanel);
        add(Box.createVerticalStrut(10));
        add(midPanel);
        add(Box.createVerticalStrut(10));
        add(botPanel);

        loadKhuyenMai();
    }
    
    private void loadKhuyenMai() {
//        List<KhuyenMaiDTO> list = khuyenMaiBUS.getAll();
//        tableModel.setRowCount(0);
//        int stt = 1;
//        for (KhuyenMaiDTO km : list) {
//            tableModel.addRow(new Object[]{
//                stt++, km.getMaKM(), km.getTenKM(), km.getMucGiam(), km.getNgayBatDau(), km.getNgayKetThuc()
//            });
//        }
    }
    
    
}
