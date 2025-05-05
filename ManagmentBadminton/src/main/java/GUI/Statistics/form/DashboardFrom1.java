package GUI.Statistics.form;

import BUS.StatisticsBUS;
import DTO.Statistics.StatisticsByMonthDTO;
import GUI.Statistics.Chart.ModelChart;
import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DashboardFrom1 extends javax.swing.JPanel {

    StatisticsBUS statisticsBUS;
    private ArrayList<StatisticsByMonthDTO> dataset;
    private DefaultTableModel model;
    static NumberFormat fm = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

    public DashboardFrom1(StatisticsBUS statisticsBUS) {
        initComponents();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        SpinnerNumberModel yearModel = new SpinnerNumberModel(currentYear, 2000, 2100, 1);
        jSpinner.setModel(yearModel);
        jSpinner.setEditor(new JSpinner.NumberEditor(jSpinner, "#"));
        int selectedYear = (int) jSpinner.getValue();
        this.statisticsBUS = statisticsBUS;
        this.dataset = this.statisticsBUS.getStatisticsByMonth(selectedYear);
        loadDataTalbe(dataset);
        loadDataChart(dataset);
        chart.addLegend("Doanh thu", new Color(12, 84, 175), new Color(0, 108, 247));
        chart.addLegend("Chi phí", new Color(54, 4, 143), new Color(104, 49, 200));
        chart.addLegend("Lợi nhuận", new Color(5, 125, 0), new Color(95, 209, 69));
    }

    private void loadDataTalbe(ArrayList<StatisticsByMonthDTO> list) {
        model = (DefaultTableModel) table.getModel();
        for (StatisticsByMonthDTO i : list) {
            model.addRow(new Object[]{
                i.getMonth(), fm.format(i.getCost()).replace("₫", "VND"),
                fm.format(i.getIncome()).replace("₫", "VND"),
                fm.format(i.getProfit()).replace("₫", "VND")
            });
        }
    }

    private void loadDataChart(ArrayList<StatisticsByMonthDTO> list) {
        for (StatisticsByMonthDTO i : list) {
            chart.addData(new ModelChart("Tháng " + i.getMonth(), new double[]{i.getIncome(), i.getCost(), i.getProfit()}));
        }
        chart.start();
    }
    
    private void excelExportForStatistics(JTable table) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
        fileChooser.setSelectedFile(new File("thongke_doanhthu.xlsx"));

        JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);
        dialog.setLocationRelativeTo(null);
        int userSelection = fileChooser.showSaveDialog(dialog);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.endsWith(".xlsx")) {
                filePath += ".xlsx";
            }

            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Thống kê");
                
                int selectedYear = (int) jSpinner.getValue();
                Row titleRow = sheet.createRow(0);
                titleRow.setHeightInPoints(30);
                Cell titleCell = titleRow.createCell(0);
                titleCell.setCellValue("Thống kê theo tháng của năm " + selectedYear);

                // Style cho tiêu đề
                CellStyle titleStyle = workbook.createCellStyle();
                org.apache.poi.ss.usermodel.Font titleFont = workbook.createFont();
                titleFont.setBold(true);
                titleFont.setFontHeightInPoints((short) 16);
                titleStyle.setFont(titleFont);
                titleStyle.setAlignment(HorizontalAlignment.CENTER);
                titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                titleCell.setCellStyle(titleStyle);

                // Gộp các ô cho tiêu đề
                sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 0, 0, table.getColumnCount() - 1));
                
                
                CreationHelper createHelper = workbook.getCreationHelper();

                // Font chữ lớn
                org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
                headerFont.setBold(true);
                headerFont.setFontHeightInPoints((short) 14);

                org.apache.poi.ss.usermodel.Font dataFont = workbook.createFont();
                dataFont.setFontHeightInPoints((short) 12);

                // Style cho header
                CellStyle headerStyle = workbook.createCellStyle();
                headerStyle.setFont(headerFont);
                headerStyle.setAlignment(HorizontalAlignment.CENTER);
                headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                headerStyle.setBorderBottom(BorderStyle.THIN);
                headerStyle.setBorderTop(BorderStyle.THIN);
                headerStyle.setBorderLeft(BorderStyle.THIN);
                headerStyle.setBorderRight(BorderStyle.THIN);

                // Style cho dữ liệu
                CellStyle dataStyle = workbook.createCellStyle();
                dataStyle.setFont(dataFont);
                dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                dataStyle.setBorderBottom(BorderStyle.THIN);
                dataStyle.setBorderTop(BorderStyle.THIN);
                dataStyle.setBorderLeft(BorderStyle.THIN);
                dataStyle.setBorderRight(BorderStyle.THIN);

                // Style cho tiền tệ
                CellStyle currencyStyle = workbook.createCellStyle();
                currencyStyle.setFont(dataFont);
                currencyStyle.setDataFormat(workbook.createDataFormat().getFormat("#,##0 \"VND\""));
                currencyStyle.setBorderBottom(BorderStyle.THIN);
                currencyStyle.setBorderTop(BorderStyle.THIN);
                currencyStyle.setBorderLeft(BorderStyle.THIN);
                currencyStyle.setBorderRight(BorderStyle.THIN);
                currencyStyle.setVerticalAlignment(VerticalAlignment.CENTER);

                // Style cho tháng dạng số
                CellStyle numberStyle = workbook.createCellStyle();
                numberStyle.setFont(dataFont);
                numberStyle.setDataFormat(workbook.createDataFormat().getFormat("0"));
                numberStyle.setBorderBottom(BorderStyle.THIN);
                numberStyle.setBorderTop(BorderStyle.THIN);
                numberStyle.setBorderLeft(BorderStyle.THIN);
                numberStyle.setBorderRight(BorderStyle.THIN);
                numberStyle.setVerticalAlignment(VerticalAlignment.CENTER);


                // Ghi header
                Row headerRow = sheet.createRow(1);
                headerRow.setHeightInPoints(25);
                for (int i = 0; i < table.getColumnCount(); i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(table.getColumnName(i));
                    cell.setCellStyle(headerStyle);
                }

                // Ghi dữ liệu
                for (int i = 0; i < table.getRowCount(); i++) {
                    Row row = sheet.createRow(i + 2);
                    row.setHeightInPoints(20);
                    for (int j = 0; j < table.getColumnCount(); j++) {
                        Cell cell = row.createCell(j);
                        Object value = table.getValueAt(i, j);
                        String columnName = table.getColumnName(j).toLowerCase();

                        if (value == null) {
                            cell.setCellValue("");
                            cell.setCellStyle(dataStyle);
                        } else if (columnName.contains("tháng")) {
                            try {
                                int month = Integer.parseInt(value.toString());
                                cell.setCellValue(month);
                                cell.setCellStyle(numberStyle);
                            } catch (NumberFormatException e) {
                                cell.setCellValue(value.toString());
                                cell.setCellStyle(dataStyle);
                            }
                        } else if (columnName.contains("doanh") || columnName.contains("chi") || columnName.contains("lợi")) {
                            try {
                                String cleaned = value.toString().replaceAll("[^\\d]", "");
                                double amount = Double.parseDouble(cleaned);
                                cell.setCellValue(amount);
                                cell.setCellStyle(currencyStyle);
                            } catch (NumberFormatException e) {
                                cell.setCellValue(value.toString());
                                cell.setCellStyle(dataStyle);
                            }
                        } else {
                            cell.setCellValue(value.toString());
                            cell.setCellStyle(dataStyle);
                        }
                    }
                }

                // Auto resize & giãn thêm cột
                for (int i = 0; i < table.getColumnCount(); i++) {
                    sheet.autoSizeColumn(i);
                    int currentWidth = sheet.getColumnWidth(i);
                    sheet.setColumnWidth(i, currentWidth + 1000); // giãn thêm cho đẹp
                }

                try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                    workbook.write(fileOut);
                }

                JOptionPane.showMessageDialog(null, "Xuất Excel thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                Desktop.getDesktop().open(new File(filePath));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Lỗi khi xuất Excel: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
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
        jButton1 = new javax.swing.JButton();

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
                .addComponent(chart, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
                .addGap(10, 10, 10))
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
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table);

        jSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinnerStateChanged(evt);
            }
        });

        jLabel2.setText("Chọn năm thống kê");

        jButton1.setText("Xuất Excel");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
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
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(jButton1)
                        .addGap(44, 44, 44))
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
                    .addComponent(jLabel2)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinnerStateChanged
        int selectedYear = (int) jSpinner.getValue();
        dataset = statisticsBUS.getStatisticsByMonth(selectedYear);

        model.setRowCount(0);
        loadDataTalbe(dataset);

        chart.clear();
        loadDataChart(dataset);
    }//GEN-LAST:event_jSpinnerStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        excelExportForStatistics(table);
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private GUI.Statistics.Chart.Chart chart;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinner;
    private GUI.Statistics.swing.RoundPanel roundPanel1;
    private GUI.Statistics.swing.TableColumn table;
    // End of variables declaration//GEN-END:variables
}
