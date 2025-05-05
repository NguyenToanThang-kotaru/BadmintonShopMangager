package GUI;

import BUS.AccountBUS;
import BUS.EmployeeBUS;
import DAO.PermissionDAO;
import DTO.EmployeeDTO;
import javax.swing.*;
import java.awt.*;
import DTO.AccountDTO;
import DAO.EmployeeDAO;
import DTO.PermissionDTO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class Form_Account extends JDialog {

    private JTextField txtEditPassword, txtUsername, txtPassword;
    private JPasswordField txtRePassword;
    private JLabel title, lblEmployeeName, txtAccount;
    private CustomCombobox<String> cbRole, cbEmployeeName;
    private CustomButton btnSave, btnCancel;

    public Form_Account(GUI_Account parent, AccountDTO account) {
        super((Frame) SwingUtilities.getWindowAncestor(parent), account == null ? "Thêm Tài Khoản" : "Sửa Tài Khoản", true);
        setSize(500, 400);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        // Đổi tiêu đề form
        title = new JLabel(account == null ? "THÊM TÀI KHOẢN" : "SỬA TÀI KHOẢN");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(new Color(52, 73, 94));
        add(title, gbc);
        gbc.gridwidth = 1;
        gbc.gridy = 1;

        // Nếu account != null => Sửa tài khoản
        lblEmployeeName = new JLabel();
        java.util.List<EmployeeDTO> employees = EmployeeDAO.getEmployeesWithoutAccount();
        String[] names = new String[employees.size()];
        int i1 = 0;
        for (EmployeeDTO emp : employees) {
            names[i1] = emp.getEmployeeID();
            System.out.println("names[i]");
            i1++;
        }
        cbEmployeeName = new CustomCombobox<>(names);
        txtAccount = new JLabel();
        txtUsername = new JTextField(20);
        txtPassword = new JTextField(20);
        txtRePassword = new JPasswordField(20);
        txtEditPassword = new JTextField(20);
//        cbRole = new CustomCombobox<>();
        java.util.List<PermissionDTO> permissions = PermissionDAO.getAllPermissions();
        String[] roles = new String[permissions.size()];
        int i = 0;
        for (PermissionDTO per : permissions) {
            roles[i] = per.getName(); // Lấy tên quyền và gán vào mảng roles
            i++;
        }
//        for (String role : roles) {
//            cbRole.addItem(role);
//        }
//        cbRole = new CustomCombobox<>(roles.toArray(new String[0]));
        cbRole = new CustomCombobox<>(roles);

        if (account != null) {
            lblEmployeeName.setText(account.getFullName());
            txtAccount.setText(account.getUsername());
            txtPassword.setText(account.getPassword());
            txtRePassword.setText(account.getPassword());
            cbRole.setSelectedItem(account.getPermission().getName());
            txtEditPassword.setText(account.getPassword());
//            String quyen = account.getTenquyen();
//            System.out.println(quyen);

//            if (quyen != null) {
//                cbRole.setSelectedItem(quyen);
//            } else {
//                cbRole.setSelectedIndex(-1); // Không chọn mục nào nếu không hợp lệ
//            }
        }
        if (account != null) {
            addComponent("Nhân Viên:", lblEmployeeName, gbc);
            addComponent("Tài Khoản: ", txtAccount, gbc);
            addComponent("Mật Khẩu:", txtEditPassword, gbc);
        } else {
            addComponent("Nhân Viên:", cbEmployeeName, gbc);
//            EmployeeDTO emp = EmployeeBUS.getEmployeeByPhone(cbEmployeeName.getSelectedItem().toString());
//            lblEmployeeName.setText(emp.getFullName());
//            addComponent("Tên nhân viên:",lblEmployeeName, gbc);
            addComponent("Tên đăng nhập: ", txtUsername, gbc);
            addComponent("Mật Khẩu: ", txtPassword, gbc);
        }

        addComponent("Quyền:", cbRole, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnSave = new CustomButton(account == null ? "Thêm" : "Cập Nhật");
        btnCancel = new CustomButton("Hủy");
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);

        btnCancel.addActionListener(e -> dispose());

        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (account == null) {
                    if (cbEmployeeName.getSelectedItem()==null) {
                        JOptionPane.showMessageDialog(null, "Vui lòng chọn nhân viên");
                    }else{                    
                    System.out.println(cbEmployeeName.getSelectedItem().toString());
                    String passwordChars = txtPassword.getText();
                    String username = txtUsername.getText();
                    String password = new String(passwordChars);
                    EmployeeDTO emp = EmployeeBUS.getEmployeeByID(cbEmployeeName.getSelectedItem().toString());
                    PermissionDTO role = PermissionDAO.getPermissionByName(cbRole.getSelectedItem().toString());
                    AccountDTO a = new AccountDTO(username, password, emp.getEmployeeID(), emp.getFullName(), role);
                        if (AccountBUS.addAccount(a)) {
                            System.out.println("Thanh cong");
                            parent.loadAccounts();
                            dispose();
                        } else {
                            System.out.println("That bai");
                        }
                    }
                } else {
                    String username = txtAccount.getText();
                    String password = txtEditPassword.getText();
                    PermissionDTO role = PermissionDAO.getPermissionByName(cbRole.getSelectedItem().toString());
                    String empID = account.getEmployeeID();
                    String empFullName = account.getFullName();
                    System.out.println(username + password + role);
                    AccountDTO a = new AccountDTO(username, password, empID, empFullName, role);
                    if (AccountBUS.updateAccount(a) == true) {
                        System.out.println("Thanh con sua tai khoan");
                        parent.loadAccounts();
                        dispose();
                    } else {
                        System.out.println("That bai");
                    }
                }
            }
        });
    }

    private void addComponent(String label, JComponent component, GridBagConstraints gbc) {

        gbc.gridx = 0;
        gbc.gridy++; // Giữ thứ tự đúng
        gbc.anchor = GridBagConstraints.WEST;

        add(new JLabel(label), gbc);

        gbc.gridx = 1;
        add(component, gbc);
    }

}
