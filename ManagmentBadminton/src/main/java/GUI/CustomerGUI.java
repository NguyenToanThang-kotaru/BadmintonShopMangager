package GUI;

/**
 *
 * @author Tung Thien
 */

 import javax.swing.*;
 import java.awt.*;
 import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class CustomerGUI extends JPanel {
    private JPanel midPanel, topPanel, botPanel;
    private JTable customerTable;
    private CustomTable tableModel;
    private CustomButton saveButton, addButton;
    private CustomSearch searchField;

    public CustomerGUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(new Color(200, 200, 200));

        // ========== PANEL TRÊN CÙNG ==========
        topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setPreferredSize(new Dimension(0, 60));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding trên-dưới 10px
        topPanel.setBackground(Color.WHITE);
        // Thanh tìm kiếm (70%)
        searchField = new CustomSearch(250, 30);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.setBackground(Color.WHITE);
//        searchField.setPreferredSize(new Dimension(0, 30));
        topPanel.add(searchField, BorderLayout.CENTER);

                midPanel = new JPanel(new BorderLayout());
        midPanel.setBackground(Color.WHITE);
        String[] columnNames = {"STT", "Khách hàng", "SĐT", "Số tiền đã chi"};
        tableModel = new CustomTable(columnNames);
        customerTable=tableModel.getAccountTable();
        TableColumnModel columnModel = customerTable.getColumnModel();
        columnModel.getColumn(1).setPreferredWidth(200); 
        columnModel.getColumn(2).setPreferredWidth(150); 
        columnModel.getColumn(3).setPreferredWidth(150); 

        // ScrollPane để bảng có thanh cuộn
        CustomScrollPane scrollPane = new CustomScrollPane(customerTable);

        midPanel.add(scrollPane, BorderLayout.CENTER);

            // ========== PANEL CHI TIẾT TÀI KHOẢN ==========  
            botPanel = new JPanel(new GridBagLayout());
            botPanel.setBackground(Color.WHITE);
            botPanel.setBorder(BorderFactory.createTitledBorder("Chi tiết tài khoản"));
    
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.anchor = GridBagConstraints.WEST;
    
    // Nhãn Mã khách hàng
            gbc.gridx = 0;
            gbc.gridy = 0;
            botPanel.add(new JLabel("Mã khách hàng: "), gbc);
    
            gbc.gridx = 1;
            JLabel customerIDLabel = new JLabel("Chọn tài khoản");
            botPanel.add(customerIDLabel, gbc);
    
    // Nhãn Tên khách hàng
            gbc.gridx = 0;
            gbc.gridy = 1;
            botPanel.add(new JLabel("Khách hàng: "), gbc);
    
            gbc.gridx = 1;
            JLabel customerLabel = new JLabel("");
            botPanel.add(customerLabel, gbc);
    
    // Nhãn Số điện thoại
            gbc.gridx = 0;
            gbc.gridy = 2;
            botPanel.add(new JLabel("SĐT: "), gbc);
    
            gbc.gridx = 1;
            JLabel phoneLabel = new JLabel("");
            botPanel.add(phoneLabel, gbc);

            // Nút lưu
            gbc.gridx = 1;
            gbc.gridy = 4;
            saveButton = new CustomButton("💾 Lưu");
            saveButton.setPreferredSize(new Dimension(80, 30));
            botPanel.add(saveButton, gbc);

        // Lấy dữ liệu từ bảng và in ra mục chi tiết
        customerTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = customerTable.getSelectedRow();
            if (selectedRow != -1) {
                // Lấy dữ liệu từ bảng
                String maKH = (String) customerTable.getValueAt(selectedRow, 0);
                String tenKH = (String) customerTable.getValueAt(selectedRow, 1);
                String phoneNumber = (String) customerTable.getValueAt(selectedRow, 2);

                // Cập nhật giao diện
                customerIDLabel.setText(maKH);
                customerLabel.setText(tenKH);
                phoneLabel.setText(phoneNumber);
            }
        });
        
        add(topPanel);
        add(Box.createVerticalStrut(10));
        add(midPanel);
        add(Box.createVerticalStrut(10));
        add(botPanel);
        addSampleData();
    }

    private void addSampleData() {
        tableModel.addRow(new Object[]{"KH001", "Nguyễn Văn A", "0123456789", 1000000});
        tableModel.addRow(new Object[]{"KH002", "Nguyễn Văn B", "0123456789", 2000000});
        tableModel.addRow(new Object[]{"KH003", "Nguyễn Văn C", "0123456789", 3000000});
        tableModel.addRow(new Object[]{"KH004", "Nguyễn Văn D", "0123456789", 4000000});
        tableModel.addRow(new Object[]{"KH005", "Nguyễn Văn E", "0123456789", 5000000});
    }
}
