package GUI.Statistics.form;

import GUI.Statistics.Chart.ModelChart;
import java.awt.Color;
import java.util.Calendar;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

public class DashboardFrom1 extends javax.swing.JPanel {

    /**
     * Creates new form DashboardFrom
     */
    public DashboardFrom1() {
        initComponents();
        init();
    }

    private void init() {
        chart.addLegend("Doanh thu", new Color(12, 84, 175), new Color(0, 108, 247));
        chart.addLegend("Chi phí", new Color(54, 4, 143), new Color(104, 49, 200));
        chart.addLegend("Lợi nhuận", new Color(5, 125, 0), new Color(95, 209, 69));

        chart.addData(new ModelChart("Tháng 1", new double[]{120, 80, 40}));
        chart.addData(new ModelChart("Tháng 2", new double[]{100, 90, 10}));
        chart.addData(new ModelChart("Tháng 3", new double[]{130, 100, 30}));
        chart.addData(new ModelChart("Tháng 4", new double[]{110, 70, 40}));
        chart.addData(new ModelChart("Tháng 5", new double[]{150, 120, 60}));
        chart.addData(new ModelChart("Tháng 6", new double[]{170, 140, 80}));
        chart.addData(new ModelChart("Tháng 7", new double[]{180, 160, 100}));
        chart.addData(new ModelChart("Tháng 8", new double[]{160, 130, 90}));
        chart.addData(new ModelChart("Tháng 9", new double[]{140, 100, 70}));
        chart.addData(new ModelChart("Tháng 10", new double[]{200, 180, 120}));
        chart.addData(new ModelChart("Tháng 11", new double[]{190, 150, 110}));
        chart.addData(new ModelChart("Tháng 12", new double[]{210, 170, 130}));

        chart.start();

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(new Object[]{"Tháng 1", 80, 120, 40});
        model.addRow(new Object[]{"Tháng 2", 90, 100, 10});
        model.addRow(new Object[]{"Tháng 3", 100, 130, 30});
        model.addRow(new Object[]{"Tháng 4", 70, 110, 40});
        model.addRow(new Object[]{"Tháng 5", 120, 150, 60});
        model.addRow(new Object[]{"Tháng 6", 140, 170, 80});
        model.addRow(new Object[]{"Tháng 7", 160, 180, 100});
        model.addRow(new Object[]{"Tháng 8", 130, 160, 90});
        model.addRow(new Object[]{"Tháng 9", 100, 140, 70});
        model.addRow(new Object[]{"Tháng 10", 180, 200, 120});
        model.addRow(new Object[]{"Tháng 11", 150, 190, 110});
        model.addRow(new Object[]{"Tháng 12", 170, 210, 130});

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        SpinnerNumberModel yearModel = new SpinnerNumberModel(currentYear, 2000, 2100, 1);
        jSpinner.setModel(yearModel);
        jSpinner.setEditor(new JSpinner.NumberEditor(jSpinner, "#"));

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        roundPanel1 = new GUI.Statistics.swing.RoundPanel();
        chart = new GUI.Statistics.Chart.Chart();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new GUI.Statistics.swing.TableColumn();
        jSpinner = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();

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
                .addComponent(chart, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                .addContainerGap())
        );

        jScrollPane1.setBorder(null);

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tháng", "Chi Phí", "Doanh Thu", "Lợi Nhuận"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table);

        jLabel2.setText("Chọn năm thống kê");

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
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(134, 134, 134))
                    .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private GUI.Statistics.Chart.Chart chart;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinner;
    private GUI.Statistics.swing.RoundPanel roundPanel1;
    private GUI.Statistics.swing.TableColumn table;
    // End of variables declaration//GEN-END:variables
}
