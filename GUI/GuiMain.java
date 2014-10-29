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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
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
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import com.sun.awt.AWTUtilities;
import java.awt.SystemColor;

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

	private static LinkedList<Task> bufferTasksList;
	private static LinkedList<Label> bufferLabelsList;
	private static Operations operations;
	private static StorageMain storageMain;

	static JFrame frameRemembra;
	static JTextArea mainDisplay;
	private static JTextField inputField;
	private JScrollPane scrollPane;
	static JTextArea feedback;
	private JScrollPane scrollPane_1;

	private static Operations operations1;
	private LogicMain logic;
	private JTabbedPane tabbedPane;
	static JTable table_1;
	private JPanel colorPanel1;
	private ArrayList<String> keywords = new ArrayList<String>(5);

	private static String[] tableHeaders = {
		"No.", "Label", "Task", "Description", "Deadline"
	};
	private static DefaultTableModel myData;

	static Object[][] objects = new Object[9][5];

	private static final String COMMIT_ACTION = "commit";
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_4;

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
						String displayStr = mainDisplay.getText();
						if(displayStr.equals("Welcome to Remembra!\nFor a quick guide, type help and press enter.")){
							displayStr = "";
						}


						//this prints the input text on the display
						mainDisplay.setText(displayStr + "\n");
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
							updateTable();
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

	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("serial")
	private void initialize() {
		frameRemembra = new JFrame("Remembra");

		try {
			JLabel label = new JLabel(new ImageIcon(ImageIO.read(new File("media/b.jpg"))));
			frameRemembra.setContentPane(label);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		frameRemembra.setResizable(false);
		frameRemembra.setFont(new Font("Accord Light SF", Font.PLAIN, 12));
		frameRemembra.setForeground(new Color(0, 0, 0));
		frameRemembra.setTitle("Remembra V0.3");

		try
		{
			frameRemembra.setUndecorated(true);
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
			UIManager.put("TabbedPane.tabAreaInsets", new javax.swing.plaf.InsetsUIResource(0,0,0,20));
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
		frameRemembra.setBounds(100, 100, 954, 681);
		frameRemembra.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frameRemembra.getContentPane().setLayout(null);
		//frameRemembra.getContentPane().setLayout(null);

		JPanel inputPanel = new JPanel();
		inputPanel.setBounds(45, 501, 810, 58);
		frameRemembra.getContentPane().add(inputPanel);
		inputPanel.setBackground(new Color(255, 255, 255));

		inputField =  new JTextField();
		inputField.setBounds(12, 0, 774, 58);
		inputField.setMargin(new Insets(0, 0, 0, 0));
		inputField.setCaretColor(new Color(0, 0, 0));
		inputField.setAlignmentX(Component.RIGHT_ALIGNMENT);
		inputField.putClientProperty("JTextField.variant", "search");
		inputPanel.setLayout(null);
		inputField.setFont(new Font("Tahoma", Font.BOLD, 18));
		inputField.setForeground(new Color(165, 42, 42));
		inputField.setBackground(new Color(255, 255, 255));
		inputPanel.add(inputField);
		inputField.setColumns(49);

		JLayeredPane displayPanel = new JLayeredPane();
		displayPanel.setBackground(new Color(204, 204, 255));
		displayPanel.setBounds(45, 46, 605, 412);
		frameRemembra.getContentPane().add(displayPanel);
		myData = new DefaultTableModel(objects, tableHeaders);

		tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
		tabbedPane.setBounds(0, 0, 605, 411);

		tabbedPane.setBackground(new Color(204, 204, 255));
		tabbedPane.setBorder(null);
		tabbedPane.setFont(new Font("Levenim MT", Font.PLAIN, 13));

		table_1 = new JTable(myData);
		table_1.setBorder(null);

		table_1.getColumnModel().getColumn(0).setPreferredWidth(30);
		table_1.getColumnModel().getColumn(1).setPreferredWidth(67);
		table_1.getColumnModel().getColumn(2).setPreferredWidth(211);
		table_1.getColumnModel().getColumn(3).setPreferredWidth(227);
		JScrollPane scrollPaneTable = new JScrollPane(table_1);
		scrollPaneTable.setBackground(new Color(255, 255, 255));
		scrollPaneTable.setBorder(null);
		table_1.setFillsViewportHeight(true);

		scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		tabbedPane.addTab("Display", null, scrollPane, null);

		mainDisplay = new JTextArea();
		mainDisplay.setMargin(new Insets(0, 0, 0, 0));
		mainDisplay.setColumns(78);
		scrollPane.setViewportView(mainDisplay);
		mainDisplay.setRows(15);
		mainDisplay.setForeground(Color.DARK_GRAY);
		mainDisplay.setFont(new Font("Consolas", Font.BOLD, 15));
		mainDisplay.setBackground(SystemColor.menu);
		mainDisplay.setEditable(false);
		mainDisplay.setText("Welcome to Remembra!\nFor a quick guide, type help and press enter.");
		mainDisplay.setBounds(14, 22, 323, 120);
		tabbedPane.addTab("Table", null, scrollPaneTable, null);


		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
		displayPanel.setLayout(null);
		displayPanel.add(tabbedPane);

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
		// Without this, cursor always leaves text field
		inputField.setFocusTraversalKeysEnabled(false);


		Autocomplete autoComplete = new Autocomplete(inputField, keywords);

		JPanel feedbackpanel = new JPanel();
		feedbackpanel.setBounds(686, 46, 169, 412);
		frameRemembra.getContentPane().add(feedbackpanel);
		feedbackpanel.setBackground(SystemColor.menu);
		feedbackpanel.setLayout(null);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(12, 0, 158, 411);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setBorder(null);
		feedbackpanel.add(scrollPane_1);
		scrollPane_1.setBackground(new Color(220, 220, 220));

		feedback = new JTextArea();
		feedback.setColumns(6);
		feedback.setText("Feedback Display");
		scrollPane_1.setViewportView(feedback);
		feedback.setWrapStyleWord(true);
		feedback.setForeground(Color.DARK_GRAY);
		feedback.setBackground(SystemColor.menu);
		feedback.setRows(4);
		feedback.setFont(new Font("Bookman Old Style", Font.BOLD, 13));
		feedback.setEditable(false);

		colorPanel1 = new JPanel();
		colorPanel1.setBackground(new Color(204, 0, 0));
		colorPanel1.setBounds(34, 491, 23, 79);
		frameRemembra.getContentPane().add(colorPanel1);
		colorPanel1.setLayout(null);

		panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(0, 0, 0));
		panel.setBounds(45, 491, 821, 79);
		frameRemembra.getContentPane().add(panel);

		panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBackground(new Color(0, 128, 0));
		panel_3.setBounds(676, 35, 190, 44);
		frameRemembra.getContentPane().add(panel_3);

		panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBackground(Color.BLACK);
		panel_1.setBounds(676, 35, 190, 434);
		frameRemembra.getContentPane().add(panel_1);

		panel_4 = new JPanel();
		panel_4.setLayout(null);
		panel_4.setBackground(new Color(25, 25, 112));
		panel_4.setBounds(34, 415, 170, 52);
		frameRemembra.getContentPane().add(panel_4);

		panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBackground(Color.BLACK);
		panel_2.setBounds(34, 35, 627, 395);
		frameRemembra.getContentPane().add(panel_2);
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

		//Activate Jfram windowstate listener for hiding programe into system tray
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
	/**
	 * Updates and displays the table.
	 */
	public static void updateTable() {
		DefaultTableModel tableModel = (DefaultTableModel) table_1.getModel();
		tableModel.setRowCount(0);

		if (!(LogicMain.bufferTasksList.size()==0)){
			Item firstTask = LogicMain.bufferTasksList.get(0);

			myData = (DefaultTableModel) table_1.getModel();
			if(!firstTask.getName().equals(Operations.EMPTY_MESSAGE)) {

				for(int i=0; i<LogicMain.bufferTasksList.size(); i++) {
					Task tempTask = LogicMain.bufferTasksList.get(i);
					Object[] data = new Object[5];

					data[0] = i+1;
					data[1] = tempTask.getLabel();
					data[2] = tempTask.getName();
					data[3] = tempTask.getDescription();
					data[4] = tempTask.getDeadline();

					myData.addRow(data);
				}

				table_1.setModel(myData);
			}
		}
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