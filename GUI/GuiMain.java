/**
 * @author Sankalp 
 * 
 * 
 */
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultCaret;

import com.sun.awt.AWTUtilities;

import java.awt.SystemColor;
import java.awt.GridLayout;

import javax.swing.JTextPane;

import java.awt.BorderLayout;

import javax.swing.DropMode;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.table.TableModel;
import javax.swing.SpringLayout;

//@author A0116160W

public class GuiMain {

	// Field descriptor #52 I
	public static final int NORMAL = 0;
	// Field descriptor #52 I
	public static final int ICONIFIED = 1;
	// Field descriptor #52 I
	public static final int MAXIMIZED_HORIZ = 2;
	// Field descriptor #52 I
	public static final int MAXIMIZED_VERT = 4;
	// Field descriptor #52 I
	public static final int MAXIMIZED_BOTH = 6;

	static //For System Tray
	TrayIcon trayIcon;
	SystemTray tray;

	static JFrame frameRemembra;
	static JTextField inputField;
	static JTextArea feedback;
	private JScrollPane scrollPane_1;

	static JTabbedPane tabbedPane;
	static JTable todoTable;
	private JPanel colorPanel1;
	private ArrayList<String> keywords = new ArrayList<String>(5);

	private static String[] tableHeaders = { "", 
		"No.", "Label", "Task", "Description", "Deadline", "Done"
	};
	static DefaultTableModel myData;
	static DefaultTableModel myDoneData;

	static Object[][] objects = new Object[9][7];

	private static final String COMMIT_ACTION = "commit";
	static PanelWithShadow calendarTab;
	private JPanel panel_2;
	static PanelWithShadow todoTab;
	private JLabel timeLabel = new JLabel();
	private JPanel panel_7;
	private JPanel panel_3;
	private JPanel panel_4;
	private JPanel panel_5;
	private JPanel panel_6;
	static PanelWithShadow doneTab;
	private JScrollPane doneScroll;
	static JTable doneTable;
	private JTextPane tableHeading2;
	private JTextPane tableHeading1;
	static PanelWithShadow searchTab;
	private JTextPane tableHeading4;
	static JTable searchTable;
	private JScrollPane searchScroll;
	static DefaultTableModel searchData;
	static PanelWithShadow allTab;
	private JTextPane tableHeading3;
	static JTable allTable;
	private JScrollPane allScroll;
	static DefaultTableModel allData;

	/**
	 * Launch the application.
	 */
	public void launch() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuiMain window = new GuiMain();
					window.frameRemembra.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				//To update system on which list to work on, whenever tab is changed
				ChangeListener changeListener = new ChangeListener() {
					public void stateChanged(ChangeEvent e) {
						JTabbedPane p = (JTabbedPane)e.getSource();
						int idx = p.getSelectedIndex(); 
						LogicMain logic = new LogicMain();
						if (idx == 0){
							logic.processInput(";view ;!done");
						} else if (idx == 1){
							logic.processInput(";view ;done");
						} else if (idx == 2){
							logic.processInput(";view ;all");
						}
					}
				};
				tabbedPane.addChangeListener(changeListener);



