package GUI.Promotion;

import BUS.PromotionBUS;
import DTO.PromotionDTO;
import GUI.CustomButton;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class Form_Promotion extends JDialog {

    private JTextField txtTen, txtMucGiam;
    private CustomDatePicker dateStart, dateEnd;
    private CustomButton btnSave, btnCancel;
    private PromotionBUS promotionBUS;

    public Form_Promotion(GUI_Promotion parent, PromotionDTO promotion) {
        super((Frame) SwingUtilities.getWindowAncestor(parent), promotion == null ? "Thêm Khuyến Mãi" : "Sửa Khuyến Mãi", true);
        setSize(500, 400);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        promotionBUS = new PromotionBUS(); // Khởi tạo BUS

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel title = new JLabel(promotion == null ? "THÊM KHUYẾN MÃI" : "SỬA KHUYẾN MÃI");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(new Color(52, 73, 94));
        add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;

        txtTen = new CustomTextField(20);
        txtMucGiam = new CustomTextField(20);
        dateStart = new CustomDatePicker();
        dateEnd = new CustomDatePicker();

        if (promotion != null) {
            txtTen.setText(promotion.getName());
            txtMucGiam.setText(String.valueOf(promotion.getDiscountRate() * 100));
            dateStart.setDate(promotion.getStartDate().toLocalDate());
            dateEnd.setDate(promotion.getEndDate().toLocalDate());
        }

        addComponent("Tên chương trình:", txtTen, gbc);
        addComponent("Tỷ lệ giảm (%):", txtMucGiam, gbc);
        addComponent("Ngày bắt đầu:", dateStart, gbc);
        addComponent("Ngày kết thúc:", dateEnd, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnSave = new CustomButton(promotion == null ? "Thêm" : "Cập nhật");
        btnCancel = new CustomButton("Hủy");
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);

        btnCancel.addActionListener(e -> dispose());

        btnSave.addActionListener((ActionEvent e) -> {
            String ten = txtTen.getText().trim();
            String mucGiamStr = txtMucGiam.getText().trim();

            if (ten.isEmpty() || mucGiamStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin.");
                return;
            }

            try {
                double mucGiam = Double.parseDouble(mucGiamStr);
                if (mucGiam < 0 || mucGiam > 100) {
                    JOptionPane.showMessageDialog(this, "Tỷ lệ giảm phải từ 0 đến 100.");
                    return;
                }

                LocalDate start = dateStart.getDate();
                LocalDate end = dateEnd.getDate();

                PromotionDTO newPromotion = new PromotionDTO(
                        ten,
                        java.sql.Date.valueOf(start),
                        java.sql.Date.valueOf(end),
                        mucGiam / 100.0
                );

                if (promotion == null) {
                    if (promotionBUS.addPromotion(newPromotion)) {
                        JOptionPane.showMessageDialog(this, "Thêm khuyến mãi thành công!");
                        parent.loadKhuyenMai();
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Thêm thất bại. Mã khuyến mãi có thể đã tồn tại.");
                    }
                } else {

                    PromotionDTO upPromotion = new PromotionDTO(
                            promotion.getId(),
                            ten,
                            java.sql.Date.valueOf(start),
                            java.sql.Date.valueOf(end),
                            mucGiam / 100.0
                    );

                    if (promotionBUS.updatePromotion(upPromotion)) {
                        JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                        parent.loadKhuyenMai();
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Cập nhật thất bại.");
                    }
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Tỷ lệ giảm phải là số.");
            }
        });
    }

    private void addComponent(String label, JComponent component, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel(label), gbc);

        gbc.gridx = 1;
        add(component, gbc);
    }

}
