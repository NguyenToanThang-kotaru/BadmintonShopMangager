package GUI.Statistics.form;

import BUS.StatisticsBUS;
import DTO.Statistics.StatisticsByYearDTO;
import GUI.Statistics.Chart.ModelChart;
import java.awt.Color;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.table.DefaultTableModel;

public class DashboardFrom extends javax.swing.JPanel {

    StatisticsBUS statisticsBUS;
    ArrayList<StatisticsByYearDTO> dataset;
    private DefaultTableModel model;
    static NumberFormat fm = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    private int chartPage = 1;
    private int chartsPerPage = 5;
    private int totalChartPage = 1;
    
    public DashboardFrom(StatisticsBUS statisticsBUS) {
        this.statisticsBUS = statisticsBUS;
        this.dataset = this.statisticsBUS.getStatisticsByYear();
        initComponents();
        loadDataTalbe(dataset);
//        loadDataChart(dataset);
        totalChartPage = (int) Math.ceil((double) dataset.size() / chartsPerPage);
        loadChartPage(chartPage);
        updateChartButtons();
    }
    
    private void loadDataTalbe(ArrayList<StatisticsByYearDTO> list){
        model = (DefaultTableModel) table.getModel();
        for(StatisticsByYearDTO i : list){
            model.addRow(new Object[]{
                i.getYear(), fm.format(i.getCost()).replace("₫", "VND"), 
                fm.format(i.getIncome()).replace("₫", "VND"), 
                fm.format(i.getProfit()).replace("₫", "VND")
            });
        }
    }
    
    private void loadDataChart(ArrayList<StatisticsByYearDTO> list){
        chart.addLegend("Doanh thu", new Color(12, 84, 175), new Color(0, 108, 247));
        chart.addLegend("Chi phí", new Color(54, 4, 143), new Color(104, 49, 200));
        chart.addLegend("Lợi nhuận", new Color(5, 125, 0), new Color(95, 209, 69));
        for(StatisticsByYearDTO i : list){
            chart.addData(new ModelChart("Năm " + i.getYear(), new double[]{i.getCost(), i.getIncome(), i.getProfit()}));
        }
        chart.start();
    }
    
    private void loadChartPage(int page) {
        chart.clear(); 

        chart.addLegend("Doanh thu", new Color(12, 84, 175), new Color(0, 108, 247));
        chart.addLegend("Chi phí", new Color(54, 4, 143), new Color(104, 49, 200));
        chart.addLegend("Lợi nhuận", new Color(5, 125, 0), new Color(95, 209, 69));

        int start = (page - 1) * chartsPerPage;
        int end = Math.min(start + chartsPerPage, dataset.size());

        for (int i = start; i < end; i++) {
            StatisticsByYearDTO data = dataset.get(i);
            chart.addData(new ModelChart("Năm " + data.getYear(), new double[]{
                data.getIncome(), data.getCost(), data.getProfit()
            }));
        }
        chart.start();
    }

    private void goToChartPage(int page) {
        if (page >= 1 && page <= totalChartPage) {
            chartPage = page;
            loadChartPage(chartPage);
            updateChartButtons();
        }
    }

    private void updateChartButtons() {
        btnChartPrev.setEnabled(chartPage > 1);
        btnChartNext.setEnabled(chartPage < totalChartPage);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        roundPanel1 = new GUI.Statistics.swing.RoundPanel();
        chart = new GUI.Statistics.Chart.Chart();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new GUI.Statistics.swing.TableColumn();
        btnChartPrev = new javax.swing.JButton();
        btnChartNext = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel1.setText("Báo Cáo");
        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 1, 1));

        roundPanel1.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout roundPanel1Layout = new javax.swing.GroupLayout(roundPanel1);
        roundPanel1.setLayout(roundPanel1Layout);
        roundPanel1Layout.setHorizontalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        roundPanel1Layout.setVerticalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chart, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
                .addContainerGap())
        );

        jScrollPane1.setBorder(null);

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Năm", "Chi Phí", "Doanh Thu", "Lợi Nhuận"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table);

        btnChartPrev.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnChartPrev.setText("<<");
        btnChartPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChartPrevActionPerformed(evt);
            }
        });

        btnChartNext.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnChartNext.setText(">>");
        btnChartNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChartNextActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 794, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnChartPrev)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnChartNext)
                        .addGap(71, 71, 71))
                    .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(btnChartPrev)
                    .addComponent(btnChartNext))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnChartPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChartPrevActionPerformed
        goToChartPage(chartPage - 1);
    }//GEN-LAST:event_btnChartPrevActionPerformed

    private void btnChartNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChartNextActionPerformed
         goToChartPage(chartPage + 1);
    }//GEN-LAST:event_btnChartNextActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChartNext;
    private javax.swing.JButton btnChartPrev;
    private GUI.Statistics.Chart.Chart chart;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private GUI.Statistics.swing.RoundPanel roundPanel1;
    private GUI.Statistics.swing.TableColumn table;
    // End of variables declaration//GEN-END:variables
}
