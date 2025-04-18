package GUI.Statistics.form;

import GUI.Statistics.Chart.ModelChart;
import java.awt.Color;
import javax.swing.table.DefaultTableModel;

public class DashboardFrom extends javax.swing.JPanel {

    /**
     * Creates new form DashboardFrom
     */
    public DashboardFrom() {
        initComponents();
        init();
    }

    private void init() {
        chart.addLegend("Doanh thu", new Color(12, 84, 175), new Color(0, 108, 247));
        chart.addLegend("Chi phí", new Color(54, 4, 143), new Color(104, 49, 200));
        chart.addLegend("Lợi nhuận", new Color(5, 125, 0), new Color(95, 209, 69));

        chart.addData(new ModelChart("2020", new double[]{500, 200, 80}));
        chart.addData(new ModelChart("2021", new double[]{600, 750, 90}));
        chart.addData(new ModelChart("2022", new double[]{200, 350, 460}));
        chart.addData(new ModelChart("2023", new double[]{480, 150, 750}));
        chart.addData(new ModelChart("2024", new double[]{350, 540, 300}));
        chart.addData(new ModelChart("2025", new double[]{190, 280, 81}));

        chart.start();

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(new Object[]{"2020", 200, 500, 80});
        model.addRow(new Object[]{"2021", 750, 600, 90});
        model.addRow(new Object[]{"2022", 350, 200, 460});
        model.addRow(new Object[]{"2023", 150, 480, 750});
        model.addRow(new Object[]{"2024", 540, 350, 300});
        model.addRow(new Object[]{"2025", 280, 190, 81});
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        roundPanel1 = new GUI.Statistics.swing.RoundPanel();
        chart = new GUI.Statistics.Chart.Chart();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new GUI.Statistics.swing.TableColumn();

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
                "Năm", "Chi Phí", "Doanh Thu", "Lợi Nhuận"
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
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
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
    private javax.swing.JScrollPane jScrollPane1;
    private GUI.Statistics.swing.RoundPanel roundPanel1;
    private GUI.Statistics.swing.TableColumn table;
    // End of variables declaration//GEN-END:variables
}
