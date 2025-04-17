package GUI;

import DTO.EmployeeDTO;
import DAO.EmployeeDAO;
import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class Form_Employee extends JDialog {

    private JTextField txtEmployeeName, txtAddress, txtPhone, txtAge, txtEmail;
    private CustomCombobox<String> Gender;
    private JLabel title;
    private CustomButton btnSave, btnCancel;

    public Form_Employee(GUI_Employee parent, EmployeeDTO employee) {
        super((Frame) SwingUtilities.getWindowAncestor(parent), employee == null ? "Thêm Nhân Viên" : "Sửa Nhân Viên", true);
        setSize(600, 500);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        // Tiêu đề
        title = new JLabel(employee == null ? "THÊM NHÂN VIÊN" : "SỬA NHÂN VIÊN");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(new Color(52, 73, 94));
        add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        ArrayList<String> GenderList = new ArrayList<String>();
        GenderList.add("Nam");
        GenderList.add("Nữ");
        // Form nhập thông tin
        txtEmployeeName = new JTextField(20);
        txtAddress = new JTextField(20);
        txtPhone = new JTextField(20);
        txtAge = new JTextField(20);
        txtEmail = new JTextField(20);
        Gender = new CustomCombobox<>(GenderList.toArray(new String[0]));
        // Nếu đang sửa nhân viên, điền thông tin vào form
        if (employee != null) {
            txtEmployeeName.setText(employee.getFullName());
            txtAddress.setText(employee.getAddress());
            txtPhone.setText(employee.getPhone());
            
            txtAge.setText(employee.getAge());
            txtEmail.setText(employee.getEmail());
            Gender.setSelectedItem(employee.getGender());
        }

        addComponent("Tên Nhân Viên:", txtEmployeeName, gbc);
        addComponent("Tuối:", txtAge, gbc);
        addComponent("Giới tính", Gender, gbc);
        addComponent("Địa Chỉ:", txtAddress, gbc);
        addComponent("Số Điện Thoại:", txtPhone, gbc);
        addComponent("Email: ", txtEmail, gbc);

        // Nút bấm
        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnSave = new CustomButton(employee == null ? "Thêm" : "Cập Nhật");
        btnCancel = new CustomButton("Hủy");
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        gbc.gridy++;
        add(buttonPanel, gbc);

        // Sự kiện nút Hủy
        btnCancel.addActionListener(e -> dispose());

        // Sự kiện nút Lưu
        btnSave.addActionListener(e -> saveEmployee(employee, parent));
    }

 
    private void saveEmployee(EmployeeDTO employee, GUI_Employee parent) {
        String name = txtEmployeeName.getText().trim();
        String address = txtAddress.getText().trim();
        String phone = txtPhone.getText().trim();
        String age = txtAge.getText().trim();
        String email = txtEmail.getText().trim();
        String gender = Gender.getSelectedItem().toString();
        // Nếu là thêm nhân viên
        if (employee == null) {
            // Tạo Employee mới
            EmployeeDTO newEmployee = new EmployeeDTO(null, name, age, phone, email, address, gender );
            boolean success = EmployeeDAO.addEmployee(newEmployee);

            if (success) {
                JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                parent.loadEmployees();
                dispose();
            }
            else{
                JOptionPane.showMessageDialog(this, "Thêm nhân viên thất bại!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } // Nếu là cập nhật nhân viên
        else {

            // Cập nhật thông tin nhân viên
            employee.setFullName(name);
            employee.setAddress(address);
            employee.setPhone(phone);
            employee.setAge(age);
            employee.setEmail(email);
            employee.setGender(gender);
            boolean success = EmployeeDAO.updateEmployee(employee);

            if (success) {
                JOptionPane.showMessageDialog(this, "Cập nhật nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                parent.loadEmployees();
                dispose();
            }
        }
    }
    // Thêm component vào form  
    private void addComponent(String label, JComponent component, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel(label), gbc);

        gbc.gridx = 1;
        add(component, gbc);
    }
}
