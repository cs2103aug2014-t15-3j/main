import java.awt.Component;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

    public class MyCellRenderer extends JTextArea implements TableCellRenderer
    {
        public MyCellRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
         }

        public Component getTableCellRendererComponent(JTable table, Object
                value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((String) value);//or something in value, like value.getNote()...
            setSize(table.getColumnModel().getColumn(column).getWidth(),
                    getPreferredSize().height);
            if (table.getRowHeight(row) != getPreferredSize().height) {
                    table.setRowHeight(row, getPreferredSize().height);
            }
            return this;
        }
    }