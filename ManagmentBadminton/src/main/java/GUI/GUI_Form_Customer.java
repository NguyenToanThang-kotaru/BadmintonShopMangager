package GUI;

// import DAO.CustomerDAO;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import BUS.CustomerBUS;
import DTO.CustomerDTO;

public class GUI_Form_Customer extends JDialog {

    private JTextField txtCustomerID, txtFullName, txtSDT, txtSpending;
    private JLabel title;
    // private CustomCombobox<String> cbRole;
    private CustomButton btnSave, btnCancel;
    private CustomerBUS customerBUS = new CustomerBUS();

    public GUI_Form_Customer(JPanel parent, CustomerDTO customer) {
        super((Frame) SwingUtilities.getWindowAncestor(parent), customer == null ? "Thêm Khách Hàng" : "Sửa Khách Hàng", true);
        setSize(400, 300);
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
        title = new JLabel(customer == null ? "THÊM KHÁCH HÀNG" : "SỬA KHÁCH HÀNG");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(new Color(52, 73, 94));
        add(title, gbc);
        gbc.gridwidth = 1;
        gbc.gridy = 1;

        // Nếu customer != null => Sửa khách hàng
        txtCustomerID = new JTextField(20);
        txtFullName = new JTextField(20);
        txtSDT = new JTextField(20);
        txtSpending = new JTextField(20);

        ArrayList<CustomerDTO> customers = customerBUS.getAll();

        if (customer != null) {
            txtCustomerID.setText(customer.getId());
            txtCustomerID.setEditable(false); // Không cho sửa
            txtCustomerID.setForeground(Color.BLACK);
            txtCustomerID.setBackground(Color.WHITE); 
            txtFullName.setText(customer.getName());
            txtSDT.setText(customer.getPhone());
            txtSpending.setText(formatCurrency(customer.getSpending()));
        } else {
            txtCustomerID.setText(getNextCustomerID(customers));
            txtCustomerID.setEditable(false); // Không cho sửa
            txtCustomerID.setForeground(Color.BLACK);
            txtCustomerID.setBackground(Color.WHITE);

        }

        addComponent("Mã Khách Hàng:", txtCustomerID, gbc);
        addComponent("Tên Khách Hàng:", txtFullName, gbc);
        addComponent("Số Điện Thoại:", txtSDT, gbc);
        addComponent("Tổng chi:", txtSpending, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnSave = new CustomButton(customer == null ? "Thêm" : "Cập Nhật");
        btnCancel = new CustomButton("Hủy");
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);

        btnCancel.addActionListener(e -> dispose());

        btnSave.addActionListener(e -> {
            String customerID = txtCustomerID.getText().trim();
            String fullName = txtFullName.getText().trim();
            String phone = txtSDT.getText().trim();
            String email = txtSpending.getText().trim();

            if (fullName.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tên và số điện thoại không được để trống.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!phone.matches("(02|03|05|07|08|09)\\d{8}")) {
                JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (fullName.length() > 50) {
                JOptionPane.showMessageDialog(this, "Tên khách hàng không được vượt quá 50 ký tự.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            for (CustomerDTO c : customers) {
                if (c.getPhone().equals(phone) && !txtCustomerID.getText().equals(c.getId())) {
                    JOptionPane.showMessageDialog(this, "Số điện thoại đã tồn tại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            CustomerDTO newCustomer = new CustomerDTO(customerID, fullName, phone, 0.0);

            if (customer == null) {
                customerBUS.add(newCustomer);
                JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!");
            } else {
                customerBUS.update(newCustomer);
                JOptionPane.showMessageDialog(this, "Cập nhật khách hàng thành công!");
            }
            dispose();
        });
    }

    private void addComponent(String label, JComponent component, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 0; 
        gbc.fill = GridBagConstraints.NONE;  

        add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;  

        add(component, gbc);
    }

    private String getNextCustomerID(ArrayList <CustomerDTO> customers) {
        // Lấy ID khách hàng tiếp theo từ CustomerBUS
        if (customers.isEmpty()) {
            return "C01"; // Nếu không có khách hàng nào, trả về ID đầu tiên
        } else {
            String lastID = customers.get(customers.size() - 1).getId();
            int nextID = Integer.parseInt(lastID.substring(2)) + 1; // Lấy số sau "KH" và tăng lên 1
            return String.format("C%02d", nextID); // Định dạng lại ID với 3 chữ số
        }
    }

    private String formatCurrency(double amount) {
        // Định dạng số tiền với dấu phẩy
        String formattedAmount = String.format("%,.0f", amount);
        return formattedAmount;
    }
}