/**
 * @author Sankalp 
 * 
 * 
 */
import java.awt.EventQueue;

import javax.swing.JFrame;
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
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTabbedPane;

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
		frameRemembra.setBackground(new Color(192, 192, 192));
		frameRemembra.setTitle("Remembra V0.1");
		try {
			UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
		} catch (Exception e) {
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
		frameRemembra.setBounds(100, 100, 653, 467);
		frameRemembra.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frameRemembra.getContentPane().setLayout(null);

		JPanel displayPanel = new JPanel();
		displayPanel.setBackground(new Color(192, 192, 192));
		displayPanel.setBounds(0, 0, 654, 324);
		frameRemembra.getContentPane().add(displayPanel);
		displayPanel.setLayout(new FlowLayout());

		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
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
		tabbedPane.addTab("Display", null, scrollPane, null);

		mainDisplay = new JTextArea();
		scrollPane.setViewportView(mainDisplay);
		mainDisplay.setColumns(80);
		mainDisplay.setRows(20);
		mainDisplay.setForeground(Color.DARK_GRAY);
		mainDisplay.setFont(new Font("Consolas", Font.PLAIN, 12));
		mainDisplay.setBackground(Color.WHITE);
		mainDisplay.setEditable(false);
		mainDisplay.setText("Welcome to Remembra!\nFor a quick guide, type help and press enter.");
		mainDisplay.setBounds(14, 22, 323, 120);
		tabbedPane.addTab("Table", null, table_1, null);

		JPanel inputPanel = new JPanel();
		inputPanel.setBounds(0, 324, 648, 35);
		frameRemembra.getContentPane().add(inputPanel);
		inputPanel.setBackground(new Color(128, 128, 128));
		inputPanel.setLayout(new FlowLayout());

		inputField = new JTextField();
		inputField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		inputField.setForeground(new Color(165, 42, 42));
		inputField.setBackground(new Color(255, 255, 255));
		inputPanel.add("SOUTH", inputField);
		inputField.setColumns(52);

		JPanel feedbackpanel = new JPanel();
		feedbackpanel.setBackground(new Color(192, 192, 192));
		feedbackpanel.setBounds(0, 359, 648, 84);
		frameRemembra.getContentPane().add(feedbackpanel);
		feedbackpanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		scrollPane_1 = new JScrollPane();
		feedbackpanel.add(scrollPane_1);

		feedback = new JTextArea();
		feedback.setTabSize(10);
		scrollPane_1.setViewportView(feedback);
		feedback.setWrapStyleWord(true);
		feedback.setColumns(57);
		feedback.setForeground(new Color(85, 107, 47));
		feedback.setBackground(new Color(255, 255, 255));
		feedback.setRows(4);
		feedback.setFont(new Font("Arial", Font.PLAIN, 12));
		feedback.setEditable(false);
	}
}
