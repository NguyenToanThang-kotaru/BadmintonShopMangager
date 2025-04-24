package GUI;

import DAO.PermissionDAO;
import DTO.PermissionDTO;
import BUS.PermissionBUS;
import DTO.ActionDTO;
import DTO.FunctionActionDTO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import org.apache.commons.lang3.StringUtils;

public class Form_Permission extends JDialog {

    private JTextField txtPermissionName;
    private JLabel title;
    private JPanel checkBoxPanel;
    private List<JCheckBox> allCheckBoxes = new ArrayList<>();
    private CustomButton btnSave, btnCancel;
    private Boolean isEditMode;

    public Form_Permission(GUI_Permission parent, PermissionDTO permission) {

        super((Frame) SwingUtilities.getWindowAncestor(parent),
                permission == null ? "Thêm Quyền" : "Sửa Quyền", true);
        if (permission == null) {
            isEditMode = false;
        } else {
            isEditMode = true;
        }
        setSize(400, 500); // Tăng chiều cao để hiển thị rõ hơn
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        // Tiêu đề form
        title = new JLabel(permission == null ? "THÊM QUYỀN MỚI" : "CHỈNH SỬA QUYỀN");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(new Color(52, 73, 94));
        add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;

        // Tên quyền
        txtPermissionName = new JTextField(20);
        if (permission != null) {
            txtPermissionName.setText(permission.getName());
        }
        addComponent("Tên Quyền:", txtPermissionName, gbc);

        // Danh sách chức năng với checkbox
        checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));
        ArrayList<FunctionActionDTO> functionList = PermissionDAO.getAllFunctionAction();
        ArrayList<ActionDTO> allActions = PermissionBUS.getAllAction(); // hoặc danh sách mặc định gồm Xem, Thêm, Sửa, Xóa

        JPanel permissionTablePanel = createPermissionTable(permission, functionList, allActions, isEditMode);
        checkBoxPanel.add(permissionTablePanel);

        // Thêm checkbox cho tất cả chức năng
//        PermissionDTO allFunctions = new PermissionDTO(PermissionDAO.getPermission("1"));
//        for (String chucNang : PermissionDAO.getAllFunctionByName()) {
//            JCheckBox checkBox = new JCheckBox(PermissionBUS.decodeFunctionName(chucNang));
//            checkBox.setName(chucNang); // Lưu mã gốc vào thuộc tính name
//            allCheckBoxes.add(checkBox);
//            checkBoxPanel.add(checkBox);
//        }
        // Thêm vào ScrollPane
        CustomScrollPane scrollPane = new CustomScrollPane(checkBoxPanel);
        scrollPane.setPreferredSize(new Dimension(350, 250));
        gbc.gridx = 0;
