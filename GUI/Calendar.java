
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

/*
 * Calendar class that creates a calendar of specific dimensions
 */
public class Calendar{
	static JLabel lblMonth, lblYear;
	static JButton btnPrev, btnNext;
	static JTable tblCalendar;
	static JComboBox cmbYear;
	static DefaultTableModel mtblCalendar; 
	static JScrollPane stblCalendar;
	static int realYear, realMonth, realDay, currentYear, currentMonth;

	//@author A0116160W
	/**
	 * Create the actual Calendar
	 * 
	 * @param JPanel for calendar to display on
	 */
	static void createCalendar(JPanel pane){

		//Create controls
		lblMonth = new JLabel ("January");
		lblYear = new JLabel ("Change year:");
		cmbYear = new JComboBox();
		btnPrev = new JButton ("<<");
		btnNext = new JButton (">>");
		mtblCalendar = new DefaultTableModel(){
			public boolean isCellEditable(int rowIndex, int mColIndex){return false;}};
			tblCalendar = new JTable(mtblCalendar);
			stblCalendar = new JScrollPane(tblCalendar);

			//Set border
			pane.setBorder(BorderFactory.createTitledBorder("Calendar"));

			//Register action listeners
			btnPrev.addActionListener(new btnPrev_Action());
			btnNext.addActionListener(new btnNext_Action());
			cmbYear.addActionListener(new cmbYear_Action());

			lblMonth.setFont(new Font("Segoe UI Semilight", Font.PLAIN, 20));
			lblYear.setFont(new Font("Segoe UI Semilight", Font.PLAIN, 14));

			//Add controls to pane
			//pane.add(pnlCalendar);
			pane.add(lblMonth);
			pane.add(lblYear);
			pane.add(cmbYear);
			pane.add(btnPrev);
			pane.add(btnNext);
			pane.add(stblCalendar);

			//Set bounds
			//pane.setBounds(5, 5, 669, 372);
			lblMonth.setBounds(334-lblMonth.getPreferredSize().width/2, 342, 100, 25);
			lblYear.setBounds(505, 347, 80, 20);
			cmbYear.setBounds(590, 347, 80, 20);
			btnPrev.setBounds(225, 342, 50, 25);
			btnNext.setBounds(390, 342, 50, 25);
			stblCalendar.setBounds(5, 0, 739, 379);


			//Get real month/year
			GregorianCalendar cal = new GregorianCalendar(); //Create calendar
			realDay = cal.get(GregorianCalendar.DAY_OF_MONTH); //Get day
			realMonth = cal.get(GregorianCalendar.MONTH); //Get month
			realYear = cal.get(GregorianCalendar.YEAR); //Get year
			currentMonth = realMonth; //Match month and year
			currentYear = realYear;

			//Add headers
			String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
			for (int i=0; i<7; i++){
				mtblCalendar.addColumn(headers[i]);
			}

			tblCalendar.getParent().setBackground(tblCalendar.getBackground());

			//No resize/reorder
			tblCalendar.getTableHeader().setResizingAllowed(false);
			tblCalendar.getTableHeader().setReorderingAllowed(false);
			tblCalendar.getTableHeader().setFont(new Font("Segoe UI Semilight", Font.PLAIN, 20));

			//Single cell selection
			tblCalendar.setColumnSelectionAllowed(true);
			tblCalendar.setRowSelectionAllowed(true);
			tblCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			//Set row/column count
			tblCalendar.setRowHeight(45);
			mtblCalendar.setColumnCount(7);
			mtblCalendar.setRowCount(6);

			//Populate table
			for (int i=realYear-100; i<=realYear+100; i++){
				cmbYear.addItem(String.valueOf(i));
			}

			//Refresh calendar
			refreshCalendar (realMonth, realYear); //Refresh calendar
	}

	//@author A0116160W
	/**
	 * Refreshes the calendar when new month or year is provided
	 * 
	 * @param month
	 * @param year
	 */
	public static void refreshCalendar(int month, int year){
		String[] months =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		int nod, som; //Number Of Days, Start Of Month

		//Allow/disallow buttons
		btnPrev.setEnabled(true);
		btnNext.setEnabled(true);
		if (month == 0 && year <= realYear-10){btnPrev.setEnabled(false);} //Too early
		if (month == 11 && year >= realYear+100){btnNext.setEnabled(false);} //Too late
		lblMonth.setText(months[month]); //Refresh the month label (at the top)
		lblMonth.setBounds(334-lblMonth.getPreferredSize().width/2, 342, 180, 25); //Re-align label with calendar
		cmbYear.setSelectedItem(String.valueOf(year)); //Select the correct year in the combo box
		lblMonth.setFont(new Font("Segoe UI Semilight", Font.PLAIN, 20));
		//Clear table
		for (int i=0; i<6; i++){
			for (int j=0; j<7; j++){
				mtblCalendar.setValueAt(null, i, j);
			}
		}

		//Get first day of month and number of days
		GregorianCalendar cal = new GregorianCalendar(year, month, 1);
		nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		som = cal.get(GregorianCalendar.DAY_OF_WEEK);

		//Draw calendar
		for (int i=1; i<=nod; i++){
			int row = new Integer((i+som-2)/7);
			int column  =  (i+som-2)%7;
			mtblCalendar.setValueAt(i, row, column);
		}

		//Apply renderers
		tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0), new tblCalendarRenderer());
	}

	static class tblCalendarRenderer extends DefaultTableCellRenderer{
		public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column){
			super.getTableCellRendererComponent(table, value, selected, focused, row, column);
			if (column == 0 || column == 6){ //Week-end
				setBackground(new Color(255, 220, 220));
				setForeground(new Color(204, 0, 51));
				setFont(new Font("Segoe UI Semilight", Font.BOLD, 15));
			}
			else{ //Week
				setBackground(new Color(255, 255, 255));
				setForeground(Color.black);
				setFont(new Font("Segoe UI Semilight", Font.BOLD, 15));
			}
			if (value != null){
				if (Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth && currentYear == realYear){ //Today
					setBackground(Color.LIGHT_GRAY);
					setForeground(new Color(204, 0, 51));
					setFont(new Font("Segoe UI Semilight", Font.BOLD, 22));
				}
			}
			setBorder(null);


			return this;  
		}
	}

	static class btnPrev_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			if (currentMonth == 0){ //Back one year
				currentMonth = 11;
				currentYear -= 1;
			}
			else{ //Back one month
				currentMonth -= 1;
			}
			refreshCalendar(currentMonth, currentYear);
		}
	}
	static class btnNext_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			if (currentMonth == 11){ //Foward one year
				currentMonth = 0;
				currentYear += 1;
			}
			else{ //Foward one month
				currentMonth += 1;
			}
			refreshCalendar(currentMonth, currentYear);
		}
	}
	static class cmbYear_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			if (cmbYear.getSelectedItem() != null){
				String b = cmbYear.getSelectedItem().toString();
				currentYear = Integer.parseInt(b);
				refreshCalendar(currentMonth, currentYear);
			}
		}
	}
}
