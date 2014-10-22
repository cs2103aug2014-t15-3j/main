/**
 * @author Sankalp 
 * 
 * 
 */
import java.awt.AWTException;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import java.awt.FlowLayout;

import javax.swing.JTextArea;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowStateListener;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTabbedPane;

import com.sun.awt.AWTUtilities;

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
    
	private JFrame frameRemembra;
	static JTextArea mainDisplay;
	private static JTextField inputField;
	private JScrollPane scrollPane;
	static JTextArea feedback;
	private JScrollPane scrollPane_1;

	private Operations operations;
	private LogicMain logic;
	private JTabbedPane tabbedPane;
	private JTable table_1;
	private JPanel colorPanel1;
	private JPanel colorPanel2;
	private JPanel colorPanel3;
	private JPanel colorPanel4;


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
						
						if (inputStr.equalsIgnoreCase("help")){
							GuiDisplay.displayHelp();
						}else {
							GuiDisplay.display(inputStr);
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
		frameRemembra.setResizable(false);
		frameRemembra.setFont(new Font("Accord Light SF", Font.PLAIN, 12));
		frameRemembra.setForeground(new Color(0, 0, 0));
		frameRemembra.setBackground(new Color(255, 255, 255));
		frameRemembra.setTitle("Remembra V0.2");
		
		try
		{
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

					operations = new Operations();
					logic = new LogicMain();
					String saveOperation = operations.saveOperations.get(0);
					logic.processInput(saveOperation);
				}

			}

		});
		
		frameRemembra.setResizable(false);
		frameRemembra.getContentPane().setBackground(Color.LIGHT_GRAY);
		frameRemembra.setBounds(100, 100, 769, 576);
		frameRemembra.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frameRemembra.getContentPane().setLayout(null);

		JPanel feedbackpanel = new JPanel();
		feedbackpanel.setBounds(37, 375, 639, 89);
		frameRemembra.getContentPane().add(feedbackpanel);
		feedbackpanel.setBackground(Color.WHITE);
		feedbackpanel.setLayout(null);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBorder(null);
		scrollPane_1.setBounds(12, 12, 615, 77);
		feedbackpanel.add(scrollPane_1);
		scrollPane_1.setBackground(new Color(220, 220, 220));

		feedback = new JTextArea();
		feedback.setTabSize(10);
		scrollPane_1.setViewportView(feedback);
		feedback.setWrapStyleWord(true);
		feedback.setColumns(52);
		feedback.setForeground(Color.DARK_GRAY);
		feedback.setBackground(Color.WHITE);
		feedback.setRows(4);
		feedback.setFont(new Font("Arial", Font.BOLD, 12));
		feedback.setEditable(false);

		JPanel inputPanel = new JPanel();
		inputPanel.setBounds(51, 319, 625, 44);
		frameRemembra.getContentPane().add(inputPanel);
		inputPanel.setBackground(new Color(255, 255, 255));

		inputField =  new JTextField();
		inputField.setBounds(12, 0, 601, 44);
		inputField.setMargin(new Insets(0, 0, 0, 0));
		inputField.setCaretColor(new Color(0, 0, 0));
		inputField.setAlignmentX(Component.RIGHT_ALIGNMENT);
		inputField.putClientProperty("JTextField.variant", "search");
		inputPanel.setLayout(null);
		inputField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		inputField.setForeground(new Color(165, 42, 42));
		inputField.setBackground(new Color(255, 255, 255));
		inputPanel.add(inputField);
		inputField.setColumns(49);

		JLayeredPane displayPanel = new JLayeredPane();
		displayPanel.setBackground(new Color(220, 220, 220));
		displayPanel.setBounds(38, 25, 639, 282);
		frameRemembra.getContentPane().add(displayPanel);
		displayPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
		tabbedPane.setBackground(new Color(245, 245, 245));
		tabbedPane.setBorder(null);
		tabbedPane.setFont(new Font("Levenim MT", Font.PLAIN, 13));
		displayPanel.add(tabbedPane);

		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(
				new Object[][] {
						{"SampleTask1", "16/11/14"},
						{"SampleTask2", "18/11/14"},
				},
				new String[] {
						"Task", "Deadline"
				}
				) {
			boolean[] columnEditables = new boolean[] {
					true, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table_1.getColumnModel().getColumn(0).setPreferredWidth(421);

		scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		tabbedPane.addTab("Display", null, scrollPane, null);

		mainDisplay = new JTextArea();
		mainDisplay.setColumns(87);
		scrollPane.setViewportView(mainDisplay);
		mainDisplay.setRows(15);
		mainDisplay.setForeground(Color.DARK_GRAY);
		mainDisplay.setFont(new Font("Consolas", Font.PLAIN, 12));
		mainDisplay.setBackground(Color.WHITE);
		mainDisplay.setEditable(false);
		mainDisplay.setText("Welcome to Remembra!\nFor a quick guide, type help and press enter.");
		mainDisplay.setBounds(14, 22, 323, 120);
		tabbedPane.addTab("Table", null, table_1, null);

		colorPanel1 = new JPanel();
		colorPanel1.setBackground(new Color(205, 92, 92));
		colorPanel1.setBounds(37, 319, 19, 44);
		frameRemembra.getContentPane().add(colorPanel1);

		colorPanel2 = new JPanel();
		colorPanel2.setBackground(new Color(0, 153, 102));
		colorPanel2.setBounds(0, 444, 715, 52);
		frameRemembra.getContentPane().add(colorPanel2);
		
		JPanel panel = new JPanel();
		panel.setBounds(37, 25, 639, 246);
		frameRemembra.getContentPane().add(panel);
		panel.setBackground(Color.WHITE);

		colorPanel3 = new JPanel();
		colorPanel3.setBounds(0, 0, 715, 445);
		frameRemembra.getContentPane().add(colorPanel3);
		colorPanel3.setBackground(new Color(204, 204, 255));

		colorPanel4 = new JPanel();
		colorPanel4.setBackground(new Color(220, 220, 220));
		colorPanel4.setBounds(-1, 0, 715, 418);
		frameRemembra.getContentPane().add(colorPanel4);
		
		//Sets the Frame's display icon
		setFrameIcon();
		
		//Check for system tray support before initializing system tray
		if(checkSystemTraySupport()){
			initSystemTray();
		}
		
		//Activate Jfram windowstate listener for hiding programe into system tray
		activateWindowStateListener();
		
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