//        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy++;
        add(scrollPane, gbc);

        // Nút lưu và hủy
        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnSave = new CustomButton(permission == null ? "Thêm" : "Cập Nhật");
        btnCancel = new CustomButton("Hủy");
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);

        // Nếu là chế độ sửa, load dữ liệu hiện có
        if (permission != null) {
            txtPermissionName.setText(permission.getName());

//            // Chọn các checkbox tương ứng với quyền hiện có
//            for (JCheckBox checkBox : allCheckBoxes) {
//                if (permission.getChucNang().contains(checkBox.getName())) {
//                    checkBox.setSelected(true);
//                }
//            }
        }
        btnCancel.addActionListener(e -> dispose());
        btnSave.addActionListener(e -> {
            PermissionDTO newPermission = new PermissionDTO();
            newPermission.setID(PermissionBUS.generateNewPermissionID());
            newPermission.setName(txtPermissionName.getText());
            newPermission.setnameUnsinged(removeAccents(txtPermissionName.getText()));
            newPermission.setTotalAccount("0");
            newPermission.setFunction(getSelectedFunctionActions());
            newPermission.setTotalFunction(Integer.toString(newPermission.getFunction().size()));
//            System.out.println("\n[Permission Object Details]");
//            System.out.println("Name: " + newPermission.getName());
//            System.out.println("Unsigned Name: " + newPermission.getnameUnsinged());
//            System.out.println("Total Functions: " + newPermission.getTotalFunction());
//            System.out.println("Total Accounts: " + newPermission.getTotalAccount());
//            for (FunctionActionDTO func : newPermission.getFunction()) {
//                // Debug permission object before saving
//                System.out.println(" Function: " + func.getID());
//                if (func.getAction() != null) {
//                    for (ActionDTO action : func.getAction()) {
//                        System.out.println("  ActionID: " + action.getID());
//                    }
//                }
//            }

            if (!isEditMode) {
                if (PermissionBUS.add_Permisison(newPermission) && PermissionBUS.add_FunctionAction(newPermission)) {
                    System.out.println("them quyen thanh con");

                }
            } else {
                permission.setName(txtPermissionName.getText());
                permission.setnameUnsinged(removeAccents(txtPermissionName.getText()));
                permission.setTotalAccount("0");
                permission.setFunction(getSelectedFunctionActions());
                permission.setTotalFunction(Integer.toString(permission.getFunction().size()));
                PermissionBUS.update_Permission(permission);
            }
            dispose(); // đóng form
        });

    }

    public List<String> getSelectedFunctions() {
        List<String> selected = new ArrayList<>();
        for (JCheckBox checkBox : allCheckBoxes) {
            if (checkBox.isSelected()) {
                selected.add(checkBox.getName()); // Lấy mã chức năng từ thuộc tính name
            }
        }
        return selected;
    }

    private void addComponent(String label, JComponent component, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(component, gbc);
        gbc.fill = GridBagConstraints.NONE;
    }

    private JPanel createPermissionTable(PermissionDTO permission, ArrayList<FunctionActionDTO> functionList, ArrayList<ActionDTO> allActions, boolean isEditMode) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Header row
        gbc.gridy = 0;
        gbc.gridx = 0;
        panel.add(new JLabel("Tên chức năng"), gbc);

        for (int i = 0; i < allActions.size(); i++) {
            gbc.gridx = i + 1;
            JLabel headerLabel = new JLabel(allActions.get(i).getVnName()); // hoặc getName() nếu không có VN
            headerLabel.setFont(new Font("Arial", Font.BOLD, 12));
            panel.add(headerLabel, gbc);
        }

        // Rows for each function
        for (int row = 0; row < functionList.size(); row++) {
            FunctionActionDTO func = functionList.get(row);
            gbc.gridy = row + 1;

            // Tên chức năng
            gbc.gridx = 0;
            panel.add(new JLabel(func.getName()), gbc);

            for (int col = 0; col < allActions.size(); col++) {
                ActionDTO action = allActions.get(col);
                gbc.gridx = col + 1;
                if (func.getNameUnsigned().equals("Quan ly thong ke") && (col == 1 || col == 2 || col == 3)) {
                    continue;
                }
                JCheckBox cb = new JCheckBox();
                cb.setName(func.getID() + "_" + action.getID()); // Ví dụ: "F001_Them"

                // Nếu đang ở chế độ sửa, và function này có action tương ứng thì tick
                boolean checked = false;
                if (permission != null && isEditMode) {
                    for (FunctionActionDTO permFunc : permission.getFunction()) {
                        if (permFunc.getID().equals(func.getID())) {
                            if (permFunc.getAction() != null) {
                                for (ActionDTO permAction : permFunc.getAction()) {
                                    if (permAction.getID().equals(action.getID())) {
                                        checked = true;
                                        break;
                                    }
                                }
                            }
                        }
                        if (checked) {
                            break;
                        }
                    }
                }

                cb.setSelected(checked);

                allCheckBoxes.add(cb);
                panel.add(cb, gbc);
            }
        }

        return panel;
    }

    public ArrayList<FunctionActionDTO> getSelectedFunctionActions() {
        ArrayList<FunctionActionDTO> result = new ArrayList<>();

        for (JCheckBox cb : allCheckBoxes) {
            if (cb.isSelected()) {
                String[] parts = cb.getName().split("_"); // "F001_Them"
                if (parts.length != 2) {
                    continue;
                }

                String funcID = parts[0];
                String actionName = parts[1];

                // Tìm FunctionActionDTO đã có sẵn trong result chưa
                FunctionActionDTO foundFunc = null;
                for (FunctionActionDTO f : result) {
                    if (f.getID().equals(funcID)) {
                        foundFunc = f;
                        break;
                    }
                }

                // Nếu chưa có thì tạo mới
                if (foundFunc == null) {
                    foundFunc = new FunctionActionDTO();
                    foundFunc.setID(funcID);
                    foundFunc.setAction(new ArrayList<>());
                    result.add(foundFunc);
                }

                // Tạo đối tượng ActionDTO và thêm vào
                ActionDTO action = new ActionDTO();
                action.setID(actionName); // hoặc setID nếu bạn cần ID thay vì name

                foundFunc.getAction().add(action);
            }
        }

        return result;
    }

    public static String removeAccents(String input) {
        return StringUtils.stripAccents(input); // Xử lý cả tiếng Việt
    }

}
