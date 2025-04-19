package GUI.Statistics;

public class StatisticsPanel extends javax.swing.JPanel {

    public StatisticsPanel() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        overviewFrom2 = new GUI.Statistics.form.overviewFrom();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        dashboardFrom1 = new GUI.Statistics.form.DashboardFrom();
        dashboardFrom11 = new GUI.Statistics.form.DashboardFrom1();

        jTabbedPane1.addTab("Tổng Quan", overviewFrom2);

        jTabbedPane2.addTab("Thống kê theo năm", dashboardFrom1);
        jTabbedPane2.addTab("Thống kê theo tháng", dashboardFrom11);

        jTabbedPane1.addTab("Tài Chính", jTabbedPane2);

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
    private GUI.Statistics.form.DashboardFrom dashboardFrom1;
    private GUI.Statistics.form.DashboardFrom1 dashboardFrom11;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private GUI.Statistics.form.overviewFrom overviewFrom2;
    // End of variables declaration//GEN-END:variables
}
