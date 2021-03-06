import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/*
 *  Class to color a table cell according to its value in color
 */
public class CellColorRenderer extends JLabel implements TableCellRenderer { 

	Color curColor;
	
	public Component getTableCellRendererComponent(JTable table, Object value, 
			boolean   isSelected, boolean hasFocus, int row, int column) { 
		if (curColor instanceof Color) {
			curColor = (Color) value;
		} else {
			curColor = table.getBackground();
		}
		return this;
	} 
	
	//@author A0116160W
	/**
	 * Color the cell
	 */
	public void paint(Graphics g) {
		g.setColor(curColor);
		g.fillRect(0, 0, 6, getHeight());
	}
} 