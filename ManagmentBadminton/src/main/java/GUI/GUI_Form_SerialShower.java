package GUI;

import DTO.ProductDTO;
import DAO.ProductDAO;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class GUI_Form_SerialShower extends JDialog {

    private CustomButton closeButton;
    private ProductDTO product;

    public GUI_Form_SerialShower(JFrame parent, ProductDTO product) {
        super(parent, "Danh sách Serial", true);
        this.product = product;

        setSize(500, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        // Panel tiêu đề
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Danh sách Serial", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Panel danh sách serial
        JPanel serialListPanel = new JPanel();
        serialListPanel.setLayout(new BoxLayout(serialListPanel, BoxLayout.Y_AXIS));
        serialListPanel.setBackground(Color.WHITE);

        // Lấy danh sách serial từ DAO bằng productID
        ArrayList<String> serials = ProductDAO.getSerialsForProduct(product.getProductID());
        for (String serial : serials) {
            serialListPanel.add(createSerialLabel(serial)); 
        }

        JScrollPane scrollPane = new JScrollPane(serialListPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Nút đóng
        closeButton = new CustomButton("Đóng");
        closeButton.setCustomColor(Color.PINK);
        closeButton.addActionListener(e -> dispose());
        JPanel botPanel = new JPanel();
        botPanel.add(closeButton);
        add(botPanel, BorderLayout.SOUTH);
    }

    // Tạo JLabel hiển thị serial
    private JLabel createSerialLabel(String serialID) {
        JLabel serialLabel = new JLabel("Serial: " + serialID);
        serialLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        return serialLabel;
    }
}