				inputField.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg) {
						/*
						 * Below is just an example of what will happen when user presses enter after 
						 * typing something in the user input text field at the bottom of the GUI.
						 */
						String inputStr = inputField.getText(); //this is to get the user input text

						//this clears the input field
						inputField.setText("");

						if (inputStr.startsWith("help")) {
							try {
								GuiDisplay.displayHelp(inputStr);
							} catch (IOException e) {
								e.printStackTrace();
							}
						} else {
							GuiDisplay.display(inputStr);
							//GridLabel.createlabel();
						}

					}


				});
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GuiMain() {
		new Splash();
		initialize();
		GuiDisplay.initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("serial")
	private void initialize() {
		frameRemembra = new JFrame("Remembra");
		frameRemembra.setResizable(false);
		frameRemembra.setFont(new Font("WhitneyBook", Font.PLAIN, 12));
		frameRemembra.setForeground(new Color(0, 0, 0));
		frameRemembra.setTitle("Remembra V0.4");
		Timer timer = new Timer(500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateTime();
			}
		});
		timer.setRepeats(true);
		timer.setCoalesce(true);
		//timer.setInitialDelay(0);
		timer.start();

		try {
			frameRemembra.setUndecorated(true);
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
			UIManager.put("TabbedPane.tabAreaInsets", new javax.swing.plaf.InsetsUIResource(0,30,0,0));
			UIManager.put("RootPane.setupButtonVisible", false);		
			AWTUtilities.setWindowOpaque(frameRemembra, false);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		frameRemembra.addWindowListener(new WindowAdapter() {
			@Override
			//this pops-up a confirmation dialog box when the user tries to exit the program
			public void windowClosing(WindowEvent arg0) {
				int option = JOptionPane.showConfirmDialog(frameRemembra, 
						"Are you sure?", 
						"Do you want to Exit?", JOptionPane.YES_NO_OPTION);
				if (option == JOptionPane.YES_OPTION) {
					System.exit(0);
				}

			}

		});
		frameRemembra.getContentPane().setBackground(Color.LIGHT_GRAY);
		frameRemembra.setBounds(100, 100, 1109, 704);
		frameRemembra.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frameRemembra.getContentPane().setLayout(null);


		panel_7 = new JPanel();
		panel_7.setBounds(31, 465, 752, 5);
		frameRemembra.getContentPane().add(panel_7);
		panel_7.setLayout(null);
		panel_7.setBackground(SystemColor.controlHighlight);

		panel_5 = new JPanel();
		panel_5.setBackground(new Color(204, 0, 51));
		panel_5.setBounds(0, 80, 39, 30);
		frameRemembra.getContentPane().add(panel_5);

		panel_6 = new JPanel();
		panel_6.setBackground(new Color(204, 0, 51));
		panel_6.setBounds(31, 80, 752, 5);
		frameRemembra.getContentPane().add(panel_6);

		panel_4 = new JPanel();
		panel_4.setBackground(new Color(204, 0, 51));
		panel_4.setBounds(780, 80, 28, 30);
		frameRemembra.getContentPane().add(panel_4);

		JLayeredPane displayPanel = new JLayeredPane();
		displayPanel.setBackground(SystemColor.controlHighlight);
		displayPanel.setBounds(34, 80, 749, 425);
		frameRemembra.getContentPane().add(displayPanel);

		tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
		tabbedPane.setBounds(0, 0, 750, 425);

		tabbedPane.setBackground(SystemColor.controlHighlight);
		tabbedPane.setBorder(null);
		tabbedPane.setFont(new Font("Levenim MT", Font.PLAIN, 13));
		displayPanel.setLayout(null);

		todoTab = new PanelWithShadow(5);
		todoTab.setForeground(Color.BLACK);
		todoTab.setBackground(Color.WHITE);
		tabbedPane.addTab("To-do", null, todoTab, null);

		myData = new DefaultTableModel(objects, tableHeaders) {
			public Class getColumnClass(int columnIndex) {
				int rowIndex = 0;
				Object o = getValueAt(rowIndex, columnIndex);
				if (o == null) {
					return Object.class;
				} else {
					return o.getClass();
				}
			}
		};
		todoTable = new JTable(myData);
		todoTable.setEnabled(false);
		todoTable.setRequestFocusEnabled(false);
		todoTable.setFocusable(false);
		todoTable.setFocusTraversalKeysEnabled(false);
		todoTable.setRowSelectionAllowed(false);
		todoTable.setForeground(new Color(0, 0, 0));
		todoTable.setFont(new Font("WhitneyBook", Font.PLAIN, 14));
		todoTable.setShowHorizontalLines(false);
		todoTable.setBorder(null);

		todoTable.setTableHeader(null);
		todoTab.setLayout(null);
		todoTable.getColumnModel().getColumn(0).setPreferredWidth(20);
		todoTable.getColumnModel().getColumn(1).setPreferredWidth(35);
		todoTable.getColumnModel().getColumn(2).setPreferredWidth(90);
		todoTable.getColumnModel().getColumn(3).setPreferredWidth(140);
		todoTable.getColumnModel().getColumn(4).setPreferredWidth(260);
		todoTable.getColumnModel().getColumn(5).setPreferredWidth(140);
		todoTable.getColumnModel().getColumn(6).setPreferredWidth(20);

		todoTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		tableHeading1 = new JTextPane();
		tableHeading1.setDisabledTextColor(new Color(204, 0, 51));
		tableHeading1.setText("     ID     LABEL          TASK                   DESCRIPTION                          DEADLINE");
		tableHeading1.setForeground(new Color(204, 0, 0));
		tableHeading1.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		tableHeading1.setEditable(false);
		tableHeading1.setBounds(5, 4, 623, 30);
		todoTab.add(tableHeading1);

		JScrollPane todoScroll = new JScrollPane(todoTable);
		todoScroll.setBounds(5, 32, 739, 346);
		todoTab.add(todoScroll);
		todoScroll.setBackground(new Color(255, 255, 255));
		todoScroll.setBorder(null);
		todoTable.setFillsViewportHeight(true);
		displayPanel.add(tabbedPane);

		doneTab = new PanelWithShadow(5);
		doneTab.setForeground(Color.BLACK);
		doneTab.setPaintBorderInsets(true);
		doneTab.setInheritAlpha(true);
		doneTab.setLayout(null);
		doneTab.setBackground(Color.WHITE);
		tabbedPane.addTab("Done", null, doneTab, null);
		tableHeading1.setText("     ID    LABEL         TASK                     DESCRIPTION                              DEADLINE");
		tableHeading1.setForeground(new Color(204, 0, 0));
		tableHeading1.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		tableHeading1.setEditable(false);
		tableHeading1.setBounds(5, 4, 739, 30);

		myDoneData = new DefaultTableModel(objects, tableHeaders) {
			public Class getColumnClass(int columnIndex) {
				int rowIndex = 0;
				Object o = getValueAt(rowIndex, columnIndex);
				if (o == null) {
					return Object.class;
				} else {
					return o.getClass();
				}
			}
		};

		doneTable = new JTable(myDoneData);
		doneTable.setEnabled(false);
		doneTable.setRequestFocusEnabled(false);
		doneTable.setFocusable(false);
		doneTable.setFocusTraversalKeysEnabled(false);
		doneTable.setRowSelectionAllowed(false);
		doneTable.setForeground(new Color(0, 0, 0));
		doneTable.setFont(new Font("WhitneyBook", Font.PLAIN, 14));
		doneTable.setShowHorizontalLines(false);
		doneTable.setBorder(null);

		doneTable.setTableHeader(null);
		doneTab.setLayout(null);
		doneTable.getColumnModel().getColumn(0).setPreferredWidth(20);
		doneTable.getColumnModel().getColumn(1).setPreferredWidth(35);
		doneTable.getColumnModel().getColumn(2).setPreferredWidth(90);
		doneTable.getColumnModel().getColumn(3).setPreferredWidth(140);
		doneTable.getColumnModel().getColumn(4).setPreferredWidth(260);
		doneTable.getColumnModel().getColumn(5).setPreferredWidth(140);
		doneTable.getColumnModel().getColumn(6).setPreferredWidth(20);
		doneTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		tableHeading2 = new JTextPane();
		tableHeading2.setBounds(5, 4, 739, 30);
		doneTab.add(tableHeading2);
		tableHeading2.setText("     ID    LABEL         TASK                     DESCRIPTION                              DEADLINE");
		tableHeading2.setForeground(new Color(204, 0, 0));
		tableHeading2.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		tableHeading2.setEditable(false);
		tableHeading2.setDisabledTextColor(new Color(204, 0, 51));
		doneTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		doneScroll = new JScrollPane(doneTable);
		doneScroll.setBounds(5, 32, 739, 346);
		doneScroll.setBackground(new Color(255, 255, 255));
		doneScroll.setBorder(null);
		doneTable.setFillsViewportHeight(true);
		doneTab.add(doneScroll);
		doneScroll.setViewportView(doneTable);

		allTab = new PanelWithShadow(5);
		allTab.setLayout(null);
		allTab.setPaintBorderInsets(true);
		allTab.setInheritAlpha(true);
		allTab.setForeground(Color.BLACK);
		allTab.setBackground(Color.WHITE);
		tabbedPane.addTab("All Tasks", null, allTab, null);

		tableHeading3 = new JTextPane();
		tableHeading3.setText("     ID    LABEL         TASK                     DESCRIPTION                              DEADLINE");
		tableHeading3.setForeground(new Color(204, 0, 0));
		tableHeading3.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		tableHeading3.setEditable(false);
		tableHeading3.setDisabledTextColor(new Color(204, 0, 51));
		tableHeading3.setBounds(5, 4, 739, 30);
		allTab.add(tableHeading3);

		allScroll = new JScrollPane();
		allScroll.setBounds(5, 32, 739, 346);
		allTab.add(allScroll);

		allData = new DefaultTableModel(objects, tableHeaders) {
			public Class getColumnClass(int columnIndex) {
				int rowIndex = 0;
				Object o = getValueAt(rowIndex, columnIndex);
				if (o == null) {
					return Object.class;
				} else {
					return o.getClass();
				}
			}
		};
		allTable = new JTable(allData);
		allTable.setFillsViewportHeight(true);
		allScroll.setViewportView(allTable);
		allTable.setEnabled(false);
		allTable.setRequestFocusEnabled(false);
		allTable.setFocusable(false);
		allTable.setFocusTraversalKeysEnabled(false);
		allTable.setRowSelectionAllowed(false);
		allTable.setForeground(new Color(0, 0, 0));
		allTable.setFont(new Font("WhitneyBook", Font.PLAIN, 14));
		allTable.setShowHorizontalLines(false);
		allTable.setBorder(null);

		allTable.setTableHeader(null);
		allTab.setLayout(null);
		allTable.getColumnModel().getColumn(0).setPreferredWidth(20);
		allTable.getColumnModel().getColumn(1).setPreferredWidth(35);
		allTable.getColumnModel().getColumn(2).setPreferredWidth(90);
		allTable.getColumnModel().getColumn(3).setPreferredWidth(140);
		allTable.getColumnModel().getColumn(4).setPreferredWidth(260);
		allTable.getColumnModel().getColumn(5).setPreferredWidth(140);
		allTable.getColumnModel().getColumn(6).setPreferredWidth(20);
		allTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);



		searchTab = new PanelWithShadow(5);
		searchTab.setLayout(null);
		searchTab.setPaintBorderInsets(true);
		searchTab.setInheritAlpha(true);
		searchTab.setForeground(Color.BLACK);
		searchTab.setBackground(Color.WHITE);
		tabbedPane.addTab("Search", null, searchTab, null);


		tableHeading4 = new JTextPane();
		tableHeading4.setText("     ID    LABEL         TASK                     DESCRIPTION                              DEADLINE");
		tableHeading4.setForeground(new Color(204, 0, 0));
		tableHeading4.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		tableHeading4.setEditable(false);
		tableHeading4.setDisabledTextColor(new Color(204, 0, 51));
		tableHeading4.setBounds(5, 4, 739, 30);
		searchTab.add(tableHeading4);

		searchScroll = new JScrollPane();
		searchScroll.setBounds(5, 32, 739, 346);
		searchTab.add(searchScroll);

		searchData = new DefaultTableModel(objects, tableHeaders){
			public Class getColumnClass(int columnIndex) {
				int rowIndex = 0;
				Object o = getValueAt(rowIndex, columnIndex);
				if (o == null) {
					return Object.class;
				} else {
					return o.getClass();
				}
			}
		};

		searchTable = new JTable(searchData);
		searchTable.setFillsViewportHeight(true);
		searchScroll.setViewportView(searchTable);
		searchTable.setEnabled(false);
		searchTable.setRequestFocusEnabled(false);
		searchTable.setFocusable(false);
		searchTable.setFocusTraversalKeysEnabled(false);
		searchTable.setRowSelectionAllowed(false);
		searchTable.setForeground(new Color(0, 0, 0));
		searchTable.setFont(new Font("WhitneyBook", Font.PLAIN, 14));
		searchTable.setShowHorizontalLines(false);
		searchTable.setBorder(null);

		searchTable.setTableHeader(null);
		searchTab.setLayout(null);
		searchTable.getColumnModel().getColumn(0).setPreferredWidth(20);
		searchTable.getColumnModel().getColumn(1).setPreferredWidth(35);
		searchTable.getColumnModel().getColumn(2).setPreferredWidth(90);
		searchTable.getColumnModel().getColumn(3).setPreferredWidth(140);
		searchTable.getColumnModel().getColumn(4).setPreferredWidth(260);
		searchTable.getColumnModel().getColumn(5).setPreferredWidth(140);
		searchTable.getColumnModel().getColumn(6).setPreferredWidth(20);
		searchTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		calendarTab = new PanelWithShadow(5);
		tabbedPane.addTab("Calendar", null, calendarTab, null);
		calendarTab.setBorder(BorderFactory.createTitledBorder("Calendar"));
		Calendar.createCalendar(calendarTab);
		calendarTab.setLayout(null);

		//switch between tabs
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
		tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);
		tabbedPane.setMnemonicAt(4, KeyEvent.VK_5);

		colorPanel1 = new JPanel();
		colorPanel1.setBounds(35, 508, 12, 76);
		frameRemembra.getContentPane().add(colorPanel1);
		colorPanel1.setBackground(new Color(51, 51, 51));
		colorPanel1.setLayout(null);

		PanelWithShadow inputPanel = new PanelWithShadow(5);
		inputPanel.setBounds(34, 517, 749, 60);
		frameRemembra.getContentPane().add(inputPanel);
		inputPanel.setBackground(new Color(255, 255, 255));

		inputField =  new JTextField();
		inputField.setBounds(25, 0, 719, 54);
		inputField.setMargin(new Insets(0, 0, 0, 0));
		inputField.setCaretColor(new Color(0, 0, 0));
		inputField.setAlignmentX(Component.RIGHT_ALIGNMENT);
		//inputField.putClientProperty("JTextField.variant", "search");
		inputPanel.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(12, 0, 24, 55);
		inputPanel.add(panel);
		inputField.setFont(new Font("Tahoma", Font.BOLD, 18));
		inputField.setForeground(new Color(165, 42, 42));
		inputField.setBackground(new Color(255, 255, 255));
		inputPanel.add(inputField);
		inputField.setColumns(49);
		//myData = new DefaultTableModel(objects, tableHeaders);
		//JLabel lblMonth = new JLabel("Task");
		//lblMonth.setBounds(160-lblMonth.getPreferredSize().width/2, 25, 100, 25);
		//calendar.add(lblMonth);

		keywords.add("add");
		keywords.add("view");
		keywords.add("delete");
		keywords.add("edit");
		keywords.add("description");
		keywords.add("undo");
		keywords.add("find");
		keywords.add("by");
		keywords.add("remind");
		keywords.add("label");
		keywords.add("search");
		keywords.add("power");
		keywords.add("start");
		keywords.add("end");

		// Without this, cursor always leaves text field
		inputField.setFocusTraversalKeysEnabled(false);

		Autocomplete autoComplete = new Autocomplete(inputField, keywords);

		PanelWithShadow feedbackpanel = new PanelWithShadow(5);
		feedbackpanel.setBounds(806, 80, 251, 545);
		frameRemembra.getContentPane().add(feedbackpanel);
		feedbackpanel.setBackground(new Color(51, 51, 51));
		feedbackpanel.setLayout(null);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(12, 12, 238, 533);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setBorder(null);
		feedbackpanel.add(scrollPane_1);
		scrollPane_1.setBackground(new Color(51, 51, 51));

		feedback = new JTextArea();
		feedback.setLineWrap(true);
		feedback.setWrapStyleWord(true);
		feedback.setAutoscrolls(false);
		feedback.setColumns(6);
		scrollPane_1.setViewportView(feedback);
		feedback.setForeground(UIManager.getColor("Button.background"));
		feedback.setBackground(new Color(51, 51, 51));
		feedback.setRows(4);
		feedback.setFont(new Font("WhitneyBook", Font.PLAIN, 15));
		feedback.setEditable(false);

		JPanel panel_8 = new JPanel();
		panel_8.setBackground(new Color(51, 51, 51));
		panel_8.setBounds(5, 5, 244, 539);
		feedbackpanel.add(panel_8);

		panel_2 = new PanelWithShadow(5);
		panel_2.setLayout(null);
		panel_2.setBackground(new Color(204, 0, 51));
		panel_2.setBounds(0, 0, 1057, 114);
		frameRemembra.getContentPane().add(panel_2);

		timeLabel.setFont(new Font("Segoe UI Light", Font.BOLD, 15));
		timeLabel.setForeground(new Color(255, 255, 255));
		timeLabel.setBounds(876, 0, 181, 22);
		panel_2.add(timeLabel);
		timeLabel.setText("Time");

		JTextPane txtpnRemembra = new JTextPane();
		txtpnRemembra.setEditable(false);
		txtpnRemembra.setForeground(new Color(255, 255, 255));
		txtpnRemembra.setBackground(new Color(204, 0, 51));
		txtpnRemembra.setFont(new Font("Segoe UI Semilight", Font.PLAIN, 41));
		txtpnRemembra.setText("REMEMBRA");
		txtpnRemembra.setBounds(12, 22, 770, 66);
		panel_2.add(txtpnRemembra);

		panel_3 = new JPanel();
		panel_3.setBounds(0, 0, 1057, 110);
		panel_2.add(panel_3);
		panel_3.setBackground(new Color(204, 0, 51));

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.controlHighlight);
		panel_1.setBounds(0, 0, 1057, 625);
		frameRemembra.getContentPane().add(panel_1);
		SpringLayout sl_panel_1 = new SpringLayout();
		panel_1.setLayout(sl_panel_1);

		JCheckBox chckbxStillIncomplete = new JCheckBox("Still Incomplete");
		sl_panel_1.putConstraint(SpringLayout.NORTH, chckbxStillIncomplete, 465, SpringLayout.NORTH, panel_1);
		sl_panel_1.putConstraint(SpringLayout.WEST, chckbxStillIncomplete, 547, SpringLayout.WEST, panel_1);
		sl_panel_1.putConstraint(SpringLayout.SOUTH, chckbxStillIncomplete, 495, SpringLayout.NORTH, panel_1);
		sl_panel_1.putConstraint(SpringLayout.EAST, chckbxStillIncomplete, 678, SpringLayout.WEST, panel_1);
		chckbxStillIncomplete.setHorizontalAlignment(SwingConstants.RIGHT);
		chckbxStillIncomplete.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12));
		chckbxStillIncomplete.setFocusable(false);
		chckbxStillIncomplete.setEnabled(false);
		chckbxStillIncomplete.setBackground(SystemColor.controlHighlight);
		panel_1.add(chckbxStillIncomplete);

		JCheckBox chckbxTasksDone = new JCheckBox("Tasks Done!");
		sl_panel_1.putConstraint(SpringLayout.NORTH, chckbxTasksDone, 465, SpringLayout.NORTH, panel_1);
		sl_panel_1.putConstraint(SpringLayout.WEST, chckbxTasksDone, 673, SpringLayout.WEST, panel_1);
		sl_panel_1.putConstraint(SpringLayout.SOUTH, chckbxTasksDone, 495, SpringLayout.NORTH, panel_1);
		sl_panel_1.putConstraint(SpringLayout.EAST, chckbxTasksDone, 780, SpringLayout.WEST, panel_1);
		panel_1.add(chckbxTasksDone);
		chckbxTasksDone.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12));
		chckbxTasksDone.setFocusable(false);
		chckbxTasksDone.setEnabled(false);
		chckbxTasksDone.setBackground(SystemColor.controlHighlight);
		chckbxTasksDone.setSelected(true);
		chckbxTasksDone.setHorizontalAlignment(SwingConstants.RIGHT);

		//To prevent autoscrolling
		DefaultCaret caret1 = (DefaultCaret) feedback.getCaret();
		caret1.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);

		inputField.getDocument().addDocumentListener(autoComplete);

		// Maps the tab key to the commit action, which finishes the autocomplete
		// when given a suggestion
		inputField.getInputMap().put(KeyStroke.getKeyStroke("TAB"), COMMIT_ACTION);
		inputField.getActionMap().put(COMMIT_ACTION, autoComplete.new CommitAction());

		//Sets the Frame's display icon
		setFrameIcon();

		//Check for system tray support before initializing system tray
		if (checkSystemTraySupport()) {
			initSystemTray();
		}

		//Activate Jframe windowstate listener for hiding program into system tray
		activateWindowStateListener();
		frameRemembra.addWindowListener( new WindowAdapter() {
			public void windowOpened( WindowEvent e ) {
				inputField.requestFocus();
			}
		} );
		//To center the frame on startup wrt device screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frameRemembra.setLocation(dim.width/2-frameRemembra.getSize().width/2, dim.height/2-frameRemembra.getSize().height/2);

	}


	//Updates the label which displays the time
	void updateTime() {
		Date date = new Date();
		String str = DateFormat.getDateTimeInstance().format(date);
		timeLabel.setText(str);
	}
	
	//@author A0112898U
	/**
	 * Sets the JFrame's top left programme displayIcon
	 */
	void setFrameIcon() {
		frameRemembra.setIconImage(Toolkit.getDefaultToolkit().getImage("media/logo.png"));	
	}


	//@author A0112898U
	/**
	 * Checks if the system supports system tray
	 */
	boolean checkSystemTraySupport() {
		if (SystemTray.isSupported()) {
			tray = SystemTray.getSystemTray();
			return true;
		}
		return false;
	}



	//@author A0112898U
	/**
	 * Inits the system tray outlook, display name when hovering over and adding options 
	 * to the popup menu when right clicked at the system tray. This functions also adds 
	 * the action listeners for the options
	 * 
	 * For now it only supports 'Exit' and re'Open' of the programme.
	 */
	void initSystemTray() {
		//Set image when program is in system tray
		Image image = Toolkit.getDefaultToolkit().getImage("media/logo.png");
		
		//Action Listener to exit the programme ONLY when in system tray
		ActionListener exitListener = new ActionListener() {
			//If clicked on the exit option
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		};

		//Action Listener to open the programme frame ONLY when in system tray
		ActionListener openListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameRemembra.setVisible(true);
				frameRemembra.setExtendedState(JFrame.NORMAL);
			}
		};

		/* Popup Menu @ system tray */
		PopupMenu popup = new PopupMenu();
		MenuItem defaultItem;

		//Added a 'Exit' option to the menu when right clicked
		defaultItem = new MenuItem("Exit");
		defaultItem.addActionListener(exitListener);
		popup.add(defaultItem);

		//Added a 'Option' option to the menu when right clicked
		
		defaultItem = new MenuItem("Open");
		defaultItem.addActionListener(openListener);
		popup.add(defaultItem);

		trayIcon = new TrayIcon(image, "Remembra", popup);
		trayIcon.setImageAutoSize(true);
		trayIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                	frameRemembra.setVisible(true);
    				frameRemembra.setExtendedState(JFrame.NORMAL);
                }
            }
        });
	}

	//@author A0112898U
	/**
	 * Activate Window State Listener for the JFrame, this is for the implementation to
	 * hide frame into system tray
	 */
	void activateWindowStateListener() {
		frameRemembra.addWindowStateListener(new WindowStateListener() {
			public void windowStateChanged(WindowEvent e) {

				//If click on the minimize icon on the window, this function will 
				//detect the window new "ICONFIED" state and activate system tray
				if (e.getNewState() == ICONIFIED) {
					try {
						tray.add(trayIcon);
						frameRemembra.setVisible(false);
					} catch (AWTException ex) {
						System.out.println("unable to add to tray");
					}
				}

				//If click on the 'open' open option to re-open the program,
				//this call will reinstate the JFrame's visibility and remove trayicon
				if (e.getNewState() == MAXIMIZED_BOTH || e.getNewState() == NORMAL) {
					tray.remove(trayIcon);
					frameRemembra.setVisible(true);
				}
			}
		});
	}
}