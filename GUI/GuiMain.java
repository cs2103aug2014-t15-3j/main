/**
 * @author Sankalp 
 * 
 * 
 */
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultCaret;

import com.sun.awt.AWTUtilities;

import java.awt.SystemColor;
import java.awt.GridLayout;

import javax.swing.JTextPane;
import java.awt.BorderLayout;

/*********************************************************************/
/******************* QA I - Refactor Code I***************************/
/*********************************************************************/
// @Sankalp - GuiMain.java
//
// 1. could you add in some comments into the code?
// 2. for lines > 80 char do indent them to the next line 
// 3. Do correct me if i'm seeing it wrongly, For initialize(), 
//    is that a function in a function?
//    If it is you might want to do something about the nested function 
//    'windowClosing()'. Nested functions are strictly not allowed?
//	  Do extract it out is possible.
//
// @Sankalp - GuiMain.java
/*********************************************************************/
/*********************************************************************/

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

	//For System Tray
	TrayIcon trayIcon;
	SystemTray tray;

	static JFrame frameRemembra;
	private static JTextField inputField;
	static JTextArea feedback;
	private JScrollPane scrollPane_1;

	private static Operations operations1;
	private LogicMain logic;
	private JTabbedPane tabbedPane;
	static JTable table_1;
	private JPanel colorPanel1;
	private ArrayList<String> keywords = new ArrayList<String>(5);

	private static String[] tableHeaders = { "", 
		"No.", "Label", "Task", "Description", "Deadline"
	};
	static DefaultTableModel myData;

	static Object[][] objects = new Object[9][5];

	private static final String COMMIT_ACTION = "commit";
	private static JPanel calendar;
	private JPanel panel_2;
	private JPanel tableDisplay;
	private JLabel timeLabel = new JLabel();
	private JPanel panel_7;
	private JPanel panel_3;
	private JPanel panel_4;
	private JPanel panel_5;
	private JPanel panel_6;

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

				inputField.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg){
						/*
						 * Below is just an example of what will happen when user presses enter after 
						 * typing something in the user input text field at the bottom of the GUI.
						 */
						String inputStr = inputField.getText(); //this is to get the user input text

						//this clears the input field
						inputField.setText("");

						if (inputStr.startsWith("help")){
							try {
								GuiDisplay.displayHelp(inputStr);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}else {
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
		initialize();
		GuiDisplay.initialize();
		}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("serial")
	private void initialize() {
		frameRemembra = new JFrame("Remembra");
		//For background
		//		try {
		//			JLabel label = new JLabel(new ImageIcon(ImageIO.read(new File("media/j.jpg"))));
		//			frameRemembra.setContentPane(label);
		//		} catch (IOException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}

		frameRemembra.setResizable(false);
		frameRemembra.setFont(new Font("Accord Light SF", Font.PLAIN, 12));
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
		timer.setInitialDelay(0);
		timer.start();

		try
		{
			frameRemembra.setUndecorated(true);
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
			UIManager.put("TabbedPane.tabAreaInsets", new javax.swing.plaf.InsetsUIResource(0,30,0,0));
			UIManager.put("RootPane.setupButtonVisible", false);		
			AWTUtilities.setWindowOpaque(frameRemembra, false);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		frameRemembra.addWindowListener(new WindowAdapter() {
			@Override
			//this pops-up a confirmation dialog box when the user tries to exit the program
			public void windowClosing(WindowEvent arg0) {
				int option = JOptionPane.showConfirmDialog(frameRemembra, "Do you want to save changes before exiting?", "Save and Exit?", JOptionPane.YES_NO_OPTION);
				if (option == 0){

					operations1 = new Operations();
					logic = new LogicMain();
					String saveOperation = operations1.saveOperations.get(0);
					logic.processInput(saveOperation);
				}

			}

		});

		frameRemembra.setResizable(false);
		frameRemembra.getContentPane().setBackground(Color.LIGHT_GRAY);
		frameRemembra.setBounds(100, 100, 1043, 675);
		frameRemembra.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frameRemembra.getContentPane().setLayout(null);

		panel_7 = new JPanel();
		panel_7.setBounds(642, 115, 3, 347);
		frameRemembra.getContentPane().add(panel_7);
		panel_7.setLayout(null);
		panel_7.setBackground(SystemColor.controlHighlight);

		panel_5 = new JPanel();
		panel_5.setBackground(new Color(204, 0, 51));
		panel_5.setBounds(0, 80, 39, 30);
		frameRemembra.getContentPane().add(panel_5);

		panel_6 = new JPanel();
		panel_6.setBackground(new Color(204, 0, 51));
		panel_6.setBounds(31, 80, 610, 5);
		frameRemembra.getContentPane().add(panel_6);

		panel_4 = new JPanel();
		panel_4.setBackground(new Color(204, 0, 51));
		panel_4.setBounds(639, 80, 69, 30);
		frameRemembra.getContentPane().add(panel_4);

		JLayeredPane displayPanel = new JLayeredPane();
		displayPanel.setBackground(SystemColor.controlHighlight);
		displayPanel.setBounds(34, 80, 705, 382);
		frameRemembra.getContentPane().add(displayPanel);

		tabbedPane = new JTabbedPane(JTabbedPane.RIGHT);
		tabbedPane.setBounds(0, 0, 705, 382);

		tabbedPane.setBackground(SystemColor.controlHighlight);
		tabbedPane.setBorder(null);
		tabbedPane.setFont(new Font("Levenim MT", Font.PLAIN, 13));
		displayPanel.setLayout(null);

		tableDisplay = new PanelWithShadow(5);
		tableDisplay.setBackground(Color.WHITE);
		tabbedPane.addTab("Table", null, tableDisplay, null);

		myData = new DefaultTableModel(objects, tableHeaders);
		table_1 = new JTable(myData);
		table_1.setForeground(new Color(0, 0, 0));
		table_1.setFont(new Font("Dialog", Font.PLAIN, 17));
		table_1.setShowHorizontalLines(false);
		table_1.setBorder(null);
		table_1.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		table_1.setTableHeader(null);
		tableDisplay.setLayout(null);
		table_1.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		table_1.setTableHeader(null);
		table_1.getColumnModel().getColumn(0).setPreferredWidth(10);
		table_1.getColumnModel().getColumn(1).setPreferredWidth(10);
		table_1.getColumnModel().getColumn(2).setPreferredWidth(65);
		table_1.getColumnModel().getColumn(3).setPreferredWidth(100);
		table_1.getColumnModel().getColumn(4).setPreferredWidth(210);
		table_1.getColumnModel().getColumn(5).setPreferredWidth(100);

		table_1.getColumnModel().getColumn(5).setCellRenderer(new MyCellRenderer());
		tableDisplay.setLayout(null);


		JTextPane textPane = new JTextPane();
		textPane.setText("   ID       LABEL       TASK               DESCRIPTION                          DEADLINE");
		textPane.setForeground(new Color(204, 0, 0));
		textPane.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		textPane.setEditable(false);
		textPane.setBounds(5, 0, 600, 33);
		tableDisplay.add(textPane);

		JScrollPane scrollPaneTable = new JScrollPane(table_1);
		scrollPaneTable.setBounds(5, 28, 600, 343);
		tableDisplay.add(scrollPaneTable);
		scrollPaneTable.setBackground(new Color(255, 255, 255));
		scrollPaneTable.setBorder(null);
		table_1.setFillsViewportHeight(true);
		displayPanel.add(tabbedPane);

		calendar = new PanelWithShadow(5);
		tabbedPane.addTab("Calendar", null, calendar, null);
		calendar.setLayout(new GridLayout(0, 1, 0, 0));
		calendar.setBorder(BorderFactory.createTitledBorder("Calendar"));

		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

		colorPanel1 = new JPanel();
		colorPanel1.setBounds(34, 494, 15, 78);
		frameRemembra.getContentPane().add(colorPanel1);
		colorPanel1.setBackground(new Color(51, 51, 51));
		colorPanel1.setLayout(null);
		//frameRemembra.getContentPane().setLayout(null);

		PanelWithShadow inputPanel = new PanelWithShadow(5);
		inputPanel.setBounds(34, 501, 610, 65);
		frameRemembra.getContentPane().add(inputPanel);
		inputPanel.setBackground(new Color(255, 255, 255));

		inputField =  new JTextField();
		inputField.setBounds(25, 0, 581, 59);
		inputField.setMargin(new Insets(0, 0, 0, 0));
		inputField.setCaretColor(new Color(0, 0, 0));
		inputField.setAlignmentX(Component.RIGHT_ALIGNMENT);
		inputField.putClientProperty("JTextField.variant", "search");
		inputPanel.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 36, 59);
		inputPanel.add(panel);
		inputField.setFont(new Font("Tahoma", Font.BOLD, 18));
		inputField.setForeground(new Color(165, 42, 42));
		inputField.setBackground(new Color(255, 255, 255));
		inputPanel.add(inputField);
		inputField.setColumns(49);
		myData = new DefaultTableModel(objects, tableHeaders);
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

		// Without this, cursor always leaves text field
		inputField.setFocusTraversalKeysEnabled(false);


		Autocomplete autoComplete = new Autocomplete(inputField, keywords);

		PanelWithShadow feedbackpanel = new PanelWithShadow(5);
		feedbackpanel.setBounds(739, 80, 250, 516);
		frameRemembra.getContentPane().add(feedbackpanel);
		feedbackpanel.setBackground(new Color(51, 51, 51));
		feedbackpanel.setLayout(null);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(5, 5, 245, 511);
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
		feedback.setFont(new Font("Calibri", Font.PLAIN, 15));
		feedback.setEditable(false);

		//		 DropShadowBorder shadow = new DropShadowBorder();
		//	        shadow.setShadowColor(Color.BLACK);
		//	        shadow.setShowLeftShadow(true);
		//	        shadow.setShowRightShadow(true);
		//	        shadow.setShowBottomShadow(true);
		//	        shadow.setShowTopShadow(true);
		//	        tableDisplay.setBorder(shadow);
		//	        calendar.setBorder(shadow);

		panel_2 = new PanelWithShadow(5);
		panel_2.setLayout(null);
		panel_2.setBackground(new Color(204, 0, 51));
		panel_2.setBounds(0, 0, 989, 114);
		frameRemembra.getContentPane().add(panel_2);

		timeLabel.setFont(new Font("Segoe UI Light", Font.BOLD, 13));
		timeLabel.setForeground(new Color(255, 255, 255));
		timeLabel.setBounds(818, 0, 171, 22);
		panel_2.add(timeLabel);
		timeLabel.setText("Time");

		JTextPane txtpnRemembra = new JTextPane();
		txtpnRemembra.setEditable(false);
		txtpnRemembra.setForeground(new Color(255, 255, 255));
		txtpnRemembra.setBackground(new Color(204, 0, 51));
		txtpnRemembra.setFont(new Font("Segoe UI Semilight", Font.PLAIN, 41));
		txtpnRemembra.setText("REMEMBRA");
		txtpnRemembra.setBounds(12, 22, 631, 64);
		panel_2.add(txtpnRemembra);

		panel_3 = new JPanel();
		panel_3.setBounds(0, 0, 989, 110);
		panel_2.add(panel_3);
		panel_3.setBackground(new Color(204, 0, 51));

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBackground(SystemColor.controlHighlight);
		panel_1.setBounds(0, 0, 989, 596);
		frameRemembra.getContentPane().add(panel_1);
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
		if(checkSystemTraySupport()){
			initSystemTray();
		}

		//Activate Jframe windowstate listener for hiding program into system tray
		activateWindowStateListener();
		frameRemembra.addWindowListener( new WindowAdapter() {
			public void windowOpened( WindowEvent e ){
				inputField.requestFocus();
			}
		} );

	}


	//@author A0112898U
	/**
	 * Sets the JFrame's top left programme displayIcon
	 * 
	 */
	void setFrameIcon(){

		frameRemembra.setIconImage(Toolkit.getDefaultToolkit().getImage("media/logo.png"));	
	}



	//@author A0112898U
	/**
	 * Checks if the system supports system tray
	 * 
	 */
	boolean checkSystemTraySupport(){

		if(SystemTray.isSupported()){

			System.out.println("system tray supported");
			tray = SystemTray.getSystemTray();
			return true;
		}

		System.out.println("system tray not supported, check taskbar when minimized");
		return false;
	}



	//@author A0112898U
	/**
	 * Inits the system tray outlook, display name when hovering over and adding options 
	 * to the popup menu when right clicked at the system tray. This functions also adds 
	 * the action listeners for the options
	 * 
	 * For now it only supports 'Exit' and re'Open' of the programme.
	 * 
	 */
	void initSystemTray(){

		//Set image when program is in system tray
		Image image = Toolkit.getDefaultToolkit().getImage("media/logo.png");

		//Action Listener to exit the programme ONLY when in system tray
		ActionListener exitListener = new ActionListener() {

			//If clicked on the exit option
			public void actionPerformed(ActionEvent e) {

				System.out.println("Exiting Remembra....");
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

	}

	//Updates the label which displays the time
	void updateTime(){
		Date date = new Date();
		String str = DateFormat.getDateTimeInstance().format(date);
		timeLabel.setText(str);
	}


	//@author A0112898U
	/**
	 * activate Window State Listener for the JFrame, this is for the implementation to
	 * hide frame into system tray
	 * 
	 */
	void activateWindowStateListener(){

		frameRemembra.addWindowStateListener(new WindowStateListener() {

			public void windowStateChanged(WindowEvent e) {

				//If click on the minimize icon on the window, this function will 
				//detect the window new "ICONFIED" state and activate system tray
				if(e.getNewState() == ICONIFIED){

					try {

						tray.add(trayIcon);
						frameRemembra.setVisible(false);

						System.out.println("added to SystemTray");

					} catch (AWTException ex) {

						System.out.println("unable to add to tray");
					}
				}

				//If click on the 'open' open option to re-open the program,
				//this call will reinstate the JFrame's visibility and remove trayicon
				if(e.getNewState() == MAXIMIZED_BOTH || e.getNewState() == NORMAL){

					tray.remove(trayIcon);
					frameRemembra.setVisible(true);
					System.out.println("Tray icon removed");

				}
			}
		});
	}
}