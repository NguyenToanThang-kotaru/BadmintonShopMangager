package GUI.Statistics.swing;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class TableColumn extends JTable {

    public TableColumn() {
        setBackground(new Color(245, 245, 245));
        setRowHeight(40);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                return new TableCell(value);
            }

        });
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        TableCell.CellType cellType = TableCell.CellType.CENTER;
        if (column == 0) {
            cellType = TableCell.CellType.LEFT;
        } else if (column == getColumnCount() - 1) {
            cellType = TableCell.CellType.RIGHT;
        }
        return new TableCell(getValueAt(row, column), isCellSelected(row, column), cellType);
    }

}
