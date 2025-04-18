package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import DTO.AccountDTO;
import GUI.Promotion.GUI_Promotion;
import GUI.Statistics.StatisticsPanel;


public class GUI_MainLayout extends JFrame {

    private GUI_Sidebar Sidebar;
    private GUI_TittleBar tittleBar;
    
    public GUI_MainLayout(JFrame login, AccountDTO logined) {
        setTitle("Quản Lý Kho Hàng");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0, 0));
        setUndecorated(true);
        
        // ================================ Title Bar ================================
        tittleBar = new GUI_TittleBar(this);

        // ================================ GUI_Sidebar ================================
        Sidebar = new GUI_Sidebar(login,this);

        // ================================ Content ================================
        JPanel contentPanel = new JPanel(new BorderLayout());

        Sidebar.statisticsPanel = new StatisticsPanel();


        Sidebar.productPanel = new JPanel();
        Sidebar.productPanel.setBackground(Color.GREEN);
        Sidebar.productPanel.add(new JLabel("Danh sách sản phẩm"));
        
        Sidebar.productPanel = new GUI_Product();

        Sidebar.orderPanel = new GUI_SaleInvoice(logined);

        Sidebar.employeePanel = new GUI_Employee();


        Sidebar.supplierPanel = new GUI_Supplier();

        Sidebar.importPanel = new GUI_Import(logined);

        Sidebar.promotionPanel = new GUI_Promotion();

        Sidebar.customerPanel = new GUI_Customer();

        Sidebar.accountPanel = new GUI_Account();

        Sidebar.repairPanel = new JPanel();
        Sidebar.repairPanel.setBackground(Color.DARK_GRAY);
        Sidebar.repairPanel.add(new JLabel("Bảo hành"));

        Sidebar.rolePanel = new GUI_Permission();
        for (Component comp : Sidebar.panel2.getComponents()) {
            if (comp instanceof JLabel menuLabel) {
                menuLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        contentPanel.removeAll(); // Xóa nội dung cũ

                        switch (menuLabel.getText()) {
                            case "Thống kê":
                                contentPanel.add(Sidebar.statisticsPanel, BorderLayout.CENTER);
                                break;
                            case "Sản Phẩm" :
                                contentPanel.add(Sidebar.productPanel, BorderLayout.CENTER);
                                break;
                            case "Đơn Hàng" :
                                contentPanel.add(Sidebar.orderPanel, BorderLayout.CENTER);
                                break;
                            case "Nhân Viên" :
                                contentPanel.add(Sidebar.employeePanel, BorderLayout.CENTER);
                                break;
                            case "Nhà Cung Cấp" :
                                contentPanel.add(Sidebar.supplierPanel, BorderLayout.CENTER);
                                break;
                            case "Hóa Đơn Nhập" :
                                contentPanel.add(Sidebar.importPanel, BorderLayout.CENTER);
                                break;
                            case "Khuyến Mãi" :
                                contentPanel.add(Sidebar.promotionPanel, BorderLayout.CENTER);
                                break;
                            case "Khách Hàng" :
                                contentPanel.add(Sidebar.customerPanel, BorderLayout.CENTER);
                                break;
                            case "Tài Khoản" :
                                contentPanel.add(Sidebar.accountPanel, BorderLayout.CENTER);
                                break;
                            case "Bảo Hành" :
                                contentPanel.add(Sidebar.repairPanel, BorderLayout.CENTER);
                                break;
                            case "Phân Quyền" :
                                contentPanel.add(Sidebar.rolePanel, BorderLayout.CENTER);
                                break;
                            default :
                                contentPanel.add(new JLabel("Chưa có nội dung"), BorderLayout.CENTER);
                        }

                        contentPanel.revalidate();
                        contentPanel.repaint();
                    }
                });
            }
        }

        add(tittleBar, BorderLayout.NORTH);
        add(Sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }
    
}
