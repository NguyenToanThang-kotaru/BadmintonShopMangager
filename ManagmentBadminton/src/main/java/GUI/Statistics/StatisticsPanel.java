package GUI.Statistics;

import BUS.StatisticsBUS;
import GUI.Statistics.form.DashboardFrom;
import GUI.Statistics.form.DashboardFrom1;
import GUI.Statistics.form.InventoryProductStatisticsFrom;

public class StatisticsPanel extends javax.swing.JPanel {
    
    private StatisticsBUS statisticsBUS;
    
    public StatisticsPanel() {
        initComponents();
        addFinancialTabs();
    }

    private void addFinancialTabs() {
        statisticsBUS = new StatisticsBUS();
        DashboardFrom dashboardFrom = new DashboardFrom(statisticsBUS);
        jTabbedPane2.addTab("Thống kê theo năm", dashboardFrom);
        DashboardFrom1 dashboardFrom1 = new DashboardFrom1(statisticsBUS);
        jTabbedPane2.addTab("Thống kê theo tháng", dashboardFrom1);
        InventoryProductStatisticsFrom inventoryProductStatisticsFrom = new InventoryProductStatisticsFrom();
        jTabbedPane3.add(inventoryProductStatisticsFrom);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        overviewFrom2 = new GUI.Statistics.form.overviewFrom();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jTabbedPane3 = new javax.swing.JTabbedPane();

        jTabbedPane1.addTab("Tổng Quan", overviewFrom2);
        jTabbedPane1.addTab("Tài Chính", jTabbedPane2);
        jTabbedPane1.addTab("Tồn Kho", jTabbedPane3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 971, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private GUI.Statistics.form.overviewFrom overviewFrom2;
    // End of variables declaration//GEN-END:variables
}
