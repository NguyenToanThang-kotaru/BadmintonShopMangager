//package GUI;
//
//import DAO.PermissionDAO;
//import DTO.PermissionDTO;
//import BUS.PermissionBUS;
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.ArrayList;
//import java.util.List;
//import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
//
//public class Form_Permission extends JDialog {
//
//    private JTextField txtPermissionName;
//    private JLabel title;
//    private JPanel checkBoxPanel;
//    private List<JCheckBox> allCheckBoxes = new ArrayList<>();
//    private CustomButton btnSave, btnCancel;
//
//    public Form_Permission(JPanel parent, PermissionDTO permission) {
//        super((Frame) SwingUtilities.getWindowAncestor(parent),
//                permission == null ? "Thêm Quyền" : "Sửa Quyền", true);
//
//        setSize(400, 500); // Tăng chiều cao để hiển thị rõ hơn
//        setLocationRelativeTo(parent);
//        setLayout(new GridBagLayout());
//        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(10, 10, 10, 10);
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        gbc.gridwidth = 2;
//        gbc.anchor = GridBagConstraints.CENTER;
//
//        // Tiêu đề form
//        title = new JLabel(permission == null ? "THÊM QUYỀN MỚI" : "CHỈNH SỬA QUYỀN");
//        title.setFont(new Font("Arial", Font.BOLD, 18));
//        title.setForeground(new Color(52, 73, 94));
//        add(title, gbc);
//
//        gbc.gridwidth = 1;
//        gbc.gridy = 1;
//
//        // Tên quyền
//        txtPermissionName = new JTextField(20);
//        addComponent("Tên Quyền:", txtPermissionName, gbc);
//
//        // Danh sách chức năng với checkbox
//        checkBoxPanel = new JPanel();
//        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));
//
//        // Thêm checkbox cho tất cả chức năng
////        PermissionDTO allFunctions = new PermissionDTO(PermissionDAO.getPermission("1"));
//        for (String chucNang : PermissionDAO.getAllFunctionByName()) {
//            JCheckBox checkBox = new JCheckBox(PermissionBUS.decodeFunctionName(chucNang));
//            checkBox.setName(chucNang); // Lưu mã gốc vào thuộc tính name
//            allCheckBoxes.add(checkBox);
//            checkBoxPanel.add(checkBox);
//        }
//
//        // Thêm vào ScrollPane
//        CustomScrollPane scrollPane = new CustomScrollPane(checkBoxPanel);
//        scrollPane.setPreferredSize(new Dimension(350, 250));
//        gbc.gridx = 0;
//        gbc.gridy++;
//        gbc.gridwidth = 2;
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        add(new JLabel("Danh Sách Chức Năng:"), gbc);
//
//        gbc.gridy++;
//        add(scrollPane, gbc);
//
//        // Nút lưu và hủy
//        JPanel buttonPanel = new JPanel(new FlowLayout());
//        btnSave = new CustomButton(permission == null ? "Thêm" : "Cập Nhật");
//        btnCancel = new CustomButton("Hủy");
//        buttonPanel.add(btnSave);
//        buttonPanel.add(btnCancel);
//
//        gbc.gridy++;
//        gbc.anchor = GridBagConstraints.CENTER;
//        add(buttonPanel, gbc);
//
//        // Nếu là chế độ sửa, load dữ liệu hiện có
//        if (permission != null) {
//            txtPermissionName.setText(permission.getName());
//
//            // Chọn các checkbox tương ứng với quyền hiện có
//            for (JCheckBox checkBox : allCheckBoxes) {
//                if (permission.getChucNang().contains(checkBox.getName())) {
//                    checkBox.setSelected(true);
//                }
//            }
//        }
//        btnCancel.addActionListener(e -> dispose());
//        btnSave.addActionListener(e -> {
//            PermissionDTO NewPermission = new PermissionDTO("1", txtPermissionName.getText(), getSelectedFunctions(), "0");
//            if (permission == null) {
//                if (PermissionDAO.addPermission(NewPermission) == true) {
//                    System.out.println(txtPermissionName.getText());
//                    System.out.println(NewPermission.getName());
//                    System.out.println("Them thanh cong");
//                    dispose();
//                } else {
//                    System.out.println("Them that bai...");
//                }
//            } else if (PermissionDAO.editPermission(permission,txtPermissionName.getText(),getSelectedFunctions()) == true) {
//                System.out.println(txtPermissionName.getText());
//
//                System.out.println("Sua thanh cong");
//                dispose();
//            } else {
//                System.out.println("Sua that bai...");
//            }
//        });
//    }
//
//    public List<String> getSelectedFunctions() {
//        List<String> selected = new ArrayList<>();
//        for (JCheckBox checkBox : allCheckBoxes) {
//            if (checkBox.isSelected()) {
//                selected.add(checkBox.getName()); // Lấy mã chức năng từ thuộc tính name
//            }
//        }
//        return selected;
//    }
//
//    private void addComponent(String label, JComponent component, GridBagConstraints gbc) {
//        gbc.gridx = 0;
//        gbc.gridy++;
//        gbc.gridwidth = 1;
//        gbc.anchor = GridBagConstraints.WEST;
//        add(new JLabel(label), gbc);
//
//        gbc.gridx = 1;
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        add(component, gbc);
//        gbc.fill = GridBagConstraints.NONE;
//    }
//}
