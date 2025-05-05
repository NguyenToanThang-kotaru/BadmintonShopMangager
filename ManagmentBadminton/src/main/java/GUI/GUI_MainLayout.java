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
import DTO.FunctionActionDTO;
import DTO.PermissionDTO;
import GUI.Promotion.GUI_Promotion;
import GUI.Statistics.StatisticsPanel;

public class GUI_MainLayout extends JFrame {

    private GUI_Sidebar Sidebar;
    private GUI_TittleBar tittleBar;
    public GUI_MainLayout(JFrame login, AccountDTO logined) {
        setTitle("Quản Lý Kho Hàng");
         setExtendedState(JFrame.MAXIMIZED_BOTH);
//        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0, 0));
        setUndecorated(true);
        AccountDTO account = logined; // account đã được gán từ trước

        System.out.println("Tài khoản: " + account.getUsername());
        System.out.println("Nhân viên: " + account.getFullName());
        System.out.println("Danh sách quyền:");

        PermissionDTO permission = account.getPermission();
        if (permission != null && permission.getFunction() != null) {
            for (FunctionActionDTO func : permission.getFunction()) {
                System.out.println("- Chức năng: " + func.getNameUnsigned());

                if (func.getAction() != null && !func.getAction().isEmpty()) {
                    System.out.print("  + Các hành động: ");
                    for (int i = 0; i < func.getAction().size(); i++) {
                        System.out.print(func.getAction().get(i).getName());
                        if (i < func.getAction().size() - 1) {
                            System.out.print(", ");
                        }
                    }
                    System.out.println(); // xuống dòng sau danh sách hành động
                } else {
                    System.out.println("  + Không có hành động nào.");
                }
            }
        } else {
            System.out.println("Không có quyền nào được phân.");
        }

        // ================================ Title Bar ================================
        tittleBar = new GUI_TittleBar(this);

        // ================================ GUI_Sidebar ================================
        Sidebar = new GUI_Sidebar(login, this, logined);

        // ================================ Content ================================
        JPanel contentPanel = new JPanel(new BorderLayout());

        Sidebar.statisticsPanel = new StatisticsPanel();

        Sidebar.productPanel = new JPanel();
        Sidebar.productPanel.setBackground(Color.GREEN);
        Sidebar.productPanel.add(new JLabel("Danh sách sản phẩm"));

        Sidebar.orderPanel = new GUI_SaleInvoice(logined);

        Sidebar.supplierPanel = new GUI_Supplier(logined);

        Sidebar.importPanel = new GUI_Import(logined);

        Sidebar.promotionPanel = new GUI_Promotion();

        Sidebar.customerPanel = new GUI_Customer(logined);

        Sidebar.accountPanel = new GUI_Account(logined);

        Sidebar.repairPanel = new GUI_Guarantee(logined);
        
        Sidebar.employeePanel = new GUI_Employee(logined);

        Sidebar.productPanel = new GUI_Product(logined);

        Sidebar.rolePanel = new GUI_Permission(logined);
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
                            case "Sản Phẩm":
                                contentPanel.add(Sidebar.productPanel, BorderLayout.CENTER);
                                break;
                            case "Đơn Hàng":
                                contentPanel.add(Sidebar.orderPanel, BorderLayout.CENTER);
                                break;
                            case "Nhân Viên":
                                contentPanel.add(Sidebar.employeePanel, BorderLayout.CENTER);
                                break;
                            case "Nhà Cung Cấp":
                                contentPanel.add(Sidebar.supplierPanel, BorderLayout.CENTER);
                                break;
                            case "Hóa Đơn Nhập":
                                contentPanel.add(Sidebar.importPanel, BorderLayout.CENTER);
                                break;
                            case "Khuyến Mãi":
                                contentPanel.add(Sidebar.promotionPanel, BorderLayout.CENTER);
                                break;
                            case "Khách Hàng":
                                Sidebar.customerPanel = new GUI_Customer(logined);
                                contentPanel.add(Sidebar.customerPanel, BorderLayout.CENTER);
                                break;
                            case "Tài Khoản":
                                contentPanel.add(Sidebar.accountPanel, BorderLayout.CENTER);
                                break;
                            case "Bảo Hành":
                                contentPanel.add(Sidebar.repairPanel, BorderLayout.CENTER);
                                break;
                            case "Phân Quyền":
                                contentPanel.add(Sidebar.rolePanel, BorderLayout.CENTER);
                                break;
                            default:
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
