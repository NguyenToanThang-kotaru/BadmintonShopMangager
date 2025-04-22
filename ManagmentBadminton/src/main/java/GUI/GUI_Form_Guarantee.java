package GUI;

import DTO.GuaranteeDTO;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import DAO.GuaranteeDAO;
import GUI.GUI_Guarantee;
import java.awt.event.ItemEvent;

public class GUI_Form_Guarantee extends JDialog {

    private JTextField reasonField;
    private JPanel reasonPanel;
    private CustomCombobox statusBaohanh;
    private CustomButton saveButton;
    private GuaranteeDTO guarantee;
    private GUI_Guarantee parentGUI;

    public GUI_Form_Guarantee(JFrame parent, GUI_Guarantee parentGUI, GuaranteeDTO guarantee) {
        super(parent, "Cập nhật bảo hành", true);
        this.parentGUI = parentGUI;
        this.guarantee = guarantee;
        setSize(400, 310);
        setLayout(new GridBagLayout());
        setLocationRelativeTo(parent);

        getContentPane().setBackground(Color.PINK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Mã bảo hành: "), gbc);
        gbc.gridx = 1;
        JLabel BaohanhId = new JLabel("");
        BaohanhId.setText(String.valueOf(guarantee.getBaohanhID()));
        add(BaohanhId, gbc);

        gbc.fill = GridBagConstraints.NONE; // Ngăn không cho kéo dài
        gbc.anchor = GridBagConstraints.WEST; // Căn trái thay vì giãn ra

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Mã serial: "), gbc);
        gbc.gridx = 1;
        JLabel SerialID = new JLabel("");
        SerialID.setText(String.valueOf(guarantee.getSerialID()));
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        add(SerialID, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Trạng thái: "), gbc);
        gbc.gridx = 1;
        CustomCombobox statusBaohanh = new CustomCombobox(new String[]{"Chưa bảo hành", "Đang Bảo hành", "Đã bảo hành"});
        statusBaohanh.setSelectedItem(guarantee.gettrangthai());
        
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;

        add(statusBaohanh, gbc);

//        gbc.gridx = 0;
//        gbc.gridy = 3;
//        add(new JLabel("Lý do bảo hành: "), gbc);
//        gbc.gridx = 1;
//        reasonField = new JTextField(20);
//        reasonField.setText(String.valueOf(guarantee.getLydo()));
////        add(reasonField, gbc);
        reasonPanel = new JPanel(new GridBagLayout());

        reasonPanel.setBackground(Color.GREEN);
        GridBagConstraints reasonGbc = new GridBagConstraints();
        reasonGbc.insets = new Insets(5, 5, 5, 5);
        reasonGbc.anchor = GridBagConstraints.WEST;
        reasonGbc.fill = GridBagConstraints.HORIZONTAL;

        reasonGbc.gridx = 0;
        reasonGbc.gridy = 3;
        reasonPanel.add(new JLabel("Lý do bảo hành: "), reasonGbc);

        reasonGbc.gridx = 1;
        reasonField = new JTextField(20);
        reasonField.setText(guarantee.getLydo());
        reasonPanel.add(reasonField, reasonGbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(reasonPanel, gbc);

        // Ẩn panel lý do bảo hành nếu không phải "Đang Bảo hành"
        reasonPanel.setVisible("Đang Bảo hành".equals(statusBaohanh.getSelectedItem()));

        // Lắng nghe sự kiện thay đổi lựa chọn của ComboBox
        statusBaohanh.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selected = (String) statusBaohanh.getSelectedItem();
                reasonPanel.setVisible("Đang Bảo hành".equals(selected));
                reasonField.setVisible("Đang Bảo hành".equals(selected));
                revalidate();
                repaint();
            }
        });

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        saveButton = new CustomButton("Lưu");
        saveButton.addActionListener(e -> {
            // Lấy dữ liệu từ form
//            String serialID = SerialID.getText();
            String status = (String) statusBaohanh.getSelectedItem();
            String lydo = reasonField.getText();

            // Cập nhật vào ProductDTO
//            guarantee.setSerialID(serialID);
            guarantee.settrangthai(status);
            guarantee.setLydo(lydo);

            // Gọi updateProduct để cập nhật sản phẩm với mã loại tương ứng
            GuaranteeDAO.updateGuarantee(guarantee);
//
            JOptionPane.showMessageDialog(this, "Cập nhật bảo hành thành công!");
            parentGUI.loadGuaranteeData();
            dispose();
//             Đóng form sửa
        });
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(saveButton, gbc);
    }
}
