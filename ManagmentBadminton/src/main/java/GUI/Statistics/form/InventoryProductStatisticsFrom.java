package GUI.Statistics.form;

import BUS.StatisticsBUS;
import DTO.Statistics.ProductStatisticsDTO;
import GUI.Statistics.Chart.ModelPolarAreaChart;
import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
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
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class InventoryProductStatisticsFrom extends javax.swing.JPanel {

    StatisticsBUS statisticsBUS;
    HashMap<String, List<ProductStatisticsDTO>> list;
    private DefaultTableModel model;

    public InventoryProductStatisticsFrom() {
        this.statisticsBUS = new StatisticsBUS();
        initComponents();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        SpinnerNumberModel yearModel = new SpinnerNumberModel(currentYear, 2000, 2100, 1);
        jSpinner.setModel(yearModel);
        jSpinner.setEditor(new JSpinner.NumberEditor(jSpinner, "#"));
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        cboChosse.setSelectedItem("Tháng " + currentMonth);
        this.list = this.statisticsBUS.filterTonKho("", currentMonth, currentYear);
        loadDataTable(list);
        loadDataChart(list);
    }

    private void loadDataTable(HashMap<String, List<ProductStatisticsDTO>> list) {
        model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        int index = 1;
        for (String productID : list.keySet()) {
            int[] amount = statisticsBUS.getAmount(list.get(productID));
            model.addRow(new Object[]{
                index,
                productID,
                list.get(productID).get(0).getName_product(),
                amount[0], amount[1], amount[2], amount[3]
            });
            index++;
        }
    }

    private void search() {
        String input = txtSearch.getText() != null ? txtSearch.getText() : "";
        int month = Integer.parseInt(cboChosse.getSelectedItem().toString().replaceAll("\\D+", ""));
        int year = ((int) jSpinner.getValue());

        Calendar now = Calendar.getInstance();
        int currentMonth = now.get(Calendar.MONTH) + 1;
        int currentYear = now.get(Calendar.YEAR);

        if (year > currentYear || (year == currentYear && month > currentMonth)) {
            model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            polarAreaChart.clear();
            return;
        }

        this.list = statisticsBUS.filterTonKho(input, month, year);

        loadDataTable(this.list);

        boolean allZero = list.values().stream()
                .flatMap(List::stream)
                .allMatch(dto -> dto.getInventoryEndMonth() == 0
                && dto.getImportsInMonth() == 0
                && dto.getExportInMonth() == 0);

        if (allZero) {
            polarAreaChart.clear();
        } else {
            loadDataChart(this.list);
        }
    }

    private void updateChartFromTable(int rowIndex) {
        int importAmount = (int) model.getValueAt(rowIndex, 4); // Nhập
        int exportAmount = (int) model.getValueAt(rowIndex, 5); // Xuất
        int inventoryAmount = (int) model.getValueAt(rowIndex, 6); // Tồn

        int total = importAmount + exportAmount + inventoryAmount;
        if (importAmount == 0 && exportAmount == 0 && inventoryAmount == 0) {
            polarAreaChart.clear();
            return;
        }

        if (total == 0) {
            return;
        }

        float importPercent = (float) importAmount / total * 100;
        float exportPercent = (float) exportAmount / total * 100;
        float inventoryPercent = (float) inventoryAmount / total * 100;

        polarAreaChart.clear();
        polarAreaChart.addItem(new ModelPolarAreaChart(new Color(52, 148, 203), "Nhập", importPercent));
        polarAreaChart.addItem(new ModelPolarAreaChart(new Color(175, 67, 237), "Xuất", exportPercent));
        polarAreaChart.addItem(new ModelPolarAreaChart(new Color(87, 218, 137), "Tồn", inventoryPercent));
        polarAreaChart.start();
    }

    private void loadDataChart(HashMap<String, List<ProductStatisticsDTO>> list) {
        int totalImport = 0;
        int totalExport = 0;
        int totalInventory = 0;

        for (List<ProductStatisticsDTO> productList : list.values()) {
            for (ProductStatisticsDTO product : productList) {
                totalImport += product.getImportsInMonth();
                totalExport += product.getExportInMonth();
                totalInventory += product.getInventoryEndMonth();
            }
        }

        int total = totalImport + totalExport + totalInventory;
        if (total == 0) {
            return;
        }
        float importPercent = (float) totalImport / total * 100;
        float exportPercent = (float) totalExport / total * 100;
        float inventoryPercent = (float) totalInventory / total * 100;

        polarAreaChart.clear();
        polarAreaChart.addItem(new ModelPolarAreaChart(new Color(52, 148, 203), "Nhập", importPercent));
        polarAreaChart.addItem(new ModelPolarAreaChart(new Color(175, 67, 237), "Xuất", exportPercent));
        polarAreaChart.addItem(new ModelPolarAreaChart(new Color(87, 218, 137), "Tồn", inventoryPercent));
        polarAreaChart.start();
    }

    private void exportInventoryToExcel(JTable table) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
        fileChooser.setSelectedFile(new File("thongketonkho.xlsx"));

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
                Sheet sheet = workbook.createSheet("Tồn kho");

                int month = Integer.parseInt(cboChosse.getSelectedItem().toString().replaceAll("\\D+", ""));
                int year = (int) jSpinner.getValue();

                // Thêm tiêu đề thống kê tồn kho của tháng X/XXXX
                Row titleRow = sheet.createRow(0);
                Cell titleCell = titleRow.createCell(0);
                titleCell.setCellValue("Thống kê tồn kho của tháng " + month + "/" + year);

                CellStyle titleStyle = workbook.createCellStyle();
                org.apache.poi.ss.usermodel.Font titleFont = workbook.createFont();
                titleFont.setBold(true);
                titleFont.setFontHeightInPoints((short) 14);
                titleStyle.setFont(titleFont);
                titleStyle.setAlignment(HorizontalAlignment.CENTER);
                titleCell.setCellStyle(titleStyle);

                // Gộp các ô cho tiêu đề (giả sử có 7 cột dữ liệu)
                sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 0, 0, 6));

                org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
                headerFont.setBold(true);
                headerFont.setFontHeightInPoints((short) 12);

                CellStyle headerStyle = workbook.createCellStyle();
                headerStyle.setFont(headerFont);
                headerStyle.setAlignment(HorizontalAlignment.CENTER);
                headerStyle.setBorderBottom(BorderStyle.THIN);
                headerStyle.setBorderTop(BorderStyle.THIN);
                headerStyle.setBorderLeft(BorderStyle.THIN);
                headerStyle.setBorderRight(BorderStyle.THIN);

                CellStyle dataStyle = workbook.createCellStyle();
                dataStyle.setBorderBottom(BorderStyle.THIN);
                dataStyle.setBorderTop(BorderStyle.THIN);
                dataStyle.setBorderLeft(BorderStyle.THIN);
                dataStyle.setBorderRight(BorderStyle.THIN);

                CellStyle currencyStyle = workbook.createCellStyle();
                DataFormat format = workbook.createDataFormat();
                currencyStyle.setDataFormat(format.getFormat("#,##0 \"VNĐ\""));
                currencyStyle.setBorderBottom(BorderStyle.THIN);
                currencyStyle.setBorderTop(BorderStyle.THIN);
                currencyStyle.setBorderLeft(BorderStyle.THIN);
                currencyStyle.setBorderRight(BorderStyle.THIN);

                Row headerRow = sheet.createRow(1);
                for (int i = 0; i < table.getColumnCount(); i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(table.getColumnName(i));
                    cell.setCellStyle(headerStyle);
                }

                for (int i = 0; i < table.getRowCount(); i++) {
                    Row row = sheet.createRow(i + 2);
                    for (int j = 0; j < table.getColumnCount(); j++) {
                        Cell cell = row.createCell(j);
                        Object value = table.getValueAt(i, j);
                        String columnName = table.getColumnName(j).toLowerCase();

                        if (value == null) {
                            cell.setCellValue("");
                            cell.setCellStyle(dataStyle);
                        } else if (columnName.contains("giá")) {
                            try {
                                double money = Double.parseDouble(value.toString().replaceAll("[^\\d.]", ""));
                                cell.setCellValue(money);
                                cell.setCellStyle(currencyStyle);
                            } catch (NumberFormatException e) {
                                cell.setCellValue(value.toString());
                                cell.setCellStyle(dataStyle);
                            }
                        } else if (value instanceof Number) {
                            cell.setCellValue(((Number) value).doubleValue());
                            cell.setCellStyle(dataStyle);
                        } else {
                            cell.setCellValue(value.toString());
                            cell.setCellStyle(dataStyle);
                        }
                    }
                }

                for (int i = 0; i < table.getColumnCount(); i++) {
                    sheet.autoSizeColumn(i);
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

        roundPanel2 = new GUI.Statistics.swing.RoundPanel();
        jLabel1 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cboChosse = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jSpinner = new javax.swing.JSpinner();
        roundPanel1 = new GUI.Statistics.swing.RoundPanel();
        polarAreaChart = new GUI.Statistics.Chart.PolarAreaChart();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new GUI.Statistics.swing.TableColumn();

        roundPanel2.setBackground(new java.awt.Color(255, 255, 255));
        roundPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        roundPanel2.setLayout(new java.awt.GridLayout(0, 1));

        jLabel1.setText("Tìm kiếm sản phẩm");
        roundPanel2.add(jLabel1);

        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });
        roundPanel2.add(txtSearch);

        jLabel2.setText("Chọn tháng");
        roundPanel2.add(jLabel2);

        cboChosse.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12" }));
        cboChosse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboChosseActionPerformed(evt);
            }
        });
        roundPanel2.add(cboChosse);

        jLabel3.setText("Chọn năm");
        roundPanel2.add(jLabel3);

        jSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinnerStateChanged(evt);
            }
        });
        roundPanel2.add(jSpinner);

        roundPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setText("Xuất excel");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundPanel1Layout = new javax.swing.GroupLayout(roundPanel1);
        roundPanel1.setLayout(roundPanel1Layout);
        roundPanel1Layout.setHorizontalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(polarAreaChart, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                .addContainerGap())
        );
        roundPanel1Layout.setVerticalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(239, Short.MAX_VALUE))
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(polarAreaChart, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã Sản Phẩm", "Tên Sản Phẩm", "Tồn đầu tháng", "Nhập trong tháng", "Xuất trong tháng", "Tồn kho"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)
                .addGap(10, 10, 10)
                .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(roundPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        search();
    }//GEN-LAST:event_txtSearchActionPerformed

    private void cboChosseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboChosseActionPerformed
        search();
    }//GEN-LAST:event_cboChosseActionPerformed

    private void jSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinnerStateChanged
        search();
    }//GEN-LAST:event_jSpinnerStateChanged

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            updateChartFromTable(selectedRow);
        }
    }//GEN-LAST:event_tableMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        exportInventoryToExcel(table);
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cboChosse;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinner;
    private GUI.Statistics.Chart.PolarAreaChart polarAreaChart;
    private GUI.Statistics.swing.RoundPanel roundPanel1;
    private GUI.Statistics.swing.RoundPanel roundPanel2;
    private GUI.Statistics.swing.TableColumn table;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
